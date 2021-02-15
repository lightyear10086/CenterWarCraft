package rarityeg.HoofPower.hero;


import org.bukkit.entity.Player;

//英雄基类
public class HeroBase {
    private Player player;

    private float hp;
    private float armor;
    private float moveSpeed;
    private float lifeRegain;
    private float attack;
    private float destroySpeed;
    private float rebornTime;
    private float skillTime;

    public HeroBase(Player player){
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public float getHp() {
        return hp;
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


}
