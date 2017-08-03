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

public class Flip implements CommandExecutor {

	@SuppressWarnings("unused")
	private static BuilderMain plugin;

	private ChatColor warning = Colour.WARNING.getColour();
	private ChatColor good = Colour.GOOD.getColour();
	
	public Flip(BuilderMain main) {
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
		
//		if(!(player.hasPermission("build.flip"))) {
//			player.sendMessage(warning + "You do not have permission to use this command.");
//			return true;
//		}
		Region region = WandSelect.getRegion(player);
		
		if(region == null) {
			player.sendMessage(warning + "You must select a region with the building wand first.");
			return false;
		}
		UndoConfig.saveRegion(player, region, "undos");
		region.flip();
		
		player.sendMessage(good + "The selected Region has been flipped.");
		
		return true;
	}

}
