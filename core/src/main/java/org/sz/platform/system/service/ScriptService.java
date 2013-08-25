package org.sz.platform.system.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.sz.core.service.BaseService;
import org.sz.platform.system.model.Script;

public interface ScriptService  extends BaseService<Script>{

	List<String> getDistinctCategory();

	String importXml(InputStream inputStream) throws Exception;

	String exportXml(Long[] tableId) throws FileNotFoundException, IOException;

}