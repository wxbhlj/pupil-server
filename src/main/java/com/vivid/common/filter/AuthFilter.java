package com.vivid.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.vivid.common.enums.PlatformCodeEnum;
import com.vivid.common.utils.TokenCacheUtil;
import com.vivid.ums.db.dao.UserDO;
import com.vivid.ums.service.UserService;

public class AuthFilter implements Filter{

	 
	@Autowired
	private UserService userService;
 
    String[] ignores = new String[]{"/auth/", "swagger", "api-docs"};
 
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HeaderMapRequestWrapper requestWrapper = new HeaderMapRequestWrapper(request);

        String uri = request.getRequestURI();
        System.out.println("filter url:"+uri + ", this = " + this);
        //是否需要过滤

        if (isIgnoreUri(uri)) { //不需要过滤直接传给下一个过滤器
            filterChain.doFilter(servletRequest, servletResponse);
        } else { //需要过滤器
         
        	String token = request.getHeader("Authorization");
        	System.out.println("token = " + token);
        	if(token == null) {
        		response.setContentType("application/json;charset=utf-8");
        		response.getWriter().write("{code:\"" + PlatformCodeEnum.TOKEN_IS_NULL.getCode() + "\", message:\"请重新登录\"}");
        		return;
        	}
            Integer userId = TokenCacheUtil.getInstance().get(token);
            System.out.println("userId = " + userId);
            if(userId != null && userId > 0){
            	requestWrapper.addHeader("userId", userId + "");
            	filterChain.doFilter(requestWrapper, servletResponse);
            }else{
            	
                UserDO user = userService.findByToken(token);
                
                if(user != null ) {
                	System.out.println("验证通过 = " + user.getId());
                	TokenCacheUtil.getInstance().put(token, user.getId());
                	requestWrapper.addHeader("userId", user.getId() + "");
          
                	filterChain.doFilter(requestWrapper, servletResponse);
                } else {
                	System.out.println("验证失败 = " );
                	response.setContentType("application/json;charset=utf-8");
                	response.getWriter().write("{\"code\":\"" + PlatformCodeEnum.TOKEN_ERROR.getCode() + "\", \"message\":\"请重新登录\"}");
                	return;
                }
            }
            return;
        }
    }
 
    /**
     * @Description: 是否需要过滤
     */
    public boolean isIgnoreUri(String uri) {
        for (String ignore : ignores) {
            if(uri.contains(ignore)) {
                return true;
            }
        }
        return false;
 
    }
 
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }
 
    @Override
    public void destroy() {
    }



}
