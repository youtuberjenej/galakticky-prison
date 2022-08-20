package tomas.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import tomas.planets.Planet;

public class VoidFall implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        if(e.getPlayer().getLocation().getY() <= 50) {
            Planet planet = Planet.getPlanetByName(e.getPlayer().getWorld().getName());
            e.getPlayer().teleport(planet.spawnLocation());
        }
    }
}
