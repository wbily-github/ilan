package com.yjg.blog.mybatis.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.yjg.blog.pojo.Activity;
import com.yjg.blog.pojo.ImgDTO;
import com.yjg.blog.pojo.MyPhoto;
import com.yjg.blog.pojo.MyPhotoVO;

@Mapper
public interface ActivityDAO {
	/**
	 * 插入动态表
	 * 
	 * @param activityDAO
	 */
	@Insert("insert into activity (id,content,author,createTime) values(#{id},#{content},#{username},now())")
	public void inertActivity(Activity activity);

	/**
	 * 插入图片表
	 * 
	 * @param activityDAO
	 */
	@Insert("insert into articleimg (id,urlKey,url,articleId) values(REPLACE(unix_timestamp(current_timestamp(3)),'.',''),#{urlKey},#{url},#{articleId})")
	public void inertImg(ImgDTO imgDTO);

	/**
	 * 查询动态表
	 * 
	 * @param activityDAO
	 * @return
	 */
	@Select(" select id, content, DATE_FORMAT(createTime,'%Y-%m-%d %H:%i') createTime, author username from activity where 1=1 order by createTime desc ")
	public List<Activity> queryActivity(Activity activity);

	/**
	 * 查询图片
	 * 
	 * @param img
	 * @return
	 */
	@Select(" select id,urlKey, url,articleId from articleimg where 1=1 " + " and articleId = #{articleId}  ")
	public List<ImgDTO> queryImgs(ImgDTO img);

	/**
	 * 删除文章
	 * 
	 * @param id
	 */
	@Delete("delete from activity where id = #{deleteId}")
	public void deleteArcile(@Param(value = "deleteId") String ids);

	/**
	 * 查询图库
	 * 
	 * @param myPhoto
	 * @return 
	 */
	@Select("select * from myphoto where folderid = #{folderid} and owner = #{owner} ")
	public List<MyPhoto> queryMyPhoto(MyPhoto myPhoto);
	/**
	 * 查询图库folder
	 * 
	 * @param myPhoto
	 * @return 
	 */
	@Select("select distinct folder,folderid,owner from myphoto where owner = #{owner} ")
	public List<MyPhotoVO> queryMyPhotoFolder(MyPhotoVO myPhoto);

}
