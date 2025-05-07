package com.teak.blog.utils;


import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Component;

import java.beans.FeatureDescriptor;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;
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


    /**
     * 赋值方法 原赋值给目标
     *
     * @param source 源数据
     * @param target 目标数据
     * @param <V>    原类型
     * @param <T>    目标类型
     */
    public <V, T> void copyProperties(V source, T target) {
        if (source == null) {
            return;
        }
        if (target == null) {
            return;
        }
        log.debug("Copying properties from {} to {}",
                source.getClass().getSimpleName(),
                target.getClass().getSimpleName());
        BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
    }

    // 在类顶部添加缓存声明
    private static final Cache<Class<?>, List<String>> PROPERTY_NAMES_CACHE = Caffeine.newBuilder().maximumSize(1000).build();

    /**
     * 获取空属性名数组工具方法
     */
    private String[] getNullPropertyNames(Object source) {
        // 获取类属性名列表（带缓存）
        List<String> propertyNames = PROPERTY_NAMES_CACHE.get(source.getClass(), clazz ->
                Arrays.stream(BeanUtils.getPropertyDescriptors(clazz))
                        .map(FeatureDescriptor::getName)
                        .filter(name -> !name.equals("class"))
                        .toList()
        );

        // 动态计算当前实例的空值属性
        BeanWrapper src = new BeanWrapperImpl(source);
        return propertyNames.stream()
                .filter(name -> src.getPropertyValue(name) == null)
                .toArray(String[]::new);
    }

    /**
     * 首字母小写并去除空格
     *
     * @param str 待处理的字符串
     * @return 返回字符串
     */
    public String lowerFirstCharAndTrim(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        char firstChar = Character.toLowerCase(str.charAt(0));
        String newString = (str.length() > 1) ? firstChar + str.substring(1) : String.valueOf(firstChar);
        return newString.trim();
    }


    /**
     * 解析引用类型和数组类型
     *
     * @param typeName 类型名称
     * @return 返回用逗号分割的引用类型和数组类型全类名
     */
    public String resolveReferenceClassName(String typeName) {
        if (typeName == null) {
            return null;
        }

        ArrayList<String> typeClassNames = new ArrayList<>();
        String[] split = typeName.split(",");
        for (String splitName : split) {
            try {
                boolean handled = false;

                // 处理引用类型
                Class<? extends Serializable> aClass = getAClass(splitName);
                if (aClass != null) {
                    typeClassNames.add(aClass.getName());
                    handled = true;
                }
                if ("Object".equals(splitName)) {
                    typeClassNames.add(Object.class.getName());
                    handled = true;
                }

                // 处理数组类型
                if (!handled && splitName.endsWith("[]")) {
                    String elementType = splitName.replace("[]", "");
                    Class<? extends Serializable> arrayAClass = getAClass(elementType);
                    if (arrayAClass != null) {
                        typeClassNames.add(Class.forName("[L" + arrayAClass.getName() + ";").getName());
                        handled = true;
                    }
                }

                // 基本数据类型
                if (!handled) {
                    typeClassNames.add(splitName);
                }
            } catch (ClassNotFoundException e) {
                log.error("无法解析类型: {}", splitName, e);
                throw new IllegalArgumentException("无效类型: " + splitName);
            }
        }
        return String.join(",", typeClassNames);
    }

    private static Class<? extends Serializable> getAClass(String splitName) {
        return switch (splitName) {
            case "Integer" -> Integer.class;
            case "Long" -> Long.class;
            case "String" -> String.class;
            case "Double" -> Double.class;
            case "Float" -> Float.class;
            case "BigDecimal" -> BigDecimal.class;
            case "Date" -> Date.class;
            case "Boolean" -> Boolean.class;
            case "Byte" -> Byte.class;
            case "Short" -> Short.class;
            case "Character" -> Character.class;
            default -> null;
        };
    }

    /**
     * 解析基本数据类型
     *
     * @param typeName 类型名称
     * @return 直接返回基本数据类型class类
     */
    public Class<? extends Serializable> resolveClassName(String typeName) {
        return switch (typeName) {
            case "int" -> int.class;
            case "long" -> long.class;
            case "double" -> double.class;
            case "boolean" -> boolean.class;
            case "char" -> char.class;
            case "float" -> float.class;
            case "byte" -> byte.class;
            case "short" -> short.class;
            default -> null;
        };
    }
}
