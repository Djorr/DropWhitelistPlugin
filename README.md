# DropWhitelist Plugin for Minecraft 1.8.8

**DropWhitelist** is a Minecraft plugin designed for PaperMC servers that allows server administrators to manage which items are not dropped upon player death. This plugin helps maintain game balance by preventing specific items from being lost when a player dies.

## Features

- **Whitelist Control**: Easily add or remove item display names and material IDs from the whitelist.
- **Configurable**: Load item names and material IDs from a `config.yml` file for easy customization.
- **Tab Completion**: Commands are equipped with tab-completion for enhanced user experience.
- **User-Friendly Messages**: Informative messages to guide server administrators in using commands.

## Installation

1. **Download the Plugin**: Get the latest version of the DropWhitelist plugin JAR file from the [Releases](https://github.com/Djorr/DropWhitelistPlugin/releases).

2. **Place the JAR File**: Move the JAR file to your server's `plugins` directory.

3. **Restart the Server**: Restart your Minecraft server to load the plugin.

4. **Configure the Plugin**: After loading, a `config.yml` file will be generated in the `plugins/DropWhitelist` folder. Open it to configure the whitelisted items.

## Configuration

The `config.yml` file structure is as follows:

```yaml
# List of item display names that should not be dropped on player death
whitelist-item-names:
  - Diamond Sword
  - Special Axe
  - Enchanted Pickaxe

# List of item IDs (Materials) that should not be dropped on player death
whitelist-item-ids:
  - DIAMOND_SWORD
  - GOLDEN_APPLE
 ```
- You can leave the lists empty ([]) if you don’t want any specific items to be whitelisted.

## Commands
### `/dropwhitelist`

Main command for managing the drop whitelist.

### Subcommands:
- `/dropwhitelist reload`: Reloads the configuration file.
- `/dropwhitelist addDisplayName <name>`: Adds a display name to the whitelist.
- `/dropwhitelist removeDisplayName <name>`: Removes a display name from the whitelist.
- `/dropwhitelist addMaterial <name>`: Adds a material ID to the whitelist.
- `/dropwhitelist removeMaterial <name>`: Removes a material ID from the whitelist.
- `/dropwhitelist listDisplayNames`: Lists all whitelisted display names.
- `/dropwhitelist listMaterials`: Lists all whitelisted materials.
- `/dropwhitelist toggleKeepItems`: Toggles the option for players to keep their whitelisted items upon respawn.

## Usage
- Adding an Item: To add a display name to the whitelist, use the command `/dropwhitelist addDisplayName <item_name>`.
- Removing an Item: To remove a display name, use `/dropwhitelist removeDisplayName <item_name>`.
- Reloading the Config: If you make changes to the config.yml, remember to run `/dropwhitelist reload` to apply those changes.
- Listing Items: Use `/dropwhitelist listDisplayNames` or `/dropwhitelist listMaterials` to view the current whitelisted items.
- Toggle Keeping Items: To toggle the setting that allows players to keep whitelisted items upon respawn, use `/dropwhitelist toggleKeepItems`.

## License
This plugin is licensed under a custom license that allows you to:

- **Modify** and **distribute** this plugin freely.
- Use this plugin in your own projects.
However, you **cannot claim** this plugin or its modified versions as your own work. Proper attribution to the original creator is appreciated.

## Contributing
Contributions are welcome! Please fork the repository and submit a pull request if you have suggestions or improvements.

## Support
If you encounter any issues or have questions, please open an issue on the GitHub repository.
