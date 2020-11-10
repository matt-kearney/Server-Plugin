package io.github.mattkearney.MyPlugin;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

import java.util.List;
import java.util.Vector;
import java.util.logging.Level;

/*
https://bukkit.gamepedia.com/Help.yml
 */

public class MyPlugin extends JavaPlugin{

    private FileConfiguration customConfig;
    private File customConfigFile;

    @Override
    public void onEnable(){
        getLogger().info("onEnable is called!");

        saveDefaultConfig();

        SetHome homeManager = new SetHome(this);
        this.getCommand("sethome").setExecutor(homeManager);
        this.getCommand("home").setExecutor(homeManager);
        this.getCommand("homes").setExecutor(homeManager);

        NickName nicknameManager = new NickName(this);
        this.getCommand("nickname").setExecutor(nicknameManager);

        getServer().getPluginManager().registerEvents(new LoginListener(this), this);
    }

    @Override
    public void onDisable() {
        saveConfig();
        getLogger().info("onDisable is called!");
    }

    @Override
    public boolean onCommand(CommandSender sender,
                             Command command,
                             String label,
                             String[] args) {
        if(command.getName().equalsIgnoreCase("mycommand")){
            sender.sendMessage("You ran the command!");
            return true;
        }
        return true;
    }

    public void writeMeta(String path, String value){
        getConfig().set(path, value);
    }
}
