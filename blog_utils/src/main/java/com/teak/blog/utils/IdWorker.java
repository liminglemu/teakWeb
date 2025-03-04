package com.teak.blog.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * Created with: IntelliJ IDEA
 *
 * @Author: li zheng
 * @Date: 2025/2/16 02:07
 * @Project: teakWeb
 * @File: IdWorker.java
 * @Description: 核心代码为其IdWorker这个类实现，其原理结构如下，我分别用一个0表示一位，用—分割开部分的作用：
 * 1||0---0000000000 0000000000 0000000000 0000000000 0 --- 00000 ---00000 ---000000000000
 * 在上面的字符串中，第一位为未使用（实际上也可作为long的符号位），接下来的41位为毫秒级时间，
 * 然后5位datacenter标识位，5位机器ID（并不算标识符，实际是为线程标识），
 * 然后12位该毫秒内的当前毫秒内的计数，加起来刚好64位，为一个Long型。
 * 这样的好处是，整体上按照时间自增排序，并且整个分布式系统内不会产生ID碰撞（由datacenter和机器ID作区分），
 * 并且效率较高，经测试，snowflake每秒能够产生26万ID左右，完全满足需要。
 */
@Slf4j
@Component
public final class IdWorker {
    /**
     * 时间起始标记点，作为基准，一般取系统的最近时间（一旦确定不能变动）
     */
    private static final long TWEPOCH = 1288834974657L;
    /**
     * 机器标识位数
     */
    private static final long WORKER_ID_BITS = 5L;
    /**
     * 数据中心标识位数
     */
    private static final long DATA_CENTER_ID_BITS = 5L;
    /**
     * 机器ID最大值
     */
    private static final long MAX_WORKER_ID = ~(-1L << WORKER_ID_BITS);
    /**
     * 数据中心ID最大值
     */
    private static final long MAX_DATA_CENTER_ID = ~(-1L << DATA_CENTER_ID_BITS);
    /**
     * 毫秒内自增位
     */
    private static final long SEQUENCE_BITS = 12L;
    /**
     * 机器ID偏左移12位
     */
    private static final long WORKER_ID_SHIFT = SEQUENCE_BITS;
    /**
     * 数据中心ID左移17位
     */
    private static final long DATA_CENTER_ID_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS;
    /**
     * 时间毫秒左移22位
     */
    private static final long TIME_STAMP_LEFT_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS + DATA_CENTER_ID_BITS;

    private static final long SEQUENCE_MASK = ~(-1L << SEQUENCE_BITS);
    /**
     * 上次生产id时间戳
     * 使用static修饰，当存在多个IdWorker实例时会导致时间戳状态共享，可能产生重复ID。应改为实例变量
     */
    private long lastTimeStamp = -1L;
    /**
     * 0，并发控制
     */
    private long sequence = 0L;

    private final long workerId;

    /**
     * 数据标识id部分
     */
    private final long DATA_CENTER_ID;

    /**
     *  添加时钟回拨容忍阈值（3秒）
     *  */
    private static final long MAX_BACKWARD_MS = 1000;



    public IdWorker() {
        this.DATA_CENTER_ID = getDatacenterId(MAX_DATA_CENTER_ID);
        this.workerId = getMaxWorkerId(DATA_CENTER_ID, MAX_WORKER_ID);
    }

    /**
     * 获取下一个ID
     *
     * @return id
     */
    public synchronized long nextId() {
        long timestamp = timeGen();

        // 增强时钟回拨处理
        if (timestamp < lastTimeStamp) {
            long offset = lastTimeStamp - timestamp;
            if (offset > MAX_BACKWARD_MS) {
                throw new RuntimeException(String.format("时钟回拨超过阈值，拒绝生成ID %d ms", offset));
            }
            try {
                TimeUnit.MILLISECONDS.sleep(offset);
                timestamp = timeGen();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("时钟回拨等待中断", e);
            }
        }

        if (timestamp == lastTimeStamp) {
            sequence = (sequence + 1) & SEQUENCE_MASK;
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimeStamp);
            }
        } else {
            sequence = 0L;
        }
        lastTimeStamp = timestamp;

        // 添加位移注释说明
        return ((timestamp - TWEPOCH) << TIME_STAMP_LEFT_SHIFT) // 41位时间戳
                | (DATA_CENTER_ID << DATA_CENTER_ID_SHIFT)      // 5位数据中心
                | (workerId << WORKER_ID_SHIFT)                 // 5位工作节点
                | sequence;                                     // 12位序列号
    }

    private long tilNextMillis(final long lastTimestamp) {
        long timestamp = this.timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = this.timeGen();
        }
        return timestamp;
    }

    private long timeGen() {
        return System.currentTimeMillis();
    }

    /**
     * <p>
     * 数据标识id部分
     * </p>
     */
    private static long getDatacenterId(long maxDatacenterId) {
        long id = 0L;
        try {
            InetAddress ip = InetAddress.getLocalHost();
            NetworkInterface network = NetworkInterface.getByInetAddress(ip);
            if (network == null) {
                id = 1L;
            } else {
                byte[] mac = network.getHardwareAddress();
                id = ((0x000000FF & (long) mac[mac.length - 1])
                        | (0x0000FF00 & (((long) mac[mac.length - 2]) << 8))) >> 6;
                id = id % (maxDatacenterId + 1);
            }
        } catch (Exception e) {
            log.error("获取数据中心ID失败，使用随机回退策略", e);
            // 添加随机fallback
            return ThreadLocalRandom.current().nextLong(maxDatacenterId + 1);
        }
        return id;
    }

    /**
     * <p>
     * 获取 maxWorkerId
     * </p>
     */
    private static long getMaxWorkerId(long dataCenterId, long maxWorkerId) {
        StringBuilder mpid = new StringBuilder();
        mpid.append(dataCenterId);
        String name = ManagementFactory.getRuntimeMXBean().getName();
        if (!name.isEmpty()) {
            /*
             * GET jvmPid
             */
            mpid.append(name.split("@")[0]);
        }
        /*
         * MAC + PID 的 hashcode 获取16个低位
         */
        return (mpid.toString().hashCode() & 0xffff) % (maxWorkerId + 1);
    }
}
