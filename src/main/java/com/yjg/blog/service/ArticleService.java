package com.yjg.blog.service;

import com.yjg.blog.mybatis.dao.QueryDAO;
import com.yjg.blog.pojo.Article;
import com.yjg.blog.pojo.RespBean;
import com.yjg.blog.pojo.RespBean1;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ArticleService {
    @Autowired
    private QueryDAO articleDAO;

    /**
     * 查询文章内容
     *
     * @param article
     * @return
     */

    public RespBean queryArticleInfo(Article article) {
        try {
            article.setPage((article.getPage()-1)* article.getSize());
            List<Article> users = articleDAO.queryLoginInfo(article);
          /*  article.setPage(null);
            List<Article> us = articleDAO.queryLoginInfo(article);*/
            if (users.size() > 0) {
                return RespBean.success("查询成功", users,users.size()/*null == us ? 0 :us.size()*/);
            } else {
                return RespBean.success("查询成功", new ArrayList<Article>(),0);
            }
        } catch (Exception e) {
            log.error("查询失败", e);
            return RespBean.success("查询失败", null);
        }
    }

    /**
     * 新增留言
     *
     * @param article
     * @return
     */

    public RespBean insertArticleInfo(Article article) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            article.setAuthor(auth.getName());
            articleDAO.insertArticleInfo(article);
            return RespBean.success("保存成功", new Article(),0);
        } catch (Exception e) {
            log.error("保存失败", e);
            return RespBean.success("保存失败", null);
        }
    }

    /**
     * 查询动态
     * @return
     */

    public RespBean1 getArticleToday() {
        try {
            List<Article> article = articleDAO.getArticleToday();
            if(null != article && article.size()>0) {
                return RespBean1.success(article.get(0));
            }else{
                Article art = new Article();
                art.setContent("今天什么事情也没发生噻~");
                return  RespBean1.success(art);
            }
        } catch (Exception e) {
            log.error("查询动态失败", e);
            return RespBean1.success(null);
        }
    }
}
