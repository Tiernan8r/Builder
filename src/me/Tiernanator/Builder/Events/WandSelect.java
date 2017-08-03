package me.Tiernanator.Builder.Events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import me.Tiernanator.Builder.BuilderMain;
import me.Tiernanator.Utilities.Colours.Colour;
import me.Tiernanator.Utilities.Locations.Region.Region;
import me.Tiernanator.Utilities.Locations.Region.Cuboids.Cuboid;
import me.Tiernanator.Utilities.Players.SelectAction;

public final class WandSelect implements Listener {

	public WandSelect(BuilderMain main) {
	}

	private static HashMap<Player, List<Location>> locationsMap = new HashMap<Player, List<Location>>();
	private static HashMap<Player, Region> playerRegions = new HashMap<Player, Region>();
	
	private ChatColor informative = Colour.INFORMATIVE.getColour();
	private ChatColor highlight = Colour.HIGHLIGHT.getColour();
	private ChatColor good = Colour.GOOD.getColour();

	public static List<Location> getSelectedLocations(Player player) {

		if (!(locationsMap.containsKey(player))) {
			return null;
		}
		List<Location> locations = locationsMap.get(player);
		
//		Region region = new Region(locations.get(0), locations.get(1));
//		String playerUUID = player.getUniqueId().toString();
//		UndoConfig.saveRegion(playerUUID, region, "undos");
		
		return locations;

	}

	public static Region getRegion(Player player) {

		if (playerRegions.containsKey(player)) {
			return playerRegions.get(player);
		}

		List<Location> selectedLocations = getSelectedLocations(player);

		if (selectedLocations == null || selectedLocations.isEmpty()
				|| selectedLocations.size() < 2) {
			return null;
		}

		Cuboid cuboid = new Cuboid(selectedLocations.get(0),
				selectedLocations.get(1));

		Region region = new Region(cuboid);

//		String playerUUID = player.getUniqueId().toString();
//		UndoConfig.saveRegion(playerUUID, region, "undos");
		
		return region;

	}

	public static void setRegion(Player player, List<Block> blocks) {

		if (playerRegions.containsKey(player)) {
			playerRegions.remove(player);
		}

		Region region = new Region(blocks);
		
		playerRegions.put(player, region);

	}
	
	public static void setRegion(Player player, Region region) {

		if (playerRegions.containsKey(player)) {
			playerRegions.remove(player);
		}

		playerRegions.put(player, region);

	}
	
	public static void setRegion(Player player, Cuboid cuboid) {

		if (playerRegions.containsKey(player)) {
			playerRegions.remove(player);
		}

		Region region = new Region(cuboid);
		
		playerRegions.put(player, region);

	}

	@EventHandler
	public void wandSelectBlockEvent(WandSelectEvent event) {

		List<Location> blockListLocations = new ArrayList<Location>();
		Player player = event.getPlayer();
		Location blockLocation = event.getSelectedLocation();

		if (locationsMap.containsKey(player)) {

			blockListLocations = locationsMap.get(player);

		}
		int size = blockListLocations.size();
		if (size == 0) {
			blockListLocations.add(null);
			blockListLocations.add(null);
		} else if(size == 1) {
			blockListLocations.add(null);
		}
		
		SelectAction action = event.getAction();
		
		String actionPhrase = "";		
		if(action.equals(SelectAction.RIGHT_CLICK)) {
			blockListLocations.set(1, blockLocation);
			actionPhrase = "Second Location";
		} else if(action.equals(SelectAction.LEFT_CLICK)) {
			blockListLocations.set(0, blockLocation);
			actionPhrase = "First Location";
		}
		locationsMap.put(player, blockListLocations);

		player.sendMessage(good + "The " + informative + actionPhrase + good + " at " + highlight
				+ blockLocation.getBlockX() + " " + blockLocation.getY() + " "
				+ blockLocation.getBlockZ() + good
				+ " has been registered for world building.");

	}
}
