package com.vivid.forum.rest;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.vivid.common.api.base.BaseQueryReq;
import com.vivid.common.api.base.PageInfo;
import com.vivid.common.api.base.Result;
import com.vivid.forum.db.dao.ForumReplyDO;
import com.vivid.forum.rest.dto.ForumReplyDTO;
import com.vivid.forum.service.ForumReplyService;


/**
 * 
 *
 * @author xbwang
 * @email 
 * @date 2020-03-12 16:11:28
 */

@RestController
@RequestMapping("/api/v1/forum")
public class ForumReplyController { 

	@Autowired
	private ForumReplyService forumReplyService;

	@RequestMapping(value = "/forumReply/search", method = RequestMethod.POST)
	public Result<PageInfo<ForumReplyDTO>> searchForumReply(@RequestBody BaseQueryReq req) {
		Result<PageInfo<ForumReplyDTO>> resp = new Result<PageInfo<ForumReplyDTO>>();
		PageInfo<ForumReplyDTO> data = new PageInfo<ForumReplyDTO>();
		resp.setData(data);
		System.err.println(req);
		Page<ForumReplyDO> entities = forumReplyService.searchForumReply(req.getPage(), req.getPageSize(), Integer.valueOf(req.getKeyword()));
		if (entities != null && entities.getSize() > 0) {
			List<ForumReplyDTO> list = new ArrayList<ForumReplyDTO>();
			for (ForumReplyDO dao : entities.getContent()) {
				ForumReplyDTO dto = ForumReplyDO.toDTO(dao);
				list.add(dto); 
			}
			resp.getData().setTotalNumbers((int) entities.getTotalElements());
			resp.getData().setTotalPages(entities.getTotalPages());
			resp.getData().setList(list);
			
		} 
		resp.getData().setPage(req.getPage());
		resp.getData().setPageSize(req.getPageSize());
		return resp;
	}

	@RequestMapping(value = "/forumReply", method = RequestMethod.POST)
	public Result<ForumReplyDTO> createForumReply(@RequestBody ForumReplyDTO req) {
		ForumReplyDO dao = ForumReplyDO.fromDTO(req);
		dao.setCreated(new Date());
		dao.setUpdated(new Date());
		forumReplyService.saveForumReply(dao);
		return Result.ok("OK");

	}


	@RequestMapping(value = "/forumReply/{forumReplyId}", method = RequestMethod.DELETE)
	public Result<ForumReplyDTO> delete(@PathVariable Integer forumReplyId) {

		forumReplyService.deleteForumReply(forumReplyId);
		return Result.ok("OK");
	}
	

}



