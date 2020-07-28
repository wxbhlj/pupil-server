package com.vivid.ums.rest;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.vivid.common.api.base.BaseQueryReq;
import com.vivid.common.api.base.PageInfo;
import com.vivid.common.api.base.Result;
import com.vivid.common.exception.ServiceException;
import com.vivid.common.utils.QiniuUtils;
import com.vivid.ums.db.dao.TaskAttachmentDO;
import com.vivid.ums.db.dao.TaskDO;
import com.vivid.ums.rest.dto.TaskAttachmentDTO;
import com.vivid.ums.rest.dto.TaskCheckReq;
import com.vivid.ums.rest.dto.TaskDTO;
import com.vivid.ums.rest.dto.TaskDetailResp;
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
public class TaskController {

	@Autowired
	private TaskService taskService;

	@Autowired
	private TaskAttachmentService taskAttachmentService;

	@Autowired
	private HttpServletRequest request;

	@RequestMapping(value = "/task/search", method = RequestMethod.POST)
	public Result<PageInfo<TaskDTO>> searchTask(@RequestBody BaseQueryReq req) {
		Result<PageInfo<TaskDTO>> resp = new Result<PageInfo<TaskDTO>>();
		PageInfo<TaskDTO> data = new PageInfo<TaskDTO>();
		resp.setData(data);
		System.err.println(req);
		Page<TaskDO> entities = taskService.searchTask(req.getPage(), req.getPageSize(), req.getKeyword());
		if (entities != null && entities.getSize() > 0) {
			List<TaskDTO> list = new ArrayList<TaskDTO>();
			for (TaskDO dao : entities.getContent()) {
				TaskDTO dto = TaskDO.toDTO(dao);
				list.add(dto);
			}
			resp.getData().setTotalNumbers((int) entities.getTotalElements());
			resp.getData().setTotalPages(entities.getTotalPages());
			resp.getData().setList(list);

		}
		resp.getData().setPage(req.getPage());
		resp.getData().setPageSize(req.getPageSize());
		return resp;
	}

	@RequestMapping(value = "/task", method = RequestMethod.POST)
	public Result<TaskDTO> createTask(TaskDTO req, @RequestParam(name = "files") MultipartFile[] files) {
		TaskDO dao = TaskDO.fromDTO(req);
		Date now = new Date();
		dao.setCreated(now);
		dao.setUpdated(now);

		if (req.getId() != null && req.getId() > 0) {
			dao = taskService.findOne(req.getId());
			dao.setSpendTime(req.getSpendTime());
			dao.setOutTime(req.getOutTime());
			dao.setStatus(req.getStatus());
		}

		if ("UPLOAD".equals(req.getStatus())) {
			dao.setNeedCheck(1);
		}

		taskService.createTask(dao, files);

		return Result.ok("OK");

	}
	
	@RequestMapping(value = "/task", method = RequestMethod.PUT)
	public Result<TaskDTO> editTask(@RequestBody TaskDTO req) {
		TaskDO dao = taskService.findOne(req.getId());
		Date now = new Date();
		dao.setUpdated(now);
		dao.setTitle(req.getTitle());
		dao.setClassification(req.getClassification());
		dao.setCourse(req.getCourse());
		dao.setScore(req.getScore());
		dao.setSpendTime(req.getSpendTime());
		taskService.saveTask(dao);

		return Result.ok("OK");

	}

	@RequestMapping(value = "/taskAttachment", method = RequestMethod.POST)
	public Result<TaskAttachmentDTO> createTaskAttachment(@RequestBody TaskAttachmentDTO req) {
		TaskAttachmentDO dao = TaskAttachmentDO.fromDTO(req);
		dao.setCreated(new Date());

		taskAttachmentService.saveTaskAttachment(dao);
		return Result.ok("OK");

	}
	
	

	// @RequiresRoles("task")
	@RequestMapping(value = "/task/checked", method = RequestMethod.PUT)
	public Result<TaskDTO> checked(TaskCheckReq req, @RequestParam(name = "files") MultipartFile[] files) {
		System.out.println(req);
		TaskDO dao = taskService.findOne(req.getId());

		dao.setTitle(req.getTitle());
		dao.setScore(dao.getStatus().equals("UPLOAD")?req.getScore():dao.getScore());
		dao.setComments(req.getComments());
		dao.setStatus(dao.getStatus().equals("UPLOAD") ? "CHECKED" : dao.getStatus());
		dao.setNeedCheck(0);
		if (Math.abs(req.getSpendTime() - dao.getSpendTime()) > 60) {
			dao.setSpendTime(req.getSpendTime());
		}
		dao.setUpdated(new Date());
		taskService.checkTask(dao, files);
		return Result.ok("OK");
	}

