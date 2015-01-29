package com.zhangkai.Activiti.dao;

import com.zhangkai.Activiti.model.Bangong;

public interface BangongDao {
	public void insertBangong(Bangong bangong);
	public Bangong getBangongByProcessInstanceId(String processInstanceId);
	public Bangong getBangongById(int id);
}
