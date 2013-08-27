
(function($) {


	$.ligerMsg = {
		_alertButFrag : '<input callback="#callback#" type="button"  value=" #butMsg# "></input>',
		_alertBoxFrag : '<div id="alertMsgBox" class="msg"><div class="msgContent"><div class="#type#"><div class="msgInner"><h1>#title#</h1><div class="message">#message#</div></div><div class="toolBar"><ul>#butFragment#</ul></div></div></div></div>',

		_boxId : "#alertMsgBox",
		_closeTimer : null,

		_types : {
			error : "error",
			info : "info",
			warn : "warn",
			correct : "correct",
			confirm : "confirm"
		},

		_title : {
			error : "错误",
			info : "温馨提示",
			warn : "警告",
			correct : "成功",
			confirm : "请确定"
		},
		_butMsg : {
			ok : "确定",
			yes : "是",
			no : "否",
			cancel : "取消"
		},

		_getTitle : function(key) {
			return this._title[key];
		},

		/**
		 * 
		 * @param {Object}
		 *            type
		 * @param {Object}
		 *            msg
		 * @param {Object}
		 *            bottom
		 * @param {Object}
		 *            buttons [button1, button2]
		 */
		_open : function(type, msg, bottom, buttons) {
			
			$(top.document).find(this._boxId).remove();
			if (!(this._types.info == type || this._types.correct == type)) {
				$("<div class='l-window-mask' style='display: block;'></div>")
						.appendTo(top.document.body);
			}
			var butsHtml = "";
			if (buttons) {
				for ( var i = 0; i < buttons.length; i++) {
					var sRel = buttons[i].call ? "callback" : "";
					butsHtml += this._alertButFrag.replace("#butMsg#",
							buttons[i].name).replace("#callback#", sRel) + "&nbsp;";
				}
			}
			var boxHtml = this._alertBoxFrag.replace("#type#", type).replace(
					"#title#", this._getTitle(type)).replace("#message#", msg)
					.replace("#butFragment#", butsHtml);
			if (bottom != true) {
				$(boxHtml).appendTo(top.document.body).css({
					top : -$(top.document).find(this._boxId).height() + "px"
				}).animate({
					top : "0px"
				}, 500);
			} else {
				var height = $(top.document.body).height();
				$(boxHtml).css({
					top : height + "px",
					left : "auto",
					right : "10px"
				}).appendTo(top.document.body).animate({
					top : height - $(top.document).find(this._boxId).height()
				}, 500);
			}
			if (this._closeTimer) {
				clearTimeout(this._closeTimer);
				this._closeTimer = null;
			}
			if (this._types.info == type || this._types.correct == type) {
				this._closeTimer = setTimeout(function() {
					$.ligerMsg.close()
				}, 3500);
			}

			var jCallButs = $(top.document).find(this._boxId).find(
					"[callback=callback]");
			for ( var i = 0; i < buttons.length; i++) {
				if (buttons[i].call){
					jCallButs.eq(i).click(buttons[i].call);
				}
				
			}
			
		},
		_alert : function(type, msg, bottom,callback) {
			var op = {
				okName : this._butMsg.ok,
				okCall : callback
			};
			var buttons = [ {
				name : op.okName,
				call : op.okCall
			} ];
			this._open(type, msg, bottom, buttons);
		},
		close : function() {
			if ($(top.document).find(this._boxId).length > 0) {
				if ($(top.document).find(this._boxId).position().top <= 0) {
					$(top.document).find(this._boxId).animate({
						top : -$(top.document).find(this._boxId).height()
					}, 500, function() {
						$(this).remove();
					});
				} else {
					var height = $(top.document.body).height();
					$(top.document).find(this._boxId).animate({
						top : height
					}, 500, function() {
						$(this).remove();
					});
				}
			}
			$(top.document).find(".l-window-mask").remove();
		},
		error : function(msg, bottom,callback) {
			this._alert(this._types.error, msg, bottom,callback);
		},
		info : function(msg, bottom,callback) {
			this._alert(this._types.info, msg, bottom,callback);
		},
		warn : function(msg, bottom,callback) {
			this._alert(this._types.warn, msg, bottom,callback);
		},
		correct : function(msg, bottom,callback) {
			this._alert(this._types.correct, msg, bottom,callback);
		},

		/**
		 * 
		 * @param {Object}
		 *            msg
		 * @param {Object}
		 *            callback
		 * @param {Object}
		 *            bottom
		 */
		confirm : function(msg, callback, bottom) {
			var op = {
				okName : this._butMsg.ok,
				okCall : null,
				cancelName : this._butMsg.cancel,
				cancelCall : null
			};
			var buttons = [ {
				name : op.okName,
				call : callback
			}, {
				name : op.cancelName
			} ];
			this._open(this._types.confirm, msg, bottom, buttons);
		}
	};
})(jQuery);