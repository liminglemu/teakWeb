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
}
