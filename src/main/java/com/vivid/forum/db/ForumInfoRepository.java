package com.vivid.forum.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.vivid.forum.db.dao.ForumInfoDO;

public interface ForumInfoRepository  extends JpaSpecificationExecutor<ForumInfoDO>,JpaRepository<ForumInfoDO, Integer>{

	
}
