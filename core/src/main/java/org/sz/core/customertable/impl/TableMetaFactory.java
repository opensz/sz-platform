package org.sz.core.customertable.impl;

import org.sz.core.customertable.BaseTableMeta;
import org.sz.core.customertable.IDbView;
import org.sz.core.util.ContextUtil;
import org.sz.platform.system.model.SysDataSource;
import org.sz.platform.system.service.SysDataSourceService;

public class TableMetaFactory {
	public static BaseTableMeta getMetaData(String dsName) throws Exception {
		SysDataSourceService sysDataSourceService = (SysDataSourceService) ContextUtil
				.getBean(SysDataSourceService.class);

		SysDataSource sysDataSource = sysDataSourceService.getByAlias(dsName);
		String dbType = sysDataSource.getDbType();
		BaseTableMeta meta = null;
		if (dbType.equals("oracle"))
			meta = new OracleTableMeta();
		else if (dbType.equals("mysql"))
			meta = new MySqlTableMeta();
		else {
			throw new Exception("未知的数据库类型");
		}
		meta.setDataSource(sysDataSource);
		return meta;
	}

	public static IDbView getDbView(String dsName) throws Exception {
		SysDataSourceService sysDataSourceService = (SysDataSourceService) ContextUtil
				.getBean(SysDataSourceService.class);

		SysDataSource sysDataSource = sysDataSourceService.getByAlias(dsName);
		String dbType = sysDataSource.getDbType();
		IDbView meta = null;
		if (dbType.equals("oracle"))
			meta = new OracleDbView();
		else if (dbType.equals("sql2005"))
			meta = new SqlserverDbView();
		else if (dbType.equals("mysql"))
			meta = new MysqlDbView();
		else {
			throw new Exception("未知的数据库类型");
		}
		meta.setDataSource(sysDataSource);
		return meta;
	}
}
