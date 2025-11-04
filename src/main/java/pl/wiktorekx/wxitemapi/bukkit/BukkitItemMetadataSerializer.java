package pl.wiktorekx.wxitemapi.bukkit;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.FireworkEffect;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.*;
import pl.wiktorekx.wxitemapi.metadata.DefaultItemMetadataKeys;
import pl.wiktorekx.wxitemapi.metadata.ItemMetadata;
import pl.wiktorekx.wxitemapi.metadata.ItemMetadataKey;
import pl.wiktorekx.wxitemapi.metadata.NMSUtils;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

public final class BukkitItemMetadataSerializer {
    private static final Map<ItemMetadataKey<?>, BiConsumer<Object, ItemMeta>> SERIALIZERS = new HashMap<>();

    static {
        registerSerializers();
    }

    @SuppressWarnings("unchecked")
    private static void registerSerializers() {
        SERIALIZERS.put(DefaultItemMetadataKeys.DISPLAY_NAME, (value, itemMeta) ->
                itemMeta.setDisplayName((String) value)
        );
        SERIALIZERS.put(DefaultItemMetadataKeys.LORE, (value, itemMeta) ->
                itemMeta.setLore((List<String>) value)
        );
        SERIALIZERS.put(DefaultItemMetadataKeys.UNBREAKABLE, (value, itemMeta) ->
                itemMeta.setUnbreakable((Boolean) value)
        );
        SERIALIZERS.put(DefaultItemMetadataKeys.DAMAGE, (value, itemMeta) -> {
            if (itemMeta instanceof Damageable) {
                ((Damageable) itemMeta).setDamage((Integer) value);
            }
        });
        SERIALIZERS.put(DefaultItemMetadataKeys.ITEM_FLAGS, (value, itemMeta) -> {
            List<String> itemFlags = (List<String>) value;
            if(itemFlags.contains("HIDE_ALL")){
                itemMeta.addItemFlags(ItemFlag.values());
            } else {
                for(String itemFlag : itemFlags) {
                    try {
                        itemMeta.addItemFlags(ItemFlag.valueOf(itemFlag));
                    } catch (IllegalArgumentException e){
                        throw new IllegalArgumentException("not found ItemFlag " + itemFlag);
                    }
                }
            }
        });
        SERIALIZERS.put(DefaultItemMetadataKeys.ENCHANTS, (value, itemMeta) -> {
            for(Map.Entry<String, Integer> enchant : ((Map<String, Integer>) value).entrySet()) {
                Enchantment enchantment = Enchantment.getByName(enchant.getKey());
                if(enchantment == null) throw new IllegalArgumentException("not found enchant " + enchant.getKey());
                itemMeta.addEnchant(enchantment, enchant.getValue(), true);
            }
        });
        SERIALIZERS.put(DefaultItemMetadataKeys.SKULL_OWNER, (value, itemMeta) -> {
            if (itemMeta instanceof SkullMeta) {
                ((SkullMeta) itemMeta).setOwner((String) value);
            }
        });
        SERIALIZERS.put(DefaultItemMetadataKeys.SKULL_VALUE, (value, itemMeta) -> {
            if (itemMeta instanceof SkullMeta) {
                NMSUtils.applySkullValue((SkullMeta) itemMeta, (String) value);
            }
        });
        SERIALIZERS.put(DefaultItemMetadataKeys.COLOR, (value, itemMeta) -> {
            java.awt.Color valueColor = (java.awt.Color) value;
            Color color = Color.fromARGB(
                    valueColor.getAlpha(),
                    valueColor.getRed(),
                    valueColor.getGreen(),
                    valueColor.getBlue()
            );
            if(itemMeta instanceof LeatherArmorMeta) {
                ((LeatherArmorMeta) itemMeta).setColor(color);
            } else if (itemMeta instanceof FireworkEffectMeta) {
                ((FireworkEffectMeta)itemMeta).setEffect(FireworkEffect.builder()
                        .withColor(color)
                        .build());
            }
        });
        SERIALIZERS.put(DefaultItemMetadataKeys.BANNER_PATTERNS, (value, itemMeta) -> {
            if(itemMeta instanceof BannerMeta) {
                List<Pattern> patterns = new ArrayList<>();
                for (Map.Entry<String, String> pattern : ((Map<String, String>) value).entrySet()) {
                    PatternType patternType;
                    try {
                        patternType = PatternType.valueOf(pattern.getKey());
                    } catch (IllegalArgumentException e) {
                        throw new IllegalArgumentException("not found PatternType " + pattern.getKey());
                    }
                    DyeColor dyeColor;
                    try {
                        dyeColor = DyeColor.valueOf(pattern.getValue());
                    } catch (IllegalArgumentException e) {
                        throw new IllegalArgumentException("not found DyeColor " + pattern.getValue());
                    }
                    patterns.add(new Pattern(dyeColor, patternType));
                }
                ((BannerMeta) itemMeta).setPatterns(patterns);
            }
        });
        SERIALIZERS.put(DefaultItemMetadataKeys.BOOK_DATA, (value, itemMeta) -> {
            if(itemMeta instanceof BookMeta) {
                    Map<String, Object> bookData = (Map<String, Object>) value;
                    BookMeta bookMeta = (BookMeta) itemMeta;
                    bookMeta.setTitle((String) bookData.getOrDefault("title", ""));
                    bookMeta.setAuthor((String) bookData.getOrDefault("author", ""));
                    String generationValue = (String) bookData.get("generation");
                    if(generationValue != null){
                        BookMeta.Generation generation;
                        try {
                            generation = BookMeta.Generation.valueOf(generationValue);
                        } catch (IllegalArgumentException e) {
                            throw new IllegalArgumentException("not found Generation " + generationValue);
                        }
                        bookMeta.setGeneration(generation);
                    } else {
                        bookMeta.setGeneration(BookMeta.Generation.ORIGINAL);
                    }
                    List<String> pages = (List<String>) bookData.get("pages");
                    if(pages != null) {
                        bookMeta.setPages(pages);
                    } else {
                        List<String> pagesComponent = (List<String>) bookData.get("pages-component");
                        if (pagesComponent != null) {
                            NMSUtils.setBookPagesComponent(bookMeta, pagesComponent);
                        }
                    }
                }
        });
    }

    private BukkitItemMetadataSerializer() {
        throw new RuntimeException();
    }

    public static ItemMeta createItemMeta(String material) {
        return Bukkit.getItemFactory().getItemMeta(BukkitMaterialSerializer.getMaterial(material));
    }

    public static ItemMeta serializeItemMetadata(ItemMeta itemMeta, ItemMetadata itemMetadata) {
        Objects.requireNonNull(itemMeta, "itemMeta is null");
        Objects.requireNonNull(itemMetadata, "itemMetadata is null");
        itemMetadata.toMap().forEach((key, value) -> {
            if(SERIALIZERS.containsKey(key)) {
                SERIALIZERS.get(key).accept(value, itemMeta);
            }
        });
        return itemMeta;
    }
}