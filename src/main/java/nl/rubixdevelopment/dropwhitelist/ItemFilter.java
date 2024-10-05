package nl.rubixdevelopment.dropwhitelist;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.util.Set;

public class ItemFilter implements Listener {
    private final ConfigManager configManager;

    public ItemFilter(ConfigManager configManager) {
        this.configManager = configManager;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Set<String> displayNameWhitelist = configManager.getDisplayNameWhitelist();
        Set<Material> materialWhitelist = configManager.getMaterialWhitelist();
        ItemStack[] drops = event.getDrops().toArray(new ItemStack[0]);

        for (ItemStack drop : drops) {
            String itemName = ChatColor.stripColor(drop.getType().name()); // Strip color codes from display names
            if (displayNameWhitelist.contains(itemName) || materialWhitelist.contains(drop.getType())) {
                drops = removeDrop(drops, drop);
            }
        }

        event.getDrops().clear();
        for (ItemStack drop : drops) {
            if (drop != null) {
                event.getDrops().add(drop);
            }
        }
    }

    private ItemStack[] removeDrop(ItemStack[] drops, ItemStack drop) {
        ItemStack[] newDrops = new ItemStack[drops.length - 1];
        int index = 0;
        for (ItemStack d : drops) {
            if (!d.equals(drop)) {
                newDrops[index++] = d;
            }
        }
        return newDrops;
    }
}
