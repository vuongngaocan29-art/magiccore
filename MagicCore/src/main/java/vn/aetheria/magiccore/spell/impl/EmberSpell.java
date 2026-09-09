package vn.aetheria.magiccore.spell.impl;

import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import vn.aetheria.magiccore.MagicCore;
import vn.aetheria.magiccore.spell.Element;
import vn.aetheria.magiccore.spell.Spell;
import vn.aetheria.magiccore.util.SpellRay;

public class EmberSpell extends Spell {

    public EmberSpell() {
        super("ember", "Ember", "Bắn một quả cầu lửa nhỏ.",
                8, 2, 15, Element.FIRE, 1);
    }

    @Override
    public void cast(MagicCore plugin, Player caster) {
        caster.getWorld().playSound(caster.getLocation(), Sound.ENTITY_BLAZE_SHOOT, 1f, 1.2f);
        LivingEntity target = SpellRay.fireBoltAndGetTarget(caster, getRange(), Particle.FLAME);
        if (target != null) {
            target.damage(4.0, caster);
            target.setFireTicks(40);
            target.getWorld().spawnParticle(Particle.LAVA, target.getLocation().add(0, 1, 0), 8, 0.3, 0.3, 0.3, 0);
        }
    }
}
