package com.zhangkai.Activiti.service.Impl;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhangkai.Activiti.dao.LeaveDao;
import com.zhangkai.Activiti.model.Leave;
import com.zhangkai.Activiti.service.LeaveWorkflowService;
import com.zhangkai.Activiti.util.Page;
@Service
public class LeaveWorkflowServiceImpl implements LeaveWorkflowService {

	@Autowired
	private TaskService taskService;
	
	@Autowired
	private RuntimeService runtimeService;
	
	@Autowired
	private RepositoryService repositoryService;
	
	@Autowired
	private LeaveDao leaveDao;
	
	@Override
	public List<Leave> findToDo(String userId,Page<Leave> page) {
		List<Leave> result = new  ArrayList<Leave>();
		//根据用户查执行任务
		TaskQuery taskQuery = taskService.createTaskQuery().taskCandidateOrAssigned(userId);
		List <Task>taskList = taskQuery.list();
		//关联实体
		for(Task task : taskList) {
			String processInstanceId = task.getProcessInstanceId();
			ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
			String businessKey = processInstance.getBusinessKey();
			if(StringUtils.isBlank(businessKey)) {
				continue;
			}
			Leave leave = leaveDao.getLeaveByProcessInstanceId(processInstanceId);
			leave.setProcessInstance(processInstance);
			leave.setProcessDefinition(this.getProcessDefinition(processInstance.getProcessDefinitionId()));
			leave.setTask(task);
			result.add(leave);
		}
		page.setTotalCount(taskQuery.count());
		page.setResult(result);
		return result;
	}
	
	public ProcessDefinition getProcessDefinition(String processDefinitionId) {
		ProcessDefinition processDefinition = repositoryService.getProcessDefinition(processDefinitionId);
		return processDefinition;
	}

}
