package com.yjg.blog.controller;

import com.yjg.blog.pojo.FileDTO;
import com.yjg.blog.pojo.RespBean;
import com.yjg.blog.pojo.RespBean1;
import com.yjg.blog.utils.FastDFSClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.csource.fastdfs.FileInfo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.swing.filechooser.FileSystemView;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

@Slf4j
@RestController
public class CommonController {
    private String CONF_FILENAME = "fdfs_client.properties";

    /**
     * 文件上传
     *
     * @param fileDTO
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "file/uploadFast", method = RequestMethod.POST)
    public RespBean uploadFast(@RequestBody FileDTO fileDTO, HttpServletRequest request) throws Exception {
        // 1、把FastDFS提供的jar包添加到工程中
        // 2、初始化全局配置。加载一个配置文件。
        try {
            String confUrl = this.getClass()
                    .getClassLoader()
                    .getResource(CONF_FILENAME)
                    .getPath();
            FastDFSClient fastDFSClient = new FastDFSClient(confUrl);
            //上传文件
            //String filePath = fastDFSClient.uploadFile(fileDTO.getUploadFile());
            String filePath = fastDFSClient.uploadFile("C:/Users/Administrator/Desktop/id.jpg");
            System.out.println("返回路径：" + filePath);
            fileDTO.setFilePath(filePath);
            if (StringUtils.isNotBlank(filePath)) {
                return RespBean.success("上传成功", fileDTO, 1);
            } else {
                return RespBean.error("上传失败");
            }
        } catch (Exception e) {
            log.error("上传失败", e);
            return RespBean.error("上传失败");
        }
        //省略其他
    }

    /**
     * 删除
     *
     * @param fileDTO
     * @param request
     * @throws Exception
     */
    @RequestMapping(value = "file/deleteFast", method = RequestMethod.GET)
    public RespBean1 deleteFast(@RequestBody FileDTO fileDTO, HttpServletRequest request) throws Exception {

        try {
            String confUrl = this.getClass().getClassLoader().getResource("/fdfs_client.properties").getPath();
            FastDFSClient fastDFSClient = new FastDFSClient(confUrl);
            //删除文件
            int flag = fastDFSClient.delete_file(fileDTO.getFilePath());
            System.out.println("删除结果：" + (flag == 0 ? "删除成功" : "删除失败"));
            return RespBean1.success();
        } catch (Exception e) {
            return RespBean1.error("删除失败");
        }
    }

    /**
     * 下载
     *
     * @param fileDTO
     * @param request
     * @throws Exception
     */
    @RequestMapping(value = "file/downloadFast", method = RequestMethod.GET)
    public void downloadFast(@RequestBody FileDTO fileDTO, HttpServletRequest request) throws Exception {
        String confUrl = this.getClass().getClassLoader().getResource("/fdfs_client.properties").getPath();
        FastDFSClient fastDFSClient = new FastDFSClient(confUrl);
        //下载文件到用户桌面位置
        FileSystemView fsv = FileSystemView.getFileSystemView();
        File com = fsv.getHomeDirectory(); //读取桌面路径
        int downFlag = fastDFSClient.download_file(fileDTO.getFilePath(), new BufferedOutputStream(new FileOutputStream(com.getPath() + "\\aa.jpg")));
        System.out.println("下载结果为：" + (downFlag == 0 ? "下载文件成功" : "下载文件失败"));
    }

    /**
     * 查询信息
     *
     * @param request
     * @throws Exception
     */

    @RequestMapping(value = "file/queryFastInfo", method = RequestMethod.GET)
    public void queryFastInfo(HttpServletRequest request) throws Exception {
        String confUrl = this.getClass().getClassLoader().getResource("/fdfs_client.properties").getPath();
        FastDFSClient fastDFSClient = new FastDFSClient(confUrl);
        //获取文件信息
        FileInfo file = fastDFSClient.getFile("group1", "M00/00/00/wKgrPFpe9OqAWsHxAAH5yvc2jn8251.jpg");
        System.out.println("获取文件信息成功：" + file.getFileSize());
    }

}
