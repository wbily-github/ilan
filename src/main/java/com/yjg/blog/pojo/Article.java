package com.yjg.blog.pojo;

import lombok.Data;


@Data
public class Article {
    private Long id;
    //@NotBlank(message = "标题不能为空啊")
    private String title;
    private String content;
    private String urlKey;
    private String missTime;
    private String remark;
    private String author;
    private Integer page;
    private Integer size;
}
