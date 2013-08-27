if (typeof FormUtil == 'undefined') {
	FormUtil = {};
}

/**
 * 初始化表单tab。
 */
FormUtil.initTab=function(){
	var amount=$("#formTab").length;
	if(amount>0){
		$("#formTab").ligerTab();
	}
};

/**
 * 初始化日历控件。
 */
FormUtil.initCalendar=function(){
	
	$("body").delegate("input.Wdate", "click",function(){
		var fmt=$(this).attr("dateFmt");
		WdatePicker({el:this,dateFmt:fmt});
	});
};


/**
 * 绑定对话框。 按钮或者文本框定义如下：
 * dialog="{name:'globalType',fields:[{src:'TYPENAME',target:'m:mainTable:name'},{src:'TYPEID',target:'m:mainTable:address'}]}"
 * 
 * name:对话框的别名 fields：为字段映射，可以有多个映射。 src：对话框返回的字段。 target：需要映射的控件名称。
 */
FormUtil.initCommonDialog=function(){
	$("body").delegate("[dialog]", "click", function(){
		var obj=$(this);
		var dialogJson=obj.attr("dialog");
		var json=eval("("+dialogJson+")" );
		var name=json.name;

		var fields=json.fields;
		var parentObj=obj.closest("[formtype]");
		var isGlobal=parentObj.length==0;
		
		CommonDialog(name,function(data){
			var len=data.length;
			for(var i=0;i<fields.length;i++){
				var json=fields[i];
				var src=json.src;
				var targets=json.target.split(','),target;
				while(target=targets.pop()){
					if(!target)return;
					var filter="[name$='"+target+"']";
					//在子表中选择
					var targetObj=isGlobal?$(filter):$(filter,parentObj);
					
					//单选
					if(len==undefined){
						targetObj.val(data[src]);
					}
					//多选
					else{
						for(var k=0;k<len;k++){
							var dataJson=data[k];
							if(json.data){
								json.data.push(dataJson[src]);
							}
							else{
								var tmp=[];
								tmp.push(dataJson[src]);
								json.data=tmp;
							}
						}
						targetObj.val(json.data.join(","));
					}
				}				
			}
		});
	});
};


/**
 * 显示选择器对话框。
 * obj 按钮控件
 * fieldName 字段名称
 * type :选择器类型。
 * 1.单用户选择器。2.角色选择器。3.组织选择器.4.岗位选择器。5.人员选择器(多选)
 */
