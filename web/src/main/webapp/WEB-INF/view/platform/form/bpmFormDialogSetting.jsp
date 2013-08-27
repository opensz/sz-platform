<%--
	desc:edit the 通用表单对话框
--%>
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
	<title>编辑 通用表单对话框</title>
	<%@include file="/commons/include/get.jsp" %>		
	<script type="text/javascript">
		$(function() {
				$("#defLayout").ligerLayout({ leftWidth: 270,height: '100%',
				bottomHeight:50,allowLeftCollapse:false,rightWidth:465,allowRightResize:false,centerWidth:20,
			 	allowBottomResize:false,allowRightCollapse:false});
			
				init();	
			}
			
		);
		function init(){
			$("input.treeField").focus(function(){
				curField=$(this);
			});
			resultCheck();
			
		}
		var curField;
		
		function resultCheck(){
			var Id=$("#id").val();			
			if(Id<1) return;
			var returnField=$("#resultfield").val();
			$("input:checkbox[name='treeReturn']:checked").each(function(){
				$(this).removeAttr("checked");
			});
			var strs=returnField.split(",");
			for(var i=0;i<strs.length;i++)
				$("input:text[value='"+strs[i]+"']").next().attr("checked","checked");
		}
		
		function selectTreeField(){   //左边字段选择
			var obj=$("input:radio[name='fieldName']:checked");
			if(curField==null || curField.length==0){
				$.ligerMessageBox.error('提示信息',"请选择右边的输入框!");
				return;
			}
			if(obj.length==0){
				$.ligerMessageBox.error('提示信息',"请选择左边的字段!");
				return;
			}
			
			if(obj.length>0){
				curField.val(obj.val());
			}
		}
		
		function setDisplayField(){
			var objContainer=$("#trDisplayField");
			
			$("input:checkbox[name='fieldName']:checked").each(function(){
				var trObj=$(this).closest("tr");	
				var fieldName=$(this).val();  //id
				var comment=$("input[name='comment']",trObj).val();
				var obj=$("#display" + fieldName);
				if(obj.length==0){
					var tr=getDispalyField(fieldName,comment);
					objContainer.append(tr);
				}
			});
		}
		
		function delRow(obj){
			$(obj).closest("tr").remove();
			
		}
		
		function getDispalyField(fieldName,comment){
			var tr="<tr id='display"+ fieldName +"' name='"+fieldName+"' comment='"+comment+"'>";
			tr+="<td >"+fieldName+"</td>";
			tr+="<td >"+comment+"</td>";
			tr+="<td><a alt='上移' href='#' class='link moveup' onclick='sortUp(this)'>&nbsp;&nbsp;</a>";
			tr+="<a alt='下移' href='#' class='link movedown' onclick='sortDown(this)'>&nbsp;&nbsp;</a>";
			tr+="<a href='#' class='link del'  onclick='delRow(this)' >删除</a></td>";
			tr+="</tr>";
			return tr;
		}
		
		function getConditionField(fieldName,comment,dbType){
			var db=getConditionSelect(dbType,fieldName,comment);			
			var tr="<tr id='condition"+ fieldName +"'>";
			tr+="<td >"+fieldName+"</td>";
			tr+="<td >"+db+"</td>";
			tr+="<td >"+comment+"</td>";
			tr+="<td><a alt='上移' href='#' class='link moveup' onclick='sortUp(this)'></a>";
			tr+="<a alt='下移' href='#' class='link movedown' onclick='sortDown(this)'></a>";
			tr+="<a href='#' class='link del'  onclick='delRow(this)' >删除</a></td>";
			tr+="</tr>";
			return tr;
		}
		
		function getRtnField(fieldName,comment){
			var tr="<tr id='rtn"+ fieldName +"' name='"+fieldName+"' comment='"+comment+"' >";
			tr+="<td >"+fieldName+"</td>";
			tr+="<td >"+comment+"</td>";
			tr+="<td><a alt='上移' href='#' class='link moveup' onclick='sortUp(this)'>&nbsp;&nbsp;</a>";
			tr+="<a alt='下移' href='#' class='link movedown' onclick='sortDown(this)'>&nbsp;&nbsp;</a>";
			tr+="<a href='#' class='link del'  onclick='delRow(this)' >删除</a></td>";
			tr+="</tr>";
			return tr;
		}
		
		function getConditionSelect(dbType,name,comment){
			var sb=new StringBuffer();
			sb.append("<select name='"+name+"' dbType='"+dbType+"' comment='"+comment+"'>");
			switch(dbType){
				case "varchar":
					sb.append("<option value='='>等于</option>");
					sb.append("<option value='like'>LIKE</option>");
					sb.append("<option value='likeEnd'>LIKEEND</option>");
					break;
				case "number":
					sb.append("<option value='='>等于</option>");
					sb.append("<option value='>='>大于等于</option>");
					sb.append("<option value='>'>大于</option>");
					sb.append("<option value='<'>小于</option>");
					sb.append("<option value='<='>小于等于</option>");
					break;
				case "date":
					sb.append("<option value='='>等于</option>");
					sb.append("<option value='>='>大于等于</option>");
					sb.append("<option value='>'>大于</option>");
					sb.append("<option value='<'>小于</option>");
					sb.append("<option value='<='>小于等于</option>");
					break;
				
			}
			
			sb.append("</select>");
			return sb;
		}
		
		
		function setConditionField(){
			var objContainer=$("#trConditionField");
			$("input:checkbox[name='fieldName']:checked").each(function(){
				var trObj=$(this).closest("tr");	
				var fieldName=$(this).val();  //id
				var comment=$("input[name='comment']",trObj).val();
				var dbType=$(this).attr("dbType");
				var obj=$("#condition" + fieldName);
				if(obj.length==0){
					var tr=getConditionField(fieldName,comment,dbType);
					objContainer.append(tr);
				}
			});
		}
		
		function setReturnField(){
			var objContainer=$("#trRtnField");
			$("input:checkbox[name='fieldName']:checked").each(function(){
				var trObj=$(this).closest("tr");	
				var fieldName=$(this).val();  //id
				var comment=$("input[name='comment']",trObj).val();
				var obj=$("#rtn" + fieldName);
				if(obj.length==0){
					var tr=getRtnField(fieldName,comment);
					objContainer.append(tr);
				}
			});
		}
		var displayStr="";
		var conditionStr="";
		var rtnStr="";		
		
		function buildTreeJson(){
			var treeId=$("#treeId").val();
			var parentId=$("#parentId").val();
			var displayName=$("#displayName").val();
			displayStr="{\"id\":\""+treeId+"\",\"pid\":\""+parentId+"\",\"displayName\":\""+displayName+"\"}";
			$("input:checkbox[name='treeReturn']:checked").each(function(){
				var prevObj=$(this).prevUntil();   
				rtnStr+=prevObj.val()+",";
			});
			rtnStr=rtnStr.substring(0, rtnStr.length-1);
		}
		
		function buildListJson(){
			
			$("#trDisplayField").children().each(function(){
				var fieldName=$(this).attr("name");
				var comment=$(this).attr("comment");
				displayStr+="{\"field\":\""+fieldName+"\",\"comment\":\""+comment+"\"},";
			});
			
			$("#trConditionField").find("select").each(function(){
				var fieldName=$(this).attr("name");
				var comment=$(this).attr("comment");
				var condition=$(this).attr("value");
				var dbType=$(this).attr("dbType");
				conditionStr+="{\"field\":\""+fieldName+"\",\"comment\":\""+comment+"\",\"condition\":\""+condition+"\",\"dbType\":\""+dbType+"\"},";
			});

			$("#trRtnField").children().each(function(){
				var fieldName=$(this).attr("name");
				var comment=$(this).attr("comment");
				rtnStr+="{\"field\":\""+fieldName+"\",\"comment\":\""+comment+"\"},";
			});
			
			if(displayStr.length>0){
				displayStr="["+displayStr.substring(0,displayStr.length-1)+"]";
			}
			 	
			if(conditionStr.length>0){
				conditionStr="["+conditionStr.substring(0,conditionStr.length-1)+"]";
			}
				
			if(rtnStr.length>0){
				rtnStr="["+rtnStr.substring(0, rtnStr.length-1)+"]";
			}
		}
		
	    function selectForm(){
	    	$.ligerMessageBox.confirm('提示信息','确认选择吗？',function(rtn) {
				if(!rtn) return;
								
				if($("input.treeField").length>0) //如果是树状的只取不大于3个的返回值
					buildTreeJson();
				else
					buildListJson();
				
				var rerurnlist= new Array(displayStr,conditionStr,rtnStr); 
				window.returnValue=rerurnlist;
		    	window.close();
			});
	    }
	    
		function sortUp(obj) {
			var thisTr = $(obj).parents("tr");
			var prevTr = $(thisTr).prev();
			if(prevTr){
				thisTr.insertBefore(prevTr);
			}
		};
	    
	
		function sortDown(obj) {
			var thisTr = $(obj).parents("tr");
			var nextTr = $(thisTr).next();
			if(nextTr){
				thisTr.insertAfter(nextTr);
			}
		};
	    
	</script>
	<style type="text/css">
		body{ padding:2px; margin:0; }
		div.fieldContainer{border:1px solid #BED5F3;margin-top: 3px;height:143px;}
		div.content{height:110px;overflow: auto;}
		ul.btnContainer{text-align: center;margin-top: 60px;}
		li.btn{margin-top: 3px;height:143px;line-height:143px; }
		li.btnTree{margin-top: 3px;height:40px;line-height:40px; }
	</style>
	
</head>
<body>
		<div id="defLayout" >
	            <div position="left" title="获取字段列表" style="overflow: auto;width: 300px;height:450px;">
					<table cellpadding="1" class="table-grid table-list" cellspacing="1">
						<tr>
							<th></th>
							<th>字段</th>
							<th>注释</th>
						</tr>
						<c:forEach var="col" items="${tableModel.columnList }" varStatus="status">
						<c:set var="clsName"  ><c:choose><c:when test="${status.index%2==0}">odd</c:when><c:otherwise>even</c:otherwise></c:choose> </c:set>
						<tr class="${clsName}">
							<td>
								<c:choose>
	            					<c:when test="${style==0 }">
										<input type="checkbox" name="fieldName"  class="pk"  value="${col.name }"  dbType="${col.columnType }">
									</c:when>
									<c:otherwise>
										<input type="radio" name="fieldName" class="pk"  value="${col.name }" id="${col.name }">
									</c:otherwise>
								</c:choose>
							</td>
							<td nowrap="nowrap">
								${col.name }
							</td>
							<td>
								<input type="text" name="comment" class="inputText" value="${col.comment }">
							</td>
						</tr>
						</c:forEach>
					</table>
	            </div>
	            <div position="center" >
	            	<c:choose>
	            		<c:when test="${style==0 }">
	            			<ul class="btnContainer">
			          			<li class="btn">
			          				 <a href='#' class='button'  onclick="setDisplayField()" ><span >=></span></a>
			          			</li>
			          			<li class="btn">
			          				<a href='#' class='button'  onclick="setConditionField()" ><span >=></span></a>
			          			</li>
			          			<li class="btn">
			          				<a href='#' class='button'  onclick="setReturnField()" ><span >=></span></a>
			          			</li>
			          		</ul>
	            		</c:when>
	            		<c:otherwise>
	            			<ul class="btnContainer">
			          			<li class="btnTree">
			          				 <a href='#' class='button'  onclick="selectTreeField()" ><span >==></span></a>
			          			</li>
			          			
			          		</ul>
	            		</c:otherwise>
	            	</c:choose>
	            	
	          		
	            </div>  
	            <div id="fieldSetting" position="right"  title="字段设置">
	            	<c:choose>
	            		<c:when test="${style==0 }">
			          		<div class="fieldContainer">
			          			<div class="header">
			          				显示的字段
			          			</div>
			          			<div class="content">
			          				<table cellpadding="1" class="table-grid table-list" cellspacing="1">
				          				<tr>
				          					<th>
				          						字段名
				          					</th>
				          					<th>
				          						显示名
				          					</th>
				          					<th>
				          						管理
				          					</th>
				          				</tr>
				          				<tbody id="trDisplayField">
				          					<c:forEach items="${ bpmFormDialog.displayList}" var="field">
				          						<tr id='display${field.fieldName}' name='${field.fieldName}' comment='${field.comment}'>
				          							<td>${field.fieldName}</td>
				          							<td>${field.comment}</td>
				          							<td>
				          							<a alt='上移' href='#' class='link moveup' onclick='sortUp(this)'>&nbsp;</a>
				          							<a alt='下移' href='#' class='link movedown' onclick='sortDown(this)'>&nbsp;</a>
				          							<a href='#' class='link del'  onclick='delRow(this)' >删除</a>
				          							</td>
				          						</tr>
				          					</c:forEach>
				          				</tbody>
				          			</table>
			          			</div>
			          			
			          		</div>
			          		<div class="fieldContainer">
			          			<div class="header">
			          				条件字段
			          			</div>
			          			<div class="content">
				          			<table cellpadding="1" class="table-grid table-list" cellspacing="1">
				          				<tr>
				          					<th>
				          						字段名
				          					</th>
				          					<th>
				          						条件
				          					</th>
				          					<th>
				          						显示名
				          					</th>
				          					<th>
				          						管理
				          					</th>
				          				</tr>
				          				<tbody id="trConditionField">
				          					<c:forEach items="${ bpmFormDialog.conditionList}" var="field">
				          						<tr id='condition${field.fieldName}' name='${field.fieldName}' comment='${field.comment}'>
				          							<td>${field.fieldName}</td>
				          							<td>
				          								<select name='${field.fieldName}' dbType='${field.fieldType}' comment='${field.comment}' >
				          								<c:choose>
				          									<c:when test="${field.fieldType=='varchar'}">
					          									<option value='=' <c:if test="${field.condition=='='}">selected</c:if> >等于</option>
																<option value='like' <c:if test="${field.condition=='like'}">selected</c:if>>LIKE</option>
																<option value='likeEnd' <c:if test="${field.condition=='likeEnd'}">selected</c:if>>LIKEEND</option>
				          									</c:when>
				          									<c:when test="${field.fieldType=='number'}">
				          										<option value='=' <c:if test="${field.condition=='='}">selected</c:if>>等于</option>
																<option value='>=' <c:if test="${field.condition=='>='}">selected</c:if> >大于等于</option>
																<option value='>' <c:if test="${field.condition=='>'}">selected</c:if> >大于</option>
																<option value='<' <c:if test="${field.condition=='<'}">selected</c:if> >小于</option>
																<option value='<=' <c:if test="${field.condition=='<='}">selected</c:if>>小于等于</option>
				          									</c:when>
				          									<c:otherwise>
				          										<option value='=' <c:if test="${field.condition=='='}">selected</c:if>>等于</option>
																<option value='>=' <c:if test="${field.condition=='>='}">selected</c:if> >大于等于</option>
																<option value='>' <c:if test="${field.condition=='>'}">selected</c:if> >大于</option>
																<option value='<' <c:if test="${field.condition=='<'}">selected</c:if> >小于</option>
																<option value='<=' <c:if test="${field.condition=='<='}">selected</c:if> >小于等于</option>
				          									</c:otherwise>
				          								</c:choose>
				          								</select>
													</td>
				          							<td>${field.comment}</td>
				          							<td>
				          							<a alt='上移' href='#' class='link moveup' onclick='sortUp(this)'></a>
				          							<a alt='下移' href='#' class='link movedown' onclick='sortDown(this)'></a>
				          							<a href='#' class='link del'  onclick='delRow(this)' >删除</a>
				          							</td>
				          						</tr>
				          					</c:forEach>
				          				</tbody>
				          			</table>
			          			</div>
			          		</div>
			          		<div class="fieldContainer">
			          			<div class="header">
			          				返回字段
			          			</div>
			          			<div class="content">
				          			<table cellpadding="1" class="table-grid table-list" cellspacing="1">
				          				<tr>
				          					<th>
				          						字段名
				          					</th>
				          					<th>
				          						显示名
				          					</th>
				          					<th>
				          						管理
				          					</th>
				          				</tr>
				          				<tbody id="trRtnField">
				          					<c:forEach items="${ bpmFormDialog.returnList}" var="field">
				          						<tr id='rtn${field.fieldName}' name='${field.fieldName}' comment='${field.comment}'>
				          							<td>${field.fieldName}</td>
				          							<td>${field.comment}</td>
				          							<td>
				          							<a alt='上移' href='#' class='link moveup' onclick='sortUp(this)'>&nbsp;</a>
				          							<a alt='下移' href='#' class='link movedown' onclick='sortDown(this)'>&nbsp;</a>
				          							<a href='#' class='link del'  onclick='delRow(this)' >删除</a>
				          							</td>
				          						</tr>
				          					</c:forEach>
				          				</tbody>
				          			</table>
				          		</div>
			          		</div>
			          	</c:when>
			          	<c:otherwise>
			          	
			          		<table class="table-detail" cellpadding="0" cellspacing="0" border="0">
								<tr>
									<th width="20%">ID: </th>
									<td>
										<input type="text" id="treeId"  name="treeId"  class="inputText treeField" value="${ bpmFormDialog.treeField.id}"/>								
										<input type="checkbox" name="treeReturn" checked="checked" />返回
									</td>
								</tr>
								<tr>
									<th width="20%">父ID: </th>
									<td><input type="text" id="parentId" name="parentId"  class="inputText treeField" value="${ bpmFormDialog.treeField.pid}"/>
										<input type="checkbox" name="treeReturn"/>返回
									</td>
								</tr>
								<tr>
									<th width="20%">显示名称: </th>
									<td>
										<input type="text" id="displayName" name="displayName"  class="inputText treeField" value="${bpmFormDialog.treeField.displayName}"/>
										<input type="checkbox" name="treeReturn" checked="checked" />返回
										
									</td>
								</tr>
								
							</table>
							
			          	</c:otherwise>
			          </c:choose>
	            </div>
	            <div position="bottom" class="bottom" style="padding-top: 15px;">
					  <a href='#' class='button'  onclick="selectForm()" ><span class="icon ok"></span><span >选择</span></a>
			  		<a href='#' class='button' style='margin-left:10px;' onclick="window.close()"><span class="icon cancel"></span><span >取消</span></a>
				</div>
				
				<input type="hidden" name="resultfield" id="resultfield" value="${bpmFormDialog.resultfield}"/>
				<input type="hidden" name="id" id="id" value="${bpmFormDialog.id}"/>
       	  </div>
       	 
</body>
</html>
