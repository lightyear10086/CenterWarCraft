package rarityeg.HoofPower;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import rarityeg.HoofPower.task.GameRoundTask;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class HoofPower extends JavaPlugin{
    public static JavaPlugin instance;
    public static String playerhero="";
    public List<String> commandslist=new ArrayList<>();
    public List<Player> builderplayerlist=new ArrayList<>();
    public List<Player> ruinerplayerlist=new ArrayList<>();
    private Integer LeastPlayer=1;//每队人数，两队人数都达到这个数后才能开始（指执行/startgame）游戏
    public static List<Block> towerBlockList = new CopyOnWriteArrayList<>();

    public void Loginfo(String infocontent){
        getLogger().info(infocontent);
    }

    //检测玩家是否已加入某一队伍
    public boolean PlayerinTeamCheck(Player player){
        if(builderplayerlist.contains(player) || ruinerplayerlist.contains(player)){
            player.sendMessage(ChatColor.RED +"玩家已经在队伍里了");
            return false;
        }
        return true;
    }

    @Override
    public void onLoad(){
        saveDefaultConfig();
        commandslist.add("cd");
        commandslist.add("jointeam");
        commandslist.add("createtower");
    }

    @Override
    public void onEnable(){
        Bukkit.getPluginManager().registerEvents(new EventListener(),this);
        instance=this;
    }

    @Override
    @ParametersAreNonnullByDefault
    public boolean onCommand(CommandSender sender, Command command,String label,String[] args){
//        if(!commandslist.contains(command.getName())){
//            return false;
//        }
        if(!(sender instanceof Player)){
            return false;
        }
        switch (command.getName()){
            case "cd":
                new Menu((Player) sender).open();
                break;
            case "jointeam":
                if("create".equals(args[0]) && PlayerinTeamCheck((Player)sender)){
                    builderplayerlist.add((Player)sender);
                }
                if("ruin".equals(args[0]) && PlayerinTeamCheck((Player)sender)){
                    ruinerplayerlist.add((Player) sender);
                }
                break;
            case "startgame":
                if(builderplayerlist.size()!=ruinerplayerlist.size() || builderplayerlist.size()<LeastPlayer || ruinerplayerlist.size()<LeastPlayer){
                    instance.getServer().broadcastMessage("未达到游戏开始条件");
                    break;
                }
                startGameCommand(sender, command, label, args);
                break;
            default:
                return false;
        }

        return true;
    }

    private void startGameCommand(CommandSender sender, Command command,String label,String[] args){

        getLogger().info(Arrays.toString(args));
        if(args.length==3){
            double x = Double.parseDouble(args[0]);
            double y = Double.parseDouble(args[1]);
            double z = Double.parseDouble(args[2]);

            for(int i=0;i<4;i++){
                for(int j=0;j<4;j++){
                    Location location = new Location(((Player) sender).getWorld(), x+i, y, z+j);
                    Block block = location.getBlock();
                    if(j>1){
                        block.setType(Material.STONE);
                    }
                    towerBlockList.add(block);
                }
            }
            for(int i=1;i<3;i++){
                for(int j=1;j<3;j++){
                    Location location = new Location(((Player) sender).getWorld(), x+i, y+1, z+j);
                    Block block = location.getBlock();
                    if(j>1){
                        block.setType(Material.STONE);
                    }
                    towerBlockList.add(block);
                }
            }

            World playerWorld=Bukkit.getWorld("world");

            //建设方开局和重生坐标点
            Location builderbeginpos=new Location(playerWorld,x,y+14,z);
            //拆除方开局和重生坐标点
            Location ruinerbeginpos=new Location(playerWorld,x,y-10,z);
            //将所有玩家传送到对应队伍开局坐标点
            for(Player builder:builderplayerlist){
                builder.teleport(builderbeginpos);
            }
            for(Player ruiner:ruinerplayerlist){
                ruiner.teleport(ruinerbeginpos);
            }

            new GameRoundTask(this).runTaskTimer(this, 0,5);
        }
    }
}
