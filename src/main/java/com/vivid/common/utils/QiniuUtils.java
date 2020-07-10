package com.vivid.common.utils;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;

public class QiniuUtils {
	
	/**
     * ACCESS_KEY
     */
    private static final String ACCESS_KEY = "nZWStgvYXb-dwYQFybpW39qkxaMj86irxXOp7k9i";
    /**
     * SECRET_KEY
     */
    private static final String SECRET_KEY = "u_RhhjfOuYR4sndmXzrfLO3ZBl_q4f8IGtWBXWWJ";
    /**
     * BUCKET_NAME
     */
    private static final String BUCKET_NAME = "bosoft";
    
    private static Configuration cfg = new Configuration(Zone.zone0());
    
	public static void deleteFile(String key) { 
		
		Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
		BucketManager bucketManager = new BucketManager(auth, cfg);
		try {
		    bucketManager.delete(BUCKET_NAME, key);
		} catch (QiniuException ex) {
		    //如果遇到异常，说明删除失败
		    System.err.println(ex.code());
		    System.err.println(ex.response.toString());
		}
	}
	
	public static String uploadToken() {
		Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
		return auth.uploadToken(BUCKET_NAME);
	}
	
	public static boolean uploadFile(String key, byte[] bytes) {
		try {
		
		    Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
		    String upToken = auth.uploadToken(BUCKET_NAME);
		    try {
	
		    	UploadManager uploadManager = new UploadManager(cfg);
		        Response response = uploadManager.put(bytes, key, upToken);
		        //解析上传成功的结果
		        //DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
		        return true;
		    } catch (Exception ex) {
		        ex.printStackTrace();
		    }
		} catch (Exception ex) {
		    //ignore
		}
		return false;
	}

}
