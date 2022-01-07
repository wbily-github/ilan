package com.yjg.blog.pojo;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class Activity {
	private Long id;
	@NotBlank(message = "文章内容不能为空")
	private String content;
	private String createTime;
	private String author;
}
