/*
公共全局操作对象 K.yang
*/

(function ($)
{

    //全局系统对象
    window['COM'] = {};

    COM.cookies = (function ()
    {
        var fn = function ()
        {
        };
        fn.prototype.get = function (name)
        {
            var cookieValue = "";
            var search = name + "=";
            if (document.cookie.length > 0)
            {
                offset = document.cookie.indexOf(search);
                if (offset != -1)
                {
                    offset += search.length;
                    end = document.cookie.indexOf(";", offset);
                    if (end == -1) end = document.cookie.length;
                    cookieValue = decodeURIComponent(document.cookie.substring(offset, end))
                }
            }
            return cookieValue;
        };
        fn.prototype.set = function (cookieName, cookieValue, DayValue)
        {
            var expire = "";
            var day_value = 1;
            if (DayValue != null)
            {
                day_value = DayValue;
            }
            expire = new Date((new Date()).getTime() + day_value * 86400000);
            expire = "; expires=" + expire.toGMTString();
            document.cookie = cookieName + "=" + encodeURIComponent(cookieValue) + ";path=/" + expire;
        }
        fn.prototype.remvoe = function (cookieName)
        {
            var expire = "";
            expire = new Date((new Date()).getTime() - 1);
            expire = "; expires=" + expire.toGMTString();
            document.cookie = cookieName + "=" + escape("") + ";path=/" + expire;
            /*path=/*/
        };

        return new fn();
    })();

    //右下角的提示框
    COM.tip = function (message)
    {
        if (COM.wintip)
        {
            COM.wintip.set('content', message);
            COM.wintip.show();
        }
        else
        {
            COM.wintip = $.ligerDialog.tip({ content: message });
        }
        setTimeout(function ()
        {
            COM.wintip.hide()
        }, 4000);
    };

    //预加载图片
    COM.prevLoadImage = function (rootpath, paths)
    {
        for (var i in paths)
        {
            $('<img />').attr('src', rootpath + paths[i]);
        }
    };
    //显示loading
    COM.showLoading = function (message)
    {
        message = message || "正在加载中...";
        $('body').append("<div class='jloading'>" + message + "</div>");
        $.ligerui.win.mask();
    };
    //隐藏loading
    COM.hideLoading = function (message)
    {
    	
        $('body > div.jloading').remove();
        $.ligerui.win.unmask({ id: new Date().getTime() });
    }
    //显示成功提示窗口
    COM.showSuccess = function (message, callback)
    {
        if (typeof (message) == "function" || arguments.length == 0)
        {
            callback = message;
            message = "操作成功!";
        }
        $.ligerDialog.success(message, '提示信息', callback);
    };
    //显示失败提示窗口
    COM.showError = function (message, callback)
    {
        if (typeof (message) == "function" || arguments.length == 0)
        {
            callback = message;
            message = "操作失败!";
        }
        $.ligerDialog.error(message, '提示信息', callback);
    };


    //预加载dialog的图片
    COM.prevDialogImage = function (rootPath)
    {
        rootPath = rootPath || "";
        COM.prevLoadImage(rootPath + 'libs/ligerUI/skins/Aqua/images/win/', ['dialog-icons.gif']);
        COM.prevLoadImage(rootPath + 'libs/ligerUI/skins/Gray/images/win/', ['dialogicon.gif']);
    };

    //提交服务器请求
    //返回json格式
    //1,提交给类 options.type  方法 options.method 处理
    //2,并返回 AjaxResult(这也是一个类)类型的的序列化好的字符串
    COM.ajax = function (options,callback)
    {
    	
        var p = options || {};
        $.ajax({
            cache: false,
            async: (p.async==false)?false:true,
            type: "POST",
            url: p.url,
            data:p.data,
            dataType: 'json',
            beforeSend: function ()
            {
                COM.loading = true;
                if (p.beforeSend)
                    p.beforeSend();
                else
                    COM.showLoading(p.loading);
            },
            complete: function ()
            {
                COM.loading = false;
                if (p.complete)
                    p.complete();
                else
                    COM.hideLoading();
            },
            success: function (result)
            {
                if (!result)return;
                if (!result.IsError)
                {
                    if (p.success)
                    	p.success(result.Data, result.Message);
                    else 
                    	COM.showSuccess(result.Message,callback);
                }
                else
                {
                    if (p.error)
                        p.error(result.Message);
                    else
                    	COM.showError(result.Message );
                }
            },
            error: function (result, b)
            {
                COM.tip('发现系统错误 <BR>错误码：' + result.status);
            }
        });
    };
   
    
    
    //获取当前页面的MenuNo
    //优先级1：如果页面存在MenuNo的表单元素，那么加载它的值
    //优先级2：加载QueryString，名字为MenuNo的值
    COM.getPageMenuNo = function ()
    {
        var menuno = $("#MenuNo").val();
        if (!menuno)
        {
            menuno = getQueryStringByName("MenuNo");
        }
        return menuno;
    };

    //创建按钮
    COM.createButton = function (options)
    {
        var p = $.extend({
            appendTo: $('body')
        }, options || {});
        var btn = $('<div class="button button2 buttonnoicon" style="width:60px"><div class="button-l"> </div><div class="button-r"> </div> <span></span></div>');
        if (p.icon)
        {
            btn.removeClass("buttonnoicon");
            btn.append('<div class="button-icon"> <img src="../' + p.icon + '" /> </div> ');
        }
        //绿色皮肤
        if (p.green)
        {
            btn.removeClass("button2");
        }
        if (p.width)
        {
            btn.width(p.width);
        }
        if (p.click)
        {
            btn.click(p.click);
        }
        if (p.text)
        {
            $("span", btn).html(p.text);
        }
        if (typeof (p.appendTo) == "string") p.appendTo = $(p.appendTo);
        btn.appendTo(p.appendTo);
    };

    //创建过滤规则(查询表单)
    COM.bulidFilterGroup = function (form)
    {
        if (!form) return null;
        var group = { op: "and", rules: [] };
        $(":input", form).not(":submit, :reset, :image,:button, [disabled]")
        .each(function ()
        {
            if (!this.name) return;
            if (!$(this).hasClass("field")) return;
            if ($(this).val() == null || $(this).val() == "") return;
            var ltype = $(this).attr("ltype");
            var optionsJSON = $(this).attr("ligerui"), options;
            if (optionsJSON)
            {
                options = JSON2.parse(optionsJSON);
            }
            var op = $(this).attr("op") || "like";
            //get the value type(number or date)
            var type = $(this).attr("vt") || "string";
            var value = $(this).val();
            var name = this.name;
            //如果是下拉框，那么读取下拉框关联的隐藏控件的值(ID值,常用与外表关联)
            if (ltype == "select" && options && options.valueFieldID)
            {
                value = $("#" + options.valueFieldID).val();
                name = options.valueFieldID;
            }
            group.rules.push({
                op: op,
                field: name,
                value: value,
                type: type
            });
        });
        return group;
    };

    //附加表单搜索按钮：搜索、高级搜索
    COM.appendSearchButtons = function (form, grid)
    {
        if (!form) return;
        form = $(form);
        //搜索按钮 附加到第一个li  高级搜索按钮附加到 第二个li
        var container = $('<ul><li style="margin-right:8px"></li><li></li></ul><div class="l-clear"></div>').appendTo(form);
        COM.addSearchButtons(form, grid, container.find("li:eq(0)"), container.find("li:eq(1)"));

    };

    //创建表单搜索按钮：搜索、高级搜索
    COM.addSearchButtons = function (form, grid, btn1Container, btn2Container)
    {
        if (!form) return;
        if (btn1Container)
        {
            COM.createButton({
                appendTo: btn1Container,
                text: '搜索',
                click: function ()
                {
                    var rule = COM.bulidFilterGroup(form);
                    if (rule.rules.length)
                    {
                        grid.set('parms', { where: JSON2.stringify(rule) });
                    } else
                    {
                        grid.set('parms', {});
                    }
                    grid.loadData();
                }
            });
        }
        if (btn2Container)
        {
            COM.createButton({
                appendTo: btn2Container,
                width: 80,
                text: '高级搜索',
                click: function ()
                {
                    grid.showFilter();
                }
            });
        }
    };

    //快速设置表单底部默认的按钮:保存、取消
    COM.setFormDefaultBtn = function (cancleCallback, savedCallback)
    {
        //表单底部按钮
        var buttons = [];
        if (cancleCallback)
        {
            buttons.push({ text: '取消', onclick: cancleCallback });
        }
        if (savedCallback)
        {
            buttons.push({ text: '保存', onclick: savedCallback });
        }
        COM.addFormButtons(buttons);
    };

    //增加表单底部按钮,比如：保存、取消
    COM.addFormButtons = function (buttons)
    {
        if (!buttons) return;
        var formbar = $("body > div.form-bar");
        if (formbar.length == 0)
            formbar = $('<div class="form-bar"><div class="form-bar-inner"></div></div>').appendTo('body');
        if (!(buttons instanceof Array))
        {
            buttons = [buttons];
        }
        $(buttons).each(function (i, o)
        {
            var btn = $('<div class="l-dialog-btn"><div class="l-dialog-btn-l"></div><div class="l-dialog-btn-r"></div><div class="l-dialog-btn-inner"></div></div> ');
            $("div.l-dialog-btn-inner:first", btn).html(o.text || "BUTTON");
            if (o.onclick)
            {
                btn.bind('click', function ()
                {
                    o.onclick(o);
                });
            }
            if (o.width)
            {
                btn.width(o.width);
            }
            $("> div:first", formbar).append(btn);
        });
    };

    //填充表单数据
    COM.loadForm = function (mainform, options, callback)
    {
        options = options || {};
        var p = $.extend({
            beforeSend: function ()
            {
                COM.showLoading('正在加载表单数据中...');
            },
            complete: function ()
            {
                COM.hideLoading();
            },
            success: function (data)
            {
                var preID = options.preID || "";
                //根据返回的属性名，找到相应ID的表单元素，并赋值
                for (var c in data)
                {
                	var ele = $("[name='" + (preID + c) + "']");

                    //针对复选框和单选框 处理
                	if(ele.length > 0){
                	   if (ele.is(":checkbox,:radio"))
                	   {
                		   ele[0].checked = (data[c]=="true")? true : false;
                	   }else{
                	   	    try{
                	   	    	ele.ligerGetComboBoxManager().setValue(data[c]);
                	   	    }catch(e){
                	   	    	ele.val(data[c]);
                	   	    }
                	   }
                    }
                }
                //下面是更新表单的样式
                var managers = $.ligerui.find($.ligerui.controls.Input);
                for (var i = 0, l = managers.length; i < l; i++)
                {
                    //改变了表单的值，需要调用这个方法来更新ligerui样式
                    var o = managers[i];
                    o.updateStyle();
                    if (managers[i] instanceof $.ligerui.controls.TextBox)
                        o.checkValue();
                }
                if (callback)
                    callback(data);
            },
            error: function (message)
            {
                COM.showError('数据加载失败!<BR>错误信息：' + message);
            }
        }, options);
        COM.ajax(p);
    };
    
/*  两个json对像合并
    var a ={"a":"1"} var b ={"c":"3"}
    var c = extend({}, [a,b]);*/
    COM.joinJsonString =function(des, src, override){
        if(src instanceof Array){
            for(var i = 0, len = src.length; i < len; i++)
                 extend(des, src[i], override);
        }
        for( var i in src){
            if(override || !(i in des)){
                des[i] = src[i];
            }
        }
        return des;
    };
    
    /* 普通jquery表单提交及验证*/
    COM.AjaxSubmit =function(form,obj,valid){
     	hardform.ajaxSubmit({
 			 target:     '#divToUpdate',
  			url: obj.url,
  			type:"post",
  			data:obj.data,
  			dataType:"json",
  			beforeSubmit:function(){
  			//验证valid
  			},
  			beforeSend: function (a, b, c)
           {
               COM.showLoading('正在保存数据中...');

           },
           success:  function (msg) { 
   				$.ligerDialog.success("保存成功", '提示信息', callback);
           
           }}
           );
       };


    //带验证、带loading的提交
    COM.submitForm = function (mainform, success, error)
    {
        if (!mainform)
            mainform = $("form:first");
        if (mainform.valid())
        {
            mainform.ajaxSubmit({
                dataType: 'json',
                success: success,
                beforeSubmit: function (formData, jqForm, options)
                {
                    //针对复选框和单选框 处理
                    $(":checkbox,:radio", jqForm).each(function ()
                    {
                        if (!existInFormData(formData, this.name))
                        {
                            formData.push({ name: this.name, type: this.type, value: this.checked });
                        }
                    });
                    for (var i = 0, l = formData.length; i < l; i++)
                    {
                        var o = formData[i];
                        if (o.type == "checkbox" || o.type == "radio")
                        {
                            o.value = $("[name=" + o.name + "]", jqForm)[0].checked ? "true" : "false";
                        }
                    }
                },
                beforeSend: function (a, b, c)
                {
                    COM.showLoading('正在保存数据中...');

                },
                complete: function ()
                {
                    COM.hideLoading();
                },
                error: function (result)
                {
                    COM.tip('发现系统错误 <BR>错误码：' + result.status);
                }
            });
        }
        else
        {
            COM.showInvalid();
        }
        function existInFormData(formData, name)
        {
            for (var i = 0, l = formData.length; i < l; i++)
            {
                var o = formData[i];
                if (o.name == name) return true;
            }
            return false;
        }
    };

    //提示 验证错误信息
    COM.showInvalid = function (validator)
    {
        validator = validator || COM.validator;
        if (!validator) return;
        var message = '<div class="invalid">存在' + validator.errorList.length + '个字段验证不通过，请检查!</div>';
        //top.COM.tip(message);
        $.ligerDialog.error(message);
    };

    //表单验证
    COM.validate = function (form, options)
    {
        if (typeof (form) == "string")
            form = $(form);
        else if (typeof (form) == "object" && form.NodeType == 1)
            form = $(form);
        options = $.extend({
            errorPlacement: function (lable, element)
            {
                 if (!element.attr("id"))
                    element.attr("id", new Date().getTime());
                if (element.hasClass("l-textarea"))
                {
                    element.addClass("l-textarea-invalid");
                }
                else if (element.hasClass("l-text-field"))
                {
                    element.parent().addClass("l-text-invalid");
                	 //element.parent().ligerTip({ content: lable.html(), appendIdTo: lable });
                }
                $(element).removeAttr("title").ligerHideTip();
                $(element).attr("title", lable.html()).ligerTip({
                    distanceX: -10,
                    distanceY: -3,
                    auto: true
                });
            },
            success: function (lable)
            {
            	var element = $("[name='" + lable.attr("for")+"']");
                if (element.hasClass("l-textarea"))
                {
                    element.removeClass("l-textarea-invalid");
                }
                else if (element.hasClass("l-text-field"))
                {
                    element.parent().removeClass("l-text-invalid");
                }
                $(element).removeAttr("title").ligerHideTip();
            }
        }, options || {});
        COM.validator = form.validate(options);
        
        return COM.validator;
    };


    COM.loadToolbar = function (grid, toolbarBtnItemClick)
    {
        var MenuNo = COM.getPageMenuNo();
        COM.ajax({
            loading: '正在加载工具条中...',
            type: 'AjaxSystem',
            method: 'GetMyButtons',
            data: { HttpContext: true, MenuNo: MenuNo },
            success: function (data)
            {
                if (!grid.toolbarManager) return;
                if (!data || !data.length) return;
                var items = [];
                for (var i = 0, l = data.length; i < l; i++)
                {
                    var o = data[i];
                    items[items.length] = {
                        click: toolbarBtnItemClick,
                        text: o.BtnName,
                        img: rootPath + o.BtnIcon,
                        id: o.BtnNo
                    };
                    items[items.length] = { line: true };
                }
                grid.toolbarManager.set('items', items);
            }
        });
    };

    //关闭Tab项,如果tabid不指定，那么关闭当前显示的
    COM.closeCurrentTab = function (tabid)
    {
        if (!tabid)
        {
            tabid = $("#framecenter > .l-tab-content > .l-tab-content-item:visible").attr("tabid");
        }
        if (tab)
        {
            tab.removeTabItem(tabid);
        }
    };

    //关闭Tab项并且刷新父窗口
    COM.closeAndReloadParent = function (tabid, parentMenuNo)
    {
        COM.closeCurrentTab(tabid);
        var menuitem = $("#mainmenu ul.menulist li[menuno=" + parentMenuNo + "]");
        var parentTabid = menuitem.attr("tabid");
        var iframe = window.frames[parentTabid];
        if (tab)
        {
        	tab.selectTabItem(parentTabid);
        }
        if (iframe && iframe.f_reload)
        {
            iframe.f_reload();
        }
        else if (parent.tab)
        {
        	tab.reload(parentTabid);
        }
    };

    //覆盖页面grid的loading效果
    COM.overrideGridLoading = function ()
    {
        $.extend($.ligerDefaults.Grid, {
            onloading: function ()
            {
                COM.showLoading('正在加载表格数据中...');
            },
            onloaded: function ()
            {
                COM.hideLoading();
            }
        });
    };


    //查找是否存在某一个按钮
    COM.findToolbarItem = function (grid, itemID)
    {
        if (!grid.toolbarManager) return null;
        if (!grid.toolbarManager.options.items) return null;
        var items = grid.toolbarManager.options.items;
        for (var i = 0, l = items.length; i < l; i++)
        {
            if (items[i].id == itemID) return items[i];
        }
        return null;
    }


    //设置grid的双击事件(带权限控制)
    COM.setGridDoubleClick = function (grid, btnID, btnItemClick)
    {
        btnItemClick = btnItemClick || toolbarBtnItemClick;
        if (!btnItemClick) return;
        grid.bind('dblClickRow', function (rowdata)
        {
            var item = COM.findToolbarItem(grid, btnID);
            if (!item) return;
            grid.select(rowdata);
            btnItemClick(item);
        });
    }
    COM.getSelectIds=function(arr,findPara){
    	var arrIds=new Array();
    	findPara=findPara==null?id:findPara;
    	$.each(arr,function(index, content){
    		arrIds.push(content[findPara]);
    	});
    	return arrIds;
    }


    COM.getSelectIdsStr=function(arr,findPara){
    	var arrIds=new Array();
    	findPara=findPara==null?id:findPara;
    	$.each(arr,function(index, content){
    		arrIds.push(content[findPara]);
    	});
    	return arrIds.join(",");
    }

    COM.setUrlJson=function(obj, url) {
		$.getJSON(url + "&r=" + Math.round(Math.random() * 1000000).toString(),
				function(json) {
				    if(obj.ligerGetComboBoxManager()){
				    obj.val("");
					obj.ligerGetComboBoxManager().setData(json);
					var objValue=$("[name='"+obj.attr("name")+"_val']");
				    if(objValue&&obj.val()==""){objValue.val("");}
					}
				});
	}
	
	COM.loadBrandModel=function(brand,model,params){
		if(brand){
			$.ajax({url:'../com/getBrandInfo'+(params||''),type:"POST",dataType: 'json',success: function (result){brand.autocomplete({source: result});}});
		}
		if(model){
			$.ajax({url:'../com/getModelInfo'+(params||''),type:"POST",dataType: 'json',success: function (result){model.autocomplete({source: result});}});
		}
	}
})(jQuery);