	// @RequiresRoles("task")
	@RequestMapping(value = "/task/return", method = RequestMethod.PUT)
	public Result<TaskDTO> returnToCorrect(TaskCheckReq req, @RequestParam(name = "files") MultipartFile[] files) {
		System.out.println(req);
		TaskDO dao = taskService.findOne(req.getId());

		dao.setTitle(req.getTitle());
		dao.setScore(req.getScore());
		dao.setComments(req.getComments());
		dao.setStatus("RETURN");
		dao.setNeedCheck(0);
		if (Math.abs(req.getSpendTime() - dao.getSpendTime()) > 60) {
			dao.setSpendTime(req.getSpendTime());
		}
		dao.setUpdated(new Date());
		taskService.checkTask(dao, files);
		return Result.ok("OK");
	}

	@RequestMapping(value = "/task/reviewed/{taskId}", method = RequestMethod.PUT)
	public Result<TaskDTO> checked(@PathVariable Integer taskId, @RequestParam(name = "files") MultipartFile[] files) {
		TaskDO dao = taskService.findOne(taskId);
		Date now = new Date();
		if(dao.getStatus().equals("RETURN")) {
			dao.setStatus("UPLOAD");
		} else if(dao.getStatus().equals("CHECKED")) {
			dao.setStatus("REVIEWED");
		} else {
			dao.setStatus("REVIEWED2");
		}
		dao.setUpdated(now);
		dao.setReviewTime(now);
		dao.setNeedCheck(1);
		taskService.reviewTask(dao, files);
		return Result.ok("OK");
	}

	@RequestMapping(value = "/task/{taskId}", method = RequestMethod.DELETE)
	public Result<TaskDTO> delete(@PathVariable Integer taskId) {

		taskService.deleteTask(taskId);
		List<TaskAttachmentDO> list = taskAttachmentService.findByTaskId(taskId);
		if (list != null && list.size() > 0) {
			for (TaskAttachmentDO att : list) {
				String key = att.getUrl().replace("http://img.shellsports.cn/", "");
				QiniuUtils.deleteFile(key);
			}
		}

		return Result.ok("OK");
	}

	@RequestMapping(value = "/task/deleteAttachment/{attachmentId}", method = RequestMethod.DELETE)
	public Result<TaskDTO> deleteAttachment(@PathVariable Integer attachmentId) {
		System.out.println("try to delete " + attachmentId);
		TaskAttachmentDO one = taskAttachmentService.findOne(attachmentId);

		if (one != null && one.getId() > 0) {
			String key = one.getUrl().replace("http://img.shellsports.cn/", "");
			QiniuUtils.deleteFile(key);
			taskAttachmentService.deleteTaskAttachment(attachmentId);
		}

		return Result.ok("OK");
	}

	@RequestMapping(value = "/task/attachment/{attachmentId}", method = RequestMethod.PUT)
	public Result<TaskDTO> updateAttachment(@PathVariable Integer attachmentId,
			@RequestParam(name = "file") MultipartFile file) {

		if (file == null) {
			return Result.error("File is null");
		}
		int userId = Integer.valueOf(request.getHeader("userId"));

		TaskAttachmentDO one = taskAttachmentService.findOne(attachmentId);
		if (one == null) {
			return Result.error("Attachment is is invalid");
		}
		taskAttachmentService.updateAttachment(userId, one, file);

		return Result.ok("OK");

	}

	@RequestMapping(value = "/task/{taskId}", method = RequestMethod.GET)
	public Result<TaskDetailResp> findOne(@PathVariable Integer taskId) {

		TaskDO one = taskService.findOne(taskId);
		if (one != null) {
			TaskDetailResp resp = new TaskDetailResp();

			List<TaskAttachmentDTO> list = new ArrayList<TaskAttachmentDTO>();
			List<TaskAttachmentDO> entities = taskAttachmentService.findByTaskId(taskId);
			if (entities != null && entities.size() > 0) {
				for (TaskAttachmentDO dao : entities) {
					list.add(TaskAttachmentDO.toDTO(dao));
				}
			}
			resp.setTask(TaskDO.toDTO(one));
			resp.setAttachments(list);

			return Result.ok("OK", resp);
		} else {
			return Result.error("bad id " + taskId);
		}
	}

	@RequestMapping(value = "/task/list/unchecked", method = RequestMethod.GET)
	public Result<List<TaskDTO>> unchecked(int userId) {
		List<TaskDTO> list = new ArrayList<TaskDTO>();
		List<TaskDO> entities = taskService.todayTask(userId);
		if (entities != null && entities.size() > 0) {
			for (TaskDO dao : entities) {
				TaskDTO dto = TaskDO.toDTO(dao);
				list.add(dto);
			}
		}
		return Result.ok(list);
	}

