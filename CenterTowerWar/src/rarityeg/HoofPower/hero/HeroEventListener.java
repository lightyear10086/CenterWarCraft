package rarityeg.HoofPower.hero;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerAnimationEvent;

public class HeroEventListener implements Listener {
    private HeroBase heroBase;
    public HeroEventListener(HeroBase heroBase){
        this.heroBase=heroBase;
    }
    @EventHandler
    public void beattacked(EntityDamageByEntityEvent e){

    }
}
