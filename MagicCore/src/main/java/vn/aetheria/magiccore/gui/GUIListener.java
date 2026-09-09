package vn.aetheria.magiccore.gui;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import vn.aetheria.magiccore.MagicCore;
import vn.aetheria.magiccore.data.House;
import vn.aetheria.magiccore.data.PlayerClass;
import vn.aetheria.magiccore.data.PlayerMagicData;

public class GUIListener implements Listener {

    private final MagicCore plugin;

    public GUIListener(MagicCore plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (!(event.getInventory().getHolder() instanceof MagicGUIHolder holder)) {
            return;
        }
        event.setCancelled(true);

        if (event.getClickedInventory() == null || !event.getClickedInventory().equals(event.getInventory())) {
            return;
        }
        if (event.getCurrentItem() == null || event.getCurrentItem().getType().isAir()) {
            return;
        }

        Player player = (Player) event.getWhoClicked();
        PlayerMagicData data = plugin.getDataManager().load(player.getUniqueId());
        int slot = event.getRawSlot();

        switch (holder.getType()) {
            case HOUSE_SELECT -> {
                if (data.getHouse() != null) {
                    player.sendMessage(ChatColor.RED + "Bạn đã thuộc về " + data.getHouse().getColoredName()
                            + ChatColor.RED + " rồi. Hãy hỏi Admin nếu muốn đổi Nhà.");
                    player.closeInventory();
                    return;
                }
                House house = HouseSelectionGUI.houseFromSlot(slot);
                if (house != null) {
                    data.setHouse(house);
                    player.sendMessage(ChatColor.GREEN + "Chúc mừng! Bạn đã gia nhập " + house.getColoredName());
                    player.closeInventory();
                }
            }
            case CLASS_SELECT -> {
                if (data.getPlayerClass() != null) {
                    player.sendMessage(ChatColor.RED + "Bạn đã chọn Class "
                            + data.getPlayerClass().getColoredName() + ChatColor.RED + " rồi.");
                    player.closeInventory();
                    return;
                }
                PlayerClass playerClass = ClassSelectionGUI.classFromSlot(slot);
                if (playerClass != null) {
                    data.setPlayerClass(playerClass);
                    player.sendMessage(ChatColor.GREEN + "Bạn đã trở thành " + playerClass.getColoredName());
                    player.closeInventory();
                }
            }
        }
    }
}
