package org.sz.core.mybatis;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import javax.sql.DataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.managed.ManagedTransactionFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.NestedIOException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.util.Assert;

import org.sz.core.mybatis.SqlSessionFactoryFactoryBean;

public class SqlSessionFactoryFactoryBean implements FactoryBean,
		InitializingBean {
	Log logger = LogFactory.getLog(SqlSessionFactoryFactoryBean.class);
	private Resource configLocation;
	private Resource[] mapperLocations;
	private DataSource dataSource;
	private boolean useTransactionAwareDataSource = true;
	SqlSessionFactory sqlSessionFactory;

	public void afterPropertiesSet() throws Exception {
		Assert.notNull(this.configLocation, "configLocation must be not null");
		this.sqlSessionFactory = createSqlSessionFactory();
	}

	private SqlSessionFactory createSqlSessionFactory() throws IOException {
		Reader reader = new InputStreamReader(getConfigLocation()
				.getInputStream());
		try {
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder()
					.build(reader);
			Configuration conf = sqlSessionFactory.getConfiguration();

			if (this.dataSource != null) {
				DataSource dataSourceToUse = this.dataSource;
				if ((this.useTransactionAwareDataSource)
						&& (!(this.dataSource instanceof TransactionAwareDataSourceProxy))) {
					dataSourceToUse = new TransactionAwareDataSourceProxy(
							this.dataSource);
				}
				conf.setEnvironment(new Environment("development",
						new ManagedTransactionFactory(), dataSourceToUse));
				sqlSessionFactory = new SqlSessionFactoryBuilder().build(conf);
			}

			if (this.mapperLocations != null) {
				for (Resource mapperLocation : this.mapperLocations) {
					String path = "";
					if ((mapperLocation instanceof ClassPathResource))
						path = ((ClassPathResource) mapperLocation).getPath();
					else
						path = mapperLocation.toString();
					try {
						XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(
								mapperLocation.getInputStream(), conf, path,
								conf.getSqlFragments());

						xmlMapperBuilder.parse();
						conf.getMappedStatementNames(); // conf.buildAllStatements();
					} catch (Exception e) {
						throw new NestedIOException(
								"Failed to parse mapping resource: '"
										+ mapperLocation + "'", e);
					} finally {
						ErrorContext.instance().reset();
					}
				}
			}
			return sqlSessionFactory;
		} finally {
			reader.close();
		}
	}

	public Object getObject() throws Exception {
		return this.sqlSessionFactory;
	}

	public DataSource getDataSource() {
		return this.dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public Class getObjectType() {
		return SqlSessionFactory.class;
	}

	public boolean isSingleton() {
		return true;
	}

	public Resource getConfigLocation() {
		return this.configLocation;
	}

	public void setConfigLocation(Resource configurationFile) {
		this.configLocation = configurationFile;
	}

	public void setMapperLocations(Resource[] mapperLocations) {
		this.mapperLocations = mapperLocations;
	}
}