package com.BernielDE.blackwizard.network.database;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.lang.Validate;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.scheduler.BukkitRunnable;

import com.BernielDE.blackwizard.main.BWMainClass;
import com.BernielDE.blackwizard.util.Secure;

public class BWMySQLConnection {

	private String host;
	private int port;
	private String user;
	private String password;
	private String database;

	private Connection conn;
	
	private BWMainClass bwmc;

	public BWMySQLConnection(BWMainClass bwmc) throws Exception {
		this.bwmc = bwmc;
		
		File file = new File("plugins/BWMySQL/", "database.yml");
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

		String db = "database.";
		cfg.addDefault(db + "host", "localhost");
		cfg.addDefault(db + "port", 3306);
		cfg.addDefault(db + "user", "user");
		cfg.addDefault(db + "password", "password");
		cfg.addDefault(db + "database", "database");
		cfg.options().copyDefaults(true);
		try {
			cfg.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.host = cfg.getString(db + "host");
		this.port = cfg.getInt(db + "port");
		this.user = cfg.getString(db + "user");
		this.password = cfg.getString(db + "password");
		this.database = cfg.getString(db + "database");

		new BukkitRunnable() {
			
			@Override
			public void run() {
				try {
					if(getConnection() == null) {
						openConnection();
					}
				} 
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.runTaskAsynchronously(this.bwmc);
	}

	public Connection openConnection() throws Exception {
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database + "?autoReconnect=true&maxReconnect=5", this.user, this.password);
		this.conn = conn;
		return conn;
//		BWDBConnectionPooling dbPool = new BWDBConnectionPooling("jdbc:mysql://"
//				+ this.host + ":" + this.port + "/" + this.database, this.user, this.password);
//		Connection[] connArr = new Connection[7];
//		for (int i = 0; i < connArr.length; i++) {
//			conn = connArr[i];
//            connArr[i] = dbPool.connectionCheck();
//            System.out.println("Checking Connections..." + connArr[i]);
//            System.out.println("Connections Available... "
//                    + dbPool.availableCount());
//        }
//		return conn;
	}

	public Connection getConnection() {
		return conn;
	}

	public boolean hasConnection() {
		try {
			return conn != null
					|| conn.isValid(1);
		}
		catch (SQLException e) {
			return false;
		}
	}

	public void reconnect() throws Exception {
		if (conn == null || conn.isClosed()) {
			openConnection();
		}
		else {
			return;
		}
	}

	@SuppressWarnings("resource")
	public ResultSet getQuery(String query) throws SQLException {
		ResultSet rs = null;
		PreparedStatement stmt = conn.prepareStatement(query);
		rs = stmt.executeQuery(query);
		return rs;
	}

	public void queryUpdate(String query) {
		Connection conn = this.conn;
		PreparedStatement st = null;
		try {
			if (getConnection() == null)
				reconnect();
		}
		catch (Exception e1) {
			e1.printStackTrace();
		}
		try {
			st = conn.prepareStatement(query);
			st.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Failed to send update: " + query);
			System.out.println("Error: " + e.toString());
			System.out.println("SQLState: " + e.getSQLState());
			System.out.println("Stacktrace: ");
			e.printStackTrace();
		} 
		finally {
			try {
				this.closeRessources(null, st);
			} 
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	@SuppressWarnings("resource")
	public void queryResult(String query) throws SQLException {
		Connection con = this.conn;
	    PreparedStatement st = null;
	    st = con.prepareStatement(query);
	    st.executeUpdate();
	    closeRessources(null, st);
	}

	public void closeRessources(ResultSet rs, PreparedStatement st) throws SQLException {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
			}
		}
		if (st != null) {
			try {
				st.close();
			} catch (SQLException e) {
			}
		}
	}

	public void closeConnection() {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conn = null;
		}
	}

	@SuppressWarnings("resource")
	public ResultSet query(String qry) {
		ResultSet rs = null;

		try {
			if(getConnection() == null) reconnect();
			Statement stmt = conn.createStatement();
			rs = stmt.executeQuery(qry);

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}

		return rs;
	}

	private PreparedStatement st;

	private ResultSet rs;

	/**
	 * @param o
	 *            for value to insert
	 * @param column
	 *            for belonging value for <b>o</b>
	 * @notice statement: UPDATE table SET field = ? WHERE otherField = ? |
	 *         table => Tabelle - "bw_^" | field => Spalte - "level" | ? =
	 *         actual value | otherField => identifier for first value | ? =
	 *         value for identifier |
	 * @throws Exception
	 */

	@Secure(secure = true)
	public void update(String table, String field, String otherField,
			Object value, Object otherValue) throws Exception {
		st = null;

		conn.prepareStatement("UPDATE " + table + " SET " + field
				+ "= ? WHERE " + field + " = ?");
		st.setObject(1, value);
		st.setObject(2, otherValue);
		rs = st.executeQuery();

		if (hasConnection())
			this.closeRessources(rs, st);
	}

	/**
	 * @param o
	 *            [] for multiple values to insert
	 * @param column
	 *            for belonging value for <b>o</b>
	 * @throws Exception
	 */

	@Secure(secure = true)
	public void update(Object[] value, String table, String field, String column)
			throws Exception {
		st = null;

		conn.prepareStatement("UPDATE " + table + " SET = ? WHERE " + field
				+ " = ?");
		st.setObject(1, value);
		st.setObject(2, column);
		rs = st.executeQuery();

		if (hasConnection())
			this.closeRessources(rs, st);
	}

	/**
	 * @param o
	 *            [] for multiple values to insert like: new
	 *            Object[]{"'value', 'value'"}
	 * @throws Exception
	 */

	@Secure(secure = true)
	public void insert(Object[] value, String table, String... field)
			throws Exception {
		st = null;

		conn.prepareStatement("INSERT INTO " + table + " (?) VALUES ('?')");
		st.setObject(1, field);
		st.setObject(2, value);
		rs = st.executeQuery();

		if (hasConnection())
			this.closeRessources(rs, st);
	}

	/**
	 * @param o
	 *            for value to insert
	 * @throws Exception
	 */

	@Secure(secure = true)
	public void insert(Object value, String table, String... field)
			throws Exception {
		st = null;

		conn.prepareStatement("INSERT INTO " + table + " (?) VALUES ('?')");
		st.setObject(1, field);
		st.setObject(2, value);
		rs = st.executeQuery();

		if (hasConnection())
			this.closeRessources(rs, st);
	}

	/**
	 * @param field
	 *            for field in database
	 * @param column
	 *            for column in <b>field</b>
	 * @throws Exception
	 */

	@Secure(secure = true)
	public void delete(String table, String field, String column)
			throws Exception {
		st = null;

		conn.prepareStatement("DELETE FROM " + table + " WHERE ? = '?'");
		st.setObject(1, field);
		st.setObject(2, column);
		rs = st.executeQuery();

		if (hasConnection())
			this.closeRessources(rs, st);
	}

	@SuppressWarnings("resource")
	@Secure(secure = true)
	public Object get(String column, String validate, String table) {
		BWMySQLConnection sql = BWMainClass.instance().getMySQL();
		Connection conn = sql.getConnection();
		ResultSet rs = null;
		PreparedStatement st = null;
		Object a = null;
		try {
			st = conn.prepareStatement("SELECT * FROM " + table + " WHERE "
					+ validate + " = ?");
			st.setObject(1, a);
			rs = st.executeQuery();
			rs.last();
			if (rs.getRow() != 0) {
				rs.first();
				a = rs.getString(column);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				sql.closeRessources(rs, st);
			} 
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return a;
	}
	
	@SuppressWarnings("resource")
	@Secure(secure = true)
	public Object get(String column, String validate, String table, Object searchedValue) {
		BWMySQLConnection sql = BWMainClass.instance().getMySQL();
		Connection conn = sql.getConnection();
		ResultSet rs = null;
		PreparedStatement st = null;
		Object a = null;
		try {
			st = conn.prepareStatement("SELECT " + searchedValue + " FROM " + table + " WHERE "
					+ validate + " = ?");
			st.setObject(1, a);
			rs = st.executeQuery();
			rs.last();
			if (rs.getRow() != 0) {
				rs.first();
				a = rs.getString(column);
			}
		} catch (SQLException e) {
			System.err.println("Failed to send update: " + st);
			System.out.println("Error: " + e.toString());
			System.out.println("SQLState: " + e.getSQLState());
			System.out.println("Stacktrace: ");
			e.printStackTrace();
		} finally {
			try {
				sql.closeRessources(rs, st);
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		Validate.notNull(a, "The result is null!");
		return a;
	}
	
	@SuppressWarnings("resource")
	@Secure(secure = true)
	public Integer getInteger(String column, String field, String table) {
		BWMySQLConnection sql = BWMainClass.instance().getMySQL();
		Connection conn = sql.getConnection();
		ResultSet rs = null;
		PreparedStatement st = null;
		Integer a = 0;
		try {
			st = conn.prepareStatement("SELECT * FROM " + table + " WHERE "
					+ field + " = ?");
			st.setObject(1, a);
			rs = st.executeQuery();
			rs.last();
			if (rs.getRow() != 0) {
				rs.first();
				a = rs.getInt(column);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				sql.closeRessources(rs, st);
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return a;
	}

	@Deprecated
	@SuppressWarnings("resource")
	public ResultSet queryU(String qry) {
		ResultSet rs = null;

		try {
			Statement stmt = conn.createStatement();
			rs = stmt.executeQuery(qry);

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}

		return rs;
	}

	public void update(String qry) {
		try {
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(qry);

			stmt.close();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	public void updateQuery(String qry) {
		try {
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(qry);

			stmt.close();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	private static BWMySQLConnection SQL;
	
	public static BWMySQLConnection instance() {
		return SQL;
	}
	
	public com.BernielDE.blackwizard.network.database.BWTable getTable(String tablename) {
		return new BWTable(this, tablename);
	}
}
