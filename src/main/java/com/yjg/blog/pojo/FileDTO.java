package com.yjg.blog.pojo;

import lombok.Data;

@Data
public class FileDTO {
    /**
     * 上传文件
     */
    private String uploadFile;
    /**
     * 文件服务器路径
     */
    private String filePath;
    /**
     *
     */
    private byte[] file;
    /**
     * 文件类型
     */
    private String fileClass;
}
