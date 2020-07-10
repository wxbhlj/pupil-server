package com.vivid.ums.rest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.vivid.common.api.base.BaseQueryReq;
import com.vivid.common.api.base.PageInfo;
import com.vivid.common.api.base.Result;

import com.vivid.common.utils.QiniuUtils;
import com.vivid.ums.db.dao.TaskDO;
import com.vivid.ums.db.dao.UserDO;

import com.vivid.ums.rest.dto.LoginResp;
import com.vivid.ums.rest.dto.TaskCheckReq;
import com.vivid.ums.rest.dto.TaskDTO;
import com.vivid.ums.rest.dto.UpdateMemberReq;
import com.vivid.ums.rest.dto.UserDTO;
import com.vivid.ums.service.TaskService;
import com.vivid.ums.service.UserService;


/**
 * 
 *
 * @author xbwang
 * @email
 * @date 2020-01-28 12:37:20
 */

@RestController
@RequestMapping("/api/v1/ums")
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private HttpServletRequest request;

	@RequestMapping(value = "/user/search", method = RequestMethod.POST)
	public Result<PageInfo<UserDTO>> searchUser(@RequestBody BaseQueryReq req) {
		Result<PageInfo<UserDTO>> resp = new Result<PageInfo<UserDTO>>();
		PageInfo<UserDTO> data = new PageInfo<UserDTO>();
		resp.setData(data);
		System.err.println(req);
		Page<UserDO> entities = userService.searchUser(req.getPage(), req.getPageSize(), req.getKeyword());
		if (entities != null && entities.getSize() > 0) {
			List<UserDTO> list = new ArrayList<UserDTO>();
			for (UserDO dao : entities.getContent()) {
				UserDTO dto = UserDO.toDTO(dao);
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



	@RequestMapping(value = "/user/{userId}", method = RequestMethod.DELETE)
	public Result<UserDTO> delete(@PathVariable Integer userId) {

		userService.deleteUser(userId);
		return Result.ok("OK");
	}
	
	// @RequiresRoles("task")
	@RequestMapping(value = "/user/updateNick", method = RequestMethod.PUT)
	public Result<Boolean> updateNick(String nick) {
		System.out.println(request.getHeader("userId"));
		int userId = Integer.valueOf(request.getHeader("userId"));
		UserDO dao = userService.findOne(userId);
		dao.setNick(nick);
		dao.setUpdated(new Date());
		userService.saveUser(dao);
		return Result.ok("OK");
	}
	
	@RequestMapping(value = "/user/updatePwd", method = RequestMethod.PUT)
	public Result<Boolean> updatePwd(String pwd) {
		System.out.println(request.getHeader("userId"));
		int userId = Integer.valueOf(request.getHeader("userId"));
		UserDO dao = userService.findOne(userId);
		dao.setPassword(DigestUtils.md5Hex(pwd));
		dao.setUpdated(new Date());
		userService.saveUser(dao);
		return Result.ok("OK");
	}

	@RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
	public Result<UserDTO> findOne(@PathVariable Integer userId) {

		UserDO one = userService.findOne(userId);

		if (one != null) {
			return Result.ok("OK", UserDO.toDTO(one));
		} else {
			return Result.error("bad id " + userId);
		}
	}
	
	@RequestMapping(value = "/user/getDetail/{userId}", method = RequestMethod.GET)
	public Result<LoginResp> getDetail(@PathVariable Integer userId) {

		UserDO user = userService.findOne(userId);

		if (user != null) {
			LoginResp resp = new LoginResp();
			
			resp.setCoinsTotal(user.getCoinsTotal());
			resp.setCoinsUsed(user.getCoinsUsed());
			resp.setAvatar(user.getAvatar());
			resp.setNick(user.getNick());
			resp.setToken(user.getToken());
			resp.setUserId(user.getId());
			resp.setAvgScore(taskService.getLastMonthAvgScore(user.getId()));
			resp.setLoginTime(System.currentTimeMillis());
			
			return Result.ok("OK", resp);
		} else {
			return Result.error("bad id " + userId);
		}
	}
	
	@RequestMapping(value = "/user/updateAvatar", method = RequestMethod.POST)
	public Result<UserDTO> updateAvatar(@RequestParam(name = "file") MultipartFile file) {
		Date now = new Date();
		System.out.println(request.getHeader("userId"));
		int userId = Integer.valueOf(request.getHeader("userId"));
		
		if(file != null && file.getSize() > 0) {
			UserDO user = userService.findOne(userId);
			String oldAvatar = user.getAvatar();
			String key = user.getId() + "-HEADER-" + System.currentTimeMillis() + ".png";
			user.setAvatar("http://img.shellsports.cn/" + key);
			user.setUpdated(now);
			try {
				boolean bRet = QiniuUtils.uploadFile(key, file.getBytes());
				if(bRet) {
					userService.saveUser(user);
					if(oldAvatar != null && oldAvatar.startsWith("http")) {
						key = oldAvatar.replace("http://img.shellsports.cn/", "");
						QiniuUtils.deleteFile(key);
					}
					return Result.ok(UserDO.toDTO(user));
				}
			} catch(Exception ex) {
				ex.printStackTrace();
				
			}
		}
		return Result.error("图片上传错误");
	}

}
