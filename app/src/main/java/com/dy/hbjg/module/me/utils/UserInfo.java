package com.dy.hbjg.module.me.utils;

/**
 * 用户
 */
public class UserInfo {
	public static String LOGIN_TYPE_NO = "-1";//未登录
	public static String LOGIN_TYPE_YES = "0";//默认登录
	public static String LOGIN_TYPE_QQ = "1";//QQ默认登录


	private String email = "";// 邮箱
	private String mobile = "";// 电话
	private String nickname = "";// 昵称
	private String gender = "";// 性别，1男2女
	private String avatar = "";// 用户头像地址

	private String loginType = LOGIN_TYPE_NO;


	private String birthday; //生日


	private String qq_id;
	private String wx_id;
	private String xm_id;



	private UserInfo() {
		email = UtilsUser.getUserInfoParference("email","");
		nickname = UtilsUser.getUserInfoParference("nickname","");
		mobile = UtilsUser.getUserInfoParference("mobile","");
		gender= UtilsUser.getUserInfoParference("gender","");
		avatar= UtilsUser.getUserInfoParference("avatar","");
		birthday = UtilsUser.getUserInfoParference("birthday","");
	}


	private static UserInfo inStance;

	public static UserInfo getInstance() {
		if (inStance == null) {
			inStance = new UserInfo();
			
		}
		return inStance;
	}



	public void clear() {

		mobile = "";
		nickname = "";
		gender = "";
		avatar = "";
		email = "";

		loginType = LOGIN_TYPE_NO;

		qq_id="";
		xm_id="";
		wx_id="";

		UtilsUser.clearUserInfo();
	}





}
