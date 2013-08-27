//验证英文字母
jQuery.validator.addMethod('英文字母', function(value, element) {
	return this.optional(element) || /^[a-zA-Z]+$/.test(value);
}, '请输入英文字母');

//验证非负整数
jQuery.validator.addMethod('非负整数', function(value, element) {
	return this.optional(element) || /^\d+$/.test(value);
}, '请输入非负整数');

//验证输入英文与数字
jQuery.validator.addMethod('英数字', function(value, element) {
	return this.optional(element) || /^[a-zA-Z0-9]+$/.test(value);
}, '请输入英文字母和数字');

//验证汉字
jQuery.validator.addMethod('汉字', function(value, element) {
	return this.optional(element) || /^[\u4E00-\u9FA5]+$/.test(value);
}, '请输入汉字');

//验证负整数
jQuery.validator.addMethod('负整数', function(value, element) {
	return this.optional(element) || /^-{1}\d+$/.test(value);
}, '请输入负整数');

//验证正整数
jQuery.validator.addMethod('正整数', function(value, element) {
	return this.optional(element) || /^[1-9]+\d*$/.test(value);
}, '请输入正整数');

//验证整数
jQuery.validator.addMethod('整数', function(value, element) {
	return this.optional(element) || /^-?\d+$/.test(value);
}, '请输入整数');

//请输入有效的QQ号码
jQuery.validator.addMethod('QQ号码', function(value, element) {
	return this.optional(element) || /^[1-9]*[1-9][0-9]*$/.test(value);
}, '验证QQ号码');

//email格式输入有误
jQuery.validator.addMethod('email', function(value, element) {
	return this.optional(element) || /^\w+([-+.]\w+)*@\w+([-.]\w+)*.\w+([-.]\w+)*$/.test(value);
}, 'email规则验证');

//手机号码输入有误
jQuery.validator.addMethod('手机号码', function(value, element) {
	return this.optional(element) || /^(((13[0-9]{1})|(15[0-9]{1}))+\d{8})$/.test(value);
}, '验证手机号码');

