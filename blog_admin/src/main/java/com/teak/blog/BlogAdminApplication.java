package com.teak.blog;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * The type Blog admin application.
 *
 * @author 柚mingle木
 * @version 1.0
 * @date 2023 /2/18
 */
@Slf4j
@EnableScheduling
@SpringBootApplication
@EnableTransactionManagement // 启用事务管理
@MapperScan(basePackages = "com.teak.blog.mapper")
public class BlogAdminApplication {
    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(BlogAdminApplication.class, args);
        log.info("""
               
                                                                                                                                                                                \s
                                                                                                                                                              bbbbbbb           \s
                         tttt                                                 kkkkkkk                                                                         b:::::b           \s
                      ttt:::t                                                 k:::::k                                                                         b:::::b           \s
                      t:::::t                                                 k:::::k                                                                         b:::::b           \s
                      t:::::t                                                 k:::::k                                                                         b:::::b           \s
                ttttttt:::::ttttttt        eeeeeeeeeeee      aaaaaaaaaaaaa    k:::::k    kkkkkkkwwwwwww           wwwww           wwwwwww    eeeeeeeeeeee     b:::::bbbbbbbbb   \s
                t:::::::::::::::::t      ee::::::::::::ee    a::::::::::::a   k:::::k   k:::::k  w:::::w         w:::::w         w:::::w   ee::::::::::::ee   b::::::::::::::bb \s
                t:::::::::::::::::t     e::::::eeeee:::::ee  aaaaaaaaa:::::a  k:::::k  k:::::k    w:::::w       w:::::::w       w:::::w   e::::::eeeee:::::ee b::::::::::::::::b\s
                tttttt:::::::tttttt    e::::::e     e:::::e           a::::a  k:::::k k:::::k      w:::::w     w:::::::::w     w:::::w   e::::::e     e:::::e b:::::bbbbb:::::::b
                      t:::::t          e:::::::eeeee::::::e    aaaaaaa:::::a  k::::::k:::::k        w:::::w   w:::::w:::::w   w:::::w    e:::::::eeeee::::::e b:::::b    b::::::b
                      t:::::t          e:::::::::::::::::e   aa::::::::::::a  k:::::::::::k          w:::::w w:::::w w:::::w w:::::w     e:::::::::::::::::e  b:::::b     b:::::b
                      t:::::t          e::::::eeeeeeeeeee   a::::aaaa::::::a  k:::::::::::k           w:::::w:::::w   w:::::w:::::w      e::::::eeeeeeeeeee   b:::::b     b:::::b
                      t:::::t    tttttte:::::::e           a::::a    a:::::a  k::::::k:::::k           w:::::::::w     w:::::::::w       e:::::::e            b:::::b     b:::::b
                      t::::::tttt:::::te::::::::e          a::::a    a:::::a k::::::k k:::::k           w:::::::w       w:::::::w        e::::::::e           b:::::bbbbbb::::::b
                      tt::::::::::::::t e::::::::eeeeeeee  a:::::aaaa::::::a k::::::k  k:::::k           w:::::w         w:::::w          e::::::::eeeeeeee   b::::::::::::::::b\s
                        tt:::::::::::tt  ee:::::::::::::e   a::::::::::aa:::ak::::::k   k:::::k           w:::w           w:::w            ee:::::::::::::e   b:::::::::::::::b \s
                          ttttttttttt      eeeeeeeeeeeeee    aaaaaaaaaa  aaaakkkkkkkk    kkkkkkk           www             www               eeeeeeeeeeeeee   bbbbbbbbbbbbbbbb  \s
               """);
    }
}
