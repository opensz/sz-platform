package org.sz.platform.system.service;

import java.util.List;

import org.sz.core.service.BaseService;
import org.sz.platform.system.model.SysParam;

public interface SysParamService  extends BaseService<SysParam>{

	List<SysParam> getUserParam();

	List<SysParam> getOrgParam();
	
	String setParamIcon(String ctx,String json);

}