/**
 * 
 */
package com.BernielDE.blackwizard.util;

import java.sql.SQLException;

import org.bukkit.entity.Player;

import com.BernielDE.blackwizard.main.BWMainClass;
import com.BernielDE.blackwizard.network.database.BWMySQLConnection;
import com.BernielDE.blackwizard.network.database.BWTable;
import com.BernielDE.blackwizard.network.database.DataType;

/**
 * @author fl0W
 * @version 0.1
 * 
 * @creation 19.07.2014 at 19:34:30
 * @class BWTableData.java in @package com.fl0W.blackwizard.commandlib.util in @project BWCommandLib
 **/

public class BWTableData {
	
	private BWMainClass bwmc;
	
	private BWTable TABLE_UTIL;
	
	private String[] columns = {"uuid", "name", "currentName", "isRegistered", "online", "userPlus", "reward", "server_for_reward", "vote_Time_left", "warn", "denied_to_join", "mute", "spy", "isBanned", "reason"};
	
	private DataType[] types = {DataType.TEXT, DataType.TEXT, DataType.TEXT, DataType.BOOLEAN, 
			DataType.BOOLEAN, DataType.BOOLEAN, DataType.BOOLEAN, DataType.TEXT, DataType.INT, DataType.INT, DataType.BOOLEAN, DataType.BOOLEAN, DataType.BOOLEAN, DataType.BOOLEAN, DataType.TEXT};
	
	private String[] colums = {"token", "rank", "isRegistered"};
	
	private DataType[] type = {DataType.INT, DataType.TEXT, DataType.INT};
	
	private BWMySQLConnection sql;
	
	private BWTable TABLE_TOKEN;
	
	/**
	 * 
	 */
	public BWTableData(BWMainClass bwmc) {
		this.bwmc = bwmc;
		this.sql = this.bwmc.getMySQL();
		
		TABLE_TOKEN = new BWTable(sql, "bw_util_token");
		
		if(!TABLE_TOKEN.exists()) {
			try {
				TABLE_TOKEN.create(colums, type);
			} 
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
//		TABLE_UTIL = sql.getTable(BWMainClass.DATABASE_PLAYER_TABLE);
	}
	
	/**
	 * 
	 */
	public BWTableData() {
		this.sql = BWMainClass.instance().getMySQL();
		
		TABLE_TOKEN = new BWTable(sql, "bw_util_token");
		
		if(!TABLE_TOKEN.exists()) {
			try {
				TABLE_TOKEN.create(colums, type);
			} 
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	private Player p;
	
	/**
	 * 
	 */
	public BWTableData(Player p) {
		this.p = p;
		this.sql = BWMainClass.instance().getMySQL();
		
		TABLE_TOKEN = new BWTable(sql, "bw_util_token");
		
		if(!TABLE_TOKEN.exists()) {
			try {
				TABLE_TOKEN.create(colums, type);
			} 
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void insertPlayer() {
		if(TABLE_TOKEN.exists()) {
			Object[] v = {"100", "null", "1"};
			
			try {
				TABLE_TOKEN.insertEntry(colums, v);
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public boolean tableExists() {
		return TABLE_TOKEN.exists();
	}
}
