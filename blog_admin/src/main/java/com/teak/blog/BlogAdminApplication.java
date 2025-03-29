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
                
                
                    ___                                 ,-.          .---.
                  ,--.'|_                           ,--/ /|         /. ./|            ,---,
                  |  | :,'                        ,--. :/ |     .--'.  ' ;          ,---.'|
                  :  : ' :                        :  : ' /     /__./ \\ : |          |   | :
                .;__,'  /     ,---.     ,--.--.   |  '  /  .--'.  '   \\' .   ,---.  :   : :
                |  |   |     /     \\   /       \\  '  |  : /___/ \\ |    ' '  /     \\ :     |,-.
                :__,'| :    /    /  | .--.  .-. | |  |   \\;   \\  \\;      : /    /  ||   : '  |
                  '  : |__ .    ' / |  \\__\\/: . . '  : |. \\\\   ;  `      |.    ' / ||   |  / :
                  |  | '.'|'   ;   /|  ," .--.; | |  | ' \\ \\.   \\    .\\  ;'   ;   /|'   : |: |
                  ;  :    ;'   |  / | /  /  ,.  | '  : |--'  \\   \\   ' \\ |'   |  / ||   | '/ :
                  |  ,   / |   :    |;  :   .'   \\;  |,'      :   '  |--" |   :    ||   :    |
                   ---`-'   \\   \\  / |  ,     .-./'--'         \\   \\ ;     \\   \\  / /    \\  /
                             `----'   `--`---'                  '---"       `----'  `-'----'
                """);
    }
}
