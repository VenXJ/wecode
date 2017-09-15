package com.code.wecode.core.util;

import javax.servlet.http.HttpServletRequest;


/**
 * @author rw
 *
 */
public class ClientSysEvnUtil {
	
	public static  String getIpAddr(HttpServletRequest request) { 
	    String ip = request.getHeader("x-forwarded-for"); 
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	        ip = request.getHeader("Proxy-Client-IP"); 
	    } 
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	        ip = request.getHeader("WL-Proxy-Client-IP"); 
	    } 
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	        ip = request.getRemoteAddr(); 
	    }
	    if(StringUtil.isNotEmpty(ip)&&ip.split(",").length>1){
	    	ip=ip.split(",")[0];
	    }
	    return ip; 
	}
}
