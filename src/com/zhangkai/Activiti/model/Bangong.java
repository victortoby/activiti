package com.zhangkai.Activiti.model;

import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

public class Bangong {
	private int id;
	private String bangongType;
	private int number;
	private String userId;
	private String processInstanceId;
	private ProcessInstance processInstance;
	private String reason;
	private Task task;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getBangongType() {
		return bangongType;
	}

	public void setBangongType(String bangongType) {
		this.bangongType = bangongType;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
	
	public ProcessInstance getProcessInstance() {
		return processInstance;
	}

	public void setProcessInstance(ProcessInstance processInstance) {
		this.processInstance = processInstance;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}
}
