package com.zhangkai.Activiti.activiti;

import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.BaseEntityEventListener;

public class MyListener extends BaseEntityEventListener {

	@Override
	protected void onCreate(ActivitiEvent event) {
	System.out.println("my create Listener");
	}

}
