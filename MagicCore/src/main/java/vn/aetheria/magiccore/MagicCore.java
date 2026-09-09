package vn.aetheria.magiccore;

import org.bukkit.plugin.java.JavaPlugin;
import vn.aetheria.magiccore.commands.*;
import vn.aetheria.magiccore.gui.GUIListener;
import vn.aetheria.magiccore.listeners.PlayerConnectionListener;
import vn.aetheria.magiccore.manager.DataManager;
import vn.aetheria.magiccore.manager.HouseManager;
import vn.aetheria.magiccore.manager.ManaManager;
import vn.aetheria.magiccore.manager.SpellManager;
import vn.aetheria.magiccore.util.ActionBarTask;

public class MagicCore extends JavaPlugin {

    private static MagicCore instance;

    private DataManager dataManager;
    private ManaManager manaManager;
    private HouseManager houseManager;
    private SpellManager spellManager;
    private ActionBarTask actionBarTask;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();

        // Managers (thứ tự quan trọng: DataManager trước, SpellManager cần config đã load)
        this.dataManager = new DataManager(this);
        this.manaManager = new ManaManager(this);
        this.houseManager = new HouseManager(this);
        this.spellManager = new SpellManager(this);

        // Listeners
        getServer().getPluginManager().registerEvents(new PlayerConnectionListener(this), this);
        getServer().getPluginManager().registerEvents(new GUIListener(this), this);

        // Commands
        getCommand("magic").setExecutor(new MagicCommand(this));
        getCommand("house").setExecutor(new HouseCommand(this));
        getCommand("class").setExecutor(new ClassCommand(this));
        getCommand("spell").setExecutor(new SpellCommand(this));
        getCommand("magicadmin").setExecutor(new MagicAdminCommand(this));

        // Tasks
        manaManager.startRegenTask();
        if (getConfig().getBoolean("actionbar.enabled", true)) {
            long interval = getConfig().getLong("actionbar.update-interval-ticks", 20);
            actionBarTask = new ActionBarTask(this);
            actionBarTask.runTaskTimer(this, 20L, interval);
        }

        getLogger().info("🪄 MagicCore (Aetheria) đã khởi động thành công!");
    }

    @Override
    public void onDisable() {
        if (manaManager != null) manaManager.stop();
        if (actionBarTask != null) actionBarTask.cancel();
        if (dataManager != null) dataManager.saveAll();
        if (houseManager != null) houseManager.save();

        getLogger().info("MagicCore đã tắt, dữ liệu đã được lưu.");
    }

    public static MagicCore getInstance() {
        return instance;
    }

    public DataManager getDataManager() {
        return dataManager;
    }

    public ManaManager getManaManager() {
        return manaManager;
    }

    public HouseManager getHouseManager() {
        return houseManager;
    }

    public SpellManager getSpellManager() {
        return spellManager;
    }
}
