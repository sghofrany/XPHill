package me.iran.xphill.hill;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.iran.xphill.XPHill;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class HillManager {

	private File file = null;
	
	private static ArrayList<Hill> hills = new ArrayList<>();
	
	private static HillManager hm;
	
	private HillManager() { }
	
	public static HillManager getManager() {
		if(hm == null) {
			hm = new HillManager();
		}
		
		return hm;
	}
	
	public void loadHills() {
		
		file = new File(XPHill.plugin.getDataFolder(), "hill.yml");
		
		if(file.exists()) {
			
			YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
			
			for(String name : config.getConfigurationSection("hill").getKeys(false)) {
				
				String hillName = config.getString("hill." + name + ".name");
				
				//Location 1
				int loc1x = config.getInt("hill." + name + ".loc1x");
				int loc1y = 0;
				int loc1z = config.getInt("hill." + name + ".loc1z");
				
				//Location 2
				int loc2x = config.getInt("hill." + name + ".loc2x");
				int loc2y = 0;
				int loc2z = config.getInt("hill." + name + ".loc2z");
				
				//Loot location
				int lootx = config.getInt("hill." + name + ".lootx");
				int looty = config.getInt("hill." + name + ".looty");
				int lootz = config.getInt("hill." + name + ".lootz");
				
				int timer = config.getInt("hill." + name + ".timer");
			
				boolean status = config.getBoolean("hill." + name + ".status");
				
				String world = config.getString("hill." + name + ".world");
				
				List<?> items = config.getList("hill." + name + ".loot");
				
				ItemStack[] loot = new ItemStack[items.size()];
				
				for(int i = 0; i < items.size(); i++) {
					loot[i] = (ItemStack) items.get(i);
				}
				
				Location loc1 = new Location(Bukkit.getWorld(world), loc1x, loc1y, loc1z);
				Location loc2 = new Location(Bukkit.getWorld(world), loc2x, loc2y, loc2z);
				
				Location lootLoc = new Location(Bukkit.getWorld(world), lootx, looty, lootz);
				
				Hill hill = new Hill(hillName, loc1, loc2, lootLoc, world);
				
				hill.setActualTime(timer);
				hill.setLoot(loot);
				hill.setStatus(status);
				
				hills.add(hill);
				
			}
			
		} else {
			System.out.println("");
			System.out.println("[XPHill] Couldn't find anything to load in :(");
			System.out.println("");
		}
		
	}
	
	public void saveHills() {
	
		file = new File(XPHill.plugin.getDataFolder(), "hill.yml");
		
		if(file.exists()) {
			
			YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
			
			for(String name : config.getConfigurationSection("hill").getKeys(false)) {
				
				Hill hill = getHillByName(name);
				
				config.set("hill." + name + ".name", name);
				
				config.set("hill." + name + ".loc1x", hill.getLoc1().getBlockX());
				config.set("hill." + name + ".loc1z", hill.getLoc1().getBlockZ());
				
				config.set("hill." + name + ".loc2x", hill.getLoc2().getBlockX());
				config.set("hill." + name + ".loc2z", hill.getLoc2().getBlockZ());
				
				config.set("hill." + name + ".lootx", hill.getLoot().getBlockX());
				config.set("hill." + name + ".looty", hill.getLoot().getBlockY());
				config.set("hill." + name + ".lootz", hill.getLoot().getBlockZ());
				
				config.set("hill." + name + ".timer", hill.getActualTimer());
				
				config.set("hill." + name + ".status", hill.getActive());
				
				config.set("hill." + name + ".world", hill.getWorld());
				
				config.set("hill." + name + ".loot", hill.getItems());
			
				try {
					config.save(file);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
			
		}
		
	}
	
	public void createHill(Player player, String name) {
		
		if(!(getHillByName(name) == null)) {
			player.sendMessage(ChatColor.RED + "You have already made a Hill with the name " + name);
			return;
		}
		
		Hill hill = new Hill(name, player.getLocation(), player.getLocation(), player.getLocation(), player.getWorld().getName());
		
		hill.setName(name);
		
		file = new File(XPHill.plugin.getDataFolder(), "hill.yml");
		
		if(file.exists()) {
			
			YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
			
			config.createSection("hill." + name + ".name");
			
			config.createSection("hill." + name + ".loc1x");
			config.createSection("hill." + name + ".loc1z");
			
			config.createSection("hill." + name + ".loc2x");
			config.createSection("hill." + name + ".loc2z");
			
			config.createSection("hill." + name + ".lootx");
			config.createSection("hill." + name + ".looty");
			config.createSection("hill." + name + ".lootz");
			
			config.createSection("hill." + name + ".timer");
			
			config.createSection("hill." + name + ".world");
			
			config.createSection("hill." + name + ".loot");
			
			config.createSection("hill." + name + ".status");
			
			config.set("hill." + name + ".name", name);
			
			config.set("hill." + name + ".loc1x", hill.getLoc1().getBlockX());
			config.set("hill." + name + ".loc1z", hill.getLoc1().getBlockZ());
			
			config.set("hill." + name + ".loc2x", hill.getLoc2().getBlockX());
			config.set("hill." + name + ".loc2z", hill.getLoc2().getBlockZ());
			
			config.set("hill." + name + ".lootx", hill.getLoot().getBlockX());
			config.set("hill." + name + ".looty", hill.getLoot().getBlockY());
			config.set("hill." + name + ".lootz", hill.getLoot().getBlockZ());
			
			config.set("hill." + name + ".timer", hill.getActualTimer());
			
			config.set("hill." + name + ".status", hill.getActive());
			
			config.set("hill." + name + ".world", hill.getWorld());
			
			config.set("hill." + name + ".loot", hill.getItems());
			
			try {
				config.save(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			file = new File(XPHill.plugin.getDataFolder(), "hill.yml");
			
			YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
			
			config.createSection("hill." + name + ".name");
			
			config.createSection("hill." + name + ".loc1x");
			config.createSection("hill." + name + ".loc1z");
			
			config.createSection("hill." + name + ".loc2x");
			config.createSection("hill." + name + ".loc2z");
			
			config.createSection("hill." + name + ".lootx");
			config.createSection("hill." + name + ".looty");
			config.createSection("hill." + name + ".lootz");
			
			config.createSection("hill." + name + ".timer");
			
			config.createSection("hill." + name + ".world");
			
			config.createSection("hill." + name + ".status");
			
			config.createSection("hill." + name + ".loot");
			
			config.set("hill." + name + ".name", name);
			
			config.set("hill." + name + ".loc1x", hill.getLoc1().getBlockX());
			config.set("hill." + name + ".loc1z", hill.getLoc1().getBlockZ());
			
			config.set("hill." + name + ".loc2x", hill.getLoc2().getBlockX());
			config.set("hill." + name + ".loc2z", hill.getLoc2().getBlockZ());
			
			config.set("hill." + name + ".lootx", hill.getLoot().getBlockX());
			config.set("hill." + name + ".looty", hill.getLoot().getBlockY());
			config.set("hill." + name + ".lootz", hill.getLoot().getBlockZ());
			
			config.set("hill." + name + ".timer", hill.getActualTimer());
			
			config.set("hill." + name + ".status", hill.getActive());
			
			config.set("hill." + name + ".world", hill.getWorld());
			
			config.set("hill." + name + ".loot", hill.getItems());
			
			try {
				config.save(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		hills.add(hill);
		
		player.sendMessage(ChatColor.GREEN + "Created an Hill by the name of " + name);
		
	}

	public void setHillLoc1(Player player, Location loc1, String name) {
		if((getHillByName(name) == null)) {
			player.sendMessage(ChatColor.RED + "No XPHill by the name of " + name + " exists");
			return;
		}
		
		Hill hill = getHillByName(name);
		
		hill.setLoc1(loc1);
		
		player.sendMessage(ChatColor.GREEN + "Set Location 1 for the XPHill " + name);
		
	}
	
	public void setHillLoc2(Player player, Location loc2, String name) {
		if((getHillByName(name) == null)) {
			player.sendMessage(ChatColor.RED + "No XPHill by the name of " + name + " exists");
			return;
		}
		
		Hill hill = getHillByName(name);
		
		hill.setLoc2(loc2);
		
		player.sendMessage(ChatColor.GREEN + "Set Location 2 for the XPHill " + name);
		
	}
	
	public void setLootLocation(Player player, Location loot, String name) {
		if((getHillByName(name) == null)) {
			player.sendMessage(ChatColor.RED + "No XPHill by the name of " + name + " exists");
			return;
		}
		
		Hill hill = getHillByName(name);
		
		hill.setLootLoc(loot);
		
		player.sendMessage(ChatColor.GREEN + "Set Loot Location for the XPHill " + name);
	}
	
	public void setLootItems(Player player, ItemStack[] items, String name) {
		if((getHillByName(name) == null)) {
			player.sendMessage(ChatColor.RED + "No XPHill by the name of " + name + " exists");
			return;
		}
		
		Hill hill = getHillByName(name);
		
		hill.setLoot(items);
		
		player.sendMessage(ChatColor.GREEN + "Set Loot for the XPHill " + name);
	}
	
	public void setHillTime(Player player, int time, String name) {
		if((getHillByName(name) == null)) {
			player.sendMessage(ChatColor.RED + "No XPHill by the name of " + name + " exists");
			return;
		}
		
		Hill hill = getHillByName(name);
		
		hill.setActualTime(time);
		hill.setTimer(time);
		
		player.sendMessage(ChatColor.GREEN + "Set timer for " + name + " to " + time);
	}
	
	public Hill getHillByName(String name) {
		for(Hill hill : hills) {
			if(hill.getName().equalsIgnoreCase(name)) {
				return hill;
			}
		}
		return null;
	}
	
	public Hill getHillByLocation(Location loc) {
		for(Hill hill : hills) {
			
			Location loc1 = hill.getLoc1();
			Location loc2 = hill.getLoc2();

			int xMax = Math.max(loc1.getBlockX(), loc2.getBlockX());
			int zMax = Math.max(loc1.getBlockZ(), loc2.getBlockZ());

			int xMin = Math.min(loc1.getBlockX(), loc2.getBlockX());
			int zMin = Math.min(loc1.getBlockZ(), loc2.getBlockZ());

			if ((loc.getBlockX() >= xMin) && (loc.getBlockX() <= xMax)) {
				if ((loc.getBlockZ() >= zMin) && (loc.getBlockZ() <= zMax)) {
					return hill;
				}
			}
		}
		
		return null;
	}
	
	public boolean insideHill(Location loc) {
		for(Hill hill : hills) {
			Location loc1 = hill.getLoc1();
			Location loc2 = hill.getLoc2();

			int xMax = Math.max(loc1.getBlockX(), loc2.getBlockX());
			int zMax = Math.max(loc1.getBlockZ(), loc2.getBlockZ());

			int xMin = Math.min(loc1.getBlockX(), loc2.getBlockX());
			int zMin = Math.min(loc1.getBlockZ(), loc2.getBlockZ());

			if ((loc.getBlockX() >= xMin) && (loc.getBlockX() <= xMax)) {
				if ((loc.getBlockZ() >= zMin) && (loc.getBlockZ() <= zMax)) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	public boolean insideSpecificHill(Location loc, Hill hill) {
		
		Location loc1 = hill.getLoc1();
		Location loc2 = hill.getLoc2();

		int xMax = Math.max(loc1.getBlockX(), loc2.getBlockX());
		int zMax = Math.max(loc1.getBlockZ(), loc2.getBlockZ());

		int xMin = Math.min(loc1.getBlockX(), loc2.getBlockX());
		int zMin = Math.min(loc1.getBlockZ(), loc2.getBlockZ());

		if ((loc.getBlockX() >= xMin) && (loc.getBlockX() <= xMax)) {
			if ((loc.getBlockZ() >= zMin) && (loc.getBlockZ() <= zMax)) {
				return true;
			}
		}
		
		return false;
	}
	
	public ArrayList<Hill> getAllHills() {
		return hills;
	}
	
}
