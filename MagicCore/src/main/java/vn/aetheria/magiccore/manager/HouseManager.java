package vn.aetheria.magiccore.manager;

import org.bukkit.configuration.file.YamlConfiguration;
import vn.aetheria.magiccore.MagicCore;
import vn.aetheria.magiccore.data.House;

import java.io.File;
import java.io.IOException;
import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;

/**
 * Quản lý House Points (Nhà điểm), dùng chung toàn server (không phải per-player).
 * Lưu trong plugins/MagicCore/houses.yml
 */
public class HouseManager {

    private final MagicCore plugin;
    private final File houseFile;
    private final Map<House, Integer> points = new EnumMap<>(House.class);

    public HouseManager(MagicCore plugin) {
        this.plugin = plugin;
        this.houseFile = new File(plugin.getDataFolder(), "houses.yml");
        load();
    }

    private void load() {
        int startingPoints = plugin.getConfig().getInt("houses.starting-points", 0);
        for (House house : House.values()) {
            points.put(house, startingPoints);
        }

        if (houseFile.exists()) {
            YamlConfiguration yaml = YamlConfiguration.loadConfiguration(houseFile);
            for (House house : House.values()) {
                points.put(house, yaml.getInt(house.name(), startingPoints));
            }
        }
    }

    public void save() {
        YamlConfiguration yaml = new YamlConfiguration();
        for (Map.Entry<House, Integer> entry : points.entrySet()) {
            yaml.set(entry.getKey().name(), entry.getValue());
        }
        try {
            yaml.save(houseFile);
        } catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE, "Không thể lưu houses.yml", e);
        }
    }

    public int getPoints(House house) {
        return points.getOrDefault(house, 0);
    }

    public void addPoints(House house, int amount) {
        points.put(house, getPoints(house) + amount);
        save();
    }

    public void setPoints(House house, int amount) {
        points.put(house, amount);
        save();
    }

    /** Trả về bảng xếp hạng Nhà, điểm cao nhất trước. */
    public Map<House, Integer> getLeaderboard() {
        LinkedHashMap<House, Integer> sorted = new LinkedHashMap<>();
        points.entrySet().stream()
                .sorted((a, b) -> b.getValue() - a.getValue())
                .forEach(e -> sorted.put(e.getKey(), e.getValue()));
        return sorted;
    }

    public House getLeadingHouse() {
        return getLeaderboard().keySet().stream().findFirst().orElse(House.IGNIS);
    }
}
