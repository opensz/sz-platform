<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title></title>

<%@include file="/commons/include/form.jsp"%>

<link rel="stylesheet" href="${ctx }/jslib/tree/v30/zTreeStyle.css" type="text/css"/>
<script type="text/javascript" src="${ctx }/js/ligerui/plugins/ligerComboBox.js"></script>
<script type="text/javascript" src="${ctx }/jslib/tree/v30/jquery.ztree.core-3.0.min.js"></script>
<script type="text/javascript" src="${ctx }/jslib/tree/v30/jquery.ztree.excheck-3.0.min.js"></script>

<script type="text/javascript">

	$(function() {

		var data = [ {
			id : 1,
			pId : 0,
			name : "北京"
		}, {
			id : 2,
			pId : 0,
			name : "天津"
		}, {
			id : 3,
			pId : 0,
			name : "上海"
		}, {
			id : 6,
			pId : 0,
			name : "重庆"
		}, {
			id : 4,
			pId : 0,
			name : "河北省",
			open : true
		}, {
			id : 41,
			pId : 4,
			name : "石家庄"
		}, {
			id : 42,
			pId : 4,
			name : "保定"
		}, {
			id : 43,
			pId : 4,
			name : "邯郸"
		}, {
			id : 44,
			pId : 4,
			name : "承德"
		}, {
			id : 5,
			pId : 0,
			name : "广东省",
			open : true
		}, {
			id : 51,
			pId : 5,
			name : "广州"
		}, {
			id : 52,
			pId : 5,
			name : "深圳"
		}, {
			id : 53,
			pId : 5,
			name : "东莞"
		}, {
			id : 54,
			pId : 5,
			name : "佛山"
		}, {
			id : 6,
			pId : 0,
			name : "福建省",
			open : true
		}, {
			id : 61,
			pId : 6,
			name : "福州"
		}, {
			id : 62,
			pId : 6,
			name : "厦门"
		}, {
			id : 63,
			pId : 6,
			name : "泉州"
		}, {
			id : 64,
			pId : 6,
			name : "三明"
		} ];
		//树形下拉-带复选框：
		$("#txt1").ligerComboBox( {
			
			width : 180,
			selectBoxWidth : 200,
			selectBoxHeight : 200,
			//valueField : 'id',
			treeLeafOnly : false,
			tree : {
				//ztree setting
				data : {
					simpleData : {
						enable : true
					},
					//扩展的放置data的属性
					data : data
				},
				check : {
					enable : true,
					chkboxType : {
						"Y" : "s",
						"N" : "s"
					}
				}
			}
		});
		// 树形下拉-不带复选框：
		$("#txt2").ligerComboBox( {
			width : 180,
			
			selectBoxWidth : 200,
			selectBoxHeight : 200,
			//valueField : 'id',
			treeLeafOnly : false,
			//ztree setting
			tree : {
				data : {
					simpleData : {
						enable : true
					},
					data : data
				}
			}
		});
		//树形下拉-只选择叶节点：
		$("#txt3").ligerComboBox( {
			width : 180,
			selectBoxWidth : 200,
			selectBoxHeight : 200,
		
			//valueField : 'id',
			tree : {
				data : {
					simpleData : {
						enable : true
					},
					//扩展的放置data的属性
					data : data
				},
				check : {
					enable : true,
					chkboxType : {
						"Y" : "s",
						"N" : "s"
					}
				}
			}
		});

		$("#txt4").ligerComboBox( {
			width : 100,
			data : data

		});


	$("#txt5").ligerComboBox( {
			width : 100,
			isShowCheckBox : true,
			isMultiSelect : true,
			data : data
				

		});

	});
</script>
</head>
<body style="padding: 10px">

<form id="globalTypeForm" method="post" action="add2.xht">

	 树形下拉-不带复选框：
	<br />
	<input type="text" id="txt2" name="txt2"/>
	<br /><br />
	
	
	树形下拉-带复选框：
	<br />
	<input type="text" id="txt1" name="txt1"/>
	
	
	
	
	<br /><br />
	
	
	树形下拉-只选择叶节点：
	<br />
	<input type="text" id="txt3" name="txt3"/>
	
	
	<br /><br />
	
	
	普通下拉-单选
	<br />
	<input type="text" id="txt4" name="txt4"/>
	
	
	
	<br /><br />
	
	
	
	普通下拉-多选
	<br />
	<input type="text" id="txt5" name="txt5"/> 
	
	
	<br />
	<br />
	<br />
	<input type="submit" value="提交"/>
	
</form>
	
</body>
</html>
