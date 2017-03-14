package me.Tiernanator.Builder.WorldTemplates;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Tiernanator.Builder.Main;
import me.Tiernanator.Builder.Events.WandSelect;
import me.Tiernanator.Builder.Undo.UndoConfig;
import me.Tiernanator.Colours.Colour;
import me.Tiernanator.Utilities.Locations.Region.Region;
import me.Tiernanator.Utilities.MetaData.MetaData;

public class Move implements CommandExecutor {

	private static Main plugin;

	private ChatColor warning = Colour.WARNING.getColour();
	private ChatColor highlight = Colour.HIGHLIGHT.getColour();
	private ChatColor good = Colour.GOOD.getColour();

	public Move(Main main) {
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

		// if (!(player.hasPermission("build.move"))) {
		// player.sendMessage(warning
		// + "You do not have permission to use this command.");
		// return true;
		// }
		Region region = WandSelect.getRegion(player);

		if (region.allBlocks() == null) {
			player.sendMessage(warning
					+ "You must select a region with the building wand first.");
			return false;
		}

		// Region template = Save.getSavedRegion(player);
		String regionName = (String) MetaData.getMetadata(player,
				"TemplateRegion", plugin);
		if (regionName == null) {
			player.sendMessage(
					warning + "You must use " + highlight + "/getTemplate"
							+ warning + " to select a saved region first.");
			return false;
		}
		Region template = TemplateConfig.getRegion(regionName);
		// Region template = new Region(UndoConfig.getRegion(regionName));

		if (template.allBlocks() == null || template.allBlocks().isEmpty()) {
			player.sendMessage(
					warning + "You must use " + highlight + "/getTemplate"
							+ warning + " to select a saved region first.");
			return false;
		}
		UndoConfig.saveRegion(player, region, "undos");

		Location baseLocation = region.getLowerNE();

		List<Block> templateBlocks = template.allBlocks();

		for (Block b : templateBlocks) {

			Location base = baseLocation;

			Location relativeBlockLocation = template
					.getRelativeBlock(b.getLocation()).getLocation();

			double x1 = relativeBlockLocation.getX();
			double y1 = relativeBlockLocation.getY();
			double z1 = relativeBlockLocation.getZ();

			double x2 = base.getX();
			double y2 = base.getY();
			double z2 = base.getZ();

			double no1 = x2 + x1;
			double no2 = y2 + y1;
			double no3 = z2 + z1;

			Location rel = new Location(base.getWorld(), no1, no2, no3);

			rel.getBlock().setType(b.getType());
			rel.getBlock().setData(b.getData());

		}

		player.sendMessage(
				good + "The Region between " + highlight + region.getLowerX()
						+ " " + region.getLowerY() + " " + region.getLowerZ()
						+ good + " and " + highlight + region.getUpperX() + " "
						+ region.getUpperY() + " " + region.getUpperZ() + good
						+ " has been changed to the saved Template.");

		return true;
	}

}
