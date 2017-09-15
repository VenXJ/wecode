package com.code.wecode.core.interceptor;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.code.wecode.core.util.ClientSysEvnUtil;

public class MyInterceptor implements HandlerInterceptor {
	private static final Logger logger = Logger.getLogger(MyInterceptor.class);
	
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		  String url=request.getServletPath();    
		  String ip = ClientSysEvnUtil.getIpAddr(request);
		  String agent = request.getHeader("user-agent");
	        if (url.equals("/")||url.equals("")){
	        	request.setAttribute("head", "index");
	        	//输出访问日志
				try {
					if(!url.startsWith("/images") && !url.startsWith("/css") && !url.startsWith("/scripts")){
						logger.info("[操作日志]--IP:" + ip + "; 来源：" + request.getHeader("referer") + "; 浏览器：" + request.getHeader("user-agent") + "; 访问路径：" + url);
					}
				} catch (Exception e) {
					// 防御容错
				}
	        	return true;  
			}else{
				return true;
			}
	}
	 /**
     *默认权限
     * @return
     */
    public boolean defaultAuth(String url){
    	List<String> list = new ArrayList<String>();
    	list.add("/home");
    	list.add("/secret/random");
    	list.add("/mchntCenter");
    	list.add("/loginOut");
    	list.add("/error");
    	
    	for(String l:list){
    		if(url.startsWith(l)){
    			return true;
    		}
    	}
    	return false;
    }

    /**
	 * url是否有访问权限
	 * @return
	 */
	public boolean urlIsAuth(HttpServletRequest request,String url){
		/*List<MerMenu> menuInfList = (List<MerMenu>) request.getSession().getAttribute(Constants.LOGIN_MENU);
		for(MerMenu menuInf:menuInfList){
			try {
				if(StringUtils.isNotEmpty(menuInf.getUrl().trim())&&url.startsWith(menuInf.getUrl())){
					return true;
				}
			} catch (Exception e) {
				LogWriter.error("异常菜单:"+menuInf);
				e.printStackTrace();
			}
		}*/
		return false;
	}
}
