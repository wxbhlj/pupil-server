package com.vivid.ums.rest.dto;



import java.io.Serializable;
import java.util.Date;


/**
 * 
 * 
 * @author xbwang
 * @email 
 * @date 2020-02-21 11:42:32
 */
public class CoinsChangeDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	 // 
	private Integer id;
	
	
	 // 
	private Integer userId;
	
	 // 
	private String reason;
	
	 // 
	private Integer coins;
	
	 // 
	private Integer changeType;
	
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

	public void setReason(String reason) {
		this.reason = reason;
	}
	
	public String getReason() {
		return reason;
	}

	public void setCoins(Integer coins) {
		this.coins = coins;
	}
	
	public Integer getCoins() {
		return coins;
	}

	public void setChangeType(Integer changeType) {
		this.changeType = changeType;
	}
	
	public Integer getChangeType() {
		return changeType;
	}

	public void setCreated(Date created) {
		this.created = created;
	}
	
	public Date getCreated() {
		return created;
	}
	
}
