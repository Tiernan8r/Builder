package me.Tiernanator.Builder.WorldTemplates;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Tiernanator.Builder.BuilderMain;
import me.Tiernanator.Utilities.Colours.Colour;

public class ListTemplates implements CommandExecutor {

	@SuppressWarnings("unused")
	private static BuilderMain plugin;

	private ChatColor warning = Colour.WARNING.getColour();
	private ChatColor informative = Colour.INFORMATIVE.getColour();
	private ChatColor good = Colour.GOOD.getColour();
	private ChatColor bad = Colour.BAD.getColour();
	
	public ListTemplates(BuilderMain main) {
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
		
		List<String> allTemplates = TemplateConfig.allTemplateNames();
		if(allTemplates == null || allTemplates.isEmpty()) {
			player.sendMessage(bad + "There are no saved templates...");
			return false;
		}
		player.sendMessage(good + "The Current templates are:");
		for(String i : allTemplates) {
			player.sendMessage(informative + "- " + i);
		}
		
		return true;
	}

}
