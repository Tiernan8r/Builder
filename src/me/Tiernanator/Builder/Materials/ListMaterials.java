package me.Tiernanator.Builder.Materials;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.Tiernanator.Builder.BuilderMain;
import me.Tiernanator.Colours.Colour;
import me.Tiernanator.Materials.BuildingMaterial;
import me.Tiernanator.Menu.Menu;
import me.Tiernanator.Menu.MenuAction;
import me.Tiernanator.Menu.MenuEntry;

public class ListMaterials implements CommandExecutor {
	
	private static ChatColor warning = Colour.WARNING.getColour();

	@SuppressWarnings("unused")
	private BuilderMain plugin;
	//sets the value of the plugin to the main class
	public ListMaterials(BuilderMain main) {
		plugin = main;
	}
	
	//This Command sends a player a list of all their active Permissions

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(!(sender instanceof Player)) {
			sender.sendMessage(warning + "Only players can use this command.");
			return false;
		}
		Player player = (Player) sender;
 		
		List<MenuEntry> menuEntries = new ArrayList<MenuEntry>();
		
		for(BuildingMaterial buildingMaterial : BuildingMaterial.values()) {

		
			int damage = buildingMaterial.getDamage();
			Material type = buildingMaterial.getMaterial();

			String materialName = ChatColor.BLUE + "§L" + buildingMaterial.name();
			
			ItemStack materialItem = new ItemStack(type, 1, (short) damage);
			materialItem = getValidItemForDisplay(materialItem);
			
			MenuEntry menuEntry = new MenuEntry(materialName, materialItem, MenuAction.NOTHING, null);
			menuEntries.add(menuEntry);
		}
		
		Menu menu = new Menu("Building Materials", menuEntries, 54, true);
		menu.makeMenu(player);
		
