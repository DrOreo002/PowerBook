package me.droreo002.powerbook.model;

import lombok.Getter;
import me.droreo002.powerbook.PowerBook;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.util.Arrays;
import java.util.List;

public class BookFileManager {

    private static BookFileManager instance;

    public static BookFileManager getInstance(PowerBook plugin) {
        if (instance == null) {
            instance = new BookFileManager(plugin);
            return instance;
        }
        return instance;
    }

    @Getter
    private PowerBook plugin;

    private BookFileManager(PowerBook main) {
        this.plugin = main;
        setupFirst();
    }

    private void setupFirst() {
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }

        File file = new File(plugin.getDataFolder(), "books");
        if (!file.exists()) {
            file.mkdir();
        }

        File data = new File(plugin.getDataFolder(), "books" + File.separator + "test.yml");
        if (!data.exists()) {
            try {
                data.createNewFile();
                System.out.println("Created new test data!");
                FileConfiguration config = YamlConfiguration.loadConfiguration(data);
                String[] test = {
                       "This is a test file",
                       "There's nothing to worry about!"
                };
                List<String> list = Arrays.asList(test);
                config.set("Pages.first", list);
                config.save(data);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Create a new book
     *
     * @param name The book file name
     * @return LoadResult of book
     */
    public LoadResult create(String name) {
        File file = new File(plugin.getDataFolder(), "books" + File.separator + name + ".yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
                FileConfiguration config = YamlConfiguration.loadConfiguration(file);
                String[] test = {
                        "Done creating the file. Now start editing!"
                };
                List<String> list = Arrays.asList(test);
                config.set("Pages.first", list);
                config.save(file);
                return LoadResult.ON_CREATE_SUCCESS;
            } catch (IOException e) {
                e.printStackTrace();
                return LoadResult.ON_CREATE_IO_EXCEPTION;
            }
        }
        return LoadResult.ON_CREATE_UNKNOWN_ERROR;
    }

    /**
     * Save that book file
     *
     * @param name The book name
     * @return true if succeeded, false otherwise
     */
    public boolean save(String name) {
        File file = new File(plugin.getDataFolder(), "books" + File.separator + name + ".yml");
        if (!file.exists()) {
            return false;
        }
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        try {
            config.save(file);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Get file for that book
     *
     * @param name The book name
     * @return FileConfiguration
     */
    public FileConfiguration getFile(String name) {
        File file = new File(plugin.getDataFolder(), "books" + File.separator + name + ".yml");
        if (!file.exists()) {
            return null;
        }
        return YamlConfiguration.loadConfiguration(file);
    }
}
