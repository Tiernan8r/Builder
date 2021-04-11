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

public class Drain implements CommandExecutor {

	@SuppressWarnings("unused")
	private static BuilderMain plugin;

	private ChatColor warning = Colour.WARNING.getColour();
	private ChatColor highlight = Colour.HIGHLIGHT.getColour();
	private ChatColor good = Colour.GOOD.getColour();
	private ChatColor bad = Colour.BAD.getColour();

	public Drain(BuilderMain main) {
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

//		if (!(player.hasPermission("build.drain"))) {
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

		if (!(region.contains(Material.WATER)
				|| region.contains(Material.LEGACY_STATIONARY_WATER)
				|| region.contains(Material.LAVA)
				|| region.contains(Material.LEGACY_STATIONARY_LAVA))) {
			player.sendMessage(bad + "There are no liquids to drain...");
			return false;
		}

		UndoConfig.saveRegion(player, region, "undos");
		region.drain();

		player.sendMessage(good + "All the liquid in the selected Region between "
				+ highlight + region.getLowerX() + " " + region.getLowerY() + " "
				+ region.getLowerZ() + good + " and " + highlight
				+ region.getUpperX() + " " + region.getUpperY() + " "
				+ region.getUpperZ() + good + " has been drained.");

		return true;
	}

}
