package me.Tiernanator.Builder.Events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import me.Tiernanator.Builder.BuilderMain;
import me.Tiernanator.Utilities.Players.PlayerTime;
import me.Tiernanator.Utilities.Players.SelectAction;

public class PlayerInteract implements Listener {
	
	@SuppressWarnings("unused")
	private BuilderMain plugin;
	
	public PlayerInteract(BuilderMain main) {
		plugin = main;
	}
	
	private int rightClickBalancer = 0;
	
	//An event handler that detects when the player uses a wand on a block and calls my custom wand block event
	@SuppressWarnings({"deprecation"})
	@EventHandler
	public void onPlayerWandInteract(PlayerInteractEvent event) {
		
		//get the type of interaction, we are only interested in players clicking on blocks
		Action action = event.getAction();
		SelectAction selectAction = SelectAction.getSelectAction(action);
		
		if(selectAction == SelectAction.UNKNOWN) {
			return;
		}
		
		//get the player
		Player player = event.getPlayer();
		
		//get the time intervals between clicks, and set the previous time to now
		long currentPlayerTime =  System.currentTimeMillis();
		
		long previousTime = 0;
		PlayerTime playerTime = new PlayerTime(player);
		if(playerTime.hasPlayerTime()) {
			previousTime = playerTime.getPreviousPlayerTime();
		}

		//determine the difference, if it is less than 4, don't call the event
		long timeDifference = currentPlayerTime - previousTime;
		
		if(timeDifference < 1000) {
			return;
		}
		playerTime.addPlayerTime(currentPlayerTime);
		
		if(selectAction == SelectAction.RIGHT_CLICK) {
			if(rightClickBalancer % 2 == 0) {
				rightClickBalancer++;
				return;
			}
			rightClickBalancer++;
		}
		
		//get the player's held item
		ItemStack item = player.getItemInHand();
		Material itemType = item.getType();
		//the wand is a stick so we aren't interested in anything else
		if(!(itemType == Material.STICK)) {
			return;
		}

		String itemName;
		//check if it's a normal stick or if it has been renamed
		boolean hasName = item.getItemMeta().hasDisplayName();
		if(!(hasName)) {
			return;
		}
		
		//get the item's name and check if it is called the name for a wand
		itemName = item.getItemMeta().getDisplayName();
		if(!(itemName.equalsIgnoreCase(ChatColor.AQUA + "Wand"))) {
			return;
		}
		event.setCancelled(true);
		//thenn get the clicked block's location and call the event
		Block block = event.getClickedBlock();
		Location blockLocation = block.getLocation();
		//create the custom event
		WandSelectEvent wandEvent = new WandSelectEvent(blockLocation, player, selectAction);
		// Call the event
		Bukkit.getServer().getPluginManager().callEvent(wandEvent);
		event.setCancelled(true);
		
	}
	
}
