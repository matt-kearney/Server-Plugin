package io.github.mattkearney.MyPlugin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

/*
https://bukkit.gamepedia.com/Help.yml
 */

public class MyPlugin extends JavaPlugin {


    @Override
    public void onEnable(){
        getLogger().info("onEnable is called!");

        SetHome homeManager = new SetHome(this);
        this.getCommand("sethome").setExecutor(homeManager);
        this.getCommand("home").setExecutor(homeManager);

        NickName nicknameManager = new NickName(this);
        this.getCommand("nickname").setExecutor(nicknameManager);
    }

    @Override
    public void onDisable() {
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
}
