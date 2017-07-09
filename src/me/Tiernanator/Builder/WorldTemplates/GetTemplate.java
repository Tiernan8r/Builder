package me.Tiernanator.Builder.WorldTemplates;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Tiernanator.Builder.BuilderMain;
import me.Tiernanator.Colours.Colour;
import me.Tiernanator.Utilities.Locations.Region.Region;
import me.Tiernanator.Utilities.Locations.Zones.ZoneName;
import me.Tiernanator.Utilities.MetaData.MetaData;

public class GetTemplate implements CommandExecutor {

	private static BuilderMain plugin;

	private ChatColor warning = Colour.WARNING.getColour();
	private ChatColor informative = Colour.INFORMATIVE.getColour();
	private ChatColor highlight = Colour.HIGHLIGHT.getColour();
	private ChatColor good = Colour.GOOD.getColour();
	private ChatColor bad = Colour.BAD.getColour();
	private ChatColor regal = Colour.REGAL.getColour();
	
	public GetTemplate(BuilderMain main) {
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
		
		Region region = TemplateConfig.getRegion(name);
		
		if(region.allBlocks() == null) {
			player.sendMessage(bad + "That template does not exist!");
			player.sendMessage(regal + "The Current templates are:");
			for(String i : allTemplates) {
				player.sendMessage(informative + "- " + i);
			}
			return false;
		}
		
//		UndoConfig.saveRegion(name, region.allBlocks());
		MetaData.setMetadata(player, "TemplateRegion", name, plugin);
		
		String parsedName = ZoneName.parseZoneCodeToName(name);
		
		player.sendMessage(good + "The template " + highlight + parsedName + good + " has been retrieved and can now be used.");
		
		return true;
	}

}
