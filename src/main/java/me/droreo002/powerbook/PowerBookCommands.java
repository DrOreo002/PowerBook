package me.droreo002.powerbook;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PowerBookCommands implements CommandExecutor {

    private PowerBook plugin;

    PowerBookCommands(PowerBook plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        Player player = (Player) sender;

        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("load")) {
                if (!player.hasPermission("powerbook.load")) {
                    sendMessage(player, "You do not have the permission to run this!");
                    return true;
                }
                if (args.length == 1) {
                    sendMessage(player, "Invalid usage!, please check spigot page for command list!");
                    return true;
                }
                if (args.length > 2) {
                    sendMessage(player, "Invalid usage!, please check spigot page for command list!");
                    return true;
                }
                String name = args[1];
                switch (plugin.getBookEditorAPI().loadBook(player, name)) {
                    case ON_LOAD_SUCCESS:
                        sendMessage(player, "Book has been loaded and transferred into your inventory");
                        return true;
                    case ON_LOAD_SIZE_MAX:
                        sendMessage(player, "Book lines cannot be greater than 14!");
                        return true;
                    case ON_LOAD_FILE_NULL:
                        sendMessage(player, "Cannot find that book file! &7(&a" + name + "&7)");
                        return true;
                }
            }
            if (args[0].equalsIgnoreCase("create")) {
                if (!player.hasPermission("powerbook.create")) {
                    sendMessage(player, "No permission!");
                    return true;
                }
                if (args.length == 1) {
                    sendMessage(player, "Invalid usage!, please check spigot page for command list!");
                    return true;
                }
                if (args.length > 2) {
                    sendMessage(player, "Invalid usage!, please check spigot page for command list!");
                    return true;
                }
                String name = args[1];
                switch (plugin.getBookEditorAPI().createBook(name)) {
                    case ON_CREATE_SUCCESS:
                        sendMessage(player, "Successfully created a new book with the name of &a" + name);
                        return true;
                    case ON_CREATE_IO_EXCEPTION:
                        sendMessage(player, "An error occurred please check your console!");
                        return true;
                    case ON_CREATE_UNKNOWN_ERROR:
                        sendMessage(player, "An unknown error occurred!");
                        return true;
                }
            }
        }

        if (args.length == 0) {
            sendMessage(player, "Created by DrOreo002. Please see the spigot page for command list!");
            return true;
        }

        return false;
    }

    /**
     * Send a message to player
     * 
     * @param player Target player
     * @param message Message
     */
    private void sendMessage(Player player, String message) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }
}
