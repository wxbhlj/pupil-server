package com.vivid.ums.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import com.vivid.ums.db.dao.TaskAttachmentDO;
import com.vivid.ums.db.dao.TaskDO;

/**
 * 
 *
 * @author xbwang
 * @email 
 * @date 2020-06-06 11:37:47
 */
 
public interface TaskAttachmentService {

	Page<TaskAttachmentDO> searchTaskAttachment(int page, int size, String keyword) ;
	
	void deleteTaskAttachment(Integer id);
	
	Integer saveTaskAttachment(TaskAttachmentDO dao);

	TaskAttachmentDO findOne(Integer id);
	
	List<TaskAttachmentDO> findByTaskId(int taskId);
	
	Integer updateAttachment(int userId, TaskAttachmentDO dao, MultipartFile file);
}