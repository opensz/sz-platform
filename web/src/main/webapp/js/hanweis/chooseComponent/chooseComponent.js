
function chooseComponent(customerId, warehouseId, assetId,
		rootCICatalogId) {
	var chooseItCompontWin;
	var urlPath = getContextPath();

	//资产组件选择搜索框
	var chooseItCompontSearchForm=Ext.getCmp('chooseItCompontSearchForm');
	if(!Ext.isDefined(chooseItCompontSearchForm)){
		
		chooseItCompontSearchForm= Ext.create('Ext.form.Panel', {
			xtype : 'form',
			id:'chooseItCompontSearchForm',
			frame : true,
			//title : '快速查询',
			border:0,
			bodyPadding: 5,
			bodyCls:'',         //body样式
			cls:'',             //
			componentCls:'',
			defaults:{
				xtype:'textfield',
				cls:'search-line-part',
				margin:'2 10 2 0'
			},
			layout: {
		        type: 'table'
		    },
			items : [ {
				xtype : 'textfield',
				fieldLabel : '搜索内容',
				name : 'itcomponent.searchKey',
				anchor : '100%',
				listeners : {
					'specialkey' : function(field, e) {
						if (e.getKey() == e.ENTER) {
							chooseItCompontGridStore1.loadPage(1);
						}
					}
				},
				emptyText : '-按组件名称、描述搜索 -回车键结束-'
			}, {
				xtype : 'combo',
				fieldLabel : '资产目录',
				name : 'itcomponent.assetCatalog.id',
				id : 'getComboAssetCatalogId',
				displayField : 'label',
				valueField : 'value',
				queryMode : 'local',
				editable : false,
				store : Ext.create('Ext.data.Store', {
					//autoLoad:true,
					proxy : {
						type : 'ajax',
						url : urlPath + '/asset/assetCatalog/getComboAssetCatalog'
						//params:{'customerId':customerId,'rootCICatalogId':rootCICatalogId}
					},
					listeners:{
						'load':function(store,record,success){
							if(record!=null && record.length>=1){
								Ext.getCmp('getComboAssetCatalogId').select(record[0]);
							}
							//Ext.getCmp('staff_search_orgId').setValue('${loginUser.orgId}');
						}
					},
					fields : [ {
						name : 'label'
					}, {
						name : 'value'
					} ]
				})
			},{
				text : '查询',
				handler : function() {
					chooseItCompontGridStore1.loadPage(1);
				}
			}  ]

		});
	}

	var assetCatalogCombo = Ext.getCmp('getComboAssetCatalogId');

	assetCatalogCombo.store.on('beforeload', function(store, operation) {
		//var orgId=Ext.getCmp(randomId+'itCaseParam_customer_id').getValue();
			var params = {
				'customerId' : customerId,
				'rootCICatalogId' : rootCICatalogId
			};
			Ext.apply(store.proxy.extraParams, params);
		});
	assetCatalogCombo.store.load();

	//可选
	var chooseItCompontGridStore1 = Ext.create('Ext.data.Store', {
		pageSize : 10,
		fields : [ {
			name : 'id',
			type : 'String'
		}, {
			name : 'name',
			type : 'String'
		}, {
			name : 'label',
			type : 'String'
		}, {
			name : 'customerId',
			type : 'String'
		}, {
			name : 'assetCatalog'
		}, {
			name : 'assetStatus'
		}, {
			name : 'isKey',
			type : 'Boolean'
		} ],
		proxy : {
			type : 'ajax',
			url : urlPath + '/asset/componentDataAvi?warehouseId='
					+ warehouseId + "&customerId=" + customerId,
			reader : {
				type : 'json',
				root : 'rows',
				totalProperty : 'total'
			}
		}
	});
	chooseItCompontGridStore1.on('beforeload', function(store, operation) {
		var parameters = chooseItCompontSearchForm.getForm().getFieldValues(
				false);
		Ext.apply(store.proxy.extraParams, parameters);
	});

	//已选
	var chooseItCompontGridStore2 = Ext.create('Ext.data.Store', {
		autoLoad : true,
		fields : [ {
			name : 'id',
			type : 'String'
		}, {
			name : 'name',
			type : 'String'
		}, {
			name : 'label',
			type : 'String'
		}, {
			name : 'customerId',
			type : 'String'
		}, {
			name : 'assetCatalog'
		}, {
			name : 'assetStatus'
		}, {
			name : 'isKey',
			type : 'Boolean'
		} ],
		proxy : {
			type : 'ajax',
			url : urlPath + '/asset/componentDataNoPager?id=' + assetId,
			reader : {
				type : 'json',
				root : 'rows'
			}
		}
	});

	var chooseItCompontGridSm1 = Ext.create('Ext.selection.CheckboxModel', {
		checkOnly : true
	});
	var chooseItCompontGridSm2 = Ext.create('Ext.selection.CheckboxModel', {
		checkOnly : true
	});

	//可选择组件列表
	var chooseItCompontGridForm1 =Ext.getCmp('chooseItCompontGridForm1');
	if(!Ext.isDefined(chooseItCompontGridForm1)){
		
		chooseItCompontGridForm1= Ext.create('Ext.grid.Panel', {
			frame : true,
			title : '可选组件',
			id:'chooseItCompontGridForm1',
			height : 400,
			viewConfig : {
				stripeRows : true
			},
			store : chooseItCompontGridStore1,
			dockedItems : [ {
				xtype : 'toolbar',
				items : [ {
					text : '选择',
					handler : getChosenComponent
				} ]
			} ],
			listeners : {
				itemdblclick : function(view, record, item, index, e) {
					var assetCatalogId1 = record.data.assetCatalog.id;
					var warehouseId1 = record.data.warehouse.id;
					//showDetailList(assetCatalogId,warehouseId);
				}
			},
			columnLines : true,
			columns : [ {
				text : "资产名称",
				flex : 1,
				dataIndex : 'name'
			}, {
				text : "条码号",
				flex : 1,
				dataIndex : 'label',
				sortable : true
			}, {
				text : "资产目录",
				flex : 1,
				dataIndex : 'assetCatalog',
				sortable : true,
				renderer : function(assetCatalog) {
					if (assetCatalog == null) {
						return '';
					} else {
						return assetCatalog.name;
					}
				}
			} ],
			selModel : chooseItCompontGridSm1,
			bbar : Ext.create('Ext.PagingToolbar', {
				store : chooseItCompontGridStore1,
				displayInfo : true
			})
		});
	}

	//资产已选择选组件列表(无分页)
	var chooseItCompontGridForm2 =Ext.getCmp('chooseItCompontGridForm2');
	if(!Ext.isDefined(chooseItCompontGridForm2)){
		
		chooseItCompontGridForm2 = Ext.create('Ext.grid.Panel', {
			frame : true,
			title : '已选组件',
			height : 400,
			id:'chooseItCompontGridForm2',
			autoScroll : true,
			viewConfig : {
				stripeRows : true
			},
			store : chooseItCompontGridStore2,
			dockedItems : [ {
				xtype : 'toolbar',
				items : [ {
					text : '保存',
					handler : saveChosenComponent
				}, '-', {
					text : '删除',
					handler : delChosenComponent
				} ]
			} ],
			listeners : {
				itemdblclick : function(view, record, item, index, e) {
					//var assetCatalogId2 = record.data.assetCatalog.id;
					//var warehouseId2 = record.data.warehouse.id;
				}
			},
			columnLines : true,
			columns : [ {
				text : "资产名称",
				flex : 1,
				dataIndex : 'name'
			}, {
				text : "条码号",
				flex : 1,
				dataIndex : 'label',
				sortable : true
			}, {
				text : "资产目录",
				flex : 1,
				dataIndex : 'assetCatalog',
				sortable : true,
				renderer : function(assetCatalog) {
					if (assetCatalog == null) {
						return '555';
					} else {
						return assetCatalog.name;
					}
				}
			} ],
			selModel : chooseItCompontGridSm2
		});
	}

	//定义资产组件选择弹出窗口
	chooseItCompontWin =Ext.getCmp('chooseItCompontWin');
	if(!Ext.isDefined(chooseItCompontWin)){
		chooseItCompontWin = Ext.widget('window', {
			closeAction : 'destroy',
			id:'chooseItCompontWin',
			cls : 's3-largewindow',
			height : 520,
			defaults : {
				margins : '0 0 10 0'
			},
			layout : 'column',
			anchor : '100%',
			title : '组件维护',
			listeners : {
				//显示window前，除掉已选框里的所有数据，重新加入前页面传入的数据
				beforeshow : function() {
					
				}
			},
			items : [ {
				xtype : 'container',
				columnWidth : 1,
				layout : 'anchor',
				items : [ chooseItCompontSearchForm ]
			}, {
				xtype : 'container',
				columnWidth : .5,
				layout : 'anchor',
				items : [ chooseItCompontGridForm1 ]
			}, {
				xtype : 'container',
				columnWidth : .5,
				layout : 'anchor',
				items : [ chooseItCompontGridForm2 ]
			} ]
		});
	}

	chooseItCompontWin.show();

	//左边grid选到右边grid
	function getChosenComponent() {
		var aselections = chooseItCompontGridForm1.getSelectionModel().selected;
		var leftLength = aselections.length;
		for ( var i = 0; i < leftLength; i++) {
			if (isExist(aselections.get(i).data.id, chooseItCompontGridStore2)) {
				chooseItCompontGridStore2.insert(
						chooseItCompontGridStore2.data.length, aselections
								.get(i));
			}
		}
		//取消所有选择的
		var chooseItCompontGridStore1Len = chooseItCompontGridStore1.data.length;
		for ( var k = 0; k < chooseItCompontGridStore1Len; k++) {
			chooseItCompontGridForm1.getSelectionModel().deselect(k, false);
		}
	}

	//保存该资产的组件关联信息
	function saveChosenComponent() {
		var slen = chooseItCompontGridStore2.data.length;
		var idStr = new Array();
		if (slen == 0) {
			Ext.MessageBox.alert('信息提示', '请至少在左边选择一个组件过来');
			return false;
		}
		for ( var i = 0; i < slen; i++) {
			var componentId = chooseItCompontGridStore2.getAt(i).data.id;
			idStr.push(componentId);
		}
		Ext.Ajax.request( {
			url : urlPath + "/asset/componentSave",
			params : {
				'removeIdList' : idStr,
				'id' : assetId
			},
			success : function(response) {
				chooseItCompontGridStore2.load();
				Ext.Msg.alert("信息提示", "保存成功！");
				chooseItCompontWin.hide();
			}
		});
	}

	//删除关联
	function delChosenComponent() {
		var selections = chooseItCompontGridForm2.getSelectionModel().selected;
		var slen = selections.length;
		var idStr = new Array();
		if (slen == 0) {
			Ext.MessageBox.alert('信息提示', '请至少选择一条信息');
			return false;
		}
		for (i = slen; i--; i >= 0) {
			var componentId = selections.get(i).data.id;
			idStr.push(componentId);
			chooseItCompontGridStore2.remove(selections.get(i));
		}
		Ext.Ajax.request( {
			url : urlPath + "/asset/componentDelete",
			params : {
				'removeIdList' : idStr
			},
			success : function(response) {
				chooseItCompontGridStore2.load();
				Ext.Msg.alert("信息提示", "成功删除！");
			}
		});
	}

	function isExist(id, store) {
		var flag = true;
		for ( var i = 0; i < store.data.length; i++) {
			if (id == store.getAt(i).data.id) {
				flag = false;
				break;
			}
		}
		return flag;
	}

}