package com.yjg.blog.config;

import com.yjg.blog.pojo.SysRole;
import com.yjg.blog.pojo.UserDTO;
import com.yjg.blog.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    LoginService loginService;

  /*  @Override
    public UserDetails loadUserByUsername(String username) {
        UserDTO user = loginService.getUserByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new MyUserPrincipal(user);
    }
*/
    //test2
     @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        UserDTO user = loginService.getUserByUserName(username);
         SysRole sysRole = new SysRole();
         sysRole.setUserId(user.getId());
         List<SysRole> syss = loginService.getRole(sysRole);
         user.setRoles(syss);
         if (user == null) {
             throw new UsernameNotFoundException("用户名不存在");
         }
         return user;
    }

}
