package org.sz.platform.webservice.api;

import java.util.Collection;
import javax.jws.WebMethod;
import javax.jws.WebService;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.sz.platform.model.system.SysUser;

@WebService
public abstract interface UserDetailsService
{
  @WebMethod(operationName="UserDetailsService_loadUserByUsername")
  public abstract SysUser loadUserByUsername(String paramString)
    throws UsernameNotFoundException, DataAccessException;

  @WebMethod(operationName="UserDetailsService_loadRoleByUsername")
  public abstract Collection<String> loadRoleByUsername(String paramString);
}