FormUtil.handSelector=function(){
	$("body").delegate("a.link.user", "click",function(){
		var obj=$(this);
		var readOnly=obj.attr("readonly");
		if(readOnly!="readonly"){
			FormUtil.Selector(obj,1);
		}
		
	});
	var userArr=$("a[class='link user']");
	var json={id:userId,name:userName};
	for(var i=0;i<userArr.length;i++){
		var obj=$(userArr[i]);
		var readOnly=obj.attr("readonly");
		var isdefault=obj.attr("isdefault");
		if(readOnly!="readonly" && isdefault!="false"){
			FormUtil.Selector(obj,1,json);
		}
		
	}
	
	$("body").delegate("a.link.dept", "click",function(){
		var obj=$(this);
		var readOnly=obj.attr("readonly");
		if(readOnly!="readonly"){
			FormUtil.Selector(obj,16);
		}
		
	});
	
	$("body").delegate("a.link.role", "click",function(){
		var obj=$(this);
		var readOnly=obj.attr("readonly");
		if(readOnly!="readonly"){
			FormUtil.Selector(obj,2);
		}
		
	});
	
	$("body").delegate("a.link.org", "click",function(){
		var obj=$(this);
		var readOnly=obj.attr("readonly");
		if(readOnly!="readonly"){
			FormUtil.Selector(obj,3);
		}
		
	});
	
	$("body").delegate("a.link.position", "click",function(){
		var obj=$(this);
		var readOnly=obj.attr("readonly");
		if(readOnly!="readonly"){
			FormUtil.Selector(obj,4);
		}
		
	});
	
	$("body").delegate("a.link.users", "click",function(){
		var obj=$(this);
		var readOnly=obj.attr("readonly");
		if(readOnly!="readonly"){
			FormUtil.Selector(obj,5);
		}
		
	});
	
	//客户选择框
	$("body").delegate("a.link.customer", "click",function(){
		var obj=$(this);
		var readOnly=obj.attr("readonly");
		if(readOnly!="readonly"){
			FormUtil.Selector(obj,16);
		}
		
	});
	//项目选择框
	$("body").delegate("a.link.project", "click",function(){
		var obj=$(this);
		var readOnly=obj.attr("readonly");
		if(readOnly!="true"){
			FormUtil.Selector(obj,17);
		}
		
	});
	//合同选择框
	$("body").delegate("a.link.contract", "click",function(){
		var obj=$(this);
		var readOnly=obj.attr("readonly");
		if(readOnly!="readonly"){
			FormUtil.Selector(obj,18);
		}
		
	});
	//机房
	$("body").delegate("a.link.asset_jf", "click",function(){
		var obj=$(this);
		var readOnly=obj.attr("readOnly");
		if(readOnly!="true"){
			FormUtil.Selector(obj,20);
		}
		
	});
	
	//供应商选择框
	$("body").delegate("a.link.supplier", "click",function(){
		var obj=$(this);
		var readOnly=obj.attr("readonly");
		if(readOnly!="readonly"){
			FormUtil.Selector(obj,23);
		}
		
	});
	//资产选择框
	$("body").delegate("a.link.asset", "click",function(){
		var obj=$(this);
		var readOnly=obj.attr("readonly");
		if(readOnly!="readonly"){
			FormUtil.Selector(obj,25);
		}
	});
	
	//链路选择框
	$("body").delegate("a.link.linkInfo", "click",function(){
		var obj=$(this);
		var readOnly=obj.attr("readonly");
		if(readOnly!="readonly"){
			FormUtil.Selector(obj,24);
		}
		
	});
	
	$("body").delegate("a.link.asset_vlan", "click",function(){
		var obj=$(this);
		var readOnly=obj.attr("readonly");
		if(readOnly!="readonly"){
			var fieldName=obj.attr("name");
			var parm_ss=obj.attr("parm_ss");
			var sta="ss";
			if(parm_ss==null || parm_ss!="true"){
				sta="design";
			}
			var parent=obj.parent();
			var idFilter="input[name='"+fieldName+"ID']";
			var nameFilter="input[name='"+fieldName+"']";
			var inputId=$(idFilter,parent);
			var inputName=$(nameFilter,parent);
			AssetVlanDialog({obj:obj,sta:sta,callback :function(ids, names){if(inputId.length>0){
				inputId.val(ids);
			};
			
			inputName.val(names);}});
		}
		
	});
	
	$("body").delegate("a.link.asset_jhj", "click",function(){
		var obj=$(this);
		var readOnly=obj.attr("readonly");
		if(readOnly!="readonly"){
			var fieldName=obj.attr("name");
			var parm_ss=obj.attr("parm_ss");
			var sta="ss";
			if(parm_ss==null || parm_ss!="true"){
				sta="design";
			}
			var parent=obj.parent();
			var idFilter="input[name='"+fieldName+"ID']";
			var nameFilter="input[name='"+fieldName+"']";
			var inputId=$(idFilter,parent);
			var inputName=$(nameFilter,parent);
			AssetJhjDialog({obj:obj,sta:sta,callback :function(ids, names,parents){
					if(inputId.length>0){
						inputId.val(ids);
					};
					inputName.val(names);
				}
			});
		}
	});
	
	$("body").delegate("a.link.asset_gdsf", "click",function(){
		var obj=$(this);
		var readOnly=obj.attr("readonly");
		if(readOnly!="readonly"){
			var fieldName=obj.attr("name");
			var parm_ss=obj.attr("parm_ss");
			var sta="ss";
			if(parm_ss==null || parm_ss!="true"){
				sta="design";
			}
			var parent=obj.parent();
			var idFilter="input[name='"+fieldName+"ID']";
			var nameFilter="input[name='"+fieldName+"']";
			var inputId=$(idFilter,parent);
			var inputName=$(nameFilter,parent);
			AssetGdsfDialog({obj:obj,sta:sta,callback :function(ids, names,parents){if(inputId.length>0){
				inputId.val(ids);
			};
			inputName.val(names);}});
		}
	});
	
	$("body").delegate("a.link.asset_routerport", "click",function(){
		var obj=$(this);
		var readOnly=obj.attr("readonly");
		if(readOnly!="readonly"){
			var fieldName=obj.attr("name");
			var parm_ss=obj.attr("parm_ss");
			var sta="ss";
			if(parm_ss==null || parm_ss!="true"){
				sta="design";
			}
			var parent=obj.parent();
			var idFilter="input[name='"+fieldName+"ID']";
			var nameFilter="input[name='"+fieldName+"']";
			var inputId=$(idFilter,parent);
			var inputName=$(nameFilter,parent);
			AssetRouterPortDialog({obj:obj,sta:sta,callback :function(ids, names,parents){if(inputId.length>0){
				inputId.val(ids);
			};
			inputName.val(names);}});
		}
	});
	
	$("body").delegate("a.link.asset_ip", "click",function(){
		var obj=$(this);
		var readOnly=obj.attr("readonly");
		if(readOnly!="readonly"){
			var fieldName=obj.attr("name");
			var parm_ss=obj.attr("parm_ss");
			var sta="ss";
			if(parm_ss==null || parm_ss!="true"){
				sta="design";
			}
			var parent=obj.parent();
			var idFilter="input[name='"+fieldName+"ID']";
			var nameFilter="input[name='"+fieldName+"']";
			var inputId=$(idFilter,parent);
			var inputName=$(nameFilter,parent);
			AssetIPDialog({obj:obj,sta:sta,callback :function(ids, names,parents){if(inputId.length>0){
				inputId.val(ids);
			};
			inputName.val(names);}});
		}
	});

};

