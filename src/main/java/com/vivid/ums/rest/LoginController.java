package com.vivid.ums.rest;


import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.vivid.common.api.base.Result;
import com.vivid.common.utils.CodeCacheUtil;
import com.vivid.common.utils.TokenCacheUtil;
import com.vivid.common.utils.Utils;
import com.vivid.ums.db.dao.UserDO;
import com.vivid.ums.rest.dto.LoginReq;
import com.vivid.ums.rest.dto.LoginResp;
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
@RequestMapping("/api/v1/auth")
public class LoginController { 
	
	private static final Logger LOG = LoggerFactory.getLogger(LoginController.class);


	private static String PRIVATE_KEY = "my@#family##";
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TaskService taskService;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public Result<LoginResp> login(@RequestBody LoginReq req) {
		
		LoginResp resp = new LoginResp();
		
		String code = CodeCacheUtil.getInstance().get10(req.getMobile());
		System.out.println("code = " + code + "," + req.getCode());
		UserDO user = userService.findByLoginName(req.getMobile());
		
		if(user == null ) { //注册
			if(req.getCode() != null && StringUtils.equals(code, req.getCode())) {
				user = userService.register(req.getMobile());
				TokenCacheUtil.getInstance().put(user.getToken(), user.getId());
			} else {
				return Result.error("验证码错误"); 
			}
			
		} else {
			
			if(req.getCode() != null && StringUtils.equals(code, req.getCode())) {
				System.out.println("验证码登录成功");
				//验证码登录成功
			} else if(user.getPassword() != null && user.getPassword().equals(DigestUtils.md5Hex(req.getCode()))) {
				System.out.println("密码登录成功");
				//密码登录成功
			} else {
				return Result.error("验证码或密码错误"); 
			}
			
			//废弃老TOKEN
			/*
			TokenCacheUtil.getInstance().remove(user.getToken());
			String token = DigestUtils.md5Hex(req.getMobile() + String.valueOf(System.currentTimeMillis()) + "@DETREDWwse");
			user.setToken(token);
			userService.saveUser(user);
			TokenCacheUtil.getInstance().put(user.getToken(), user.getId());
			*/
		}
		
		
		resp.setCoinsTotal(user.getCoinsTotal());
		resp.setCoinsUsed(user.getCoinsUsed());
		resp.setAvatar(user.getAvatar());
		resp.setNick(user.getNick());
		resp.setToken(user.getToken());
		resp.setUserId(user.getId());
		resp.setAvgScore(taskService.getLastMonthAvgScore(user.getId()));
		resp.setLoginTime(System.currentTimeMillis());

		return Result.ok(resp);
		
	}
	/*
	 * 发送短信验证码 
	 * mobile 手机号码
	 * sign   签名，用于验证请求的合法性 sign = md5Hex(mobile + private_key)
	 */
	@RequestMapping(value = "/sendVerifyCode/{mobile}/{sign}", method = RequestMethod.GET)
	public Result<Boolean> sendVerifyCode(@PathVariable String mobile, @PathVariable String sign){
		
		if(!StringUtils.equals(sign, "myfamily")) {//!DigestUtils.md5Hex(mobile + PRIVATE_KEY).equals(sign) ) {
			return Result.error("Sign is invalid. sign = md5Hex(mobile + private_key) ");
		}
		 
		if(CodeCacheUtil.getInstance().exist1(mobile + "-recent")) {
			return Result.error("Request is frequent");
		}
		String code = RandomStringUtils.randomNumeric(4);
        try {
            boolean b = Utils.sendCode(mobile, code);
            if(b) {
            	CodeCacheUtil.getInstance().put10(mobile, code); //10分钟有效
            	CodeCacheUtil.getInstance().put1(mobile + "-recent", "1"); //1分钟内不能重复发请求
            	return Result.ok(true);
            } else {
            	return Result.error("发送错误");
            }
        } catch (Exception e) {
        	LOG.error("", e);
            return Result.error(e.getMessage());
        } 
	}
	


}



