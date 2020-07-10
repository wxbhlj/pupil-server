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
import com.vivid.ums.rest.dto.CoinsChangeDTO;

/**
 * 
 * 
 * @author xbwang
 * @email 
 * @date 2020-02-21 11:41:42
 */
@Entity  
@Table(name="pupil_coins_change")
public class CoinsChangeDO implements Serializable {
	private static final long serialVersionUID = 1L;

	
	// 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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
    public static CoinsChangeDTO toDTO(CoinsChangeDO obj){
		CoinsChangeDTO o = DozerUtil.convert(obj, CoinsChangeDTO.class);
		return o;
	}
	
	public static CoinsChangeDO fromDTO(CoinsChangeDTO dto) {
		CoinsChangeDO o = DozerUtil.convert(dto, CoinsChangeDO.class);
		return o;
	}
	public static List<CoinsChangeDTO> toDTOs(List<CoinsChangeDO> objs){
		List<CoinsChangeDTO> list = new ArrayList<CoinsChangeDTO>();
		if(objs !=  null) {
			for(CoinsChangeDO obj:objs) {
				list.add(CoinsChangeDO.toDTO(obj));
			}
		}
		return list;
	}
}