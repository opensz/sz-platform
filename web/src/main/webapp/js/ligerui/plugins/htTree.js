Namespace.register("org.sz.form");

/**
 * 使用方法如下：
 * var tr = new org.sz.form.htTree({			
 * 		menuName:菜单div的id,
		treeName:树div的id,
		url:读取数据路径,
		param:{}传递参数,
		setting:ztree树表现形式});
 *
 * 显示菜单
 * tr.showRMenu(x,y, 菜单div的id);
 * 隐藏菜单
 * tr.hideRMenu(菜单div的id);
 * 合并树事件
 * tr.apply(原有事件,新增事件);
 */
org.sz.form.htTree = function(obj){
	
	var treeObj;
	//选中节点
	var selectNode;
	$.ajax({
		type: 'POST',
		url: obj.url,
		data: obj.param,
		success: function(result){
			if(obj.setting.onRightClick){
				apply(obj.setting.callback,{onRightClick: zTreeOnRightClick});
			}
			treeObj=$.fn.zTree.init($("#"+obj.treeName), obj.setting,eval(result));
			treeObj.expandAll(true);
		}
	});
	
	$("body").bind("mousedown", 
	function(event){
		if (!(event.target.id == obj.menuName || $(event.target).parents("#"+obj.menuName).length>0)) {
			hideRMenu(obj.menuName);
		}
	});
	
	/**
	 * 树右击事件
	 */
	function zTreeOnRightClick(event, treeId, treeNode) {
		if (treeNode&&!treeNode.notRight) {
			treeObj.selectNode(treeNode);
			showRMenu(event.clientX, event.clientY, obj.menuName);
			if(treeNode.orgSupId=="-1")//根节点时，把删除和编辑隐藏掉
			{
				$("#btnEditNode,#btnDelNode").css("visibility","hidden");
			}
			//高置选中节点
			selectNode=treeNode;
		}
	};
	
	/**
	 * 菜单显示
	 */
	showRMenu=function showRMenu(x, y, mName) {
		$("#"+mName+" a").show();
		$("#"+mName).css({"top":y+"px", "left":x+"px", "visibility":"visible"});
	};
	
	/**
	 * 菜单隐藏
	 */
	hideRMenu=function hideRMenu(mName) {
		$("#"+mName).css("visibility","hidden");
	};
	/**
	 * 给外部调用,返回选中节点
	 */
	this.getSelectNode=function(){
		
		return selectNode;
	};
	
	/**
	 * 合并数组
	 * @returns
	 */	
	apply=function apply(){
	    var l=arguments.length;
	    if(l<1)return null; //无参数
	    if(l>2){ //多于两个
		    apply(arguments[0],arguments[l-1]); //从最后一个开始继承
		    arguments.length=l-1; //去掉最后一个参数然后递归
		    apply(arguments);
	    }
	    //两个参数
	    if(arguments[0]&&arguments[1]&&typeof arguments[1]=="object"){
		    for(var A in arguments[1]){
		    	arguments[0][A]=arguments[1][A];
		    }
	    }
	    return arguments[0];
	}
	

};



