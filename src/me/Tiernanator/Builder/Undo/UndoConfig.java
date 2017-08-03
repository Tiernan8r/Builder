package me.Tiernanator.Builder.Undo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import me.Tiernanator.Builder.BuilderMain;
import me.Tiernanator.Utilities.File.ConfigAccessor;
import me.Tiernanator.Utilities.Locations.Region.Region;
import me.Tiernanator.Utilities.Locations.Region.Cuboids.Cuboid;

public class UndoConfig {

	private static BuilderMain plugin;

	public static void setPlugin(BuilderMain main) {
		plugin = main;
	}
	
//	@SuppressWarnings("deprecation")
	public static void saveRegion(Player player, List<Block> blocks, String folderName) {

//		String playerUUID = player.getUniqueId().toString();
//		
//		ConfigAccessor regionAccessor = new ConfigAccessor(plugin,
//				playerUUID + ".yml", folderName);
//		
//		int totalUndos = regionAccessor.getConfig().getInt("Total");
//		totalUndos++;
//		
//		int i = 1;
//		for (Block block : blocks) {
//
//			Location location = block.getLocation();
//			int x = location.getBlockX();
//			int y = location.getBlockY();
//			int z = location.getBlockZ();
//			World world = location.getWorld();
//			String worldName = world.getName();
//			
//			Material material = block.getType();
//			int damage = block.getData();
//
//			regionAccessor.getConfig().set("Block." + totalUndos + "." + i + ".x", x);
//			regionAccessor.getConfig().set("Block." + totalUndos + "." + i + ".y", y);
//			regionAccessor.getConfig().set("Block." + totalUndos + "." + i + ".z", z);
//			regionAccessor.getConfig().set("Block." + totalUndos + "." + i + ".world", worldName);
//
//			regionAccessor.getConfig().set("Block." + totalUndos + "." + i + ".material", material.name());
//			regionAccessor.getConfig().set("Block." + totalUndos + "." + i + ".damage", damage);
//			
//			i++;
//
//		}
//		regionAccessor.getConfig().set("Block." + totalUndos + ".Amount", i - 1);
//		regionAccessor.getConfig().set("Total", totalUndos);
//		
//		regionAccessor.saveConfig();
		
	}

	public static void saveRegion(Player player, Region region, String folderName) {
		saveRegion(player, region.allBlocks(), folderName);
	}
	
	public static void saveRegion(Player player, Cuboid cuboid, String folderName) {
		Region region = new Region(cuboid);
		saveRegion(player, region.allBlocks(), folderName);
	}
	
	public static Region getLastEdit(Player player, String folderName) {

		String playerUUID = player.getUniqueId().toString();
		
		List<Block> blocks = new ArrayList<Block>();
		
		ConfigAccessor regionAccessor = new ConfigAccessor(plugin,
				playerUUID + ".yml", folderName);
		
		int totalUndos = regionAccessor.getConfig().getInt("Total");
		
		int total = regionAccessor.getConfig().getInt("Block." + totalUndos + ".Amount");
		for(int i = 1; i <= total; i++) {

			int x = regionAccessor.getConfig().getInt("Block." + totalUndos + "." + i + ".x");
			int y = regionAccessor.getConfig().getInt("Block." + totalUndos + "." + i + ".y");
			int z = regionAccessor.getConfig().getInt("Block." + totalUndos + "." + i + ".z");
			String worldName = regionAccessor.getConfig().getString("Block." + totalUndos + "." + i + ".world");
			World world = plugin.getServer().getWorld(worldName);

//			String materialName = regionAccessor.getConfig().getString("Block." + i + ".material");
//			Material material = Material.valueOf(materialName);
//			int damage = regionAccessor.getConfig().getInt("Block." + i + ".damage");
			
			Location location = new Location(world, x, y, z);
			Block block = location.getBlock();
			blocks.add(block);

		}
		return new Region(blocks);
	}
	
	@SuppressWarnings("deprecation")
	public static void undo(Player player, String folderName) {

		String playerUUID = player.getUniqueId().toString();
		
		ConfigAccessor regionAccessor = new ConfigAccessor(plugin,
				playerUUID + ".yml", folderName);
		
		int totalUndos = regionAccessor.getConfig().getInt("Total");
		
		int total = regionAccessor.getConfig().getInt("Block." + totalUndos + ".Amount");
		for(int i = 1; i <= total; i++) {

			int x = regionAccessor.getConfig().getInt("Block." + totalUndos + "." + i + ".x");
			int y = regionAccessor.getConfig().getInt("Block." + totalUndos + "." + i + ".y");
			int z = regionAccessor.getConfig().getInt("Block." + totalUndos + "." + i + ".z");
			String worldName = regionAccessor.getConfig().getString("Block." + totalUndos + "." + i + ".world");
			World world = plugin.getServer().getWorld(worldName);

			String materialName = regionAccessor.getConfig().getString("Block." + totalUndos + "." + i + ".material");
			Material material = Material.valueOf(materialName);
			byte damage = (byte) regionAccessor.getConfig().getInt("Block." + totalUndos + "." + i + ".damage");
			
			Location location = new Location(world, x, y, z);
			Block block = location.getBlock();
			
			block.setType(material);
			block.setData(damage);
			
			removeEdit(player, folderName, totalUndos);
			
		}
	}
	
	public static void removeEdit(Player player, String folderName, int index) {
		
		String playerUUID = player.getUniqueId().toString();
		
		ConfigAccessor regionAccessor = new ConfigAccessor(plugin,
				playerUUID + ".yml", folderName);
		
		int totalUndos = regionAccessor.getConfig().getInt("Total");
		
		int amountOfBlocks = regionAccessor.getConfig().getInt("Block." + totalUndos + ".Amount") + 1;
		
		for(int i = 1; i < amountOfBlocks; i++) {
		
			regionAccessor.getConfig().set("Block." + totalUndos + "." + i + ".x", null);
			regionAccessor.getConfig().set("Block." + totalUndos + "." + i + ".y", null);
			regionAccessor.getConfig().set("Block." + totalUndos + "." + i + ".z", null);
			regionAccessor.getConfig().set("Block." + totalUndos + "." + i + ".world", null);

			regionAccessor.getConfig().set("Block." + totalUndos + "." + i + ".material", null);
			regionAccessor.getConfig().set("Block." + totalUndos + "." + i + ".damage", null);
			
			i++;

		}
		regionAccessor.getConfig().set("Block." + totalUndos + ".Amount", null);
		regionAccessor.getConfig().set("Total", totalUndos--);
		
		regionAccessor.saveConfig();
		
	}
	
	public static void deleteConfig(Player player, String folderName) {
		
		String playerUUID = player.getUniqueId().toString();
		
	    ConfigAccessor regionAccessor = new ConfigAccessor(plugin, playerUUID + ".yml", folderName);
	    for(String key : regionAccessor.getConfig().getKeys(false)) {
	    	regionAccessor.getConfig().set(key, null);
	    }
	    regionAccessor.saveConfig(); 
	    
	    File configFile = new File(plugin.getDataFolder().toString() + File.separator + folderName, playerUUID + ".yml");
	    configFile.delete();
	    
	}
	
}
