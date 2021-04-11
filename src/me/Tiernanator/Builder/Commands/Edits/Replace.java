package me.Tiernanator.Builder.Commands.Edits;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Tiernanator.Builder.BuilderMain;
import me.Tiernanator.Builder.Events.WandSelect;
import me.Tiernanator.Builder.Undo.UndoConfig;
import me.Tiernanator.Utilities.Colours.Colour;
import me.Tiernanator.Utilities.Locations.Region.Region;
import me.Tiernanator.Utilities.Materials.BuildingMaterial;

public class Replace implements CommandExecutor {

	@SuppressWarnings("unused")
	private static BuilderMain plugin;

	private ChatColor warning = Colour.WARNING.getColour();
	private ChatColor informative = Colour.INFORMATIVE.getColour();
	private ChatColor highlight = Colour.HIGHLIGHT.getColour();
	private ChatColor good = Colour.GOOD.getColour();
	private ChatColor bad = Colour.BAD.getColour();

	public Replace(BuilderMain main) {
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

//		if (!(player.hasPermission("build.replace"))) {
//			player.sendMessage(warning
//					+ "You do not have permission to use this command.");
//			return true;
//		}

		Region region = WandSelect.getRegion(player);
		
		if (region == null) {
			player.sendMessage(warning
					+ "You must select a region with the building wand first.");
			return false;
		}
		if (args.length < 1) {
			player.sendMessage(warning
					+ "You must specify the both the material you want replaced, and the one you want to replace it with.");
			return false;
		}
		if (args.length < 2) {
			player.sendMessage(warning
					+ "You must specify the material you want to replace with.");
			return false;
		}

		String currentMaterialName = args[0].toUpperCase();
		if (!(BuildingMaterial.isMaterial(currentMaterialName))) {
			player.sendMessage(
					warning + "The material to be replaced is not a material, use the command: "
							+ informative + "/materials" + warning
							+ " to find out what the Materials are.");
			return false;
		}
		BuildingMaterial currentMaterial = BuildingMaterial
				.getBuildingMaterial(currentMaterialName);
		
		if (!(region.contains(currentMaterial))) {
			player.sendMessage(bad + "There are no blocks of the material "
					+ informative + currentMaterialName + bad
					+ " to replace in this area.");
			return false;
		}

		String replacementMaterialName = args[1].toUpperCase();
		if (!(BuildingMaterial.isMaterial(replacementMaterialName))) {
			player.sendMessage(
					warning + "The replacement is not a material, use the command: "
							+ informative + "/materials" + warning
							+ " to find out what the Materials are.");
			return false;
		}
		BuildingMaterial replacementMaterial = BuildingMaterial
				.getBuildingMaterial(replacementMaterialName);

		UndoConfig.saveRegion(player, region, "undos");
		region.replace(currentMaterial,
				replacementMaterial);
		
		player.sendMessage(good + "The blocks of material " + highlight
				+ currentMaterialName + good + " in the selected Region have been replaced with the Material " + highlight
				+ replacementMaterialName + good + ".");

		return true;
	}

}
