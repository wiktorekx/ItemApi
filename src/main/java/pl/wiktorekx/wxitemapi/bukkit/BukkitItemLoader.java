package pl.wiktorekx.wxitemapi.bukkit;

import org.bukkit.configuration.ConfigurationSection;
import pl.wiktorekx.wxitemapi.Item;
import pl.wiktorekx.wxitemapi.metadata.DefaultItemMetadataKeys;
import pl.wiktorekx.wxitemapi.metadata.ItemMetadata;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class BukkitItemLoader {
    private BukkitItemLoader() {
        throw new RuntimeException();
    }

    public static Item loadItem(ConfigurationSection section) {
        Objects.requireNonNull(section, "section is null");
        Item item = Item.create(Objects.requireNonNull(section.getString("material"), "not found material from section"));
        if(section.contains("amount")) {
            item = item.setAmount(section.getInt("amount"));
        }
        ItemMetadata metadata = item.getMetadata();
        if(section.contains("display-name")){
            metadata = metadata.setMetadata(DefaultItemMetadataKeys.DISPLAY_NAME,
                    section.getString("display-name"));
        }
        if(section.contains("lore")){
            metadata = metadata.setMetadata(DefaultItemMetadataKeys.LORE,
                    section.getStringList("lore"));
        }
        if(section.contains("unbreakable")){
            metadata = metadata.setMetadata(DefaultItemMetadataKeys.UNBREAKABLE,
                    section.getBoolean("unbreakable"));
        }
        if(section.contains("damage")){
            metadata = metadata.setMetadata(DefaultItemMetadataKeys.DAMAGE,
                    section.getInt("damage"));
        }
        if(section.contains("item-flags")){
            metadata = metadata.setMetadata(DefaultItemMetadataKeys.ITEM_FLAGS,
                    section.getStringList("item-flags"));
        }
        ConfigurationSection enchantsSection = section.getConfigurationSection("enchants");
        if(enchantsSection != null){
            Map<String, Integer> enchantMap = new HashMap<>();
            for(String key : enchantsSection.getKeys(false)){
                enchantMap.put(key, enchantsSection.getInt(key));
            }
            metadata = metadata.setMetadata(DefaultItemMetadataKeys.ENCHANTS, enchantMap);
        }
        if(section.contains("skull-owner")){
            metadata = metadata.setMetadata(DefaultItemMetadataKeys.SKULL_OWNER,
                    section.getString("skull-owner"));
        }
        if(section.contains("skull-value")){
            metadata = metadata.setMetadata(DefaultItemMetadataKeys.SKULL_VALUE,
                    section.getString("skull-value"));
        }
        if(section.contains("color")){
            metadata = metadata.setMetadata(DefaultItemMetadataKeys.COLOR,
                    Color.decode(Objects.requireNonNull(section.getString("color"))));
        }
        ConfigurationSection bannerPatternsSection = section.getConfigurationSection("banner-patterns");
        if(bannerPatternsSection != null){
            Map<String, String> bannerPatternsMap = new HashMap<>();
            for(String key : bannerPatternsSection.getKeys(false)){
                bannerPatternsMap.put(key, bannerPatternsSection.getString(key));
            }
            metadata = metadata.setMetadata(DefaultItemMetadataKeys.BANNER_PATTERNS, bannerPatternsMap);
        }
        ConfigurationSection bookDataSection = section.getConfigurationSection("book-data");
        if(bookDataSection != null){
            Map<String, Object> bookDataSectionMap = new HashMap<>();
            if(bookDataSection.contains("title")) bookDataSectionMap.put("title", bookDataSection.getString("title"));
            if(bookDataSection.contains("author")) bookDataSectionMap.put("author", bookDataSection.getString("author"));
            if(bookDataSection.contains("generation")) bookDataSectionMap.put("generation", bookDataSection.getString("generation"));
            if(bookDataSection.contains("pages")) bookDataSectionMap.put("pages", bookDataSection.getStringList("pages"));
            if(bookDataSection.contains("pages-component")) bookDataSectionMap.put("pages-component", bookDataSection.getStringList("pages-component"));
            metadata = metadata.setMetadata(DefaultItemMetadataKeys.BOOK_DATA, bookDataSectionMap);
        }
        item.setMetadata(metadata);
        return item;
    }
}
