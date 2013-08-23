var	curOrgId="";//全局变量客户id
var curDeptId="";
var assetStatus="1408";//资产相关数据状态

/**
 * 组织选择器
 * @param conf
 * 
 * conf 参数
 * 
 * orgId：组织ID
 * orgName:组织名称
 * @returns
 */
function OrgDialog(conf)
{
	var dialogWidth=900;
	var dialogHeight=700;
	var type="org";//客户,org:客户|project:项目|dept:部门|group:流程组
	if(conf!=null && (conf.type!=null || conf.type!=undefined)){
		type=conf.type;
	}
	conf=$.extend({},{dialogWidth:dialogWidth ,dialogHeight:dialogHeight ,help:0,status:0,scroll:0,center:1},conf);

	var winArgs="dialogWidth="+conf.dialogWidth+"px;dialogHeight="+conf.dialogHeight
		+"px;help=" + conf.help +";status=" + conf.status +";scroll=" + conf.scroll +";center=" +conf.center;
	
	var url=__ctx + '/platform/system/sysOrg/dialog.xht?type='+type;
	if(type=="org"){
		url=__ctx + '/platform/system/sysOrg/dialog.xht?type='+type+"&orgId="+curOrgId;
	}
	 
	url=url.getNewUrl();
	
	var rtn=window.showModalDialog(url,"",winArgs);
	
	if(conf.callback)
	{
		if(rtn!=undefined){
			if(type=='org'){
				curOrgId=rtn.orgId;
			}
			else{
				curOrgId="";
			}
			conf.callback.call(this,rtn.orgId,rtn.orgName);
		}
	}
}


/**
 * 用户选择器 
 */
function UserDialog(conf){
	
	var dialogWidth=650;
	var dialogHeight=500;
	
	conf=$.extend({},{dialogWidth:dialogWidth ,dialogHeight:dialogHeight ,help:0,status:0,scroll:0,center:1},conf);

	var winArgs="dialogWidth="+conf.dialogWidth+"px;dialogHeight="+conf.dialogHeight
		+"px;help=" + conf.help +";status=" + conf.status +";scroll=" + conf.scroll +";center=" +conf.center;
	
	if(!conf.isSingle)conf.isSingle=false;
	curOrgId="";
	url=__ctx + "/platform/system/sysUser/dialog.xht?isSingle=" + conf.isSingle+"&orgId="+curOrgId;
	url=url.getNewUrl();
	var rtn=window.showModalDialog(url,"",winArgs);
	
	if(rtn && conf.callback){
		var userIds=rtn.userIds;
		var fullnames=rtn.fullnames;
		var emails=rtn.emails;
		var mobiles=rtn.mobiles;
		var retypes=rtn.retypes;	
		conf.callback.call(this,userIds,fullnames,emails,mobiles,retypes);
	}
}

/**
 * 合同付款信息 
 */
function ContractPayDialog(conf){
	
	var dialogWidth=650;
	var dialogHeight=500;
	
	conf=$.extend({},{dialogWidth:dialogWidth ,dialogHeight:dialogHeight ,help:0,status:0,scroll:0,center:1},conf);

	var winArgs="dialogWidth="+conf.dialogWidth+"px;dialogHeight="+conf.dialogHeight
		+"px;help=" + conf.help +";status=" + conf.status +";scroll=" + conf.scroll +";center=" +conf.center;
	
	if(!conf.isSingle)conf.isSingle=false;
	
	url=__ctx + "/itsm/contract/contractMgt/dialog.xht?isSingle=" + conf.isSingle;
	url=url.getNewUrl();
	var rtn=window.showModalDialog(url,"",winArgs);
	
	if(rtn && conf.callback){
		/**
		 * nodeName, paymentDue, paymentDateDue,
			actualPayment, actualPaymentDate,remarks
		 */
		var nodeName=rtn.nodeName;
		var paymentDue=rtn.paymentDue;
		var paymentDateDue=rtn.paymentDateDue;
		var actualPayment=rtn.actualPayment;
		var actualPaymentDate=rtn.actualPaymentDate;
		var remarks=rtn.remarks;
		
		
		conf.callback.call(this,nodeName, paymentDue, paymentDateDue,actualPayment, actualPaymentDate,remarks);
	}
}

