package me.Tiernanator.Builder.WorldTemplates;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;

import me.Tiernanator.Builder.Main;
import me.Tiernanator.File.ConfigAccessor;
import me.Tiernanator.Utilities.Locations.Region.Region;

public class TemplateConfig {

	private static Main plugin;

	public static void setPlugin(Main main) {
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
		for(int i = 1; i <= total; i++) {

			int x = regionAccessor.getConfig().getInt("Block." + i + ".x");
			int y = regionAccessor.getConfig().getInt("Block." + i + ".y");
			int z = regionAccessor.getConfig().getInt("Block." + i + ".z");
			String worldName = regionAccessor.getConfig().getString("Block." + i + ".world");
			World world = plugin.getServer().getWorld(worldName);

			Location location = new Location(world, x, y, z);
			Block block = location.getBlock();
			blocks.add(block);

		}
		return new Region(blocks);
	}
	
	public static void deleteConfig(String name) {
		
	    ConfigAccessor regionAccessor = new ConfigAccessor(plugin, name + ".yml", "templates");
	    for(String key : regionAccessor.getConfig().getKeys(false)) {
	    	regionAccessor.getConfig().set(key, null);
	    }
	    regionAccessor.saveConfig(); 
	    
	    File configFile = new File(plugin.getDataFolder().toString() + File.separator + "templates", name + ".yml");
	    configFile.delete();
	    
	    removeTemplate(name);
		
	}
	
	public static List<String> allTemplateNames() {
		
		List<String> allTemplates = new ArrayList<String>();
		
//		BukkitRunnable runnable = new BukkitRunnable() {
//			
//			@Override
//			public void run() {
				String query = "SELECT Name FROM Templates;";

				Connection connection = Main.getSQL().getConnection();
				Statement statement = null;
				try {
					statement = connection.createStatement();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				ResultSet resultSet = null;
				try {
					resultSet = statement.executeQuery(query);
				} catch (SQLException e) {
					e.printStackTrace();
				}

				try {
					if (!resultSet.isBeforeFirst()) {
						return null;
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}

				List<String> templates = new ArrayList<String>();
				try {
					while(resultSet.next()) {
						templates.add(resultSet.getString(1));
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}

				allTemplates.addAll(templates);
//				setAllTemplateNames(allTemplates);
//			}
//		};
//		runnable.runTaskAsynchronously(plugin);
		return allTemplates;
		
//		String query = "SELECT Name FROM Templates;";
//
//		Connection connection = Main.getSQL().getConnection();
//		Statement statement = null;
//		try {
//			statement = connection.createStatement();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		ResultSet resultSet = null;
//		try {
//			resultSet = statement.executeQuery(query);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//
//		try {
//			if (!resultSet.isBeforeFirst()) {
//				return null;
//			}
//		} catch (SQLException e1) {
//			e1.printStackTrace();
//		}
//
//		List<String> allTemplates = new ArrayList<String>();
//		try {
//			while(resultSet.next()) {
//				allTemplates.add(resultSet.getString(1));
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//
//		return allTemplates;
	}
	
	public static boolean isTemplate(String name) {

		List<String> allTemplates = new ArrayList<String>();
		allTemplates = allTemplateNames();
		
		if(allTemplates == null) {
			return false;
		}
		
		if(allTemplates.isEmpty() || allTemplates.equals(null) || allTemplates.size() <= 0) {
			return false;
		}
		if(allTemplates.contains(name)) {
			return true;
		} else {
			return false;
		}
	}

	public static void addTemplate(String name) {

		BukkitRunnable runnable = new BukkitRunnable() {
			
			@Override
			public void run() {
				Connection connection = Main.getSQL().getConnection();
				PreparedStatement preparedStatement = null;
				try {
					preparedStatement = connection.prepareStatement(
							"INSERT INTO Templates (Name) VALUES (?);");
				} catch (SQLException e) {
					e.printStackTrace();
				}
				try {
					preparedStatement.setString(1, name);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				try {
					preparedStatement.executeUpdate();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				try {
					preparedStatement.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		runnable.runTask(plugin);
		
//		Connection connection = Main.getSQL().getConnection();
//		PreparedStatement preparedStatement = null;
//		try {
//			preparedStatement = connection.prepareStatement(
//					"INSERT INTO Templates (Name) VALUES (?);");
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		try {
//			preparedStatement.setString(1, name);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		try {
//			preparedStatement.executeUpdate();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		try {
//			preparedStatement.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

	}
	
	public static void removeTemplate(String name) {

		BukkitRunnable runnable = new BukkitRunnable() {
			
			@Override
			public void run() {
				String query = "DELETE FROM Templates WHERE Name = '" + name + "';";

				Connection connection = Main.getSQL().getConnection();
				Statement statement = null;
				try {
					statement = connection.createStatement();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				try {
					statement.execute(query);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		};
		runnable.runTaskAsynchronously(plugin);
		
//		String query = "DELETE FROM Templates WHERE Name = '" + name + "';";
//
//		Connection connection = Main.getSQL().getConnection();
//		Statement statement = null;
//		try {
//			statement = connection.createStatement();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		try {
//			statement.execute(query);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}

	}
	
	public static String getTemplateName(int id) {

		List<String> allTemplateNames = allTemplateNames();
		if(allTemplateNames.size() > id + 1) {
			return allTemplateNames.get(id);
		}
		return null;
//		String query = "SELECT Name FROM Templates WHERE ID = '"
//				+ id + "';";
//
//		Connection connection = Main.getSQLConnection();
//		Statement statement = null;
//		try {
//			statement = connection.createStatement();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		ResultSet resultSet = null;
//		try {
//			resultSet = statement.executeQuery(query);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//
//		try {
//			if (!resultSet.isBeforeFirst()) {
//				return null;
//			}
//		} catch (SQLException e1) {
//			e1.printStackTrace();
//		}
//		try {
//			resultSet.next();
//		} catch (SQLException e1) {
//			e1.printStackTrace();
//		}
//
//		String destinationName = "";
//		try {
//			destinationName = resultSet.getString("Name");
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return destinationName;
	}

}
