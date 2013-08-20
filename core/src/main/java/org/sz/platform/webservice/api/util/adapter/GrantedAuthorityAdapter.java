package org.sz.platform.webservice.api.util.adapter;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;

import org.sz.platform.webservice.api.util.adapter.GrantedAuthorityValue;

public class GrantedAuthorityAdapter extends
		XmlAdapter<GrantedAuthorityValue, GrantedAuthority> {
	public GrantedAuthorityValue marshal(GrantedAuthority v) throws Exception {
		return new GrantedAuthorityValue(v.getAuthority());
	}

	public GrantedAuthority unmarshal(GrantedAuthorityValue v) throws Exception {
		return new GrantedAuthorityImpl(v.authority);
	}
}