/**
 * 角色选择器 
 */
function RoleDialog(conf)
{
	var dialogWidth=695;
	var dialogHeight=500;
	
	conf=$.extend({},{dialogWidth:dialogWidth ,dialogHeight:dialogHeight ,help:0,status:0,scroll:0,center:1},conf);

	var winArgs="dialogWidth="+conf.dialogWidth+"px;dialogHeight="+conf.dialogHeight
		+"px;help=" + conf.help +";status=" + conf.status +";scroll=" + conf.scroll +";center=" +conf.center;
	
	var url=__ctx + '/platform/system/sysRole/dialog.xht';
	url=url.getNewUrl();
	var rtn=window.showModalDialog(url,"",winArgs);
	
	if(conf.callback)
	{
		if(rtn!=undefined){
			 conf.callback.call(this,rtn.roleId,rtn.roleName);
		}
	}
}


/**
 * 岗位选择器
 * @param conf
 * 
 * dialogWidth：对话框高度 650
 * dialogHeight：对话框高度 500
 * 
 * conf.callback
 * 参数：
 * 		posId：岗位ID
 * 		posName:岗位名称
 * @returns
 */
function PosDialog(conf)
{
	var dialogWidth=680;
	var dialogHeight=500;
	conf=$.extend({},{dialogWidth:dialogWidth ,dialogHeight:dialogHeight ,help:0,status:0,scroll:0,center:1},conf);
	var winArgs="dialogWidth="+conf.dialogWidth+"px;dialogHeight="+conf.dialogHeight
		+"px;help=" + conf.help +";status=" + conf.status +";scroll=" + conf.scroll +";center=" +conf.center;
	var url=__ctx + '/platform/system/position/dialog.xht';
	url=url.getNewUrl();
	var rtn=window.showModalDialog(url,"",winArgs);
	if(conf.callback){
		if(rtn!=undefined){
			 conf.callback.call(this,rtn.posId,rtn.posName);
		}
	}
}

/**
 * 用户选择器 
 */
function UserParamDialog(conf){
	
	var dialogWidth=650;
	var dialogHeight=500;
	conf=$.extend({},{dialogWidth:dialogWidth ,dialogHeight:dialogHeight ,help:0,status:0,scroll:0,center:1},conf);
	var winArgs="dialogWidth="+conf.dialogWidth+"px;dialogHeight="+conf.dialogHeight
		+"px;help=" + conf.help +";status=" + conf.status +";scroll=" + conf.scroll +";center=" +conf.center;
	var url=__ctx + '/platform/system/sysUserParam/dialog.xht?nodeUserId='+conf.nodeUserId;
	var rtn=window.showModalDialog(url,"",winArgs);
	if(conf.callback){
		if(rtn!=undefined){
			 conf.callback.call(this,rtn.paramValue1,rtn.paramValue2);
		}
	}
}


/**
 * 用户选择器 
 */
function OrgParamDialog(conf){
	var dialogWidth=650;
	var dialogHeight=500;
	conf=$.extend({},{dialogWidth:dialogWidth ,dialogHeight:dialogHeight ,help:0,status:0,scroll:0,center:1},conf);
	var winArgs="dialogWidth="+conf.dialogWidth+"px;dialogHeight="+conf.dialogHeight
		+"px;help=" + conf.help +";status=" + conf.status +";scroll=" + conf.scroll +";center=" +conf.center;
	var url=__ctx + '/platform/system/sysOrgParam/dialog.xht?nodeUserId='+conf.nodeUserId;
	url=url.getNewUrl();
	var rtn=window.showModalDialog(url,"",winArgs);
	if(conf.callback){
		if(rtn!=undefined){
			 conf.callback.call(this,rtn.paramValue1,rtn.paramValue2);
		}
	}
}


/**
 * 上下级选择器 
 */

