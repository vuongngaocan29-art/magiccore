package vn.aetheria.magiccore.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import vn.aetheria.magiccore.MagicCore;
import vn.aetheria.magiccore.data.PlayerMagicData;
import vn.aetheria.magiccore.manager.SpellManager;
import vn.aetheria.magiccore.spell.Spell;

import java.util.List;

public class SpellCommand implements CommandExecutor {

    private final MagicCore plugin;

    public SpellCommand(MagicCore plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Lệnh này chỉ dùng được trong game.");
            return true;
        }

        if (args.length == 0) {
            player.sendMessage(ChatColor.RED + "Dùng: /spell <list|cast <tên>>");
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "list" -> listSpells(player);
            case "cast" -> {
                if (args.length < 2) {
                    player.sendMessage(ChatColor.RED + "Dùng: /spell cast <tên_phép>");
                    return true;
                }
                castSpell(player, args[1]);
            }
            default -> player.sendMessage(ChatColor.RED + "Dùng: /spell <list|cast <tên>>");
        }
        return true;
    }

    private void listSpells(Player player) {
        PlayerMagicData data = plugin.getDataManager().load(player.getUniqueId());
        player.sendMessage(ChatColor.DARK_PURPLE + "📚 ===== Sách Phép =====");

        List<Spell> all = plugin.getSpellManager().getAll();
        for (Spell spell : all) {
            boolean unlocked = data.getMagicLevel() >= spell.getLevelRequirement();
            String prefix = unlocked ? ChatColor.GREEN + "✓ " : ChatColor.DARK_GRAY + "✗ ";
            player.sendMessage(prefix + spell.getFormattedLine()
                    + (unlocked ? "" : ChatColor.RED + "  [Khoá]"));
        }
        player.sendMessage(ChatColor.GRAY + "Dùng /spell cast <id> để sử dụng, vd: /spell cast ember");
    }

    private void castSpell(Player player, String spellId) {
        SpellManager.CastResult result = plugin.getSpellManager().cast(player, spellId);

        switch (result) {
            case SUCCESS -> {
                // Message hiển thị qua particle/sound trong Spell.cast(), không spam chat
            }
            case UNKNOWN_SPELL -> player.sendMessage(ChatColor.RED + "Không tìm thấy phép \"" + spellId + "\".");
            case LEVEL_TOO_LOW -> player.sendMessage(ChatColor.RED + "Magic Level của bạn chưa đủ để dùng phép này.");
            case NOT_ENOUGH_MANA -> player.sendMessage(ChatColor.RED + "🔮 Không đủ Mana!");
            case ON_COOLDOWN -> {
                PlayerMagicData data = plugin.getDataManager().load(player.getUniqueId());
                long remaining = data.getRemainingCooldownSeconds(spellId.toLowerCase());
                player.sendMessage(ChatColor.RED + "⏳ Phép đang hồi chiêu (" + remaining + "s).");
            }
        }
    }
}
