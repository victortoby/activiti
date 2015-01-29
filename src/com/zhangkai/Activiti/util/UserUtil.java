package com.zhangkai.Activiti.util;

import javax.servlet.http.HttpSession;

import org.activiti.engine.identity.User;

public class UserUtil {
	public static final String USER = "user";
	
	/**
	 * 设置用户到session
	 */
	public static void saveUserToSession(HttpSession session, User user) {
		session.setAttribute(USER, user);
	}
	
	/**
	 * 从session中取得当前用户信息
	 */
	public static User getUserFromSession(HttpSession session) {
		 Object attribute = session.getAttribute(USER);
         return attribute == null ? null : (User) attribute;
	}
}
