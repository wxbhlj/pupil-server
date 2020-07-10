package com.vivid.ums.service;

import java.util.List;

import org.springframework.data.domain.Page;
import com.vivid.ums.db.dao.UserDO;

/**
 * 
 *
 * @author xbwang
 * @email 
 * @date 2020-01-28 12:36:29
 */
 
public interface UserService {

	Page<UserDO> searchUser(int page, int size, String keyword) ;
	
	void deleteUser(Integer id);
	
	Integer saveUser(UserDO dao);

	UserDO findOne(Integer id);
	
	UserDO findByLoginName(String loginName);
	
	UserDO findByToken(String token);
	
	UserDO register(String mobile);

	
}