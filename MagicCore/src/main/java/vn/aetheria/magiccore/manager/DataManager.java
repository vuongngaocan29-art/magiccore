package vn.aetheria.magiccore.manager;

import org.bukkit.configuration.file.YamlConfiguration;
import vn.aetheria.magiccore.MagicCore;
import vn.aetheria.magiccore.data.House;
import vn.aetheria.magiccore.data.PlayerClass;
import vn.aetheria.magiccore.data.PlayerMagicData;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;

/**
 * Lưu/đọc dữ liệu phép thuật của từng người chơi dưới dạng file YAML
 * trong plugins/MagicCore/playerdata/<uuid>.yml
 */
public class DataManager {

    private final MagicCore plugin;
    private final File playerDataFolder;
    private final Map<UUID, PlayerMagicData> cache = new HashMap<>();

    public DataManager(MagicCore plugin) {
        this.plugin = plugin;
        this.playerDataFolder = new File(plugin.getDataFolder(), "playerdata");
        if (!playerDataFolder.exists()) {
            playerDataFolder.mkdirs();
        }
    }

    public PlayerMagicData load(UUID uuid) {
        if (cache.containsKey(uuid)) {
            return cache.get(uuid);
        }

        File file = new File(playerDataFolder, uuid + ".yml");
        double baseMaxMana = plugin.getConfig().getDouble("mana.base-max", 50);
        double baseMaxStamina = plugin.getConfig().getDouble("stamina.base-max", 100);

        PlayerMagicData data = new PlayerMagicData(uuid, baseMaxMana, baseMaxStamina);

        if (file.exists()) {
            YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);
            data.setMagicLevel(yaml.getInt("magic-level", 1));
            data.setMagicXp(yaml.getLong("magic-xp", 0));
            data.setMana(yaml.getDouble("mana", baseMaxMana));
            data.setMaxMana(yaml.getDouble("max-mana", baseMaxMana));
            data.setStamina(yaml.getDouble("stamina", baseMaxStamina));
            data.setMaxStamina(yaml.getDouble("max-stamina", baseMaxStamina));
            data.setYear(yaml.getInt("year", 1));

            String houseStr = yaml.getString("house");
            if (houseStr != null) {
                try {
                    data.setHouse(House.valueOf(houseStr));
                } catch (IllegalArgumentException ignored) {
                }
            }

            String classStr = yaml.getString("class");
            if (classStr != null) {
                try {
                    data.setPlayerClass(PlayerClass.valueOf(classStr));
                } catch (IllegalArgumentException ignored) {
                }
            }
        }

        cache.put(uuid, data);
        return data;
    }

    public PlayerMagicData get(UUID uuid) {
        return cache.get(uuid);
    }

    public void save(PlayerMagicData data) {
        File file = new File(playerDataFolder, data.getUuid() + ".yml");
        YamlConfiguration yaml = new YamlConfiguration();

        yaml.set("magic-level", data.getMagicLevel());
        yaml.set("magic-xp", data.getMagicXp());
        yaml.set("mana", data.getMana());
        yaml.set("max-mana", data.getMaxMana());
        yaml.set("stamina", data.getStamina());
        yaml.set("max-stamina", data.getMaxStamina());
        yaml.set("year", data.getYear());
        yaml.set("house", data.getHouse() != null ? data.getHouse().name() : null);
        yaml.set("class", data.getPlayerClass() != null ? data.getPlayerClass().name() : null);

        try {
            yaml.save(file);
        } catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE, "Không thể lưu dữ liệu cho " + data.getUuid(), e);
        }
    }

    public void unload(UUID uuid) {
        PlayerMagicData data = cache.remove(uuid);
        if (data != null) {
            save(data);
        }
    }

    public void saveAll() {
        for (PlayerMagicData data : cache.values()) {
            save(data);
        }
    }
}
