package vn.aetheria.magiccore.manager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import vn.aetheria.magiccore.MagicCore;
import vn.aetheria.magiccore.data.PlayerMagicData;

public class ManaManager {

    private final MagicCore plugin;
    private BukkitRunnable regenTask;

    public ManaManager(MagicCore plugin) {
        this.plugin = plugin;
    }

    public double calculateMaxMana(int magicLevel) {
        double base = plugin.getConfig().getDouble("mana.base-max", 50);
        double perLevel = plugin.getConfig().getDouble("mana.per-level", 5);
        return base + (perLevel * (magicLevel - 1));
    }

    public void startRegenTask() {
        double manaPerSecond = plugin.getConfig().getDouble("mana.regen-per-second", 1.5);
        double staminaPerSecond = plugin.getConfig().getDouble("stamina.regen-per-second", 4.0);

        regenTask = new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    PlayerMagicData data = plugin.getDataManager().get(player.getUniqueId());
                    if (data == null) continue;

                    if (data.getMana() < data.getMaxMana()) {
                        data.setMana(data.getMana() + manaPerSecond);
                    }
                    if (data.getStamina() < data.getMaxStamina()) {
                        data.setStamina(data.getStamina() + staminaPerSecond);
                    }
                }
            }
        };
        regenTask.runTaskTimer(plugin, 20L, 20L); // mỗi giây
    }

    public void stop() {
        if (regenTask != null) {
            regenTask.cancel();
        }
    }
}
