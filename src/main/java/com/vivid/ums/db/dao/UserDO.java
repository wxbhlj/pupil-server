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
import com.vivid.ums.rest.dto.UserDTO;


/**
 * 
 * 
 * @author xbwang
 * @email 
 * @date 2020-01-28 12:32:43
 */
@Entity  
@Table(name="pupil_user")
public class UserDO implements Serializable {
	private static final long serialVersionUID = 1L;

	
	// 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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

	public static UserDTO toDTO(UserDO obj){
		UserDTO o = DozerUtil.convert(obj, UserDTO.class);
		return o;
	}
	
	public static UserDO fromDTO(UserDTO dto) {
		UserDO o = DozerUtil.convert(dto, UserDO.class);
		return o;
	}
	public static List<UserDTO> toDTOs(List<UserDO> objs){
		List<UserDTO> list = new ArrayList<UserDTO>();
		if(objs !=  null) {
			for(UserDO obj:objs) {
				list.add(UserDO.toDTO(obj));
			}
		}
		return list;
	}
}