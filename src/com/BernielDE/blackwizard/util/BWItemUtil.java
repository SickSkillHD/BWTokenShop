package com.BernielDE.blackwizard.util;

import net.minecraft.server.v1_7_R3.NBTTagCompound;
import net.minecraft.server.v1_7_R3.NBTTagList;

import org.bukkit.craftbukkit.v1_7_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;

public class BWItemUtil {
	
	/**
	 * <code>		
	 	ItemStack i = new ItemStack(Material.DIAMOND_SWORD);
		ItemMeta meta = i.getItemMeta();
		
		meta.setDisplayName("§ahi");
		
		i.setItemMeta(meta);
		
		ItemStack modified = ItemUtil.remove(i);
		try {
			EnchantmentGlow.addGlow(modified);
		}
		catch (Exception e) {
		}
		
		inventory.setItem(3, modified);
		</code>
	 * 
	 * @param i ItemStack
	 * @return ItemStack ohne Attribute
	 */
	
	public static ItemStack remove(ItemStack i) {
		try {
			net.minecraft.server.v1_7_R3.ItemStack itemstack = CraftItemStack.asNMSCopy(i);
			NBTTagCompound tag = itemstack.getTag();
			tag.set("AttributeModifiers", new NBTTagList());
			itemstack.setTag(tag);
			
			return CraftItemStack.asCraftMirror(itemstack);
		} 
		catch (Exception e) {
			return i;
		}	
	}
	
	/**
	 * <code>		
	 	ItemStack i = new ItemStack(Material.DIAMOND_SWORD);
		ItemMeta meta = i.getItemMeta();
		
		meta.setDisplayName("§ahi");
		
		i.setItemMeta(meta);
		
		ItemStack modified = ItemUtil.remove(i);
		try {
			EnchantmentGlow.addGlow(modified);
		}
		catch (Exception e) {
		}
		
		inventory.setItem(3, modified);
		</code>
	 * 
	 * @param i ItemStack
	 * @return ItemStack ohne Attribute
	 */
	
	public static ItemStack removePotionAttributes(ItemStack i){
		if(!(i.getItemMeta() instanceof PotionMeta)) return i;
		try {
			net.minecraft.server.v1_7_R3.ItemStack itemstack = CraftItemStack.asNMSCopy(i);
			NBTTagCompound tag = itemstack.getTag();
			tag.set("CustomPotionEffects", new NBTTagList());
			itemstack.setTag(tag);
			return CraftItemStack.asCraftMirror(itemstack);
		} 
		catch (Exception e) {
			return i;
		}
	}
}
