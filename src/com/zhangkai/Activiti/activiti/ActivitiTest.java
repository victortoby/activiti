package com.zhangkai.Activiti.activiti;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.DeploymentBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ActivitiTest {
	public static void main(String [] args) {
		ApplicationContext context = 	new ClassPathXmlApplicationContext("ApplicationContext.xml");
		ProcessEngine processEngine =(ProcessEngine) context.getBean("processEngine");
//		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		RepositoryService repositoryService = processEngine.getRepositoryService();
		DeploymentBuilder builder = repositoryService.createDeployment();
		builder.addClasspathResource("com/zhangkai/Activiti/diagrams/zhangkaiTest.bpmn");
		builder.deploy();
		RuntimeService runtimeService = processEngine.getRuntimeService();
		runtimeService.startProcessInstanceByKey("myProcess");
		System.out.println("ok......");
	}
}
