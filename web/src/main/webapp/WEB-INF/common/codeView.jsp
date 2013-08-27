<%@ page language="java"  pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>二维码生成</title>
    <link rel="stylesheet" type="text/css" href="${ctx }/jslib/ligerUI/skins/Aqua/css/ligerui-all.css" />
	<link rel="stylesheet" type="text/css" href="${ctx }/styles/css/default.css" />
	<script type="text/javascript" src="${ctx }/jslib/jquery/jquery-1.7.2.min.js"></script>
	<script type="text/javascript" src="${ctx }/jslib/ligerUI/js/ligerui.min.js"></script>
    <script>
    var codeform=null;
    var opData="";
    var imgUrl="";
    $(function () {
				$.ajax({url:'../com/showImg?assetId='+parent.currentID,async:false,dataType: 'json',success: function (result){
			 		opData=result.Data;
			 		$("#qr_show").attr("src",result.imgUrl);
       			}});
     			$("#codeViewDiv").ligerLayout({
                    leftWidth: 200,
                });
                
			//机房信息
			codeform = $("#codeform");  
			codeform.ligerForm({
                inputWidth: 100, labelWidth: 60, space: 4,
                fields: [
                { name: "id",type:"hidden"}, 
    			{ display: "数据中心", name: "dcName", newline: true,type: "text"}, 
				{ display: "机房", name: "roomName", newline: false, type: "text"},
				{ display: "区域", name: "areaName", newline: true,type: "text"},
    			{ display: "机柜", name: "rackName", newline: false, type: "text"},    						
    			{ display: "资产分类", name: "assetCatalogName", newline: true,type: "text"}, 
				{ display: "资产编号", name: "assetNo", newline: false, type: "text"},
				{ display: "资产名称", name: "assetName", newline: true,type: "text"},
				{ display: "类别", name: "type", newline: false, type: "text"},
				{ display: "品牌", name: "brand", newline: true, type: "text"},
				{ display: "型号", name: "model", newline: false, type: "text"},
    			{ display: "描述", name: "description",newline: true,width:265,type: "text"},
    			{ display: "URL", name: "urlAddress", newline: true,width:265, type: "text"}
                ]
			});
			setFormData();
        });
    
    
    function preview() {
    	bdhtml=window.document.body.innerHTML;
    	sprnstr="<!--startprint-->";
    	eprnstr="<!--endprint-->";
    	prnhtml=bdhtml.substr(bdhtml.indexOf(sprnstr)+17);
    	prnhtml=prnhtml.substring(0,prnhtml.indexOf(eprnstr));
    	window.document.body.innerHTML=prnhtml;
    	window.print();
    	window.document.body.innerHTML=bdhtml;
    	setFormData();

    }
    
    function setFormData(){
		if(opData!="" && typeof(opData)=="object"){
			try{
        	for (var c in opData){
        		var ele = $("[name='" + (c) + "']");
        			if(ele.length > 0){
        			ele.val(opData[c]);
        			ele.attr("readOnly",'true');;
        	}}}catch(e){}
        }
    }
    </script>
	  </head>
  <body style="padding:0px; overflow:hidden;">
 	  <div id="codeViewDiv">
             <div id="codeImg" position="left" title="二维码预览" style="display:table-cell;text-align:center;vertical-align:middle;" >
              <!--startprint-->
              <img id="qr_show" src="" width="200" style="border:0;" height="200" alt="二维码" />
              <!--endprint-->
              	<label id="qrCodeLabel"></label>
             </div>
            <div id="codeCenter" position="center" title="二维码信息">
			  <div id="toptoolbar"></div><form id="codeform" method="post"></form>
            </div>
        </div>
</body>
</html>
