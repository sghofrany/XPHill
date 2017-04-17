package me.iran.xphill.hill;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

public class Hill {

	private Location loc1;
	private Location loc2;
	
	private Location loot;
	
	private String name;
	
	private String world;
	
	private boolean active;

	private int timer;
	
	private int atime;
	
	private ArrayList<String> cappers;
	
	private ItemStack[] items;
	
	public Hill(String name, Location loc1, Location loc2, Location loot, String world) {
		
		this.loc1 = loc1;
		this.loc2 = loc2;
		this.loot = loot;
		this.name = name;
		this.world = world;
		cappers = new ArrayList<>();
		this.active = false; 
		
	}

	public void setWorld(String world) {
		this.world = world;
	}
	
	public void setLoc1(Location loc1) {
		this.loc1 = loc1;
	}
	
	public void setLoc2(Location loc2) {
		this.loc2 = loc2;
	}
	
	public void setLootLoc(Location loot) {
		this.loot = loot;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setStatus(boolean active) {
		this.active = active;
	}
	
	public void setTimer(int timer) {
		this.timer = timer;
	}
	
	public void setActualTime(int atime) {
		this.atime = atime;
	}
	
	public void setLoot(ItemStack[] items) {
		this.items = items;
	}
	
	public ItemStack[] getItems() {
		return items;
	}
	
	public String getWorld() {
		return world;
	}
	
	public int getTimer() {
		return timer;
	}
	
	public int getActualTimer() {
		return atime;
	}
	
	public Location getLoc1() {
		return loc1;
	}
	
	public Location getLoc2() {
		return loc2;
	}
	
	public Location getLoot() {
		return loot;
	}
	
	public String getName() {
		return name;
	}
	
	public boolean getActive() {
		return active;
	}
	
	public ArrayList<String> getCappers() {
		return cappers;
	}
	
}
