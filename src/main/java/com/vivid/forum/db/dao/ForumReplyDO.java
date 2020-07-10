package com.vivid.forum.db.dao;

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
import com.vivid.forum.rest.dto.ForumReplyDTO;


/**
 * 
 * 
 * @author xbwang
 * @email 
 * @date 2020-03-12 15:57:40
 */
@Entity  
@Table(name="forum_forum_reply")
public class ForumReplyDO implements Serializable {
	private static final long serialVersionUID = 1L;

	
	// 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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

	public static ForumReplyDTO toDTO(ForumReplyDO obj){
		ForumReplyDTO o = DozerUtil.convert(obj, ForumReplyDTO.class);
		return o;
	}
	
	public static ForumReplyDO fromDTO(ForumReplyDTO dto) {
		ForumReplyDO o = DozerUtil.convert(dto, ForumReplyDO.class);
		return o;
	}
	public static List<ForumReplyDTO> toDTOs(List<ForumReplyDO> objs){
		List<ForumReplyDTO> list = new ArrayList<ForumReplyDTO>();
		if(objs !=  null) {
			for(ForumReplyDO obj:objs) {
				list.add(ForumReplyDO.toDTO(obj));
			}
		}
		return list;
	}
}