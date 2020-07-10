package com.vivid.ums.db;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.vivid.ums.db.dao.UserDO;

public interface UserRepository  extends JpaSpecificationExecutor<UserDO>,JpaRepository<UserDO, Integer>{

	UserDO findByLoginName(String loginName);
	
	UserDO findByToken(String token);
	
}
