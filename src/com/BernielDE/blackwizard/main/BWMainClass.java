package com.BernielDE.blackwizard.main;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.BernielDE.blackwizard.listener.BWPlayerProtocol;
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
		Bukkit.getPluginManager().registerEvents(new BWPlayerProtocol(), this);
		try {
			this.sql = new BWMySQLConnection(this);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * Token API:
	 * Wichtigster Schritt:
	 * - BWDatabasePlayer dbPlayer = new BWDatabasePlayer(uuid);
	 * 
	 * Methoden:
	 * 
	 * getToken() - Token
	 * setToken(int t) - Setze die Token
	 * 
	 * hasRank() - Rankabfrage (bspw. if(!hasRank) setRank("rank");
	 * getRank() - Rank
	 * setRank(String rank) - Setze den Rang
	 */
	
	public BWMySQLConnection getMySQL() {
		return this.sql;
	}
}
