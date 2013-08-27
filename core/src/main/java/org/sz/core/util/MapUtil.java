package org.sz.core.util;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class MapUtil {
	public static String getString(Map map, String field) {
		field = field.toLowerCase();
		Set set = map.keySet();
		Iterator it = set.iterator();
		Hashtable ht = new Hashtable();
		while (it.hasNext()) {
			String key = (String) it.next();
			ht.put(key.toLowerCase(), key);
		}
		field = (String) ht.get(field);
		Object obj = map.get(field);
		return obj != null ? obj.toString().trim() : "";
	}

	public static long getLong(Map map, String field) {
		String value = getString(map, field);
		if (value.equals(""))
			return -1L;
		return Long.parseLong(value);
	}

	public static int getInt(Map map, String field) {
		String value = getString(map, field);
		if (value.equals(""))
			return -1;
		return Integer.parseInt(value);
	}

	public static float getFloat(Map map, String field) {
		String value = getString(map, field);
		if (value.equals(""))
			return -1.0F;
		return Float.parseFloat(value);
	}

	public static double getDouble(Map map, String field) {
		String value = getString(map, field);
		if (value.equals(""))
			return -1.0D;
		return Double.parseDouble(value);
	}
}
