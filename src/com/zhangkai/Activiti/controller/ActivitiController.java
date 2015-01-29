package com.zhangkai.Activiti.controller;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.identity.User;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.zhangkai.Activiti.util.Page;
import com.zhangkai.Activiti.util.UserUtil;

@Controller
@RequestMapping("/workflow")
public class ActivitiController {
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private RepositoryService repositoryService;
	
	protected static Map<String,ProcessDefinition> PROCESS_DEFINITION_CACHE = new HashMap<String,ProcessDefinition>();
	
	@RequestMapping(value = "/task/todo/list")
    @ResponseBody
    public List<Map<String,Object>> todoList(HttpSession session) {
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		User user = UserUtil.getUserFromSession(session);
		//已经签收的任务
		List<Task> todoList = taskService.createTaskQuery().taskAssignee(user.getId()).active().list();
		for(Task task : todoList) {
			String processDefinitionId = task.getProcessDefinitionId();
			ProcessDefinition processDefinition = getProcessDefinition(processDefinitionId);
			Map<String,Object> singleTask = packageTaskInfo(sdf, task, processDefinition);
			singleTask.put("status", "todo");
			result.add(singleTask);
		}
		
		//等待签收的任务
		List<Task>toClaimList = taskService.createTaskQuery().taskCandidateUser(user.getId()).active().list();
		for(Task task : toClaimList){
			String processDefinitionId = task.getProcessDefinitionId();
			ProcessDefinition processDefinition = getProcessDefinition(processDefinitionId);
			Map<String,Object> singleTask = packageTaskInfo(sdf, task, processDefinition);
			singleTask.put("status", "claim");
			result.add(singleTask);
		}
		return result;
	}
	
	protected ProcessDefinition getProcessDefinition(String processDefinitionId) {
		ProcessDefinition processDefinition = PROCESS_DEFINITION_CACHE.get(processDefinitionId);
		if(null == processDefinition) {
			processDefinition = repositoryService.getProcessDefinition(processDefinitionId);
			PROCESS_DEFINITION_CACHE.put(processDefinitionId, processDefinition);
		}
		return processDefinition;
	}
	
	private Map<String,Object>packageTaskInfo(SimpleDateFormat sdf,Task task,ProcessDefinition processDefinition) {
		Map<String,Object> singleTask = new HashMap<String,Object>();
		singleTask.put("id", task.getId());
		singleTask.put("name", task.getName());
		singleTask.put("createTime", sdf.format(task.getCreateTime()));
		singleTask.put("pdname", processDefinition.getName());
		singleTask.put("pdversion", processDefinition.getVersion());
		singleTask.put("pid", task.getProcessInstanceId());
		return singleTask;
	}
	
	@RequestMapping("/deploy/bangong")
	public String deployBangong() {
		String resourceName = "bangong.bpmn";
		InputStream inputStream = this.getClass().getResourceAsStream("/com/zhangkai/Activiti/diagrams/bangong.bpmn");
		Deployment deployment = repositoryService.createDeployment()
			.name("办公用品申请")
			.addInputStream(resourceName, inputStream)
			.deploy();
		System.out.println("部署ID" + deployment.getId());
		System.out.println("部署名称" + deployment.getName());
		return "redirect:/workflow/deployList";
	}
	
	@RequestMapping("/deployList")
	public ModelAndView deployList(ModelAndView mav) {
		List<Deployment> list = repositoryService.createDeploymentQuery()
				.orderByDeploymenTime().desc().list();
		if(null != list && list.size() > 0) {
			Page<Deployment> page = new Page<Deployment>(15);
			page.setResult(list);
			mav.addObject("page", page);
		}
		mav.setViewName("workFlow/deployList");
		return mav;
	} 
	
}
