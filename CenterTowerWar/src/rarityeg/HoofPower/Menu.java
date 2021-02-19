package rarityeg.HoofPower;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.security.auth.login.LoginContext;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Menu {
    public Inventory components;
    public Player owner;
    public static final String TITLE="选择你的英雄";

    public static final String QUIT_SERVER="退出服务器";

    public static final String SHOW_ANNOUNCEMENT=ChatColor.GOLD+"显示公告";
    public static final String TELEPORT=ChatColor.GREEN+"随机传送";

    public static final String HERO_FARMER=ChatColor.GREEN+"荒野农夫·史蒂夫";

    public Menu(Player player){
        components= Bukkit.createInventory(player,9,TITLE);
        owner=player;

        //农夫史蒂夫信息
        ItemStack herofarmer=new ItemStack(Material.WHEAT_SEEDS);
        ItemMeta herofarmerMeta=herofarmer.getItemMeta();
        herofarmerMeta.setDisplayName(HERO_FARMER);
        List<String> Farminfosp=new ArrayList<String>();
        Farminfosp.add(ChatColor.BLUE+" "+ChatColor.BOLD+"\"种豆南山下，草盛豆苗稀。十步杀一人，千里不留行。\"");
        Farminfosp.add(ChatColor.GOLD+"----被动技能----");
        Farminfosp.add(ChatColor.GREEN+"五谷之灵");
        Farminfosp.add(ChatColor.WHITE+"  史蒂夫位于草丛中时，每3秒获得一个种子，最多8个");
        Farminfosp.add(ChatColor.GOLD+"----主动技能----");
        Farminfosp.add(ChatColor.GREEN+"魔种附身");
        Farminfosp.add(ChatColor.WHITE+"  史蒂夫将种子附着在敌人身上直到其死亡，被种子附身的目标被攻击时额外受到2伤害，且其死亡时荒野农夫在20秒内获得攻击力+3");
        Farminfosp.add(ChatColor.DARK_AQUA+"冷却：3秒");
        Farminfosp.add(ChatColor.GREEN+"收割季节");
        Farminfosp.add(ChatColor.WHITE+"  史蒂夫收割全场附着有种子的目标，对这些目标造成6伤害");
        Farminfosp.add(ChatColor.DARK_AQUA+"冷却：30秒");
        herofarmerMeta.setLore(Farminfosp);
        herofarmer.setItemMeta(herofarmerMeta);

        //深渊矿工史蒂夫信息
        ItemStack digstave=new ItemStack(Material.IRON_PICKAXE);
        ItemMeta digstaveMeta=digstave.getItemMeta();
        digstaveMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        digstaveMeta.setDisplayName("深渊矿工·史蒂夫");
        List<String> digstaveinfosp=new ArrayList<String>();
        digstaveinfosp.add(ChatColor.BLUE+" "+ChatColor.BOLD+"\"这里一片黑暗，但依旧比人心光明。\"");
        digstaveinfosp.add(ChatColor.GOLD+"----被动技能----");
        digstaveinfosp.add(ChatColor.GREEN+"掘金者");
        digstaveinfosp.add(ChatColor.WHITE+"  史蒂夫挖掘方块用时-1秒");
        digstaveinfosp.add(ChatColor.GOLD+"----主动技能----");
        digstaveinfosp.add(ChatColor.GREEN+"光明火把");
        digstaveinfosp.add(ChatColor.WHITE+"  史蒂夫在可放置火把的方块上放置可持续2分钟的火把，提供周围35格内的草丛视野，火把不可被敌方挖掘破坏，但可能被技能及流体破坏");
        digstaveinfosp.add(ChatColor.DARK_AQUA+"冷却：6秒");
        digstaveinfosp.add(ChatColor.GREEN+"背后诅咒");
        digstaveinfosp.add(ChatColor.WHITE+"  史蒂夫在身后召唤一只爬行者并在2秒后爆炸，对范围内所有单位造成10伤害");
        digstaveinfosp.add(ChatColor.DARK_AQUA+"冷却：50秒");
        digstaveMeta.setLore(digstaveinfosp);
        digstave.setItemMeta(digstaveMeta);

        //剑道史蒂夫信息
        ItemStack jiandao=new ItemStack(Material.GOLDEN_SWORD);
        ItemMeta jiandaoMeta=jiandao.getItemMeta();
        jiandaoMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        jiandaoMeta.setDisplayName("剑道·史蒂夫");
        List<String> jiandaoinfosp=new ArrayList<String>();
        jiandaoinfosp.add(ChatColor.BLUE+" "+ChatColor.BOLD+"\"剑为器，人为道。\"");
        jiandaoinfosp.add(ChatColor.GOLD+"----被动技能----");
        jiandaoinfosp.add(ChatColor.GREEN+"剑极");
        jiandaoinfosp.add(ChatColor.WHITE+"  剑道·史蒂夫击杀敌方英雄后在6秒内移速+2，若在此期间击杀其他英雄，获得额外2秒移速加成时间，并重置第一技能冷却");
        jiandaoinfosp.add(ChatColor.GOLD+"----主动技能----");
        jiandaoinfosp.add(ChatColor.GREEN+"观致");
        jiandaoinfosp.add(ChatColor.WHITE+"  剑道·史蒂夫进行一次高超的剑术攻击，100%对目标造成260%暴击伤害");
        jiandaoinfosp.add(ChatColor.DARK_AQUA+"冷却：15秒");
        jiandaoinfosp.add(ChatColor.GREEN+"血闻");
        jiandaoinfosp.add(ChatColor.WHITE+"  剑道·史蒂夫在20秒内造成的伤害会回复自身1/3伤害值的生命");
        jiandaoinfosp.add(ChatColor.DARK_AQUA+"冷却：10秒");
        jiandaoMeta.setLore(jiandaoinfosp);
        jiandao.setItemMeta(jiandaoMeta);

        components.setItem(0,herofarmer);
        components.setItem(1,digstave);
        components.setItem(2,jiandao);
    }

    public void open(){
        owner.openInventory(components);
    }
}
