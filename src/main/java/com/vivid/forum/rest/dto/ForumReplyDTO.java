package com.vivid.forum.rest.dto;




import java.io.Serializable;
import java.util.Date;


/**
 * 
 * 
 * @author xbwang
 * @email 
 * @date 2020-03-12 15:58:23
 */
public class ForumReplyDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	 // 
	private Integer id;
	
	 // 
	private Integer forumId;
	
	 // 
	private String content;
	
	 // 
	private Integer userId;
	
	 // 
	private String userNick;
	
	private String userAvatar;
	
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

	public void setForumId(Integer forumId) {
		this.forumId = forumId;
	}
	
	public Integer getForumId() {
		return forumId;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public String getContent() {
		return content;
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
