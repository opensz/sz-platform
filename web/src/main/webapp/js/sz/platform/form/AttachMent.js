/**
 * 附件管理。
 * @returns {AttachMent}
 */
if (typeof AttachMent == 'undefined') {
	AttachMent = {};
}

/**
 * 添加附件数据。
 * @param obj 按钮。
 * @param fieldName 字段名称
 */
AttachMent.addFile=function(obj){	
	var inputObj=$(obj);
	var fieldName=inputObj.attr("field");
	var parent=inputObj.parent();

	var rights=parent.attr("right");
	var divName="div.attachement";
	var inputName="textarea[name='" +fieldName +"']";
	//获取div对象。
	var divObj=$(divName,parent);
	var inputJson=$(inputName,parent);
	
	var aryJson=AttachMent.getFileJsonArray(divObj);
	//文件选择器
	FlexUploadDialog({isSingle:false,callback:function (fileIds,fileNames,filePaths,extPaths){
		if(fileIds==undefined || fileIds=="") return ;
		var aryFileId=fileIds.split(",");
		var aryName=fileNames.split(",");
		var aryPath=filePaths.split(",");
		var aryExtPath=extPaths.split(",");
	
		for(var i=0;i<aryFileId.length;i++){
			var name=aryName[i] +"." +aryExtPath[i];
			AttachMent.addJson(aryFileId[i],name,aryPath[i]  ,aryJson);
		}
		//获取json
		var json=JSON.stringify(aryJson);
		var html=AttachMent.getHtml(aryJson,rights);
		divObj.empty();
		divObj.append($(html));
		inputJson.val(json);	
	}});
};

/**
 * 删除附件
 * @param obj 删除按钮。
 */
AttachMent.delFile=function(obj){
	var inputObj=$(obj);
	var parent=inputObj.parent();
	var divObj=parent.parent();
	var spanObj=$("span[name='attach']",parent);
	var divContainer=divObj.parent();
	var fileId=spanObj.attr("fileId");
	var aryJson=AttachMent.getFileJsonArray(divObj);
	AttachMent.delJson(fileId,aryJson);
	var json=JSON.stringify(aryJson);
	var inputJsonObj=$("textarea",divContainer);
	//设置json
	inputJsonObj.val(json);
	//删除span
	parent.remove();
};

/**
 * 初始化表单的附件字段数据。
 */
AttachMent.init=function(){

	var divContainer=$("div[name='div_attachment_container']");
	
	divContainer.each(function(i){
		//w:可编辑，r：可看,no:无权限。
		var rights=divContainer.attr("right");
		//没有权限
		if(rights!="w" && rights!="r"){
			divContainer.remove();
		}
		else{
		
			var objDiv=$(this);
			//选择按钮处理
			//如果只读，删除选择按钮。
			var divAttachment=$(".selectFile",objDiv);
			if(rights=="r"){
				divAttachment.remove();
			}
			var jsonStr=$("textarea",objDiv).val();
			//json数据为空。
			if(jsonStr=="" || jsonStr==undefined ) return;
			var jsonObj=jQuery.parseJSON(jsonStr);
			var html=AttachMent.getHtml(jsonObj,rights);
			var divAttachment=$("div.attachement",objDiv);
			divAttachment.empty();
			divAttachment.append($(html));
		}
	});
};

/**
 * 获取文件的html。
 * @param aryJson
 * @returns {String}
 */
AttachMent.getHtml=function(aryJson,rights){	
	var str="";
	var template="";
	var templateW="<span><span fileId='#fileId#' name='attach' file='#file#'><a class='attachment' target='_blank' href='#path#'>#name#</a></span><a href='#' onclick='AttachMent.delFile(this);' class='link cancel'> </a></span>";
	var templateR="<span><span fileId='#fileId#' name='attach' file='#file#'><a class='attachment' target='_blank' href='#path#'>#name#</a></span>&nbsp;</span>";
	
	if(rights=="w"){
		template=templateW;
	}
	else{
		template=templateR;
	}
	for(var i=0;i<aryJson.length;i++){
		var obj=aryJson[i];
		var id=obj.id;
		var name=obj.name;
		var path=__ctx +"/"+ obj.path;		
		
		var file=id +"," + name +"," + path;
		var tmp=template.replace("#file#",file).replace("#path#",path).replace("#name#", name).replace("#fileId#", id);
		str+=tmp;
	}
	return str;
};

/**
 * 添加json。
 * @param fileId
 * @param name
 * @param path
 * @param aryJson
 */
AttachMent.addJson=function(fileId,name,path,aryJson){
	var rtn=AttachMent.isFileExist(aryJson,fileId);
	if(!rtn){
		var obj={id:fileId,name:name,path:path};
		aryJson.push(obj);
	}
};

/**
 * 删除json。
 * @param fileId 文件ID。
 * @param aryJson 文件的JSON。
 */
AttachMent.delJson=function(fileId,aryJson){
	for(var i=aryJson.length-1;i>=0;i--){
		var obj=aryJson[i];
		if(obj.id==fileId){
			aryJson.splice(i,1);
		}
	}
};

/**
 * 判断文件是否存在。
 * @param aryJson
 * @param fileId
 * @returns {Boolean}
 */
AttachMent.isFileExist=function(aryJson,fileId){
	
	for(var i=0;i<aryJson.length;i++){
		var obj=aryJson[i];
		if(obj.id==fileId){
			return true;
		}
	}
	return false;
};

/**
 * 取得文件json数组。
 * @param divObj
 * @returns {Array}
 */
AttachMent.getFileJsonArray=function(divObj){
	var aryJson=[];
	var arySpan=$("span[name='attach']",divObj);
	arySpan.each(function(i){
		var obj=$(this);
		var file=obj.attr("file");
		var aryFile=file.split(",");
		var obj={id:aryFile[0],name:aryFile[1],path:aryFile[2]};
		aryJson.push(obj);
	});
	return aryJson;
};

