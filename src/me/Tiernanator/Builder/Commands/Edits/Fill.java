package me.Tiernanator.Builder.Commands.Edits;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Tiernanator.Builder.Main;
import me.Tiernanator.Builder.Events.WandSelect;
import me.Tiernanator.Colours.Colour;
import me.Tiernanator.Materials.BuildingMaterial;
import me.Tiernanator.Materials.IsMaterial;
import me.Tiernanator.Utilities.Locations.Region.Region;

public class Fill implements CommandExecutor {

	@SuppressWarnings("unused")
	private static Main plugin;

	private ChatColor warning = Colour.WARNING.getColour();
	private ChatColor informative = Colour.INFORMATIVE.getColour();
	private ChatColor highlight = Colour.HIGHLIGHT.getColour();
	private ChatColor good = Colour.GOOD.getColour();

	public Fill(Main main) {
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
		
//		if(!(player.hasPermission("build.fill"))) {
//			player.sendMessage(warning + "You do not have permission to use this command.");
//			return true;
//		}
		Region region = WandSelect.getRegion(player);
		
		if(region == null) {
			player.sendMessage(warning + "You must select a region with the building wand first.");
			return false;
		}
		if(args.length < 1) {
			player.sendMessage(warning + "You must specify a material to fill with.");
			return false;
		}
		
		String materialName = args[0].toUpperCase();
		if(!(IsMaterial.isMaterial(materialName))) {
			player.sendMessage(
					warning + "The material to be replaced is not a material, use the command: "
							+ informative + "/materials" + warning
							+ " to find out what the Materials are.");
			return false;
		}
		BuildingMaterial material = BuildingMaterial.getBuildingMaterial(materialName);
		
//		UndoConfig.saveRegion(player, region, "undos");
		region.fill(material);
		
		player.sendMessage(good + "The selected Region has been filled with the Material " + highlight + materialName + good + ".");
		
		return true;
	}

}
