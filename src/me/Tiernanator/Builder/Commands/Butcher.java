package me.Tiernanator.Builder.Commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import me.Tiernanator.Builder.BuilderMain;
import me.Tiernanator.Builder.Events.WandSelect;
import me.Tiernanator.Colours.Colour;
import me.Tiernanator.Utilities.Locations.Region.Region;

public class Butcher implements CommandExecutor {

	private static BuilderMain plugin;

	private ChatColor warning = Colour.WARNING.getColour();
	private ChatColor informative = Colour.INFORMATIVE.getColour();
	private ChatColor highlight = Colour.HIGHLIGHT.getColour();
	private ChatColor good = Colour.GOOD.getColour();
	private ChatColor bad = Colour.BAD.getColour();
	private ChatColor caution = Colour.CAUTION.getColour();

	public Butcher(BuilderMain main) {
		plugin = main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		
		
		if (!(sender instanceof Player)) {
			sender.sendMessage(warning + "You can't use this command.");
			return true;
		}
		Player player = (Player) sender;
		
		Region region = WandSelect.getRegion(player);
		
		if(region == null) {
			player.sendMessage(warning + "You must select a region with the building wand first.");
			return false;
		}
		if(args.length < 1) {
			player.sendMessage(warning + "You must provide the name of the entities to butcher.");
			return false;
		}
		
		
		BukkitRunnable runnable = new BukkitRunnable() {
			
			@Override
			public void run() {
				
				String entityName = args[0].toUpperCase();
				
				EntityType entityType = null;
				try {
					entityType = EntityType.valueOf(entityName);
				}catch (Exception e) {
						player.sendMessage(caution + "That is not an entity.");
						entityType = null;
				}
				if(entityType == null || entityType == EntityType.UNKNOWN) {
					player.sendMessage(caution + "That is not an entity.");
					player.sendMessage(good + "The entities are: ");
					for(EntityType eType : EntityType.values()) {
						player.sendMessage(informative + " - " + eType.name());
					}
					return;
				}
				
				List<Entity> worldEntities = region.getCenter().getWorld().getEntities();
				List<Entity> validEntities = new ArrayList<Entity>();
				
				for(Entity entity : worldEntities) {
					EntityType type = entity.getType();
					if(type == entityType) {
						
						Location entityLocation = entity.getLocation();
						if(region.contains(entityLocation)) {
							validEntities.add(entity);
						}
						
					}
				}
				
				if(validEntities.isEmpty()) {
					player.sendMessage(bad + "There are no entities of the type " + highlight + entityName + bad + " in the selected Region.");
					return;
				}
				
				for(Entity entity : validEntities) {
					entity.remove();
				}
				String entityGrammar = "entities";
				String hasGrammar = "have";
				if(validEntities.size() == 1) {
					entityGrammar = "entity";
					hasGrammar = "has";
				}
				
				player.sendMessage(informative + Integer.toString(validEntities.size()) + good +" " + entityGrammar + " of type " + highlight + entityName + good + " " + hasGrammar + " been butchered in the selected region.");
				
			}
		};
		runnable.runTaskAsynchronously(plugin);

//		String entityName = args[0].toUpperCase();
//		
//		EntityType entityType = null;
//		try {
//			entityType = EntityType.valueOf(entityName);
//		}catch (Exception e) {
//				player.sendMessage(caution + "That is not an entity.");
//				entityType = null;
//		}
//		if(entityType == null || entityType == EntityType.UNKNOWN) {
//			player.sendMessage(caution + "That is not an entity.");
//			player.sendMessage(good + "The entities are: ");
//			for(EntityType eType : EntityType.values()) {
//				player.sendMessage(informative + " - " + eType.name());
//			}
//			return false;
//		}
//		List<Entity> worldEntities = region.getCenter().getWorld().getEntities();
//		List<Entity> validEntities = new ArrayList<Entity>();
//		
//		for(Entity entity : worldEntities) {
//			EntityType type = entity.getType();
//			if(type == entityType) {
//				
//				Location entityLocation = entity.getLocation();
//				if(region.contains(entityLocation)) {
//					validEntities.add(entity);
//				}
//				
//			}
//		}
//		
//		if(validEntities.isEmpty()) {
//			player.sendMessage(bad + "There are no entities of the type " + highlight + entityName + bad + " in the selected Region.");
//			return false;
//		}
//		
//		for(Entity entity : validEntities) {
//			entity.remove();
//		}
//		String entityGrammar = "entities";
//		String hasGrammar = "have";
//		if(validEntities.size() == 1) {
//			entityGrammar = "entity";
//			hasGrammar = "has";
//		}
//		
//		player.sendMessage(informative + Integer.toString(validEntities.size()) + good +" " + entityGrammar + " of type " + highlight + entityName + good + " " + hasGrammar + " been butchered in the selected region.");
//		
		return true;
	}

}
