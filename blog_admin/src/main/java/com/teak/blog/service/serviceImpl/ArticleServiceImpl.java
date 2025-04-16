package com.teak.blog.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.teak.blog.entity.model.Article;
import com.teak.blog.entity.model.ArticleDetail;
import com.teak.blog.entity.model.Category;
import com.teak.blog.entity.model.UserDb;
import com.teak.blog.entity.vo.ArticleVo;
import com.teak.blog.mapper.ArticleDetailMapper;
import com.teak.blog.mapper.ArticleMapper;
import com.teak.blog.mapper.CategoryMapper;
import com.teak.blog.mapper.UserDbMapper;
import com.teak.blog.service.ArticleService;
import com.teak.blog.utils.TeakUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.teak.blog.ContentStatusConstants.STATUS_TO_CODE_MAP;

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
    private final ArticleDetailMapper articleDetailMapper;
    private final CategoryMapper categoryMapper;
    private final UserDbMapper userDbMapper;
    private final TeakUtils teakUtils;

    public ArticleServiceImpl(ArticleMapper articleMapper, ArticleDetailMapper articleDetailMapper, CategoryMapper categoryMapper, UserDbMapper userDbMapper, TeakUtils teakUtils) {
        this.articleMapper = articleMapper;
        this.articleDetailMapper = articleDetailMapper;
        this.categoryMapper = categoryMapper;
        this.userDbMapper = userDbMapper;
        this.teakUtils = teakUtils;
    }

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
            Integer code = STATUS_TO_CODE_MAP.get(status);
            wrapper.eq("status", code);
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
                if (article.getStatus() == 0) {
                    article.setStatusString("草稿");
                } else if (article.getStatus() == 1) {
                    article.setStatusString("已发布");
                }
            });
        }

        return page;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Article addArticle(ArticleVo articleVo) {
        Article article = new Article();
        teakUtils.copyProperties(articleVo, article);

        Category category = categoryMapper.getByCateName(articleVo.getCategoryName(), articleVo.getUserId());
        article.setCateId(category.getId());
        article.setStatus(Integer.valueOf(articleVo.getStatus()));
        articleMapper.insert(article);

        ArticleDetail articleDetail = new ArticleDetail();
        articleDetail.setArticleId(article.getId());
        articleDetail.setContent(articleVo.getContent());
        articleDetail.setArticleCoverUrl(articleVo.getArticleCoverUrl());

        articleDetailMapper.insert(articleDetail);
        return article;

    }

    @Override
    public void delArticle(Long id) {
        QueryWrapper<ArticleDetail> wrapper = new QueryWrapper<>();
        wrapper.eq("article_id", id);
        articleDetailMapper.delete(wrapper);
        articleMapper.deleteById(id);
    }
}
