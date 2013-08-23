/**
 * 联动的JS  依赖 Jquery
 * 
 * sm.zhang  2012-8-31
 */
$.linkCombo=function (tag1,tag2,require,callback){
	
	var parent=$("select[name='"+tag1+"']");
	var target=$("select[name='"+tag2+"']");
	
	if(parent == null || parent == undefined ){
		$.error(tag1+", is null");
		return;
	}
	if(target == null || target == undefined ){
		$.error(tag2+", is null");
		return;
	}
	if(require == undefined){
		require = false;
	}
	
	//接下来绑定事件
	parent.on("change",function(){
		var url=$(this).attr("url");
		var value=$(this).val();
		
		if(url == undefined){
			$.error("url is undefined in select tag");
		}
		if(value == null || value == -1 || value == ""){
			target.empty();
			target.append("<option value='' index=-1>--请选择--</option>");
			return;
		}
		var pathName = document.location.pathname;
		var index = pathName.substr(1).indexOf("/");
		var result = pathName.substr(0,index+1);
		
		url=result+url+"?query="+value;
		//ajax request
		$.ajax({
			url:url,			//url
			dataType:'json',
			type: "POST",
			success:function(data, textStatus, jqXHR){
				//删除所有子节点 option
				target.empty();
				//如果未必填
				if(!require){
					 target.append("<option value='' index=-1>--请选择--</option>");
				}
				
				//循环 加入节点
				$.each(data, function(i,item){
					var label=item.label;
					var value=item.value;
				    //加入新的节点
				    target.append("<option value='"+value+"' index='"+i+"'>"+label+"</option>");
				  });
				if(callback !=undefined){
					callback(value);
				}
				
			},
			error:function(XMLHttpRequest, textStatus, errorThrown){
				$.error("出现错误:"+errorThrown);
			}
		});
	});
};
/**
 * 多个联动
 */
$.linkCombo2=function (self,object1,object2){
	var tag1=object1.tag;
	var url1=object1.url;
	
	var tag2=object2.tag;
	var url2=object2.url;
	
	var parent=$("select[name='"+self+"']");
	var target1=$("select[name='"+tag1+"']");
	var target2=$("select[name='"+tag2+"']");
	
	parent.attr("url",url1);
	$.linkCombo(self,tag1,false,function(value){
		var url=url2;
		
		if(url == undefined){
			$.error("url is undefined in select tag");
		}
		if(value == null || value == -1 || value == ""){
			target2.empty();
			target2.append("<option value='' index=-1>--请选择--</option>");
			return;
		}
		var pathName = document.location.pathname;
		var index = pathName.substr(1).indexOf("/");
		var result = pathName.substr(0,index+1);
		
		url=result+url+"?query="+value;
		//ajax request
		$.ajax({
			url:url,			//url
			dataType:'json',
			type: "POST",
			success:function(data, textStatus, jqXHR){
				//删除所有子节点 option
				target2.empty();
				//如果未必填
				 target2.append("<option value='' index=-1>--请选择--</option>");
				
				//循环 加入节点
				$.each(data, function(i,item){
					var label=item.label;
					var value=item.value;
				    //加入新的节点
					target2.append("<option value='"+value+"' index='"+i+"'>"+label+"</option>");
				  });
				
			},
			error:function(XMLHttpRequest, textStatus, errorThrown){
				$.error("出现错误:"+errorThrown);
			}
		});
		
	});
}