package craftgpt.craftgpt;

import org.bukkit.plugin.java.JavaPlugin;

public final class CraftGPT extends JavaPlugin {

    @Override
    public void onEnable() {
        getCommand("chat").setExecutor(new CommandClass(this));
        getServer().getPluginManager().registerEvents(new EventClass(this), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
