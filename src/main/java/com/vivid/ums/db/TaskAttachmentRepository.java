package com.vivid.ums.db;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.vivid.ums.db.dao.TaskAttachmentDO;

public interface TaskAttachmentRepository  extends JpaSpecificationExecutor<TaskAttachmentDO>,JpaRepository<TaskAttachmentDO, Integer>{
	
	List<TaskAttachmentDO> findByTaskId(int taskId);

	
}
