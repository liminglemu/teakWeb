package com.teak.blog.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Created with: IntelliJ IDEA
 *
 * @Author:
 * @Date: 2023/11/16 10:41
 * @Project: crm
 * @File: TeakUtils.java
 * @Description: 封装工具
 */
@Component
@Slf4j
public class TeakUtils {

    /**
     * 一对一map分组
     *
     * @param dataList   数据
     * @param classifier 参照的字段
     * @return 返回分组后的map
     */
    public <K, F> Map<K, F> oneToOneGrouping(List<F> dataList, Function<F, K> classifier) {
        ConcurrentHashMap<K, F> hashMap = new ConcurrentHashMap<>();
        for (F data : dataList) {
            try {
                K k = classifier.apply(data);
                hashMap.put(k, data);
            } catch (Exception e) {
                throw new RuntimeException("字段值为空");
            }
        }
        return hashMap;
    }

    /**
     * 一对一map分组,可自定义map类型
     *
     * @param dataList   数据
     * @param classifier 参照的字段
     * @param map        自定义map
     * @return 返回分组后的map
     */
    public <K, F> Map<K, F> oneToOneGrouping(List<F> dataList, Function<F, K> classifier, Map<K, F> map) {
        for (F data : dataList) {
            try {
                K k = classifier.apply(data);
                map.put(k, data);
            } catch (Exception e) {
                throw new RuntimeException("字段值为空");
            }
        }
        return map;
    }

    /*private static MimeMessageHelper getMimeMessageHelper(ReceiverEntity receiverEntity, MimeMessage mimeMessage) throws MessagingException {
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        mimeMessageHelper.setFrom(receiverEntity.getSenderEmail());
        mimeMessageHelper.setTo(receiverEntity.getReceiver());
        mimeMessageHelper.setSubject(receiverEntity.getMessageSubject());
        if (receiverEntity.getMessageContent() == null) {
            throw new GlobalException("文本内容为空，无法进行发送邮件");
        }
        mimeMessageHelper.setText(receiverEntity.getMessageContent(), true);
        mimeMessageHelper.setSentDate(new Date());
        return mimeMessageHelper;
    }

    public void emailHandler(ReceiverEntity receiverEntity, MimeMessage mimeMessage) {
        try {
            MimeMessageHelper mimeMessageHelper = getMimeMessageHelper(receiverEntity, mimeMessage);
            if (receiverEntity.getFiles() != null && receiverEntity.getFiles().length > 0) {
                Arrays.stream(receiverEntity.getFiles()).forEach(file -> {
                    try {
                        mimeMessageHelper.addAttachment(file.getName(), file);
                    } catch (MessagingException e) {
                        throw new GlobalException("文件无法识别，请联系管理人员");
                    }
                });
            } else if (receiverEntity.getOutputStream() != null) {
                ByteArrayOutputStream outputStream = receiverEntity.getOutputStream();
                ByteArrayResource byteArrayResource = new ByteArrayResource(outputStream.toByteArray());
                mimeMessageHelper.addAttachment(receiverEntity.getMessageSubject() + ".docx", byteArrayResource);
            }
        } catch (MessagingException e) {
            throw new GlobalException(EmailResultEnum.FAIL.getMessage());
        }
    }*/

    /**
     * 根据某个字段进行自定义相加
     *
     * @param dataList
     * @param function
     * @param biFunction
     * @param <F>
     * @param <K>
     * @return
     */
    public <F, K> List<F> mergeBasedOnFields(List<F> dataList, Function<F, K> function, BiFunction<F, F, F> biFunction) {
        Map<K, F> map = new HashMap<>();
        for (F data : dataList) {
            K k = function.apply(data);
            map.merge(k, data, biFunction);
        }
        return new ArrayList<>(map.values());
    }


    /**
     * 字节转FileInputStream
     *
     * @param bytes
     * @return
     */
    public InputStream byteToFileInPutStream(byte[] bytes) {
        ByteArrayInputStream is = new ByteArrayInputStream(bytes);
        return is;
    }

}
