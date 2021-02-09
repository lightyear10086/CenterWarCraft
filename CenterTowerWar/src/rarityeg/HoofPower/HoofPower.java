package rarityeg.HoofPower;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;

public class HoofPower extends JavaPlugin{
    public static JavaPlugin instance;
    public static String playerhero="";


    public void Loginfo(String infocontent){
        getLogger().info(infocontent);
    }


    @Override
    public void onLoad(){
        saveDefaultConfig();
    }

    @Override
    public void onEnable(){
        Bukkit.getPluginManager().registerEvents(new EventListener(),this);
        instance=this;
    }

    @Override
    @ParametersAreNonnullByDefault
    public boolean onCommand(CommandSender sender, Command command,String label,String[] args){
        if(!command.getName().equals("cd")){
            return false;
        }
        if(!(sender instanceof Player)){
            return false;
        }
        new Menu((Player) sender).open();
        return true;
    }
}
