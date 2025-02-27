package com.teak.blog.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * The type Mybatis config.
 *
 * @author 柚mingle木
 * @version 1.0
 * @date 2023 /2/18
 */
@SpringBootConfiguration
public class MybatisConfig {
    // 主拦截器配置（分页插件）
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();

        PaginationInnerInterceptor paginationInterceptor = new PaginationInnerInterceptor(DbType.MYSQL);
        paginationInterceptor.setOptimizeJoin(true);
        // 分页插件
        interceptor.addInnerInterceptor(paginationInterceptor);
        // 防止全表更新与删除插件
        interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());
        return interceptor;
    }

    // 新增二级缓存配置（按需启用）
    @Bean
    public ConfigurationCustomizer configurationCustomizer() {
        return configuration -> {
            configuration.setCacheEnabled(true); // 开启二级缓存
            configuration.setLazyLoadingEnabled(true); // 延迟加载
        };
    }
}
