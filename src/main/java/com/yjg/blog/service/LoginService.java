package com.yjg.blog.service;

import com.yjg.blog.config.JwtTokenUtil;
import com.yjg.blog.config.MyUserDetailsService;
import com.yjg.blog.mybatis.dao.LoginDAO;
import com.yjg.blog.pojo.RespBean;
import com.yjg.blog.pojo.RespBean1;
import com.yjg.blog.pojo.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yujiangong
 * @Title:
 * @Package
 * @Description:
 * @date 2021/12/214:23
 */
@Slf4j
@Service
public class LoginService {

    @Autowired
    private LoginDAO loginDao;
    @Autowired
    private MyUserDetailsService myUserDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    /**
     * 登录返沪token
     *
     * @param userDto
     * @param request
     * @return
     */
    public RespBean login(UserDTO userDto, HttpServletRequest request) {
        //登录
        UserDetails userDetails = myUserDetailsService.loadUserByUsername(userDto.getUsername());
        String captcha = (String) request.getSession().getAttribute("captcha");
        if (StringUtils.isEmpty(userDto.getCode()) || !captcha.equalsIgnoreCase(userDto.getCode())) {
            return RespBean.error("验证码输入错误，请重新输入！");
        }
        if (null == userDetails || passwordEncoder.matches(userDto.getPassword(), userDetails.getPassword())) {
            return RespBean.error("用户名或密码不正确");
        }
        if (userDetails.isEnabled()) {
            return RespBean.error("账号被禁用，请联系管理员");
        }
        List<UserDTO> users = loginDao.queryLoginInfo(userDto);
        //更新security登录用户对象
        log.info("$&^%$#$%&^#@" + userDetails.getAuthorities());
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword()
                , userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        //生成token
        log.info("userDetails@@@@@@@@@@@@@@@@@@@" + userDetails.getPassword() + userDetails.getUsername() + userDetails.getAuthorities());
        String token = jwtTokenUtil.getToken(userDetails);
        Map<String, String> tokenMap = new HashMap<>(3);
        tokenMap.put("token", token);
        tokenMap.put("username", userDto.getUsername());
        if (null != users && users.size() > 0) {
            tokenMap.put("icon", users.get(0).getIcon());
        }
        log.info("token++++++++++++++++++++++++++++" + token);
        tokenMap.put("tokenHead", tokenHead);
        return RespBean.success("登陆成功", tokenMap, 0);
    }


    /**
     * 注册方法
     *
     * @param userDto
     * @param request
     * @return
     */

    public RespBean register(UserDTO userDto, HttpServletRequest request) {

        try {
            String captcha = (String) request.getSession().getAttribute("captcha");
            if (StringUtils.isEmpty(userDto.getCode()) || !captcha.equalsIgnoreCase(userDto.getCode())) {
                return RespBean.error("验证码输入错误，请重新输入！");
            }
            UserDTO userdto1 = new UserDTO();
            userdto1.setUsername(userDto.getUsername());
            List<UserDTO> user11 = loginDao.queryLoginInfo(userdto1);
            if (null != user11 && user11.size() > 0) {
                return RespBean.error("该用户已注册，您可以直接使用该用户登录", user11);
            }
            List<UserDTO> user1 = loginDao.queryTokenInfo(userDto);
            if (user1.size() == 0) {
                return RespBean.error("注册失败,请输入正确令牌", user1);
            }
            loginDao.insertLoginInfo(userDto);
            return RespBean.success("注册成功", userDto, 0);
        } catch (Exception e) {
            log.info("失败了" + e.getMessage(), e);
            return RespBean.error("注册失败，服务器异常，主人没空改，洗洗睡吧", null);
        }
    }

    /**
     * 简介
     *
     * @return
     */
    public RespBean1 getKjjj() {
        try {
            log.info("$$$$$" + SecurityContextHolder.getContext().getAuthentication());
            List<UserDTO> user1 = loginDao.queryKjjj();
            return RespBean1.success(user1.get(0));
        } catch (Exception e) {
            log.info("失败了" + e.getMessage(), e);
            return RespBean1.error("简介暂未到位");
        }
    }

    /**
     * 获取用户信息
     *
     * @param username
     * @return
     */
    public UserDTO getUserByUserName(String username) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(username);
        List<UserDTO> users = loginDao.queryLoginInfo(userDTO);
        if (null != users && users.size() > 0) {
            return users.get(0);
        } else {
            log.info("有没有搞错，都没查出来却能登陆");
            return new UserDTO();
        }
    }

    /**
     * 新增修改个人信息方法
     *
     * @param userDto
     * @return
     */
    public RespBean updateUser(UserDTO userDto) {
        try {
            UserDTO dto = new UserDTO();
            dto.setId(userDto.getId());
            List<UserDTO> user11 = loginDao.queryLoginInfo(userDto);
            if (null == user11 || user11.size() == 0) {
                return RespBean.error("用户信息不存在");
            } else if (user11.get(0).getUsername() != userDto.getUsername()
                    && 0 == user11.get(0).getNameChangeTime()) {
                return RespBean.error("修改失败,距离上次修改用户名未满一个月，不能修改");
            }
            loginDao.updateUserInfo(userDto);
            return RespBean.success("修改成功", userDto, 1);
        } catch (Exception e) {
            log.error("修改失败", e);
            return RespBean.error("修改失败");
        }
    }
}
