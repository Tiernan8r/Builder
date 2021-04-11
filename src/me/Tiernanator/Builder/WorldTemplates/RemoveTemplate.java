package me.Tiernanator.Builder.WorldTemplates;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Tiernanator.Builder.BuilderMain;
import me.Tiernanator.Utilities.Colours.Colour;
import me.Tiernanator.Utilities.Locations.Region.Region;
import me.Tiernanator.Utilities.Locations.Zones.ZoneName;

public class RemoveTemplate implements CommandExecutor {

	@SuppressWarnings("unused")
	private static BuilderMain plugin;

	private ChatColor warning = Colour.WARNING.getColour();
	private ChatColor informative = Colour.INFORMATIVE.getColour();
	private ChatColor highlight = Colour.HIGHLIGHT.getColour();
	private ChatColor good = Colour.GOOD.getColour();
	private ChatColor bad = Colour.BAD.getColour();
	private ChatColor regal = Colour.REGAL.getColour();
	
	public RemoveTemplate(BuilderMain main) {
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
		
		if(!(player.hasPermission("build.removeTemplate"))) {
			player.sendMessage(warning + "You do not have permission to use this command.");
			return true;
		}
		
		List<String> allTemplates = TemplateConfig.allTemplateNames();
		if(allTemplates == null || allTemplates.isEmpty()) {
			player.sendMessage(bad + "There are no saved templates...");
			return false;
		}
		
		if(args.length < 1) {
			player.sendMessage(warning + "Please specify the name of the template.");
			return false;
		}
		
		String name = "";
		
		for(int i = 0; i < args.length; i++) {
			name += args[i];
			if(!(i == args.length - 1)) {
				name += "_";
			}
		}
		if(!TemplateConfig.isTemplate(name)) {
			player.sendMessage(bad + "That template does not exist!");
			player.sendMessage(regal + "The Current templates are:");
			for(String i : allTemplates) {
				player.sendMessage(informative + "- " + i);
			}
			return false;
		}
		
//		Region region = new Region(regionConfig.getRegion(name));
		Region region = TemplateConfig.getRegion(name);
		
		if(region.allBlocks() == null) {
			player.sendMessage(bad + "That template does not exist!");
			player.sendMessage(regal + "The Current templates are:");
			for(String i : allTemplates) {
				player.sendMessage(informative + "- " + i);
			}
			return false;
		}
		
		TemplateConfig.removeTemplate(name);
		TemplateConfig.deleteConfig(name);
		
		String parsedName = ZoneName.parseZoneCodeToName(name);
		
		player.sendMessage(good + "The template " + highlight + parsedName + good + " has been removed.");
		
		return true;
	}

}
