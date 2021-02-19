package rarityeg.HoofPower;

import com.google.common.eventbus.Subscribe;
import net.minecraft.server.v1_16_R3.Block;
import net.minecraft.server.v1_16_R3.Blocks;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import rarityeg.HoofPower.hero.HeroBase;
import rarityeg.HoofPower.hero.WildFarmer;

import java.util.*;

public class EventListener implements Listener{
    public static final Random RANDOM=new Random();
    public List<Entity> BeAimed= new ArrayList<>();


    //当玩家位于草丛中时对敌对队伍所有玩家隐身
    @EventHandler
    public void Hide(PlayerMoveEvent e){
        Player player=e.getPlayer();
        if(player.getLocation().getBlock().getType()==Material.GRASS){
            if(HoofPower.hpinstance.ruinerplayerlist.contains(player)){
                HoofPower.hpinstance.builderplayerlist.forEach((enamys)->{
                    enamys.hidePlayer(HoofPower.instance,player);
                });
            }
            if(HoofPower.hpinstance.builderplayerlist.contains(player)){
                HoofPower.hpinstance.ruinerplayerlist.forEach((enamys)->{
                    enamys.hidePlayer(HoofPower.instance,player);
                });
            }
        }else{
            if(HoofPower.hpinstance.ruinerplayerlist.contains(player)){
                HoofPower.hpinstance.builderplayerlist.forEach((enamys)->{
                    enamys.showPlayer(HoofPower.instance,player);
                });
            }
            if(HoofPower.hpinstance.builderplayerlist.contains(player)){
                HoofPower.hpinstance.ruinerplayerlist.forEach((enamys)->{
                    enamys.showPlayer(HoofPower.instance,player);
                });
            }
        }
    }
    //提交代码23
    public void GivePlayerItem(Player player,ItemStack itemStack,String name,String[] info){
        ItemStack hoe=itemStack;
        ItemMeta hoeMeta=hoe.getItemMeta();
        hoeMeta.setDisplayName(name);
        hoeMeta.setLore(Arrays.asList(info));
        hoe.setItemMeta(hoeMeta);
        Inventory playerinventory=player.getInventory();
        playerinventory.addItem(hoe);
    }

    public void Loginfo(String infocontent){
        HoofPower.instance.getLogger().info(infocontent);
    }

    /*
    @EventHandler
    public void onAttack(PlayerInteractEntityEvent e){
        Player player= e.getPlayer();
        if(HoofPower.playerhero=="farmer"){
            player.setHealth((double) 10);
            ItemStack magicseed=new ItemStack(Material.WHEAT_SEEDS);
            PlayerInventory playerinventory=e.getPlayer().getInventory();

            String skillname=playerinventory.getItemInMainHand().getItemMeta().getDisplayName();

            if("魔种攫取".equals(skillname)){
                GivePlayerItem(player,magicseed,"魔种",new String[]{"可种在目标身上标记目标"});
                return;
            }
            if("魔种".equals(skillname)){
                if(!BeAimed.contains(e.getRightClicked())){
                    BeAimed.add(e.getRightClicked());
                    e.getPlayer().getInventory().getItemInMainHand().setAmount(e.getPlayer().getInventory().getItemInMainHand().getAmount()-1);
                    Loginfo(BeAimed.size()+"");
                    return;
                }

            }
            if("收割季节".equals(skillname)){
                if(!BeAimed.isEmpty()){
                    BeAimed.forEach((aim)->{
                        //对aim造成伤害
                        if(aim instanceof Damageable){
                            ((Damageable)aim).damage(2);
                        }
                    });
                    BeAimed.clear();
                }
                return;
            }
        }
        return;
    }
    */
    @EventHandler
    public void onClick(InventoryClickEvent e){
        Player player= (Player)e.getWhoClicked();
        InventoryView inv=player.getOpenInventory();

        if(inv.getTitle().equals(Menu.TITLE)){
            e.setCancelled(true);
            if(e.getRawSlot()<0 || e.getRawSlot()>e.getInventory().getSize()){
                return;
            }
            ItemStack clickedItem=e.getCurrentItem();
            if(clickedItem==null){
                return;
            }
            //...
            /*
            if(clickedItem.getItemMeta().getDisplayName().equals(Menu.QUIT_SERVER)){
                player.kickPlayer("您已离开服务器");
                return;
            }
            if(clickedItem.getItemMeta().getDisplayName().equals(Menu.TELEPORT)){
                player.closeInventory();
                World playerWorld= Bukkit.getWorld("world");
                double randX=RANDOM.nextInt(200000)-100000;
                double randZ=RANDOM.nextInt(200000)-100000;
                Location offset=new Location(playerWorld,randX,0,randZ);

                player.teleport(offset);
                player.sendMessage(ChatColor.GREEN+"传送成功");
                return;

            }
            if(clickedItem.getItemMeta().getDisplayName().equals(Menu.SHOW_ANNOUNCEMENT)){
                ItemStack ann=new ItemStack(Material.WRITTEN_BOOK);
                BookMeta annBm=(BookMeta)ann.getItemMeta();
                String[] acText=Objects.requireNonNullElse(HoofPower.instance.getConfig().getString("announcement"),"").split("\\+\\+\\+");
                    annBm.setPages(acText);
                    annBm.setAuthor("HoofPower");
                    annBm.setTitle("公告");
                    ann.setItemMeta(annBm);
                    player.openBook(ann);
                return;
            }
            */
            if(clickedItem.getItemMeta().getDisplayName().equals(Menu.HERO_FARMER)){
                HoofPower.hpinstance.putHeroMap(player.getName(), new WildFarmer(player));
                player.closeInventory();
            }
        }
    }
}
