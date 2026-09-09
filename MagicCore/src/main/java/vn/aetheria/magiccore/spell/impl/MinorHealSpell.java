package vn.aetheria.magiccore.spell.impl;

import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import vn.aetheria.magiccore.MagicCore;
import vn.aetheria.magiccore.spell.Element;
import vn.aetheria.magiccore.spell.Spell;

public class MinorHealSpell extends Spell {

    public MinorHealSpell() {
        super("minor_heal", "Minor Heal", "Hồi một lượng máu nhỏ cho bản thân.",
                10, 6, 0, Element.NATURE, 1);
    }

    @Override
    public void cast(MagicCore plugin, Player caster) {
        double maxHealth = caster.getAttribute(Attribute.MAX_HEALTH).getValue();
        caster.setHealth(Math.min(maxHealth, caster.getHealth() + 6));
        caster.getWorld().spawnParticle(Particle.HEART, caster.getLocation().add(0, 1.5, 0), 6, 0.4, 0.4, 0.4, 0);
        caster.getWorld().playSound(caster.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 0.5f, 2f);
    }
}
