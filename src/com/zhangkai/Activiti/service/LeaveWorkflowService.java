package com.zhangkai.Activiti.service;

import java.util.List;

import com.zhangkai.Activiti.model.Leave;
import com.zhangkai.Activiti.util.Page;

public interface LeaveWorkflowService {
	public List<Leave> findToDo(String userId,Page<Leave> page);
}
