package vn.aetheria.magiccore.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import vn.aetheria.magiccore.MagicCore;
import vn.aetheria.magiccore.data.House;
import vn.aetheria.magiccore.data.PlayerMagicData;
import vn.aetheria.magiccore.util.WandItem;

public class MagicAdminCommand implements CommandExecutor {

    private final MagicCore plugin;

    public MagicAdminCommand(MagicCore plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("magiccore.admin")) {
            sender.sendMessage(ChatColor.RED + "Bạn không có quyền dùng lệnh này.");
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "Dùng: /magicadmin <housepoints|setlevel|setmana> ...");
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "housepoints" -> handleHousePoints(sender, args);
            case "setlevel" -> handleSetLevel(sender, args);
            case "setmana" -> handleSetMana(sender, args);
            case "givewand" -> handleGiveWand(sender, args);
            default -> sender.sendMessage(ChatColor.RED + "Dùng: /magicadmin <housepoints|setlevel|setmana> ...");
        }
        return true;
    }

    // /magicadmin housepoints <house> <add|set> <amount>
    private void handleHousePoints(CommandSender sender, String[] args) {
        if (args.length < 4) {
            sender.sendMessage(ChatColor.RED + "Dùng: /magicadmin housepoints <house> <add|set> <amount>");
            return;
        }
        House house;
        try {
            house = House.valueOf(args[1].toUpperCase());
        } catch (IllegalArgumentException e) {
            sender.sendMessage(ChatColor.RED + "Nhà không hợp lệ. Chọn: IGNIS, NOCTIS, SILVA, AETHER.");
            return;
        }

        int amount;
        try {
            amount = Integer.parseInt(args[3]);
        } catch (NumberFormatException e) {
            sender.sendMessage(ChatColor.RED + "Số điểm không hợp lệ.");
            return;
        }

        if (args[2].equalsIgnoreCase("add")) {
            plugin.getHouseManager().addPoints(house, amount);
        } else if (args[2].equalsIgnoreCase("set")) {
            plugin.getHouseManager().setPoints(house, amount);
        } else {
            sender.sendMessage(ChatColor.RED + "Dùng add hoặc set.");
            return;
        }
        sender.sendMessage(ChatColor.GREEN + "Đã cập nhật điểm " + house.getColoredName()
                + ChatColor.GREEN + " -> " + plugin.getHouseManager().getPoints(house));
    }

    // /magicadmin setlevel <player> <level>
    private void handleSetLevel(CommandSender sender, String[] args) {
        if (args.length < 3) {
            sender.sendMessage(ChatColor.RED + "Dùng: /magicadmin setlevel <player> <level>");
            return;
        }
        OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
        PlayerMagicData data = plugin.getDataManager().load(target.getUniqueId());
        try {
            int level = Integer.parseInt(args[2]);
            data.setMagicLevel(level);
            data.setMaxMana(plugin.getManaManager().calculateMaxMana(level));
            sender.sendMessage(ChatColor.GREEN + "Đã đặt Magic Level của " + args[1] + " thành " + level);
        } catch (NumberFormatException e) {
            sender.sendMessage(ChatColor.RED + "Level không hợp lệ.");
        }
    }

    // /magicadmin givewand <player>
    private void handleGiveWand(CommandSender sender, String[] args) {
        if (args.length < 2) {
            sender.sendMessage(ChatColor.RED + "Dùng: /magicadmin givewand <player>");
            return;
        }
        Player target = Bukkit.getPlayerExact(args[1]);
        if (target == null) {
            sender.sendMessage(ChatColor.RED + "Người chơi không online.");
            return;
        }
        target.getInventory().addItem(WandItem.create(plugin));
        sender.sendMessage(ChatColor.GREEN + "Đã đưa Đũa Phép cho " + target.getName());
    }

    // /magicadmin setmana <player> <amount>
    private void handleSetMana(CommandSender sender, String[] args) {
        if (args.length < 3) {
            sender.sendMessage(ChatColor.RED + "Dùng: /magicadmin setmana <player> <amount>");
            return;
        }
        OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
        PlayerMagicData data = plugin.getDataManager().load(target.getUniqueId());
        try {
            double amount = Double.parseDouble(args[2]);
            data.setMana(amount);
            sender.sendMessage(ChatColor.GREEN + "Đã đặt Mana của " + args[1] + " thành " + amount);
        } catch (NumberFormatException e) {
            sender.sendMessage(ChatColor.RED + "Giá trị không hợp lệ.");
        }
    }
}
