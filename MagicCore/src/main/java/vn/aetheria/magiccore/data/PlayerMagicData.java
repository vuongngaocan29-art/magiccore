package vn.aetheria.magiccore.data;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Toàn bộ dữ liệu phép thuật của một người chơi:
 * Mana, Stamina, Magic Level/XP, House, Class, Year, cooldown các spell.
 */
public class PlayerMagicData {

    private final UUID uuid;

    private double mana;
    private double maxMana;

    private double stamina;
    private double maxStamina;

    private int magicLevel;
    private long magicXp;

    private House house;       // null nếu chưa chọn Nhà
    private PlayerClass playerClass; // null nếu chưa chọn Class
    private int year = 1;      // Năm học (1-5)

    // spellId -> thời điểm (millis) có thể cast lại
    private final Map<String, Long> cooldowns = new HashMap<>();

    public PlayerMagicData(UUID uuid, double maxMana, double maxStamina) {
        this.uuid = uuid;
        this.maxMana = maxMana;
        this.mana = maxMana;
        this.maxStamina = maxStamina;
        this.stamina = maxStamina;
        this.magicLevel = 1;
        this.magicXp = 0;
    }

    public UUID getUuid() {
        return uuid;
    }

    public double getMana() {
        return mana;
    }

    public void setMana(double mana) {
        this.mana = Math.max(0, Math.min(mana, maxMana));
    }

    public double getMaxMana() {
        return maxMana;
    }

    public void setMaxMana(double maxMana) {
        this.maxMana = maxMana;
        if (this.mana > maxMana) this.mana = maxMana;
    }

    public double getStamina() {
        return stamina;
    }

    public void setStamina(double stamina) {
        this.stamina = Math.max(0, Math.min(stamina, maxStamina));
    }

    public double getMaxStamina() {
        return maxStamina;
    }

    public void setMaxStamina(double maxStamina) {
        this.maxStamina = maxStamina;
    }

    public boolean consumeMana(double amount) {
        if (mana < amount) return false;
        mana -= amount;
        return true;
    }

    public int getMagicLevel() {
        return magicLevel;
    }

    public void setMagicLevel(int magicLevel) {
        this.magicLevel = Math.max(1, magicLevel);
    }

    public long getMagicXp() {
        return magicXp;
    }

    public void setMagicXp(long magicXp) {
        this.magicXp = Math.max(0, magicXp);
    }

    public void addMagicXp(long amount) {
        this.magicXp += amount;
    }

    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
    }

    public PlayerClass getPlayerClass() {
        return playerClass;
    }

    public void setPlayerClass(PlayerClass playerClass) {
        this.playerClass = playerClass;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = Math.max(1, Math.min(5, year));
    }

    public boolean isOnCooldown(String spellId) {
        Long until = cooldowns.get(spellId);
        return until != null && until > System.currentTimeMillis();
    }

    public long getRemainingCooldownSeconds(String spellId) {
        Long until = cooldowns.get(spellId);
        if (until == null) return 0;
        long remainingMs = until - System.currentTimeMillis();
        return Math.max(0, remainingMs / 1000);
    }

    public void setCooldown(String spellId, long seconds) {
        cooldowns.put(spellId, System.currentTimeMillis() + (seconds * 1000));
    }

    public Map<String, Long> getCooldowns() {
        return cooldowns;
    }
}
