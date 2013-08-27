/**
* jQuery ligerUI 1.0.1.1
* 
* Author leoxie [ gd_star@163.com ] 
* 
*/
if(typeof (LigerUIManagers) == "undefined") LigerUIManagers = {};
(function($)
{
    ///	<param name="$" type="jQuery"></param>

    $.fn.ligerGetToolBarManager = function()
    {
        return LigerUIManagers[this[0].id + "_ToolBar"];
    }; 
    $.fn.ligerToolBar = function(p)
    { 
    	p =  p || {};
        this.each(function()
        {
            if (this.usedToolBar) return;
            var g ={
                addItem :function(item){
                	var ditem;
                	if(g.toolBar.float=="left")
                		ditem = $('<div class="l-toolbar-item l-panel-btn"><span></span><div class="l-panel-btn-l"></div><div class="l-panel-btn-r"></div></div>');
                	else
                		ditem = $('<div class="l-toolbar-item-right l-panel-btn"><span></span><div class="l-panel-btn-l"></div><div class="l-panel-btn-r"></div></div>');
                	
                    g.toolBar.append(ditem);
                    item.id && ditem.attr("toolbarid",item.id);
                    if(item.icon){
                         ditem.append("<div class='l-icon l-icon-"+item.icon+"'></div>");
                         ditem.addClass("l-toolbar-item-hasicon");
                    }
                    item.text && $("span:first",ditem).html(item.text);
                    item.disable && ditem.addClass("l-toolbar-item-disable");
                    item.click && ditem.click(function(){ item.click(item);}); 
                    ditem.hover(function ()
                    {
                        $(this).addClass("l-panel-btn-over");
                    }, function ()
                    {
                        $(this).removeClass("l-panel-btn-over");
                    });
                },
                decorate:function(item)
                {
                	item.addClass("l-panel-btn");
                	
                	if(g.toolBar.float=="left"){
            			item.addClass("l-toolbar-item");
            		}
            		else{
            			item.addClass("l-toolbar-item-right");
            		}
        
                	if(item.attr("disable"))
                	{
                		item.addClass("disable");
                	}
                	else{
                		item.hover(function ()
                        {
                            $(this).addClass("l-panel-btn-over");
                        }, function ()
                        {
                            $(this).removeClass("l-panel-btn-over");
                        });
                		
                	
                		if(item.attr("icon")){
                    		item.append("<div class='l-icon'><img src='"+item.attr("icon")+"' border='0'/></div>");
                    		item.addClass("l-toolbar-item-hasicon");
                    	}
                    	
                    	item.append('<div class="l-panel-btn-l"></div><div class="l-panel-btn-r"></div>');
                	}
                }
            };
            g.toolBar = $(this);
            if(g.toolBar.attr("float")==null)
            	g.toolBar.float="left";
            else
            	g.toolBar.float= g.toolBar.attr("float");
            
           
           
            if(!g.toolBar.hasClass("l-toolbar")) g.toolBar.addClass("l-toolbar"); 
            if(p.items)
            {
                $(p.items).each(function(i,item){
                	
                    g.addItem(item); 
                });
            } 
            if(g.toolBar.float=="left"){
            	g.toolBar.children().each(function(i,item){
                		g.decorate($(this));
                	});
            }
            else
            {
            	var aryObj=new Array();
            	g.toolBar.children().each(function(i,item){
            		aryObj.push($(this));
            	});
            	g.toolBar.empty();
            	var i=aryObj.length;
            	for(var k=i-1;k>=0;k--){
            		g.toolBar.append(aryObj[k]);
            	}
            	g.toolBar.children().each(function(i,item){
            		g.decorate($(this));
            	});
            	aryObj=null;
            }
            
            if (this.id == undefined) this.id = "LigerUI_" + new Date().getTime();
            LigerUIManagers[this.id + "_ToolBar"] = g;
            this.usedToolBar = true;
        });
        if (this.length == 0) return null;
        if (this.length == 1) return LigerUIManagers[this[0].id + "_ToolBar"];
        var managers = [];
        this.each(function() {
            managers.push(LigerUIManagers[this.id + "_ToolBar"]);
        });
        return managers;
    };

})(jQuery);