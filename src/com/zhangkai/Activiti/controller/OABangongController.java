package com.zhangkai.Activiti.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.TaskService;
import org.activiti.engine.identity.User;
import org.activiti.engine.runtime.ProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.zhangkai.Activiti.model.Bangong;
import com.zhangkai.Activiti.service.OABangongService;
import com.zhangkai.Activiti.util.Page;
import com.zhangkai.Activiti.util.UserUtil;
import com.zhangkai.Activiti.util.Variable;
import com.zhangkai.Activiti.vo.BangongVo;

@Controller
@RequestMapping("/oa/bangong")
public class OABangongController {
	 private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private OABangongService oaBangongService;
	
	@Autowired
	private TaskService taskService;
	
	@RequestMapping("/apply")
	public String apply() {
		return "/oa/bangong/bangongApply";
	}
	
	@RequestMapping("/start")
	public String start(Model model,HttpSession session,BangongVo bangongVo,RedirectAttributes redirectAttributes) {
		User user = UserUtil.getUserFromSession(session);
		if(null == user) {
			return "redirect:/login?timeout=true";
		}
		Bangong bangong = new Bangong();
		 // 类的深度拷贝方法(2,1)将对象1拷贝到2对象
        BeanUtils.copyProperties(bangongVo, bangong);
        try{
        	bangong.setUserId(user.getId());
	        ProcessInstance processInstance = oaBangongService.startWorkflow(bangong);
	        System.out.println("流程实例ID"+processInstance.getId());
	        System.out.println("流程定义ID"+processInstance.getProcessDefinitionId());
	        redirectAttributes.addFlashAttribute("message", "流程已启动，流程ID：" + processInstance.getId());
        }
        catch (ActivitiException e) {
			 if (e.getMessage().indexOf("no processes deployed with key") != -1) {
	                logger.warn("没有部署流程!", e);
	                redirectAttributes.addFlashAttribute("error", "没有部署流程，请在[工作流]->[流程管理]页面点击<重新部署流程>");
	            } else {
	                logger.error("启动请假流程失败：", e);
	                redirectAttributes.addFlashAttribute("error", "系统内部错误！");
	            }
		}  catch (Exception e) {
           logger.error("启动请假流程失败：", e);
           redirectAttributes.addFlashAttribute("error", "系统内部错误！");
       }
		return "redirect:/oa/bangong/apply";
	}
	
	@RequestMapping("/list/task")
	public ModelAndView listTask(HttpSession session,HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("/oa/bangong/taskList");
		User user = UserUtil.getUserFromSession(session);
		Page<Bangong> page = new Page<Bangong>(15);
		oaBangongService.findTodo(user.getId(), page);
		mav.addObject("page", page);
		return mav;
	}
	
	@RequestMapping("/task/claim/{taskId}")
	public String claim(@PathVariable("taskId") String taskId,HttpSession session) {
		User user = UserUtil.getUserFromSession(session);
		if(null == user) {
			return "redirect:/login?timeout=true";
		}
		taskService.claim(taskId, user.getId());
		return "redirect:/oa/bangong/list/task";
	}
	
	@RequestMapping("/detail/{id}")
	@ResponseBody
	public Bangong getBangong(@PathVariable("id") int id) {
		Bangong bangong = oaBangongService.getBangong(id);
		return bangong;
	}
	
	@RequestMapping("/complete/{taskId}")
	@ResponseBody
	public String complete(@PathVariable("taskId") String taskId,Variable var) {
		try {
			Map<String, Object> variables = var.getVariableMap();
			taskService.complete(taskId, variables);
			return "success";
		} catch(Exception e) {
			 logger.error("error on complete task {}, variables={}", new Object[]{taskId, var.getVariableMap(), e});
             return "error";
		}
		
	}
	
}
