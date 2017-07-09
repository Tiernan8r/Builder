package me.Tiernanator.Builder.Commands.RegionSelect;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Tiernanator.Builder.BuilderMain;
import me.Tiernanator.Builder.Events.WandSelect;
import me.Tiernanator.Colours.Colour;
import me.Tiernanator.Utilities.Locations.Region.Region;
import me.Tiernanator.Utilities.Locations.Region.Shapes;

public class Circle implements CommandExecutor {

	@SuppressWarnings("unused")
	private static BuilderMain plugin;

	private ChatColor warning = Colour.WARNING.getColour();
	private ChatColor informative = Colour.INFORMATIVE.getColour();
	private ChatColor highlight = Colour.HIGHLIGHT.getColour();
	private ChatColor good = Colour.GOOD.getColour();
	private ChatColor bad = Colour.BAD.getColour();

	public Circle(BuilderMain main) {
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

//		if (!(player.hasPermission("build.circle"))) {
//			player.sendMessage(warning
//					+ "You do not have permission to use this command.");
//			return true;
//		}
		List<Location> cornerLocations = WandSelect
				.getSelectedLocations(player);

		if (cornerLocations == null) {
			player.sendMessage(warning
					+ "You must select a centre with the building wand first.");
			return false;
		}
		if (args.length < 2) {
			player.sendMessage(warning
					+ "You must specify a radius, the axis to ignore: x, y or z (and optionally: whether it's hollow) for the circle.");
			return false;
		}
		Location centre = null;
		if (cornerLocations.size() > 1) {
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
		if (radius > 100) {
			player.sendMessage(bad + "Be reasonable with those radii!");
			return false;
		}

		int axis = 0;
		if (args[1].equalsIgnoreCase("x")) {
			axis = 0;
		} else if (args[1].equalsIgnoreCase("y")) {
			axis = 1;
		} else if (args[1].equalsIgnoreCase("z")) {
			axis = 2;
		} else {
			player.sendMessage(warning + "The axis to ignore can only be: " + highlight
					+ "X" + warning + " ," + highlight + "Y" + warning + " or " + highlight + "Z" + warning + ".");
			return false;
		}

		boolean hollow = false;
		if (args.length > 2) {
			if (args[2].equalsIgnoreCase("True")
					|| args[2].equalsIgnoreCase("False")) {
				hollow = Boolean.parseBoolean(args[2]);
			} else {
				player.sendMessage(
						warning + "Hollow can only be: True or False.");
				return false;
			}
		}

		List<Block> circle = new ArrayList<Block>();
		boolean xAxis = true;
		
		// because ignore axis, selecting x means follow z
		if(axis == 0) {
			xAxis = false;
			circle = Shapes.generateVerticalCircle(centre, radius, hollow, xAxis);
		} else if(axis == 1) {
			circle = Shapes.generateCircle(centre, radius, hollow);
		} else if(axis == 2) {
			xAxis = true;
			circle = Shapes.generateVerticalCircle(centre, radius, hollow, xAxis);
		}

		Region region = new Region(circle);
		WandSelect.setRegion(player, region);
		
		String hollowString = "";
		if (hollow) {
			hollowString = "hollow ";
		}

		player.sendMessage(good + "A " + informative + hollowString + good
				+ "circle has been generated at " + highlight + centre.getX()
				+ " " + centre.getY() + " " + centre.getZ() + good
				+ " with radius " + highlight + Integer.toString(radius) + good
				+ ".");

		return true;
	}

}
