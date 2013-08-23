/**
* jQuery ligerUI 1.0.2
* 
* Author leoxie [ gd_star@163.com ] 
* 
*/
if (typeof (LigerUIManagers) == "undefined") LigerUIManagers = {};
(function($)
{ 
    ///	<param name="$" type="jQuery"></param>

    $.fn.ligerGetAccordionManager = function()
    {
        return LigerUIManagers[this[0].id + "_Accordion"];
    };
    $.fn.ligerRemoveAccordionManager = function()
    {
        return this.each(function()
        {
            LigerUIManagers[this.id + "_Accordion"] = null;
        });
    };

    $.fn.ligerAccordion = function(p)
    { 

        this.each(function()
        {
        	
            p = $.extend({
                height: null,
                speed : "normal",
                changeHeightOnResize: false,
                heightDiff: 0 ,// 高度补差  
                activeTitleName:"activeTitle"
            }, p || {});
            
            
            if (this.usedAccordion) return;
            
            var g = {
                setHeight: function(height)
                {
                    g.accordion.height(height);
                    g.height=height;
                    height -= g.headerHoldHeight;
                    $("> .l-accordion-content", g.accordion).height(height);
                },
                setContentHeight:function(height){
                	$("> .l-accordion-content", g.accordion).height(height);
                },
                init:function(){
                	//添加样式class
                	if (!g.accordion.hasClass("l-accordion-panel")) g.accordion.addClass("l-accordion-panel");
                    //获取当前选中的panel，默认第一个被选中
                    g.htmlToAccordion();

                    //处理打开状态按钮按钮
                    g.handlerToggleButton();
                    //控制panel头部鼠标移动的效果
                    g.handlerHeaderOver();
                    //控制panel头部状态按钮鼠标移动的效果
                    g.handlerToggleButtonOver();
                    //头部点击时处理
                    $(">.l-accordion-header", g.accordion).click(function(){
                    	g.headClickHandler($(this));
                    });
                    //计算panel头的高度
                    g.headerHoldHeight = 0;
                    $("> .l-accordion-header", g.accordion).each(function()
                    {
                        g.headerHoldHeight += $(this).height();
                    });
                    //百分比高度
                    if (p.height)
                    {
                        g.height = p.heightDiff + p.height;
                        g.accordion.height(g.height);
                        g.setHeight(p.height);
                    }
                    else
                    {
                        g.height = g.accordion.height();
                    }
                },
                ////添加一个panel 分两步添加，添加头，添加内容。并将添加的
                addPanel:function(title,icon,content){
                	var contentObj=$("<div></div>");
                	contentObj.append(content);
                	
                	var header = $('<div class="l-accordion-header" ><div class="l-accordion-toggle"></div><div class="l-accordion-header-inner"></div></div>');
                    if (title){
                    	title="<span class='title'>"+title+"</span>";
                    	if(icon!=null && icon!=undefined && icon!=''){
                    		title="<img src='"+icon+"' border='0' style='vertical-align:middle;margin-right:4px;'/>" + title ;
                    	}
                        $(".l-accordion-header-inner", header).html(title);
                     
                    }
                    contentObj.addClass("l-accordion-content").addClass("l-scroll");
                    //插入一个panel
                    g.accordion.append(header);
                    g.accordion.append(contentObj);
                    //添加header点击事件
                    header.click(function(){
                    	g.headClickHandler($(this));
                    });
       
                },
                addComplete:function(){
                	g.headerHoldHeight = 0;
                    $("> .l-accordion-header", g.accordion).each(function()
                    {
                        g.headerHoldHeight += $(this).height();
                    });
                    var contentHeight=g.height- g.headerHoldHeight;
                    $("> .l-accordion-header", g.accordion).each(function(i)
                    {
                    	var obj=$(this); 
                    	var toggleObj=$("> div.l-accordion-toggle",obj);
                    	$(this).next().height(contentHeight);
                    	toggleObj.removeClass("l-accordion-toggle-open").removeClass("l-accordion-toggle-close");
                    	if(i==0){
                    		 toggleObj.addClass("l-accordion-toggle-open");
                    		 $(this).next().show();
                    	}
                    	else{
                    		 toggleObj.addClass("l-accordion-toggle-close");
                    		 $(this).next().hide();
                    	}
                    });
                    g.handlerHeaderOver();
                    g.handlerToggleButtonOver();
                    
                    var title=jQuery.getCookie(p.activeTitleName);
                    if(title)
                    	g.activeByTitle(title);
                },
                //使用标题激活选项
                activeByTitle:function(title){
                	var selectIndex=0;
                	var objTitle=$("span.title:contains('"+title+"')",g.accordion);
                	if(objTitle.length>0){
                		var tmp=$(objTitle[0]).parent().parent();
                		selectIndex= $("> .l-accordion-header", g.accordion).index(tmp); 
                	}
                	$("> .l-accordion-header", g.accordion).each(function(i)
                    {
                    	var obj=$(this); 
                    	var toggleObj=$("> div.l-accordion-toggle",obj);
                    	toggleObj.removeClass("l-accordion-toggle-open").removeClass("l-accordion-toggle-close");
                    	if(i==selectIndex){
                    		 toggleObj.addClass("l-accordion-toggle-open");
                    		 $(this).next().show();
                    	}
                    	else{
                    		 toggleObj.addClass("l-accordion-toggle-close");
                    		 $(this).next().hide();
                    	}
                    });
                	
                },
                
                headClickHandler:function(obj){
                	
                	var title=$(".title",obj).text();
                	
                	jQuery.setCookie(p.activeTitleName,title);
                
                	//找到toggle按钮
                    var togglebtn = $(".l-accordion-toggle:first",obj);
                    //如果按钮状态为关闭情况
                    if (togglebtn.hasClass("l-accordion-toggle-close"))
                    {
                    	//移除关闭样式
                        togglebtn.removeClass("l-accordion-toggle-close")
                        .removeClass("l-accordion-toggle-close-over l-accordion-toggle-open-over"); 
                        //添加打开的样式
                        togglebtn.addClass("l-accordion-toggle-open");
                        //调用show的方法显示内容部分，并关闭其他内容可见的节点。
                        $(obj).next(".l-accordion-content").show(p.speed)
                        .siblings(".l-accordion-content:visible").hide(p.speed);
                        $(obj).siblings(".l-accordion-header").find(".l-accordion-toggle").removeClass("l-accordion-toggle-open").addClass("l-accordion-toggle-close");
                    }
                },
                handlerToggleButtonOver:function(){
                	 $(".l-accordion-toggle", g.accordion).hover(function()
                     {
                         if ($(this).hasClass("l-accordion-toggle-open"))
                             $(this).addClass("l-accordion-toggle-open-over");
                         else if ($(this).hasClass("l-accordion-toggle-close"))
                             $(this).addClass("l-accordion-toggle-close-over");
                     }, function()
                     {
                         if ($(this).hasClass("l-accordion-toggle-open"))
                             $(this).removeClass("l-accordion-toggle-open-over");
                         else if ($(this).hasClass("l-accordion-toggle-close"))
                             $(this).removeClass("l-accordion-toggle-close-over");
                     });
                },
                handlerHeaderOver:function(){
                	$(".l-accordion-header", g.accordion).hover(function()
                    {
                		$(this).addClass("l-accordion-header-over");
                    }, function()
                    {
                        $(this).removeClass("l-accordion-header-over");
                    });
                },
                
                handlerToggleButton:function(){
                	$(".l-accordion-toggle", g.accordion).each(function()
                    {
                    	//判断是否打开状态按钮样式，没有就加上关闭状态的样式
                        if (!$(this).hasClass("l-accordion-toggle-open") && !$(this).hasClass("l-accordion-toggle-close")){
                            $(this).addClass("l-accordion-toggle-close");
                        }
                        //判断panel关闭状态，内容部分隐藏。
                        if ($(this).hasClass("l-accordion-toggle-close")){
                            $(this).parent().next(".l-accordion-content:visible").hide();
                        }
                    });
                },
                htmlToAccordion:function(){
                	var selectedIndex = 0;
                    if ($("> div[lselected=true]", g.accordion).length > 0)
                        selectedIndex = $("> div", g.accordion).index($("> div[lselected=true]", g.accordion));
                    //对每一个panel进行装饰
                    $("> div", g.accordion).each(function(i, box)
                    {
                    	var header = $('<div class="l-accordion-header"><div class="l-accordion-toggle"></div><div class="l-accordion-header-inner"></div></div>');
                        if (i == selectedIndex)
                            $(".l-accordion-toggle", header).addClass("l-accordion-toggle-open");
                        var icon=$(box).attr("icon");
                        var title=$(box).attr("title");
                        if (title){
                        	title="<span class='title'>"+title+"</span>";
                        	if(icon){
                        		title="<img src='"+icon+"' border='0' style='vertical-align:middle;margin-right:4px;'/>" + title ;
                        	}
                            $(".l-accordion-header-inner", header).html(title);
                            $(box).attr("title","");
                        }
                       
                        $(box).before(header);
                        if (!$(box).hasClass("l-accordion-content")) $(box).addClass("l-accordion-content").addClass("l-scroll");
                    });
                }
                
            };
            g.accordion = $(this);
            
            g.init();
          
            if (this.id == undefined) this.id = "LigerUI_" + new Date().getTime();
        
            LigerUIManagers[this.id + "_Accordion"] = g;
            this.usedAccordion = true; 
        });
        //取得当前面板的个数。
        if (this.length == 0) return null;
        //一般是根据Id取得，为唯一的一个面板控件
        if (this.length == 1) return LigerUIManagers[this[0].id + "_Accordion"];
        var managers = [];
        this.each(function() {
            managers.push(LigerUIManagers[this.id + "_Accordion"]);
        });
        return managers;
    };

})(jQuery);