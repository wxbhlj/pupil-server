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
import com.vivid.forum.db.dao.ForumInfoDO;
import com.vivid.forum.rest.dto.ForumInfoDTO;
import com.vivid.forum.service.ForumInfoService;


/**
 * 
 *
 * @author xbwang
 * @email 
 * @date 2020-03-12 15:55:20
 */

@RestController
@RequestMapping("/api/v1/forum")
public class ForumInfoController { 

	@Autowired
	private ForumInfoService forumInfoService;

	@RequestMapping(value = "/forumInfo/search", method = RequestMethod.POST)
	public Result<PageInfo<ForumInfoDTO>> searchForumInfo(@RequestBody BaseQueryReq req) {
		Result<PageInfo<ForumInfoDTO>> resp = new Result<PageInfo<ForumInfoDTO>>();
		PageInfo<ForumInfoDTO> data = new PageInfo<ForumInfoDTO>();
		resp.setData(data);
		System.err.println(req);
		Page<ForumInfoDO> entities = forumInfoService.searchForumInfo(req.getPage(), req.getPageSize(), req.getKeyword());
		if (entities != null && entities.getSize() > 0) {
			List<ForumInfoDTO> list = new ArrayList<ForumInfoDTO>();
			for (ForumInfoDO dao : entities.getContent()) {
				ForumInfoDTO dto = ForumInfoDO.toDTO(dao);
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

	@RequestMapping(value = "/forumInfo", method = RequestMethod.POST)
	public Result<ForumInfoDTO> createForumInfo(@RequestBody ForumInfoDTO req) {
		ForumInfoDO dao = ForumInfoDO.fromDTO(req);
		dao.setCreated(new Date());
		dao.setUpdated(new Date());
		forumInfoService.saveForumInfo(dao);
		return Result.ok("OK");

	}

	//@RequiresRoles("forumInfo")
	@RequestMapping(value = "/forumInfo/{forumInfoId}", method = RequestMethod.PUT)
	public Result<ForumInfoDTO> updateForumInfo(@PathVariable Integer forumInfoId, @RequestBody ForumInfoDTO req) {
		ForumInfoDO dao = forumInfoService.findOne(forumInfoId);
		dao.setTitle(req.getTitle());
		dao.setContent(req.getContent());
		dao.setCategory(req.getCategory());
		dao.setUpdated(new Date());
		forumInfoService.saveForumInfo(dao);
		return Result.ok("OK");
	}

	@RequestMapping(value = "/forumInfo/{forumInfoId}", method = RequestMethod.DELETE)
	public Result<ForumInfoDTO> delete(@PathVariable Integer forumInfoId) {

		forumInfoService.deleteForumInfo(forumInfoId);
		return Result.ok("OK");
	}
	
	@RequestMapping(value = "/forumInfo/like/{forumInfoId}", method = RequestMethod.GET)
	public Result<Boolean> likeIt(@PathVariable Integer forumInfoId) {

		ForumInfoDO dao = forumInfoService.findOne(forumInfoId);
		dao.setUpdated(new Date());
		dao.setLikes(dao.getLikes() + 1);
		forumInfoService.saveForumInfo(dao);
		return Result.ok("OK", true);
	
	}
	
	@RequestMapping(value = "/forumInfo/{forumInfoId}", method = RequestMethod.GET)
	public Result<ForumInfoDTO> findOne(@PathVariable Integer forumInfoId) {

		ForumInfoDO one = forumInfoService.findOne(forumInfoId);
		
		if(one != null) {
			return Result.ok("OK", ForumInfoDO.toDTO(one));
		} else {
			return Result.error("bad id " + forumInfoId);
		}
	}

}



