package com.zhangkai.Activiti.dao;

import java.util.List;

import com.zhangkai.Activiti.model.Leave;

public interface LeaveDao {
	public Leave getLeave(int id);
	public List<Leave> getLeaveAll();
	public void insertLeave(Leave leave);
	public void deleteLeave(Leave leave);
	public void updateLeave(Leave leave);
	public Leave getLeaveByProcessInstanceId(String processInstanceId);
}
