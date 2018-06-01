package com.leige.blog.common.captcha;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
  * @Title:
  * @Description:  验证码生成类
  * @param
  * @return
  * @author 张亚磊
  * @date 2018年05月31日 14:14:25
  */
@Component
public class MyblogCaptcha implements InitializingBean {
	private final static Logger logger = LogManager.getLogger(MyblogCaptcha.class);
	private final static String DEFAULT_KEY_NAME = "myblog-captcha";
	private final static String DEFAULT_CHACHE_NAME = "myblogCaptchaCache";
	private final static Long liveTime = 120L; // 验证码有效时间

	private String cacheName;
	private String keyName;
	
	public MyblogCaptcha() {
		this.cacheName = DEFAULT_CHACHE_NAME;
		this.keyName = DEFAULT_KEY_NAME;
	}

	
	public String getCacheName() {
		return cacheName;
	}
	
	public void setCacheName(String cacheName) {
		this.cacheName = cacheName;
	}

	public String getKeyName() {
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.hasText(cacheName, "cacheName must not be empty!");
		Assert.hasText(keyName, "cookieName must not be empty!");
	}
	
	/**
	 * 生成验证码
	 */
	public void generate(HttpServletRequest request, HttpServletResponse response) {
		// 先检查cookie的uuid是否存在
		String captchaCode = CaptchaUtils.generateCode().toUpperCase();// 转成大写重要
		// 生成验证码
		request.getSession().setAttribute(cacheName,captchaCode);
		CaptchaUtils.generate(response, captchaCode);

	}
	
	/**
	 * 仅能验证一次，验证后立即删除
	 * @param request HttpServletRequest
	 * @param userInputCaptcha 用户输入的验证码
	 * @return 验证通过返回 true, 否则返回 false
	 */
	public boolean validate(HttpServletRequest request,String userInputCaptcha) {
		String captchaCode=(String)request.getSession().getAttribute(cacheName);
		if (StringUtils.isBlank(captchaCode)) {
			return false;
		}
		// 转成大写重要
		userInputCaptcha = userInputCaptcha.toUpperCase();
		boolean result = userInputCaptcha.equals(captchaCode);
		if (result) {
			request.getSession().removeAttribute(cacheName);
		}
		return result;
	}
}
