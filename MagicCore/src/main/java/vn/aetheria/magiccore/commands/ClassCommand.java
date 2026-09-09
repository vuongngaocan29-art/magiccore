package vn.aetheria.magiccore.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import vn.aetheria.magiccore.MagicCore;
import vn.aetheria.magiccore.data.PlayerMagicData;
import vn.aetheria.magiccore.gui.ClassSelectionGUI;

public class ClassCommand implements CommandExecutor {

    private final MagicCore plugin;

    public ClassCommand(MagicCore plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Lệnh này chỉ dùng được trong game.");
            return true;
        }

        PlayerMagicData data = plugin.getDataManager().load(player.getUniqueId());

        if (args.length == 0 || args[0].equalsIgnoreCase("info")) {
            if (data.getPlayerClass() == null) {
                player.sendMessage(ChatColor.YELLOW + "Bạn chưa chọn Class. Dùng /class choose để chọn!");
            } else {
                player.sendMessage(ChatColor.GRAY + "Class của bạn: " + data.getPlayerClass().getColoredName());
            }
            return true;
        }

        if (args[0].equalsIgnoreCase("choose")) {
            if (data.getPlayerClass() != null) {
                player.sendMessage(ChatColor.RED + "Bạn đã chọn Class " + data.getPlayerClass().getColoredName()
                        + ChatColor.RED + " rồi.");
                return true;
            }
            player.openInventory(ClassSelectionGUI.build());
            return true;
        }

        player.sendMessage(ChatColor.RED + "Dùng: /class <info|choose>");
        return true;
    }
}
