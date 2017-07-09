package me.Tiernanator.Builder.Events;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Tiernanator.Builder.BuilderMain;
import me.Tiernanator.Colours.Colour;
import me.Tiernanator.Utilities.Locations.RelativeLocation;
import me.Tiernanator.Utilities.Players.PlayerTime;
import me.Tiernanator.Utilities.Players.SelectAction;

public class Set implements CommandExecutor {

	private static BuilderMain plugin;

	private ChatColor warning = Colour.WARNING.getColour();

	public Set(BuilderMain main) {
		plugin = main;
	}
	
	private int actionNumber = 0;
	private SelectAction getAction() {
		
		boolean leftClick = true;
		if(actionNumber % 2 == 0) {
			leftClick = false;
		}
		SelectAction action = null;
		
		if(!leftClick) {
			action = SelectAction.RIGHT_CLICK;
		} else {
			action = SelectAction.LEFT_CLICK;
		}
		actionNumber++;
		
		return action;
		
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		
		if (!(sender instanceof Player)) {
			sender.sendMessage(warning + "You can't use this command.");
			return true;
		}
		
		Player player = (Player) sender;
		
//		if(!(player.hasPermission("build.set"))) {
//			player.sendMessage(warning + "You do not have permission to use this command.");
//			return true;
//		}
		if(args.length < 3) {
			player.sendMessage(warning + "You must specify the block coordinates to select.");
			return false;
		}
		
		Location location = RelativeLocation.getRelativeLocationsFromString(player, args[0], args[1], args[2]);
		if(location == null) {
			player.sendMessage(warning + "Those are not valid co-ordinates.");
			return false;
		}
		
		SelectAction action = getAction();
		
		WandSelectEvent event = new WandSelectEvent(location, player, action);
		
		//get the time intervals between clicks, and set the previous time to now
		long currentPlayerTime =  System.currentTimeMillis(); 
				
		long previousTime = 0;
		PlayerTime playerTime = new PlayerTime(player);
		if(playerTime.hasPlayerTime()) {
			previousTime = playerTime.getPreviousPlayerTime();
		}
//		playerTime.addPlayerTime(currentPlayerTime);
		//determine the difference, if it is less than 4, don't call the event
		long timeDifference = currentPlayerTime - previousTime;
		
		if((timeDifference / 4.0) < 0.25) {
			return true;
		}
		plugin.getServer().getPluginManager().callEvent(event);
		
		playerTime.addPlayerTime(currentPlayerTime);
		
		return true;
	}

}
