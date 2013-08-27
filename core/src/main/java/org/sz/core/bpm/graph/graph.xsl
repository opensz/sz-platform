<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" 

	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	
	xmlns:bpmndi="http://www.omg.org/spec/BPMN/20100524/DI" 
	xmlns:omgdc="http://www.omg.org/spec/DD/20100524/DC"
	xmlns:activiti="http://activiti.org/bpmn"
	xmlns:calc="xalan://org.sz.core.bpm.graph.TransformUtil"
	extension-element-prefixes="calc">
	<xsl:output method="html" encoding="utf-8" indent="no" />
	<xsl:template match="/">
		<xsl:apply-templates select="//bpmndi:BPMNShape"> </xsl:apply-templates>
	</xsl:template>
	<xsl:template match="//bpmndi:BPMNShape">
		
		<xsl:for-each select=".">
			<div class="flowNode">
				<xsl:variable name="id"><xsl:value-of select="@bpmnElement"></xsl:value-of></xsl:variable>
				<xsl:attribute name="id" >
					<xsl:value-of select="$id"></xsl:value-of>
				</xsl:attribute>
				<xsl:variable name="obj" select="//process/descendant::*[@id=$id]"></xsl:variable>
				<xsl:variable name="parentName" select="name($obj/parent::*)"></xsl:variable>
				<xsl:attribute name="style"><xsl:choose><xsl:when test="$parentName='process'">z-index:110;</xsl:when><xsl:otherwise>z-index:111;</xsl:otherwise></xsl:choose> position:absolute;left:<xsl:value-of select="omgdc:Bounds/@x" />px;top:<xsl:value-of select="omgdc:Bounds/@y"/>px;width:<xsl:value-of select="omgdc:Bounds/@width"/>px;height:<xsl:value-of select="omgdc:Bounds/@height"/>px;</xsl:attribute>
				<xsl:call-template name="setAttribute" >
					<xsl:with-param name="id" ><xsl:value-of select="$id"></xsl:value-of></xsl:with-param>
				</xsl:call-template>
			</div>
		</xsl:for-each>
	</xsl:template>
	
	
	
	<xsl:template name="setAttribute" >
		<xsl:param name="id" />
		<xsl:variable name="obj" select="//process/descendant::*[@id=$id]"></xsl:variable>
		<xsl:variable name="name" select="$obj/@name"></xsl:variable>
		<xsl:variable name="type" select="name($obj)"></xsl:variable>
		<xsl:attribute name="title" >
			<xsl:value-of select="$name"></xsl:value-of>
		</xsl:attribute>
		<xsl:attribute name="type" >
			<xsl:choose>
				<xsl:when test="$type='userTask'">
					<xsl:variable name="objMulti" select="//process/descendant::*[@id=$id]/multiInstanceLoopCharacteristics"></xsl:variable>
					<xsl:choose>
						<xsl:when test="$objMulti">multiUserTask</xsl:when>
						<xsl:otherwise>userTask</xsl:otherwise>
					</xsl:choose>
				</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="$type"></xsl:value-of>
				</xsl:otherwise>
			</xsl:choose>
			
		</xsl:attribute>
	</xsl:template>
</xsl:stylesheet>