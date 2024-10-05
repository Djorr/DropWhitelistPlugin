package nl.rubixdevelopment.dropwhitelist;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CommandHandler implements CommandExecutor, TabCompleter {
    private final DropWhitelistPlugin plugin;
    private final ConfigManager configManager;

    public CommandHandler(DropWhitelistPlugin plugin, ConfigManager configManager) {
        this.plugin = plugin;
        this.configManager = configManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("dropwhitelist")) {
            if (args.length == 0) {
                sender.sendMessage(ChatColor.RED + "DropWhitelist » Usage: /dropwhitelist <reload|addDisplayName|removeDisplayName|addMaterial|removeMaterial|listDisplayNames|listMaterials|toggleKeepItems>");
                return true;
            }

            switch (args[0].toLowerCase()) {
                case "reload":
                    configManager.reloadConfig();
                    sender.sendMessage(ChatColor.GREEN + "DropWhitelist » Config reloaded!");
                    return true;
                case "adddisplayname":
                    return handleAddDisplayName(sender, args);
                case "removedisplayname":
                    return handleRemoveDisplayName(sender, args);
                case "addmaterial":
                    return handleAddMaterial(sender, args);
                case "removematerial":
                    return handleRemoveMaterial(sender, args);
                case "listdisplaynames":
                    return handleListDisplayNames(sender);
                case "listmaterials":
                    return handleListMaterials(sender);
                case "togglekeepitems":
                    return handleToggleKeepItems(sender);
                default:
                    sender.sendMessage(ChatColor.RED + "DropWhitelist » Unknown subcommand.");
                    return true;
            }
        }
        return false;
    }

    private boolean handleToggleKeepItems(CommandSender sender) {
        boolean currentValue = configManager.isKeepWhitelistedItems();
        plugin.getConfig().set("keep-whitelisted-items", !currentValue);
        configManager.saveConfig();
        sender.sendMessage(ChatColor.GREEN + "DropWhitelist » Whitelisted items on respawn is now " + (currentValue ? "disabled" : "enabled") + ".");
        return true;
    }

    private boolean handleAddDisplayName(CommandSender sender, String[] args) {
        if (args.length < 2) {
            sender.sendMessage(ChatColor.RED + "DropWhitelist » Please specify a display name.");
            return true;
        }
        String itemName = args[1];
        Set<String> displayNames = configManager.getDisplayNameWhitelist();

        if (displayNames.contains(itemName)) {
            sender.sendMessage(ChatColor.RED + "DropWhitelist » Display name '" + itemName + "' is already whitelisted.");
            return true;
        }
        displayNames.add(itemName);
        plugin.getConfig().set("whitelist-item-names", new ArrayList<>(displayNames));
        configManager.saveConfig();
        sender.sendMessage(ChatColor.GREEN + "DropWhitelist » Added display name '" + itemName + "' to the whitelist.");
        return true;
    }

    private boolean handleRemoveDisplayName(CommandSender sender, String[] args) {
        if (args.length < 2) {
            sender.sendMessage(ChatColor.RED + "DropWhitelist » Please specify a display name.");
            return true;
        }
        String itemName = args[1];
        Set<String> displayNames = configManager.getDisplayNameWhitelist();

        if (!displayNames.contains(itemName)) {
            sender.sendMessage(ChatColor.RED + "DropWhitelist » Display name '" + itemName + "' is not in the whitelist.");
            return true;
        }
        displayNames.remove(itemName);
        plugin.getConfig().set("whitelist-item-names", new ArrayList<>(displayNames));
        configManager.saveConfig();
        sender.sendMessage(ChatColor.GREEN + "DropWhitelist » Removed display name '" + itemName + "' from the whitelist.");
        return true;
    }

    private boolean handleAddMaterial(CommandSender sender, String[] args) {
        if (args.length < 2) {
            sender.sendMessage(ChatColor.RED + "DropWhitelist » Please specify a material.");
            return true;
        }
        String materialName = args[1].toUpperCase();
        Set<Material> materials = configManager.getMaterialWhitelist();

        try {
            Material material = Material.valueOf(materialName);
            if (materials.contains(material)) {
                sender.sendMessage(ChatColor.RED + "DropWhitelist » Material '" + material + "' is already whitelisted.");
                return true;
            }
            materials.add(material);
            plugin.getConfig().set("whitelist-item-ids", materials.stream().map(Material::name).collect(Collectors.toList()));
            configManager.saveConfig();
            sender.sendMessage(ChatColor.GREEN + "DropWhitelist » Added material '" + material + "' to the whitelist.");
            return true;
        } catch (IllegalArgumentException e) {
            sender.sendMessage(ChatColor.RED + "DropWhitelist » Invalid material: " + args[1]);
            return true;
        }
    }

    private boolean handleRemoveMaterial(CommandSender sender, String[] args) {
        if (args.length < 2) {
            sender.sendMessage(ChatColor.RED + "DropWhitelist » Please specify a material.");
            return true;
        }
        String materialName = args[1].toUpperCase();
        Set<Material> materials = configManager.getMaterialWhitelist();

        try {
            Material material = Material.valueOf(materialName);
            if (!materials.contains(material)) {
                sender.sendMessage(ChatColor.RED + "DropWhitelist » Material '" + material + "' is not in the whitelist.");
                return true;
            }
            materials.remove(material);
            plugin.getConfig().set("whitelist-item-ids", materials.stream().map(Material::name).collect(Collectors.toList()));
            configManager.saveConfig();
            sender.sendMessage(ChatColor.GREEN + "DropWhitelist » Removed material '" + material + "' from the whitelist.");
            return true;
        } catch (IllegalArgumentException e) {
            sender.sendMessage(ChatColor.RED + "DropWhitelist » Invalid material: " + args[1]);
            return true;
        }
    }

    private boolean handleListDisplayNames(CommandSender sender) {
        Set<String> displayNames = configManager.getDisplayNameWhitelist();
        sender.sendMessage(ChatColor.GOLD + "DropWhitelist » Whitelisted Display Names:");
        displayNames.forEach(name -> sender.sendMessage(ChatColor.YELLOW + "- " + name));
        return true;
    }

    private boolean handleListMaterials(CommandSender sender) {
        Set<Material> materials = configManager.getMaterialWhitelist();
        sender.sendMessage(ChatColor.GOLD + "DropWhitelist » Whitelisted Materials:");
        materials.forEach(material -> sender.sendMessage(ChatColor.YELLOW + "- " + material.name()));
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();

        if (command.getName().equalsIgnoreCase("dropwhitelist")) {
            if (args.length == 1) {
                completions.add("reload");
                completions.add("addDisplayName");
                completions.add("removeDisplayName");
                completions.add("addMaterial");
                completions.add("removeMaterial");
                completions.add("listDisplayNames");
                completions.add("listMaterials");
            } else if (args.length == 2) {
                if (args[0].equalsIgnoreCase("addDisplayName") || args[0].equalsIgnoreCase("removeDisplayName")) {
                    completions = new ArrayList<>(configManager.getDisplayNameWhitelist());
                } else if (args[0].equalsIgnoreCase("addMaterial") || args[0].equalsIgnoreCase("removeMaterial")) {
                    for (Material material : Material.values()) {
                        if (!configManager.getMaterialWhitelist().contains(material)) {
                            completions.add(material.name());
                        }
                    }
                }
            }
        }

        return completions;
    }
}
