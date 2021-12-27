package com.yjg.blog.mybatis.dao;

import com.yjg.blog.pojo.Article;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface QueryDAO {

    @Select("<script> select (@i:=@i+1)  \"id\",a.title,a.content,a.urlKey ,  " +
            " DATE_FORMAT(a.missTime,'%Y-%m-%d %H:%i') as missTime,a.remark,a.author from article a ,(select @i := 0) t " +
            " where 1=1 "
            + "<if test='title!= null and !\"\".equals(title)'> and a.title like '%${title}%' </if>"
            + "<if test='content!= null and !\"\".equals(content)'> and a.content like '%${content}%' </if>"
            + "  order by a.id desc  "
           // + "<if test='page!= null and !\"0\".equals(page)'>  limit #{page},#{size} </if>"
            + "</script>")
    List<Article> queryLoginInfo(Article article);

    @Insert("<script> insert into article (id,title,content,urlKey, missTime,remark,author) " +
            "values (unix_timestamp(now()),#{title},#{content},#{urlKey},now(),#{remark},#{author})" +
            "</script>")
    void insertArticleInfo(Article article);

    @Select("<script> SELECT CONCAT(author,':',title) as title,content FROM article  WHERE TO_DAYS( missTime ) = TO_DAYS( now( ) ) " +
            "ORDER BY RAND( )  LIMIT 1 ;</script>")
    List<Article> getArticleToday();
}
