package com.vivid.forum.service;

import org.springframework.data.domain.Page;
import com.vivid.forum.db.dao.ForumInfoDO;

/**
 * 
 *
 * @author xbwang
 * @email 
 * @date 2020-03-12 15:54:30
 */
 
public interface ForumInfoService {

	Page<ForumInfoDO> searchForumInfo(int page, int size, String keyword) ;
	
	void deleteForumInfo(Integer id);
	
	Integer saveForumInfo(ForumInfoDO dao);

	ForumInfoDO findOne(Integer id);
}