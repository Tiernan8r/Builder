package me.Tiernanator.Builder.WorldTemplates;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Tiernanator.Builder.BuilderMain;
import me.Tiernanator.Builder.Events.WandSelect;
import me.Tiernanator.Utilities.Colours.Colour;
import me.Tiernanator.Utilities.Locations.Region.Region;
import me.Tiernanator.Utilities.Locations.Zones.ZoneName;
import me.Tiernanator.Utilities.MetaData.MetaData;

public class Save implements CommandExecutor {

	private static BuilderMain plugin;

	private ChatColor warning = Colour.WARNING.getColour();
	private ChatColor informative = Colour.INFORMATIVE.getColour();
	private ChatColor highlight = Colour.HIGHLIGHT.getColour();
	private ChatColor good = Colour.GOOD.getColour();
	
	public Save(BuilderMain main) {
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
		
//		if(!(player.hasPermission("build.save"))) {
//			player.sendMessage(warning + "You do not have permission to use this command.");
//			return true;
//		}
		Region region = WandSelect.getRegion(player);
		
		if(region == null) {
			player.sendMessage(warning + "You must select a region with the building wand first.");
			return false;
		}
		if(args.length < 1) {
			player.sendMessage(warning + "Please specify the name of the zone.");
			return false;
		}
		
		String name = "";
		
		for(int i = 0; i < args.length; i++) {
			name += args[i];
			if(!(i == args.length - 1)) {
				name += "_";
			}
		}
		
		if(TemplateConfig.isTemplate(name)) {
			player.sendMessage(warning + "A region by the name " + highlight + name + warning + " already exists.");
			return false;
		}

		TemplateConfig.saveRegion(name, region.allBlocks());
		MetaData.setMetadata(player, "TemplateRegion", name, plugin);
		
		String parsedName = ZoneName.parseZoneCodeToName(name);
		
		player.sendMessage(good + "This Region has been saved as " + informative + parsedName + good + ".");
		
		return true;
	}

}
