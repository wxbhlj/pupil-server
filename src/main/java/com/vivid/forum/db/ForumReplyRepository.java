package com.vivid.forum.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.vivid.forum.db.dao.ForumReplyDO;

public interface ForumReplyRepository  extends JpaSpecificationExecutor<ForumReplyDO>,JpaRepository<ForumReplyDO, Integer>{
	
	void deleteByForumId(int forumId);
	
}