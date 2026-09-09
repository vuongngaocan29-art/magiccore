package vn.aetheria.magiccore.manager;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import vn.aetheria.magiccore.MagicCore;
import vn.aetheria.magiccore.data.PlayerMagicData;
import vn.aetheria.magiccore.spell.Spell;
import vn.aetheria.magiccore.spell.impl.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SpellManager {

    private final MagicCore plugin;
    private final Map<String, Spell> spells = new LinkedHashMap<>();

    public SpellManager(MagicCore plugin) {
        this.plugin = plugin;
        registerDefaults();
    }

    private void registerDefaults() {
        register(new EmberSpell());
        register(new LuminaSpell());
        register(new MinorHealSpell());
        register(new FrostbindSpell());
        register(new WindDashSpell());
        register(new ThunderfallSpell());
        register(new AstralRiftSpell());
    }

    public void register(Spell spell) {
        spells.put(spell.getId(), spell);
    }

    public Spell get(String id) {
        return spells.get(id.toLowerCase());
    }

    public List<Spell> getAll() {
        return List.copyOf(spells.values());
    }

    /** Các spell mà player đã đủ Magic Level để dùng. */
    public List<Spell> getAvailableSpells(PlayerMagicData data) {
        return spells.values().stream()
                .filter(s -> s.getLevelRequirement() <= data.getMagicLevel())
                .collect(Collectors.toList());
    }

    public enum CastResult {
        SUCCESS, UNKNOWN_SPELL, LEVEL_TOO_LOW, NOT_ENOUGH_MANA, ON_COOLDOWN
    }

    public CastResult cast(Player player, String spellId) {
        Spell spell = get(spellId);
        if (spell == null) return CastResult.UNKNOWN_SPELL;

        PlayerMagicData data = plugin.getDataManager().get(player.getUniqueId());
        if (data == null) return CastResult.UNKNOWN_SPELL;

        if (data.getMagicLevel() < spell.getLevelRequirement()) {
            return CastResult.LEVEL_TOO_LOW;
        }
        if (data.isOnCooldown(spell.getId())) {
            return CastResult.ON_COOLDOWN;
        }
        if (!data.consumeMana(spell.getManaCost())) {
            return CastResult.NOT_ENOUGH_MANA;
        }

        data.setCooldown(spell.getId(), spell.getCooldownSeconds());
        spell.cast(plugin, player);
        grantMagicXp(player, data);

        return CastResult.SUCCESS;
    }

    private void grantMagicXp(Player player, PlayerMagicData data) {
        long xpPerCast = plugin.getConfig().getLong("magic-level.xp-per-cast", 4);
        data.addMagicXp(xpPerCast);

        long xpNeeded = xpToNextLevel(data.getMagicLevel());
        if (data.getMagicXp() >= xpNeeded) {
            data.setMagicXp(data.getMagicXp() - xpNeeded);
            data.setMagicLevel(data.getMagicLevel() + 1);
            data.setMaxMana(plugin.getManaManager().calculateMaxMana(data.getMagicLevel()));

            player.sendMessage(ChatColor.LIGHT_PURPLE + "✨ " + ChatColor.BOLD + "Magic Level Up! "
                    + ChatColor.RESET + ChatColor.LIGHT_PURPLE + "Bạn đã đạt Magic Level "
                    + data.getMagicLevel() + "!");
            player.playSound(player.getLocation(), org.bukkit.Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f);
        }
    }

    public long xpToNextLevel(int currentLevel) {
        long base = plugin.getConfig().getLong("magic-level.xp-to-next-level-base", 50);
        double growth = plugin.getConfig().getDouble("magic-level.xp-growth-multiplier", 1.15);
        return Math.round(base * Math.pow(growth, currentLevel - 1));
    }
}
