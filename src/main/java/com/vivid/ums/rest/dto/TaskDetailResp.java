package com.vivid.ums.rest.dto;

import java.util.List;

public class TaskDetailResp {
	private TaskDTO task;
	
	private List<TaskAttachmentDTO> attachments;

	public TaskDTO getTask() {
		return task;
	}

	public void setTask(TaskDTO task) {
		this.task = task;
	}

	public List<TaskAttachmentDTO> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<TaskAttachmentDTO> attachments) {
		this.attachments = attachments;
	}
	
	
}
