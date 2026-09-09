package vn.aetheria.magiccore.data;

import org.bukkit.ChatColor;

public enum House {

    IGNIS("Ignis", "Phoenix", ChatColor.RED, "🔥"),
    NOCTIS("Noctis", "Raven", ChatColor.DARK_PURPLE, "🌙"),
    SILVA("Silva", "Stag", ChatColor.GREEN, "🌿"),
    AETHER("Aether", "Owl", ChatColor.AQUA, "🌌");

    private final String displayName;
    private final String symbolAnimal;
    private final ChatColor color;
    private final String icon;

    House(String displayName, String symbolAnimal, ChatColor color, String icon) {
        this.displayName = displayName;
        this.symbolAnimal = symbolAnimal;
        this.color = color;
        this.icon = icon;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getSymbolAnimal() {
        return symbolAnimal;
    }

    public ChatColor getColor() {
        return color;
    }

    public String getIcon() {
        return icon;
    }

    public String getColoredName() {
        return color + icon + " " + displayName;
    }
}
