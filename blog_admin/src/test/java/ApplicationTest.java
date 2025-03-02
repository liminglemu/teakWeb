import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.teak.blog.BlogAdminApplication;
import com.teak.blog.controller.XhProductController;
import com.teak.blog.model.Article;
import com.teak.blog.result.GlobalResult;
import com.teak.blog.service.ArticleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Lazy;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

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

    private final XhProductController xhProductController;
    private final ArticleService articleService;
    private final ExecutorService executorService;

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
        futures.add(executorService.submit(() -> {
            Thread currentThread = Thread.currentThread();
            for (int i = 0; i < 1000; i++) {
                log.info("线程1执行中 | 线程ID:{} 线程名称:{}", currentThread.getId(), currentThread.getName());
            }
        }));
        futures.add(executorService.submit(() -> {
            Thread currentThread = Thread.currentThread();
            for (int i = 0; i < 1000; i++) {
                log.info("线程2执行中 | 线程ID:{} 线程名称:{}", currentThread.getId(), currentThread.getName());
            }
        }));
        futures.add(executorService.submit(() -> {
            Thread currentThread = Thread.currentThread();
            for (int i = 0; i < 1000; i++) {
                log.info("线程3执行中 | 线程ID:{} 线程名称:{}", currentThread.getId(), currentThread.getName());
            }
        }));
        futures.add(executorService.submit(() -> {
            Thread currentThread = Thread.currentThread();
            for (int i = 0; i < 1000; i++) {
                log.info("线程4执行中 | 线程ID:{} 线程名称:{}", currentThread.getId(), currentThread.getName());
            }
        }));
        futures.add(executorService.submit(() -> {
            Thread currentThread = Thread.currentThread();
            for (int i = 0; i < 1000; i++) {
                log.info("线程5执行中 | 线程ID:{} 线程名称:{}", currentThread.getId(), currentThread.getName());
            }
        }));
        futures.add(executorService.submit(() -> {
            Thread currentThread = Thread.currentThread();
            for (int i = 0; i < 1000; i++) {
                log.info("线程6执行中 | 线程ID:{} 线程名称:{}", currentThread.getId(), currentThread.getName());
            }
        }));
        futures.add(executorService.submit(() -> {
            Thread currentThread = Thread.currentThread();
            for (int i = 0; i < 1000; i++) {
                log.info("线程7执行中 | 线程ID:{} 线程名称:{}", currentThread.getId(), currentThread.getName());
            }
        }));
        futures.add(executorService.submit(() -> {
            Thread currentThread = Thread.currentThread();
            for (int i = 0; i < 1000; i++) {
                log.info("线程8执行中 | 线程ID:{} 线程名称:{}", currentThread.getId(), currentThread.getName());
            }
        }));
        futures.add(executorService.submit(() -> {
            Thread currentThread = Thread.currentThread();
            for (int i = 0; i < 1000; i++) {
                log.info("线程9执行中 | 线程ID:{} 线程名称:{}", currentThread.getId(), currentThread.getName());
            }
        }));
        futures.add(executorService.submit(() -> {
            Thread currentThread = Thread.currentThread();
            for (int i = 0; i < 1000; i++) {
                log.info("线程10执行中 | 线程ID:{} 线程名称:{}", currentThread.getId(), currentThread.getName());
            }
        }));
        futures.forEach(future -> {
            try {
                future.get();
            } catch (Exception e) {
                log.error("线程执行异常：{}", e.getMessage(), e);
            }
        });
    }
}
