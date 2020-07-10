
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
import com.vivid.ums.rest.dto.MoonDTO;

/**
 * 
 * 
 * @author xbwang
 * @email 
 * @date 2020-06-27 08:17:53
 */
@Entity  
@Table(name="pupil_moon")
public class MoonDO implements Serializable {
	private static final long serialVersionUID = 1L;

	
	// 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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
    public static MoonDTO toDTO(MoonDO obj){
		MoonDTO o = DozerUtil.convert(obj, MoonDTO.class);
		return o;
	}
	
	public static MoonDO fromDTO(MoonDTO dto) {
		MoonDO o = DozerUtil.convert(dto, MoonDO.class);
		return o;
	}
	public static List<MoonDTO> toDTOs(List<MoonDO> objs){
		List<MoonDTO> list = new ArrayList<MoonDTO>();
		if(objs !=  null) {
			for(MoonDO obj:objs) {
				list.add(MoonDO.toDTO(obj));
			}
		}
		return list;
	}
}