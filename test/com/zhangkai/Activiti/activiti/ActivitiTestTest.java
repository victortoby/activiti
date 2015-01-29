package com.zhangkai.Activiti.activiti;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.Deployment;
import org.junit.Rule;
import org.junit.Test;

public class ActivitiTestTest {
	@Rule
	public ActivitiRule activitiRule = new ActivitiRule();
	
	
	@Test
	@Deployment
	public void ruleUsageExample() {
		activitiRule.setConfigurationResource("src/com/zhangkai/Activiti/diagrams/financialReport.bpmn");
		RuntimeService runtimeService = activitiRule.getRuntimeService();
		runtimeService.startProcessInstanceByKey("financialReport");
		
	}
}
