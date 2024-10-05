package nl.rubixdevelopment.dropwhitelist;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class DropWhitelistPlugin extends JavaPlugin {
    private ConfigManager configManager;
    private CommandHandler commandHandler;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        configManager = new ConfigManager(this);
        commandHandler = new CommandHandler(this, configManager);

        Bukkit.getPluginManager().registerEvents(new ItemFilter(configManager), this);
        getCommand("dropwhitelist").setExecutor(commandHandler);
        getCommand("dropwhitelist").setTabCompleter(commandHandler);

        getLogger().info("DropWhitelist plugin enabled!");

        int pluginId = 23542; // <-- Replace with the id of your plugin!
        Metrics metrics = new Metrics(this, pluginId);
    }

    @Override
    public void onDisable() {
        getLogger().info("DropWhitelist plugin disabled!");
    }
}