		return true;
	}
	
	private ItemStack getValidItemForDisplay(ItemStack item) {

		Material material = item.getType();
		byte damage = (byte) item.getDurability();
		BuildingMaterial buildingMaterial = BuildingMaterial
				.getBuildingMaterial(material, damage);

		if (buildingMaterial == null) {
			return item;
		}

		
		switch (buildingMaterial) {
			case AIR :
				item = new ItemStack(Material.GLASS_BOTTLE);
				break;
			case ANVIL :
				item = new ItemStack(Material.ANVIL);
				break;
			case BURNING_FURNACE :
				item = new ItemStack(Material.FURNACE);
				break;
			case DAYLIGHT_DETECTOR_INVERTED :
				item = new ItemStack(Material.DAYLIGHT_DETECTOR);
				break;
			case DOUBLE_ACACIA_WOOD_SLAB :
				item = new ItemStack(Material.WOOD, 1, (short) 4);
				break;
			case DOUBLE_BIRCH_WOOD_SLAB :
				item = new ItemStack(Material.WOOD, 1, (short) 2);
				break;
			case DOUBLE_BRICK_SLAB :
				item = new ItemStack(Material.BRICK);
				break;
			case DOUBLE_COBBLESTONE_SLAB :
				item = new ItemStack(Material.COBBLESTONE);
				break;
			case DOUBLE_DARK_OAK_WOOD_SLAB :
				item = new ItemStack(Material.WOOD, 1, (short) 5);
				break;
			case DOUBLE_JUNGLE_WOOD_SLAB :
				item = new ItemStack(Material.WOOD, 1, (short) 3);
				break;
			case DOUBLE_NETHER_BRICK_SLAB :
				item = new ItemStack(Material.NETHER_BRICK);
				break;
			case DOUBLE_OAK_WOOD_SLAB :
				item = new ItemStack(Material.WOOD, 1, (short) 0);
				break;
			case DOUBLE_PURPUR_SLAB :
				item = new ItemStack(Material.PURPUR_BLOCK);
				break;
			case DOUBLE_QUARTZ_SLAB :
				item = new ItemStack(Material.QUARTZ_BLOCK);
				break;
			case DOUBLE_RED_SANDSTONE_SLAB :
				item = new ItemStack(Material.RED_SANDSTONE);
				break;
			case DOUBLE_SANDSTONE_SLAB :
				item = new ItemStack(Material.SANDSTONE);
				break;
			case DOUBLE_SPRUCE_WOOD_SLAB :
				item = new ItemStack(Material.WOOD, 1, (short) 1);
				break;
			case DOUBLE_STONE_BRICK_SLAB :
				item = new ItemStack(Material.SMOOTH_BRICK, 1, (short) 0);
				break;
			case DOUBLE_STONE_SLAB :
				item = new ItemStack(Material.STONE);
				break;
			case END_PORTAL :
				item = new ItemStack(Material.EYE_OF_ENDER);
				break;
//			case ENDER_PORTAL :
//				item = new ItemStack(Material.EYE_OF_ENDER);
//				break;
			case FARM :
				item = new ItemStack(Material.WOOD_HOE);
				break;
			case FIRE :
				item = new ItemStack(Material.FLINT_AND_STEEL);
				break;
			case FROSTED_ICE :
				item = new ItemStack(Material.PACKED_ICE);
				break;
			case GLOWING_REDSTONE_ORE :
				item = new ItemStack(Material.REDSTONE_ORE);
				break;
			case LAVA :
				item = new ItemStack(Material.LAVA_BUCKET);
				break;
			case NETHER_PORTAL :
				item = new ItemStack(Material.BLAZE_POWDER);
				break;
//			case PORTAL :
//				item = new ItemStack(Material.BLAZE_POWDER);
//				break;
			case REDSTONE_LAMP_ON :
				item = new ItemStack(Material.REDSTONE_LAMP_OFF);
				break;
			case REDSTONE_TORCH_OFF :
				item = new ItemStack(Material.REDSTONE_TORCH_ON);
				break;
			case REDSTONE_WIRE:
				item = new ItemStack(Material.REDSTONE);
				break;
			case SLIGHTLY_DAMAGED_ANVIL :
				item = new ItemStack(Material.ANVIL, 1, (short) 1);
				break;
			case BREWING_STAND:
				item = new ItemStack(Material.BREWING_STAND_ITEM);
				break;
			case CAULDRON:
				item = new ItemStack(Material.CAULDRON_ITEM);
				break;
			case SNOW :
				item = new ItemStack(Material.SNOW_BALL);
				break;
			case SNOW_LAYER :
					item = new ItemStack(Material.SNOW_BALL);
					break;
			case SNOW_LAYER_1 :
				item = new ItemStack(Material.SNOW_BALL);
				break;
			case SNOW_LAYER_2 :
				item = new ItemStack(Material.SNOW_BALL);
				break;
			case SNOW_LAYER_3 :
				item = new ItemStack(Material.SNOW_BALL);
				break;
			case SNOW_LAYER_4 :
				item = new ItemStack(Material.SNOW_BALL);
				break;
			case SNOW_LAYER_5 :
				item = new ItemStack(Material.SNOW_BALL);
				break;
			case SNOW_LAYER_6 :
				item = new ItemStack(Material.SNOW_BALL);
				break;
			case SNOW_LAYER_7 :
				item = new ItemStack(Material.SNOW_BALL);
				break;
			case SNOW_LAYER_8 :
				item = new ItemStack(Material.SNOW_BALL);
				break;
			case STRING :
				item = new ItemStack(Material.STRING);
				break;
			case VERY_DAMAGED_ANVIL :
				item = new ItemStack(Material.ANVIL, 1, (short) 2);
				break;
			case SKULL:
				item = new ItemStack(Material.SKULL_ITEM);
				break;
			case SKELETON_SKULL:
				item = new ItemStack(Material.SKULL_ITEM, 1, (short) 0);
				break;
			case WITHER_SKELETON_SKULL:
				item = new ItemStack(Material.SKULL_ITEM, 1, (short) 1);
				break;
			case ZOMBIE_HEAD:
				item = new ItemStack(Material.SKULL_ITEM, 1, (short) 2);
				break;
			case PLAYER_HEAD:
				item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
				break;
			case CREEPER_HEAD:
				item = new ItemStack(Material.SKULL_ITEM, 1, (short) 4);
				break;
			case DRAGON_HEAD:
				item = new ItemStack(Material.SKULL_ITEM, 1, (short) 5);
				break;
			case REPEATER:
				item = new ItemStack(Material.DIODE);
				break;
			case COMPARATOR:
				item = new ItemStack(Material.REDSTONE_COMPARATOR);
				break;
			case OAK_DOOR:
				item = new ItemStack(Material.DARK_OAK_DOOR_ITEM);
				break;
			case IRON_DOOR:
				item = new ItemStack(Material.IRON_DOOR);
				break;
			case SPRUCE_DOOR:
				item = new ItemStack(Material.SPRUCE_DOOR_ITEM);
				break;
			case BIRCH_DOOR:
				item = new ItemStack(Material.BIRCH_DOOR_ITEM);
				break;
			case JUNGLE_DOOR:
				item = new ItemStack(Material.JUNGLE_DOOR_ITEM);
				break;
			case ACACIA_DOOR:
				item = new ItemStack(Material.ACACIA_DOOR_ITEM);
				break;
			case DARK_OAK_DOOR:
				item = new ItemStack(Material.DARK_OAK_DOOR_ITEM);
				break;
			case CAKE:
				item = new ItemStack(Material.CAKE);
				break;
			case SUGAR_CANE:
				item = new ItemStack(Material.SUGAR_CANE);
				break;
			case WATER :
				item = new ItemStack(Material.WATER_BUCKET);
				break;
			default :
				return item;

		}
		return item;
	}

	
}
