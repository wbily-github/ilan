package com.yjg.blog.service;

import com.yjg.blog.mybatis.dao.ActivityDAO;
import com.yjg.blog.pojo.Activity;
import com.yjg.blog.pojo.RespBean;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ActivityService {
	@Value("${jwt.tokenHeader}")
	private String tokenHeader;
	@Value("${jwt.tokenHead}")
	private String tokenHead;
	@Autowired
	private ActivityDAO activityDAO;

	/**
	 * 文章保存
	 * 
	 * @param activity
	 * @param request
	 * @return
	 */

	public RespBean insertArcitle(Activity activity, HttpServletRequest request) {
		// 获取用户名
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		activity.setAuthor(auth.getName());
		// 插入动态表
		activity.setId(new Date().getTime());
		activityDAO.inertActivity(activity);
		// 插入图片表

		return RespBean.success("保存成功", 1);
	}
}
