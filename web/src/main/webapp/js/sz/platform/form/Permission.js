/**
 * 表单权限。
 * @returns {Permission}
 */
Permission=function(){
	{
		this.FieldsPermission=[];
		this.SubTablePermission=[];
		this.Opinion=[];
	};
	
	/**
	 * 获取默认权限对象。
	 */
	this.getDefaultPermission=function(name,memo){
		var permission={"title":name,"memo":memo,"read": {"type":"everyone","id":"", "fullname":""},"write":{"type":"everyone","id":"", "fullname":""}};
		return permission;
	};
	
	
	/**
	 * 从数据库加载权限，并初始化html表格状态。
	 * 需要传入tableId，formDefId。
	 * 如果是新建表单，权限根据表获取。
	 * 如果是更新表单，权限从表单权限获取。
	 */
	this.loadPermission=function(tableId,formKey){
		var params={tableId:tableId,formKey:formKey};
		this.load("getPermissionByTableFormKey.xht", params);
	};
	
	/**
	 * 从数据库加载权限，并初始化html表格状态。
	 */
	this.loadByNode=function(actDefId, nodeId,formKey){
		var params={actDefId:actDefId,nodeId:nodeId,formKey:formKey};
		this.load("getPermissionByFormNode.xht", params);
	};
	
	this.load=function(url,params){
		
		var _self=this;
		$.post(url, params,function(data){
			var fields =data["field"];
			var tables =data["table"];
			var opinions =data["opinion"];
			
			if(fields!=undefined){
				_self.FieldsPermission=fields;
			}
			if(tables!=undefined){
				_self.SubTablePermission=tables;
			}	
			if(opinions!=undefined){
				_self.Opinion=opinions;
			}

			//字段权限。
			var fieldHtml=_self.getPermission(_self.FieldsPermission,"field");

			//子表权限
			var tableHtml=_self.getPermission(_self.SubTablePermission,"subtable");
			//意见权限。
			var opinionHtml=_self.getPermission(_self.Opinion,"opinion");
			$("#fieldPermission").empty();
			$("#tablePermission").empty();
			$("#opinionPermission").empty();
			
		
			$("#fieldPermission").append(fieldHtml);
			$("#tablePermission").append(tableHtml);
			$("#opinionPermission").append(opinionHtml);
			
			_self.initStatus("fieldPermission");
			_self.initStatus("tablePermission");
			_self.initStatus("opinionPermission");
			
		
		});
		_self.handChange();
		_self.handClick();
	};
	
	/**
	 * 加载完权限表格后，修改控件的状态。
	 */
	this.initStatus=function(id){
		var _self=this;
		$("#" +id).children("tr").each(function(){
			var trObj=$(this);
			//取得下拉狂
			var selReadObj=$("select.r_select",trObj);
			var selWriteObj=$("select.w_select",trObj);
			//值为user,everyone,none,role,orgMgr,pos等。
			//查看下拉框
			var rPermissonType=selReadObj.attr("permissonType");
			var wPermissonType=selWriteObj.attr("permissonType");
			
			//初始化下拉框选中。
			selReadObj.val(rPermissonType);
			selWriteObj.val(wPermissonType);
			
			//是否显示选中的人员或岗位等信息。
			var spanReadObj=$("span[name='r_span']",trObj);
			var spanWriteObj=$("span[name='w_span']",trObj);
			//初始化是否显示选择人员
			_self.showSpan(rPermissonType,spanReadObj);
			_self.showSpan(wPermissonType,spanWriteObj);
		});
	};
	
	/**
	 * 处理下拉框change事件。
	 */
	this.handChange=function(){
		var _self=this;
		$("#fieldPermission,#tablePermission,#opinionPermission").delegate("select.r_select,select.w_select","change",function(){
			var obj=$(this);
			var spanObj=obj.next();
			//当用户权限类型修改时，同时修改span的显示。
			_self.showSpan(obj.val(), spanObj);
			
			var trObj=obj.parents("tr");
			var tbodyObj=obj.parents("tbody");
			//read,write
			var mode=(obj.attr("class")=="r_select")?"read":"write";
			//获取行在表格中的索引
			var idx=tbodyObj.children().index(trObj);
			//权限类型（field,subtable,opinion)
			var permissionType=trObj.attr("type");
			var selType=obj.val();
			_self.changePermission(permissionType,idx,mode,selType,"","");
			var txtObj=$("input",spanObj);
			txtObj.val("");
		});
	};
	
	/**
	 * 修改对应行的权限数据。
	 */
	this.changePermission=function(permissionType,idx,mode,type,ids,names){
		if(idx==-1) return;
		var aryPermission;
		switch(permissionType){
			case "field":
				aryPermission=this.FieldsPermission;
				break;
			case "subtable":
				aryPermission=this.SubTablePermission;
				break;
			case "opinion":
				aryPermission=this.Opinion;
				break;
		}
		var objPermssion=aryPermission[idx];
	
		objPermssion[mode]["type"]=type;
		objPermssion[mode]["id"]=ids;
		objPermssion[mode]["fullname"]=names;
	};
	
	/**
	 * 处理选择人员，岗位，组织，角色点击事件。
	 */
	this.handClick=function(){
		var _self=this;
		$("#fieldPermission,#tablePermission,#opinionPermission").delegate("a.link-get","click",function(){
			var obj=$(this);
			var txtObj=obj.prev();
			var selObj=obj.parent().prev();
			var selType=selObj.val();
			
			var callback = function(ids, names) {
				var trObj=obj.parents("tr");
				var tbodyObj=obj.parents("tbody");
				//read,write
				var mode=obj.attr("mode");
				var idx=tbodyObj.children().index(trObj);
				var permissionType=trObj.attr("type");
				
				_self.changePermission(permissionType,idx,mode,selType,ids,names);
				txtObj.val(names);
			};
			
			switch(selType){
				case "user":
					UserDialog({callback : callback});
					break;
				case "role":
					RoleDialog({callback : callback});
					break;
				case "org":
				case "orgMgr":
					OrgDialog({callback : callback});
					break;
				case "pos":
					PosDialog({callback : callback});
					break;
			}
		});
	};
	
	/**
	 * 是否显示选择框
	 */
	this.showSpan=function(permissionType,spanObj){
		switch(permissionType){
			case "user":
			case "role":
			case "org":
			case "orgMgr":
			case "pos":
				spanObj.show();
				break;
			case "everyone":
			case "none":
				spanObj.hide();
				break;
		}
	};
	
	/**
	 * 根据权限集合和权限类型获取权限的html，代码。
	 */
	this.getPermission=function(aryPermission,type){
		var sb=new StringBuffer();
		
		for(var i=0;i<aryPermission.length;i++){
			var objPermission=aryPermission[i];
			var str=this.getHtml(objPermission, type);
			sb.append(str);
		}
		return sb.toString();
	};
	
	
	/**
	 * 根据权限对象和权限类型（字段，子表，意见）获取一行的显示。
	 */
	this.getHtml=function(permission,type){
		
		var permissionTr = '<tr type="#permissionType">'
			+ '<td>#desc</td>'
			+ '<td>'
			+ '<select class="r_select"  permissonType="#r_type" name="#name"  >'
			+ '<option value="user">用户</option>'
			+ '<option value="role">角色</option>'
			+ '<option value="org">组织</option>'
			+ '<option value="orgMgr">组织负责人</option>'
			+ '<option value="pos">岗位</option>'
			+ '<option value="everyone">所有人</option>'
			+ '<option value="none">无</option>'
			+ '</select>'
			+ '<span name="r_span">'
			+ '<input  type="text"  readonly value="#R_FullName"/>'
			+ '<a href="#" class="link-get" mode="read" ><span class="link-btn">选择</span></a>'
			+ '</span>'
			+ '</td>'
			+ '<td>'
			+ '<select class="w_select" permissonType="#w_type" name="#name"  >'
			+ '<option value="user">用户</option>'
			+ '<option value="role">角色</option>'
			+ '<option value="org">组织</option>'
			+ '<option value="orgMgr">组织负责人</option>'
			+ '<option value="pos">岗位</option>'
			+ '<option value="everyone">所有人</option>'
			+ '<option value="none">无</option>'
			+ '</select>'
			+ '<span name="w_span"><input  type="text" readonly value="#W_FullName"/>'
			+ '<a  href="#" class="link-get" mode="write"><span class="link-btn">选择</span></a>'
			+ '</span>' 
			+ '</td></tr>';
		
		
			var tmp=permissionTr.replaceAll('#name', permission.title)
			.replaceAll('#desc', permission.memo)
			.replaceAll('#r_type', permission.read.type)
			.replaceAll('#w_type', permission.write.type)
			.replaceAll('#R_FullName', permission.read.fullname)
			.replaceAll('#W_FullName', permission.write.fullname)
			.replaceAll('#permissionType', type);
			return tmp;
	};
	
	/**
	 * 获取权限的json字符串。
	 */
	this.getPermissionJson=function(){
		var fieldJson={field:this.FieldsPermission,subtable:this.SubTablePermission,opinion:this.Opinion};
		var jsonStr=JSON.stringify(fieldJson);
		return jsonStr;
	};
	
	/**
	 * 添加权限。
	 */
	this.addPermission=function(name,memo,aryPermission){
		var rtn=this.isPermissionExist(name, aryPermission);
		if(!rtn)
		{
			var obj=this.getDefaultPermission(name, memo);
			aryPermission.push(obj);
			return true;
		}
		return false;
	};
	
	/**
	 * 判断权限在集合中已经存在。
	 */
	this.isPermissionExist=function(name,aryPermission){
		for(var i=0;i<aryPermission.length;i++){
			var obj=aryPermission[i];
			var tmp=obj.title.toLocaleLowerCase();
			name=name.toLocaleLowerCase();
			if(tmp==name){
				return true;
			}
		}
		return false;
	};
	
	/**
	 * 根据名称获取权限。
	 */
	this.getPermissionByName=function(name,aryPermission){
		for(var i=0;i<aryPermission.length;i++){
			var obj=aryPermission[i];
			var tmp=obj.title.toLocaleLowerCase();
			name=name.toLocaleLowerCase();
			if(tmp==name){
				return obj;
			}
		}
		return null;
	};
	
	/**
	 * 添加意见权限。
	 */
	this.addOpinion=function(formName,name){
		var rtn=this.addPermission(formName,name, this.Opinion);
		//意见权限。
		var opinionHtml=this.getPermission(this.Opinion,"opinion");
		$("#opinionPermission").empty().append(opinionHtml);
		this.initStatus("opinionPermission");
		return rtn;
	};
	
	/**
	 * 替换意见权限。
	 * title:"",memo:""
	 */
	this.replaceOpinion=function(originName,curName,curMemo){
		var obj=this.getPermissionByName(originName,this.Opinion);
		obj["title"]=curName;
		obj["memo"]=curMemo;
		var opinionHtml=this.getPermission(this.Opinion,"opinion");
		$("#opinionPermission").empty().append(opinionHtml);
		this.initStatus("opinionPermission");
	};
	
	
};