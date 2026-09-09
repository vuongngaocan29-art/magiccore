package vn.aetheria.magiccore.spell.impl;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.RayTraceResult;
import vn.aetheria.magiccore.MagicCore;
import vn.aetheria.magiccore.spell.Element;
import vn.aetheria.magiccore.spell.Spell;

import java.util.Collection;

public class ThunderfallSpell extends Spell {

    private static final double AOE_RADIUS = 4.0;

    public ThunderfallSpell() {
        super("thunderfall", "Thunderfall", "Gọi sét xuống một khu vực, gây damage diện rộng.",
                30, 15, 20, Element.LIGHTNING, 30);
    }

    @Override
    public void cast(MagicCore plugin, Player caster) {
        RayTraceResult result = caster.getWorld().rayTraceBlocks(
                caster.getEyeLocation(), caster.getEyeLocation().getDirection(), getRange());

        Location strikeLoc = (result != null)
                ? result.getHitPosition().toLocation(caster.getWorld())
                : caster.getEyeLocation().add(caster.getEyeLocation().getDirection().multiply(getRange()));

        caster.getWorld().strikeLightningEffect(strikeLoc); // hiệu ứng, không gây damage tự nhiên

        Collection<org.bukkit.entity.Entity> nearby = strikeLoc.getWorld().getNearbyEntities(strikeLoc, AOE_RADIUS, AOE_RADIUS, AOE_RADIUS);
        for (org.bukkit.entity.Entity entity : nearby) {
            if (entity instanceof LivingEntity living && !entity.equals(caster)) {
                living.damage(9.0, caster);
            }
        }
    }
}