function UplowDialog(conf){
	var dialogWidth=650;
	var dialogHeight=500;
	conf=$.extend({},{dialogWidth:dialogWidth ,dialogHeight:dialogHeight ,help:0,status:0,scroll:0,center:1},conf);
	var winArgs="dialogWidth="+conf.dialogWidth+"px;dialogHeight="+conf.dialogHeight
		+"px;help=" + conf.help +";status=" + conf.status +";scroll=" + conf.scroll +";center=" +conf.center;
	var url=__ctx + '/platform/bpm/bpmNodeUserUplow/dialog.xht';
	url=url.getNewUrl();
	var rtn=window.showModalDialog(url,"",winArgs);
	if(conf.callback){
		if(rtn!=undefined){
			 conf.callback.call(this,rtn.json,rtn.show);
		}
	}
}
/**
 * 合同选择器 
 */
function ContractDialog(conf){
	
	var dialogWidth=1200;
	var dialogHeight=750;
	
	conf=$.extend({},{dialogWidth:dialogWidth ,dialogHeight:dialogHeight ,help:0,status:0,scroll:0,center:1},conf);

	var winArgs="dialogWidth="+conf.dialogWidth+"px;dialogHeight="+conf.dialogHeight
		+"px;help=" + conf.help +";status=" + conf.status +";scroll=" + conf.scroll +";center=" +conf.center;
	
	if(!conf.isSingle)conf.isSingle=false;
	
	url=__ctx + "/itsm/contract/contractMgt/contractDialog.xht?isSingle=" + conf.isSingle;
	url=url.getNewUrl();
	var rtn=window.showModalDialog(url,"",winArgs);
	
	if(rtn && conf.callback){
		var ids=rtn.ids;
		var names=rtn.names;
		var codes=rtn.codes;
		var signedTimes=rtn.signedTimes;
		var expirationDates=rtn.expirationDates;
		conf.callback.call(this,ids,names,codes,signedTimes,expirationDates);
	}
}
/**
 * 客户选择器 
 */
function CustomerDialog(conf){
	
	var dialogWidth=1200;
	var dialogHeight=750;
	
	conf=$.extend({},{dialogWidth:dialogWidth ,dialogHeight:dialogHeight ,help:0,status:0,scroll:0,center:1},conf);

	var winArgs="dialogWidth="+conf.dialogWidth+"px;dialogHeight="+conf.dialogHeight
		+"px;help=" + conf.help +";status=" + conf.status +";scroll=" + conf.scroll +";center=" +conf.center;
	
	if(!conf.isSingle)conf.isSingle=false;
	
	url=__ctx + "/platform/console/customerDialog.xht?isSingle=" + conf.isSingle;
	url=url.getNewUrl();
	var rtn=window.showModalDialog(url,"",winArgs);
	
	if(rtn && conf.callback){
		var customerId=rtn.customerId;
		var customerName=rtn.customerName;
		conf.callback.call(this,customerId,customerName);
	}
}

/**
 * 供应商选择器 
 */
function SupplierDialog(conf){
	
	var dialogWidth=900;
	var dialogHeight=700;
	
	conf=$.extend({},{dialogWidth:dialogWidth ,dialogHeight:dialogHeight ,help:0,status:0,scroll:0,center:1},conf);

	var winArgs="dialogWidth="+conf.dialogWidth+"px;dialogHeight="+conf.dialogHeight
		+"px;help=" + conf.help +";status=" + conf.status +";scroll=" + conf.scroll +";center=" +conf.center;
	
	if(!conf.isSingle)conf.isSingle=false;
	
	url=__ctx + "/platform/system/sysOrg/supplierDialog.xht?isSingle=" + conf.isSingle;
	url=url.getNewUrl();
	var rtn=window.showModalDialog(url,"",winArgs);
	
	if(rtn && conf.callback){
		var ids=rtn.ids;
		var names=rtn.names;
		conf.callback.call(this,ids,names);
	}
}

