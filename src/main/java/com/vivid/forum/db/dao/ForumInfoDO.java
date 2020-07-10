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
import com.vivid.forum.rest.dto.ForumInfoDTO;


/**
 * 
 * 
 * @author xbwang
 * @email 
 * @date 2020-03-12 15:52:14
 */
@Entity  
@Table(name="forum_forum_info")
public class ForumInfoDO implements Serializable {
	private static final long serialVersionUID = 1L;

	
	// 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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
	
	

	public String getUserAvatar() {
		return userAvatar;
	}

	public void setUserAvatar(String userAvatar) {
		this.userAvatar = userAvatar;
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
    public static ForumInfoDTO toDTO(ForumInfoDO obj){
		ForumInfoDTO o = DozerUtil.convert(obj, ForumInfoDTO.class);
		return o;
	}
	
	public static ForumInfoDO fromDTO(ForumInfoDTO dto) {
		ForumInfoDO o = DozerUtil.convert(dto, ForumInfoDO.class);
		return o;
	}
	public static List<ForumInfoDTO> toDTOs(List<ForumInfoDO> objs){
		List<ForumInfoDTO> list = new ArrayList<ForumInfoDTO>();
		if(objs !=  null) {
			for(ForumInfoDO obj:objs) {
				list.add(ForumInfoDO.toDTO(obj));
			}
		}
		return list;
	}
}