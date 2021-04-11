package me.Tiernanator.Builder.WorldTemplates;

//import java.util.ArrayList;
//import java.util.List;
//
//import org.bukkit.ChatColor;
//import org.bukkit.OfflinePlayer;
//import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
//import org.bukkit.entity.Player;
//
//import me.Tiernanator.Builder.Main;
//import me.Tiernanator.Builder.Events.WandSelect;
//import me.Tiernanator.Colours.Colour;
//import me.Tiernanator.Utilities.Events.PlayerLogger;
//import me.Tiernanator.Utilities.Locations.Region.Region;
//import me.Tiernanator.Utilities.Locations.Zones.ZoneName;
//import me.Tiernanator.Utilities.Players.GetPlayer;
//
public class Protect implements CommandExecutor {
//
//	private static Main plugin;
//
//	ChatColor warning = Colour.WARNING.getColour();
//	ChatColor informative = Colour.INFORMATIVE.getColour();
//	ChatColor highlight = Colour.HIGHLIGHT.getColour();
//	ChatColor good = Colour.GOOD.getColour();
//	ChatColor bad = Colour.BAD.getColour();
//
//	public Protect(Main main) {
//		plugin = main;
//	}
//
//	//Doesn't Currently work until I fix Faction Zones Plugin.
//	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
//		
//		if (!(sender instanceof Player)) {
//			sender.sendMessage(warning + "You can't use this command.");
//			return true;
//		}
//		
//		Player player = (Player) sender;
//		
////		if(!(player.hasPermission("build.protect"))) {
////			player.sendMessage(warning + "You do not have permission to use this command.");
////			return true;
////		}
//		Region region = WandSelect.getRegion(player);
//		
//		if(region == null) {
//			player.sendMessage(warning + "You must select a region with the building wand first.");
//			return false;
//		}
//		
//		if(args.length < 2) {
//			player.sendMessage(warning + "Please specify the name of the zone, and then any owners: You may choose a " + highlight + "Player " + warning + ", " + highlight + "None " + warning + " or " + highlight + "All" + warning + ".");
//			return false;
//		}
//		
//		String zoneName = args[0];
//		
//		List<String> allProtectedZones = null; 
//		allProtectedZones = ExtraZone.allZones();
//		if(allProtectedZones != null) {
//			if(allProtectedZones.contains(zoneName)) {
//				player.sendMessage(bad + "That zone name already exists!");
//				return false;
//			}
//		}
//		
//		List<String> ownerUUIDs = new ArrayList<String>();
//		List<String> ownerNames = new ArrayList<String>();
//		for(int i = 1; i < args.length; i++) {
//			String givenName = args[i];
//			if(givenName.equalsIgnoreCase("None")) {
//				ownerUUIDs.add("[None]");
//				ownerNames.add("[None]");
//				break;
//			}
//			
//			if(givenName.equalsIgnoreCase("All")) {
//				ownerUUIDs.add("[All]");
//				ownerNames.add("[All]");
//				break;
//			}
//			
//			OfflinePlayer playerOwner = GetPlayer.functionGetOfflinePlayer(givenName, plugin);
//			if(playerOwner == null) { 
//				player.sendMessage(bad + "The player " + informative + givenName + bad + " does not exist.");
//				return false;
//			}
//			String playerUUID = PlayerLogger.getPlayerUUIDByName(playerOwner.getName());
//			ownerUUIDs.add(playerUUID);
//			ownerNames.add(playerOwner.getName());
//		}
//		List<Block> blocks = region.allBlocks();
//		
//		List<String> allExtraZones = ExtraZone.allZones();
//		if(allExtraZones != null) {
//			for(String zone : allExtraZones) {
//				List<Block> zoneBlocks = ExtraZone.getBlocks(zone);
//				for(Block b : blocks) {
//					if(zoneBlocks.contains(b)) {
//						player.sendMessage(bad + "This selected region overlaps with the zone " + highlight + zone + bad + ", you must move your selected zone as a result.");
//						return false;
//					}
//				}
//			}
//		}
//		
//		ExtraZone.addExtraZone(zoneName, ownerUUIDs, blocks);
//		
//		if(ownerUUIDs.isEmpty()) {
//			ownerUUIDs.add("[None]");
//		}
//		
//		String parsedName = ZoneName.parseZoneCodeToName(zoneName);
//		
//		player.sendMessage(good + "The Region " + informative + parsedName + good + " has been registered as a protected region, accessible only to:");
//		for(String i : ownerNames) {
//			player.sendMessage(highlight + " - " + i);
//		}
//		
//		
		return true;
	}
}