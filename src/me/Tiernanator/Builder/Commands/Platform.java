package me.Tiernanator.Builder.Commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Tiernanator.Builder.Main;
import me.Tiernanator.Colours.Colour;
import me.Tiernanator.Materials.BuildingMaterial;
import me.Tiernanator.Materials.IsMaterial;
import me.Tiernanator.Utilities.Locations.RelativeLocation;

public class Platform implements CommandExecutor {

	@SuppressWarnings("unused")
	private static Main plugin;

	private ChatColor warning = Colour.WARNING.getColour();
	private ChatColor informative = Colour.INFORMATIVE.getColour();
	private ChatColor bad = Colour.BAD.getColour();

	public Platform(Main main) {
		plugin = main;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		
		
		if (!(sender instanceof Player)) {
			sender.sendMessage(warning + "You can't use this command.");
			return true;
		}
		
		Player player = (Player) sender;
		
//		if(!(player.hasPermission("build.platform"))) {
//			player.sendMessage(warning + "You do not have permission to use this command.");
//			return true;
//		}
		Location location = RelativeLocation.getRelativeLocationsFromString(player, "~", "~-2", "~");
		if(location == null) {
			player.sendMessage(bad + "Error, Error...");
			return false;
		}
		Block block = location.getBlock();
		
		if(args.length == 0) {
			block.setType(Material.COBBLESTONE);
			player.sendMessage(ChatColor.YELLOW + "*Whoosh! A platform appears...*");
			return true;
		} else {
			String materialName = args[0].toUpperCase();
			if(!(IsMaterial.isMaterial(materialName))) {
				player.sendMessage(warning + "That is not a material, use the command: " + informative + "/listMaterials" + warning + " to find out what the Materials are.");
				return false;
			}
			BuildingMaterial material = BuildingMaterial.getBuildingMaterial(materialName);
			block.setType(material.getMaterial());
			block.setData((byte) material.getDamage());
			
			player.sendMessage(ChatColor.YELLOW + "*Whoosh! A platform of " + materialName + " appears...*");
		}
		return true;
	}

}
