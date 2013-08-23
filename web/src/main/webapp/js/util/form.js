Namespace.register("org.sz.form");  
/**
 * 动态创建form类。
 * 使用方法如下：
 * var frm=new org.sz.form.Form();
 * frm.creatForm("表单名","提交到的页面");
 *清除表单元素
 * frm.clearFormEl();
 * 添加需要提交的表单元素。
 * frm.addFormEl("name","value");
 * 表单提交
 * frm.submit();
 */
org.sz.form.Form=function(){
	this.creatForm=function(formName,action)
	{
		var  frm=document.getElementById(formName);
		if(frm==null || frm==undefined){
			frm = document.createElement("FORM");  
			document.body.appendChild(frm);  
		}
		frm.action=action;
		this.form=frm;
	};
	
	this.clearFormEl=function()
	{
		var childs=this.form.childNodes;
		for(var i=childs.length;i>=0;i--){
			var node=childs[i];
			this.form.removeNode(node);
		}
	};
	
	this.addFormEl=function(name,value){
		 var el = document.createElement("input");  
		 el.setAttribute("name",name);  
		 el.setAttribute("type","hidden");  
		 el.setAttribute("value",value);  
		 this.form.appendChild(el);  
	};
	
	this.submit=function(){
		this.form.submit();
	};
};

    
/**
 * ResponseObject对象
 * @param data
 */
org.sz.form.ResultMessage = function(data) {
	{
		this.data = eval('(' + data + ')');
	}

	
	/**
	 * 操作是否成功
	 * @returns {Boolean}
	 */
	this.isSuccess = function() {
		return this.data['result'] == 1;
	};

	
	/**
	 * 获取响应信息
	 * @returns
	 */
	this.getMessage = function() {
		return this.data['message'];
	};
};


