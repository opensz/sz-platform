//定义人员数据模型
Ext.define('staff', {extend : 'Ext.data.Model',
	fields : [
	          {name : 'id',type : 'int'}, 
	          {name : 'org.mainSite.address',type : 'String'},
	          {name : 'org.fullName',type : 'String'},
	          {name : 'org'},
	          {name : 'empId',type : 'String'}, 
	          {name : 'fullName',type : 'String'}, 
	          {name : 'nickName',type : 'String'}, 
	          {name : 'dept.name',type : 'String'}, 
	          {name : 'title',type : 'String'}, 
	          {name : 'phone',type : 'String'}, 
	          {name : 'mobile',type : 'String'}, 
	          {name : 'qq',type : 'String'},
	          {name : 'email',type : 'String'},
	          {name : 'vip',type : 'boolean'},
	          {name : 'remarks',type : 'String'}
	         ]
});

Ext.define('requestCase', {extend : 'Ext.data.Model',
	idProperty : 'id',
	fields : [ 
	           {name : 'requestId',type : 'int'}, 
	           {name : 'requestDesc',type : 'string'}, 
	           {name : 'id',type : 'int'}, 
	           {name : 'caseNo',type : 'String'}, 
	           {name : 'priority',type : 'String'}, 
	           {name : 'serviceLevel'}, 
	           {name : 'caseType',type : 'String'}, 
	           {name : 'serviceItem'}, 
	           {name : 'requester'}, 
	           {name : 'title',type : 'String'}, 
	           {name : 'requestTime',type : 'String'}, 
	           {name : 'expectTime',type : 'String'},
	           {name : 'sysStatusId',type : 'Long'}
	          ]
});