package vn.aetheria.magiccore.gui;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

/**
 * Holder đánh dấu một Inventory là GUI của MagicCore, kèm loại menu để
 * GUIListener biết cách xử lý click (chọn House hay chọn Class).
 */
public class MagicGUIHolder implements InventoryHolder {

    public enum MenuType {
        HOUSE_SELECT,
        CLASS_SELECT
    }

    private final MenuType type;
    private Inventory inventory;

    public MagicGUIHolder(MenuType type) {
        this.type = type;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    public MenuType getType() {
        return type;
    }
}
