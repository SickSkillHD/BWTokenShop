package com.BernielDE.blackwizard.network.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.BernielDE.blackwizard.main.BWMainClass;

public final class BWDatabasePlayer {
	
	private String userName;
	
	private String uuid;
	
	private BWMySQLConnection sql;
	
	private BWTable TABLE_UTIL;
	
	// 	private String[] columns = {"uuid", "name", "isRegistered", "online", "userPlus", "vote_Time_left", "warn", "denied_to_join", "mute", "spy", "isBanned", "reason"};

	public BWDatabasePlayer(String uuid) {
		this.uuid = uuid;
		
		this.sql = BWMainClass.instance().getMySQL();
		Validate.notNull(sql);
		TABLE_UTIL = new BWTable(sql, BWMainClass.DATABASE_PLAYER_TABLE);
//		TABLE_UTIL = sql.getTable(BWMainClass.DATABASE_PLAYER_TABLE);
	}
	
	/**
	 * 
	 */
	public BWDatabasePlayer() {
		this.sql = BWMainClass.instance().getMySQL();
		Validate.notNull(sql);
		TABLE_UTIL = new BWTable(sql, BWMainClass.DATABASE_PLAYER_TABLE);
//		TABLE_UTIL = sql.getTable(BWMainClass.DATABASE_PLAYER_TABLE);
	}
	
	public BWDatabasePlayer(String name, String uuid) {
		this.uuid = uuid;
		this.userName = name;
		
		this.sql = BWMainClass.instance().getMySQL();
		Validate.notNull(sql);
		TABLE_UTIL = new BWTable(sql, BWMainClass.DATABASE_PLAYER_TABLE);
//		TABLE_UTIL = sql.getTable(BWMainClass.DATABASE_PLAYER_TABLE);
	}
	
	public boolean tableExist() {
		return TABLE_UTIL.exists();
	}
	
	private String UUID;
	
