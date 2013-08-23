/**
* jQuery ligerUI 1.0.2
* 
* Author leoxie [ gd_star@163.com ] 
* 
*/
if (typeof (LigerUIManagers) == "undefined") LigerUIManagers = {};
(function ($) {
    ///	<param name="$" type="jQuery"></param>

    $.fn.ligerGetLayoutManager = function () {
        return LigerUIManagers[this[0].id + "_Layout"];
    };
    $.fn.ligerRemoveLayoutManager = function () {
        return this.each(function () {
            LigerUIManagers[this.id + "_Layout"] = null;
        });
    }; 
    $.ligerDefaults = $.ligerDefaults || {};
    $.ligerDefaults.Layout = {
        topHeight: 50,
        bottomHeight: 50,
        leftWidth: 110,
        centerWidth: 300,
        rightWidth: 170,
        InWindow : true,     //是否以窗口的高度为准 height设置为百分比时可用
        heightDiff : 0,     //高度补差
        height:'100%',      //高度
        onHeightChanged: null,
        isLeftCollapse: false,      //初始化时 左边是否隐藏
        isRightCollapse: false,     //初始化时 右边是否隐藏
        isTopCollapse: false,      //初始化时 上边是否隐藏
        isBottomCollapse: false,     //初始化时 下边是否隐藏
        allowTopCollapse: true,      //是否允许 上边可以隐藏
        allowBottomCollapse: true,     //是否允许 下边可以隐藏
        allowLeftCollapse: true,      //是否允许 左边可以隐藏
        allowRightCollapse: true,     //是否允许 右边可以隐藏
        allowLeftResize: true,      //是否允许 左边可以调整大小
        allowRightResize: true,     //是否允许 右边可以调整大小
        allowTopResize: true,      //是否允许 头部可以调整大小
        allowBottomResize: true,     //是否允许 底部可以调整大小
        space: 3 //间隔
    };
    $.fn.ligerLayout = function (p) {
        this.each(function () {
            p = $.extend({ }, $.ligerDefaults.Layout, p || {});
            if (this.usedLayout) return;
            var g = {
                init: function () {
                    $("> .l-layout-left .l-layout-header,> .l-layout-right .l-layout-header", g.layout).hover(function () {
                        $(this).addClass("l-layout-header-over");
                    }, function () {
                        $(this).removeClass("l-layout-header-over");

                    });
                    $(".l-layout-header-toggle", g.layout).hover(function () {
                        $(this).addClass("l-layout-header-toggle-over");
                    }, function () {
                        $(this).removeClass("l-layout-header-toggle-over");

                    });
                    $(".l-layout-header-toggle", g.left).click(function () {
                        g.setLeftCollapse(true);
                    });
                    $(".l-layout-header-toggle", g.right).click(function () {
                        g.setRightCollapse(true);
                    });
                    $(".l-layout-header-toggle", g.top).click(function () {
                        g.setTopCollapse(true);
                    });
                    $(".l-layout-header-toggle", g.bottom).click(function () {
                        g.setBottomCollapse(true);
                    });
                    //set top
                    g.middleTop = 0;
                    if (g.top) {
                        g.middleTop += g.top.height();
                        g.middleTop += parseInt(g.top.css('borderTopWidth'));
                        g.middleTop += parseInt(g.top.css('borderBottomWidth'));
                        g.middleTop += p.space;
                    }
                    if (g.left) {
                        g.left.css({ top: g.middleTop });
                        g.leftCollapse.css({ top: g.middleTop });
                    }
                    if (g.center) g.center.css({ top: g.middleTop });
                    if (g.right) {
                        g.right.css({ top: g.middleTop });
                        g.rightCollapse.css({ top: g.middleTop });
                    }
                    //set left
                    if (g.left) g.left.css({ left: 0 });
                    g.onResize();
                    g.onResize();
                },
                setCollapse: function () {

                    g.leftCollapse.hover(function () {
                        $(this).addClass("l-layout-collapse-left-over");
                    }, function () {
                        $(this).removeClass("l-layout-collapse-left-over");
                    });
                    g.leftCollapse.toggle.hover(function () {
                        $(this).addClass("l-layout-collapse-left-toggle-over");
                    }, function () {
                        $(this).removeClass("l-layout-collapse-left-toggle-over");
                    });
                    g.rightCollapse.hover(function () {
                        $(this).addClass("l-layout-collapse-right-over");
                    }, function () {
                        $(this).removeClass("l-layout-collapse-right-over");
                    });
                    g.rightCollapse.toggle.hover(function () {
                        $(this).addClass("l-layout-collapse-right-toggle-over");
                    }, function () {
                        $(this).removeClass("l-layout-collapse-right-toggle-over");
                    });
                    g.topCollapse.hover(function () {
										    $(this).addClass("l-layout-collapse-top-over");
										}, function () {
										    $(this).removeClass("l-layout-collapse-top-over");
										});
										g.topCollapse.toggle.hover(function () {
										    $(this).addClass("l-layout-collapse-top-toggle-over");
										}, function () {
										    $(this).removeClass("l-layout-collapse-top-toggle-over");
										});
										g.bottomCollapse.hover(function () {
										    $(this).addClass("l-layout-collapse-bottom-over");
										}, function () {
										    $(this).removeClass("l-layout-collapse-bottom-over");
										});
										g.bottomCollapse.toggle.hover(function () {
										    $(this).addClass("l-layout-collapse-bottom-toggle-over");
										}, function () {
										    $(this).removeClass("l-layout-collapse-bottom-toggle-over");
										});
                    
                    g.leftCollapse.toggle.click(function () {
                        g.setLeftCollapse(false);
                    });
                    g.rightCollapse.toggle.click(function () {
                        g.setRightCollapse(false);
                    });
                    g.topCollapse.toggle.click(function () {
                        g.setTopCollapse(false);
                    });
                    g.bottomCollapse.toggle.click(function () {
                        g.setBottomCollapse(false);
                    });
                    if (g.left && g.isLeftCollapse) {
                        g.leftCollapse.show();
                        g.leftDropHandle && g.leftDropHandle.hide();
                        g.left.hide();
                    }
                    if (g.right && g.isRightCollapse) {
                        g.rightCollapse.show();
                        g.rightDropHandle && g.rightDropHandle.hide();
                        g.right.hide();
                    }
                    if (g.top && g.isTopCollapse) {
                        g.topCollapse.show();
                        g.topDropHandle && g.topDropHandle.hide();
                        g.top.hide();
                    }
                    if (g.bottom && g.isBottomCollapse) {
                        g.bottomCollapse.show();
                        g.bottomDropHandle && g.bottomDropHandle.hide();
                        g.bottom.hide();
                    }
                },
                setLeftCollapse: function (isCollapse) {
                    if (!g.left) return false;
                    g.isLeftCollapse = isCollapse;
                    
                    if (g.isLeftCollapse) {
                        g.leftCollapse.show();
                        g.leftDropHandle && g.leftDropHandle.hide();
                        g.left.hide();
                    }
                    else {
                        g.leftCollapse.hide();
                        g.leftDropHandle && g.leftDropHandle.show();
                        g.left.show();
                    }
                    
                    g.onResize();
                },
                setRightCollapse: function (isCollapse) {
                    if (!g.right) return false;
                    g.isRightCollapse = isCollapse;
                    g.onResize();
                    if (g.isRightCollapse) {
                        g.rightCollapse.show();
                        g.rightDropHandle && g.rightDropHandle.hide();
                        g.right.hide();
                    }
                    else {
                        g.rightCollapse.hide();
                        g.rightDropHandle && g.rightDropHandle.show();
                        g.right.show();
                    }
                    g.onResize();
                },
                setTopCollapse: function (isCollapse) {
                    if (!g.top) return false;
                    g.isTopCollapse = isCollapse;
                    if (g.isTopCollapse) {
                        g.topCollapse.show();
                        g.topDropHandle && g.topDropHandle.hide();
                        g.top.hide();
                    }
                    else {
                        g.topCollapse.hide();
                        g.topDropHandle && g.topDropHandle.show();
                        g.top.show();
                    }
                    g.onResize();
                },
                setBottomCollapse: function (isCollapse) {
                    if (!g.bottom) return false;
                    g.isBottomCollapse = isCollapse;
                    if (g.isBottomCollapse) {
                        g.bottomCollapse.show();
                        g.bottomDropHandle && g.bottomDropHandle.hide();
                        g.bottom.hide();
                    }
                    else {
                        g.bottomCollapse.hide();
                        g.bottomDropHandle && g.bottomDropHandle.show();
                        g.bottom.show();
                    }
                    g.onResize();
                },
                addDropHandle: function () {
                    if (g.left && p.allowLeftResize) {
                        g.leftDropHandle = $("<div class='l-layout-drophandle-left'></div>");
                        g.layout.append(g.leftDropHandle);
                        g.leftDropHandle && g.leftDropHandle.show();
                        g.leftDropHandle.mousedown(function (e) {
                            g.start('leftresize', e);
                        });
                    }
                    if (g.right && p.allowRightResize) {
                        g.rightDropHandle = $("<div class='l-layout-drophandle-right'></div>");
                        g.layout.append(g.rightDropHandle);
                        g.rightDropHandle && g.rightDropHandle.show();
                        g.rightDropHandle.mousedown(function (e) {
                            g.start('rightresize', e);
                        });
                    }
                    if (g.top && p.allowTopResize) {
                        g.topDropHandle = $("<div class='l-layout-drophandle-top'></div>");
                        g.layout.append(g.topDropHandle);
                        g.topDropHandle.show();
                        g.topDropHandle.mousedown(function (e) {
                            g.start('topresize', e);
                        });
                    }
                    if (g.bottom  && p.allowBottomResize) {
                        g.bottomDropHandle = $("<div class='l-layout-drophandle-bottom'></div>");
                        g.layout.append(g.bottomDropHandle);
                        g.bottomDropHandle.show();
                        g.bottomDropHandle.mousedown(function (e) {
                            g.start('bottomresize', e);
                        });
                    }
                    g.draggingxline = $("<div class='l-layout-dragging-xline'></div>");
                    g.draggingyline = $("<div class='l-layout-dragging-yline'></div>");
                    g.layout.append(g.draggingxline).append(g.draggingyline);
                },
                setDropHandlePosition: function () {
                    if (g.leftDropHandle) {
                        g.leftDropHandle.css({ left: g.left.width() + parseInt(g.left.css('left')), height: g.middleHeight, top: g.middleTop });
                    }
                    if (g.rightDropHandle) {
                        g.rightDropHandle.css({ left: parseInt(g.right.css('left')) - p.space, height: g.middleHeight, top: g.middleTop });
                    }
                    if (g.topDropHandle) {
                        g.topDropHandle.css({ top: g.top.height() + parseInt(g.top.css('top')), width: g.top.width() });
                    }
                    if (g.bottomDropHandle) {
                        g.bottomDropHandle.css({ top: parseInt(g.bottom.css('top')) - p.space, width: g.bottom.width() });
                    }
                },
                onResize: function () {
                    var oldheight = g.layout.height();
                    //set layout height 
                    var h = 0;
                    var windowHeight = $(window).height(); 
                    var parentHeight = null;
                    if (typeof(p.height) == "string" && p.height.indexOf('%') > 0)
                    { 
                        var layoutparent = g.layout.parent(); 
                        if (p.InWindow || layoutparent[0].tagName.toLowerCase() == "body") { 
                            parentHeight = windowHeight; 
                            parentHeight -= parseInt($('body').css('paddingTop'));
                            parentHeight -= parseInt($('body').css('paddingBottom'));
                        }
                        else{ 
                            parentHeight = layoutparent.height();
                        }  
                        h =  parentHeight * parseFloat(p.height) * 0.01;   
                        if(p.InWindow || layoutparent[0].tagName.toLowerCase() == "body") 
                            h -= (g.layout.offset().top - parseInt($('body').css('paddingTop')));
                    } 
                    else
                    { 
                        h = parseInt(p.height);
                    }    
                    h += p.heightDiff;  
                    g.layout.height(h);
                    g.layoutHeight = g.layout.height();
                    g.middleWidth = g.layout.width();
                    g.middleHeight = g.layout.height();
                    g.middleTop = 0;
                    if (g.top) {
                		var diff = 0;
                        if (g.isTopCollapse) {
                            diff += g.topCollapse.outerHeight();
                            diff += parseInt(g.topCollapse.css('top'));
	                        diff += p.space;
                        }
                        else {
                            diff += g.top.outerHeight();
                            diff += parseInt(g.top.css('top'));
	                        diff += p.space;
                        }
                        g.middleHeight -= diff;
                       	g.middleTop += diff;
                    }
                    if (g.bottom) {
                        if (g.isBottomCollapse) {
                            g.middleHeight -= g.bottomCollapse.outerHeight();
                            g.middleHeight -= parseInt(g.bottomCollapse.css('bottom'));
	                        g.middleHeight -= p.space;
                        }
                        else {
                            g.middleHeight -= g.bottom.outerHeight();
	                        g.middleHeight -= p.space;
                        }
                    }
                    //specific
                    //g.middleHeight -= 2;

                    if (p.onHeightChanged && g.layoutHeight != oldheight) {
                        p.onHeightChanged({ layoutHeight: g.layoutHeight, diff: g.layoutHeight - oldheight, middleHeight: g.middleHeight });
                    }

                    if (g.top) {
                    	g.topCollapse.width(g.layout.width() - (g.topCollapse.outerWidth()-g.topCollapse.width()));
                        g.top.width(g.layout.width() - (g.top.outerWidth()-g.top.width()));
                        g.top.content.width(g.top.width() - (g.top.content.outerWidth()-g.top.content.width()));
                    }
                    if (g.left) {
                        g.leftCollapse.height(g.middleHeight - (g.leftCollapse.outerHeight()-g.leftCollapse.height()));
                        g.leftCollapse.css({top: g.middleTop});
                        g.left.height(g.middleHeight - (g.left.outerHeight()-g.left.height()));
                        g.left.css({top: g.middleTop});
                        var contentHeight = g.left.height();
                        if(g.left.header) contentHeight-= g.left.header.height();
                        g.left.content.height(contentHeight - (g.left.content.outerHeight()-g.left.content.height()));
                    }
                    if (g.center) {
                        g.centerWidth = g.middleWidth;
                        if (g.left) {
                            if (g.isLeftCollapse) {
                                g.centerWidth -= g.leftCollapse.outerWidth();
                                g.centerWidth -= parseInt(g.leftCollapse.css('left'));
                                g.centerWidth -= p.space;
                            }
                            else {    
                                g.centerWidth -= g.left.outerWidth();
                                g.centerWidth -= parseInt(g.left.css('left'));
                                g.centerWidth -= p.space;
                            }
                        }
                        if (g.right) {
                            if (g.isRightCollapse) {
                                g.centerWidth -= g.rightCollapse.outerWidth();
                                g.centerWidth -= parseInt(g.rightCollapse.css('right'));
                                g.centerWidth -= p.space;
                            }
                            else {
                                g.centerWidth -= g.right.outerWidth();
                                g.centerWidth -= p.space;
                            }
                        }
                        g.centerLeft = 0;
                        if (g.left) {
                            if (g.isLeftCollapse) {
                                g.centerLeft += g.leftCollapse.outerWidth();
                                g.centerLeft += parseInt(g.leftCollapse.css('left'));
                                g.centerLeft += p.space;
                            }
                            else {
                                g.centerLeft += g.left.outerWidth();
                                g.centerLeft += p.space;
                            }
                        }
                        g.center.css({ left: g.centerLeft });
                        g.center.width(g.centerWidth - (g.center.outerWidth()-g.center.width()));
                        g.center.height(g.middleHeight - (g.center.outerHeight()-g.center.height()));
                        g.center.css({top: g.middleTop});
                        var contentHeight = g.center.height();
                        if(g.center.header) contentHeight-= g.center.header.height();
                        g.center.content.height(contentHeight - (g.center.content.outerHeight()-g.center.content.height()));
                        g.center.content.width(g.center.width() - (g.center.content.outerWidth()-g.center.content.width()));
                    }
                    if (g.right) {
                        g.rightCollapse.height(g.middleHeight - (g.rightCollapse.outerHeight()-g.rightCollapse.height()));
                        g.rightCollapse.css({top: g.middleTop});
                        g.right.height(g.middleHeight - (g.right.outerHeight()-g.right.height()));
                        g.right.css({top: g.middleTop});
                        //set left
                        g.rightLeft = 0;

                        if (g.left) {
                            if (g.isLeftCollapse) {
                                g.rightLeft += g.leftCollapse.outerWidth();
                                g.rightLeft += parseInt(g.leftCollapse.css('left'));
                                g.rightLeft += p.space;
                            }
                            else {
                                g.rightLeft += g.left.outerWidth();
                                g.rightLeft += parseInt(g.left.css('left'));
                                g.rightLeft += p.space;
                            }
                        }
                        if (g.center) {
                            g.rightLeft += g.center.outerWidth();
                            g.rightLeft += p.space;
                        }
                        g.right.css({ left: g.rightLeft });
                        var contentHeight = g.right.height();
                        if(g.right.header) contentHeight-= g.right.header.height();
                        g.right.content.height(contentHeight - (g.right.content.outerHeight()-g.right.content.height()));
                    }
                    if (g.bottom) {
                    	g.bottomCollapse.width(g.layout.width() - (g.bottomCollapse.outerWidth()-g.bottomCollapse.width()));
                        g.bottomTop = g.layoutHeight - g.bottom.outerHeight();
                        g.bottom.css({ top: g.bottomTop });
                        g.bottom.width(g.layout.width() - (g.bottom.outerWidth()-g.bottom.width()));
                        g.bottom.content.width(g.bottom.width() - (g.bottom.content.outerWidth()-g.bottom.content.width()));
                    }
                    
                    
                    g.setDropHandlePosition();

                },
                start: function (dragtype, e) {
                    g.dragtype = dragtype;
                    if (dragtype == 'leftresize' || dragtype == 'rightresize') {
                        g.xresize = { startX: e.pageX };
                        g.draggingyline.css({ left: e.pageX - g.layout.offset().left, height: g.middleHeight, top: g.middleTop }).show();
                        $('body').css('cursor', 'col-resize');
                    }
                    else if (dragtype == 'topresize' || dragtype == 'bottomresize') {
                        g.yresize = { startY: e.pageY };
                        g.draggingxline.css({ top: e.pageY - g.layout.offset().top, width: g.layout.width() }).show();
                        $('body').css('cursor', 'row-resize');
                    }
                    else {
                        return;
                    }

                    g.layout.lock.width(g.layout.width());
                    g.layout.lock.height(g.layout.height());
                    g.layout.lock.show();
                    if ($.browser.msie || $.browser.safari)  $('body').bind('selectstart', function () { return false; }); // 不能选择

                    $(document).bind('mouseup', g.stop);
                    $(document).bind('mousemove', g.drag);
                },
                drag: function (e) {
                    if (g.xresize) {
                        g.xresize.diff = e.pageX - g.xresize.startX;
                        g.draggingyline.css({ left: e.pageX - g.layout.offset().left });
                        $('body').css('cursor', 'col-resize');
                    }
                    else if (g.yresize) {
                        g.yresize.diff = e.pageY - g.yresize.startY;
                        g.draggingxline.css({ top: e.pageY - g.layout.offset().top });
                        $('body').css('cursor', 'row-resize');
                    } 
                },
                stop: function (e) {
                    if (g.xresize && g.xresize.diff != undefined) {
                        if (g.dragtype == 'leftresize') {
                            g.leftWidth += g.xresize.diff;
                            g.left.width(g.leftWidth - (g.left.outerWidth()-g.left.width()));
                            g.left.content.width(g.left.width() - (g.left.content.outerWidth()-g.left.content.width()));
                            if (g.center) {
                                g.center.width(g.center.width() - g.xresize.diff).css({ left: parseInt(g.center.css('left')) + g.xresize.diff });
                                var contentWidth = g.center.width();
                                if(g.center.header) contentWidth-= g.center.header.width();
                                g.center.content.width(contentWidth - (g.center.content.outerWidth()-g.center.content.width()));
                            } else if (g.right) {
                                g.right.width(g.left.width() - g.xresize.diff).css({ left: parseInt(g.right.css('left')) + g.xresize.diff });
                                var contentWidth = g.right.width();
                                if(g.right.header) contentWidth-= g.right.header.width();
                                g.right.content.width(contentWidth - (g.right.content.outerWidth()-g.right.content.width()));
                            }
                        }
                        else if (g.dragtype == 'rightresize') {
                            g.rightWidth -= g.xresize.diff;
                            g.right.width(g.rightWidth).css({ left: parseInt(g.right.css('left')) + g.xresize.diff });
                            if (g.center) {
                                g.center.width(g.center.width() + g.xresize.diff);
                                var contentWidth = g.center.width();
                                if(g.center.header) contentWidth-= g.center.header.width();
                                g.center.content.width(contentWidth - (g.center.content.outerWidth()-g.center.content.width()));
                            } else if (g.left) {
                                g.left.width(g.left.width() + g.xresize.diff);
                                var contentWidth = g.left.width();
                                if(g.left.header) contentWidth-= g.left.header.width();
                                g.left.content.width(contentWidth - (g.left.content.outerWidth()-g.left.content.width()));
                            }
                        }
                    }
                    else if (g.yresize && g.yresize.diff != undefined) {
                        if (g.dragtype == 'topresize') {
                            g.top.height(g.top.height() + g.yresize.diff);
                            var contentHeight = g.top.height();
                            if(g.top.header) contentHeight-= g.top.header.height();
                            g.top.content.height(contentHeight);
                            g.middleTop += g.yresize.diff;
                            g.middleHeight -= g.yresize.diff;
                            if (g.left) {
                                g.left.css({ top: g.middleTop }).height(g.middleHeight - (g.left.outerHeight()-g.left.height()));
                                g.leftCollapse.css({ top: g.middleTop }).height(g.middleHeight - (g.left.outerHeight()-g.left.height()));
                                contentHeight = g.left.height();
                                if(g.left.header) contentHeight-= g.left.header.height();
                                g.left.content.height(contentHeight - (g.left.content.outerHeight()-g.left.content.height()));
                            }
                            if (g.center) {
                            	g.center.css({ top: g.middleTop }).height(g.middleHeight - (g.center.outerHeight()-g.center.height()));
                            	contentHeight = g.center.height();
                                if(g.center.header) contentHeight-= g.center.header.height();
                                g.center.content.height(contentHeight - (g.center.content.outerHeight()-g.center.content.height()));
                            }
                            if (g.right) {
                                g.right.css({ top: g.middleTop }).height(g.middleHeight - (g.right.outerHeight()-g.right.height()));
                                g.rightCollapse.css({ top: g.middleTop }).height(g.middleHeight - (g.right.outerHeight()-g.right.height()));
                                contentHeight = g.right.height();
                                if(g.right.header) contentHeight-= g.right.header.height();
                                g.right.content.height(contentHeight - (g.right.content.outerHeight()-g.right.content.height()));
                            }
                        }
                        else if (g.dragtype == 'bottomresize') {
                        	var height = g.bottom.height();
                        	g.bottom.height(0);
                            g.middleHeight += g.yresize.diff;
                            g.bottomTop += g.yresize.diff;
                            g.bottom.css({ top: g.bottomTop });
                            g.bottom.height(height - g.yresize.diff);
                            var contentHeight = g.bottom.height();
                            if(g.bottom.header) contentHeight-= g.bottom.header.height();
                            g.bottom.content.height(contentHeight - (g.bottom.content.outerHeight()-g.bottom.content.height()));
                            if (g.left) {
                                g.left.height(g.middleHeight - (g.left.outerHeight()-g.left.height()));
                                g.leftCollapse.height(g.middleHeight - (g.leftCollapse.outerHeight()-g.leftCollapse.height()));
                                contentHeight = g.left.height();
                                if(g.left.header) contentHeight-= g.left.header.height();
                                g.left.content.height(contentHeight - (g.left.content.outerHeight()-g.left.content.height()));
                            }
                            if (g.center) {
                            	g.center.height(g.middleHeight - (g.center.outerHeight()-g.center.height()));
                            	contentHeight = g.center.height();
                                if(g.center.header) contentHeight-= g.center.header.height();
                                g.center.content.height(contentHeight - (g.center.content.outerHeight()-g.center.content.height()));
                            }
                            if (g.right) {
                                g.right.height(g.middleHeight - (g.right.outerHeight()-g.right.height()));
                                g.rightCollapse.height(g.middleHeight - (g.rightCollapse.outerHeight()-g.rightCollapse.height()));
                                contentHeight = g.right.height();
                                if(g.right.header) contentHeight-= g.right.header.height();
                                g.right.content.height(contentHeight - (g.right.content.outerHeight()-g.right.content.height()));
                            }
                        }
                    }
                    g.setDropHandlePosition();
                    g.draggingxline.hide();
                    g.draggingyline.hide();
                    g.xresize = g.yresize = g.dragtype = false;
                    g.layout.lock.hide();
                    if ($.browser.msie || $.browser.safari)
                        $('body').unbind('selectstart');
                    $(document).unbind('mousemove', g.drag);
                    $(document).unbind('mouseup', g.stop);
                    $('body').css('cursor', '');
                }
            };
            g.layout = $(this);
            $(this).css('overflow', 'hidden');
            if (!g.layout.hasClass("l-layout"))
                g.layout.addClass("l-layout");
            g.width = g.layout.width();
            //top
            if ($("> div[position=top]", g.layout).length > 0) {
                g.top = $("> div[position=top]", g.layout).wrap('<div class="l-layout-top" style="top:0px;"></div>').parent();
               
                g.top.content = $("> div[position=top]", g.top);
                
                var toptitle = g.top.content.attr("title");
               	if(toptitle) {
	                g.top.header = $('<div class="l-layout-header"><div class="l-layout-header-toggle"></div><div class="l-layout-header-inner"></div></div>');
	                g.top.prepend(g.top.header);
	                g.top.header.toggle = $(".l-layout-header-toggle", g.top.header);
                }
                
                if (!g.top.content.hasClass("l-layout-content"))
                    g.top.content.addClass("l-layout-content");
               
                if(!p.allowTopCollapse) $(".l-layout-header-toggle", g.top.header).remove();
                //set title
                if (toptitle) {
                    g.top.content.attr("title", "");
                    $(".l-layout-header-inner", g.top.header).html(toptitle);
                }
                
                if (g.topHeight) {
                    g.top.height(g.topHeight - (g.top.outerHeight()-g.top.height()));
                    var contentHeight = g.top.height();
                    if(g.top.header) contentHeight-= g.top.header.height();
                    g.top.content.height(contentHeight - (g.top.content.outerHeight()-g.top.content.height()));
                }
            }

            //bottom
            if ($("> div[position=bottom]", g.layout).length > 0) {
                g.bottom = $("> div[position=bottom]", g.layout).wrap('<div class="l-layout-bottom"></div>').parent();
                
                
                g.bottom.content = $("> div[position=bottom]", g.bottom);
                
                var bottomtitle = g.bottom.content.attr("title");
                if(bottomtitle) {
	                g.bottom.header = $('<div class="l-layout-header"><div class="l-layout-header-toggle"></div><div class="l-layout-header-inner"></div></div>');
	                g.bottom.prepend(g.bottom.header);
	                g.bottom.header.toggle = $(".l-layout-header-toggle", g.bottom.header);
              	}
                
                if (!g.bottom.content.hasClass("l-layout-content"))
                    g.bottom.content.addClass("l-layout-content");
                    
                if(!p.allowBottomCollapse) $(".l-layout-header-toggle", g.bottom.header).remove();
                //set title
               	if (bottomtitle) {
                    g.bottom.content.attr("title", "");
                    $(".l-layout-header-inner", g.bottom.header).html(bottomtitle);
                }
                g.bottomHeight = p.bottomHeight;
                if (g.bottomHeight) {
                    g.bottom.height(g.bottomHeight - (g.bottom.outerHeight()-g.bottom.height()));
                    var contentHeight = g.bottom.height();
                    if(g.bottom.header) contentHeight-= g.bottom.header.height();
                    g.bottom.content.height(contentHeight - (g.bottom.content.outerHeight()-g.bottom.content.height()));
                }

            }
            //left
            if ($("> div[position=left]", g.layout).length > 0) {
                g.left = $("> div[position=left]", g.layout).wrap('<div class="l-layout-left" style="left:0px;"></div>').parent();
                g.left.header = $('<div class="l-layout-header"><div class="l-layout-header-toggle"></div><div class="l-layout-header-inner"></div></div>');
                g.left.prepend(g.left.header);
                g.left.header.toggle = $(".l-layout-header-toggle", g.left.header);
                g.left.content = $("> div[position=left]", g.left);
                if (!g.left.content.hasClass("l-layout-content"))
                    g.left.content.addClass("l-layout-content");
                if(!p.allowLeftCollapse) $(".l-layout-header-toggle", g.left.header).remove();
                //set title
                var lefttitle = g.left.content.attr("title");
                if (lefttitle) {
                    g.left.content.attr("title", "");
                    $(".l-layout-header-inner", g.left.header).html(lefttitle);
                }
                //set width
                g.leftWidth = p.leftWidth;
                if (g.leftWidth) {
                    g.left.width(g.leftWidth - (g.left.outerWidth()-g.left.width()));
	                g.left.content.width(g.left.width() - (g.left.content.outerWidth()-g.left.content.width()));
                }
            }
            //center
            if ($("> div[position=center]", g.layout).length > 0) {
                g.center = $("> div[position=center]", g.layout).wrap('<div class="l-layout-center" ></div>').parent();
                g.center.content = $("> div[position=center]", g.center);
                g.center.content.addClass("l-layout-content");
                //set title
                var centertitle = g.center.content.attr("title");
                if (centertitle) {
                    g.center.content.attr("title", "");
                    g.center.header = $('<div class="l-layout-header"></div>');
                    g.center.prepend(g.center.header);
                    g.center.header.html(centertitle);
                }
                //set width
                g.centerWidth = p.centerWidth;
                g.centerHeight = p.centerWidth;
                if (g.centerWidth) {
                    g.center.width(g.centerWidth - (g.center.outerWidth()-g.center.width()));
                    g.center.height(g.centerHeight - (g.center.outerHeight()-g.center.height()));
                }
            }
            //right
            if ($("> div[position=right]", g.layout).length > 0) {
                g.right = $("> div[position=right]", g.layout).wrap('<div class="l-layout-right"></div>').parent();

                g.right.header = $('<div class="l-layout-header"><div class="l-layout-header-toggle"></div><div class="l-layout-header-inner"></div></div>');
                g.right.prepend(g.right.header);
                g.right.header.toggle = $(".l-layout-header-toggle", g.right.header);
                if(!p.allowRightCollapse) $(".l-layout-header-toggle", g.right.header).remove();
                g.right.content = $("> div[position=right]", g.right);
                if (!g.right.content.hasClass("l-layout-content"))
                    g.right.content.addClass("l-layout-content");

                //set title
                var righttitle = g.right.content.attr("title");
                if (righttitle) {
                    g.right.content.attr("title", "");
                    $(".l-layout-header-inner", g.right.header).html(righttitle);
                }
                //set width
                g.rightWidth = p.rightWidth;
                if (g.rightWidth) {
                    g.right.width(g.rightWidth - (g.right.outerWidth()-g.right.width()));
	                g.right.content.width(g.right.width() - (g.right.content.outerWidth()-g.right.content.width()));
                }
            }
            //lock
            g.layout.lock = $("<div class='l-layout-lock'></div>");
            g.layout.append(g.layout.lock);
            //DropHandle
            g.addDropHandle();

            //Collapse
            g.isLeftCollapse = p.isLeftCollapse;
            g.isRightCollapse = p.isRightCollapse;
            g.leftCollapse = $('<div class="l-layout-collapse-left" style="display: none; "><div class="l-layout-collapse-left-toggle"></div></div>');
            g.rightCollapse = $('<div class="l-layout-collapse-right" style="display: none; "><div class="l-layout-collapse-right-toggle"></div></div>');
            g.isTopCollapse = p.isTopCollapse;
            g.isBottomCollapse = p.isBottomCollapse;
            g.topCollapse = $('<div class="l-layout-collapse-top" style="display: none; "><div class="l-layout-collapse-top-toggle"></div></div>');
            g.bottomCollapse = $('<div class="l-layout-collapse-bottom" style="display: none; "><div class="l-layout-collapse-bottom-toggle"></div></div>');
            g.layout.append(g.leftCollapse).append(g.rightCollapse).append(g.topCollapse).append(g.bottomCollapse);
            g.leftCollapse.toggle = $("> .l-layout-collapse-left-toggle", g.leftCollapse);
            g.rightCollapse.toggle = $("> .l-layout-collapse-right-toggle", g.rightCollapse);
            g.topCollapse.toggle = $("> .l-layout-collapse-top-toggle", g.topCollapse);
            g.bottomCollapse.toggle = $("> .l-layout-collapse-bottom-toggle", g.bottomCollapse);
            g.setCollapse();

            //init
            g.init();
            $(window).resize(function () {
                g.onResize();
            });
            if (this.id == undefined) this.id = "LigerUI_" + new Date().getTime();
            LigerUIManagers[this.id + "_Layout"] = g;
            this.usedLayout = true;
        });
        if (this.length == 0) return null;
        if (this.length == 1) return LigerUIManagers[this[0].id + "_Layout"];
        var managers = [];
        this.each(function() {
            managers.push(LigerUIManagers[this.id + "_Layout"]);
        });
        return managers;
    };
})(jQuery);