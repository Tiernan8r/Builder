package me.Tiernanator.Builder.Commands.Edits;

import org.bukkit.ChatColor;
import org.bukkit.block.Biome;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Tiernanator.Builder.BuilderMain;
import me.Tiernanator.Builder.Events.WandSelect;
import me.Tiernanator.Builder.Undo.UndoConfig;
import me.Tiernanator.Colours.Colour;
import me.Tiernanator.Utilities.Locations.Region.Region;

public class SetBiome implements CommandExecutor {

	@SuppressWarnings("unused")
	private static BuilderMain plugin;

	private ChatColor warning = Colour.WARNING.getColour();
	private ChatColor informative = Colour.INFORMATIVE.getColour();
	private ChatColor highlight = Colour.HIGHLIGHT.getColour();
	private ChatColor good = Colour.GOOD.getColour();
	private ChatColor bad = Colour.BAD.getColour();

	public SetBiome(BuilderMain main) {
		plugin = main;
	}

	@Deprecated
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
		
		if(args.length < 1) {
			player.sendMessage(warning + "You must specify a biome.");
			return false;
		}
		String biomeName = args[0];
		
		Biome biome = getBiome(biomeName);
		
		if(biome == null) {
			player.sendMessage(highlight + biomeName + bad + " is not a biome, the biomes are:");
			for(Biome b : Biome.values()) {
				player.sendMessage(informative + " - " + b.name());
			}
			return false;
		}
		
		UndoConfig.saveRegion(player, region, "undos");
		region.setBiome(biome);
		
		player.sendMessage(good + "The selected Region's biome has been set to the Biome: " + highlight + biome.name());
		
		return true;
	}

	private Biome getBiome(String biomeName) {
		
		biomeName = biomeName.toUpperCase();
		
		switch(biomeName) {
			case "BEACHES" :
				return Biome.BEACHES;
			case "BIRCH_FOREST" :
				return Biome.BIRCH_FOREST;
			case "BIRCH_FOREST_HILLS" :
				return Biome.BIRCH_FOREST_HILLS;
			case "COLD_BEACH" :
				return Biome.COLD_BEACH;
			case "DEEP_OCEAN" :
				return Biome.DEEP_OCEAN;
			case "DESERT" :
				return Biome.DESERT;
			case "DESERT_HILLS" :
				return Biome.DESERT_HILLS;
			case "EXTREME_HILLS" :
				return Biome.EXTREME_HILLS;
			case "EXTREME_HILLS_WITH_TREES" :
				return Biome.EXTREME_HILLS_WITH_TREES;
			case "FOREST" :
				return Biome.FOREST;
			case "FOREST_HILLS" :
				return Biome.FOREST_HILLS;
			case "FROZEN_OCEAN" :
				return Biome.FROZEN_OCEAN;
			case "FROZEN_RIVER" :
				return Biome.FROZEN_RIVER;
			case "HELL" :
				return Biome.HELL;
			case "ICE_FLATS" :
				return Biome.ICE_FLATS;
			case "ICE_MOUNTAINS" :
				return Biome.ICE_MOUNTAINS;
			case "JUNGLE" :
				return Biome.JUNGLE;
			case "JUNGLE_EDGE" :
				return Biome.JUNGLE_EDGE;
			case "JUNGLE_HILLS" :
				return Biome.JUNGLE_HILLS;
			case "MESA" :
				return Biome.MESA;
			case "MESA_CLEAR_ROCK" :
				return Biome.MESA_CLEAR_ROCK;
			case "MESA_ROCK" :
				return Biome.MESA_ROCK;
			case "MUSHROOM_ISLAND" :
				return Biome.MUSHROOM_ISLAND;
			case "MUSHROOM_ISLAND_SHORE" :
				return Biome.MUSHROOM_ISLAND_SHORE;
			case "MUTATED_BIRCH_FOREST" :
				return Biome.MUTATED_BIRCH_FOREST;
			case "MUTATED_BIRCH_FOREST_HILLS" :
				return Biome.MUTATED_BIRCH_FOREST_HILLS;
			case "MUTATED_DESERT" :
				return Biome.MUTATED_DESERT;
			case "MUTATED_EXTREME_HILLS" :
				return Biome.MUTATED_EXTREME_HILLS;
			case "MUTATED_EXTREME_HILLS_WITH_TREES" :
				return Biome.MUTATED_EXTREME_HILLS_WITH_TREES;
			case "MUTATED_FOREST" :
				return Biome.MUTATED_FOREST;
			case "MUTATED_ICE_FLATS" :
				return Biome.MUTATED_ICE_FLATS;
			case "MUTATED_JUNGLE" :
				return Biome.MUTATED_JUNGLE;
			case "MUTATED_JUNGLE_EDGE" :
				return Biome.MUTATED_JUNGLE_EDGE;
			case "MUTATED_MESA" :
				return Biome.MUTATED_MESA;
			case "MUTATED_MESA_CLEAR_ROCK" :
				return Biome.MUTATED_MESA_CLEAR_ROCK;
			case "MUTATED_MESA_ROCK" :
				return Biome.MUTATED_MESA_ROCK;
			case "MUTATED_PLAINS" :
				return Biome.MUTATED_PLAINS;
			case "MUTATED_REDWOOD_TAIGA" :
				return Biome.MUTATED_REDWOOD_TAIGA;
			case "MUTATED_REDWOOD_TAIGA_HILLS" :
				return Biome.MUTATED_REDWOOD_TAIGA_HILLS;
			case "MUTATED_ROOFED_FOREST" :
				return Biome.MUTATED_ROOFED_FOREST;
			case "MUTATED_SAVANNA" :
				return Biome.MUTATED_SAVANNA;
			case "MUTATED_SAVANNA_ROCK" :
				return Biome.MUTATED_SAVANNA_ROCK;
			case "MUTATED_SWAMPLAND" :
				return Biome.MUTATED_SWAMPLAND;
			case "MUTATED_TAIGA" :
				return Biome.MUTATED_TAIGA;
			case "MUTATED_TAIGA_COLD" :
				return Biome.MUTATED_TAIGA_COLD;
			case "OCEAN" :
				return Biome.OCEAN;
			case "PLAINS" :
				return Biome.PLAINS;
			case "REDWOOD_TAIGA" :
				return Biome.REDWOOD_TAIGA;
			case "REDWOOD_TAIGA_HILLS" :
				return Biome.REDWOOD_TAIGA_HILLS;
			case "RIVER" :
				return Biome.RIVER;
			case "ROOFED_FOREST" :
				return Biome.ROOFED_FOREST;
			case "SAVANNA" :
				return Biome.SAVANNA;
			case "SAVANNA_ROCK" :
				return Biome.SAVANNA_ROCK;
			case "SKY" :
				return Biome.SKY;
			case "SMALLER_EXTREME_HILLS" :
				return Biome.SMALLER_EXTREME_HILLS;
			case "STONE_BEACH" :
				return Biome.STONE_BEACH;
			case "SWAMPLAND" :
				return Biome.SWAMPLAND;
			case "TAIGA" :
				return Biome.TAIGA;
			case "TAIGA_COLD" :
				return Biome.TAIGA_COLD;
			case "TAIGA_COLD_HILLS" :
				return Biome.TAIGA_COLD_HILLS;
			case "TAIGA_HILLS" :
				return Biome.TAIGA_HILLS;
			case "VOID" :
				return Biome.VOID;
			default :
				return null;
			
		}
		
	}
}
