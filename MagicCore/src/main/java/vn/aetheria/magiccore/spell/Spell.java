package vn.aetheria.magiccore.spell;

import org.bukkit.entity.Player;
import vn.aetheria.magiccore.MagicCore;

/**
 * Lớp cha cho mọi phép thuật trong Aetheria.
 * Mỗi Spell định nghĩa: id, tên hiển thị, mana cost, cooldown, range,
 * element, level requirement và logic cast() riêng.
 */
public abstract class Spell {

    private final String id;
    private final String displayName;
    private final String description;
    private final double manaCost;
    private final int cooldownSeconds;
    private final double range;
    private final Element element;
    private final int levelRequirement;

    protected Spell(String id, String displayName, String description, double manaCost,
                     int cooldownSeconds, double range, Element element, int levelRequirement) {
        this.id = id;
        this.displayName = displayName;
        this.description = description;
        this.manaCost = manaCost;
        this.cooldownSeconds = cooldownSeconds;
        this.range = range;
        this.element = element;
        this.levelRequirement = levelRequirement;
    }

    /**
     * Logic thực thi khi người chơi cast phép thành công
     * (mana + cooldown + level requirement đã được SpellManager kiểm tra trước đó).
     */
    public abstract void cast(MagicCore plugin, Player caster);

    public String getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }

    public double getManaCost() {
        return manaCost;
    }

    public int getCooldownSeconds() {
        return cooldownSeconds;
    }

    public double getRange() {
        return range;
    }

    public Element getElement() {
        return element;
    }

    public int getLevelRequirement() {
        return levelRequirement;
    }

    public String getFormattedLine() {
        return element.getColor() + element.getIcon() + " " + displayName
                + org.bukkit.ChatColor.GRAY + " (Lv." + levelRequirement + ", "
                + (int) manaCost + " mana, " + cooldownSeconds + "s cd)";
    }
}
