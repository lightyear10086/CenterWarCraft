package rarityeg.HoofPower;

import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class EventListener implements Listener{
    public static final Random RANDOM=new Random();
    public List<Entity> BeAimed= new ArrayList<>();

    //提交代码2
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
            if(clickedItem.getItemMeta().getDisplayName().equals(Menu.HERO_FARMER)){
                player.closeInventory();
                HoofPower.playerhero="farmer";
                ItemStack hoe=new ItemStack(Material.DIAMOND_HOE);
                ItemMeta hoeMeta=hoe.getItemMeta();
                hoeMeta.setDisplayName("魔种攫取");
                hoeMeta.setLore(Arrays.asList("使用此锄头平A敌对单位时获得1个种子，最多32个","获得的种子可以种在草方块上，在1min后成熟为小麦","我方英雄可收获小麦，在合成台处合成面包，食用面包可回复3/5/8点生命"));
                hoe.setItemMeta(hoeMeta);
                Inventory playerinventory=player.getInventory();
                playerinventory.addItem(hoe);

                GivePlayerItem(player,new ItemStack(Material.GOLDEN_HOE),"收割季节",new String[]{"对所有被魔种标记的目标造成3点伤害"});
            }
        }
    }
}
