package com.vivid.ums.rest.dto;




import java.io.Serializable;
import java.util.Date;


/**
 * 
 * 
 * @author xbwang
 * @email 
 * @date 2020-06-06 11:21:22
 */
public class TaskAttachmentDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	 // 
	private Integer id;
	
	 // 
	private Integer taskId;
	
	 // 
	private String url;
	
	 // 
	private String type;
	
	 // 
	private Date created;
	


	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getId() {
		return id;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}
	
	public Integer getTaskId() {
		return taskId;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getUrl() {
		return url;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getType() {
		return type;
	}

	public void setCreated(Date created) {
		this.created = created;
	}
	
	public Date getCreated() {
		return created;
	}
	
}
