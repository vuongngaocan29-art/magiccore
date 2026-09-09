package vn.aetheria.magiccore.util;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import vn.aetheria.magiccore.MagicCore;

import java.util.List;

/**
 * Đũa Phép Aetheria — 1 item duy nhất dùng để cast mọi spell.
 * Chuột trái: đổi phép đang chọn. Chuột phải: cast phép đang chọn.
 * Nhận diện bằng PersistentDataContainer, không dùng tên/lore để tránh giả mạo.
 */
public final class WandItem {

    private static NamespacedKey wandKey(MagicCore plugin) {
        return new NamespacedKey(plugin, "wand");
    }

    public static ItemStack create(MagicCore plugin) {
        ItemStack item = new ItemStack(Material.BLAZE_ROD);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.LIGHT_PURPLE + "🪄 Đũa Phép Aetheria");
        meta.setLore(List.of(
                ChatColor.GRAY + "Chuột trái: đổi phép đang chọn",
                ChatColor.GRAY + "Chuột phải: dùng phép đang chọn",
                ChatColor.GRAY + "Xem /magic để biết phép đang chọn"
        ));
        meta.addEnchant(Enchantment.DURABILITY, 1, true); // chỉ để có ánh sáng "enchanted glint"
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES);
        meta.getPersistentDataContainer().set(wandKey(plugin), PersistentDataType.BYTE, (byte) 1);

        item.setItemMeta(meta);
        return item;
    }

    public static boolean isWand(MagicCore plugin, ItemStack item) {
        if (item == null || item.getType().isAir() || !item.hasItemMeta()) {
            return false;
        }
        Byte value = item.getItemMeta().getPersistentDataContainer()
                .get(wandKey(plugin), PersistentDataType.BYTE);
        return value != null && value == (byte) 1;
    }

    private WandItem() {
    }
}
