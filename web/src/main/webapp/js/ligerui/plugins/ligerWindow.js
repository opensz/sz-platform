(function ($)
{
    ///	<param name="$" type="jQuery"></param>
    $.fn.ligerWindow = function (p)
    {

    	if($(this).attr("useWindow")) return;
    	
        p = $.extend({
            showClose: true,
            showMax: true,
            showToggle: true,
            showButton:false,
            resize:false,
            modal:true,
            okHandler:null
        }, p || {});
        
        
        var g = {
        		applyWindowMask: function() {
                    $(".l-window-mask").remove();
                    $("<div class='l-window-mask' style='display: block;'></div>").height($(window).height()+$(window).scrollTop()).appendTo('body');
                },
                removeWindowMask: function() {
                    $(".l-window-mask").remove();
                },
        		close:function(){
        			
        			if(p.url ){
        				g.window.remove();
        			}
        			else
        			{
        				g.window.hide();
        			}
        			
        			if(p.modal)g.removeWindowMask();	
        		},
        		show:function(){
        			g.window.show();
        			if (p.modal) g.applyWindowMask();
        		},
        		switchWindow:function(w){
        			$(w).css("z-index", "101").siblings(".l-window").css("z-index", "100");
        		},
        		handleMax:function(){
        			$(".l-window-max", g.window).click(function ()
		            {
		                if ($(this).hasClass("l-window-regain"))
		                {
		                    if (p.onRegain && p.onRegain() == false) return false;
		                    g.window.width(g.lastWindowWidth).css({ left: g.lastWindowLeft, top: g.lastWindowTop });
		                    g.window.content.height(g.lastWindowHeight - 28);
		                    $(this).removeClass("l-window-regain");
		                }
		                else
		                {
		                    if (p.onMax && p.onMax() == false) return false;
		                    g.lastWindowWidth = g.window.width();
		                    g.lastWindowHeight = g.window.height();
		                    g.lastWindowLeft = g.window.css('left');
		                    g.lastWindowTop = g.window.css('top');
		                    g.window.width($(window).width() - 2).css({ left: 0, top: 0 });
		                    g.window.content.height($(window).height() - 28);
		                    $(this).addClass("l-window-regain");
		                }
		            });
        		},
        		handleClose:function(){
    				$(".l-window-close", g.window).click(function ()
		            {
		            	if(p.onClose==undefined)
		            	{
		            		g.close();
		            	}
		            	else
		            	{
		            		if(typeof(p.onClose)=="function"){
		            			p.onClose(g.window, g);
		            		}
		            		else{
		            			if(p.onClose){
		            				g.close();
		            			}
		            		}
		            	}
		            	
		            });
        		},
        		handleToggle:function(){
        			$(".l-window-toggle", g.window).click(function ()
		            {
		                if ($(this).hasClass("l-window-toggle-close"))
		                {
		                    $(this).removeClass("l-window-toggle-close");
		                }
		                else
		                {
		                    $(this).addClass("l-window-toggle-close");
		                }
		                g.window.content.slideToggle();
		            });
        		},
        		handleDrag:function(){
        			if ($.fn.ligerDrag)
                    {
                        g.window.ligerDrag({ handler: '.l-window-header', onStartDrag: function (){
                                g.switchWindow(g.window[0]);
                                g.window.addClass("l-window-dragging");
                            }, onStopDrag: function ()
                            {
                                g.window.removeClass("l-window-dragging");
                            }
                        });
                    }
        		},
        		handleResize:function(){
        			if(!p.resize) return;
        			 //改变大小支持
                    if ($.fn.ligerResizable)
                    {

                        g.window.ligerResizable({
                            onStartResize: function ()
                            {
                                g.switchWindow(g.window[0]);
                                if ($(".l-window-max", g.window).hasClass("l-window-regain"))
                                {
                                    $(".l-window-max", g.window).removeClass("l-window-regain");
                                }
                            },
                            onStopResize: function (current, e)
                            {
                                var top = 0;
                                var left = 0;
                                if (!isNaN(parseInt(g.window.css('top'))))
                                    top = parseInt(g.window.css('top'));
                                if (!isNaN(parseInt(g.window.css('left'))))
                                    left = parseInt(g.window.css('left'));
                                if (current.diffTop != undefined)
                                {
                                    g.window.css({
                                        top: top + current.diffTop,
                                        left: left + current.diffLeft,
                                        width: current.newWidth
                                    });
                                    g.window.content.height(current.newHeight - 28);
                                }
                                return false;
                            }
                        });
                        g.window.append("<div class='l-btn-nw-drop'></div>");
                    }
        		},
        		handleButton:function(){
        			if(p.showButton){
                   	 p.height && g.window.content.height(p.height - 28-30);
                   	 
                   	 var btnCancel = $('<div class="l-dialog-btn"><div class="l-dialog-btn-l"></div><div class="l-dialog-btn-r"></div><div class="l-dialog-btn-inner">取消</div></div>');
                   	 $(".l-window-buttons", g.window).append(btnCancel);
                   	 btnCancel.click(function(){g.close();});
                     
                        if(p.buttons){
                       	 for(var i=p.buttons.length-1;i>=0;i--){
                       		 var para=p.buttons[i];
                       		 var btn = $('<div class="l-dialog-btn"><div class="l-dialog-btn-l"></div><div class="l-dialog-btn-r"></div><div class="l-dialog-btn-inner">'+para.text+'</div></div>');
                       		 
                       		 btn.click(function(){
                       			 var txt=$(this).text();
                       			 for(var k=0;k<p.buttons.length;k++){
                       				 var obj=p.buttons[k];
                       				 if(obj.text==txt){
                       					 if(obj.handler){
                       						 obj.handler(g);
                       					 }
                       					 else{
                       						 alert("请设置确定按钮事件处理!");
                       					 }
                       					 break;
                       				 }
                       			 }                          
                                });
                       		 $(".l-window-buttons", g.window).append(btn);
                       	 }
                       	 var width=(p.buttons.length+1)*70 + p.buttons.length*10;
                            $(".l-window-buttons").width(width);
                        }
                        //设置事件
                        $(".l-dialog-btn",  g.window).hover(function() {
                            $(this).addClass("l-dialog-btn-over");
                        }, function() {
                            $(this).removeClass("l-dialog-btn-over");
                        });
                   }
                   else
                   {
                   	 p.height && g.window.content.height(p.height - 28);
                   }
        		}
        };
        
        if(p.showButton){
        	g.window=$('<div class="l-window"><div class="l-window-header"><div class="l-window-header-buttons"><div class="l-window-toggle"></div><div class="l-window-max"></div><div class="l-window-close"></div><div class="l-clear"></div></div><div class="l-window-header-inner"></div></div><div class="l-window-content"></div><div class="l-window-buttons"></div></div>');
        }
        else
        {
        	g.window = $('<div class="l-window"><div class="l-window-header"><div class="l-window-header-buttons"><div class="l-window-toggle"></div><div class="l-window-max"></div><div class="l-window-close"></div><div class="l-clear"></div></div><div class="l-window-header-inner"></div></div><div class="l-window-content"></div></div>');
        }
        g.window.content = $(".l-window-content", g.window);
        g.window.header = $(".l-window-header", g.window);

        var winId="";
         
        if (p.url && p.frameid){
        	var id=p.frameid || "" +new Date().getTime();
        	winId="window_" + id;
            var iframe = $("<iframe id='"+id+"' frameborder='0' src='" + p.url + "'></iframe>");
            iframe.appendTo(g.window.content);
            g.applyWindowMask();
        }
        else
        {
        	var id=$(this).attr("id") || "" +new Date().getTime();
        	winId="window_" + id;
        	$(this).appendTo(g.window.content);
        	$(this).show();
        	$(this).attr("useWindow",true);
        }
        g.window.attr("id",winId);
        
        g.switchWindow(g.window);
        
        //$(top.document).find(this._boxId).remove();
		//if (!(this._types.info == type || this._types.correct == type)) {
		//	$("<div class='l-window-mask' style='display: block;'></div>")
		//			.appendTo(top.document.body);
		//}
        $("body").append(g.window);
        //设置参数属性 ，如果左边表达式不为空，则执行右边的表达式
        p.left && g.window.css('left', p.left);
        p.right && g.window.css('right', p.right);
        p.top && g.window.css('top', p.top);
        p.bottom && g.window.css('bottom', p.bottom);
        p.width && g.window.width(p.width);
        
        if(p.iconCls)
        {
        	p.title='<span class="toolBar group"><a class="'+p.iconCls+'">' + p.title + '</a></span>';
        }
        
        p.title && $(".l-window-header-inner", g.window.header).html(p.title);

        p.frameid && $(">iframe", g.window.content).attr('id', p.frameid);
        if (!p.showToggle) $(".l-window-toggle", g.window).remove();
        if (!p.showMax) $(".l-window-max", g.window).remove();
        if (!p.showClose) $(".l-window-close", g.window).remove();

        g.handleButton();
        g.handleResize();
        //拖动支持
        g.handleDrag();
        g.handleToggle();
        g.handleClose();
        g.handleMax();

        return g;
    };


})(jQuery);

