package me.Tiernanator.Builder.Commands.Edits.Liquids;

import org.bukkit.ChatColor;
import org.bukkit.Material;
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

public class Flood implements CommandExecutor {

	@SuppressWarnings("unused")
	private static BuilderMain plugin;

	private ChatColor warning = Colour.WARNING.getColour();
	private ChatColor informative = Colour.INFORMATIVE.getColour();
	private ChatColor highlight = Colour.HIGHLIGHT.getColour();
	private ChatColor good = Colour.GOOD.getColour();
	private ChatColor bad = Colour.BAD.getColour();
	
	public Flood(BuilderMain main) {
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

//		if (!(player.hasPermission("build.flood"))) {
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
					warning + "You must specify if you want lava or water");
			return false;
		}
		String materialName = args[0].toUpperCase();
		if (!(materialName.equalsIgnoreCase("Lava")
				|| materialName.equalsIgnoreCase("Water"))) {
			player.sendMessage(
					warning + "That is not a liquid, the options are: "
							+ highlight + "Lava" + warning + " or " + highlight
							+ "Water" + warning + ".");
			return false;
		}
		BuildingMaterial liquid = BuildingMaterial
				.getBuildingMaterial(materialName);

		if (!(region.contains(Material.WATER)
				|| region.contains(Material.LEGACY_STATIONARY_WATER)
				|| region.contains(Material.LAVA)
				|| region.contains(Material.LEGACY_STATIONARY_LAVA)
				|| region.contains(Material.AIR))) {
			player.sendMessage(bad + "There is no space to flood...");
			return false;
		}
		
		UndoConfig.saveRegion(player, region, "undos");
		region.drain();
		region.replace(BuildingMaterial.getBuildingMaterial("AIR"), liquid);

		player.sendMessage(good + "The selected Region has been flooded with "
				+ informative + materialName + good + ".");

		return true;
	}

}
