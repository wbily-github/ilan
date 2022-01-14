package com.yjg.blog.controller;

import com.yjg.blog.pojo.Activity;
import com.yjg.blog.pojo.RespBean;
import com.yjg.blog.service.ActivityService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Validated
@RestController
public class ActivityController {
	@Autowired
	ActivityService activityService;

	/**
	 * 保存动态
	 *
	 * @param request
	 * @throws Exception
	 */

	@RequestMapping(value = "/blog/insertArticle", method = RequestMethod.POST)
	public RespBean insertArcitleInfo(@RequestBody Activity activity, HttpServletRequest request) throws Exception {
		log.info("保存动态入参~" + activity);
		return activityService.insertArcitle(activity, request);
	}

	/**
	 * 查询动态
	 *
	 * @param request
	 * @throws Exception
	 */

	@RequestMapping(value = "/blog/queryArticle", method = RequestMethod.POST)
	public RespBean queryArcitleInfo(@RequestBody Activity activity, HttpServletRequest request) throws Exception {
		log.info("查询动态入参~" + activity);
		return activityService.queryArcitle(activity, request);
	}

	/**
	 * 删除动态
	 *
	 * @param request
	 * @throws Exception
	 */

	@RequestMapping(value = "/blog/deleteArticle", method = RequestMethod.POST)
	public RespBean deleteArcitleInfo(@RequestBody String id, HttpServletRequest request) throws Exception {
		log.info("删除动态入参~" + id.substring(0, id.length() - 1));
		return activityService.deleteArcitle(id.substring(0, id.length() - 1), request);
	}
	/**
	 * 查询相册
	 *
	 * @param request
	 * @throws Exception
	 */

	@RequestMapping(value = "/blog/queryMyPhoto", method = RequestMethod.POST)
	public RespBean queryMyPhotoInfo( HttpServletRequest request) throws Exception {
		log.info("查询图库入参~");
		return activityService.queryMyPhoto(request);
	}

}
