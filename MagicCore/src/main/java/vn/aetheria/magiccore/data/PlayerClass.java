package vn.aetheria.magiccore.data;

import org.bukkit.ChatColor;

public enum PlayerClass {

    ARCANIST("Arcanist", "Phép thuật tổng hợp", "🪄", ChatColor.LIGHT_PURPLE),
    SPELLBLADE("Spellblade", "Cận chiến kết hợp phép thuật", "⚔", ChatColor.GOLD),
    DRUID("Druid", "Phép thuật thiên nhiên", "🌿", ChatColor.GREEN),
    ALCHEMIST("Alchemist", "Chế thuốc và hỗ trợ", "🧪", ChatColor.DARK_AQUA),
    WARLOCK("Warlock", "Hắc ám", "🌑", ChatColor.DARK_GRAY),
    GUARDIAN("Guardian", "Phòng thủ / Tank", "🛡", ChatColor.BLUE);

    private final String displayName;
    private final String description;
    private final String icon;
    private final ChatColor color;

    PlayerClass(String displayName, String description, String icon, ChatColor color) {
        this.displayName = displayName;
        this.description = description;
        this.icon = icon;
        this.color = color;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }

    public String getIcon() {
        return icon;
    }

    public ChatColor getColor() {
        return color;
    }

    public String getColoredName() {
        return color + icon + " " + displayName;
    }
}
