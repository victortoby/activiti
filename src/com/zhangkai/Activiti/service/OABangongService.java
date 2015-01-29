package com.zhangkai.Activiti.service;

import org.activiti.engine.runtime.ProcessInstance;

import com.zhangkai.Activiti.model.Bangong;
import com.zhangkai.Activiti.util.Page;

public interface OABangongService {
	public ProcessInstance startWorkflow(Bangong bangong);
	public void findTodo(String userId,Page<Bangong> page);
	public Bangong getBangong(int id);
}
