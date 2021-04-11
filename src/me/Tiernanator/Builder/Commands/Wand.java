package me.Tiernanator.Builder.Commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.Tiernanator.Builder.BuilderMain;
import me.Tiernanator.Utilities.Colours.Colour;
import me.Tiernanator.Utilities.Items.ItemUtility;

public class Wand implements CommandExecutor {

	@SuppressWarnings("unused")
	private static BuilderMain plugin;

	private ChatColor warning = Colour.WARNING.getColour();
	private ChatColor informative = Colour.INFORMATIVE.getColour();
	private ChatColor good = Colour.GOOD.getColour();
	private ChatColor bad = Colour.BAD.getColour();

	public Wand(BuilderMain main) {
		plugin = main;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		
		if (!(sender instanceof Player)) {
			sender.sendMessage(warning + "You can't use this command.");
			return true;
		}
		
		Player player = (Player) sender;
		
//		if(!(player.hasPermission("build.wand"))) {
//			player.sendMessage(warning + "You do not have permission to use this command.");
//			return false;
//		}
		
		
		Inventory inventory = player.getInventory();
		
		double random = Math.floor(Math.random() * 100);
		
		ItemStack wand;
		if(random < 5) {
			wand = new ItemStack(Material.LEGACY_RAW_FISH);
			wand = ItemUtility.renameItem(wand, ChatColor.AQUA + "A fish called Wanda");
//			wand = Item.renameItem(Material.LEGACY_RAW_FISH, ChatColor.AQUA + "A fish called Wanda");
		} else {
			wand = new ItemStack(Material.STICK);
			wand = ItemUtility.renameItem(wand, ChatColor.AQUA + "Wand");
//			wand = Item.renameItem(Material.STICK, ChatColor.AQUA + "Wand");
		}
		
		ItemMeta meta = wand.getItemMeta();
		meta.addEnchant(Enchantment.DURABILITY, 1, true);

		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.GOLD + "Use to select blocks for World Building.");
		lore.add(ChatColor.BOLD + "(Cannot be used to break blocks)");
		meta.setLore(lore);
		
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		wand.setItemMeta(meta);
		
		if(inventory.contains(wand)) {
			player.sendMessage(bad + "You already have a wand...");
			return true;
		}
		inventory.addItem(wand);
		
		player.sendMessage(good + "You have received a " + informative + "World Building " + good + "wand!");
		
		return true;
	}

}
