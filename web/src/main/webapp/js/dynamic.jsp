<%@page import="org.sz.core.web.controller.BaseController,org.sz.core.util.ContextUtil"%>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
//设置ContextPath	
var __ctx='<%=request.getContextPath()%>';

var __jsessionId='<%=session.getId() %>';
var userId="${sessionScope['sysUser'].userId}";
var userName="${sessionScope['sysUser'].fullname}";
var userOrgId="${sessionScope['sysUser'].userOrgId}";
var userOrgName="${sessionScope['sysUser'].orgName}";
var userDeptId="${sessionScope['sysUser'].deptId}";
var userDeptName="${sessionScope['sysUser'].deptName}";

//从bootstrap.js移过来的方法
var getContextPath = function(){
	return __ctx;
}