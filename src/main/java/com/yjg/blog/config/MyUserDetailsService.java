package com.yjg.blog.config;

import com.yjg.blog.pojo.UserDTO;
import com.yjg.blog.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
@Component
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    LoginService loginService;

    //test2
     @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        UserDTO user = loginService.getUserByUserName(username);
         List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
         GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_ADMIN");
         //此处将权限信息添加到 GrantedAuthority 对象中，在后面进行权限验证时会使用GrantedAuthority 对象。
         grantedAuthorities.add(grantedAuthority);
         MyUserPrincipal jwtUser = new MyUserPrincipal(user, grantedAuthorities);
             return jwtUser;
    }

}
