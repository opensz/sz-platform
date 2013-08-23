<%@ page language="java" pageEncoding="UTF-8"%>
<link rel="stylesheet" media="all" type="text/css" href="${pageContext.request.contextPath}/libs/jquery/flexigrid/flexigrid.css">
<link rel="stylesheet" media="all" type="text/css" href="${pageContext.request.contextPath}/libs/jquery/datepicker/jquery-ui-datepicker-1.8.23.custom.css">
<link rel="stylesheet" media="all" type="text/css" href="${pageContext.request.contextPath}/libs/jquery/combox/jquery-ui-combox-1.8.23.custom.css">
<link rel="stylesheet" media="all" type="text/css" href="${pageContext.request.contextPath}/libs/jquery/button/jquery-ui-button-1.8.23.custom.css">
<link rel="stylesheet" media="all" type="text/css" href="${pageContext.request.contextPath}/libs/jquery/window/colorbox.css">
<link rel="stylesheet" media="all" type="text/css" href="${pageContext.request.contextPath}/libs/extjs-4.1.1/resources/css/ext-all.css">
<link rel="stylesheet" media="all" type="text/css" href="${pageContext.request.contextPath}/libs/jquery/ztree/css/demo.css">
<link rel="stylesheet" media="all" type="text/css" href="${pageContext.request.contextPath}/libs/jquery/ztree/css/zTreeStyle/zTreeStyle.css">
<link rel="stylesheet" media="all" type="text/css" href="${pageContext.request.contextPath}/libs/jquery/tabs/jquery-ui-tabs-1.8.23.custom.css">
<link rel="stylesheet" media="all" type="text/css" href="${pageContext.request.contextPath}/libs/jquery/slider/jquery-ui-slider-1.8.23.custom.css">


<script type="text/javascript" src="${pageContext.request.contextPath}/libs/jquery/jquery-1.7.2.js" charset="utf-8">
</script>
<script type="text/javascript" src="${pageContext.request.contextPath}/libs/jquery/jquery.cookie.js" charset="utf-8">
</script>
<script type="text/javascript" src="${pageContext.request.contextPath}/libs/jquery/flexigrid/flexigrid.js" charset="utf-8">
</script>
<script type="text/javascript" src="${pageContext.request.contextPath}/libs/jquery/datepicker/jquery-ui-datepicker-1.8.23.min.js" charset="utf-8">
</script>
<script type="text/javascript" src="${pageContext.request.contextPath}/libs/jquery/datepicker/jquery-ui-timepicker-addon.js" charset="utf-8">
</script>
<script type="text/javascript" src="${pageContext.request.contextPath}/libs/jquery/combox/jquery-ui-combox-1.8.23.custom.min.js" charset="utf-8">
</script>
<script type="text/javascript" src="${pageContext.request.contextPath}/libs/jquery/button/jquery-ui-button-1.8.23.custom.min.js" charset="utf-8">
</script>
<script type="text/javascript" src="${pageContext.request.contextPath}/libs/jquery/window/jquery.colorbox.js" charset="utf-8">
</script>

<script type="text/javascript" src="${pageContext.request.contextPath}/libs/extjs-4.1.1/bootstrap.js" charset="utf-8">
</script>
<script type="text/javascript" src="${pageContext.request.contextPath}/libs/extjs-4.1.1/ext-lang-zh_CN.js" charset="utf-8">
</script>
<script type="text/javascript" src="${pageContext.request.contextPath}/libs/jquery/ztree/jquery.ztree.core-3.4.min.js" charset="utf-8">
</script>
<script type="text/javascript" src="${pageContext.request.contextPath}/libs/jquery/ztree/jquery.ztree.excheck-3.4.min.js" charset="utf-8">
</script>
<script type="text/javascript" src="${pageContext.request.contextPath}/libs/jquery/ztree/jquery.ztree.exedit-3.4.min.js" charset="utf-8">
</script>
<script type="text/javascript" src="${pageContext.request.contextPath}/libs/jquery/ztree/jquery.ztree.exhide-3.4.min.js" charset="utf-8">
</script>
<script type="text/javascript" src="${pageContext.request.contextPath}/libs/jquery/tabs/jquery-ui-tabs-1.8.23.custom.min.js" charset="utf-8">
</script>
<script type="text/javascript" src="${pageContext.request.contextPath}/libs/jquery/slider/jquery-ui-slider-1.8.23.custom.min.js" charset="utf-8">
</script>

<script type="text/javascript" src="${pageContext.request.contextPath}/libs/jquery/mutilcombox.js" charset="utf-8">
</script>



<!-- 以下是Combox 的支持库  ，因为用到CSS 官方也没有放到一个文件中，所以单独拿出来包含进行支持 -->
<style>
.ui-combobox {
	position: relative;
	display: inline-block;
}
.ui-combobox-toggle {
	position: absolute;
	top: 0;
	bottom: 0;
	margin-left: -1px;
	padding: 0;
	/* adjust styles for IE 6/7 */
	*height: 1.7em;
	*top: 0.1em;
}
.ui-combobox-input {
	margin: 0;
	padding: 0.3em;
}
</style>

