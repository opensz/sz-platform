package org.sz.platform.service.system;

import java.util.List;

import org.sz.core.service.BaseService;
import org.sz.platform.model.system.SysParam;

public interface SysParamService  extends BaseService<SysParam>{

	List<SysParam> getUserParam();

	List<SysParam> getOrgParam();
	
	String setParamIcon(String ctx,String json);

}