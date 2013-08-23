var treeData= 
[
    {text: '值班管理',isexpand:false, children: [ 
       	{text:"排班定义",isexpand:false,children:[
       		{url:"shift/shiftCatalog-list",click: itemclick,text:"排班类型"},
       		{url:"shift/shiftDefine-list",click: itemclick,text:"班次定义"},
       		{url:"shift/shiftCandidate-list",click: itemclick,text:"候选人或组定义"},
       		{url:"xxx.htm",click: itemclick,text:"自动排班规则"},
       		{url:"xxx.htm",click: itemclick,text:"排班冲突规则"}
       		]},
       	{text:"排班管理",isexpand:false,children:[
            {url:"shift/shiftMonth-list",click: itemclick,text:"计划排班"},
       		{url:"shift/shiftDetail-calendar",click: itemclick,text:"计划班次"},
       		{url:"shift/shiftRoster-calendar",click: itemclick,text:"排班名单"}
       		]},
       	{text:"调班管理",isexpand:false,children:[
            {url:"xxx.htm",click: itemclick,text:"调班申请"},
       		{url:"xxx.htm",click: itemclick,text:"调班管理"}
       		]},
       	{text:"值班查看",isexpand:false,children:[
            {url:"xxx.htm",click: itemclick,text:"数据中心值班表"},
            {url:"xxx.htm",click: itemclick,text:"个人值班表"},
       		{url:"shift/shiftRoster-list",click: itemclick,text:"值班表查询"}
      		 ]},
       	{text:"交接班配置",isexpand:false,children:[
            {url:"xxx.htm",click: itemclick,text:"交接班检查点配置"}
      		 ]},
      	{text:"值班与接班管理",isexpand:false,children:[
            {url:"shift/shiftConsole-list",click: itemclick,text:"值班控制台"},
            {url:"xxx.htm",click: itemclick,text:"交接班记录及确认"},
       		{url:"xxx.htm",click: itemclick,text:"值班日志查询"}
       		]},
      	{text:"指纹集成",isexpand:false,children:[
            {url:"xxx.htm",click: itemclick,text:"指纹签入签出"},
       		{url:"xxx.htm",click: itemclick,text:"指纹交接班确认"}
       		]}
	]},
	
	{text: '机房及资产',isexpand:false, children: [ 
       	{text:"机房机柜",isexpand:false,children:[
       		{url:"asset/roomView",click: itemclick,text:"机房定义"},
       		{url:"asset/rackView",click: itemclick,text:"机柜列表"},
       		{url:"asset/areaView",click: itemclick,text:"区域定义"}
       		]},
       	{text:"资产定义",isexpand:false,children:[
            {url:"asset/assetCatalogView",click: itemclick,text:"资产分类"},
       		{url:"asset/brandView",click: itemclick,text:"品牌型号"}
       		]},
       	{text:"资产管理",isexpand:false,children:[
            {url:"asset/assetView",text:"资产列表"},
       		{url:"xxx.htm",click: itemclick,text:"资产导入"}
       		]}
     ]},
	
	{text: '操作库管理',isexpand:false, children: [ 
       	{text:"操作库升级",isexpand:false,children:[
       		{url:"xxx.htm",click: itemclick,text:"本地更新"},
       		{url:"xxx.htm",click: itemclick,text:"在线更新"}
       		]},
       	{text:"指标分类维护",isexpand:false,children:[
            {url:"opcatalog/list?type=10",text:"巡检指标分类"},
       		{url:"opcatalog/list?type=20",text:"操作指标分类"}
       		]},
       	{text:"操作模版",isexpand:false,children:[
            {url:"xxx.htm",click: itemclick,text:"例行操作模版"},
            {url:"xxx.htm",click: itemclick,text:"巡检操作模版"},
       		{url:"template/list",text:"操作任务模版"}
       		]},
       	{text:"操作计划管理",isexpand:false,children:[
            {url:"xxx.htm",click: itemclick,text:"操作计划列表"},
       		{url:"xxx.htm",click: itemclick,text:"操作计划执行"}
       		]}
       		
     ]},
     
    {text: '操作任务管理',isexpand:false, children: [ 
       	{text:"任务中心",isexpand:false,children:[
       		{url:"xxx.htm",click: itemclick,text:"待处理任务列表"},
       		{url:"xxx.htm",click: itemclick,text:"历史任务查询"}
       		]},
       	{text:"任务执行",isexpand:false,children:[
            {url:"xxx.htm",click: itemclick,text:"巡检执行"},
       		{url:"xxx.htm",click: itemclick,text:"操作执行"},
       		{url:"xxx.htm",click: itemclick,text:"任务查看"}
       		]},
       	{text:"事件集成",isexpand:false,children:[
       		{url:"xxx.htm",click: itemclick,text:"调用ITSM事件"}
       		]}
     ]},
     
     {text: 'PAD管理',isexpand:false, children: [ 
       	{text:"PAD登陆设置",isexpand:false,children:[
       		{url:"xxx.htm",click: itemclick,text:"PAD登录"},
       		{url:"xxx.htm",click: itemclick,text:"PAD设置"}
       		]},
       	{text:"PAD操作",isexpand:false,children:[
            {url:"xxx.htm",click: itemclick,text:"任务领取"},
       		{url:"xxx.htm",click: itemclick,text:"巡检操作"},
       		{url:"xxx.htm",click: itemclick,text:"任务操作"},
       		{url:"xxx.htm",click: itemclick,text:"任务提交"}
       		]}
     ]},
     
     {text: '管理报表',isexpand:false, children: [ 
      		{url:"xxx.htm",click: itemclick,text:"巡检报表"}
     ]}
/*,     
     {text: 'PAD数据交互',isexpand:false, children: [ 
    	{text:"PAD初始化工作",url:"xxx.htm",click: itemclick,},
       	{text:"PAD连接状态管理",isexpand:false,children:[
       		{url:"xxx.htm",click: itemclick,text:"连接状态管理方式"}
       		]},
       	{text:"PAD登录",isexpand:false,children:[
            {url:"xxx.htm",click: itemclick,text:"已设置默认数据中心登录"},
       		{url:"xxx.htm",click: itemclick,text:"未设置默认数据中心登录"},
       		{url:"xxx.htm",click: itemclick,text:"S3 Server处理"},
       		{url:"xxx.htm",click: itemclick,text:"PAD登出"},
       		{url:"xxx.htm",click: itemclick,text:"PAD登出状态保持"},
       		{url:"xxx.htm",click: itemclick,text:"登录/登出发生场景"}
       		]},
       	{text:"任务下载",isexpand:false,children:[
            {url:"xxx.htm",click: itemclick,text:"任务下载规则描述"},
       		{url:"xxx.htm",click: itemclick,text:"任务领取处理方式"},
       		{url:"xxx.htm",click: itemclick,text:"任务放弃处理方式"},
       		{url:"xxx.htm",click: itemclick,text:"任务再次下载"}
       		]},
       	{text:"任务同步",isexpand:false,children:[
            {url:"xxx.htm",click: itemclick,text:"处理同步"},
       		{url:"xxx.htm",click: itemclick,text:"任务同步"}
       		]},
       	{text:"交接班",isexpand:false,children:[
            {url:"xxx.htm",click: itemclick,text:"值班日志"},
       		{url:"xxx.htm",click: itemclick,text:"交接班"}
       		]}
     ]}*/

];
function itemclick(item)
{
alert(item);
}