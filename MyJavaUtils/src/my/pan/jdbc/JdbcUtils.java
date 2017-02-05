package my.pan.jdbc;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public final class JdbcUtils {

//	private static String user = "ycsq";
//	private static String passwd = "123456";
//	private static String url = "jdbc:db2://192.168.133.127:60000/ycsqdb";
//	// private static String url =
//	// "jdbc:oracle:thin:@192.168.133.135:1521:orcl";
//
//	static {
//		try {
//			// ע����
//			Class.forName("com.ibm.db2.jcc.DB2Driver");
//		} catch (ClassNotFoundException e) {
//			throw new ExceptionInInitializerError(e);
//		}
//	}
	
	private static String user = new String();       //"ycsq";
	private static String passwd = new String();     //"123456";
	private static String url = new String();        //"jdbc:db2://192.168.133.127:60000/ycsqdb";
	private static String classname = new String();
	private static Properties props = new Properties();
	// private static String url =
	// "jdbc:oracle:thin:@192.168.133.135:1521:orcl";

	static {
		try {
			InputStream in = JdbcUtils.class.getClassLoader().getResourceAsStream("dbpool.properties");
			props.load(in);
			classname = props.getProperty("driverNameClass");
			user = props.getProperty("username");
			passwd = props.getProperty("password");
			url = props.getProperty("url");
			
			Class.forName(classname);
		} catch (Exception e) {
			throw new ExceptionInInitializerError(e);
		}
	}

	/**
	 * @获取connection
	 * @return
	 * @throws SQLException
	 */
	public static Connection getConnection() throws SQLException {

		return DriverManager.getConnection(url, user, passwd);
	}

	/**
	 * @释放conn
	 * @param rs
	 * @param st
	 * @param conn
	 * @throws SQLException
	 */
	public static void free(ResultSet rs, Statement st, Connection conn)
			throws SQLException {
		try {
			if (rs != null)
				rs.close();
		} finally {
			try {
				if (st != null)
					st.close();
			} finally {
				if (conn != null)
					conn.close();
			}
		}
	}

}
