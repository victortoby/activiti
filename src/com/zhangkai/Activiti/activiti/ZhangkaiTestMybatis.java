package com.zhangkai.Activiti.activiti;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.zhangkai.Activiti.dao.LeaveDao;
import com.zhangkai.Activiti.model.Leave;

public class ZhangkaiTestMybatis {
	 
	public static void main(String args[]) {
		ApplicationContext context = 	new ClassPathXmlApplicationContext("ApplicationContext.xml");
		LeaveDao leaveDao = (LeaveDao)context.getBean("leaveDao");
		Leave leave = new Leave();
		leave.setId(2);
		leaveDao.insertLeave(leave);
	}
}
