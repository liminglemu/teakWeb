package com.teak.blog.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.teak.blog.mapper.ArticleMapper;
import com.teak.blog.mapper.CategoryMapper;
import com.teak.blog.mapper.UserDbMapper;
import com.teak.blog.model.Article;
import com.teak.blog.model.Category;
import com.teak.blog.model.UserDb;
import com.teak.blog.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created with: IntelliJ IDEA
 *
 * @Author: li zheng
 * @Date: 2025/2/27 00:03
 * @Project: teakWeb
 * @File: ArticleServiceImpl.java
 * @Description:
 */
@Service
@Slf4j
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
    private final ArticleMapper articleMapper;
    private final CategoryMapper categoryMapper;
    private final UserDbMapper userDbMapper;

    public ArticleServiceImpl(ArticleMapper articleMapper, CategoryMapper categoryMapper, UserDbMapper userDbMapper) {
        this.articleMapper = articleMapper;
        this.categoryMapper = categoryMapper;
        this.userDbMapper = userDbMapper;
    }

    private static final String DRAFT_STATUS = "草稿";
    private static final String PUBLISHED_STATUS = "已发布";
    private static final int DRAFT_CODE = 0;
    private static final int PUBLISHED_CODE = 1;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Page<Article> getPage(int pageNum, int pageSize, String category, String status, Long userId) {
        // 1. 用户存在性校验（通过后续查询隐式完成）
        QueryWrapper<Article> wrapper = new QueryWrapper<Article>().eq("user_id", userId);

        // 2. 分类条件处理（合并到主查询）
        if (category != null) {
            Category categoryModel = categoryMapper.selectOne(
                    new QueryWrapper<Category>()
                            .select("id")
                            .eq("cate_name", category)
                            .eq("user_id", userId)
                            .last("LIMIT 1")
            );
            if (categoryModel == null) throw new RuntimeException("分类不存在");
            wrapper.eq("cate_id", categoryModel.getId());
        }

        if (status != null) {
            if (DRAFT_STATUS.equals(status)) {
                wrapper.eq("status", DRAFT_CODE);
            } else if (PUBLISHED_STATUS.equals(status)) {
                wrapper.eq("status", PUBLISHED_CODE);
            }
        }

        // 3. 执行分页查询（自动包含 COUNT 查询）
        Page<Article> page = articleMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);

        // 4. 批量填充分类名（减少查询次数）
        if (!page.getRecords().isEmpty()) {
            Set<Long> cateIds = page.getRecords().stream()
                    .map(Article::getCateId)
                    .collect(Collectors.toSet());

            Map<Long, String> cateNameMap = categoryMapper.selectBatchIds(cateIds).stream()
                    .collect(Collectors.toMap(Category::getId, Category::getCateName));

            // 从当前会话中获取用户信息（避免额外查询）
            UserDb currentUser = userDbMapper.selectById(userId); // 此查询会复用事务内的连接
            page.getRecords().forEach(article -> {
                article.setCateName(cateNameMap.get(article.getCateId()));
                article.setUserName(currentUser.getUserName());
            });
        }

        return page;
    }
}
