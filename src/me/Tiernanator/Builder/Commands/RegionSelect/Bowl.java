package me.Tiernanator.Builder.Commands.RegionSelect;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Tiernanator.Builder.BuilderMain;
import me.Tiernanator.Builder.Events.WandSelect;
import me.Tiernanator.Utilities.Colours.Colour;
import me.Tiernanator.Utilities.Locations.Region.Region;
import me.Tiernanator.Utilities.Locations.Region.Shapes;

public class Bowl implements CommandExecutor {

	@SuppressWarnings("unused")
	private static BuilderMain plugin;

	private ChatColor warning = Colour.WARNING.getColour();
	private ChatColor informative = Colour.INFORMATIVE.getColour();
	private ChatColor highlight = Colour.HIGHLIGHT.getColour();
	private ChatColor good = Colour.GOOD.getColour();
	private ChatColor bad = Colour.BAD.getColour();

	public Bowl(BuilderMain main) {
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
		
//		if(!(player.hasPermission("build.bowl"))) {
//			player.sendMessage(warning + "You do not have permission to use this command.");
//			return true;
//		}
		List<Location> cornerLocations = WandSelect.getSelectedLocations(player); 
		
		if(cornerLocations == null) {
			player.sendMessage(warning + "You must select a centre with the building wand first.");
			return false;
		}
		if(args.length < 1) {
			player.sendMessage(warning + "You must specify a radius (and optionally: whether it's hollow) for the bowl.");
			return false;
		}
		Location centre = null;
		if(cornerLocations.size() > 1) {
			centre = cornerLocations.get(1);
		} else {
			centre = cornerLocations.get(0);
		}

		int radius = 0;
		try {
			radius = Integer.parseInt(args[0]);
		} catch (Exception e) {
			player.sendMessage(warning + "That is not a valid radius.");
			return false;
		}
		if(radius > 100) {
			player.sendMessage(bad + "Be reasonable with those radii!");
			return false;
		}
		
		
		boolean hollow = false;
		if(args.length > 1) {
			if(args[1].equalsIgnoreCase("True") || args[1].equalsIgnoreCase("False")) {
				hollow = Boolean.parseBoolean(args[1]);				
			} else {
				player.sendMessage(warning + "Hollow can only be: True or False.");
				return false;
			}
		}
		
		Region bowl = new Region(Shapes.generateBowl(centre, radius, hollow));
		String hollowString = "";
		if(hollow) {
			hollowString = "hollow ";
		}
		
		WandSelect.setRegion(player, bowl);
		
		player.sendMessage(good + "A " + informative + hollowString + good + "bowl has been generated at " + highlight + centre.getX() + " " + centre.getY() + " " + centre.getZ() + good + " with radius " + highlight + Integer.toString(radius) + good + ".");
		
		return true;
	}

}
