package com.yjg.blog.mybatis.dao;

import com.yjg.blog.pojo.SysRole;
import com.yjg.blog.pojo.UserDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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
    @Select("<script> select username,icon,DATE_ADD(now(),INTERVAL -2 MONTH)> nameChangeTime asnameChangeTime" +
            ", id,qx,sbbsj  from user where 1=1 "
            + "<if test='id!= null and !\"\".equals(id)'> and id = #{id} </if>"
            + "<if test='username!= null and !\"\".equals(username)'> and username = #{username} </if>"
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

    /**
     * 更新用户信息
     *
     * @param userDto
     * @return
     */
    @Update(" <script> update user set id = unix_timestamp(now()) " +
            " <trim prefix=\"set\" suffixOverrides=\",\"> "
            + "<if test='username != null and !\"\".equals(username)'> username=#{username}, </if>"
            + "<if test='icon != null and !\"\".equals(icon)'> icon=#{icon}, </if>"
            + "<if test='password != null and !\"\".equals(password)'> password=#{password}, </if>" +
            " </trim>where 1=1 " +
            "and id = #{id} </script> ")
    List<UserDTO> updateUserInfo(UserDTO userDto);
}
