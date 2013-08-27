var DesktopManage = function() {
	this.desktop = null;
	this.deskColumns = [];
	this.thisDiv = null;
	this.setDesktop = function() {
		var a = this;
		this.desktop = {
			sortable : true,
			create : function() {
			},
			removeItem : function(c, b) {
				a.removeColumn(c, b);
			},
			afterDrag : function() {
				a.sortDesk();
			},
			columns : a.deskColumns
		};
	};
	this.init = function() {
		var b = this;
		var a = $("#layoutId").val();
		var c = __ctx + "/platform/system/desktop/getMyDesktop.xht";
		if (a) {
			c = __ctx + "/platform/system/desktop/getLayoutData.xht?layoutId="
					+ a;
		}
		$.post(c, function(d) {
			b.handerData(d);
			b.setDesktop();
			$("#myPage").portlet(b.desktop);
		});
	};
	this.handerData = function(g) {
		var o = this;
		o.deskColumns = [];
		var l = document.body.offsetWidth;
		for ( var h = 0; h < g.columns.length; h++) {
			var d = g.widthMap[h + 1] * (l - 30) / 100;
			var k = new Array();
			if (g.datas[h + 1] != [] && g.datas[h + 1] != null) {
				for ( var f = 0; f < g.datas[h + 1].length; f++) {
					var n = g.datas[h + 1][f];
					var c = n.columnId;
					if (!c) {
						c = n.id;
					}
					var a = "";
					if (n.columnUrl != null) {
						a = __ctx + n.columnUrl;
					}
					var b = __ctx
							+ "/platform/system/desktopColumn/getColumnData.xht?id="
							+ c;
					var m = {
						attrs : {
							id : c
						},
						title : n.columnName,
						icon : "ui-icon-signal-diag",
						more : n.columnUrl,
						content : {
							style : {
								height : "320px"
							},
							type : "ajax",
							dataType : "json",
							url : b,
							formatter : function(s, i, r) {
								var p = "";
								if (r.html) {
									p = r.html;
								} else {
									var q = r.column.html;
									var j = r.result;
									p = easyTemplate(q, j).toString();
								}
								return p;
							}
						}
					};
					k.push(m);
				}
			}
			var e = {
				width : d,
				portlets : k
			};
			o.deskColumns.push(e);
		}
	};
	this.changeClass = function(b) {
		var a = this;
		if ($(b).hasClass("active")) {
			$(b).removeClass("active");
			a.thisDiv = null;
			a.thisColumn = null;
		} else {
			$(".ui-sortable").removeClass("active");
			$(b).addClass("active");
			a.thisDiv = $(".active");
		}
	};
	this.addColumn = function() {
		var b = this;
		if (b.thisDiv == null) {
			$.ligerDialog.error("请选择一列！", "提示信息");
			return;
		}
		var d = $(b.thisDiv).index();
		var g = $("#columnName").val();
		var a = $("select option:selected").attr("name");
		var f = $("select option:selected").attr("moreUrl");
		var c = __ctx + "/platform/system/desktopColumn/getColumnData.xht?id="
				+ g;
		var e = {
			attrs : {
				id : g
			},
			title : a,
			more : f,
			icon : "ui-icon-signal-diag",
			content : {
				style : {
					height : "320px"
				},
				type : "ajax",
				dataType : "json",
				url : c,
				formatter : function(m, h, l) {
					var j = "";
					if (l.html) {
						j = l.html;
					} else {
						var k = l.column.html;
						var i = l.result;
						j = easyTemplate(k, i).toString();
					}
					return j;
				}
			}
		};
		b.deskColumns[d].portlets.push(e);
		b.setDesktop();
		b.resetDesk();
		b.thisDiv = null;
	};
	this.removeColumn = function(c, b) {
		var a = this;
		a.deskColumns[b].portlets.splice(c, 1);
		a.setDesktop();
	};
	this.resetDesk = function() {
		var a = $("#myPage").clone();
		$("#myPage").remove();
		$(a).empty();
		$(".panel-body").append(a);
		$("#myPage").portlet(this.desktop);
	};
	this.changLayout = function() {
		var b = this;
		var a = $("#colsSelect").val();
		var c = __ctx + "/platform/system/desktop/getLayoutData.xht?layoutId="
				+ a;
		$.post(c, function(d) {
			b.handerData(d);
			b.setDesktop();
			b.resetDesk();
		});
	};
	this.sortDesk = function() {
		var a = this;
		var c = $("#myPage");
		var b = new Array();
		$(c)
				.find(".ui-portlet-column")
				.each(
						function() {
							var e = $(this).width();
							var d = new Array();
							if ($(this).children().length == 0) {
								b.push({
									width : e,
									portlets : []
								});
								return true;
							}
							$(this)
									.find(".ui-portlet-item")
									.each(
											function(h) {
												var k = $(this).attr("id");
												var f = $(this).attr("title");
												var i = $(this).attr("moreurl");
												var g = __ctx
														+ "/platform/system/desktopColumn/getColumnData.xht?id="
														+ k;
												var j = {
													attrs : {
														id : k
													},
													title : f,
													more : i,
													icon : "ui-icon-signal-diag",
													content : {
														style : {
															height : "320px"
														},
														type : "ajax",
														dataType : "json",
														url : g,
														formatter : function(r,
																l, q) {
															var n = "";
															if (q.html) {
																n = q.html;
															} else {
																var p = q.column.html;
																var m = q.result;
																n = easyTemplate(
																		p, m)
																		.toString();
															}
															return n;
														}
													}
												};
												d[h] = j;
											});
							b.push({
								width : e,
								portlets : d
							});
						});
		a.deskColumns = b;
	};
	this.saveLayout = function() {
		var k = this;
		if (k.deskColumns == []) {
			return;
		}
		var g = $("#colsSelect").val();
		var b = __ctx + "/platform/system/desktopMycolumn/saveCol.xht";
		if (!g) {
			g = $("#layoutId").val();
			b = __ctx + "/platform/system/desktopLayoutcol/saveCol.xht";
		}
		var h = k.deskColumns.length;
		var e = "[";
		for ( var f = 0; f < h; f++) {
			var c = k.deskColumns[f].portlets;
			if (c) {
				for ( var d = 0; d < c.length; d++) {
					var a = c[d].attrs.id;
					if (e != "[") {
						e += ",";
					}
					e += '{"col":' + (f + 1) + ',"sn":' + (d + 1)
							+ ',"columnId":' + a + "}";
				}
			}
		}
		e += "]";
		$.post(b, {
			"layoutId" : g,
			"data" : e
		}, function(j) {
			var i = $.parseJSON(j);
			if (i.result == 1) {
				$.ligerDialog.success(i.message, "提示信息", function() {
					window.location.reload();
				});
			} else {
				$.ligerDialog.error(i.message, "提示信息", function() {
					window.location.reload();
				});
			}
		});
	};
};
function getMore(a) {
	$.post(__ctx + "/platform/console/getResourceNode.xht?columnUrl=" + a,
			function(b) {
				if (b == null) {
					$.ligerMessageBox.error("提示信息", "栏目更多路径配置有误");
				} else {
					addToTab(b.defaultUrl, b.resName, b.id, b.icon);
				}
			});
}
function addToTab(b, a, d, c) {
	top.tab.addTabItem({
		url : __ctx + b,
		text : a,
		tabid : d,
		icon : __ctx + c
	});
}