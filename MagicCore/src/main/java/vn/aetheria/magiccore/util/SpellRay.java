package vn.aetheria.magiccore.util;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

/**
 * Bắn một "tia" phép thuật đơn giản dọc theo hướng nhìn của người chơi,
 * spawn particle dọc đường đi và trả về entity đầu tiên bị trúng (nếu có).
 * Dùng chung cho các spell dạng chiếu (Ember, Frostbind, v.v.)
 */
public final class SpellRay {

    private SpellRay() {
    }

    public static LivingEntity fireBoltAndGetTarget(Player caster, double range, Particle particle) {
        Location eye = caster.getEyeLocation();
        Vector direction = eye.getDirection().normalize();

        RayTraceResult result = caster.getWorld().rayTraceEntities(
                eye, direction, range, 0.4,
                entity -> entity instanceof LivingEntity && !entity.equals(caster)
        );

        // Vẽ particle dọc đường đi (tới điểm trúng hoặc hết range)
        double distance = (result != null) ? eye.distance(result.getHitPosition().toLocation(caster.getWorld())) : range;
        for (double d = 0; d <= distance; d += 0.5) {
            Location point = eye.clone().add(direction.clone().multiply(d));
            caster.getWorld().spawnParticle(particle, point, 2, 0.05, 0.05, 0.05, 0.01);
        }

        if (result != null && result.getHitEntity() instanceof LivingEntity target) {
            return target;
        }
        return null;
    }
}
