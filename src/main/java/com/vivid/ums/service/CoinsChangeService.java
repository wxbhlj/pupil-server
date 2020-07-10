package com.vivid.ums.service;

import org.springframework.data.domain.Page;
import com.vivid.ums.db.dao.CoinsChangeDO;

/**
 * 
 *
 * @author xbwang
 * @email 
 * @date 2020-02-21 11:43:55
 */
 
public interface CoinsChangeService {

	Page<CoinsChangeDO> searchCoinsChange(int page, int size, Integer userId) ;
	
	void deleteCoinsChange(Integer id);
	
	Integer saveCoinsChange(CoinsChangeDO dao);
	
	String exchange(CoinsChangeDO dao);

	CoinsChangeDO findOne(Integer id);
	
	public String award(CoinsChangeDO dao) ;
}