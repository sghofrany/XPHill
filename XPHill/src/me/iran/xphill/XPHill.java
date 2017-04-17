package me.iran.xphill;

import me.iran.xphill.cmd.HillCommands;
import me.iran.xphill.hill.HillManager;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class XPHill extends JavaPlugin {

	public static Plugin plugin;

	HillRunnable run = new HillRunnable(this);
	
	public void onEnable() {
		
		run.runTaskTimer(this, 20, 20);
		
		XPHill.plugin = this;
		
		Bukkit.getPluginManager().registerEvents(new HillRunnable(this), this);
		
		getCommand("hill").setExecutor(new HillCommands(this));
		
		HillManager.getManager().loadHills();
	}
	
	public void onDisable() {
		HillManager.getManager().saveHills();
	}
	
}
