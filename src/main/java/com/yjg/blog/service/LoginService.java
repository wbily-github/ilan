package com.yjg.blog.service;

import com.yjg.blog.config.JwtTokenUtil;
import com.yjg.blog.config.MyUserDetailsService;
import com.yjg.blog.mybatis.dao.LoginDAO;
import com.yjg.blog.pojo.RespBean;
import com.yjg.blog.pojo.RespBean1;
import com.yjg.blog.pojo.SysRole;
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
        //更新security登录用户对象
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null
                , userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        //生成token
        log.info("userDetails@@@@@@@@@@@@@@@@@@@"+userDetails.getPassword()+userDetails.getUsername()+userDetails.getAuthorities());
        String token = jwtTokenUtil.getToken(userDetails);
        Map<String, String> tokenMap = new HashMap<>(3);
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        return RespBean.success("登陆成功", tokenMap);
    }

    /**
     * 登录
     *
     * @param userDto
     * @param request
     * @return
     */
 /*   public RespBean login(UserDTO userDto, HttpServletRequest request) {
        //登录
        //  UserDetails userDetails = userDetailsService.loadUserByUsername(userDto.getUsername());
        String captcha = (String) request.getSession().getAttribute("captcha");
        if (StringUtils.isEmpty(userDto.getCode()) || !captcha.equalsIgnoreCase(userDto.getCode())) {
            return RespBean.error("验证码输入错误，请重新输入！");
        }
        try {
            List<UserDTO> users = loginDao.queryLoginInfo(userDto);
            if (users.size() > 0) {
                log.info("^^^^^^^^^^^^^^^^^^^^^^^" + RespBean.success("登陆成功", users));
                return RespBean.success("登陆成功", users);
            }
            return RespBean.success("登录失败,账号或密码不对", null);
        } catch (Exception e) {
            log.info("失败了" + e.getMessage(), e);
            return RespBean.error("登录失败，服务器异常", null);
        }
    }*/

    /**
     * 注册方法
     *
     * @param userDto
     * @param request
     * @return
     */

    public RespBean register(UserDTO userDto, HttpServletRequest request) {
        String captcha = (String) request.getSession().getAttribute("captcha");
        if (StringUtils.isEmpty(userDto.getCode()) || !captcha.equalsIgnoreCase(userDto.getCode())) {
            return RespBean.error("验证码输入错误，请重新输入！");
        }
        try {
            List<UserDTO> user1 = loginDao.queryTokenInfo(userDto);
            if (user1.size() == 0) {
                return RespBean.error("注册失败,请输入正确令牌", user1);
            }
            loginDao.insertLoginInfo(userDto);
            return RespBean.success("注册成功,", userDto);
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
            log.info("$$$$$"+SecurityContextHolder.getContext().getAuthentication());
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
    public List<SysRole> getRole(SysRole sysRole){
        return loginDao.queryRoles(sysRole);
    }
}
