package com.yjg.blog.pojo;

import lombok.Data;

@Data
public class ImgDTO {
	private Long id;
	private String urlKey;
	private String url;
	private Long articleId;

}