function AssetJfDialog(conf){
	
	var dialogWidth=650;
	var dialogHeight=500;
	
	conf=$.extend({},{dialogWidth:dialogWidth ,dialogHeight:dialogHeight ,help:0,status:0,scroll:0,center:1},conf);
	var obj=conf.obj;
	//var url=__ctx + "/itsm/cmdb/assetBase/assetEngineroomDialog.xht?isSingle=" + conf.isSingle;
	var url=__ctx + "/itsm/cmdb/assetBase/assetDataDialog.xht?isSingle=" + conf.isSingle + "&sta=" + conf.sta + "&type=Engineroom";
	
	var winArgs="dialogWidth="+conf.dialogWidth+"px;dialogHeight="+conf.dialogHeight
		+"px;help=" + conf.help +";status=" + conf.status +";scroll=" + conf.scroll +";center=" +conf.center;
	
	if(!conf.isSingle)conf.isSingle=false;
	
	url=url.getNewUrl();
	var rtn=window.showModalDialog(url,"",winArgs);
	
	if(rtn && conf.callback){
		var ids=rtn.ids;
		var names=rtn.names;
		conf.callback.call(this,ids,names);
	}
}


function AssetVlanDialog(conf){
	
	var dialogWidth=650;
	var dialogHeight=500;
	
	conf=$.extend({},{dialogWidth:dialogWidth ,dialogHeight:dialogHeight ,help:0,status:0,scroll:0,center:1},conf);
	var obj=conf.obj;
	
	if(assetStatus!=""){
		conf.sta=assetStatus;
	}
	//var url=__ctx + "/itsm/cmdb/assetBase/assetVlanDialog.xht?isSingle=" + conf.isSingle+"&sta="+conf.sta;
	var url=__ctx + "/itsm/cmdb/assetBase/assetDataDialog.xht?isSingle=" + conf.isSingle + "&sta=" + conf.sta + "&type=Vlan";
	
	var winArgs="dialogWidth="+conf.dialogWidth+"px;dialogHeight="+conf.dialogHeight
		+"px;help=" + conf.help +";status=" + conf.status +";scroll=" + conf.scroll +";center=" +conf.center;
	
	if(!conf.isSingle)conf.isSingle=false;
	
	url=url.getNewUrl();
	var rtn=window.showModalDialog(url,"",winArgs);
	
	if(rtn && conf.callback){
		var ids=rtn.ids;
		var names=rtn.names;
		conf.callback.call(this,ids,names);
	}
}

function AssetJhjDialog(conf){
	
	var dialogWidth=650;
	var dialogHeight=500;
	
	conf=$.extend({},{dialogWidth:dialogWidth ,dialogHeight:dialogHeight ,help:0,status:0,scroll:0,center:1},conf);
	var obj=conf.obj;
	if(assetStatus!=""){
		conf.sta=assetStatus;
	}
	//var url=__ctx + "/itsm/cmdb/assetBase/assetSwitchDialog.xht?sta=" + conf.sta;
	var url=__ctx + "/itsm/cmdb/assetBase/assetDataDialog.xht?sta=" + conf.sta + "&type=SwitchPort";
	
	var winArgs="dialogWidth="+conf.dialogWidth+"px;dialogHeight="+conf.dialogHeight
		+"px;help=" + conf.help +";status=" + conf.status +";scroll=" + conf.scroll +";center=" +conf.center;
	
	if(!conf.isSingle)conf.isSingle=false;
	
	url=url.getNewUrl();
	var rtn=window.showModalDialog(url,"",winArgs);
	
	if(rtn && conf.callback){
		var ids=rtn.ids;
		var names=rtn.names;
		var parents=rtn.parents;
		conf.callback.call(this,ids,names,parents);
	}
}


