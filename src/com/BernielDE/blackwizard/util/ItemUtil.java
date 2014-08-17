package com.BernielDE.blackwizard.util;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemUtil {
	
	public static ItemStack getItem(Material material, int amount, int shortid, String name, List<String> lore){
		ItemStack is = new ItemStack(material, amount, (short) shortid);
		ItemMeta meta = is.getItemMeta();
		meta.setDisplayName(name);
		meta.setLore(lore);
		
		return is;
	}

	public static ItemStack getItem(int material, int amount, int shortid, String name, List<String> lore){
		ItemStack is = new ItemStack(material, amount, (short) shortid);
		ItemMeta meta = is.getItemMeta();
		meta.setDisplayName(name);
		meta.setLore(lore);
		
		return is;
	}
}
