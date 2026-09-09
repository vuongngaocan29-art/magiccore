package vn.aetheria.magiccore.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import vn.aetheria.magiccore.MagicCore;
import vn.aetheria.magiccore.data.PlayerMagicData;
import vn.aetheria.magiccore.util.WandItem;

public class PlayerConnectionListener implements Listener {

    private final MagicCore plugin;

    public PlayerConnectionListener(MagicCore plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        PlayerMagicData data = plugin.getDataManager().load(player.getUniqueId());

        if (!data.hasReceivedStarterWand()) {
            player.getInventory().addItem(WandItem.create(plugin));
            data.setReceivedStarterWand(true);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        plugin.getDataManager().unload(event.getPlayer().getUniqueId());
    }
}
