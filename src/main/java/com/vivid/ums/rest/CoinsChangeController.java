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
import com.vivid.ums.db.dao.CoinsChangeDO;
import com.vivid.ums.rest.dto.CoinsChangeDTO;
import com.vivid.ums.service.CoinsChangeService;

/**
 * 
 *
 * @author xbwang
 * @email
 * @date 2020-02-21 11:46:05
 */

@RestController
@RequestMapping("/api/v1/ums")
public class CoinsChangeController {

	@Autowired
	private CoinsChangeService coinsChangeService;

	@RequestMapping(value = "/coinsChange/search", method = RequestMethod.POST)
	public Result<PageInfo<CoinsChangeDTO>> searchCoinsChange(@RequestBody BaseQueryReq req) {
		Result<PageInfo<CoinsChangeDTO>> resp = new Result<PageInfo<CoinsChangeDTO>>();
		PageInfo<CoinsChangeDTO> data = new PageInfo<CoinsChangeDTO>();
		resp.setData(data);
		System.err.println(req);
		Page<CoinsChangeDO> entities = coinsChangeService.searchCoinsChange(req.getPage(), req.getPageSize(),
				Integer.valueOf(req.getKeyword()));
		if (entities != null && entities.getSize() > 0) {
			List<CoinsChangeDTO> list = new ArrayList<CoinsChangeDTO>();
			for (CoinsChangeDO dao : entities.getContent()) {
				CoinsChangeDTO dto = CoinsChangeDO.toDTO(dao);
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

	@RequestMapping(value = "/coinsChange/exchange", method = RequestMethod.POST)
	public Result<CoinsChangeDTO> exchange(@RequestBody CoinsChangeDTO req) {
		CoinsChangeDO dao = CoinsChangeDO.fromDTO(req);
		dao.setCreated(new Date());

		String message = coinsChangeService.exchange(dao);
		if (message == null) {
			return Result.ok("OK");
		} else {
			return Result.error(message);
		}

	}
	
	@RequestMapping(value = "/coinsChange/award", method = RequestMethod.POST)
	public Result<CoinsChangeDTO> award(@RequestBody CoinsChangeDTO req) {
		CoinsChangeDO dao = CoinsChangeDO.fromDTO(req);
		dao.setCreated(new Date());

		String message = coinsChangeService.award(dao);
		if (message == null) {
			return Result.ok("OK");
		} else {
			return Result.error(message);
		}

	}

	@RequestMapping(value = "/coinsChange", method = RequestMethod.POST)
	public Result<CoinsChangeDTO> createCoinsChange(@RequestBody CoinsChangeDTO req) {
		CoinsChangeDO dao = CoinsChangeDO.fromDTO(req);
		dao.setCreated(new Date());

		coinsChangeService.saveCoinsChange(dao);
		return Result.ok("OK");

	}

	// @RequiresRoles("coinsChange")
	@RequestMapping(value = "/coinsChange/{coinsChangeId}", method = RequestMethod.PUT)
	public Result<CoinsChangeDTO> updateCoinsChange(@PathVariable Integer coinsChangeId,
			@RequestBody CoinsChangeDTO req) {
		CoinsChangeDO dao = coinsChangeService.findOne(coinsChangeId);
		dao.setUserId(req.getUserId());
		dao.setReason(req.getReason());
		dao.setCoins(req.getCoins());
		dao.setChangeType(req.getChangeType());

		coinsChangeService.saveCoinsChange(dao);
		return Result.ok("OK");
	}

	@RequestMapping(value = "/coinsChange/{coinsChangeId}", method = RequestMethod.DELETE)
	public Result<CoinsChangeDTO> delete(@PathVariable Integer coinsChangeId) {

		coinsChangeService.deleteCoinsChange(coinsChangeId);
		return Result.ok("OK");
	}

	@RequestMapping(value = "/coinsChange/{coinsChangeId}", method = RequestMethod.GET)
	public Result<CoinsChangeDTO> findOne(@PathVariable Integer coinsChangeId) {

		CoinsChangeDO one = coinsChangeService.findOne(coinsChangeId);

		if (one != null) {
			return Result.ok("OK", CoinsChangeDO.toDTO(one));
		} else {
			return Result.error("bad id " + coinsChangeId);
		}
	}

}
