/**
 * 服务目标model
 */
Ext.define('serviceTarget', {
	extend : 'Ext.data.Model',
	fields : [{name : 'id',type : 'int'}, 
	          {name : 'caseType',type : 'String'},
	          {name : 'targetType',type : 'String'},
	          {name : 'description',type : 'String'},
	          {name : 'enabled',type : 'String'}, 
	          {name : 'targetValue',type : 'String'}, 
	          {name : 'minValue',type : 'String'}, 
	          {name : 'maxValue',type : 'String'}]
});
