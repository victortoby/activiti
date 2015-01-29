package com.zhangkai.Activiti.activiti;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.DeploymentBuilder;


public class ActivitiTimeStart {
	public static void main(String[] args) {
		ProcessEngineConfiguration.createProcessEngineConfigurationFromResourceDefault();
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		RepositoryService repositoryService = processEngine.getRepositoryService();
		DeploymentBuilder builder = repositoryService.createDeployment();
		builder.addClasspathResource("com/zhangkai/Activiti/diagrams/zhangkaiTest2.bpmn");
		builder.deploy();
		RuntimeService runtimeService = processEngine.getRuntimeService();
		runtimeService.startProcessInstanceByKey("zhangkaiTest2");
		System.out.println("ok......");
	}
}
