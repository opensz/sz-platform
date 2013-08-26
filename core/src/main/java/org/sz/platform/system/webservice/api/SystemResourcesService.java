package org.sz.platform.system.webservice.api;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebService;

import org.sz.platform.system.model.ResourcesUrlExt;
import org.sz.platform.system.model.SysRole;

@WebService
public abstract interface SystemResourcesService {
	@WebMethod(operationName = "SystemResourcesService_loadSecurityUrl")
	public abstract List<ResourcesUrlExt> loadSecurityUrl(String paramString);

	@WebMethod(operationName = "SystemResourcesService_loadSecurityFunction")
	public abstract List<ResourcesUrlExt> loadSecurityFunction(
			String paramString);

	@WebMethod(operationName = "SystemResourcesService_loadSecurityRole")
	public abstract List<SysRole> loadSecurityRole(String paramString1,
			String paramString2);

	@WebMethod(operationName = "SystemResourcesService_checkSecurityUrl")
	public abstract List<ResourcesUrlExt> checkSecurityUrl(String paramString);

	@WebMethod(operationName = "SystemResourcesService_checkSecurityFunction")
	public abstract List<ResourcesUrlExt> checkSecurityFunction(
			String paramString);
}
