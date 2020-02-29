package me.droreo002.powerbook.model;

import me.droreo002.powerbook.PowerBook;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BookEditorAPI {

    private static BookEditorAPI instance;

    public static BookEditorAPI getInstance() {
        if (instance == null) {
            instance = new BookEditorAPI();
            return instance;
        }
        return instance;
    }

    private BookEditorAPI() {}

    /**
     * Load book
     *
     * @param player Target player
     * @param name Book name
     * @return LoadResult
     */
    @NotNull
    public LoadResult loadBook(Player player, String name) {
        ItemStack item = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta meta = (BookMeta) item.getItemMeta();
        BookFileManager fileManager = PowerBook.getInstance().getBookFileManager();
        FileConfiguration file = fileManager.getFile(name);
        if (file == null) return LoadResult.ON_LOAD_FILE_NULL;
        ConfigurationSection sc = file.getConfigurationSection("Pages");
        int size = sc.getKeys(false).size();
        for (String s : sc.getKeys(false)) {
            List<String> list = file.getStringList("Pages." + s);
            if (list.size() > 14) return LoadResult.ON_LOAD_SIZE_MAX;
            String result = ChatColor.translateAlternateColorCodes('&', String.join("\n", list));
            meta.addPage(result);
//            if (meta.getPageCount() <= 0) {
//                meta.addPage(result);
//                continue;
//            }
//            if (meta.getPageCount() < size) {
//                for (int i = meta.getPageCount(); i < size; i++) {
//                    meta.addPage(result);
//                }
//            }
        }
        meta.setAuthor(player.getName());
        meta.setTitle(name);
        meta.setDisplayName(name);
        item.setItemMeta(meta);

        player.getInventory().addItem(item);
        return LoadResult.ON_LOAD_SUCCESS;
    }

    /**
     * Create a new book
     *
     * @param name The book name
     * @return The load result
     */
    public LoadResult createBook(String name) {
        return PowerBook.getInstance().getBookFileManager().create(name);
    }
}
