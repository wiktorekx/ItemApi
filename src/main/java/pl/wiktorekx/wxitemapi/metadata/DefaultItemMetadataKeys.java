package pl.wiktorekx.wxitemapi.metadata;

import java.awt.*;
import java.util.List;
import java.util.Map;

public interface DefaultItemMetadataKeys {
    ItemMetadataKey<String> DISPLAY_NAME = ItemMetadataKey.create("display-name");
    ItemMetadataKey<List<String>> LORE = ItemMetadataKey.create("lore");
    ItemMetadataKey<Boolean> UNBREAKABLE = ItemMetadataKey.create("unbreakable");
    ItemMetadataKey<Integer> DAMAGE = ItemMetadataKey.create("damage");
    ItemMetadataKey<Short> MATERIAL_DATA = ItemMetadataKey.create("material-data");
    ItemMetadataKey<List<String>> ITEM_FLAGS = ItemMetadataKey.create("item-flags");
    ItemMetadataKey<Map<String, Integer>> ENCHANTS = ItemMetadataKey.create("enchants");
    ItemMetadataKey<String> SKULL_OWNER = ItemMetadataKey.create("skull-owner");
    ItemMetadataKey<String> SKULL_VALUE = ItemMetadataKey.create("skull-value");
    ItemMetadataKey<Color> COLOR = ItemMetadataKey.create("color");
    ItemMetadataKey<Map<String, String>> BANNER_PATTERNS = ItemMetadataKey.create("banner-patterns");
    ItemMetadataKey<Map<String, Object>> BOOK_DATA = ItemMetadataKey.create("book-data");

}
