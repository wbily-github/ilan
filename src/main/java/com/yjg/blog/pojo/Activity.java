package com.yjg.blog.pojo;

import lombok.Data;

@Data
public class Activity {
    private Long id;
    private String content;
    private String createTime;
    private Integer authorId;
}
