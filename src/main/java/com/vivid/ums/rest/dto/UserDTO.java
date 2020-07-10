package com.vivid.ums.rest.dto;



import java.io.Serializable;
import java.util.Date;


/**
 * 
 * 
 * @author xbwang
 * @email 
 * @date 2020-01-28 12:33:53
 */
public class UserDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	 // 
	private Integer id;
	
	 // 昵称
	private String nick;
	
	 // 
	private String avatar;
	
	 // 
	private Date created;
	
	 // 
	private Date updated;
	
	 // 
	private String loginName;
	
	private String password;
	

	
	private String token;
	
	private Date tokenExpired;

	
private int coinsTotal;
	
	private int coinsUsed;


	public int getCoinsTotal() {
		return coinsTotal;
	}

	public void setCoinsTotal(int coinsTotal) {
		this.coinsTotal = coinsTotal;
	}

	public int getCoinsUsed() {
		return coinsUsed;
	}

	public void setCoinsUsed(int coinsUsed) {
		this.coinsUsed = coinsUsed;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getId() {
		return id;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}
	
	public String getNick() {
		return nick;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	
	public String getAvatar() {
		return avatar;
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

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	
	public String getLoginName() {
		return loginName;
	}


	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getTokenExpired() {
		return tokenExpired;
	}

	public void setTokenExpired(Date tokenExpired) {
		this.tokenExpired = tokenExpired;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
