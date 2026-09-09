package vn.aetheria.magiccore.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import vn.aetheria.magiccore.MagicCore;
import vn.aetheria.magiccore.data.PlayerMagicData;

public class ActionBarTask extends BukkitRunnable {

    private final MagicCore plugin;

    public ActionBarTask(MagicCore plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            PlayerMagicData data = plugin.getDataManager().get(player.getUniqueId());
            if (data == null) continue;

            double hp = player.getHealth();
            double maxHp = player.getAttribute(Attribute.MAX_HEALTH).getValue();

            Component bar = Component.text("❤ ", NamedTextColor.RED)
                    .append(Component.text((int) hp + "/" + (int) maxHp + "  ", NamedTextColor.WHITE))
                    .append(Component.text("🔮 ", NamedTextColor.LIGHT_PURPLE))
                    .append(Component.text((int) data.getMana() + "/" + (int) data.getMaxMana() + "  ", NamedTextColor.WHITE))
                    .append(Component.text("⚡ ", NamedTextColor.YELLOW))
                    .append(Component.text((int) data.getStamina() + "/" + (int) data.getMaxStamina() + "  ", NamedTextColor.WHITE))
                    .append(Component.text("✨ Lv." + data.getMagicLevel(), NamedTextColor.AQUA));

            player.sendActionBar(bar);
        }
    }
}
