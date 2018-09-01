package bt.gov.g2c.portal.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import bt.gov.g2c.portal.constant.BtConstants;
import bt.gov.g2c.portal.log.Log;

public class ConnectionManager {
	
	//public static String g2cDataSourceName = BtConstants.DATASOURCE_NAME;
	public static String alfrescoDataSourceName = BtConstants.ALFRESCO_DATASOURCE_NAME;
	public static DataSource g2cDataSource = null;
	public static DataSource alfrescoDataSource = null;

	public static DataSource getDatasourceName(String dataSourceName) {

		try {
			InitialContext ctx = new InitialContext();
			if (dataSourceName != null && !dataSourceName.equals("")) {
				try {
					g2cDataSource = (DataSource) ctx.lookup(dataSourceName);
				} catch (Exception e) {
					Log.error(" Exception occured within if block of getDatasourceName method ", e);
				}
			}
		} catch (NamingException e) {
			Log.error(" NamingException occured in getDatasourceName method ", e);
		}
		return g2cDataSource;
		
	}
	
	public static final Connection getConnection(String dataSourceName) {
		
		Connection conn = null;
		try {
			g2cDataSource = getDatasourceName(dataSourceName);
			if (g2cDataSource == null) {
				Log.debug(" Problem %%%%%%%%%%%%%%%%%%%%% ");
			} else {
				conn = g2cDataSource.getConnection();
			}
		} catch (SQLException e) {
			Log.error(" SQLException occured in getConnection method ", e);
		} catch (Exception e) {
			Log.error(" Exception occured  in getConnection method ", e);
		}
		return conn;
		
	}
	
	
	public static final Connection getConnection() {
		
		return getConnection(BtConstants.DATASOURCE_NAME_G2CDB);
	}
	
	public static Connection getDirectConnection() {
		Connection conn = null;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://172.16.149.5:3306/g2c", "root", "root");
			 
			 System.out.println("Connected to  database" + conn);

		}catch(Exception e){e.printStackTrace(); System.out.println("Connection ERROR");};
		
		return conn;
		}

	public static final Connection getAlfrescoConnection()

	{
		Connection conn = null;
		try {
			alfrescoDataSource = getAlfrescoDatasourceName();

			if (alfrescoDataSource == null) {
				Log.debug(" Problem %%%%%%%%%%%%%%%%%%%%% ");

			} else {
				conn = alfrescoDataSource.getConnection();

			}
		} catch (SQLException e) {
			Log.error(" SQLException occured in getConnection method ", e);

		} catch (Exception e) {
			Log.error(" Exception occured  in getConnection method ", e);

		}
		return conn;
	}

	private static DataSource getAlfrescoDatasourceName() {

		try {
			Context ctx = new InitialContext();
			if (alfrescoDataSource != null && !alfrescoDataSource.equals("")) {
				try {
					alfrescoDataSource = (DataSource) ctx.lookup(alfrescoDataSourceName);
				} catch (Exception e) {
					Log.error(" Exception occured within if block of getAlfrescoDatasourceName method ", e);
				}
			}
		} catch (NamingException e) {
			Log.error(" NamingException occured in getDatasourceName method ", e);
		}
		return g2cDataSource;
		
	}

	/**
	 * Returns all JDBC resources associated with the given connection to to the
	 * pool. All exceptions are handled by being logged. Should be called in
	 * 'finally{}' clause wherever JDBC connections are used.
	 * 
	 * @param conn
	 *            connection to be closed, or null
	 * @param stmt
	 *            statement to be closed, or null
	 * @param rset
	 *            ResultSet to be closed, or null
	 */
	public static void close(Connection conn, Statement stmt, ResultSet rset, PreparedStatement ps) // throws Exception
	{
		if (rset != null) {
			try {
				rset.close();
			} catch (Throwable ex) {
				Log.error(" Exception occured in if block(reset) of close method  ", ex);
			}
		}
		if (stmt != null) {
			try {
				stmt.close();
			} catch (Throwable ex) {
				Log.error(" Exception occured in if block(stmt) of close method  ", ex);
			}
		}
		if (ps != null) {
			try {
				ps.close();
			} catch (Throwable ex) {
				Log.error(" Exception occured in if block(ps) of close method  ", ex);
			}
		}

		if (conn != null) {
			try {
				conn.close();
			} catch (Throwable ex) {
				Log.error(" Exception occured in if block(conn) of close method  ", ex);
			}
		}
		
	}

}
