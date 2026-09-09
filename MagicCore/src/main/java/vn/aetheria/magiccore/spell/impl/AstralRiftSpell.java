package vn.aetheria.magiccore.spell.impl;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import vn.aetheria.magiccore.MagicCore;
import vn.aetheria.magiccore.spell.Element;
import vn.aetheria.magiccore.spell.Spell;

import java.util.Collection;

/**
 * Ultimate spell - mở khoá ở Magic Level 50.
 * AoE lớn quanh người chơi, dùng làm "finisher" cho boss fight cuối mùa.
 */
public class AstralRiftSpell extends Spell {

    private static final double RADIUS = 6.0;

    public AstralRiftSpell() {
        super("astral_rift", "Astral Rift", "Ultimate spell - xé rách không gian quanh người chơi.",
                60, 60, 0, Element.ARCANE, 50);
    }

    @Override
    public void cast(MagicCore plugin, Player caster) {
        Location center = caster.getLocation();
        caster.getWorld().playSound(center, Sound.ENTITY_WITHER_SPAWN, 1f, 1.2f);
        caster.getWorld().spawnParticle(Particle.PORTAL, center.add(0, 1, 0), 200, 2, 1.5, 2, 0.5);
        caster.getWorld().spawnParticle(Particle.EXPLOSION_EMITTER, center, 3);

        Collection<Entity> nearby = center.getWorld().getNearbyEntities(center, RADIUS, RADIUS, RADIUS);
        for (Entity entity : nearby) {
            if (entity instanceof LivingEntity living && !entity.equals(caster)) {
                living.damage(18.0, caster);
                org.bukkit.util.Vector knockback = living.getLocation().toVector()
                        .subtract(center.toVector()).normalize().multiply(1.3).setY(0.4);
                living.setVelocity(knockback);
            }
        }
    }
}
