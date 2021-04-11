package me.Tiernanator.Builder.Materials;

import me.Tiernanator.Builder.BuilderMain;
import me.Tiernanator.Utilities.Colours.Colour;
import me.Tiernanator.Utilities.File.ConfigAccessor;
import me.Tiernanator.Utilities.Materials.BuildingMaterial;
import me.Tiernanator.Utilities.Menu.Menu;
import me.Tiernanator.Utilities.Menu.MenuAction;
import me.Tiernanator.Utilities.Menu.MenuEntry;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

public class ListMaterials implements CommandExecutor {

	private static ChatColor warning = Colour.WARNING.getColour();

	private static BuilderMain plugin;
	// sets the value of the plugin to the main class
	public ListMaterials(BuilderMain main) {
		plugin = main;
	}

	// This Command sends a player a list of all their active Permissions

	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {

		if (!(sender instanceof Player)) {
			sender.sendMessage(warning + "Only players can use this command.");
			return false;
		}
		Player player = (Player) sender;

		List<MenuEntry> menuEntries = new ArrayList<MenuEntry>();

		for (BuildingMaterial buildingMaterial : BuildingMaterial
				.allBuildingMaterials()) {

			String materialName = ChatColor.BLUE + "�L"
					+ buildingMaterial.getName();

			ItemStack materialItem = getValidItemForDisplay(buildingMaterial);
			
			MenuEntry menuEntry = new MenuEntry(materialName, materialItem,
					MenuAction.NOTHING, null);
			menuEntries.add(menuEntry);
			
		}

		Menu menu = new Menu("Building Materials", menuEntries, 54, true);
		menu.makeMenu(player);

		return true;
	}

	private static HashMap<BuildingMaterial, ItemStack> allModifiedBuildingMaterials;
	public static HashMap<BuildingMaterial, ItemStack> allModifiedBuildingMaterials() {

		if (allModifiedBuildingMaterials != null) {
			return allModifiedBuildingMaterials;
		}

		allModifiedBuildingMaterials = new HashMap<BuildingMaterial, ItemStack>();

		ConfigAccessor configAccessor = new ConfigAccessor(plugin,
				"displayMaterials.yml");

		for (BuildingMaterial buildingMaterial : BuildingMaterial
				.allBuildingMaterials()) {

			int damage = buildingMaterial.getDamage();
			Material material = buildingMaterial.getMaterial();		
			
			String materialName = buildingMaterial.getName();
			String ammendmentString = configAccessor.getConfig()
					.getString(materialName);
			
			if(ammendmentString != null) {
				
				String[] parts = ammendmentString.split(":");
				if (parts.length > 1) {
					materialName = parts[0];
					try {
						damage = Integer.parseInt(parts[1]);
					} catch (Exception e) {
						plugin.getLogger().log(Level.WARNING,
								Colour.WARNING.getColourCode()
										+ "Could not read damage data '" + parts[1]
										+ "' from file for Material "
										+ materialName);
					}

				} else {
					materialName = ammendmentString;
				}

				materialName = materialName.toUpperCase();
				//TODO Figure out what ZoneName did (it was in Utilities but got removed...)
//				materialName = ZoneName.parseNameToZoneCode(materialName);

				material = Material.getMaterial(materialName);
				
			}
			
			ItemStack item = new ItemStack(material, 1, (short) damage);

			allModifiedBuildingMaterials.put(buildingMaterial, item);
		}

		return allModifiedBuildingMaterials;

	}

	public static ItemStack getValidItemForDisplay(BuildingMaterial material) {

		ItemStack item = allModifiedBuildingMaterials().get(material);
		return item;
//		List<ItemStack> allModifiedBuildingMaterials = allModifiedBuildingMaterials();
//		for (BuildingMaterial buildingMaterial : BuildingMaterialallModifiedBuildingMaterials) {
//			String name = buildingMaterial.getName();
//			if (name.equalsIgnoreCase(materialName)) {
//				return buildingMaterial;
//			}
//		}
//		return null;

	}

}
