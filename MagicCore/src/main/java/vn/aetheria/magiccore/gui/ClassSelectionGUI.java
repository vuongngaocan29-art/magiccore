package vn.aetheria.magiccore.gui;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import vn.aetheria.magiccore.data.PlayerClass;

import java.util.List;

public final class ClassSelectionGUI {

    private ClassSelectionGUI() {
    }

    public static Inventory build() {
        MagicGUIHolder holder = new MagicGUIHolder(MagicGUIHolder.MenuType.CLASS_SELECT);
        Inventory inv = org.bukkit.Bukkit.createInventory(holder, 27,
                ChatColor.DARK_AQUA + "Chọn Lớp (Class) của bạn");
        holder.setInventory(inv);

        inv.setItem(10, buildIcon(PlayerClass.ARCANIST, Material.NETHER_STAR, "Phép thuật tổng hợp."));
        inv.setItem(11, buildIcon(PlayerClass.SPELLBLADE, Material.IRON_SWORD, "Cận chiến + phép."));
        inv.setItem(12, buildIcon(PlayerClass.DRUID, Material.OAK_LEAVES, "Nature magic."));
        inv.setItem(14, buildIcon(PlayerClass.ALCHEMIST, Material.BREWING_STAND, "Potion / hỗ trợ."));
        inv.setItem(15, buildIcon(PlayerClass.WARLOCK, Material.WITHER_ROSE, "Dark magic."));
        inv.setItem(16, buildIcon(PlayerClass.GUARDIAN, Material.SHIELD, "Tank / defensive magic."));

        return inv;
    }

    private static ItemStack buildIcon(PlayerClass playerClass, Material material, String desc) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(playerClass.getColoredName());
        meta.setLore(List.of(
                ChatColor.GRAY + desc,
                "",
                ChatColor.GREEN + "▶ Click để chọn"
        ));
        item.setItemMeta(meta);
        return item;
    }

    public static PlayerClass classFromSlot(int slot) {
        return switch (slot) {
            case 10 -> PlayerClass.ARCANIST;
            case 11 -> PlayerClass.SPELLBLADE;
            case 12 -> PlayerClass.DRUID;
            case 14 -> PlayerClass.ALCHEMIST;
            case 15 -> PlayerClass.WARLOCK;
            case 16 -> PlayerClass.GUARDIAN;
            default -> null;
        };
    }
}
