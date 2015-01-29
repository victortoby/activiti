package com.zhangkai.Activiti.service.Impl;

import java.util.Map;

import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhangkai.Activiti.dao.LeaveDao;
import com.zhangkai.Activiti.model.Leave;
import com.zhangkai.Activiti.service.OALeaveService;

@Service
public class OALeaveServiceImpl implements OALeaveService {
	
	 private static Logger logger = LoggerFactory.getLogger(OALeaveService.class);
	
	@Autowired
	private LeaveDao leaveDao;
	
	@Autowired
    private IdentityService identityService;
	
	@Autowired
	private RuntimeService runtimeService;
	
	@Autowired
	protected RepositoryService repositoryService;

	@Override
	public ProcessInstance startWorkflow(Leave leave,	Map<String, Object> variables) {
		String businessKey = String.valueOf(leave.getId());
		ProcessInstance processInstance = null;
		try {
			 // 用来设置启动流程的人员ID，引擎会自动把用户ID保存到activiti:initiator中
			identityService.setAuthenticatedUserId(leave.getUserId());
			repositoryService.createDeployment().addClasspathResource("com/zhangkai/Activiti/diagrams/Leave.bpmn").deploy();
			processInstance = runtimeService.startProcessInstanceByKey("leave", businessKey, variables);
			String processInstanceId = processInstance.getId();
			leave.setProcessInstanceId(processInstanceId);
			leaveDao.insertLeave(leave);
			logger.debug("save leave: {}", leave);
			logger.debug("start process of {key={}, bkey={}, pid={}, variables={}}", new Object[]{"leave", businessKey, processInstanceId, variables});
		} finally {
			identityService.setAuthenticatedUserId(null);
		}
		return processInstance;
	}

	@Override
	public Leave getLeave(int leaveId) {
		Leave leave = leaveDao.getLeave(leaveId);
		return leave;
	}

}