FormUtil.Selector=function(obj,type,json){
	var fieldName=obj.attr("name");
	var parent=obj.parent();
	var idFilter="input[name='"+fieldName+"ID']";
	var nameFilter="input[name='"+fieldName+"']";
	var inputId=$(idFilter,parent);
	var inputName=$(nameFilter,parent);
	var readOnly=obj.attr("readonly");
	
	switch(type){
		//单用户选择器
		case 1:
			//默认值设置
		    if(json!=null && readOnly!="readonly"){
		    	if(inputName.val()==""){
		    		inputId.val(json.id);
			    	inputName.val(json.name);	
		    	}
		    }
		    else{
		    	UserDialog({isSingle:true,callback :function(ids, names){
		    		if(readOnly!="readonly"){
						if(inputId.length>0){
							inputId.val(ids);
						}
						inputName.val(names);
					}
		    	}
		    	});
		    }
			break;
		//角色选择器
		case 2:
			RoleDialog({callback :function(ids, names){
				if(inputId.length>0){
					inputId.val(ids);
				};
				inputName.val(names);}});
			break;
		//组织选择器
		case 3:
			OrgDialog({type:'org',callback :function(ids, names){
				if(inputId.length>0){
					inputId.val(ids);
				};
				inputName.val(names);}});
			break;
		//岗位选择器
		case 4:
			PosDialog({callback :function(ids, names){if(inputId.length>0){
				inputId.val(ids);
			};
			inputName.val(names);}});
			break;
		//人员选择器(多选)
		case 5:
			UserDialog({callback :function(ids, names){if(inputId.length>0){
				inputId.val(ids);
			};
			inputName.val(names);}});
			break;
		//部门选择器
		case 16:
			OrgDialog({type:'dept',callback :function(ids, names){if(inputId.length>0){
				inputId.val(ids);
			};
			inputName.val(names);}});
			break;
		//项目选择器
		case 17:
			ProjectDialog({callback :function(ids, names){if(inputId.length>0){
				inputId.val(ids);
			};
			inputName.val(names);}});
			break;
		//合同选择器
		case 18:
			ContractDialog({callback :function(ids, names,codes,signedTimes,expirationDates){if(inputId.length>0){
				inputId.val(ids);
			};
			inputName.val(names);
			jhContractParams(parent,codes,signedTimes,expirationDates);
			}});
			break;
		//机房
		case 20:
			AssetJfDialog({obj:obj,callback :function(ids, names){if(inputId.length>0){
				inputId.val(ids);
			};
			inputName.val(names);}});
			break;
		//供应商
		case 23:
			SupplierDialog({obj:obj,callback :function(ids, names){if(inputId.length>0){
				inputId.val(ids);
			};
			inputName.val(names);}});
			break;
		//链路
		case 24:
			linkInfoDialog({obj:obj,callback :function(ids, names){if(inputId.length>0){
				
				inputId.val(ids);
			};
			inputName.val(names);}});
			break;
		//资产
		case 25:
			AssetInfoDialog({obj:obj,callback :function(ids, names){if(inputId.length>0){
				inputId.val(ids);
			};
			inputName.val(names);}});
			break;
	   
	}
};


/**
 * 
 */
$(function(){
	//初始化表单tab
	FormUtil.initTab();
	//初始化日期控件。
	FormUtil.initCalendar();
	//附件初始化
	AttachMent.init();
	//Office控件初始化。
	OfficePlugin.init();
	
	FormUtil.handSelector();
	
	//绑定对话框。
	FormUtil.initCommonDialog();
});

/**
 * 合同其他属性回调
 * @param codes
 * @param signedTimes
 * @param expirationDates
 */
function jhContractParams(parent,codes,signedTimes,expirationDates){
	
	
}