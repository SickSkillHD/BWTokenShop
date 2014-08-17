package com.BernielDE.blackwizard.main;

import org.bukkit.plugin.java.JavaPlugin;

import com.BernielDE.blackwizard.network.database.BWMySQLConnection;

public class BWMainClass extends JavaPlugin {
	
	static BWMainClass bwmc;
	
	public static BWMainClass instance() {
		return bwmc;
	}
	
	public static String DATABASE_PLAYER_TABLE = "bw_utildb_player";
	
	private BWMySQLConnection sql;
	
	@Override
	public void onEnable() {
		try {
			this.sql = new BWMySQLConnection(this);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public BWMySQLConnection getMySQL() {
		return this.sql;
	}
}
