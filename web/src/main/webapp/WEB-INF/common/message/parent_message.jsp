<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<script type="text/javascript">
		function message() {
			var callback = parent.messageCallback;
			if (callback && typeof (callback) == "function") {
				callback("${ro.code}", "${ro.message}", "${ro.options}");
			} else {
				if ("${ro.code}" == "${tools.success}") {
					parent.$.ligerDialog.success("操作成功");
				} else if ("${ro.code}" == "${tools.error}") {
					parent.$.ligerDialog.error("${ro.message}" == "" ? "操作失败" : "${ro.message}");
				} else if ("${ro.code}" == "${tools.warn}") {
					parent.$.ligerDialog.warn("${ro.message}" == "" ? "操作完成,但是出现警告" : "<li>${ro.message}</li>");
				}
			}
		}
	</script>
</head>
<body onload="message()">
</body>
</html>