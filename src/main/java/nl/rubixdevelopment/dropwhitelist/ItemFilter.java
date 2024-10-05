package nl.rubixdevelopment.dropwhitelist;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.HashSet;
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
        Set<ItemStack> itemsToKeep = new HashSet<>();

        for (ItemStack drop : drops) {
            String itemName = ChatColor.stripColor(drop.getType().name()); // Strip color codes from display names
            if (displayNameWhitelist.contains(itemName) || materialWhitelist.contains(drop.getType())) {
                itemsToKeep.add(drop.clone()); // Keep a copy of the item to return on respawn
                drops = removeDrop(drops, drop);
            }
        }

        event.getDrops().clear();
        for (ItemStack drop : drops) {
            if (drop != null) {
                event.getDrops().add(drop);
            }
        }

        // Restore whitelisted items if the config option is set
        if (configManager.isKeepWhitelistedItems()) {
            Player player = event.getEntity();
            for (ItemStack item : itemsToKeep) {
                player.getInventory().addItem(item); // Give back the item to the player
            }
            player.sendMessage(ChatColor.GREEN + "You have retained your whitelisted items upon respawn!");
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
