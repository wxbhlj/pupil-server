package com.vivid.ums.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.vivid.common.api.base.Result;
import com.vivid.common.utils.QiniuUtils;

@RestController
@RequestMapping("/api/v1/qiniu")
public class QiniuUploadController {
	
	
	@RequestMapping(value = "/token", method = RequestMethod.GET)
	public Result<String> getImageUploadToken() {

        return Result.ok("", QiniuUtils.uploadToken());
 
	}
	
	//https://pub.dev/packages/flutter_uploader


}
