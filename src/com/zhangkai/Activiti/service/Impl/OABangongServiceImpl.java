package com.zhangkai.Activiti.service.Impl;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhangkai.Activiti.dao.BangongDao;
import com.zhangkai.Activiti.model.Bangong;
import com.zhangkai.Activiti.service.OABangongService;
import com.zhangkai.Activiti.util.Page;

@Service
public class OABangongServiceImpl implements OABangongService {
	
	@Autowired
	private RuntimeService runtimeService;
	
	@Autowired
	private BangongDao bangongDao;
	
	@Autowired
	private TaskService taskService;

	@Override
	public ProcessInstance startWorkflow(Bangong bangong) {
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("bangong");
		bangong.setProcessInstanceId(processInstance.getId());
		bangongDao.insertBangong(bangong);
		return processInstance;
	}

	@Override
	public void findTodo(String userId, Page<Bangong> page) {
		 TaskQuery taskQuery = taskService.createTaskQuery().taskCandidateOrAssigned(userId).orderByTaskCreateTime().desc();
		 List<Task> taskList = taskQuery.list();
		 Long totalCount = taskQuery.count();
		List<Bangong> bangongList = new ArrayList<Bangong>();
		if(null != taskList && taskList.size() > 0) {
			for(Task task : taskList) {
				String processInstanceId = task.getProcessInstanceId();
				Bangong bangong = bangongDao.getBangongByProcessInstanceId(processInstanceId);
				if(null != bangong) {
					ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
					bangong.setTask(task);
					bangong.setProcessInstance(processInstance);
					bangongList.add(bangong);
				}
				
			}
			page.setResult(bangongList);
			page.setTotalCount(totalCount);
		}
	}
	
	@Override
	public Bangong getBangong(int id) {
		Bangong bangong = bangongDao.getBangongById(id);
		return bangong;
	}

}
