package me.droreo002.powerbook;

import lombok.Getter;
import me.droreo002.powerbook.model.BookEditorAPI;
import me.droreo002.powerbook.model.BookFileManager;
import me.droreo002.powerbook.model.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class PowerBook extends JavaPlugin {

    @Getter
    private static PowerBook instance;
    @Getter
    private BookFileManager bookFileManager;
    @Getter
    private ConfigManager configManager;
    @Getter
    private BookEditorAPI bookEditorAPI;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        bookFileManager = BookFileManager.getInstance(this);
        configManager = ConfigManager.getInstance();
        bookEditorAPI = BookEditorAPI.getInstance();
        configManager.setup();

        Bukkit.getPluginCommand("powerbook").setExecutor(new PowerBookCommands(this));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public String getPrefix() {
        return ChatColor.translateAlternateColorCodes('&', configManager.getConfig().getString("Prefix"));
    }
}
