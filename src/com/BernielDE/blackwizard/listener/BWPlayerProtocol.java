/**
 * 
 */
package com.BernielDE.blackwizard.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import ru.tehkode.permissions.PermissionManager;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import com.BernielDE.blackwizard.network.database.BWDatabasePlayer;
import com.BernielDE.blackwizard.util.BWTableData;

/**
 * @author fl0W
 * @version 0.1
 * 
 * @creation 17.08.2014 at 18:20:27
 * @class BWPlayerProtocol.java in @package com.BernielDE.blackwizard.listener in @project BWTokenShop
 **/

public class BWPlayerProtocol implements Listener {
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		final Player p = e.getPlayer();
		
		BWDatabasePlayer dbPlayer = new BWDatabasePlayer(p.getName(), p.getUniqueId().toString());
		BWTableData data = new BWTableData(p);
		
		final String uuid = p.getUniqueId().toString();
		
		System.out.println(uuid);
		
		PermissionManager pm = PermissionsEx.getPermissionManager();
		
		if(!dbPlayer.isRegistered()) {
			dbPlayer.setRegistered();
			data.insertPlayer();
		}
		
		if(dbPlayer.hasRank()) {
			if(pm.getUser(p).inGroup("donator")) {
				return;
			}
			pm.getUser(p).setGroups(new String[] {"donator"});
		}
	}
}
