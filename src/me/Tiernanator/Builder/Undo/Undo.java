package me.Tiernanator.Builder.Undo;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Tiernanator.Builder.Main;
import me.Tiernanator.Colours.Colour;

public class Undo implements CommandExecutor {

	@SuppressWarnings("unused")
	private static Main plugin;

	private ChatColor warning = Colour.WARNING.getColour();
//	private ChatColor informative = Colour.INFORMATIVE.getColour();
//	private ChatColor highlight = Colour.HIGHLIGHT.getColour();
//	private ChatColor good = Colour.GOOD.getColour();

	public Undo(Main main) {
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
		
//		UndoConfig.undo(player, "undos");
//		player.sendMessage(good + "Your previous edit has been undone.");
		player.sendMessage(warning + "WIP");
		
		return true;
	}

}
