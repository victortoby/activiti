<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>流程部署列表</title>
<%@ include file="/common/global.jsp"%>
<%@ include file="/common/meta.jsp" %>
<%@ include file="/common/include-base-styles.jsp" %>
<%@ include file="/common/include-jquery-ui-theme.jsp" %>
<link href="${ctx }/js/common/plugins/jui/extends/timepicker/jquery-ui-timepicker-addon.css" type="text/css" rel="stylesheet" />
<link href="${ctx }/js/common/plugins/qtip/jquery.qtip.min.css" type="text/css" rel="stylesheet" />
<%@ include file="/common/include-custom-styles.jsp" %>
 <style type="text/css">
    /* block ui */
	.blockOverlay {
		z-index: 1004 !important;
	}
	.blockMsg {
		z-index: 1005 !important;
	}
    </style>

    <script src="${ctx }/js/common/jquery-1.8.3.js" type="text/javascript"></script>
    <script src="${ctx }/js/common/plugins/jui/jquery-ui-${themeVersion }.min.js" type="text/javascript"></script>
    <script src="${ctx }/js/common/plugins/jui/extends/timepicker/jquery-ui-timepicker-addon.js" type="text/javascript"></script>
	<script src="${ctx }/js/common/plugins/jui/extends/i18n/jquery-ui-date_time-picker-zh-CN.js" type="text/javascript"></script>
	<script src="${ctx }/js/common/plugins/qtip/jquery.qtip.pack.js" type="text/javascript"></script>
	<script src="${ctx }/js/common/plugins/html/jquery.outerhtml.js" type="text/javascript"></script>
	<script src="${ctx }/js/common/plugins/blockui/jquery.blockUI.js" type="text/javascript"></script>
	<script src="${ctx }/js/module/activiti/workflow.js" type="text/javascript"></script>
	<script src="${ctx }/js/module/oa/leave/leave-todo.js" type="text/javascript"></script>
</head>
<body>
<a href="${ctx }/workflow/deploy/bangong">部署办公用品申请流程</a>
	<table width="100%" class="need-border">
		<thead>
			<tr>
				<th>部署ID</th>
				<th>部署名称</th>
				<th>部署时间</th>
			</tr>
		</thead>
		<tbody>
			<c:if test="${not empty page.result}">
				<c:forEach items="${page.result}" var="deploy">
					<tr>
						<td>${deploy.id }</td>
						<td>${deploy.name }</td>
						<td>${deploy.deploymentTime }</td>
					</tr>
				</c:forEach>
			</c:if>
		</tbody>
	</table>
</body>
</html>