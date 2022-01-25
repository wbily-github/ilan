package com.yjg.blog.pojo;

import java.util.List;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class Activity {
	private Long id;
	@NotBlank(message = "文章内容不能为空")
	private String content;
	private UserDTO author;
	private String createTime;
	private String username;
	private List<ImgDTO> imgs;
	private String dianzan;
	private String zanUser;
}
