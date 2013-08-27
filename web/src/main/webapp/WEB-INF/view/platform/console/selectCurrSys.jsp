<%@page pageEncoding="UTF-8" autoFlush="true" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="f" uri="http://www.servicezon.com/functions" %> 
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>业务流程系统</title>
	<link rel="stylesheet" type="text/css" href="${ctx}/styles/default/css/choose_sys.css" />
    <script type="text/javascript" src="${ctx }/jslib/jquery/jquery.js" ></script>
    <script type="text/javascript" src="${ctx }/js/util/util.js"  ></script>
    <script type="text/javascript" src="${ctx}/js/util/form.js"></script>
    <script language="JavaScript"> 
	
	function showSubSystemList(){
		if($(".show_sys").css("display")=="none"){
			$(".show_sys").css("display","block");
		}else{
			$(".show_sys").css("display","none");
		}	
	}
	
	$(function(){
		$('#selectUl > li').click(function(){
			//alert($(this).attr('id'));
			$(".show_sys").css("display","none");
			$('#select_sys').html(this.innerHTML);
			
			var id=$(this).attr('id');
			if(id){
				saveCurrentSys(id);
			}
		});
	});

	function saveCurrentSys(systemId){
		var form=new org.sz.form.Form();
		form.creatForm("form", "${ctx}/platform/console/saveCurrSys.xht");
		form.addFormEl("systemId", systemId);
		form.submit();
	};
	
</script>
</head>
<body>
					
                        <div class="index_content">
                  				  <div class="choose_sys_panel">
                                			<div class="select_sys"><span id="select_sys"></span><img src="${ctx}/styles/default/images/jiantou.jpg" class="show_sys_choose" onclick="showSubSystemList()"/></div><input type="button" class="yes_button"/>
                               				<c:choose> 				
													<c:when test="${fn:length(subSystemList) > 0}">  
														<div class="show_sys">
															<ul id="selectUl">
																<c:forEach var="subSystemItem" items="${subSystemList}"> 
																	<li id="${subSystemItem.systemId}"><a href="#">${subSystemItem.sysName}</a></li>
																</c:forEach>  
															</ul>
														</div>
													</c:when>
                                            		<c:otherwise>
													<span class="label">
														您当前没有防问任何系统的权限,请系统管理员!
													</span>
												</c:otherwise>
                                       	    </c:choose>
                                  </div>
                        </div>
							
</body>
</html>
