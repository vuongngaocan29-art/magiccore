package vn.aetheria.magiccore.commands;

import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import vn.aetheria.magiccore.MagicCore;
import vn.aetheria.magiccore.data.PlayerMagicData;

public class MagicCommand implements CommandExecutor {

    private final MagicCore plugin;

    public MagicCommand(MagicCore plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Lệnh này chỉ dùng được trong game.");
            return true;
        }

        PlayerMagicData data = plugin.getDataManager().load(player.getUniqueId());
        long xpNeeded = plugin.getSpellManager().xpToNextLevel(data.getMagicLevel());

        player.sendMessage(ChatColor.DARK_PURPLE + "✨ ===== Hồ sơ Phép thuật ✨");
        player.sendMessage(ChatColor.GRAY + "Người chơi: " + ChatColor.WHITE + player.getName());
        player.sendMessage(ChatColor.GRAY + "Nhà: " + (data.getHouse() != null ? data.getHouse().getColoredName() : ChatColor.RED + "Chưa chọn"));
        player.sendMessage(ChatColor.GRAY + "Lớp: " + (data.getPlayerClass() != null ? data.getPlayerClass().getColoredName() : ChatColor.RED + "Chưa chọn"));
        player.sendMessage(ChatColor.GRAY + "Năm học: " + ChatColor.WHITE + data.getYear());
        player.sendMessage(ChatColor.GRAY + "❤ HP: " + ChatColor.WHITE
                + (int) player.getHealth() + "/" + (int) player.getAttribute(Attribute.MAX_HEALTH).getValue());
        player.sendMessage(ChatColor.GRAY + "🔮 Mana: " + ChatColor.WHITE
                + (int) data.getMana() + "/" + (int) data.getMaxMana());
        player.sendMessage(ChatColor.GRAY + "⚡ Stamina: " + ChatColor.WHITE
                + (int) data.getStamina() + "/" + (int) data.getMaxStamina());
        player.sendMessage(ChatColor.GRAY + "✨ Magic Level: " + ChatColor.AQUA + data.getMagicLevel()
                + ChatColor.GRAY + "  (XP: " + data.getMagicXp() + "/" + xpNeeded + ")");

        return true;
    }
}
