package com.vivid.ums.service;

import org.springframework.data.domain.Page;
import com.vivid.ums.db.dao.MoonDO;

/**
 * 
 *
 * @author xbwang
 * @email 
 * @date 2020-06-27 08:25:26
 */
 
public interface MoonService {

	Page<MoonDO> searchMoon(int page, int size, String keyword) ;
	
	void deleteMoon(Integer id);
	
	Integer saveMoon(MoonDO dao);

	MoonDO findOne(Integer id);
	
	MoonDO findTodayMoon(Integer userId);
}