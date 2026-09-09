package vn.aetheria.magiccore.spell.impl;

import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import vn.aetheria.magiccore.MagicCore;
import vn.aetheria.magiccore.spell.Element;
import vn.aetheria.magiccore.spell.Spell;
import vn.aetheria.magiccore.util.SpellRay;

public class FrostbindSpell extends Spell {

    public FrostbindSpell() {
        super("frostbind", "Frostbind", "Làm chậm mục tiêu.",
                14, 6, 12, Element.ICE, 10);
    }

    @Override
    public void cast(MagicCore plugin, Player caster) {
        caster.getWorld().playSound(caster.getLocation(), Sound.BLOCK_GLASS_BREAK, 1f, 0.7f);
        LivingEntity target = SpellRay.fireBoltAndGetTarget(caster, getRange(), Particle.SNOWFLAKE);
        if (target != null) {
            target.damage(2.0, caster);
            target.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 20 * 5, 2));
            target.getWorld().spawnParticle(Particle.SNOWFLAKE, target.getLocation().add(0, 1, 0), 15, 0.4, 0.4, 0.4, 0);
        }
    }
}
