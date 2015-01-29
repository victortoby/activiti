package com.zhangkai.Activiti.activiti;

import java.util.List;

import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

public class ActivitiDemo {
	public static void main(String[] args) {
		ProcessEngineConfiguration.createProcessEngineConfigurationFromResourceDefault();
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		RepositoryService repositoryService = processEngine.getRepositoryService();
		
//		DeploymentBuilder builder = repositoryService.createDeployment();
//		builder.addClasspathResource("com/zhangkai/Activiti/diagrams/financialReport.bpmn");
//		builder.deploy();
//		RuntimeService runtimeService = processEngine.getRuntimeService();
//		runtimeService.startProcessInstanceByKey("myProcess");
//		System.out.println("ok......");
		RuntimeService runtimeService = processEngine.getRuntimeService();
//		TaskService taskService = processEngine.getTaskService();
		Deployment  deployment = repositoryService.createDeployment().addClasspathResource("com/zhangkai/Activiti/diagrams/financialReport.bpmn").deploy();
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("financialReport");
//		List<Task> tasks = taskService.createTaskQuery().taskCandidateUser("kermit").list();
		// Start a process instance
	    String procId = runtimeService.startProcessInstanceByKey("financialReport").getId();

	    // Get the first task
	    TaskService taskService = processEngine.getTaskService();
	    List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup("accountancy").list();
	    for (Task task : tasks) {
	      System.out.println("Following task is available for accountancy group: " + task.getName());

	      // claim it
	      taskService.claim(task.getId(), "fozzie");
	    }

	    // Verify Fozzie can now retrieve the task
	    tasks = taskService.createTaskQuery().taskAssignee("fozzie").list();
	    for (Task task : tasks) {
	      System.out.println("Task for fozzie: " + task.getName());

	      // Complete the task
	      taskService.complete(task.getId());
	    }

	    System.out.println("Number of tasks for fozzie: "
	            + taskService.createTaskQuery().taskAssignee("fozzie").count());

	    // Retrieve and claim the second task
	    tasks = taskService.createTaskQuery().taskCandidateGroup("management").list();
	    for (Task task : tasks) {
	      System.out.println("Following task is available for accountancy group: " + task.getName());
	      taskService.claim(task.getId(), "kermit");
	    }

	    // Completing the second task ends the process
	    for (Task task : tasks) {
	      taskService.complete(task.getId());
	    }

	    // verify that the process is actually finished
	    HistoryService historyService = processEngine.getHistoryService();
	    HistoricProcessInstance historicProcessInstance =
	      historyService.createHistoricProcessInstanceQuery().processInstanceId(procId).singleResult();
	    System.out.println("Process instance end time: " + historicProcessInstance.getEndTime());
	  }

	
}
