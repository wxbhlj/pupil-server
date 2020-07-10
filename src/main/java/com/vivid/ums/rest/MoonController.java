package com.vivid.ums.rest;


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
import com.vivid.ums.db.dao.MoonDO;
import com.vivid.ums.rest.dto.MoonDTO;
import com.vivid.ums.service.MoonService;


/**
 * 
 *
 * @author xbwang
 * @email 
 * @date 2020-06-27 08:26:33
 */

@RestController
@RequestMapping("/api/v1/ums")
public class MoonController { 

	@Autowired
	private MoonService moonService;

	@RequestMapping(value = "/moon/search", method = RequestMethod.POST)
	public Result<PageInfo<MoonDTO>> searchMoon(@RequestBody BaseQueryReq req) {
		Result<PageInfo<MoonDTO>> resp = new Result<PageInfo<MoonDTO>>();
		PageInfo<MoonDTO> data = new PageInfo<MoonDTO>();
		resp.setData(data);
		System.err.println(req);
		Page<MoonDO> entities = moonService.searchMoon(req.getPage(), req.getPageSize(), req.getKeyword());
		if (entities != null && entities.getSize() > 0) {
			List<MoonDTO> list = new ArrayList<MoonDTO>();
			for (MoonDO dao : entities.getContent()) {
				MoonDTO dto = MoonDO.toDTO(dao);
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

	@RequestMapping(value = "/moon", method = RequestMethod.POST)
	public Result<MoonDTO> createMoon(@RequestBody MoonDTO req) {
		MoonDO dao = MoonDO.fromDTO(req);
		dao.setCreated(new Date());
		MoonDO moon = moonService.findTodayMoon(req.getUserId());
		if(moon != null) {
			dao.setId(moon.getId());
			moonService.saveMoon(dao);
			return Result.ok("update");
		} else {
			moonService.saveMoon(dao);
			return Result.ok("OK");
		}

	}

	//@RequiresRoles("moon")
	@RequestMapping(value = "/moon/{moonId}", method = RequestMethod.PUT)
	public Result<MoonDTO> updateMoon(@PathVariable Integer moonId, @RequestBody MoonDTO req) {
		MoonDO dao = moonService.findOne(moonId);
										dao.setUserId(req.getUserId());
								dao.setMe(req.getMe());
								dao.setDad(req.getDad());
								dao.setMum(req.getMum());
										
		moonService.saveMoon(dao);
		return Result.ok("OK");
	}

	@RequestMapping(value = "/moon/{moonId}", method = RequestMethod.DELETE)
	public Result<MoonDTO> delete(@PathVariable Integer moonId) {

		moonService.deleteMoon(moonId);
		return Result.ok("OK");
	}
	
	@RequestMapping(value = "/moon/{moonId}", method = RequestMethod.GET)
	public Result<MoonDTO> findOne(@PathVariable Integer moonId) {

		MoonDO one = moonService.findOne(moonId);
		
		if(one != null) {
			return Result.ok("OK", MoonDO.toDTO(one));
		} else {
			return Result.error("bad id " + moonId);
		}
	}

}



