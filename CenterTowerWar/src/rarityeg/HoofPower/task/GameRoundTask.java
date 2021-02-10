package rarityeg.HoofPower.task;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import rarityeg.HoofPower.HoofPower;

public class GameRoundTask extends BukkitRunnable {

    private final JavaPlugin plugin;

    public GameRoundTask(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        int count = 0;
        for(Block block:HoofPower.towerBlockList){
            if(Material.STONE.equals(block.getType())){
                count++;
            }
        }
        if(count==0){
            plugin.getServer().broadcastMessage("拆除方胜利!");
            HoofPower.towerBlockList.clear();
            this.cancel();
        }else if(count==HoofPower.towerBlockList.size()){
            plugin.getServer().broadcastMessage("建造方胜利!");
            HoofPower.towerBlockList.clear();
            this.cancel();
        }
        plugin.getLogger().info("GameRoundTask count:"+count);

    }

}