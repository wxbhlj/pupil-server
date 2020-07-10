package com.vivid.ums.db.dao;


import java.io.Serializable;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.vivid.common.utils.DozerUtil;
import com.vivid.ums.rest.dto.TaskAttachmentDTO;




/**
 * 
 * 
 * @author xbwang
 * @email 
 * @date 2020-06-06 11:20:08
 */
@Entity  
@Table(name="pupil_task_attachment")
public class TaskAttachmentDO implements Serializable {
	private static final long serialVersionUID = 1L;

	
	// 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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
    public static TaskAttachmentDTO toDTO(TaskAttachmentDO obj){
		TaskAttachmentDTO o = DozerUtil.convert(obj, TaskAttachmentDTO.class);
		return o;
	}
	
	public static TaskAttachmentDO fromDTO(TaskAttachmentDTO dto) {
		TaskAttachmentDO o = DozerUtil.convert(dto, TaskAttachmentDO.class);
		return o;
	}
	public static List<TaskAttachmentDTO> toDTOs(List<TaskAttachmentDO> objs){
		List<TaskAttachmentDTO> list = new ArrayList<TaskAttachmentDTO>();
		if(objs !=  null) {
			for(TaskAttachmentDO obj:objs) {
				list.add(TaskAttachmentDO.toDTO(obj));
			}
		}
		return list;
	}
}