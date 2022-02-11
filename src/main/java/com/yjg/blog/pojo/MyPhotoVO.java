package com.yjg.blog.pojo;

import java.util.List;

import lombok.Data;

@Data
public class MyPhotoVO {
private String folder;
private String folderid;
private Integer folderType;
private String owner;
private List<MyPhoto> imgs; 
}
