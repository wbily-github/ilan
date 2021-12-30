package com.yjg.blog.pojo;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author yujiangong
 * @Title:
 * @Package
 * @Description:
 * @date 2021/12/214:21
 */
@Data
@ApiModel(value = "login对象", description = "登录bean")
public class UserDTO implements UserDetails {
    private Long id;
    private String username;
    private String password;
    private String icon;
    private String code;
    private String accountToken;
    private String sbbsj;
    private String kjjj;
    private Integer nameChangeTime;

   // private List<SysRole> roles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

   // public List<SysRole> getRoles() {  return roles; }

    //public void setRoles(List<SysRole> roles) {this.roles = roles;}

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> auths = new ArrayList<>();
      //  List<SysRole> roles = this.getRoles();
        //for (SysRole role : roles) {
       //     auths.add(new SimpleGrantedAuthority(role.getName()));
       // }
        return auths;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
