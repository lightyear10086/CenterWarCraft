package rarityeg.HoofPower.hero;


import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_16_R3.scoreboard.CraftScoreboardManager;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import rarityeg.HoofPower.HoofPower;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

//英雄基类
public class HeroBase {
    private Player player;



    private String heroname;

    private float hp;
    private float armor;
    private float moveSpeed;
    private float lifeRegain;
    private float attack;
    private float destroySpeed;
    private float rebornTime;
    private float skillTime;
    private HeroEventListener heroEventListener;
    private ScoreboardManager heroinfo;
    private float attackadd;//攻击力额外增加的值

    public Inventory getSkillbar() {
        return skillbar;
    }

    private Inventory skillbar;//技能栏，实际是物品栏

    public HeroBase(Player player){
        this.player = player;
        this.skillbar=player.getInventory();
    }

    public Player getPlayer() {
        return player;
    }

    public String getHeroname() {
        return heroname;
    }

    public void setHeroname(String heroname) {
        this.heroname = heroname;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public float getHp() {
        return hp;
    }

    public void giveitemtoplayer(ItemStack itemStack, int pos, int amount, String itemname, String[] loreinfo){
        PlayerInventory playerInventory=player.getInventory();
        itemStack.setAmount(amount);
        ItemMeta itemMeta=itemStack.getItemMeta();
        itemMeta.setLore(Arrays.asList(loreinfo.clone()));
        itemMeta.setDisplayName(itemname);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemStack.setItemMeta(itemMeta);
        playerInventory.setItem(pos,itemStack);
    }

    public void giveitemtoplayer(ItemStack itemStack,int amount, String itemname, String[] loreinfo){
        PlayerInventory playerInventory=player.getInventory();
        itemStack.setAmount(amount);
        ItemMeta itemMeta=itemStack.getItemMeta();
        itemMeta.setLore(Arrays.asList(loreinfo.clone()));
        itemMeta.setDisplayName(itemname);
        itemStack.setItemMeta(itemMeta);
        playerInventory.addItem(itemStack);
    }

    public void setHp(float hp) {
        this.hp = hp;
        player.setHealth(hp);
    }

    public float getArmor() {
        return armor;
    }

    public void setArmor(float armor) {
        this.armor = armor;

    }

    public float getMoveSpeed() {
        return moveSpeed;
    }

    public void setMoveSpeed(float moveSpeed) {
        this.moveSpeed = moveSpeed;
        player.setWalkSpeed(moveSpeed);
    }

    public float getLifeRegain() {
        return lifeRegain;
    }

    public void setLifeRegain(float lifeRegain) {
        this.lifeRegain = lifeRegain;
    }

    public float getAttack() {
        return attack;
    }

    public void setAttack(float attack) {
        this.attack = attack;
    }

    public float getDestroySpeed() {
        return destroySpeed;
    }

    public void setDestroySpeed(float destroySpeed) {
        this.destroySpeed = destroySpeed;
    }

    public float getRebornTime() {
        return rebornTime;
    }

    public void setRebornTime(float rebornTime) {
        this.rebornTime = rebornTime;
    }

    public float getSkillTime() {
        return skillTime;
    }

    public void setSkillTime(float skillTime) {
        this.skillTime = skillTime;
    }

    public HeroEventListener getHeroEventListener(){return new HeroEventListener(this);}

    public void useSkill(int i){

    }

    public ScoreboardManager getHeroinfo() {
        return heroinfo;
    }

    public void setHeroinfo(ScoreboardManager heroinfo) {
        this.heroinfo = heroinfo;
    }

    public float getAttackadd() {
        return attackadd;
    }

    public void setAttackadd(float attackadd) {
        this.attackadd = attackadd;
    }
}
