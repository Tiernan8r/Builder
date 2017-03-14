package me.Tiernanator.Builder.Commands.Edits.Cuboidular;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Tiernanator.Builder.Main;
import me.Tiernanator.Builder.Events.WandSelect;
import me.Tiernanator.Builder.Undo.UndoConfig;
import me.Tiernanator.Colours.Colour;
import me.Tiernanator.Materials.BuildingMaterial;
import me.Tiernanator.Materials.IsMaterial;
import me.Tiernanator.Utilities.Locations.Region.Cuboids.Cuboid;

public class Walls implements CommandExecutor {

	@SuppressWarnings("unused")
	private static Main plugin;

	private ChatColor warning = Colour.WARNING.getColour();
	private ChatColor informative = Colour.INFORMATIVE.getColour();
	private ChatColor highlight = Colour.HIGHLIGHT.getColour();
	private ChatColor good = Colour.GOOD.getColour();

	public Walls(Main main) {
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
		
//		if(!(player.hasPermission("build.wall"))) {
//			player.sendMessage(warning + "You do not have permission to use this command.");
//			return true;
//		}
		List<Location> cornerLocations = WandSelect.getSelectedLocations(player); 
		
		if(cornerLocations == null) {
			player.sendMessage(warning + "You must select edges with the building wand first.");
			return false;
		}
		if(cornerLocations.size() < 2) {
			player.sendMessage(warning + "You must select your two corners first.");
			return false;
		}
		if(args.length < 1) {
			player.sendMessage(warning + "You must specify a material for the walls.");
			return false;
		}
		
		Location location1 = cornerLocations.get(0);
		Location location2 = cornerLocations.get(1);
		
		Cuboid cuboid = new Cuboid(location1, location2);
		
		String materialName = args[0].toUpperCase();
		if(!(IsMaterial.isMaterial(materialName))) {
			player.sendMessage(
					warning + "The material to be replaced is not a material, use the command: "
							+ informative + "/materials" + warning
							+ " to find out what the Materials are.");
			return false;
		}
		BuildingMaterial material = BuildingMaterial.getBuildingMaterial(materialName);
		
		UndoConfig.saveRegion(player, cuboid, "undos");
		cuboid.walls(material);
		
		player.sendMessage(good + "The walls of the Area between " + highlight + location1.getX() + " " + location1.getY() + " " + location1.getZ() + good + " and " + highlight + location2.getX() + " " + location2.getY() + " " + location2.getZ() + good + " have been built from the Material " + highlight + materialName + good + ".");
		
		return true;
	}

}
