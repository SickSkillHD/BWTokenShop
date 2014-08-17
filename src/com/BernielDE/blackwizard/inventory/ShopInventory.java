package com.BernielDE.blackwizard.inventory;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.BernielDE.blackwizard.util.BWItemUtil;
import com.BernielDE.blackwizard.util.ItemUtil;

public class ShopInventory {
	
	public static Inventory getHaupInventory(Player p){
		Inventory inv = Bukkit.createInventory(null, 27, "§c§lTokens:§7 ");
		ItemStack pvp = ItemUtil.getItem(Material.POTION, 1, 8257, "§cPVP§7-§cSERVER", Arrays.asList("§cComing soon.."));
		ItemStack mpvp = BWItemUtil.removePotionAttributes(pvp);
		
		ItemStack kit = ItemUtil.getItem(Material.GOLDEN_APPLE, 1, 0, "§aKIT§7-§aSERVER", Arrays.asList("§cComing soon.."));
		ItemStack soup = ItemUtil.getItem(Material.MUSHROOM_SOUP, 1, 0, "§3SOUP§7-§3SERVER", Arrays.asList("§cComing soon.."));
		ItemStack rpg = ItemUtil.getItem(Material.SKULL_ITEM, 1, 1, "§eRPG§7-§eSERVER", Arrays.asList("§cComing soon.."));
		ItemStack rang = ItemUtil.getItem(Material.BOOK, 1, 0, "§aRÄNGE", null);
		
		ItemStack glass = ItemUtil.getItem(160, 1, 15, " ", null);
		
		inv.setItem(0, mpvp);
		inv.setItem(1, glass);
		inv.setItem(2, kit);
		inv.setItem(3, glass);
		inv.setItem(4, soup);
		inv.setItem(5, glass);
		inv.setItem(6, rpg);
		inv.setItem(7, glass);
		inv.setItem(8, rang);
		for(int i = 9; i < 27; i++){
			inv.setItem(i, glass);
		}
		
		return inv;
	}

}
