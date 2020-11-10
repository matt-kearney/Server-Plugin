package io.github.mattkearney.MyPlugin;

import com.sun.media.sound.FFT;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.List;

public class LoginListener implements Listener {

    private MyPlugin plugin;

    public LoginListener(MyPlugin plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent pje){
        FileConfiguration config = plugin.getConfig();
        Player p = pje.getPlayer();
        if(config.contains(p.getName())){
            plugin.getLogger().info("Player found");
            int homecount = 0;
            // LOAD HOMES
            if(contains(p.getName(), "home.home1")){
                String homename = config.getString(p.getName()+".home.home1.nickname");
                if(!homename.equals("dne")){
                    homecount++;
                    int x = config.getInt(p.getName()+".home.home1.x");
                    int y = config.getInt(p.getName()+".home.home1.y");
                    int z = config.getInt(p.getName()+".home.home1.z");
                    String world = config.getString(p.getName()+".home.home1.world");
                    plugin.getLogger().info("WORLD: "+world);
                    plugin.getLogger().info("GET WORLD: "+plugin.getServer().getWorld(world).getName());
                    Location l = new Location(plugin.getServer().getWorld(world), x, y, z);
                    p.setMetadata(homename, new FixedMetadataValue(plugin, l));
                    p.setMetadata("&home1", new FixedMetadataValue(plugin, homename));
                }
            }
            if(contains(p.getName(), "home.home2")){
                String homename = config.getString(p.getName()+".home.home2.nickname");
                if(!homename.equals("dne")){
                    homecount++;
                    int x = config.getInt(p.getName()+".home.home2.x");
                    int y = config.getInt(p.getName()+".home.home2.y");
                    int z = config.getInt(p.getName()+".home.home2.z");
                    String world = config.getString(p.getName()+".home.home2.world");
                    Location l = new Location(plugin.getServer().getWorld(world), x, y, z);
                    p.setMetadata(homename, new FixedMetadataValue(plugin, l));
                    p.setMetadata("&home2", new FixedMetadataValue(plugin, homename));
                }
            }
            plugin.getLogger().info("HOMECOUNT SAVED TO META");
            p.setMetadata("homecount", new FixedMetadataValue(plugin, homecount));
            config.set(p.getName()+".home.homecount", homecount);
            // LOAD NICKNAME
            if(contains(p.getName(), "nickname")) {
                plugin.getLogger().info("Loaded nickname!");
                p.setMetadata("nickname", new FixedMetadataValue(plugin, config.getString(p.getName() + ".nickname")));
                p.setDisplayName(config.getString(p.getName() + ".nickname"));
            }
        }else{
            plugin.getLogger().info("Player not found, creating plugin data");
            p.setMetadata("nickname", new FixedMetadataValue(plugin, p.getName()));
            p.setMetadata("homecount", new FixedMetadataValue(plugin, 0));
            config.set(p.getName()+".nickname", p.getName());
            config.set(p.getName()+".home.home1.nickname", "dne");
            config.set(p.getName()+".home.home2.nickname", "dne");
            config.set(p.getName()+".home.homecount", 0);
        }
    }

    private boolean contains(String player, String key){
        return plugin.getConfig().contains(player+"."+key);
    }
}
