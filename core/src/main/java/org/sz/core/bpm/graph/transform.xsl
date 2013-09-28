<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
	xmlns:fn="http://www.w3.org/2005/02/xpath-functions"
	xmlns:bg="bpm.graphic" 
	xmlns:activiti="http://activiti.org/bpmn"
	xmlns:bpmndi="http://www.omg.org/spec/BPMN/20100524/DI" 
	xmlns:omgdc="http://www.omg.org/spec/DD/20100524/DC"
	xmlns:omgdi="http://www.omg.org/spec/DD/20100524/DI"
	xmlns:ciied="com.ibm.ilog.elixir.diagram" 
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:calc="xalan://org.sz.core.bpm.graph.TransformUtil"
	extension-element-prefixes="calc"	>
	 <xsl:param name="id"/>
	 <xsl:param name="name"/>
	<xsl:output method="xml" version="1.0" encoding="GBK" indent="yes"/>
	<xsl:template match="/">
		<definitions xmlns="http://www.omg.org/spec/BPMN/20100524/MODEL"
			xmlns:activiti="http://activiti.org/bpmn"
			xmlns:bpmndi="http://www.omg.org/spec/BPMN/20100524/DI" 
			xmlns:omgdc="http://www.omg.org/spec/DD/20100524/DC"
			xmlns:omgdi="http://www.omg.org/spec/DD/20100524/DI" 
			targetNamespace="http://activiti.org/bpmn20">
			
			<process >
			
			<xsl:attribute name="id">
				<xsl:value-of select="$id"></xsl:value-of>
			</xsl:attribute>
			<xsl:attribute name="name">
				<xsl:value-of select="$name"></xsl:value-of>
			</xsl:attribute>
			<extensionElements>
				<!-- 流程开始事件监听器 -->
				<activiti:executionListener class="org.sz.platform.bpm.service.flow.listener.StartEventListener"  event="start" />
				<!-- 流程结束事件监听器 -->
				<activiti:executionListener class="org.sz.platform.bpm.service.flow.listener.EndEventListener"  event="end" />
			</extensionElements>
			<xsl:apply-templates />
				
			</process>
			<bpmndi:BPMNDiagram >
				<xsl:attribute name="id">BPMNDiagram_<xsl:value-of select="$id"></xsl:value-of>
				</xsl:attribute>
			    <bpmndi:BPMNPlane >
			    	<xsl:attribute name="bpmnElement"><xsl:value-of select="$id"></xsl:value-of>
					</xsl:attribute>
					<xsl:attribute name="id">BPMNPlane_<xsl:value-of select="$id"></xsl:value-of>
					</xsl:attribute>
					
					<xsl:call-template name="diagram"></xsl:call-template>
					
					<xsl:call-template name="transition"></xsl:call-template>
			    </bpmndi:BPMNPlane>
		    </bpmndi:BPMNDiagram>
		</definitions>
	</xsl:template>
	
	<xsl:template name="diagram" >
		
		<xsl:call-template name="diagram_block">
			<xsl:with-param name="elName" select="//bg:StartEvent" ></xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="diagram_block">
			<xsl:with-param name="elName" select="//bg:EndEvent" ></xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="diagram_block">
			<xsl:with-param name="elName" select="//bg:Task" ></xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="diagram_block">
			<xsl:with-param name="elName" select="//bg:SubProcess" ></xsl:with-param>
		</xsl:call-template>
		<xsl:call-template name="diagram_block">
			<xsl:with-param name="elName" select="//bg:Gateway" ></xsl:with-param>
		</xsl:call-template>
	</xsl:template>
	
	<xsl:template name="transition">
		<xsl:for-each select="//bg:SequenceFlow">
			<bpmndi:BPMNEdge >
       			<xsl:attribute name="bpmnElement"><xsl:value-of select="@id"></xsl:value-of></xsl:attribute>
       			<xsl:attribute name="id">BPMNEdge_<xsl:value-of select="@id"></xsl:value-of></xsl:attribute>
       			
       			<xsl:variable name="startPort" select="@startPort"></xsl:variable>
       			<xsl:variable name="endPort" select="@endPort"></xsl:variable>

       			 <xsl:variable name="parentName" select="name(//ciied:Port[@id=$startPort]/parent::*/parent::*/parent::*)"></xsl:variable>
       			 <xsl:variable name="fport" select="//ciied:Port[@id=$startPort]"></xsl:variable>
       			 <xsl:variable name="fromFlowEl" select="$fport/parent::*/parent::*"></xsl:variable>
       			 <xsl:variable name="fX" select="$fromFlowEl/@x"></xsl:variable>
       			 <xsl:variable name="fy" select="$fromFlowEl/@y"></xsl:variable>
       			 <xsl:variable name="fW" select="$fromFlowEl/@width"></xsl:variable>
       			 <xsl:variable name="fH" select="$fromFlowEl/@height"></xsl:variable>
       			 <xsl:variable name="fName" select="name($fromFlowEl)"></xsl:variable>
       			 <xsl:variable name="fDirX" select="$fport/@x"></xsl:variable>
       			 <xsl:variable name="fDirY" select="$fport/@y"></xsl:variable>
       			 <xsl:variable name="fHOffset" select="$fport/@horizontalOffset"></xsl:variable>
       			 <xsl:variable name="fVOffset" select="$fport/@verticalOffset"></xsl:variable>
       			 
       			 
       			 <xsl:variable name="tport" select="//ciied:Port[@id=$endPort]"></xsl:variable>
       			 <xsl:variable name="toFlowEl" select="$tport/parent::*/parent::*"></xsl:variable>
       			 <xsl:variable name="tX" select="$toFlowEl/@x"></xsl:variable>
       			 <xsl:variable name="ty" select="$toFlowEl/@y"></xsl:variable>
       			 <xsl:variable name="tW" select="$toFlowEl/@width"></xsl:variable>
       			 <xsl:variable name="tH" select="$toFlowEl/@height"></xsl:variable>
       			 <xsl:variable name="tName" select="name($toFlowEl)"></xsl:variable>
       			 <xsl:variable name="tDirX" select="$tport/@x"></xsl:variable>
       			 <xsl:variable name="tDirY" select="$tport/@y"></xsl:variable>
       			 <xsl:variable name="tHOffset" select="$tport/@horizontalOffset"></xsl:variable>
       			 <xsl:variable name="tVOffset" select="$tport/@verticalOffset"></xsl:variable>

       			<xsl:choose>
       				 <!--节点为子流程节点  -->
	  				<xsl:when  test="$parentName='bg:SubProcess'">
	   					 <xsl:variable name="fParent" select="//ciied:Port[@id=$startPort]/parent::*/parent::*/parent::*"></xsl:variable>
		       			  <xsl:variable name="tParent" select="//ciied:Port[@id=$endPort]/parent::*/parent::*/parent::*"></xsl:variable>
		       			  
		       			  <xsl:variable name="fX" select="calc:add($fX,$fParent/@x)"></xsl:variable>
		       			  <xsl:variable name="fy" select="calc:add($fy,$fParent/@y)"></xsl:variable>
		       			  
		       			  <xsl:variable name="tX" select="calc:add($tX,$tParent/@x)"></xsl:variable>
		       			  <xsl:variable name="ty" select="calc:add($ty,$tParent/@y)"></xsl:variable>
		       			  
		       			  <xsl:variable name="fX" select="calc:add($fX,'10')"></xsl:variable>
		       			  <xsl:variable name="fy" select="calc:add($fy,'28')"></xsl:variable>
		       			  
		       			  <xsl:variable name="tX" select="calc:add($tX,'10')"></xsl:variable>
		       			  <xsl:variable name="ty" select="calc:add($ty,'28')"></xsl:variable>
		       			  <xsl:value-of select="calc:calc($fName,$fX,$fy,$fW,$fH,$fHOffset,$fVOffset,$fDirX,$fDirY,$tName,$tX,$ty,$tW,$tH,$tHOffset,$tVOffset,$tDirX,$tDirY )" />
	   				</xsl:when>
	   				<xsl:otherwise>
	   					<xsl:value-of select="calc:calc($fName,$fX,$fy,$fW,$fH,$fHOffset,$fVOffset,$fDirX,$fDirY,$tName,$tX,$ty,$tW,$tH,$tHOffset,$tVOffset,$tDirX,$tDirY )" />
	   				</xsl:otherwise>
       			</xsl:choose>

      		</bpmndi:BPMNEdge>
		</xsl:for-each>
	</xsl:template>
	
	
	
	
	<xsl:template name="diagram_block"  >
		<xsl:param name="elName" />
		<xsl:for-each select="$elName">
			<bpmndi:BPMNShape >
				<xsl:variable name="name" select="name(.)"></xsl:variable>
				<xsl:variable name="parentName" select="name(./parent::*)"></xsl:variable>				
				<xsl:attribute name="bpmnElement">
					<xsl:value-of select="@id"></xsl:value-of>
				</xsl:attribute>
				<xsl:attribute name="id">BPMNShape_<xsl:value-of select="@id"></xsl:value-of>
				</xsl:attribute>

				<xsl:variable name="x" select="@x"></xsl:variable>
				<xsl:variable name="y" select="@y"></xsl:variable>
				
			
        		<omgdc:Bounds >
        			<xsl:choose>
        				<xsl:when test="$name='bg:StartEvent' or $name='bg:EndEvent'">
        					<xsl:attribute name="height"><xsl:value-of select="@width"></xsl:value-of></xsl:attribute>
        				</xsl:when>
        				<xsl:otherwise>
        					<xsl:attribute name="height"><xsl:value-of select="@height"></xsl:value-of></xsl:attribute>
        				</xsl:otherwise>
        			</xsl:choose>
        			<xsl:attribute name="width"><xsl:value-of select="@width"></xsl:value-of></xsl:attribute>
        			<xsl:choose>
        				<xsl:when  test="$parentName='bg:SubProcess'">
        					<xsl:variable name="parent" select="./parent::*"></xsl:variable>	
        					<xsl:variable name="x" select="calc:add($x,$parent/@x)"></xsl:variable>
	       					<xsl:variable name="y" select="calc:add($y,$parent/@y)"></xsl:variable>
	       					
	       					<xsl:variable name="x" select="calc:add($x,'10')"></xsl:variable>
	       					<xsl:variable name="y" select="calc:add($y,'28')"></xsl:variable>
	       					<xsl:attribute name="x"><xsl:value-of select="$x"></xsl:value-of></xsl:attribute>
        					<xsl:attribute name="y"><xsl:value-of select="$y"></xsl:value-of></xsl:attribute>
        				</xsl:when>
        				<xsl:otherwise>
        					<xsl:attribute name="x"><xsl:value-of select="$x"></xsl:value-of></xsl:attribute>
        					<xsl:attribute name="y"><xsl:value-of select="$y"></xsl:value-of></xsl:attribute>
        				</xsl:otherwise>
        			</xsl:choose>
        			
        		</omgdc:Bounds>
        		 
      		</bpmndi:BPMNShape>
		</xsl:for-each>
	</xsl:template>
	
	
	<xsl:template name="setAttrubute">
		<xsl:param name="obj" />
		<xsl:attribute name="id">
			<xsl:value-of select="$obj/@id"></xsl:value-of>
		</xsl:attribute>
		<xsl:attribute name="name">
			<xsl:value-of select="$obj/label"></xsl:value-of>
		</xsl:attribute>
			
	</xsl:template>
	


	<xsl:template match="/diagram">
		<xsl:apply-templates select="bg:StartEvent">
			</xsl:apply-templates>
			<xsl:apply-templates select="bg:EndEvent">
			</xsl:apply-templates>
			<xsl:apply-templates select="bg:Task">
			</xsl:apply-templates>
			<xsl:apply-templates select="bg:SequenceFlow">
			</xsl:apply-templates>
			<xsl:apply-templates select="bg:SubProcess">
			</xsl:apply-templates>
			<xsl:apply-templates select="bg:Gateway">
			</xsl:apply-templates>
			
	</xsl:template>

	<xsl:template match="bg:SubProcess">
		<xsl:for-each select=".">
		<subProcess>
			<xsl:call-template name="setAttrubute">
				<xsl:with-param name="obj" select="."></xsl:with-param>
			</xsl:call-template>
			<xsl:choose>
				<xsl:when test="@multiInstance='true'" >
						<extensionElements>
							<activiti:executionListener event="start" class="org.sz.platform.bpm.service.flow.listener.SubProcessStartListener"/>
							<activiti:executionListener event="end" class="org.sz.platform.bpm.service.flow.listener.SubProcessEndListener"/>						
						</extensionElements>
						<multiInstanceLoopCharacteristics activiti:elementVariable="assignee" >
							<xsl:attribute name="isSequential">
								<xsl:choose>
									<xsl:when test="@isSequential='true'">true</xsl:when>
									<xsl:otherwise>false</xsl:otherwise>
								</xsl:choose>
							</xsl:attribute>
							<xsl:attribute name="activiti:collection">${taskUserAssignService.getSignUser(execution)}</xsl:attribute>							
						</multiInstanceLoopCharacteristics>
				</xsl:when>
			</xsl:choose>
			
			
			<xsl:apply-templates select="bg:StartEvent">
			</xsl:apply-templates>
			<xsl:apply-templates select="bg:EndEvent">
			</xsl:apply-templates>
			<xsl:apply-templates select="bg:Task">
			</xsl:apply-templates>
			<xsl:apply-templates select="bg:SequenceFlow">
			</xsl:apply-templates>
			<xsl:apply-templates select="bg:Gateway">
			</xsl:apply-templates>

		</subProcess>
		</xsl:for-each>
	</xsl:template>
	
	<!-- 网关 -->
	<xsl:template match="bg:Gateway">
		<xsl:for-each select=".">
		<xsl:variable name="type" select="./gatewayType" />
		<xsl:choose>
			<xsl:when test="$type='AND'">
				<parallelGateway>
					<xsl:call-template name="setAttrubute">
						<xsl:with-param name="obj" select="."></xsl:with-param>
					</xsl:call-template>
				</parallelGateway>
			</xsl:when>
			<xsl:when test="$type='OR'">
				<inclusiveGateway>
					<xsl:call-template name="setAttrubute">
						<xsl:with-param name="obj" select="."></xsl:with-param>
					</xsl:call-template>
				</inclusiveGateway>
			</xsl:when>
			<xsl:otherwise>
				<exclusiveGateway>
					<xsl:call-template name="setAttrubute">
						<xsl:with-param name="obj" select="."></xsl:with-param>
					</xsl:call-template>
				</exclusiveGateway>
				
			</xsl:otherwise>
		</xsl:choose>
		</xsl:for-each>
	</xsl:template>

	<xsl:template match="bg:Task">
		<xsl:for-each select=".">
			<xsl:choose>
				<xsl:when test="@user='true'">
					<userTask >
						<xsl:call-template name="setAttrubute">
							<xsl:with-param name="obj" select="."></xsl:with-param>
						</xsl:call-template>
						<documentation>
							<xsl:value-of select="Description"></xsl:value-of>
						</documentation>
						<extensionElements>
						    <activiti:taskListener event="create" class="org.sz.platform.bpm.service.flow.listener.TaskCreateListener" />
						    <activiti:taskListener event="assignment" class="org.sz.platform.bpm.service.flow.listener.TaskAssignListener" />
						    <activiti:taskListener event="complete" class="org.sz.platform.bpm.service.flow.listener.TaskCompleteListener" />
						</extensionElements>
					</userTask>
				</xsl:when>
				<xsl:when test="@script='true'">
					<serviceTask activiti:class="org.sz.platform.bpm.service.flow.ScriptTask">
						<xsl:call-template name="setAttrubute">
							<xsl:with-param name="obj" select="."></xsl:with-param>
						</xsl:call-template>
					</serviceTask>
				</xsl:when>
			
				<xsl:when test="@mail='true'">
					<serviceTask activiti:class="org.sz.platform.bpm.service.flow.MessageTask">
						<xsl:call-template name="setAttrubute">
							<xsl:with-param name="obj" select="."></xsl:with-param>
						</xsl:call-template>
					</serviceTask>
				</xsl:when>
				<xsl:when test="@receive='true'">
					<receiveTask >
						<xsl:call-template name="setAttrubute">
							<xsl:with-param name="obj" select="."></xsl:with-param>
						</xsl:call-template>
					</receiveTask>
				</xsl:when>
				<xsl:when test="@businessRule='true'">
					<businessRuleTask >
						<xsl:call-template name="setAttrubute">
							<xsl:with-param name="obj" select="."></xsl:with-param>
						</xsl:call-template>
					</businessRuleTask>
				</xsl:when>
				<!-- 会签 -->
				<xsl:when test="@multiInstance='true'" > 
					<userTask >
						<xsl:attribute name="activiti:assignee">${assignee}</xsl:attribute>
						<xsl:attribute name="id">
							<xsl:value-of select="@id"></xsl:value-of>
						</xsl:attribute>
						<xsl:attribute name="name"><xsl:value-of select="label"></xsl:value-of></xsl:attribute>
						<extensionElements>
							<activiti:taskListener event="create" class="org.sz.platform.bpm.service.flow.listener.TaskSignCreateListener"/>
							<activiti:taskListener event="assignment" class="org.sz.platform.bpm.service.flow.listener.TaskAssignListener" />
						    <activiti:taskListener event="complete" class="org.sz.platform.bpm.service.flow.listener.TaskCompleteListener" />
						</extensionElements>
						<multiInstanceLoopCharacteristics   activiti:elementVariable="assignee" >
							<xsl:attribute name="isSequential">
								<xsl:choose>
									<xsl:when test="@isSequential='true'">true</xsl:when>
									<xsl:otherwise>false</xsl:otherwise>
								</xsl:choose>
							</xsl:attribute>
							<xsl:attribute name="activiti:collection">${taskUserAssignService.getSignUser(execution)}</xsl:attribute>
							<completionCondition >${signComplete.isComplete(execution) }</completionCondition>
						</multiInstanceLoopCharacteristics>
					</userTask>
				</xsl:when>
				<xsl:otherwise>
					
					<userTask >
						<xsl:call-template name="setAttrubute">
							<xsl:with-param name="obj" select="."></xsl:with-param>
						</xsl:call-template>
						<documentation>
							<xsl:value-of select="Description"></xsl:value-of>
						</documentation>
						<extensionElements>
						    <activiti:taskListener event="create" class="org.sz.platform.bpm.service.flow.listener.TaskCreateListener" />
						    <activiti:taskListener event="assignment" class="org.sz.platform.bpm.service.flow.listener.TaskAssignListener" />
						    <activiti:taskListener event="complete" class="org.sz.platform.bpm.service.flow.listener.TaskCompleteListener" />
						</extensionElements>
					</userTask>
				
				</xsl:otherwise>
			</xsl:choose>
			
		</xsl:for-each>
	</xsl:template>


	<xsl:template match="bg:StartEvent">
		<startEvent  activiti:initiator="startUser" >
			<xsl:call-template name="setAttrubute">
				<xsl:with-param name="obj" select="."></xsl:with-param>
			</xsl:call-template>
		</startEvent>
	</xsl:template>



	<xsl:template match="bg:EndEvent">
		<xsl:for-each select=".">
			<endEvent>
				<xsl:call-template name="setAttrubute">
					<xsl:with-param name="obj" select="."></xsl:with-param>
				</xsl:call-template>
			</endEvent>
		</xsl:for-each>
	</xsl:template>


	<xsl:template match="bg:SequenceFlow">

		<xsl:for-each select=".">
			<xsl:variable name="sourceRef" select="@startPort"></xsl:variable>
			<xsl:variable name="targetRef" select="@endPort"></xsl:variable>
			<sequenceFlow>
				<xsl:call-template name="setAttrubute">
					<xsl:with-param name="obj" select="."></xsl:with-param>
				</xsl:call-template>
				<xsl:attribute name="sourceRef">
					<xsl:value-of select="//ciied:Port[@id=$sourceRef]/parent::*/parent::*/@id"></xsl:value-of>
				</xsl:attribute>
				<xsl:attribute name="targetRef">
					<xsl:value-of select="//ciied:Port[@id=$targetRef]/parent::*/parent::*/@id"></xsl:value-of>
				</xsl:attribute>
				<!-- 设置跳转条件 -->
				<xsl:variable name="condition" select="./Condition" />
				<xsl:variable name="commentPre" >&lt;![CDATA[${</xsl:variable>
				<xsl:variable name="commentSuffix" >}]]&gt;</xsl:variable>
				<xsl:variable name="condition" select="./Condition" />
				<xsl:if test="$condition!=''">
					<conditionExpression xsi:type="tFormalExpression">
    					<xsl:value-of select="$commentPre" /><xsl:value-of select="$condition" /><xsl:value-of select="$commentSuffix" />
  					</conditionExpression>
				</xsl:if>
				
			</sequenceFlow>
		</xsl:for-each>
	</xsl:template>


</xsl:stylesheet>