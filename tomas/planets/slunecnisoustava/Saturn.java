package tomas.planets.slunecnisoustava;

import org.bukkit.*;
import org.bukkit.entity.Player;
import tomas.main.Main;
import tomas.planets.Planet;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Saturn extends Planet {

    @Override
    public HashMap<Material, Integer> getPrizes() {
        HashMap<Material, Integer> prizes = new HashMap<>();
        prizes.put(Material.STONE, 2);
        prizes.put(Material.COAL_ORE, 4);
        prizes.put(Material.IRON_ORE, 6);
        prizes.put(Material.GOLD_ORE, 15);
        return prizes;
    }

    @Override
    public void checkForReset() {
        int blocks = 0;
        int blocksInMine = 0;
        for(int x = mine().get(0); x <= mine().get(1); x++) {
            for(int y = mine().get(2); y <= mine().get(3); y++) {
                for(int z = mine().get(4); z <= mine().get(5); z++) {
                    blocks++;
                    if(!(Bukkit.getWorld("SATURN").getBlockAt(x, y, z).getType().equals(Material.AIR))) blocksInMine++;
                }
            }
        }
        if(blocksInMine <= blocks/5) {
            reset();
        }
    }

    @Override
    public String name() {
        return "SATURN";
    }

    @Override
    public String message() {
        return ChatColor.AQUA + "Vítej na planetě SATURN!";
    }

    @Override
    public void reset() {
        boolean contains = false;
        for(World world : Bukkit.getWorlds()) {
            if(world.getName().equals("SATURN")) contains = true;
        }
        if(!contains) return;
        Main.instance.broadcast(ChatColor.AQUA + "důl SATURN byl obnoven!");
        for(int x = mine().get(0); x <= mine().get(1); x++) {
            for(int y = mine().get(2); y <= mine().get(3); y++) {
                for(int z = mine().get(4); z <= mine().get(5); z++) {
                    int number = new Random().nextInt(100) + 1;
                    int n1 = 0;
                    for(Material material : chance().keySet()) {
                        if(number >= n1 && number <= n1 + chance().get(material)) {
                            Bukkit.getWorld("SATURN").getBlockAt(x, y, z).setType(material);
                        }
                        n1 += chance().get(material);
                    }
                }
            }
        }
        for(Player player : Bukkit.getOnlinePlayers()) {
            if(player.getWorld().getName().equals("SATURN")) {
                if(player.getLocation().getX() >= mine().get(0) && player.getLocation().getX() <= mine().get(1)) {
                    if(player.getLocation().getY() >= mine().get(2) && player.getLocation().getY() <= mine().get(3)) {
                        if(player.getLocation().getZ() >= mine().get(4) && player.getLocation().getZ() <= mine().get(5)) {
                            player.teleport(spawnLocation());
                            if(Main.data.get(player).getMiner().isSpawned()) Main.data.get(player).getMiner().getEntity().teleport(player.getLocation());
                            player.sendTitle("" + ChatColor.RED + ChatColor.BOLD + "Důl se resetuje", ChatColor.GREEN + "Byl jsi teleportován na bezpečnou lokaci");
                        }
                    }
                }
            }
        }
    }

    public HashMap<Material, Integer> chance() {
        HashMap<Material, Integer> chances = new HashMap<>();
        chances.put(Material.STONE, 10);
        chances.put(Material.COAL_ORE, 65);
        chances.put(Material.IRON_ORE, 15);
        chances.put(Material.GOLD_ORE, 10);
        return chances;
    }

    @Override
    public List<Integer> mine() {
        return List.of(1, 11, 65, 75, 1, 11);
    }

    @Override
    public Location spawnLocation() {
        return new Location(Bukkit.getWorld("SATURN"), 0, 65, 0);
    }
}
