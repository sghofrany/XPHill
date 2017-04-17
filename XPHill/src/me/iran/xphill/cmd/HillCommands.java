package me.iran.xphill.cmd;

import java.util.ArrayList;
import java.util.Arrays;

import me.iran.xphill.XPHill;
import me.iran.xphill.hill.Hill;
import me.iran.xphill.hill.HillManager;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class HillCommands implements CommandExecutor {

	XPHill plugin;
	
	public HillCommands(XPHill plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(!(sender instanceof Player)) {
			return true;
		}
		
		Player player = (Player) sender;
		
		if(cmd.getName().equalsIgnoreCase("hill")) {
			if(args.length < 1) {
				player.sendMessage("/hill create | setloc1 | setloc2 | setloot | settime");
				return true;
			}
			
			if(args[0].equalsIgnoreCase("create")) {
				if(args.length < 2) {
					player.sendMessage("/hill create <name>");
					return true;
				}
				
				HillManager.getManager().createHill(player, args[1]);
			}
			
			if(args[0].equalsIgnoreCase("setloc1")) {
				if(args.length < 2) {
					player.sendMessage("/hill setloc1 <name>");
					return true;
				}
				
				HillManager.getManager().setHillLoc1(player, player.getLocation(), args[1]);
			}
			
			if(args[0].equalsIgnoreCase("setloc2")) {
				if(args.length < 2) {
					player.sendMessage("/hill setloc2 <name>");
					return true;
				}
				
				HillManager.getManager().setHillLoc2(player, player.getLocation(), args[1]);
			}
			
			if(args[0].equalsIgnoreCase("setlootloc")) {
				if(args.length < 2) {
					player.sendMessage("/hill setlootloc <name>");
					return true;
				}
				
				HillManager.getManager().setLootLocation(player, player.getLocation(), args[1]);
			}
			
			if(args[0].equalsIgnoreCase("setloot")) {
				if(args.length < 2) {
					player.sendMessage("/hill setloot <name>");
					return true;
				}
				
				ArrayList<ItemStack> items = new ArrayList<ItemStack>();
				
				for(int i = 0; i < player.getInventory().getContents().length; i++) {
					 if(player.getInventory().getContents()[i] != null) {
						 items.add(player.getInventory().getContents()[i]);
					 }
				}
				
				ItemStack[] itm = new ItemStack[items.size()];
				
				itm = items.toArray(itm);
				
				HillManager.getManager().setLootItems(player, itm, args[1]);
			}
			
			if(args[0].equalsIgnoreCase("settime")) {
				if(args.length < 3) {
					player.sendMessage("/hill settime <name> <time>");
					return true;
				}
				
				int timer = Integer.parseInt(args[2]);
				
				HillManager.getManager().setHillTime(player, timer, args[1]);
			}
			
			if(args[0].equalsIgnoreCase("setactive")) {
				if(args.length < 2) {
					player.sendMessage("/hill settime <name>");
					return true;
				}
				
				Hill hill = HillManager.getManager().getHillByName(args[1]);
				
				if(hill.getActive()) {
					hill.setStatus(false);
					player.sendMessage(ChatColor.GREEN + "hill is now inactive");
				} else{
					hill.setStatus(true);
					player.sendMessage(ChatColor.GREEN + "hill is now active");
				}
			}
			
		}
		
		return true;
	}
	
}
