package util;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ServiceLocator {
	private Context initalContext;

	private String dataSourceName_default = "jdbc/skin-sercet-maven";

	private static ServiceLocator serviceLocator = new ServiceLocator();

	public static ServiceLocator getInstance() {
		return serviceLocator;
	}

	private ServiceLocator() {
		try {
			this.initalContext = new InitialContext();
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	public DataSource getDataSource() {
		DataSource dataSource = null;
		try {
			Context ctx = (Context) initalContext.lookup("java:comp/env");
			dataSource = (DataSource) ctx.lookup(dataSourceName_default);
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return dataSource;
	}
	
	public DataSource getDataSource(String dataSourceName) {
		DataSource datasource = null;
		try {
			Context ctx = (Context) initalContext.lookup("java:comp/env");
			datasource = (DataSource) ctx.lookup(dataSourceName);
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return datasource;
	}
}