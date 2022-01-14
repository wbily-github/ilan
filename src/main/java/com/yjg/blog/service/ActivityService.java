package com.yjg.blog.service;

import com.yjg.blog.mybatis.dao.ActivityDAO;
import com.yjg.blog.mybatis.dao.LoginDAO;
import com.yjg.blog.pojo.Activity;
import com.yjg.blog.pojo.ImgDTO;
import com.yjg.blog.pojo.MyPhoto;
import com.yjg.blog.pojo.MyPhotoVO;
import com.yjg.blog.pojo.RespBean;
import com.yjg.blog.pojo.UserDTO;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ActivityService {
	@Value("${jwt.tokenHeader}")
	private String tokenHeader;
	@Value("${jwt.tokenHead}")
	private String tokenHead;
	@Autowired
	private ActivityDAO activityDAO;
	@Autowired
	private LoginDAO loginDAO;

	/**
	 * 文章保存
	 * 
	 * @param activity
	 * @param request
	 * @return
	 */

	public RespBean insertArcitle(Activity activity, HttpServletRequest request) {
		try {
			// 获取用户名
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			activity.setUsername(auth.getName());
			// 插入动态表
			Long id = new Date().getTime();

			activity.setId(id);
			activityDAO.inertActivity(activity);
			// 插入图片表
			List<ImgDTO> imgs = activity.getImgs();
			if (null != imgs && imgs.size() > 0) {
				for (ImgDTO img : imgs) {
					img.setArticleId(id);
					img.setUrlKey(activity.getUsername());
					log.info("@@@img" + img);
					activityDAO.inertImg(img);
				}
			}
		} catch (Exception e) {
			log.error("保存失败", e.getMessage(), e);
			return RespBean.error("保存失败");
		}
		return RespBean.success("保存成功", activity, 1);
	}

	/**
	 * 查询所有动态
	 * 
	 * @param activity
	 * @param request
	 * @return
	 */
	public RespBean queryArcitle(Activity activity, HttpServletRequest request) {
		List<Activity> activities = new ArrayList<>();
		try {
			List<Activity> acts = activityDAO.queryActivity(activity);
			for (Activity act : acts) {
				// 查询文章图片
				ImgDTO img = new ImgDTO();
				img.setArticleId(act.getId());
				List<ImgDTO> imgs = activityDAO.queryImgs(img);
				act.setImgs(imgs);
				// 查询作者信息
				UserDTO user = new UserDTO();
				user.setUsername(act.getUsername());
				List<UserDTO> uss = loginDAO.queryLoginInfo(user);
				UserDTO u = (null == uss || uss.size() == 0) ? null : uss.get(0);
				u.setPassword(null);
				act.setAuthor(u);
				activities.add(act);
			}
		} catch (Exception e) {
			log.error("查询失败" + e.getMessage(), e);
			return RespBean.error("查询失败");
		}
		return RespBean.success("查询成功", activities, activities.size());
	}

	/**
	 * 删除动态
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	public RespBean deleteArcitle(String ids, HttpServletRequest request) {
		try {
			activityDAO.deleteArcile(ids);
		} catch (Exception e) {
			log.error("删除失败", e.getMessage(), e);
			return RespBean.error("删除失败");
		}
		return RespBean.success("删除成功", ids, 1);
	}

	/**
	 * 查询图库
	 * 
	 * @param request
	 * @return
	 */
	public RespBean queryMyPhoto(HttpServletRequest request) {
		// TODO Auto-generated method stub
		List<MyPhotoVO> pbotos = new ArrayList<MyPhotoVO>();
		try {
			// 获取用户名
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			MyPhotoVO myPhotoVo = new MyPhotoVO();
			myPhotoVo.setOwner(auth.getName());
			pbotos = activityDAO.queryMyPhotoFolder(myPhotoVo);
			for (MyPhotoVO pho : pbotos) {
				MyPhoto myPhoto = new MyPhoto();
				myPhoto.setFolderid(pho.getFolderid());
				myPhoto.setOwner(pho.getOwner());
				List<MyPhoto> oto = activityDAO.queryMyPhoto(myPhoto);
				pho.setImgs(oto);
			}
			log.info("pbotosWWWWWWWWWWWWWWWWW", pbotos);
		} catch (Exception e) {
			log.error("查询失败", e.getMessage(), e);
			return RespBean.error("查询失败");
		}
		return RespBean.success("查询成功", pbotos, pbotos.size());
	}
}
