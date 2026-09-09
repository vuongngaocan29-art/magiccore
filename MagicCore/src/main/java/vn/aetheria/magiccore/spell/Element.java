package vn.aetheria.magiccore.spell;

import org.bukkit.ChatColor;

public enum Element {
    FIRE("Lửa", "🔥", ChatColor.RED),
    ICE("Băng", "❄", ChatColor.AQUA),
    LIGHTNING("Sét", "⚡", ChatColor.YELLOW),
    LIGHT("Ánh sáng", "✨", ChatColor.WHITE),
    NATURE("Thiên nhiên", "🌿", ChatColor.GREEN),
    ARCANE("Huyền bí", "🌌", ChatColor.LIGHT_PURPLE),
    DARK("Hắc ám", "🌑", ChatColor.DARK_GRAY);

    private final String displayName;
    private final String icon;
    private final ChatColor color;

    Element(String displayName, String icon, ChatColor color) {
        this.displayName = displayName;
        this.icon = icon;
        this.color = color;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getIcon() {
        return icon;
    }

    public ChatColor getColor() {
        return color;
    }
}
