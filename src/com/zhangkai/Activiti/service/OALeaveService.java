package com.zhangkai.Activiti.service;

import java.util.Map;

import org.activiti.engine.runtime.ProcessInstance;

import com.zhangkai.Activiti.model.Leave;

public interface OALeaveService {
	public ProcessInstance startWorkflow(Leave leave,Map<String, Object> variables);
	public Leave getLeave(int leaveId);
}
