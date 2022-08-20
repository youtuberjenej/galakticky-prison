package tomas.main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import tomas.commands.ConsoleCommands;
import tomas.commands.PlayerCommands;
import tomas.data.DataPlayer;
import tomas.enchantments.CustomEnchants;
import tomas.events.*;
import tomas.inventory.Planets;
import tomas.inventory.Upgrades;
import tomas.planets.Galaxie;
import tomas.planets.TestGalaxy;
import tomas.planets.TestPlanet;
import tomas.planets.slunecnisoustava.*;
import tomas.planets.Planet;

import java.util.*;

public final class Main extends JavaPlugin {

    public static HashMap<Player, DataPlayer> data = new HashMap<>();
    public static List<Map.Entry<Planet,Integer>> planets;
    public static List<Map.Entry<Galaxie, Integer>> galaxie;
    public static Main instance;
    private int counter;

    @Override
    public void onEnable() {
        instance = this;
        CustomEnchants.register();
        counter = 0;
        HashMap<Planet, Integer> prePlanets = new HashMap<>();
        prePlanets.put(new Merkur(), 0);
        prePlanets.put(new Venuše(), 1500);
        prePlanets.put(new Země(), 5500);
        prePlanets.put(new Mars(), 13000);
        prePlanets.put(new Jupiter(), 22000);
        prePlanets.put(new Saturn(), 35000);
        prePlanets.put(new Uran(), 50000);
        prePlanets.put(new Neptun(), 70000);
        prePlanets.put(new TestPlanet(), 100000);
        HashMap<Galaxie, Integer> preGalaxie = new HashMap<>();
        preGalaxie.put(new Slunecnisoustava(), 0);
        preGalaxie.put(new TestGalaxy(), 10000);
        new Voting();

        planets = new ArrayList<>(prePlanets.entrySet());

        Collections.sort(planets, new Comparator<Map.Entry<Planet, Integer>>() {
            @Override
            public int compare(Map.Entry<Planet, Integer> o1, Map.Entry<Planet, Integer> o2) {
                return o1.getValue()-o2.getValue();
            }
        });

        galaxie = new ArrayList<>(preGalaxie.entrySet());

        Collections.sort(galaxie, new Comparator<Map.Entry<Galaxie, Integer>>() {
            @Override
            public int compare(Map.Entry<Galaxie, Integer> o1, Map.Entry<Galaxie, Integer> o2) {
                return o1.getValue()-o2.getValue();
            }
        });


        getConfig().options().copyDefaults(true);
        saveConfig();
        getServer().getPluginManager().registerEvents(new Join(), this);
        getServer().getPluginManager().registerEvents(new Leave(), this);
        getServer().getPluginManager().registerEvents(new WorldChange(), this);
        getServer().getPluginManager().registerEvents(new BlockBreak(), this);
        getServer().getPluginManager().registerEvents(new Planets(), this);
        getServer().getPluginManager().registerEvents(new Upgrades(), this);
        getServer().getPluginManager().registerEvents(new onDamage(), this);
        getServer().getPluginManager().registerEvents(new VoidFall(), this);
        getServer().getPluginManager().registerEvents(new Shift(), this);
        getCommand("planets").setExecutor(new Planets());
        getCommand("sell").setExecutor(new Commands());
        getCommand("vote").setExecutor(new ConsoleCommands());
        getCommand("miner").setExecutor(new PlayerCommands());

        for(Map.Entry<Planet, Integer> planet : planets) {
            planet.getKey().reset();
        }

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                int i  = 0;
                for(Map.Entry<Planet, Integer> planet : planets) {
                    if(i == counter) planet.getKey().reset();
                    i++;
                }
                counter++;
                if(counter == planets.size()) counter = 0;
            }
        }, 0, 6000);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getConfig().set("VOTING." + ".VOTES", Voting.votes);
        saveConfig();
        for(Player player : Bukkit.getOnlinePlayers()) {
            data.get(player).getDataManager().save();
            if(data.get(player).getMiner().isSpawned()) data.get(player).getMiner().despawn();
            player.kickPlayer(ChatColor.RED + "Server byl vypnut!");
        }
    }

    public void broadcast(String message) {
        for(Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(ChatColor.GRAY + "[" + ChatColor.RED + ChatColor.BOLD + "BROADCAST" + ChatColor.GRAY + "] " + ChatColor.RESET + message);
        }
    }
}
