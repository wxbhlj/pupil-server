package com.vivid.ums.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import com.vivid.ums.db.dao.TaskDO;

/**
 * 
 *
 * @author xbwang
 * @email 
 * @date 2020-05-27 16:18:11
 */
 
public interface TaskService {

	Page<TaskDO> searchTask(int page, int size, String keyword) ;
	
	Page<TaskDO> listTask(int page, int size, int userId, String status) ;
	
	Page<TaskDO> listCourseTask(int page, int size, int userId, String course) ;
	
	Page<TaskDO> listCheckedTask(int userId) ;
	
	Page<TaskDO> listTodoTask(int userId) ;
	
	List<TaskDO> listNeedCheckTask(int userId) ;
	
	List<TaskDO> todayTask(int userId) ;
	
	
	void deleteTask(Integer id);
	
	Integer saveTask(TaskDO dao);
	
	Integer createTask(TaskDO dao, MultipartFile[] files);
	
	Integer reviewTask(TaskDO dao, MultipartFile[] files);
	
	Integer checkTask(TaskDO dao, MultipartFile[] files);

	TaskDO findOne(Integer id);
	
	Integer getLastMonthAvgScore(int userId);
}