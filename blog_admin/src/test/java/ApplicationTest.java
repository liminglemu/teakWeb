import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.teak.blog.BlogAdminApplication;
import com.teak.blog.controller.XhProductController;
import com.teak.blog.entity.model.Article;
import com.teak.blog.entity.model.ArticleDetail;
import com.teak.blog.entity.model.UserDb;
import com.teak.blog.result.GlobalResult;
import com.teak.blog.service.ArticleDetailService;
import com.teak.blog.service.ArticleService;
import com.teak.blog.service.DeviceFaultRecordsService;
import com.teak.blog.utils.IdWorker;
import com.teak.blog.utils.TeakUtils;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created with: IntelliJ IDEA
 *
 * @Author: li zheng
 * @Date: 2025/2/23 20:50
 * @Project: teakWeb
 * @File: ApplicationTest.java
 * @Description:
 */
@RequiredArgsConstructor(onConstructor_ = {@Autowired, @Lazy})
@SpringBootTest(classes = BlogAdminApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
class ApplicationTest {

    // 创建ThreadLocal实例（建议用static final修饰）
    private static final ThreadLocal<UserDb> USER_CONTEXT = new ThreadLocal<>();
    private final XhProductController xhProductController;
    private final ArticleService articleService;
    private final ArticleDetailService articleDetailService;
    private final ExecutorService executorService;
    private final DeviceFaultRecordsService deviceFaultRecordsService;
    private final TeakUtils teakUtils;
    @Autowired
    private IdWorker idWorker;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private final ApplicationContext applicationContext;

    public static void main(String[] args) {
        /*String[] strings= {"String"};
        Class<? extends String[]> aClass = strings.getClass();
        log.info("aClass:{}", aClass);*/

        try {
            Class<?> aClass = Class.forName("int.class");
            log.info("aClass:{}", aClass);

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void test16() {

        String strings = teakUtils.resolveReferenceClassName("String,String[],int");
        log.info("解析名称为:{}", strings);
    }

    @Test
    void test15() {
        try {
            Class<?> aClass = Class.forName("com.teak.blog.entity.model.SysScheduledTask");
            log.info("aClass:{}", aClass);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void test14() {
        Object imgServiceImp = applicationContext.getBean("reportGenerateTask");
        try {
            Method scheduledTest = imgServiceImp.getClass().getMethod("generateDailyReport", String.class);
            log.info("generateDailyReport方法名为:{}", scheduledTest.getName());
            scheduledTest.invoke(imgServiceImp, "{\"format\":\"pdf\"}");
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    // 测试用内部类
    @Data
    static class TestDTO {
        private String name;
        private Integer age;
        private Boolean isAdmin;
    }

    @Data
    static class TestEntity {
        private String name;
        private Integer age;
        private Boolean isAdmin;
        private String existingField = "original";
    }

    @Test
    void test13() {
        // Arrange
        TestDTO source = new TestDTO();
        source.setName("John");
        source.setAge(null); // 空字段
        source.setIsAdmin(true);

        TestEntity target = new TestEntity();
        target.setName("Original");
        target.setAge(20);

        // Act
        teakUtils.copyProperties(source, target);

        log.info("target.getName:{}", target.getName());
        log.info("target.getAge:{}", target.getAge());
        log.info("target.getIsAdmin:{}", target.getIsAdmin());
        log.info("target.getExistingField:{}", target.getExistingField());
    }

    @Test
    void test12() {
//        articleDetailService.redisInstallTest(String.valueOf(l), "我是redis数据的value，你能在redis中获取到我吗？");
//        String revised = articleDetailService.redisGetTest("1905178472776019968");
        /*ArrayList<ArticleDetail> articleDetails = new ArrayList<>();
        ArticleDetail articleDetail1 = new ArticleDetail();
        ArticleDetail articleDetail2 = new ArticleDetail();
        ArticleDetail articleDetail3 = new ArticleDetail();
        articleDetails.add(articleDetail1);
        articleDetails.add(articleDetail2);
        articleDetails.add(articleDetail3);

        articleDetails.forEach(articleDetail -> {
            long l = idWorker.nextId();
            articleDetail.setId(l);
            articleDetail.setContent("我是redis数据的value，你能在redis中获取到我吗？" + l);
            articleDetail.setArticleName(articleDetail.getClass().getName());
        });

        redisTemplate.opsForValue().set("articleDetail", articleDetails);*/
        // 获取时直接指定类型（自动反序列化）
        List<ArticleDetail> revised = (List<ArticleDetail>) redisTemplate.opsForValue().get("articleDetail");
        log.info("反序列化结果：{}", revised);
    }

    @Test
    void test11() {
        deviceFaultRecordsService.getDeviceFaultRecords("2020-01-01 00:00:00", "2025-02-01 12:30:30");
    }

    @Test
    void test10() {
        ConcurrentHashMap<Long, Long> hashMap = new ConcurrentHashMap<>();
        ArrayList<Future<?>> futures = new ArrayList<>();
        for (int i = 0; i < 200; i++) {

            Future<?> future = executorService.submit(() -> {
                for (int j = 0; j < 1000; j++) {
                    long l = idWorker.nextId();
                    hashMap.putIfAbsent(l, l);
                }
            });

            futures.add(future);
        }
        futures.forEach(future -> {
            try {
                future.get();
            } catch (Exception e) {
                log.error("线程执行异常：{}", e.getMessage(), e);
            }
        });
        log.info("hashMap:{}", hashMap.size());

    }

    @Test
    void test9() {
        ConcurrentHashMap<Long, Long> hashMap = new ConcurrentHashMap<>();
        ArrayList<Future<?>> futures = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            Future<?> future = ThreadUtil.execAsync(() -> {
                for (int j = 0; j < 10000; j++) {
                    long l = idWorker.nextId();
                    hashMap.putIfAbsent(l, l);
                }
            });
            futures.add(future);
        }
        futures.forEach(future -> {
            try {
                future.get();
            } catch (Exception e) {
                log.error("线程执行异常：{}", e.getMessage(), e);
            }
        });
        log.info("hashMap:{}", hashMap.size());
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    @Rollback(value = false)
    void test8() {
        QueryWrapper<ArticleDetail> wrapper = new QueryWrapper<>();
        List<ArticleDetail> articleDetailList = articleDetailService.list(wrapper.gt("id", 1896058355961565184L));
        List<Long> list = articleDetailList.stream().map(ArticleDetail::getId).toList();
        articleDetailService.removeByIds(list);
    }

    @Test
    @Transactional
    void test7() {

        int count = 2;
        List<Future<?>> futures = new ArrayList<>(count);
        for (int i = 0; i < 2; i++) {
            Future<?> future = ThreadUtil.execAsync(() -> {
                ArrayList<ArticleDetail> articleDetails = new ArrayList<>();
                for (int j = 0; j < count; j++) {
                    ArticleDetail detail = new ArticleDetail();

                    detail.setContent(RandomUtil.randomString("\\u4e00-\\u9fa5", 30));

                    detail.setArticleCoverUrl("http://dashijian.oss-cn-beijing.aliyuncs.com/uploads/Screenshot%202024-08-04%20174752_20250302004714_7d704d5e.png?Expires=1741207634&OSSAccessKeyId=LTAI5t8tbEDxXwWupFqPaBEj&Signature=9U2yLxpItxaKcSOp%2B2AKl3zSCF8%3D");
                    articleDetails.add(detail);
                }
                articleDetailService.saveBatch(articleDetails);
            });
            futures.add(future);
        }
        futures.forEach(future -> {
            try {
                future.get();
            } catch (Exception e) {
                log.error("线程执行异常：{}", e.getMessage(), e);
            }
        });
    }

    @Test
    void test6() {
        ArrayList<Future<?>> futures = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            int finalI = i;
            Future<?> future = ThreadUtil.execAsync(() -> {
                Thread currentThread = Thread.currentThread();
                log.info("线程{}}执行中 | 线程ID:{} 线程名称:{}", finalI, currentThread.getId(), currentThread.getName());
                ThreadUtil.sleep(100);
            });
            futures.add(future);
        }

        futures.forEach(future -> {
            try {
                future.get();
            } catch (Exception e) {
                log.error("线程执行异常：{}", e.getMessage(), e);
            }
        });
    }

    @Test
    void test5() {
        List<ArticleDetail> articleDetailList = articleDetailService.list();
    }

    @Test
    void test1() {
        GlobalResult list = xhProductController.getList();
        log.info("获取商品列表：" + list);
    }

    @Test
    void test2() {
        Page<Article> page = articleService.getPage(1, 10, "678678L", "1", 1894330079119998976L);
        page.getRecords().forEach(System.out::println);
    }

    @Test
    void test3() {
        ArrayList<Future<?>> futures = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            int finalI = i;
            futures.add(executorService.submit(() -> {
                Thread currentThread = Thread.currentThread();
                try {
                    log.info("线程{}}执行中 | 线程ID:{} 线程名称:{}", finalI, currentThread.getId(), currentThread.getName());
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }));
        }

        futures.forEach(future -> {
            try {
                future.get();
            } catch (Exception e) {
                log.error("线程执行异常：{}", e.getMessage(), e);
            }
        });
    }

    @Test
    void test4() {

        int count = 10000;
        List<ArticleDetail> result = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            ArticleDetail detail = new ArticleDetail();
            // 使用雪花算法生成ID
            detail.setId(null);
            detail.setArticleId(null);
            detail.setFileId(null);

//            detail.setContent("离职。看见对方留下。杰克逊对方公开。在进行覅/方向相反高跟鞋micg/kf");
            detail.setContent(RandomUtil.randomString("\\u4e00-\\u9fa5", 30));

            detail.setArticleCoverUrl("http://dashijian.oss-cn-beijing.aliyuncs.com/uploads/Screenshot%202024-08-04%20174752_20250302004714_7d704d5e.png?Expires=1741207634&OSSAccessKeyId=LTAI5t8tbEDxXwWupFqPaBEj&Signature=9U2yLxpItxaKcSOp%2B2AKl3zSCF8%3D");
            result.add(detail);
        }
        articleDetailService.saveBatch(result);
    }

    // 示例：在多线程任务中使用
    @Test
    void testThreadLocalUsage() throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        IdWorker idWorker = new IdWorker();
        // 创建10个带用户信息的任务
        List<Callable<Void>> tasks = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            tasks.add(() -> {
                // 设置当前线程的用户数据
                UserDb user = new UserDb();
                user.setId(idWorker.nextId());
                user.setToken(idWorker.nextId() % 3);
                USER_CONTEXT.set(user);

                // 执行需要用户上下文的操作
                doBusinessLogic();

                // 必须清理ThreadLocal避免内存泄漏
                USER_CONTEXT.remove();
                return null;
            });
        }

        executor.invokeAll(tasks);
        executor.shutdown();
    }

    private void doBusinessLogic() {
        // 获取当前线程的用户数据
        UserDb currentUser = USER_CONTEXT.get();
        log.info("线程[{}]正在处理用户：{},Token:{}",
                Thread.currentThread().getName(),
                currentUser.getId(),
                currentUser.getToken());

        // 业务逻辑代码...
    }


}
