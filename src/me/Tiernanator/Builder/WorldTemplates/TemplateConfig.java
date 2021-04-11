package me.Tiernanator.Builder.WorldTemplates;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

import me.Tiernanator.Builder.BuilderMain;
import me.Tiernanator.Utilities.File.ConfigAccessor;
import me.Tiernanator.Utilities.Locations.Region.Region;
import me.Tiernanator.Utilities.SQL.SQLServer;

public class TemplateConfig {

	private static BuilderMain plugin;

	public static void setPlugin(BuilderMain main) {
		plugin = main;
	}

	public static void saveRegion(String name, List<Block> blocks) {

		ConfigAccessor regionAccessor = new ConfigAccessor(plugin,
				name + ".yml", "templates");
		int i = 1;
		for (Block block : blocks) {

			Location location = block.getLocation();
			int x = location.getBlockX();
			int y = location.getBlockY();
			int z = location.getBlockZ();
			World world = location.getWorld();
			String worldName = world.getName();

			regionAccessor.getConfig().set("Block." + i + ".x", x);
			regionAccessor.getConfig().set("Block." + i + ".y", y);
			regionAccessor.getConfig().set("Block." + i + ".z", z);
			regionAccessor.getConfig().set("Block." + i + ".world", worldName);

			i++;

		}
		regionAccessor.getConfig().set("Block.Amount", i - 1);
		regionAccessor.saveConfig();

		addTemplate(name);

	}

	public static void saveRegion(String name, Region region) {
		saveRegion(name, region.allBlocks());
	}

	public static Region getRegion(String name) {

		List<Block> blocks = new ArrayList<Block>();

		ConfigAccessor regionAccessor = new ConfigAccessor(plugin,
				name + ".yml", "templates");

		int total = regionAccessor.getConfig().getInt("Block.Amount");
		for (int i = 1; i <= total; i++) {

			int x = regionAccessor.getConfig().getInt("Block." + i + ".x");
			int y = regionAccessor.getConfig().getInt("Block." + i + ".y");
			int z = regionAccessor.getConfig().getInt("Block." + i + ".z");
			String worldName = regionAccessor.getConfig()
					.getString("Block." + i + ".world");
			World world = plugin.getServer().getWorld(worldName);

			Location location = new Location(world, x, y, z);
			Block block = location.getBlock();
			blocks.add(block);

		}
		return new Region(blocks);
	}

	public static void deleteConfig(String name) {

		ConfigAccessor regionAccessor = new ConfigAccessor(plugin,
				name + ".yml", "templates");
		for (String key : regionAccessor.getConfig().getKeys(false)) {
			regionAccessor.getConfig().set(key, null);
		}
		regionAccessor.saveConfig();

		File configFile = new File(plugin.getDataFolder().toString()
				+ File.separator + "templates", name + ".yml");
		configFile.delete();

		removeTemplate(name);

	}

	public static List<String> allTemplateNames() {

		String query = "SELECT Name FROM Templates;";
		List<Object> list = SQLServer.getList(query, "Name");
		List<String> allTemplates = new ArrayList<String>();

		for (Object entry : list) {
			allTemplates.add((String) entry);
		}

		return allTemplates;
	}

	public static boolean isTemplate(String name) {

		List<String> allTemplates = allTemplateNames();

		if (allTemplates == null) {
			return false;
		}

		if (allTemplates.isEmpty() || allTemplates.equals(null)
				|| allTemplates.size() <= 0) {
			return false;
		}
		return allTemplates.contains(name);
	}

	public static void addTemplate(String name) {

		String statement = "INSERT INTO Templates (Name) VALUES (?);";
		Object[] values = new Object[]{name};
		SQLServer.executePreparedStatement(statement, values);

	}

	public static void removeTemplate(String name) {

		String query = "DELETE FROM Templates WHERE Name = '" + name + "';";
		SQLServer.executeQuery(query);

	}

	public static String getTemplateName(int id) {

		List<String> allTemplateNames = allTemplateNames();
		if (allTemplateNames.size() > id + 1) {
			return allTemplateNames.get(id);
		}
		return null;
	}

}