<script type="text/javascript" charset="utf-8">
(function( $ ) {
	$.widget( "ui.combobox", {
		_create: function() {
			var input,
				self = this,
				select = this.element.hide(),
				selected = select.children( ":selected" ),
				value = selected.val() ? selected.text() : "",
				wrapper = this.wrapper = $( "<span>" )
					.addClass( "ui-combobox" )
					.insertAfter( select );
				input = $( "<input>" ).appendTo( wrapper ).val( value ).addClass( "ui-state-default ui-combobox-input" )
				.autocomplete({
					delay: 0,
					minLength: 0,
					source: function( request, response ) {
						var matcher = new RegExp( $.ui.autocomplete.escapeRegex(request.term), "i" );
						response( select.children( "option" ).map(function() {
							var text = $( this ).text();
							if ( this.value && ( !request.term || matcher.test(text) ) )
								return {
									label: text.replace(
										new RegExp(
											"(?![^&;]+;)(?!<[^<>]*)(" +
											$.ui.autocomplete.escapeRegex(request.term) +
											")(?![^<>]*>)(?![^&;]+;)", "gi"
										), "<strong>$1</strong>" ),
									value: text,
									option: this
								};
						}) );
					},
					select: function( event, ui ) {
						ui.item.option.selected = true;
						self._trigger( "selected", event, {
							item: ui.item.option
						});
						//进行联动
						//console.log(ui.item.option.text);
						self.link(ui.item.option.value);
					},
					change: function( event, ui ) {
						if ( !ui.item ) {
							var matcher = new RegExp( "^" + $.ui.autocomplete.escapeRegex( $(this).val() ) + "$", "i" ),
								valid = false;
							select.children( "option" ).each(function() {
								if ( $( this ).text().match( matcher ) ) {
									this.selected = valid = true;
									return false;
								}
							});
							if ( !valid ) {
								// remove invalid value, as it didn't match anything
								$( this ).val( "" );
								select.val( "" );
								input.data( "autocomplete" ).term = "";
								return false;
							}
						}
					}
				})
				.addClass( "ui-widget ui-widget-content ui-corner-left" );

				input.data( "autocomplete" )._renderItem = function( ul, item ) {
					return $( "<li></li>" )
						.data( "item.autocomplete", item )
						.append( "<a>" + item.label + "</a>" )
						.appendTo( ul );
				};
				$( "<a>" )
					.attr( "tabIndex", -1 )
					.attr( "title", "显示所有" )
					.appendTo( wrapper )
					.button({
						icons: {
							primary: "ui-icon-triangle-1-s"
						},
						text: false
					})
					.removeClass( "ui-corner-all" )
					.addClass( "ui-corner-right ui-combobox-toggle" )
					.click(function() {
						// close if already visible
						if ( input.autocomplete( "widget" ).is( ":visible" ) ) {
							input.autocomplete( "close" );
							return;
						}

						// work around a bug (likely same cause as #5265)
						$( this ).blur();

						// pass empty string as value to search for, displaying all results
						input.autocomplete( "search", "" );
						input.focus();
					});
			},

			destroy: function() {
				this.wrapper.remove();
				this.element.show();
				$.Widget.prototype.destroy.call( this );
			},
			//联动
			link:function(value){
				var self=this;
				//自定义，加入联动   A： zhang
				var linkable=self.options.linkable;			//是否可联动
				var target=self.options.target;				//联动目标Tag 标签
				var url=self.options.url;				//联动URL
				var require=self.options.require;			//是否必填		如果非必填会有 --请选择---
				var queryName=self.options.queryName;		//自定义参数
				
				if(linkable !=undefined && linkable){
					
					if(target == undefined || target == null ){
						$.error("target property is can't be null :目标标签不能为空");
						return;
					}
					
					if(url == undefined || url ==  null){
						$.error("url property is can't be null :目标URL不能为空");
						return;
					}
					
					if(require == undefined || require ==  null){
						require =false;
					}
					
					if(queryName == undefined || queryName ==  null){
						queryName="query";
					}
						target=$("select[name='"+target+"']");			//获取标签对象
						url=url+"?"+queryName+"="+value;					//组装URL
						//ajax request
						$.ajax({
							url:url,			//url
							dataType:'json',
							type: "POST",
							success:function(data, textStatus, jqXHR){
								//删除所有子节点 option
								target.empty();
								//循环 加入节点
								$.each(data, function(i,item){
									var label=item.label;
									var value=item.value;
								    //加入新的节点
								    target.append("<option value='"+value+"' index='"+i+"'>"+label+"</option>");
								  });
								//如果未必填
								if(!require){
									 target.append("<option value='' index='-1'>--请选择--</option>");
								}
							},
							error:function(XMLHttpRequest, textStatus, errorThrown){
								$.error("出现错误:"+errorThrown);
							}
						});
				}
			}
	});
})( jQuery );
</script>

<!-- 设置默认日期控件的显示格式 -->
<script type="text/javascript">
	$.datepicker.setDefaults({
		dayNames:["星期日","星期一","星期二","星期三","星期四","星期五","星期六"],
		dayNamesMin:["日","一","二","三","四","五","六"],
		monthNames:["一月","二月","三月","四月","五月","六月","七月","八月","九月","十月","十一月","十二月"],
		currentText:'今天',
		dateFormat:'yy-mm-dd',
		closeText:'关闭',
		
	});
</script>
