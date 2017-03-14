package me.Tiernanator.Builder.WorldTemplates;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.World;

import me.Tiernanator.Builder.Main;
import me.Tiernanator.File.ConfigAccessor;
import me.Tiernanator.Utilities.Locations.Region.Region;

public class GetTemplateZone {

	public static Main plugin;

	public GetTemplateZone(Main main) {
		plugin = main;
	}
	
	public void setPlugin(Main m) {
		plugin = m;
	}
	
	public static Region getTemplateZone(String name) {

		ConfigAccessor locationsAccessor = new ConfigAccessor(plugin,
				"zoneTemplates.yml");
		
		String corner1Path = "Templates." + name + ".corner1";
		String corner2Path = "Templates." + name + ".corner2";
		String worldPath = "Templates." + name + ".world";

		List<String> allTemplates = locationsAccessor.getConfig().getStringList("All Templates");

		if (!(allTemplates.contains(name))) {
			return null;
		}
		
		String worldName = locationsAccessor.getConfig().getString(worldPath);
		World world = plugin.getServer().getWorld(worldName);
		
		
		List<Double> location1List = locationsAccessor.getConfig().getDoubleList(corner1Path);
		double x1 = location1List.get(0);
		double y1 = location1List.get(1);
		double z1 = location1List.get(2);
		
		List<Double> location2List = locationsAccessor.getConfig().getDoubleList(corner2Path);
		double x2 = location2List.get(0);
		double y2 = location2List.get(1);
		double z2 = location2List.get(2);
		
		Location location1 = new Location(world, x1, y1, z1);
		Location location2 = new Location(world, x2, y2, z2);
		
		Region region = new Region(location1, location2);

		return region;
	}
	
	public static List<String> getAllTemplateNames() {
		ConfigAccessor locationsAccessor = new ConfigAccessor(plugin,
				"zoneTemplates.yml");
		List<String> allTemplates = locationsAccessor.getConfig().getStringList("All Templates");
		return allTemplates;
	}
}
