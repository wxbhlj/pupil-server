package com.vivid.forum.service;

import org.springframework.data.domain.Page;
import com.vivid.forum.db.dao.ForumReplyDO;

/**
 * 
 *
 * @author xbwang
 * @email 
 * @date 2020-03-12 15:59:14
 */
 
public interface ForumReplyService {

	Page<ForumReplyDO> searchForumReply(int page, int size, int forumId) ;
	
	void deleteForumReply(Integer id);
	
	Integer saveForumReply(ForumReplyDO dao);

}