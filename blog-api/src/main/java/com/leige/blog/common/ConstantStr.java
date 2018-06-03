package com.leige.blog.common;

/**
 * @author 张亚磊
 * @Description:
 * @date 2018/3/1  14:36
 */
public class ConstantStr {

	/** 系统标题 */
	public static final String TITLE = "磊哥博客后台管理系统";

	/** session用户标示 */
	public static final String SYS_USER = "sysUser";

	/** 菜单类型定义 */
	public static final int MENU_YTPE = 0;

	/**
	 * 验证码类型
	 */
	public enum CaptchaType {

		/** 用户登录 */
		userLogin,

		/** 找回密码 */
		findPassword,

		/** 重置密码 */
		resetPassword,

		/** 其它 */
		other
	}
}
