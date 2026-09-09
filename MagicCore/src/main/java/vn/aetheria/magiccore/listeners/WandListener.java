package vn.aetheria.magiccore.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import vn.aetheria.magiccore.MagicCore;
import vn.aetheria.magiccore.data.PlayerMagicData;
import vn.aetheria.magiccore.manager.SpellManager;
import vn.aetheria.magiccore.spell.Spell;
import vn.aetheria.magiccore.util.WandItem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class WandListener implements Listener {

    private final MagicCore plugin;
    // chống spam click liên tục (tính bằng millis)
    private final Map<UUID, Long> lastClick = new HashMap<>();
    private static final long CLICK_COOLDOWN_MS = 200;

    public WandListener(MagicCore plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.getHand() != EquipmentSlot.HAND) return; // tránh xử lý 2 lần (main hand + off hand)

        ItemStack item = event.getItem();
        if (!WandItem.isWand(plugin, item)) return;

        Player player = event.getPlayer();

        switch (event.getAction()) {
            case LEFT_CLICK_AIR, LEFT_CLICK_BLOCK -> {
                event.setCancelled(true); // không cho phá block bằng đũa
                if (isThrottled(player)) return;
                cycleSpell(player);
            }
            case RIGHT_CLICK_AIR, RIGHT_CLICK_BLOCK -> {
                event.setCancelled(true); // không cho tương tác block (mở cửa, v.v.) bằng đũa
                if (isThrottled(player)) return;
                castActiveSpell(player);
            }
            default -> {
            }
        }
    }

    private boolean isThrottled(Player player) {
        long now = System.currentTimeMillis();
        Long last = lastClick.get(player.getUniqueId());
        if (last != null && now - last < CLICK_COOLDOWN_MS) {
            return true;
        }
        lastClick.put(player.getUniqueId(), now);
        return false;
    }

    private void cycleSpell(Player player) {
        PlayerMagicData data = plugin.getDataManager().load(player.getUniqueId());
        List<Spell> available = plugin.getSpellManager().getAvailableSpells(data);

        if (available.isEmpty()) {
            player.sendMessage(ChatColor.RED + "Bạn chưa mở khoá phép nào (cần Magic Level cao hơn).");
            return;
        }

        int currentIndex = -1;
        for (int i = 0; i < available.size(); i++) {
            if (available.get(i).getId().equals(data.getActiveSpellId())) {
                currentIndex = i;
                break;
            }
        }

        int nextIndex = (currentIndex + 1) % available.size();
        Spell next = available.get(nextIndex);
        data.setActiveSpellId(next.getId());

        player.sendActionBar(net.kyori.adventure.text.Component.text(
                "» Đã chọn: " + next.getElement().getIcon() + " " + next.getDisplayName(),
                net.kyori.adventure.text.format.NamedTextColor.LIGHT_PURPLE));
        player.playSound(player.getLocation(), org.bukkit.Sound.UI_BUTTON_CLICK, 0.6f, 1.4f);
    }

    private void castActiveSpell(Player player) {
        PlayerMagicData data = plugin.getDataManager().load(player.getUniqueId());
        String spellId = data.getActiveSpellId();

        if (spellId == null) {
            List<Spell> available = plugin.getSpellManager().getAvailableSpells(data);
            if (available.isEmpty()) {
                player.sendMessage(ChatColor.RED + "Bạn chưa mở khoá phép nào.");
                return;
            }
            spellId = available.get(0).getId();
            data.setActiveSpellId(spellId);
        }

        SpellManager.CastResult result = plugin.getSpellManager().cast(player, spellId);
        switch (result) {
            case SUCCESS -> {
                // hiệu ứng đã tự hiện qua Spell.cast(), không spam chat
            }
            case NOT_ENOUGH_MANA -> player.sendMessage(ChatColor.RED + "🔮 Không đủ Mana!");
            case ON_COOLDOWN -> {
                long remaining = data.getRemainingCooldownSeconds(spellId);
                player.sendMessage(ChatColor.RED + "⏳ Phép đang hồi chiêu (" + remaining + "s).");
            }
            case LEVEL_TOO_LOW -> player.sendMessage(ChatColor.RED + "Magic Level chưa đủ để dùng phép này.");
            case UNKNOWN_SPELL -> player.sendMessage(ChatColor.RED + "Phép đang chọn không hợp lệ, thử đổi phép khác.");
        }
    }
}
