package io.github.mattkearney.MyPlugin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

public class NickName implements CommandExecutor {

    private final MyPlugin plugin;

    public NickName(MyPlugin plugin){
        this.plugin = plugin;
    }

    public void setNickname(Player target, String nickname){
        target.setDisplayName(nickname);
        plugin.writeMeta(target.getName()+".nickname", nickname);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if(!(sender instanceof Player)){ return true; }

        if(cmd.getName().equalsIgnoreCase("nickname")){
            if(args.length != 1){
                sender.sendMessage("§6Incorrect arguments!");
                return false;
            }else{
                Player player = (Player) sender;
                setNickname(player, args[0]);

            }
        }
        return true;
    }
}
