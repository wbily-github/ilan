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
		return activityService.insertArcitle(activity,request);
	}
	/**
	 * 查询动态
	 *
	 * @param request
	 * @throws Exception
	 */

	@RequestMapping(value = "/blog/queryArticle", method = RequestMethod.POST)
	public RespBean queryArcitleInfo(@RequestBody Activity activity, HttpServletRequest request) throws Exception {
		log.info("保存动态入参~" + activity);
		return activityService.queryArcitle(activity,request);
	}

}
