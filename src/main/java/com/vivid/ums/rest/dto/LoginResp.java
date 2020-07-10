package com.vivid.ums.rest.dto;

import java.util.List;

public class LoginResp {
	private String token;
	 // 
	private Integer userId;
	
	private String nick;
	
	 // 
	private String avatar;
	
	//
	private int coinsTotal;
	
	private int coinsUsed;
	
	private int avgScore;
	
	private long loginTime;
	

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

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

	public int getAvgScore() {
		return avgScore;
	}

	public void setAvgScore(int avgScore) {
		this.avgScore = avgScore;
	}

	public long getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(long loginTime) {
		this.loginTime = loginTime;
	}
	
	
	
}
