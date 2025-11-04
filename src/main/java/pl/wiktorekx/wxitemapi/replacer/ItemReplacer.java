package pl.wiktorekx.wxitemapi.replacer;

import pl.wiktorekx.wxitemapi.Item;
import pl.wiktorekx.wxitemapi.metadata.DefaultItemMetadataKeys;
import pl.wiktorekx.wxitemapi.metadata.ItemMetadata;

import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class ItemReplacer {
    private ItemReplacer() {
        throw new RuntimeException();
    }

    public static Item replaceItem(Item item, Function<String, String> replaceFunction) {
        Objects.requireNonNull(replaceFunction, "replaceFunction is null");
        return item.replaceMetadata(metadata -> {
            if(metadata.hasMetadata(DefaultItemMetadataKeys.DISPLAY_NAME)) {
                metadata = metadata.setMetadata(DefaultItemMetadataKeys.DISPLAY_NAME,
                        replaceFunction.apply(metadata.getMetadata(DefaultItemMetadataKeys.DISPLAY_NAME)));
            }
            if(metadata.hasMetadata(DefaultItemMetadataKeys.LORE)) {
                metadata = metadata.setMetadata(DefaultItemMetadataKeys.LORE, metadata
                        .getMetadata(DefaultItemMetadataKeys.LORE).stream()
                                .map(replaceFunction).collect(Collectors.toList()));
            }
            if(metadata.hasMetadata(DefaultItemMetadataKeys.SKULL_OWNER)) {
                metadata = metadata.setMetadata(DefaultItemMetadataKeys.SKULL_OWNER,
                        replaceFunction.apply(metadata.getMetadata(DefaultItemMetadataKeys.SKULL_OWNER)));
            }
            return metadata;
        });
    }
}
