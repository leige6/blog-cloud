package com.leige.blog.common.utils;

import javax.servlet.http.HttpServletRequest;

/**
  * @Title:
  * @Description: 获取客户端Ip地址
  * @param
  * @return
  * @author 张亚磊
  * @date 2018年05月25日 13:08:06
  */
public class IpHelper {
	

	//获取客户端的IP地址的方法是：request.getRemoteAddr()，这种方法在大部分情况下都是有效的。但是在通过了Apache,Squid等反向代理软件就不能获取到客户端的真实IP地址了。 经过代理以后，由于在客户端和服务之间增加了中间层，因此服务器无法直接拿到客户端的IP，服务器端应用也无法直接通过转发请求的地址返回给客户端。
	public static String getClientIpAddress(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");

		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {

			ip = request.getHeader("WL-Proxy-Client-IP");

		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {

			ip = request.getRemoteAddr();

		}
		return ip;
	}
}
