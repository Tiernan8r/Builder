package me.Tiernanator.Builder.Events;

import me.Tiernanator.Builder.BuilderMain;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class WandBreakBlock implements Listener {
	
	@SuppressWarnings("unused")
	private BuilderMain plugin;
	
	public WandBreakBlock(BuilderMain main) {
		plugin = main;
	}
	
	@SuppressWarnings({"deprecation"})
	@EventHandler
	public void onPlayerWandBreakBlock(BlockBreakEvent event) {
		
		if(event.isCancelled()) {
			return;
		}
		
		Player player = event.getPlayer();
		
		ItemStack item = player.getItemInHand();
		Material itemType = item.getType();
		
		if(!(itemType == Material.STICK)) {
			return;
		}
		String itemName;
		
		boolean hasName = item.getItemMeta().hasDisplayName();
		
		if(!(hasName)) {
			return;
		}
		itemName = item.getItemMeta().getDisplayName();

		if(!(itemName.equalsIgnoreCase(ChatColor.AQUA + "Wand"))) {
			return;
		}
		
		event.setCancelled(true);
		
	}
	
}
