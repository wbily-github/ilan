package com.yjg.blog.mybatis.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.yjg.blog.pojo.Activity;
import com.yjg.blog.pojo.Fabulous;
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
	@Select(" select id, content, DATE_FORMAT(createTime,'%Y-%m-%d %H:%i') createTime, author username from activity where 1=1"
			+ " order by createTime desc ")
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
	 * 查询点赞
	 * 
	 * @param img
	 * @return
	 */
	@Select("<script> select id,username,targetid as targetId from dianzan where 1=1  "
			+ "<if test='zanUser!= null and !\"\".equals(zanUser)'> and username = #{zanUser} </if>"
			+ "and targetid = #{id} </script>")
	public List<Fabulous> queryFabulous(Activity activity);

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
	 * 查询动态图片
	 * 
	 * @param myPhoto
	 * @return
	 */
	@Select("select '99999999' folderid,'我的空间'  folder,#{owner} owner,b.url imgurl from activity a inner join articleimg b on a.id = b.articleId where "
			+ "a.author=#{owner} order by a.id desc ")
	public List<MyPhoto> queryMyActivityImgs(MyPhotoVO myPhoto);

	/**
	 * 查询图库folder
	 * 
	 * @param myPhoto
	 * @return
	 */
	@Select("select distinct a.folder,a.folderid,a.owner from folder a where a.owner = #{owner} ")
	public List<MyPhotoVO> queryMyPhotoFolder(MyPhotoVO myPhoto);

	/**
	 * 保存图库folder
	 * 
	 * @param photo
	 */
	@Insert("insert into folder (id,folder,folderId,foldertype,owner) VALUES("
			+ "REPLACE(unix_timestamp(current_timestamp(3)),'.',''),#{folder},REPLACE(unix_timestamp(current_timestamp(3)),'.',''),#{folderType},#{owner}) ")
	public void insertFolder(MyPhotoVO photo);

	/**
	 * 保存点赞
	 * 
	 * @param fabulous2
	 */
	@Insert("insert into dianzan (id,username,targetid) values(REPLACE(unix_timestamp(current_timestamp(3)),'.',''),#{username},#{targetId})")
	public void insertZan(Fabulous fabulous);

	/**
	 * 删除赞
	 * 
	 * @param fabulous2
	 */
	@Delete("delete from dianzan where targetid = #{targetId} and username = #{username}")
	public void deleteZan(Fabulous fabulous2);

}
