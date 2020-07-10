package com.vivid.forum.rest.dto;


import java.io.Serializable;
import java.util.Date;


/**
 * 
 * 
 * @author xbwang
 * @email 
 * @date 2020-03-12 15:53:22
 */
public class ForumInfoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	 // 
	private Integer id;
	
	 // 
	private String title;
	
	 // 
	private String content;
	
	 // 
	private String category;
	
	 // 
	private Integer userId;
	
	private String userAvatar;
	
	 // 
	private String userNick;
	
	 // 
	private Integer likes;
	
	 // 
	private Date created;
	
	 // 
	private Date updated;
	


	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getId() {
		return id;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getTitle() {
		return title;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public String getContent() {
		return content;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
	public String getCategory() {
		return category;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	public Integer getUserId() {
		return userId;
	}

	public void setUserNick(String userNick) {
		this.userNick = userNick;
	}
	
	public String getUserNick() {
		return userNick;
	}

	public void setLikes(Integer likes) {
		this.likes = likes;
	}
	
	public Integer getLikes() {
		return likes;
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

	public String getUserAvatar() {
		return userAvatar;
	}

	public void setUserAvatar(String userAvatar) {
		this.userAvatar = userAvatar;
	}
	
}