	public String getUUID() {
		try {
			UUID = TABLE_UTIL.getString("uuid", "currentName", userName);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return UUID;
	}
	
	private int warns = 0;
	
	public int getWarns() {
		try {
			warns = TABLE_UTIL.getInt("warn", "uuid", uuid);
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return warns;
	}
	
	public void setWarns() {
		try {
			warns = TABLE_UTIL.getInt("warn", "uuid", uuid);
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		++warns;
		
		Object[] v = {warns};
		String[] column = {"warn"};
		
		try {
			TABLE_UTIL.updateEntry(column, v, "uuid", uuid);
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private boolean isBanned = false;
	
	private int banned = 0;
	
	public boolean isBanned() {
		TABLE_UTIL = sql.getTable(BWMainClass.DATABASE_PLAYER_TABLE);
		try {
			banned = TABLE_UTIL.getInt("isBanned", "uuid", uuid);
			
			if(banned == 0) {
				return isBanned = false;
			}
			else {
				return isBanned = true;
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	private String reason = "";
	
	public String getReason() {
		TABLE_UTIL = sql.getTable(BWMainClass.DATABASE_PLAYER_TABLE);
		try {
			reason = TABLE_UTIL.getString("reason", "uuid", uuid);
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return reason;
	}
	
	public boolean isReason() {
		return getReason() == "null";
	}
	
	public void setReason(String reason) {
		TABLE_UTIL = sql.getTable(BWMainClass.DATABASE_PLAYER_TABLE);
		try {
			Object[] v = {reason};
			String[] column = {"reason"};
			
			TABLE_UTIL.updateEntry(column, v, "uuid", uuid);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void unban() {
		try {
			Object[] v = {"null"};
			String[] column = {"reason"};
			
			TABLE_UTIL.updateEntry(column, v, "uuid", uuid);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private boolean deniedTJ = false;
	
	private int denied = 0;
	
	public boolean isDeniedToJoin() {
		try {
			denied = TABLE_UTIL.getInt("denied_to_join", "uuid", uuid);
			
			if(denied == 0) {
				return deniedTJ = false;
			}
			else {
				return deniedTJ = true;
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public void setDeniedToJoin(boolean d) {
		if(d) {
			try {
				Object[] v = {1};
				String[] column = {"denied_to_join"};
				
				TABLE_UTIL.updateEntry(column, v, "uuid", uuid);
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		else {
			try {
				Object[] v = {0};
				String[] column = {"denied_to_join"};
				
				TABLE_UTIL.updateEntry(column, v, "uuid", uuid);
			} 
			catch (Exception e) {
				e.printStackTrace();
			}	
		}
	}
	
	private boolean isOnline = false;
	
	private int online = 0;
	
	public boolean isOnline() {
		try {
			online = TABLE_UTIL.getInt("online", "uuid", uuid);
			
			if(online == 0) {
				return isOnline = false;
			}
			else {
				return isOnline = true;
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public void setOnline(boolean o) {
		if(o) {
			try {
				Object[] v = {1};
				String[] column = {"online"};
				
				TABLE_UTIL.updateEntry(column, v, "uuid", uuid);
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		else {
			try {
				Object[] v = {0};
				String[] column = {"online"};
				
				TABLE_UTIL.updateEntry(column, v, "uuid", uuid);
			} 
			catch (Exception e) {
				e.printStackTrace();
			}	
		}
	}
	
	public int getOnline() {
		try {
			online = TABLE_UTIL.getInt("online", "uuid", uuid);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return online;
	}
	
	private String NAME;
	
	public String getName() {
		try {
			NAME = TABLE_UTIL.getString("name", "uuid", uuid);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return NAME;
	}
	
	public String getChangedName() {
		try {
			NAME = TABLE_UTIL.getString("currentName", "uuid", uuid);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return NAME;
	}
	
	public void setChangedName(String name) {
		try {
			Object[] v = {name};
			String[] column = {"currentName"};
			
			TABLE_UTIL.updateEntry(column, v, "uuid", uuid);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean hasChangedName(String name) {
		return getName() != name;
	}
	
	private boolean time_left = false;
	
	private long a = 0;
	
	public boolean isPendingOnPayment() {
		try {
			a = TABLE_UTIL.getLong("vote_Time_left", "uuid", uuid);
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(a == 0) {
			return time_left = false;
		}
		else {
			return time_left = true;
		}
	}
	
	public long getPendingOnPayment() {
		try {
			a = TABLE_UTIL.getLong("vote_Time_left", "uuid", uuid);
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return a;
	}
	
	public void setPendingOnPayment(long v) {
		Object[] vT = {v};
		String[] column = {"vote_Time_left"};
		
		try {
			TABLE_UTIL.updateEntry(column, vT, "uuid", uuid);
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private int registeredID = 0;
	
	private boolean registered = false;
	
	public boolean isRegistered() {
		try {
			registeredID = TABLE_UTIL.getInt("isRegistered", "uuid", uuid);
			
			if(registeredID == 0) {
				return registered = false;
			}
			else {
				return registered = true;
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public void setRegistered() {
		Object[] vT = {1};
		String[] column = {"isRegistered"};
		
		try {
			TABLE_UTIL.updateEntry(column, vT, "uuid", uuid);
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void unregister() {
		Object[] vT = {0};
		String[] column = {"isRegistered"};
		
		try {
			TABLE_UTIL.updateEntry(column, vT, "uuid", uuid);
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private boolean isUserPlus = false;
	
	private int userPlus = 0;
	
	public boolean isUserPlus() {
		try {
			userPlus = TABLE_UTIL.getInt("userPlus", "uuid", uuid);
			
			if(userPlus == 0) {
				return isUserPlus = false;
			}
			else {
				return isUserPlus = true;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public void setUserPlus(boolean u) {
		if(u) {
			Object[] v = {1};
			String[] column = {"userPlus"};
			
			try {
				TABLE_UTIL.updateEntry(column, v, "uuid", uuid);
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		else {
			Object[] v = {0};
			String[] column = {"userPlus"};
			
			try {
				TABLE_UTIL.updateEntry(column, v, "uuid", uuid);
			}
			catch (SQLException e) {
				e.printStackTrace();
			}	
		}
	}
	
	private boolean isObserving = false;
	
	private int observe = 0;
	
	public boolean isObserving() {
		try {
			observe = TABLE_UTIL.getInt("spy", "uuid", uuid);
			
			if(observe == 0) {
				return isObserving = false;
			}
			else {
				return isObserving = true;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public void setObserving(boolean b) {
		if (b) {
			Object[] v = {1};
			String[] column = {"spy"};
			
			try {
				TABLE_UTIL.updateEntry(column, v, "uuid", uuid);
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		} 
		else {
			Object[] v = {0};
			String[] column = {"spy"};
			
			try {
				TABLE_UTIL.updateEntry(column, v, "uuid", uuid);
			} 
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	private boolean isMuted = false;
	
	private int mute = 0;
	
	public boolean isMuted() {
		try {
			mute = TABLE_UTIL.getInt("mute", "uuid", uuid);
			
			if(observe == 0) {
				return isMuted = false;
			}
			else {
				return isMuted = true;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public void setMuted(boolean b) {
		if (b) {
			Object[] v = {1};
			String[] column = {"mute"};
			
			try {
				TABLE_UTIL.updateEntry(column, v, "uuid", uuid);
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		} 
		else {
			Object[] v = {0};
			String[] column = {"mute"};
			
			try {
				TABLE_UTIL.updateEntry(column, v, "uuid", uuid);
			} 
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	private boolean pendingOnReward = false;
	
	private int reward = 0;
	
	public boolean isPendingOnReward() {
		try {
			reward = TABLE_UTIL.getInt("reward", "uuid", uuid);
			
			if(reward == 0) {
				return pendingOnReward = false;
			} 
			else {
				return pendingOnReward = true;
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return pendingOnReward;
	}
	
	public int getReward() {
		try {
			reward = TABLE_UTIL.getInt("reward", "uuid", uuid);
			return reward;
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public void setPendingOnReward(boolean o) {
		if(o) {
			Object[] v = {1};
			String[] column = {"reward"};
			
			try {
				TABLE_UTIL.updateEntry(column, v, "uuid", uuid);
			} 
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		else {
			Object[] v = {0};
			String[] column = {"reward"};
			
			try {
				TABLE_UTIL.updateEntry(column, v, "uuid", uuid);
			} 
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	private boolean isPendingOnServer;
	
	private String vote;
	
	public boolean isPendingOnServer() {
		try {
			vote = TABLE_UTIL.getString("server_for_reward", "uuid", uuid);
			
			if(vote.equals("notPendingReward")) {
				return isPendingOnServer = false;
			}
			else {
				return isPendingOnServer = true;
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return isPendingOnServer;
	}
	
	public String getPendingOnServer() {
		try {
			vote = TABLE_UTIL.getString("server_for_reward", "uuid", uuid);
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return vote;
	}
	
	public void setPendingOnServer(String server) {
		Object[] v = {server};
		String[] column = {"server_for_reward"};
		
		try {
			TABLE_UTIL.updateEntry(column, v, "uuid", uuid);
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void resetPendingOnServer() {
		Object[] v = {"notPendingReward"};
		String[] column = {"server_for_reward"};
		
		try {
			TABLE_UTIL.updateEntry(column, v, "uuid", uuid);
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("resource")
	public static String getBanReason(String player) {
		BWMySQLConnection sql = BWMainClass.instance().getMySQL();
		Connection conn = sql.getConnection();
		ResultSet rs = null;
		PreparedStatement st = null;
		String reason = null;
		try {
			st = conn.prepareStatement("SELECT * FROM "  + BWMainClass.DATABASE_PLAYER_TABLE + " WHERE name=?");
			st.setString(1, player);
			rs = st.executeQuery();
			rs.last();
			if (rs.getRow() != 0) {
				rs.first();
				reason = rs.getString("reason");
			}
			return reason;
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
				sql.closeRessources(rs, st);
			} 
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public static void unban(String player) {
//		try {
//			Object[] v = {0};
//			String[] column = {"denied_to_join"};
//			
//			TABLE_UTIL.updateEntry(column, v, "name", player);
//		} 
//		catch (Exception e) {
//			e.printStackTrace();
//		}
	}
}
