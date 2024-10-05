package nl.rubixdevelopment.dropwhitelist;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ConfigManager {
    private final DropWhitelistPlugin plugin;
    private Set<String> displayNameWhitelist;
    private Set<Material> materialWhitelist;
    private boolean keepWhitelistedItems;

    public ConfigManager(DropWhitelistPlugin plugin) {
        this.plugin = plugin;
        loadWhitelist();
    }

    public void loadWhitelist() {
        FileConfiguration config = plugin.getConfig();
        displayNameWhitelist = new HashSet<>(config.getStringList("whitelist-item-names"));
        materialWhitelist = new HashSet<>();

        List<String> whitelistItemIDs = config.getStringList("whitelist-item-ids");
        for (String materialName : whitelistItemIDs) {
            try {
                Material material = Material.valueOf(materialName.toUpperCase());
                materialWhitelist.add(material);
            } catch (IllegalArgumentException e) {
                plugin.getLogger().warning("Invalid material name in config: " + materialName);
            }
        }

        keepWhitelistedItems = config.getBoolean("keep-whitelisted-items", true);

        plugin.getLogger().info("Loaded " + displayNameWhitelist.size() + " whitelisted display names.");
        plugin.getLogger().info("Loaded " + materialWhitelist.size() + " whitelisted item IDs.");
    }

    public Set<String> getDisplayNameWhitelist() {
        return displayNameWhitelist;
    }

    public Set<Material> getMaterialWhitelist() {
        return materialWhitelist;
    }

    public boolean isKeepWhitelistedItems() {
        return keepWhitelistedItems;
    }

    public void saveConfig() {
        plugin.saveConfig();
    }

    public void reloadConfig() {
        plugin.reloadConfig();
        loadWhitelist();
    }
}
