package com.zhangkai.Activiti.activiti.dao;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.zhangkai.Activiti.dao.LeaveDao;
import com.zhangkai.Activiti.model.Leave;

public class TestMybatis {
	@Test
	public void addLeave() {
		ApplicationContext context = 	new ClassPathXmlApplicationContext("ApplicationContext.xml");
		LeaveDao leaveDao = (LeaveDao)context.getBean("leaveDao");
		Leave leave = new Leave();
		leave.setId(1);
		leaveDao.insertLeave(leave);
	}
}
