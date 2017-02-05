package my.pan.jdbc;


import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSourceFactory;

public final class DbcpDataSourceUtils {
	
	private static DataSource dataSource = null;
	private static Properties props = new Properties();
	
	static{
		try {
			InputStream in = DbcpDataSourceUtils.class.getClassLoader().getResourceAsStream("dbpool.properties");
			props.load(in);
			dataSource = BasicDataSourceFactory.createDataSource(props);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}
	
	public static DataSource getDataSource(){
		return dataSource;
	}

}
