package rarityeg.HoofPower.hero;

import com.sun.source.tree.WhileLoopTree;
import org.bukkit.scheduler.BukkitRunnable;
import rarityeg.HoofPower.HoofPower;

public class HeroTimeTask extends BukkitRunnable {
    private int seconds;
    private String taskname;
    private HeroBase heroBase;

    public HeroTimeTask(int seconds,String taskname,HeroBase heroBase){
        this.seconds=seconds;
        this.taskname=taskname;
        this.heroBase=heroBase;
        this.runTaskTimer(HoofPower.instance,0,20);
    }

    @Override
    public void run(){
        switch (taskname){
            case "REBORN"://重生倒计时
                //...
                break;
            case "SKILLCOLD:魔种"://技能[魔种]冷却
                ((WildFarmer)heroBase).setSkill_magicseed(seconds);
                ((WildFarmer)heroBase).setSkill_magicseed(seconds);
                break;
            case "SKILLCOLD:收割季节":
                ((WildFarmer)heroBase).setSkill_harvesting_season(seconds);
                ((WildFarmer)heroBase).setSkill_harvesting_season(seconds);
                break;
        }
        seconds--;
        if(seconds<0){
            switch (taskname){
                case "WILDFARMERHARVESTATTACKADD":
                    ((WildFarmer)heroBase).setAttackadd(((WildFarmer)heroBase).getAttackadd()-3);
                    ((WildFarmer)heroBase).setAttack(((WildFarmer)heroBase).getAttack()-3);
                    ((WildFarmer)heroBase).updateheroinfo();
            }
            this.cancel();
        }
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public String getTaskname() {
        return taskname;
    }

    public void setTaskname(String taskname) {
        this.taskname = taskname;
    }

    public HeroBase getHeroBase() {
        return heroBase;
    }

    public void setHeroBase(HeroBase heroBase) {
        this.heroBase = heroBase;
    }
}
class persecondtask extends BukkitRunnable{
    
    public persecondtask(){
        runTaskTimer(HoofPower.instance,0,20);
    }
    @Override
    public void run(){

    }
}