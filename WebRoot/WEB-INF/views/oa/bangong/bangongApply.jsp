<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>办公用品申请</title>
<%@ include file="/common/global.jsp"%>
<%@ include file="/common/meta.jsp" %>
<%@ include file="/common/include-base-styles.jsp" %>
<%@ include file="/common/include-jquery-ui-theme.jsp" %>
<link href="${ctx }/js/common/plugins/jui/extends/timepicker/jquery-ui-timepicker-addon.css" type="text/css" rel="stylesheet" />

<script src="${ctx }/js/common/jquery-1.8.3.js" type="text/javascript"></script>
<script src="${ctx }/js/common/plugins/jui/jquery-ui-${themeVersion }.min.js" type="text/javascript"></script>
<script src="${ctx }/js/common/plugins/jui/extends/timepicker/jquery-ui-timepicker-addon.js" type="text/javascript"></script>
<script src="${ctx }/js/common/plugins/jui/extends/i18n/jquery-ui-date_time-picker-zh-CN.js" type="text/javascript"></script>
</head>
<body>
	<div class="container showgrid">
		<c:if test="${not empty message}">
			<div id="message" class="alert alert-success">${message}</div>
			<!-- 自动隐藏提示信息 -->
			<script type="text/javascript">
				setTimeout(function() {
					$('#message').hide('slow');
				}, 5000);
			</script>
		</c:if>
		<c:if test="${not empty error}">
			<div id="error" class="alert alert-error">${error}</div>
			<!-- 自动隐藏提示信息 -->
			<script type="text/javascript">
				setTimeout(function() {
					$('#error').hide('slow');
				}, 5000);
			</script>
		</c:if>
		<form id="inputForm" action="${ctx}/oa/bangong/start" method="post" class="form-horizontal">
			<fieldset>
				<legend>
					<small>办公用品申请</small>
				</legend>
				<table border="1">
					<tr>
						<td>办公用品类型</td>
						<td>
								<select id="bangongType" name="bangongType">
									<option>签字笔</option>
									<option>笔记本</option>
									<option>胶条</option>
									<option>其他</option>
								 </select>
						 </td>
					</tr>
					<tr>
						<td>数量</td>
						<td><input type="text" id="number" name="number"></td>
					</tr>
					<tr>
						<td>理由</td>
						<td><textarea name="reason" id="reason"></textarea></td>
					</tr>
					<tr>
						<td>&nbsp;</td>
						<td>
							<input type="submit" value="申请">
						</td>
					</tr>
				</table>
			</fieldset>
		</form>
	</div>
</body>
</html>