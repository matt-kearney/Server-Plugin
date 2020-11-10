package io.github.mattkearney.MyPlugin;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;


public class SetHome implements CommandExecutor {

    private final MyPlugin plugin;

    public SetHome(MyPlugin plugin){
        this.plugin = plugin;
    }

    public void setHome(Player p, String homename){
        if(homename.equals("dne")){
            p.sendMessage("§cCannot set homename to 'null'");
            return;
        }
        int homecount = p.getMetadata("homecount").get(0).asInt();
        if(homecount > 2 || homecount < 0){
            plugin.getLogger().info("Invalid homecount!");
        }else if(homecount == 1 || homecount == 0){
            Location l = p.getLocation();
            p.sendMessage("§6Set home §D"+homename);
            if(homecount == 0){
                plugin.getLogger().info("No homes found! Creating new home");
                // write to the config
                plugin.writeMeta(p.getName()+".home.home1.x", Integer.toString(l.getBlockX()));
                plugin.writeMeta(p.getName()+".home.home1.y", Integer.toString(l.getBlockY()));
                plugin.writeMeta(p.getName()+".home.home1.z", Integer.toString(l.getBlockZ()));
                plugin.writeMeta(p.getName()+".home.home1.nickname", homename);
                plugin.writeMeta(p.getName()+".home.home1.world", p.getWorld().getName());
                // set metadata stats
                p.setMetadata("&home1", new FixedMetadataValue(this.plugin, homename));
                p.setMetadata("homecount", new FixedMetadataValue(this.plugin, 1));
                p.setMetadata(homename, new FixedMetadataValue(this.plugin, l));

            }else{
                // first we check to replace, then we look to set
                if(p.hasMetadata(homename)){
                    if(p.getMetadata("&home1").get(0).equals(homename)){
                        plugin.getLogger().info("Replacing home1");
                        // update the config
                        plugin.writeMeta(p.getName()+".home.home1.x", Integer.toString(l.getBlockX()));
                        plugin.writeMeta(p.getName()+".home.home1.y", Integer.toString(l.getBlockY()));
                        plugin.writeMeta(p.getName()+".home.home1.z", Integer.toString(l.getBlockZ()));
                        plugin.writeMeta(p.getName()+".home.home1.world", p.getWorld().getName());
                        // update the metadata
                        p.setMetadata(homename, new FixedMetadataValue(this.plugin, l));
                    }else if(p.getMetadata("&home2").get(0).equals(homename)){
                        plugin.getLogger().info("Replacing home2");
                        // update the config
                        plugin.writeMeta(p.getName()+".home.home2.x", Integer.toString(l.getBlockX()));
                        plugin.writeMeta(p.getName()+".home.home2.y", Integer.toString(l.getBlockY()));
                        plugin.writeMeta(p.getName()+".home.home2.z", Integer.toString(l.getBlockZ()));
                        plugin.writeMeta(p.getName()+".home.home2.world", p.getWorld().getName());
                        // update the metadata
                        p.setMetadata(homename, new FixedMetadataValue(this.plugin, l));

                    }
                }else{
                    plugin.writeMeta(p.getName()+".home.home2.x", Integer.toString(l.getBlockX()));
                    plugin.writeMeta(p.getName()+".home.home2.y", Integer.toString(l.getBlockY()));
                    plugin.writeMeta(p.getName()+".home.home2.z", Integer.toString(l.getBlockZ()));
                    plugin.writeMeta(p.getName()+".home.home2.nickname", homename);
                    plugin.writeMeta(p.getName()+".home.home2.world", p.getWorld().getName());
                    p.setMetadata("&home2", new FixedMetadataValue(this.plugin, homename));
                    p.setMetadata("homecount", new FixedMetadataValue(this.plugin, 2));
                    p.setMetadata(homename, new FixedMetadataValue(this.plugin, l));

                }
            }
        }else{
            if(p.hasMetadata(homename)){
                Location l = p.getLocation();
                if(p.getMetadata("&home1").get(0).equals(homename)){
                    p.setMetadata(homename, new FixedMetadataValue(this.plugin, l));
                    plugin.writeMeta(p.getName()+".home.home1.x", Integer.toString(l.getBlockX()));
                    plugin.writeMeta(p.getName()+".home.home1.y", Integer.toString(l.getBlockY()));
                    plugin.writeMeta(p.getName()+".home.home1.z", Integer.toString(l.getBlockZ()));
                    plugin.writeMeta(p.getName()+".home.home1.world", p.getWorld().getName());
                    p.sendMessage("§dhome 1 set!");
                }else {
                    p.setMetadata(homename, new FixedMetadataValue(this.plugin, l));
                    plugin.writeMeta(p.getName() + ".home.home2.x", Integer.toString(l.getBlockX()));
                    plugin.writeMeta(p.getName() + ".home.home2.y", Integer.toString(l.getBlockY()));
                    plugin.writeMeta(p.getName() + ".home.home2.z", Integer.toString(l.getBlockZ()));
                    plugin.writeMeta(p.getName() + ".home.home2.world", p.getWorld().getName());
                    p.sendMessage("§dhome 2 set!");
                }
            }else{
                p.sendMessage("§cCannot add another home. Either replace or delete existing homes.");
            }
        }
    }

    public Location getHome(Player p, String homename){
        if(homename.equals("dne")){
            p.sendMessage("§cInvalid home name.");
            return null;
        }
        if(p.hasMetadata(homename)){
            return (Location) p.getMetadata(homename).get(0).value();
        }else{
            p.sendMessage("§6You must set home first with /sethome <name>");
            return null;
        }
    }

    public void printHomes(Player p){
        String home1 = (String) p.getMetadata("&home1").get(0).value();
        String home2 = (String) p.getMetadata("&home2").get(0).value();
        if(home1.equals("dne") && home2.equals("dne")){
            p.sendMessage("§6You don't have any existing homes.");
            return;
        }else{
            p.sendMessage("§6Currently set homes:");
            if(!home1.equals("dne")){
                p.sendMessage("§d"+home1);
            }
            if(!home2.equals("dne")){
                p.sendMessage("§d"+home2);
            }
        }
    }

    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if(!(sender instanceof Player)){ return true; }

        if(cmd.getName().equalsIgnoreCase("sethome")) {
            if (args.length > 1) {
                sender.sendMessage("§6Too many arguments!");
                return false;
            } else {
                String home = "default";
                if (args.length == 1) {
                    home = args[0];
                }
                Player player = (Player) sender;
                if (home.charAt(0) == '&'){
                    player.sendMessage("§6Home name cannot start with '&'");
                    return true;
                }
                setHome(player, home);
            }
        }else if(cmd.getName().equalsIgnoreCase("home")) {
            if (args.length > 1) {
                sender.sendMessage("§6Too many arguments!");
                return false;
            }else{
                String homename = "default";
                if (args.length == 1) {
                    homename = args[0];
                }
                Player player = (Player) sender;
                Location home = getHome(player, homename);
                if(home == null){
                    player.sendMessage("§6Home "+homename+" doesn't exist!");
                    return false;
                }else{
                    player.sendMessage("§6Teleporting player to §D"+homename);
                    home.getWorld().loadChunk(home.getChunk());
                    player.teleport(home);
                }
            }
        }else if(cmd.getName().equalsIgnoreCase("homes")) {
            if (args.length > 0){
                sender.sendMessage("§6Too many arguements!");
                return false;
            }else{
                Player player = (Player) sender;
                printHomes(player);
            }
        }
        return true;
    }
}
