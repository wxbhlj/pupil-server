package com.vivid.ums.rest;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.client.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.vivid.common.api.base.Result;
import com.vivid.ums.db.dao.TaskDO;
import com.vivid.ums.service.TaskAttachmentService;
import com.vivid.ums.service.TaskService;

/**
 * 
 *
 * @author xbwang
 * @email
 * @date 2020-05-27 16:19:08
 */

@RestController
@RequestMapping("/api/v1/ums")
public class TaskChartController {

	@Autowired
	private TaskService taskService;

	@Autowired
	private TaskAttachmentService taskAttachmentService;
	
	@Autowired
	private HttpServletRequest request;

	
	@RequestMapping(value = "/task/chart/home", method = RequestMethod.GET)
	public Result<JSONObject> lineChart(int userId) {
		JSONArray date = new JSONArray();
		Map<String, JSONArray> lists = new HashMap<String, JSONArray>();
		
		JSONObject resp = new JSONObject();
		
		Map<String, String> courses = new HashMap<String, String>();
		courses.put("语文", "yuwen");
		courses.put("数学", "shuxue");
		courses.put("英语", "yingyu");
		
		for(String key: courses.keySet()) {
			resp.put(courses.get(key), 0);
			resp.put(courses.get(key) + "_count", 0);
			lists.put(key, new JSONArray());
		
		}
		
		Map<String, JSONObject> maps = new HashMap<String, JSONObject>();
		Map<String, String> dateMap = new HashMap<String, String>();
		System.out.println("userId = " + userId );
		Page<TaskDO> entities = taskService.listCheckedTask(userId);
		if (entities != null && entities.getSize() > 0) {
			
			for (TaskDO dao : entities.getContent()) {
				if(courses.containsKey(dao.getCourse())) {
					System.out.println(dao.getCreated() + "___" + dao.getTitle());
					String dateStr = DateUtils.formatDate(dao.getCreated(), "MM/dd");
					String key = dateStr + dao.getCourse();
					JSONObject jb = maps.get(key);
					if(jb == null) {
						jb = new JSONObject();
						jb.put("key",dateStr);
						jb.put("count",1);
						jb.put("score", dao.getScore());
						jb.put("spendTime", dao.getSpendTime());
						lists.get(dao.getCourse()).add(jb);
						maps.put(key,jb);
					} else {
						jb.put("count",jb.getInteger("count") + 1);
						jb.put("score",jb.getInteger("score") + dao.getScore());
						jb.put("spendTime", jb.getInteger("spendTime") + dao.getSpendTime());
					}
					for(String key1: courses.keySet()) {
						if(dao.getCourse().equals(key1)) {
							String val = courses.get(key1);
							resp.put(val, dao.getScore() + resp.getIntValue(val));
							resp.put(val + "_count", 1 + resp.getIntValue(val + "_count"));
							break;
						}
					}
					
					if(!dateMap.containsKey(dateStr)) {
						dateMap.put(dateStr, "");
						date.add(dateStr);
					}
					
				}
				
			}
			
			for(String key: courses.keySet()) {
				resp.put(courses.get(key)+ "data", lists.get(key));
			}
			resp.put("date", date);
		}
		
		return Result.ok(resp);
	}

}
