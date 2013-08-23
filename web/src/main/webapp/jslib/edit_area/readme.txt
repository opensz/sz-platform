使用方法
1.引用JS <script language="javascript" type="text/javascript" src="${ctx}/js/edit_area/edit_area_full.js"></script>。
2.加载textarea
$(function(){
	  
		editAreaLoader.init({
			language:'zh',
			fullscreen:true,
			allow_toggle:false,
			id : "content"		// textarea id
			,syntax: "html"			// syntax to be uses for highgliting
			,start_highlight: true		// to display with highlight mode on start-up
			,toolbar:'search,go_to_line,undo,redo,select_font,highlight,fullscreen,save',
			save_callback: "callBack"
		});    
	});
3.保存回调函数。
function callBack(id,content)
{
	
}