function AssetGdsfDialog(conf){
	
	var dialogWidth=650;
	var dialogHeight=500;
	var obj=conf.obj;
	if(assetStatus!=""){
		conf.sta=assetStatus;
	}
	//var url=__ctx + "/itsm/cmdb/assetBase/assetComponentsopticalDialog.xht?sta=" + conf.sta;
	var url=__ctx + "/itsm/cmdb/assetBase/assetDataDialog.xht?sta=" + conf.sta + "&type=Componentsoptical";
	
	conf=$.extend({},{dialogWidth:dialogWidth ,dialogHeight:dialogHeight ,help:0,status:0,scroll:0,center:1},conf);
	var winArgs="dialogWidth="+conf.dialogWidth+"px;dialogHeight="+conf.dialogHeight
		+"px;help=" + conf.help +";status=" + conf.status +";scroll=" + conf.scroll +";center=" +conf.center;
	
	if(!conf.isSingle)conf.isSingle=false;
	
	url=url.getNewUrl();
	var rtn=window.showModalDialog(url,"",winArgs);
	
	if(rtn && conf.callback){
		var ids=rtn.ids;
		var names=rtn.names;
		var parents=rtn.parents;
		conf.callback.call(this,ids,names,parents);
	}

}

function AssetRouterPortDialog(conf){
	
	var dialogWidth=650;
	var dialogHeight=500;
	var obj=conf.obj;
	//var url=__ctx + "/itsm/cmdb/assetBase/assetRouterPortDialog.xht?status=" + conf.sta;
	var url=__ctx + "/itsm/cmdb/assetBase/assetDataDialog.xht?sta=" + conf.sta + "&type=RouterPort";
	
	conf=$.extend({},{dialogWidth:dialogWidth ,dialogHeight:dialogHeight ,help:0,status:0,scroll:0,center:1},conf);
	var winArgs="dialogWidth="+conf.dialogWidth+"px;dialogHeight="+conf.dialogHeight
		+"px;help=" + conf.help +";status=" + conf.status +";scroll=" + conf.scroll +";center=" +conf.center;
	
	if(!conf.isSingle)conf.isSingle=false;
	
	url=url.getNewUrl();
	var rtn=window.showModalDialog(url,"",winArgs);
	
	if(rtn && conf.callback){
		var ids=rtn.ids;
		var names=rtn.names;
		var parents=rtn.parents;
		conf.callback.call(this,ids,names,parents);
	}
}

function AssetIPDialog(conf){
	var dialogWidth=650;
	var dialogHeight=500;
	var obj=conf.obj;
	if(assetStatus!=""){
		conf.sta=assetStatus;
	}
	//var url=__ctx + "/itsm/cmdb/assetBase/assetIPDialog.xht?sta=" + conf.sta;
	var url=__ctx + "/itsm/cmdb/assetBase/assetDataDialog.xht?sta=" + conf.sta + "&type=IP";
	
	conf=$.extend({},{dialogWidth:dialogWidth ,dialogHeight:dialogHeight ,help:0,status:0,scroll:0,center:1},conf);
	var winArgs="dialogWidth="+conf.dialogWidth+"px;dialogHeight="+conf.dialogHeight
		+"px;help=" + conf.help +";status=" + conf.status +";scroll=" + conf.scroll +";center=" +conf.center;
	
	if(!conf.isSingle)conf.isSingle=false;
	
	url=url.getNewUrl();
	var rtn=window.showModalDialog(url,"",winArgs);
	
	if(rtn && conf.callback){
		var ids=rtn.ids;
		var names=rtn.names;
		var parents=rtn.parents;
		conf.callback.call(this,ids,names,parents);
	}
}

function ProjectDialog(conf)
{
	var dialogWidth=900;
	var dialogHeight=700;
	
	conf=$.extend({},{dialogWidth:dialogWidth ,dialogHeight:dialogHeight ,help:0,status:0,scroll:0,center:1},conf);

	var winArgs="dialogWidth="+conf.dialogWidth+"px;dialogHeight="+conf.dialogHeight
		+"px;help=" + conf.help +";status=" + conf.status +";scroll=" + conf.scroll +";center=" +conf.center;
	
	var url=__ctx + '/platform/system/project/dialog.xht?orgId='+curOrgId;
	url=url.getNewUrl();
	
	var rtn=window.showModalDialog(url,"",winArgs);
	
	if(conf.callback)
	{
		if(rtn!=undefined){
			
			conf.callback.call(this,rtn.id,rtn.name);
		}
	}

}

/**
 * 资产信息选择 
 */
