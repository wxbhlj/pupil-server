package com.vivid.ums.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.vivid.ums.db.dao.CoinsChangeDO;

public interface CoinsChangeRepository  extends JpaSpecificationExecutor<CoinsChangeDO>,JpaRepository<CoinsChangeDO, Integer>{

	
}