	@RequestMapping(value = "/task/list", method = RequestMethod.GET)
	public Result<List<TaskDTO>> listTasks(int userId, String status) {
		List<TaskDTO> list = new ArrayList<TaskDTO>();
		System.out.println("userId = " + userId + ", status = " + status);
		Page<TaskDO> entities = taskService.listTask(1, 100, userId, status);
		if (entities != null && entities.getSize() > 0) {

			for (TaskDO dao : entities.getContent()) {
				TaskDTO dto = TaskDO.toDTO(dao);
				list.add(dto);
			}
		}
		return Result.ok(list);
	}
	
	@RequestMapping(value = "/task/listAll", method = RequestMethod.GET)
	public Result<PageInfo<TaskDTO>> listAll(int pageNo, int pageSize, int userId, String course) {
		Result<PageInfo<TaskDTO>> resp = new Result<PageInfo<TaskDTO>>();
		PageInfo<TaskDTO> data = new PageInfo<TaskDTO>();
		resp.setData(data);
	
		Page<TaskDO> entities = taskService.listCourseTask(pageNo, pageSize, userId, course);
		if (entities != null && entities.getSize() > 0) {
			List<TaskDTO> list = new ArrayList<TaskDTO>();
			for (TaskDO dao : entities.getContent()) {
				TaskDTO dto = TaskDO.toDTO(dao);
				list.add(dto);
			}
			resp.getData().setTotalNumbers((int) entities.getTotalElements());
			resp.getData().setTotalPages(entities.getTotalPages());
			resp.getData().setList(list);

		}
		resp.getData().setPage(pageNo);
		resp.getData().setPageSize(pageSize);
		return resp;
	}

	@RequestMapping(value = "/task/needCheck", method = RequestMethod.GET)
	public Result<List<TaskDTO>> needCheck(int userId) {
		List<TaskDTO> list = new ArrayList<TaskDTO>();

		List<TaskDO> entities = taskService.listNeedCheckTask(userId);
		if (entities != null && entities.size() > 0) {

			for (TaskDO dao : entities) {
				TaskDTO dto = TaskDO.toDTO(dao);
				list.add(dto);
			}
		}
		return Result.ok(list);
	}



	@RequestMapping(value = "/task/todoList", method = RequestMethod.GET)
	public Result<List<TaskDTO>> todoList(int userId) {
		List<TaskDTO> list = new ArrayList<TaskDTO>();
		System.out.println("userId = " + userId);

		Page<TaskDO> entities = taskService.listTodoTask(userId);

		if (entities != null && entities.getSize() > 0) {

			for (TaskDO dao : entities.getContent()) {
				TaskDTO dto = TaskDO.toDTO(dao);
				list.add(dto);
			}
		}

		return Result.ok(list);
	}

	@RequestMapping(value = "/task/lineChart", method = RequestMethod.GET)
	public Result<JSONObject> lineChart(int userId, String status) {
		JSONArray list = new JSONArray();
		JSONObject resp = new JSONObject();

		Map<String, String> courses = new HashMap<String, String>();
		courses.put("语文", "yuwen");
		courses.put("数学", "shuxue");
		courses.put("英语", "yingyu");

		for (String key : courses.keySet()) {
			resp.put(courses.get(key), 0);
			resp.put(courses.get(key) + "_count", 0);
		}

		Map<String, JSONObject> maps = new HashMap<String, JSONObject>();
		System.out.println("userId = " + userId + ", status = " + status);
		Page<TaskDO> entities = taskService.listCheckedTask(userId);
		if (entities != null && entities.getSize() > 0) {

			for (TaskDO dao : entities.getContent()) {
				String key = DateUtils.formatDate(dao.getCreated(), "MM-dd");
				JSONObject jb = maps.get(key);
				if (jb == null) {
					jb = new JSONObject();
					jb.put("key", key);
					jb.put("count", 1);
					jb.put("score", dao.getScore());
					jb.put("spendTime", dao.getSpendTime());
					list.add(jb);
					maps.put(key, jb);
				} else {
					jb.put("count", jb.getInteger("count") + 1);
					jb.put("score", jb.getInteger("score") + dao.getScore());
					jb.put("spendTime", jb.getInteger("spendTime") + dao.getSpendTime());
				}
				for (String key1 : courses.keySet()) {
					if (dao.getCourse().equals(key1)) {
						String val = courses.get(key1);
						resp.put(val, dao.getScore() + resp.getIntValue(val));
						resp.put(val + "_count", 1 + resp.getIntValue(val + "_count"));
						break;
					}
				}

			}
			resp.put("list", list);
		}

		return Result.ok(resp);
	}

}
