package org.sz.platform.system.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

public class ParamResult<T> {
	private int type;
	private String typeName;
	private Collection<T> allList = null;
	private Map<String, Collection<T>> conditionMap = null;

	public void add(String condition, Collection<T> ul) {
		if (this.allList == null)
			this.allList = new HashSet();
		if (this.conditionMap == null)
			this.conditionMap = new HashMap();
		this.allList.addAll(ul);
		this.conditionMap.put(condition, ul);
	}

	public ParamResult(int t, String tn) {
		this.type = t;
		this.typeName = tn;
	}

	public int getType() {
		return this.type;
	}

	public String getTypeName() {
		return this.typeName;
	}

	public Collection<T> getUserList() {
		if ((this.type == 1) && (this.allList != null)
				&& (this.allList.size() > 0))
			return this.allList;
		if ((this.type == 1)
				&& ((this.allList == null) || (this.allList.size() < 0)))
			return null;
		if ((this.type == 2) && (this.allList != null)
				&& (this.allList.size() > 0))
			return getAndUserList();
		if ((this.type == 2)
				&& ((this.allList == null) || (this.allList.size() < 0)))
			return null;
		if (this.type == 3) {
			return this.allList;
		}
		return null;
	}

	private Collection<T> getAndUserList() {
		if (this.allList == null)
			return null;
		Collection<T> resList = new ArrayList<T>();
		Collection<Collection<T>> values = this.conditionMap.values();

		for (Iterator i$ = this.allList.iterator(); i$.hasNext();) {
			T t = (T) i$.next();
			boolean add = true;
			for (Collection<T> c : values) {
				if ((c == null) || (c.size() == 0))
					add = false;
				if (!c.contains(t))
					add = false;
			}
			if (add) {
				resList.add(t);
			}
		}
		return resList;
	}
}
