package vn.aetheria.magiccore.spell.impl;

import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import vn.aetheria.magiccore.MagicCore;
import vn.aetheria.magiccore.spell.Element;
import vn.aetheria.magiccore.spell.Spell;

public class WindDashSpell extends Spell {

    public WindDashSpell() {
        super("wind_dash", "Wind Dash", "Lao nhanh về phía trước.",
                12, 10, 0, Element.ARCANE, 10);
    }

    @Override
    public void cast(MagicCore plugin, Player caster) {
        Vector direction = caster.getLocation().getDirection().normalize();
        caster.setVelocity(direction.multiply(1.8).setY(0.3));
        caster.getWorld().spawnParticle(Particle.CLOUD, caster.getLocation(), 20, 0.3, 0.1, 0.3, 0.05);
        caster.getWorld().playSound(caster.getLocation(), Sound.ENTITY_ENDER_DRAGON_FLAP, 0.6f, 1.6f);
    }
}
