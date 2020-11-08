package io.github.mattkearney.MyPlugin;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

public class SetHome implements CommandExecutor {

    private final MyPlugin plugin;

    public SetHome(MyPlugin plugin){
        this.plugin = plugin;
    }

    public void setHome(Player p, String homename){
        p.setMetadata(homename, new FixedMetadataValue(this.plugin, p.getLocation()));
        p.sendMessage("&6Set home &D"+homename);
    }

    public Location getHome(Player p, String homename){
        if(p.hasMetadata(homename)){
            return (Location) p.getMetadata(homename).get(0).value();
        }else{
            p.sendMessage("&6You must set home first with /sethome <name>");
            return null;
        }
    }

    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if(!(sender instanceof Player)){ return true; }

        if(cmd.getName().equalsIgnoreCase("sethome")) {
            if (args.length > 1) {
                sender.sendMessage("&6Too many arguments!");
                return false;
            } else {
                String home = "default";
                if (args.length == 1) {
                    home = args[0];
                }
                Player player = (Player) sender;
                setHome(player, home);
            }
        }else if(cmd.getName().equalsIgnoreCase("home")) {
            if (args.length > 1) {
                sender.sendMessage("&6Too many arguments!");
                return false;
            }else{
                String homename = "default";
                if (args.length == 1) {
                    homename = args[0];
                }
                Player player = (Player) sender;
                Location home = getHome(player, homename);
                if(home == null){
                    player.sendMessage("Home "+homename+" doesn't exist!");
                    return false;
                }else{
                    player.teleport(home);
                }
            }
        }
        return true;
    }
}
