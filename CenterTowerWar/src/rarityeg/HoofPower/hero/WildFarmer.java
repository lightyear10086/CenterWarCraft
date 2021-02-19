package rarityeg.HoofPower.hero;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scoreboard.*;
import rarityeg.HoofPower.HoofPower;
import rarityeg.HoofPower.task.InBlockTask;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WildFarmer extends HeroBase implements Listener {
    private InBlockTask inBlockTask;
    private boolean first;//使玩家不能刚进草丛就获得一个种子
    private List<Entity> beaimed;
    private int skill_magicseed;//魔种冷却倒计时
    private int skill_harvesting_season;//收割季节冷却倒计时

    private Scoreboard heroboard;
    private Objective heroinfo;//英雄属性信息
    private Score skill_magicseed_cold;
    private Score skill_harvest_season_cold;

    public WildFarmer(Player player) {
        super(player);
        setHeroname("荒野农夫·史蒂夫");
        setArmor(10);
        setAttack(2);
        setDestroySpeed((float)0.5);
        setHp(10);
        setLifeRegain(1);
        setMoveSpeed((float) 0.2);
        setRebornTime(5);
        setSkillTime(3);
        Bukkit.getPluginManager().registerEvents(this,HoofPower.instance);
        first=true;
        beaimed=new ArrayList<>();
        skill_magicseed=0;
        skill_harvesting_season=0;
        giveitemtoplayer(new ItemStack(Material.IRON_HOE),0,1,"魔力锄头",new String[]{"基础攻击力 "+ChatColor.GOLD+" "+getAttack()});
        giveitemtoplayer(new ItemStack(Material.GOLDEN_HOE),1,1,"收割季节",new String[]{"对所有被[魔种]标记的实体造成3伤害，若目标死亡，20秒内为荒野农夫+3攻击力"});
        heroboard=Bukkit.getServer().getScoreboardManager().getNewScoreboard();
        heroinfo=heroboard.registerNewObjective("heroinfo","dummy","英雄信息");
        heroinfo.setDisplayName("英雄信息");
        heroinfo.setDisplaySlot(DisplaySlot.SIDEBAR);
        skill_magicseed_cold=heroinfo.getScore("魔种"+skill_magicseed);
        skill_magicseed_cold.setScore(1);
        skill_harvest_season_cold=heroinfo.getScore("收割季节"+skill_harvesting_season);
        skill_harvest_season_cold.setScore(2);
        heroinfo.getScore("基础攻击力 "+getAttack()).setScore(3);
        heroinfo.getScore("生命值    "+getHp()).setScore(4);

        player.setScoreboard(heroboard);
    }

    public void InGrassGetSeeds(){
        if(first){
            first=false;
            return;
        }

        ItemStack seed=new ItemStack(Material.WHEAT_SEEDS);
        ItemMeta seedmeta=seed.getItemMeta();
        seedmeta.setDisplayName("魔种");
        seedmeta.setLore(Collections.singletonList(ChatColor.WHITE+"可右键附着于敌方单位身上,被附着的目标受到攻击时额外受到2伤害，死亡时在20秒内为你+3攻击力"));
        seed.setItemMeta(seedmeta);
        if(getSkillbar().getItem(3)!=null && getSkillbar().getItem(3).getAmount()<8){
            getSkillbar().getItem(3).setAmount(getSkillbar().getItem(3).getAmount()+1);
        }
        if(getSkillbar().getItem(3)==null){
            getPlayer().getInventory().setItem(3,seed);
        }
    }

    @EventHandler
    public void grassgetseed(PlayerMoveEvent e){
        if(this.getPlayer().getLocation().getBlock().getType()== Material.TALL_GRASS){
            if(inBlockTask==null){
                inBlockTask=new InBlockTask(this);
                inBlockTask.runTaskTimer(HoofPower.instance,0,60);
            }
        }else{
            if(inBlockTask!=null){
                inBlockTask.cancel();
                inBlockTask=null;
                first=true;
            }
        }
    }

    @EventHandler
    public void aimPlayer(PlayerInteractEntityEvent e){
        PlayerInventory playerinventory=getPlayer().getInventory();
        String skillname="";
        if(playerinventory.getItemInMainHand()!=null && playerinventory.getItemInMainHand().getItemMeta()!=null){
            skillname=playerinventory.getItemInMainHand().getItemMeta().getDisplayName();
        }
        if(!beaimed.contains(e.getRightClicked()) && "魔种".equals(skillname) && e.getRightClicked() instanceof Player && skill_magicseed==0){
            beaimed.add(e.getRightClicked());
            playerinventory.getItemInMainHand().setAmount(playerinventory.getItemInMainHand().getAmount()-1);
            skill_magicseed=10;
            new HeroTimeTask(5,"SKILLCOLD:魔种",this);
        }
        if("魔种".equals(skillname) && skill_magicseed>0){
            getPlayer().sendMessage(ChatColor.RED+"技能冷却中");

        }

        if("魔力锄头".equals(skillname)){
            if(beaimed.contains(e.getRightClicked())){
                ((Damageable)e.getRightClicked()).damage(getAttack()+2);
            }else{
                ((Damageable)e.getRightClicked()).damage(getAttack());
            }
        }

    }

    @EventHandler
    public void shougejijie(PlayerInteractEvent e){
        PlayerInventory playerinventory=e.getPlayer().getInventory();
        if(playerinventory.getItemInMainHand()==null){
            return;
        }
        String skillname=playerinventory.getItemInMainHand().getItemMeta().getDisplayName();
        if("收割季节".equals(skillname) && skill_harvesting_season==0){
            if(beaimed.size()>0){
                beaimed.forEach((aim)->{
                    double damageheal=((Player)aim).getHealth()-3;
                    ((Damageable)aim).damage(3);
                    if(damageheal<=0){
                        setAttackadd(3);
                        setAttack(getAttack()+getAttackadd());
                        ItemMeta itemMeta=playerinventory.getItem(0).getItemMeta();
                        itemMeta.setLore(Collections.singletonList("基础攻击力 "+ChatColor.GOLD+" "+getAttack()+"(+3)"));
                        playerinventory.getItem(0).setItemMeta(itemMeta);
                        updateheroinfo();
                        new HeroTimeTask(20,"WILDFARMERHARVESTATTACKADD",this);
                    }
                });
            }else{
                getPlayer().sendMessage(ChatColor.YELLOW+"没有被标记的目标");
                return;
            }
            beaimed.clear();
            skill_harvesting_season=10;
            new HeroTimeTask(10,"SKILLCOLD:收割季节",this);
        }
        if("收割季节".equals(skillname) && skill_harvesting_season>0){
            getPlayer().sendMessage(ChatColor.RED+"技能冷却中");
        }
    }

    public void setSkill_magicseed(int skill_magicseed) {
        this.skill_magicseed = skill_magicseed;
        updateheroinfo();
    }

    public int isSkill_harvesting_season() {
        return skill_harvesting_season;
    }

    public void setSkill_harvesting_season(int skill_harvesting_season) {
        this.skill_harvesting_season = skill_harvesting_season;
        updateheroinfo();
    }
    public void updateheroinfo(){
        heroinfo.unregister();
        heroinfo=heroboard.registerNewObjective("heroinfo","dummy","英雄信息");
        heroinfo.setDisplayName("英雄信息");
        heroinfo.setDisplaySlot(DisplaySlot.SIDEBAR);
        skill_magicseed_cold=heroinfo.getScore("魔种"+skill_magicseed);
        skill_magicseed_cold.setScore(1);
        skill_harvest_season_cold=heroinfo.getScore("收割季节"+skill_harvesting_season);
        skill_harvest_season_cold.setScore(2);
        heroinfo.getScore("基础攻击力 "+getAttack()+"(+"+getAttackadd()+")").setScore(3);
        heroinfo.getScore("生命值    "+getHp()).setScore(4);
    }
}
