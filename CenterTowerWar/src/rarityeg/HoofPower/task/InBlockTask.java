package rarityeg.HoofPower.task;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import rarityeg.HoofPower.hero.HeroBase;
import rarityeg.HoofPower.hero.WildFarmer;

public class InBlockTask extends BukkitRunnable {
    private HeroBase heroBase;
    private JavaPlugin plugin;

    public InBlockTask(HeroBase heroBase){
        this.heroBase=heroBase;
    }

    public void setHeroBase(HeroBase heroBase){
        this.heroBase=heroBase;
    }


    @Override
    public void run(){
        switch (heroBase.getHeroname()){
                case "荒野农夫·史蒂夫":
                    WildFarmer wf=(WildFarmer) heroBase;
                    wf.InGrassGetSeeds();
                    break;
            }
    }
}
