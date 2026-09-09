package vn.aetheria.magiccore.spell.impl;

import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import vn.aetheria.magiccore.MagicCore;
import vn.aetheria.magiccore.spell.Element;
import vn.aetheria.magiccore.spell.Spell;

public class LuminaSpell extends Spell {

    public LuminaSpell() {
        super("lumina", "Lumina", "Tạo ra ánh sáng, cho Glowing hiệu ứng tạm thời quanh khu vực.",
                6, 8, 0, Element.LIGHT, 1);
    }

    @Override
    public void cast(MagicCore plugin, Player caster) {
        caster.getWorld().playSound(caster.getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 0.6f, 2f);
        caster.getWorld().spawnParticle(Particle.END_ROD, caster.getLocation().add(0, 1, 0), 40, 1, 1, 1, 0.02);
        caster.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 20 * 30, 0, true, false));
    }
}
