package com.yjg.blog.mybatis.dao;

import com.yjg.blog.pojo.SysRole;
import com.yjg.blog.pojo.UserDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author yujiangong
 * @Title:
 * @Package
 * @Description:
 * @date 2021/12/215:16
 */
@Mapper
public interface LoginDAO {
    @Select("<script>select * from user where 1=1 "
            + "and username = #{username}"
            + "<if test='password!= null and !\"\".equals(password)'> and password = #{password} </if>" +
            "</script>")
    public List<UserDTO> queryLoginInfo(UserDTO user);

    /**
     * 保存
     */
    @Insert("insert into user ( id,username,password )values(unix_timestamp(now()),#{username},#{password}) ")
    void insertLoginInfo(UserDTO userDto);

    @Select("select id,accountToken from accounttoken where 1=1 "
            + "and accountToken = #{accountToken}")
    public List<UserDTO> queryTokenInfo(UserDTO user);

    @Select("select kjjj as kjjj from kjjj")
    List<UserDTO> queryKjjj();
    @Select("select id,name,userid  from sysrole where userid = #{userId}")
    List<SysRole> queryRoles(SysRole sysRole);
}
