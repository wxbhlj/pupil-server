package com.vivid.ums.rest.dto;




import java.io.Serializable;
import java.util.Date;


/**
 * 
 * 
 * @author xbwang
 * @email 
 * @date 2020-06-27 08:24:08
 */
public class MoonDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	 // 
	private Integer id;
	
	 // 
	private Integer userId;
	
	 // 
	private Integer me;
	
	 // 
	private Integer dad;
	
	 // 
	private Integer mum;
	
	 // 
	private Date created;
	


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

	public void setMe(Integer me) {
		this.me = me;
	}
	
	public Integer getMe() {
		return me;
	}

	public void setDad(Integer dad) {
		this.dad = dad;
	}
	
	public Integer getDad() {
		return dad;
	}

	public void setMum(Integer mum) {
		this.mum = mum;
	}
	
	public Integer getMum() {
		return mum;
	}

	public void setCreated(Date created) {
		this.created = created;
	}
	
	public Date getCreated() {
		return created;
	}
	
}
