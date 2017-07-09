package me.Tiernanator.Builder.Commands.RegionSelect;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Tiernanator.Builder.BuilderMain;
import me.Tiernanator.Builder.Events.WandSelect;
import me.Tiernanator.Colours.Colour;
import me.Tiernanator.Utilities.Locations.Region.Region;

public class Cuboid implements CommandExecutor {

	@SuppressWarnings("unused")
	private static BuilderMain plugin;

	private ChatColor warning = Colour.WARNING.getColour();
	private ChatColor highlight = Colour.HIGHLIGHT.getColour();
	private ChatColor good = Colour.GOOD.getColour();

	public Cuboid(BuilderMain main) {
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
		
//		if(!(player.hasPermission("build.cuboid"))) {
//			player.sendMessage(warning + "You do not have permission to use this command.");
//			return true;
//		}
		List<Location> selectedLocations = WandSelect.getSelectedLocations(player);
		
		if(selectedLocations == null) {
			player.sendMessage(warning + "You must select corners with the building wand first.");
			return false;
		}
		if(selectedLocations.size() < 2) {
			player.sendMessage(warning + "You must select two corners for the Cuboid with the building wand.");
			return false;
		}
		if(selectedLocations.get(0) == null || selectedLocations.get(1) == null) {
			player.sendMessage(warning + "You must select two corners for the Cuboid with the building wand.");
			return false;
		}
		
		Location location1 = selectedLocations.get(0);
		Location location2 = selectedLocations.get(1);
		
		me.Tiernanator.Utilities.Locations.Region.Cuboids.Cuboid cuboid = null;
		try {
			cuboid = new me.Tiernanator.Utilities.Locations.Region.Cuboids.Cuboid(location1, location2);
		} catch (Exception e) {
			player.sendMessage(warning + "The two selected corners for the Cuboid must be in the same world.");
			return false;
		}
		
		WandSelect.setRegion(player, cuboid);
		
		Region region = WandSelect.getRegion(player);
		
		player.sendMessage(good + "The Cuboid Region has been selected between "
				+ highlight + region.getLowerX() + " " + region.getLowerY() + " "
				+ region.getLowerZ() + good + " & " + highlight
				+ region.getUpperX() + " " + region.getUpperY() + " "
				+ region.getUpperZ() + good + " for editting.");
		
		return true;
	}

}