function AssetInfoDialog(conf){
	
	var dialogWidth=760;
	var dialogHeight=515;
	var obj=conf.obj;
	var url=__ctx + "/itsm/cmdb/assetBase/assetInfoDialog.xht?status=" + conf.sta;
	
	
	conf=$.extend({},{dialogWidth:dialogWidth ,dialogHeight:dialogHeight ,help:0,status:0,scroll:0,center:1},conf);
	var winArgs="dialogWidth="+conf.dialogWidth+"px;dialogHeight="+conf.dialogHeight
		+"px;help=" + conf.help +";status=" + conf.status +";scroll=" + conf.scroll +";center=" +conf.center;
	
	if(!conf.isSingle)conf.isSingle=true;
	
	url=url.getNewUrl();
	var rtn=window.showModalDialog(url,"",winArgs);
	if(rtn && conf.callback){
		var ids=rtn.ids;
		var names=rtn.names;
		var parents=rtn.parents;
		conf.callback.call(this,ids,names,parents);
	}
}

function CatalogInfoDialog(conf){
	var dialogWidth=370;
	var dialogHeight=450;
	var obj=conf.obj;
	var url = __ctx + "/itsm/cmdb/selected/catalog.xht?orgId=" + conf.orgId;
	if (conf.type) {
		url += "&type=" + conf.type;
	}
	
	if (conf.needRoot || conf.needRoot == false) {
		url += "&needRoot=" + conf.needRoot;
	} else {
		url += "&needRoot=true";
	}
	
	conf=$.extend({},{dialogWidth:dialogWidth ,dialogHeight:dialogHeight ,help:0,status:0,scroll:0,center:1},conf);
	var winArgs="dialogWidth="+conf.dialogWidth+"px;dialogHeight="+conf.dialogHeight
		+"px;help=" + conf.help +";status=" + conf.status +";scroll=" + conf.scroll +";center=" +conf.center;
	
	if(!conf.isSingle)conf.isSingle=true;
	
	url=url.getNewUrl();
	var rtn=window.showModalDialog(url,"",winArgs);
	if(rtn && conf.callback){
		var id = rtn.id;
		var name = rtn.name;
		conf.callback.call(this, id, name);
	}
}

function linkInfoDialog(conf){
	var dialogWidth=760;
	var dialogHeight=500;
	var obj=conf.obj;
	var url=__ctx + "/itsm/custom/customInfo/linkInfoDialog2.xht?orgId=" + curOrgId;
	
	
	conf=$.extend({},{dialogWidth:dialogWidth ,dialogHeight:dialogHeight ,help:0,status:0,scroll:0,center:1},conf);
	var winArgs="dialogWidth="+conf.dialogWidth+"px;dialogHeight="+conf.dialogHeight
		+"px;help=" + conf.help +";status=" + conf.status +";scroll=" + conf.scroll +";center=" +conf.center;
	
	if(!conf.isSingle)conf.isSingle=true;
	
	url=url.getNewUrl();
	var rtn=window.showModalDialog(url,"",winArgs);
	if(rtn && conf.callback){
		var id = rtn.id;
		var name = rtn.name;
		var orgId,orgName,contractId,contractName,signedTime,expirationDate,contractCode,projectId,projectName;
		
		$.post(__ctx+"/itsm/custom/customInfo/linkInfoById.xht?id="+id,function(jsonArr){
			var data=jsonArr[0];
			orgId=data["F_own_customId"];
			orgName=data["F_own_custom"];
			contractId=data["contractnoID"];
			contractName=data["contractName"];
			signedTime=data["signedTime"];
			expirationDate=data["expirationDate"];
			contractCode=data["cIId"];
			projectId=data["F_projectId"];
			projectName=data["F_project"];
			callBackLinkInfo(orgId,orgName,contractId,contractName,contractCode,signedTime,expirationDate,projectId,projectName);
		});
		conf.callback.call(this, id, name);
		
		
	}
}


function callBackLinkInfo(orgId,orgName,contractId,contractName,contractCode,signedTime,expirationDate,projectId,projectName){
	
}