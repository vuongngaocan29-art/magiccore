package vn.aetheria.magiccore.gui;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import vn.aetheria.magiccore.data.House;

import java.util.List;

public final class HouseSelectionGUI {

    private HouseSelectionGUI() {
    }

    public static Inventory build() {
        MagicGUIHolder holder = new MagicGUIHolder(MagicGUIHolder.MenuType.HOUSE_SELECT);
        Inventory inv = org.bukkit.Bukkit.createInventory(holder, 27,
                ChatColor.DARK_PURPLE + "Chọn Nhà của bạn");
        holder.setInventory(inv);

        inv.setItem(11, buildIcon(House.IGNIS, Material.BLAZE_POWDER,
                "Dũng cảm - Chiến đấu - Quyết đoán", "Bonus: ⚔ Combat"));
        inv.setItem(13, buildIcon(House.NOCTIS, Material.FEATHER,
                "Bí ẩn - Tham vọng - Nghiên cứu phép thuật", "Bonus: 🔮 Dark Magic"));
        inv.setItem(15, buildIcon(House.SILVA, Material.OAK_SAPLING,
                "Thiên nhiên - Sinh vật - Chữa trị", "Bonus: 🌿 Healing / Nature"));
        inv.setItem(17, buildIcon(House.AETHER, Material.ENDER_EYE,
                "Trí tuệ - Khám phá - Nghiên cứu", "Bonus: ✨ Utility / Knowledge"));

        return inv;
    }

    private static ItemStack buildIcon(House house, Material material, String desc, String bonus) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(house.getColoredName());
        meta.setLore(List.of(
                ChatColor.GRAY + "Biểu tượng: " + house.getSymbolAnimal(),
                ChatColor.GRAY + desc,
                ChatColor.YELLOW + bonus,
                "",
                ChatColor.GREEN + "▶ Click để gia nhập"
        ));
        item.setItemMeta(meta);
        return item;
    }

    /** Xác định House từ slot được click (theo layout ở build()). */
    public static House houseFromSlot(int slot) {
        return switch (slot) {
            case 11 -> House.IGNIS;
            case 13 -> House.NOCTIS;
            case 15 -> House.SILVA;
            case 17 -> House.AETHER;
            default -> null;
        };
    }
}
