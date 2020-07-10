package com.vivid.ums.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.vivid.ums.db.dao.MoonDO;

public interface MoonRepository  extends JpaSpecificationExecutor<MoonDO>,JpaRepository<MoonDO, Integer>{

	
}
	