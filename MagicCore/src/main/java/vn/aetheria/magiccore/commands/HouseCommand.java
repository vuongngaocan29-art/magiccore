package vn.aetheria.magiccore.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import vn.aetheria.magiccore.MagicCore;
import vn.aetheria.magiccore.data.House;
import vn.aetheria.magiccore.data.PlayerMagicData;
import vn.aetheria.magiccore.gui.HouseSelectionGUI;

import java.util.Map;

public class HouseCommand implements CommandExecutor {

    private final MagicCore plugin;

    public HouseCommand(MagicCore plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Lệnh này chỉ dùng được trong game.");
            return true;
        }

        if (args.length == 0) {
            showInfo(player);
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "join" -> {
                PlayerMagicData data = plugin.getDataManager().load(player.getUniqueId());
                if (data.getHouse() != null) {
                    player.sendMessage(ChatColor.RED + "Bạn đã thuộc " + data.getHouse().getColoredName()
                            + ChatColor.RED + " rồi.");
                    return true;
                }
                player.openInventory(HouseSelectionGUI.build());
            }
            case "top" -> showTop(player);
            case "info" -> showInfo(player);
            default -> player.sendMessage(ChatColor.RED + "Dùng: /house <info|join|top>");
        }
        return true;
    }

    private void showInfo(Player player) {
        PlayerMagicData data = plugin.getDataManager().load(player.getUniqueId());
        if (data.getHouse() == null) {
            player.sendMessage(ChatColor.YELLOW + "Bạn chưa gia nhập Nhà nào. Dùng /house join để chọn!");
            return;
        }
        House house = data.getHouse();
        player.sendMessage(ChatColor.GRAY + "Bạn thuộc về " + house.getColoredName()
                + ChatColor.GRAY + " - Điểm hiện tại: " + ChatColor.GOLD
                + plugin.getHouseManager().getPoints(house));
    }

    private void showTop(Player player) {
        player.sendMessage(ChatColor.GOLD + "🏆 ===== House Cup =====");
        Map<House, Integer> leaderboard = plugin.getHouseManager().getLeaderboard();
        int rank = 1;
        for (Map.Entry<House, Integer> entry : leaderboard.entrySet()) {
            player.sendMessage(ChatColor.GRAY + "#" + rank + " " + entry.getKey().getColoredName()
                    + ChatColor.GRAY + " - " + ChatColor.GOLD + entry.getValue() + " điểm");
            rank++;
        }
    }
}
