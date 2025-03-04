import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.teak.blog.BlogAdminApplication;
import com.teak.blog.controller.XhProductController;
import com.teak.blog.model.Article;
import com.teak.blog.model.ArticleDetail;
import com.teak.blog.model.Cart;
import com.teak.blog.model.UserDb;
import com.teak.blog.result.GlobalResult;
import com.teak.blog.service.ArticleDetailService;
import com.teak.blog.service.ArticleService;
import com.teak.blog.utils.IdWorker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Lazy;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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
    private final ArticleDetailService articleDetailService;
    private final ExecutorService executorService;

    public static void main(String[] args) {
        Cart cart = new Cart();
        cart.setId(1L);
        cart.setName("测试");
        cart.setPrice(new BigDecimal("100.00"));
        cart.setCount(1L);
        cart.setThumb("https://www.baidu.com");

        Cart cart1 = new Cart();
        cart1 = cart;

        cart1.setId(2L);
        cart1.setName("测试1");

        log.info("cart:{}", cart);
        log.info("cart1:{}", cart1);

        Cart cart2 = new Cart();
        BeanUtils.copyProperties(cart, cart2);
        cart2.setId(3L);
        cart2.setName("测试2");
        log.info("cart2:{}", cart2);

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
            detail.setContent(cn.hutool.core.util.RandomUtil.randomString("\\u4e00-\\u9fa5", 30));

            detail.setArticleCoverUrl("http://dashijian.oss-cn-beijing.aliyuncs.com/uploads/Screenshot%202024-08-04%20174752_20250302004714_7d704d5e.png?Expires=1741207634&OSSAccessKeyId=LTAI5t8tbEDxXwWupFqPaBEj&Signature=9U2yLxpItxaKcSOp%2B2AKl3zSCF8%3D");
            result.add(detail);
        }
        articleDetailService.saveBatch(result);
    }

    // 创建ThreadLocal实例（建议用static final修饰）
    private static final ThreadLocal<UserDb> USER_CONTEXT = new ThreadLocal<>();

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
