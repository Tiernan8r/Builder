package me.Tiernanator.Builder.Commands.Edits;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
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

public class Corners implements CommandExecutor {

	@SuppressWarnings("unused")
	private static BuilderMain plugin;

	private ChatColor warning = Colour.WARNING.getColour();
	private ChatColor informative = Colour.INFORMATIVE.getColour();
	private ChatColor highlight = Colour.HIGHLIGHT.getColour();
	private ChatColor good = Colour.GOOD.getColour();

	public Corners(BuilderMain main) {
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

//		if (!(player.hasPermission("build.corners"))) {
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
			player.sendMessage(
					warning + "You must specify a material for the corners.");
			return false;
		}

		String materialName = args[0].toUpperCase();
		if (!(BuildingMaterial.isMaterial(materialName))) {
			player.sendMessage(
					warning + "The material to be replaced is not a material, use the command: "
							+ informative + "/materials" + warning
							+ " to find out what the Materials are.");
			return false;
		}
		BuildingMaterial material = BuildingMaterial
				.getBuildingMaterial(materialName);

		UndoConfig.saveRegion(player, region, "undos");
		Block[] corners = region.corners();

		for (Block block : corners) {
			block.setType(material.getMaterial());
			block.setData((byte) material.getDamage());
		}

		player.sendMessage(good + "The corners for the selected Region between "
				+ highlight + region.getLowerX() + " " + region.getLowerY()
				+ " " + region.getLowerZ() + good + " and " + highlight
				+ region.getUpperX() + " " + region.getUpperY() + " "
				+ region.getUpperZ() + good + " have been set to the Material: "
				+ highlight + materialName + good + ".");

		return true;
	}

}
