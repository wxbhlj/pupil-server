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
import com.vivid.ums.rest.dto.TaskDTO;

/**
 * 
 * 
 * @author xbwang
 * @email 
 * @date 2020-05-27 16:11:56
 */
@Entity  
@Table(name="pupil_task")
public class TaskDO implements Serializable {
	private static final long serialVersionUID = 1L;

	
	// 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	// 
	private Integer userId;
	
	// 
	private String title;
	
	// 
	private String course;
	
	// 
	private String classification;
	
	// 
	private Integer spendTime;
	
	private Integer outTime;
	
	
	
	// 
	private Integer score;
	
	// 
	private String comments;
	
	// 
	private String imageUrls;
	
	// 
	private String status;
	
	// 
	private Date reviewTime;
	
	// 
	private Date created;
	
	// 
	private Date updated;
	
	private Integer needCheck;


	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getId() {
		return id;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	public Integer getUserId() {
		return userId;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getTitle() {
		return title;
	}

	public void setCourse(String course) {
		this.course = course;
	}
	
	public String getCourse() {
		return course;
	}

	public void setClassification(String classification) {
		this.classification = classification;
	}
	
	public String getClassification() {
		return classification;
	}

	public void setSpendTime(Integer spendTime) {
		this.spendTime = spendTime;
	}
	
	public Integer getSpendTime() {
		return spendTime;
	}

	public void setScore(Integer score) {
		this.score = score;
	}
	
	public Integer getScore() {
		return score;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
	
	public String getComments() {
		return comments;
	}

	public void setImageUrls(String imageUrls) {
		this.imageUrls = imageUrls;
	}
	
	public String getImageUrls() {
		return imageUrls;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getStatus() {
		return status;
	}

	public void setReviewTime(Date reviewTime) {
		this.reviewTime = reviewTime;
	}
	
	public Date getReviewTime() {
		return reviewTime;
	}

	public void setCreated(Date created) {
		this.created = created;
	}
	
	public Date getCreated() {
		return created;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}
	
	public Date getUpdated() {
		return updated;
	}
	
	
    public Integer getOutTime() {
		return outTime;
	}

	public void setOutTime(Integer outTime) {
		this.outTime = outTime;
	}

	public Integer getNeedCheck() {
		return needCheck;
	}

	public void setNeedCheck(Integer needCheck) {
		this.needCheck = needCheck;
	}

	public static TaskDTO toDTO(TaskDO obj){
		TaskDTO o = DozerUtil.convert(obj, TaskDTO.class);
		return o;
	}
	
	public static TaskDO fromDTO(TaskDTO dto) {
		TaskDO o = DozerUtil.convert(dto, TaskDO.class);
		return o;
	}
	public static List<TaskDTO> toDTOs(List<TaskDO> objs){
		List<TaskDTO> list = new ArrayList<TaskDTO>();
		if(objs !=  null) {
			for(TaskDO obj:objs) {
				list.add(TaskDO.toDTO(obj));
			}
		}
		return list;
	}
}