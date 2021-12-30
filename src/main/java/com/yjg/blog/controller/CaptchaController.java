package com.yjg.blog.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.yjg.blog.pojo.Article;
import com.yjg.blog.pojo.RespBean;
import com.yjg.blog.pojo.RespBean1;
import com.yjg.blog.pojo.UserDTO;
import com.yjg.blog.service.LoginService;
import com.yjg.blog.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.security.Principal;

/**
 *
 */
@Slf4j
@Validated
@RestController
public class CaptchaController {

    @Autowired
    private DefaultKaptcha defaultKaptcha;
    @Autowired
    private LoginService loginService;
    @Autowired
    private ArticleService queryService;

    /**
     * 验证码
     *
     * @author yujiangong
     * @since 1.0.0
     */
    // @GetMapping(value = "/captcha", produces = "image/jpeg")
    @RequestMapping(value = "/blog/captcha", method = RequestMethod.GET, produces = "image/jpeg")
    public void captcha(HttpServletRequest request, HttpServletResponse response) {
        // 定义response输出类型为image/jpeg类型
        response.setDateHeader("Expires", 0);
        // Set standard HTTP/1.1 no-cache headers.
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        // Set IE extended HTTP/1.1 no-cache headers (use addHeader).
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        // Set standard HTTP/1.0 no-cache header.
        response.setHeader("Pragma", "no-cache");
        // return a jpeg
        response.setContentType("image/jpeg");
        //-------------------生成验证码 begin --------------------------
        //获取验证码文本内容
        String text = defaultKaptcha.createText();
        System.out.println("验证码内容：" + text);
        //将验证码文本内容放入session
        request.getSession().setAttribute("captcha", text);
        //根据文本验证码内容创建图形验证码
        BufferedImage image = defaultKaptcha.createImage(text);
        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            //输出流输出图片，格式为jpg
            ImageIO.write(image, "jpg", outputStream);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != outputStream) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //-------------------生成验证码 end --------------------------

    }

    /**
     * 登录
     *
     * @param userDto
     * @param request
     * @return
     */
    @RequestMapping(value = "/blog/login/login", method = RequestMethod.POST)
    public RespBean login(@RequestBody UserDTO userDto, HttpServletRequest request) {
        log.info("#########QQQ登录入参QQQ#######" + userDto);
        return loginService.login(userDto, request);
    }

    /**
     * 注册方法
     *
     * @param userDto
     * @param request
     * @return
     */
    @PostMapping("/blog/login/register")
    public RespBean register(@RequestBody UserDTO userDto, HttpServletRequest request) {
        log.info("#########QQQ注册入参QQQ#######" + userDto);
        return loginService.register(userDto, request);
    }

    /**
     * 更新用户信息
     */
    @PostMapping("/blog/user/update")
    public RespBean saveUserInfo(@RequestBody UserDTO userDto, HttpServletRequest request) {
        log.info("修改信息入参" + userDto.toString());
        return loginService.updateUser(userDto);
    }

    /**
     * 查询留言
     *
     * @param article
     * @param request
     * @return
     */
    @PostMapping("/blog/getArt")
    public RespBean getArt(@Validated @RequestBody Article article, HttpServletRequest request) {
        log.info("#########QQQ查询入参QQQ#######" + article);
        return queryService.queryArticleInfo(article);
    }

    /**
     * 新增留言
     *
     * @param article
     * @param request
     * @return
     */
    @PostMapping("/blog/insertArt")
    public RespBean insertArt(@RequestBody Article article, HttpServletRequest request) {
        log.info("#########QQQ查询入参QQQ#######" + article);
        if (StringUtils.isBlank(article.getContent())) {
            article.setContent("没有内容噻~");
        }
        if (StringUtils.isBlank(article.getTitle())) {
            return RespBean.error("标题不能为空啊");
        }
        if (StringUtils.isBlank(article.getUrlKey())) {
            article.setUrlKey("../assets/index.jpg");
        }
        if (StringUtils.isBlank(article.getRemark())) {
            article.setRemark("没啥说的啊");
        }
        log.info("#########QQQ最后入参MMM#######" + article);
        return queryService.insertArticleInfo(article);
    }

    @PostMapping("/blog/login/kjjj")
    public RespBean1 getKjjj(HttpServletRequest request) {
        log.info("#########QQQ简介查询入参QQQ#######");

        return loginService.getKjjj();
    }


    /**
     * 今日份动态
     */
    @PostMapping("/blog/login/getHot")
    public RespBean1 getArtToday() {
        log.info("#########QQQ动态入参QQQ#######");
        return queryService.getArticleToday();
    }

    /**
     * 获取当前登录信息
     *
     * @param principal
     * @return
     */
    @GetMapping("/blog/user/info")
    public UserDTO getUserInfo(Principal principal) {
        if (null == principal) {
            return null;
        }
        String username = principal.getName();
        UserDTO userDTO = loginService.getUserByUserName(username);
        userDTO.setPassword(null);
        return userDTO;
    }

    /**
     * 注销
     *
     * @return
     */

    @PostMapping("/blog/logout")
    public RespBean logout() {
        return RespBean.success("注销成功", 0);
    }
}
