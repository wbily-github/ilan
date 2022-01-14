package com.yjg.blog.pojo;

import lombok.Data;

@Data
public class MyPhoto {
	private Long id;
	private String imgurl;
	private String folder;
	private String folderid;
	private String owner;
}
