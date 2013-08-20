package org.sz.platform.service.system;

import java.util.List;

import org.sz.core.service.BaseService;
import org.sz.platform.model.system.Identity;

public interface IdentityService extends BaseService<Identity>{

	boolean isAliasExisted(String alias);

	boolean isAliasExistedByUpdate(Identity identity);

	String nextId(String alias);

	List<Identity> getList();
	
	public  String getCurDate();
}