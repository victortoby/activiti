package com.zhangkai.Activiti.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.TaskService;
import org.activiti.engine.identity.User;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.zhangkai.Activiti.model.Leave;
import com.zhangkai.Activiti.service.LeaveWorkflowService;
import com.zhangkai.Activiti.service.OALeaveService;
import com.zhangkai.Activiti.util.Page;
import com.zhangkai.Activiti.util.UserUtil;
import com.zhangkai.Activiti.util.Variable;
import com.zhangkai.Activiti.vo.LeaveVO;

@Controller
@RequestMapping("/oa/leave")
public class OALeaveController {
	
	 private Logger logger = LoggerFactory.getLogger(getClass());

     @Autowired
     protected TaskService taskService;
     
     @Autowired
     protected OALeaveService oaLeaveService;
     
     @Autowired
     protected LeaveWorkflowService leaveWorkflowService;
	
	@RequestMapping("/apply")
	public String apply(Model model) {
		model.addAttribute("leave", new Leave());
        return "/oa/leave/leaveApply";
	}
	
	@RequestMapping("/start")
	public String start(LeaveVO leaveVO,RedirectAttributes redirectAttributes,HttpSession session) {
		try{
			User user = UserUtil.getUserFromSession(session);
			 // 用户未登录不能操作，实际应用使用权限框架实现，例如Spring Security、Shiro等
			 if (user == null || StringUtils.isBlank(user.getId())) {
	                return "redirect:/login?timeout=true";
	         }
			 Map<String, Object> variables = new HashMap<String, Object>();
			 Leave leave = new Leave();
			 // 类的深度拷贝方法(2,1)将对象1拷贝到2对象
             BeanUtils.copyProperties(leaveVO, leave);
             leave.setUserId(user.getId());
			 ProcessInstance processInstance = oaLeaveService.startWorkflow(leave, variables);
			 redirectAttributes.addFlashAttribute("message", "流程已启动，流程ID：" + processInstance.getId());
		} catch (ActivitiException e) {
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
        return "redirect:/oa/leave/apply";
	}
	
	@RequestMapping("/list/task")
	public ModelAndView listTask(HttpSession session,HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("/oa/leave/taskList");
		String userId = UserUtil.getUserFromSession(session).getId();
		Page<Leave> page = new Page<Leave>(15);
		leaveWorkflowService.findToDo(userId, page);
		mav.addObject("page", page);
		return mav;
	}
	
	@RequestMapping("/task/claim/{taskId}")
	public String claimTask(@PathVariable ("taskId") String taskId,HttpSession session,RedirectAttributes redirectAttributes) {
		User user = UserUtil.getUserFromSession(session);
		taskService.claim(taskId, user.getId());
		redirectAttributes.addFlashAttribute("message", "任务已签收");
		return "redirect:/oa/leave/list/task";
	}
	
	@RequestMapping(value = "/detail/{id}")
    @ResponseBody
    public Leave getLeave(@PathVariable("id") int id) {
        Leave leave = oaLeaveService.getLeave(id);
        return leave;
    }
	
	@RequestMapping(value = "complete/{id}", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public String complete(@PathVariable("id") String taskId, Variable var) {
        try {
            Map<String, Object> variables = var.getVariableMap();
            taskService.complete(taskId, variables);
            return "success";
        } catch (Exception e) {
            logger.error("error on complete task {}, variables={}", new Object[]{taskId, var.getVariableMap(), e});
            return "error";
        }
    }
	
	 /**
     * 读取详细数据
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "detail-with-vars/{id}/{taskId}")
    @ResponseBody
    public Leave getLeaveWithVars(@PathVariable("id") int id, @PathVariable("taskId") String taskId) {
        Leave leave = oaLeaveService.getLeave(id);
        Map<String, Object> variables = taskService.getVariables(taskId);
        leave.setVariables(variables);
        return leave;
    }

}
