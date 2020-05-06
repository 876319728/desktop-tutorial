package top.baivip.utils;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataSourceUtil {
	private static ComboPooledDataSource dataSource;
	private static ThreadLocal<Connection> c1=new ThreadLocal<>();
	static {
		//创建c3p0的连接池对象
		dataSource = new ComboPooledDataSource();
	}
	public static DataSource getDataSource(){
		return dataSource;
	}

	/**
	 * 当我调用这个方法 的时候  直接返回的就是线程所拥有那个 connection对象
	 * @return
	 * @throws SQLException
	 */
	public static Connection getConnection() throws SQLException {
		Connection connection = c1.get();

		if(connection==null){
			connection=dataSource.getConnection();
			c1.set(connection);
		}
		return  connection;
	}
	public static void beginTransaction() throws SQLException {
		Connection connection = getConnection();
		connection.setAutoCommit(false);
	}
	public static void commit() throws SQLException {
		Connection connection = getConnection();
		connection.commit();
	}
	public static void rollback()  {
		try {
			Connection connection = getConnection();
			connection.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void commitAndClose(){
		try {
			commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close();
		}

	}
	public static void rollbackAndClose(){
		try {
			rollback();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			close();
		}

	}

	public static void close(){
		try {
			Connection connection = getConnection();
			connection.close();
			//解除与当前线程的关系
			c1.remove();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}





	public static void release(Connection connection, Statement statement, ResultSet resultSet){
		if(resultSet!=null){
			try {
				resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(statement!=null){
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(connection!=null){
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

}
