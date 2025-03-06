package com.teak.blog.config;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.teak.blog.annotation.singleAnnotation.SnowflakeAlgorithm;
import com.teak.blog.utils.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created with: IntelliJ IDEA
 *
 * @Author: li zheng
 * @Date: 2025/3/5 19:14
 * @Project: teakWeb
 * @File: MybatisPlusAutoInsert.java
 * @Description: 如果想用mybatisPlus的自动填充需要在实体类的字段上添加@TableField(fill = FieldFill.INSERT)
 * @TableField(fill = FieldFill.INSERT_UPDATE)
 */
@Slf4j
@Component
public class MybatisPlusAutoInsert implements MetaObjectHandler {
    private final IdWorker idWorker;

    public MybatisPlusAutoInsert(IdWorker idWorker) {
        this.idWorker = idWorker;
    }

    @PostConstruct
    public void init() {
        log.info("✅ MyBatis Plus 自动填充处理器已注册"); // 启动时观察此日志
    }

    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("触发自动填充-插入，对象类型：{}", metaObject.getOriginalObject().getClass().getSimpleName());

        Object entity = metaObject.getOriginalObject();
        Class<?> entityClass = entity.getClass();

        // 获取所有数据库字段（包括父类）
        getFieldsRecursively(entityClass).forEach(field -> {
            String fieldName = field.getName();

            // 雪花算法ID填充
            if (field.isAnnotationPresent(SnowflakeAlgorithm.class)) {
                fillIfNull(metaObject, fieldName, Long.class, idWorker.nextId());
            }
        });


        // 基础字段填充
        this.strictInsertFill(metaObject, "status", Integer.class, 0);
        this.strictInsertFill(metaObject, "createTime", Date.class, new Date());
        this.strictInsertFill(metaObject, "updateTime", Date.class, new Date());
        this.strictInsertFill(metaObject, "isDeleted", Integer.class, 0);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("触发自动填充-更新，对象类型：{}", metaObject.getOriginalObject().getClass().getSimpleName());
        // 强制更新（无论字段是否有值）
        this.setFieldValByName("updateTime", new Date(), metaObject);
    }


    private <T> void fillIfNull(MetaObject metaObject, String fieldName, Class<T> fieldType, T value) {
        if (metaObject.getValue(fieldName) == null) {
            this.strictInsertFill(metaObject, fieldName, fieldType, value);
        }
    }

    private List<Field> getFieldsRecursively(Class<?> clazz) {
        List<Field> fields = new ArrayList<>();
        while (clazz != null) {
            // 过滤非数据库字段
            Arrays.stream(clazz.getDeclaredFields())
                    .filter(f -> {
                        TableField tf = f.getAnnotation(TableField.class);
                        return tf == null || tf.exist();
                    })
                    .forEach(fields::add);
            clazz = clazz.getSuperclass();
        }
        return fields;
    }
}