/**
 * 显示对话框
 * @param setting 参照ligerWindow的setting
 */
function HtWindow(setting)
{
	if(!setting) setting={}; 
	var win=null;
	var btntext="确定";
	if(setting.text!="")
	{
		btntext=setting.text;
	}
	var buttons=[{text:btntext,handler:function(){
		if(win) win.close();
	}}];
	var w=600;
	var h=400;
	if(setting.width!="")
	{
		w=setting.width;
	}
	if(setting.height!="")
	{
		h=setting.height;
	}
	var left=($(window).width()-w)/2;
	var top=($(window).height()-h)/2;
	//对话框ID
	var dlgId="";
	if(!setting.dlgId)
	{
		var div=document.createElement("DIV");
		dlgId=parseInt(Math.random()*10000);
		$(div).attr("id",dlgId);
		$(div).css('display','none');
		$(div).appendTo($(document.body));
	}
	else
	{
		dlgId=setting.dlgId;
	}	
	var defSetting={width:w,height:h,left:left,top:top,title:"未定义",showMax:true,showToggle:true,onClose:true,showButton:true,buttons:buttons};
	jQuery.extend(defSetting,setting);

	//若存在url，则加载于同一页面
	if(defSetting.url && !(defSetting.frameid))
	{
		$('#'+dlgId).html('<h2>正在加载....</h2>');
		var url=defSetting.url.getNewUrl();
		
		$('#'+dlgId).load(url,null,function(){
			if(defSetting.onReady)
			{
				defSetting.onReady.call(this);
			}
		});
	}
	
	win=$('#'+dlgId).ligerWindow(defSetting);

	return {
		owner:win,
		getId:function(){
			return dlgId;
		},
		close:function(){
			if(win)win.close();
		},
		show:function(){
			if(win)win.show();
		}
	};
}
