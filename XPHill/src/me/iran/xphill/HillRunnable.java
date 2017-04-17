package me.iran.xphill;

import me.iran.xphill.hill.Hill;
import me.iran.xphill.hill.HillManager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class HillRunnable extends BukkitRunnable implements Listener {

	XPHill plugin;
	public HillRunnable(XPHill plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public void run() {
		
		hillTimer();
	
	}
	
	public void hillTimer() {

		for(Hill hill : HillManager.getManager().getAllHills()) {
		
			if(hill.getActive() && hill.getCappers().size() > 0) {
				
				hill.setTimer(hill.getTimer() - 1);
				
				if(hill.getTimer() <= 0) {
					hill.setTimer(hill.getActualTimer());
					
					if(hill.getCappers().size() > 0 && hill.getCappers().size() < 3) {
						
						Location loc = hill.getLoot();
						
						for(int i = 0; i < hill.getItems().length; i++) {
							Bukkit.getWorld(hill.getWorld()).dropItemNaturally(loc, hill.getItems()[i]);
						}
						
						Bukkit.broadcastMessage(ChatColor.DARK_AQUA + "There are " + hill.getCappers().size() + " people standing on Hill " + hill.getName());
					}
					
				}
			} else {
				hill.setTimer(hill.getActualTimer());
			}
		}
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent event) {
		hillLogic(event.getPlayer());
	}
	
	public void hillLogic(Player player) {
	
		if(HillManager.getManager().insideHill(player.getLocation())) { 
			Hill hill = HillManager.getManager().getHillByLocation(player.getLocation());
			
			if(!hill.getCappers().contains(player.getName())) {
				hill.getCappers().add(player.getName());
				player.sendMessage(ChatColor.GOLD + "You have entered Hill " + hill.getName() + ChatColor.YELLOW + "(" + hill.getTimer() + ")"); 
			}
		} else {
			
			for(Hill hill : HillManager.getManager().getAllHills()) {
				
				if(hill.getCappers().contains(player.getName())) {
					hill.getCappers().remove(player.getName());
					
					player.sendMessage(ChatColor.RED + "Removed you from Hill " + hill.getName() + ChatColor.YELLOW + "(" + hill.getTimer() + ")");
				}
				
			}
			
		}
	}
	
}
