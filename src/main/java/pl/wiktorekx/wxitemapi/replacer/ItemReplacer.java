package pl.wiktorekx.wxitemapi.replacer;

import org.bukkit.inventory.ItemStack;
import pl.wiktorekx.wxitemapi.Item;
import pl.wiktorekx.wxitemapi.metadata.DefaultItemMetadataKeys;
import pl.wiktorekx.wxitemapi.metadata.ItemMetadata;
import pl.wiktorekx.wxitemapi.metadata.ItemMetadataKey;

import java.util.Collection;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class ItemReplacer {
    private ItemReplacer() {
        throw new RuntimeException();
    }

    public static Item replaceItem(Item item, Function<String, String> replaceFunction) {
        Objects.requireNonNull(replaceFunction, "replaceFunction is null");
        Item clonedItem = Objects.requireNonNull(item, "item is null").clone();
        ItemMetadata metadata = clonedItem.getMetadata();
        if(metadata.hasMetadata(DefaultItemMetadataKeys.DISPLAY_NAME)) {
            metadata.setMetadata(DefaultItemMetadataKeys.DISPLAY_NAME,
                    replaceFunction.apply(metadata.getMetadata(DefaultItemMetadataKeys.DISPLAY_NAME)));
        }
        if(metadata.hasMetadata(DefaultItemMetadataKeys.LORE)) {
            metadata.setMetadata(DefaultItemMetadataKeys.LORE, metadata
                    .getMetadata(DefaultItemMetadataKeys.LORE).stream()
                            .map(replaceFunction).collect(Collectors.toList()));
        }
        if(metadata.hasMetadata(DefaultItemMetadataKeys.SKULL_OWNER)) {
            metadata.setMetadata(DefaultItemMetadataKeys.SKULL_OWNER,
                    replaceFunction.apply(metadata.getMetadata(DefaultItemMetadataKeys.SKULL_OWNER)));
        }
        return clonedItem;
    }
}
