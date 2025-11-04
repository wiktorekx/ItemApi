package pl.wiktorekx.wxitemapi.bukkit;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.wiktorekx.wxitemapi.Item;
import pl.wiktorekx.wxitemapi.metadata.DefaultItemMetadataKeys;
import pl.wiktorekx.wxitemapi.utils.MinecraftVersion;

import java.util.Objects;

public final class BukkitItemSerializer {
    private BukkitItemSerializer() {
        throw new RuntimeException();
    }

    public static ItemStack serializeItem(Item item) {
        return serializeItem(item, BukkitItemMetadataSerializer
                .serializeItemMetadata(BukkitItemMetadataSerializer
                        .createItemMeta(item.getMaterial()), item.getMetadata()));
    }

    public static ItemStack serializeItem(Item item, ItemMeta itemMeta) {
        Objects.requireNonNull(item, "item is null");
        Objects.requireNonNull(item, "itemMeta is null");
        ItemStack itemStack = new ItemStack(BukkitMaterialSerializer.getMaterial(item.getMaterial()));
        itemStack.setAmount(item.getAmount());
        if(!MinecraftVersion.support(13) && item.getMetadata().hasMetadata(DefaultItemMetadataKeys.MATERIAL_DATA)) {
            itemStack.setDurability(item.getMetadata().getMetadata(DefaultItemMetadataKeys.MATERIAL_DATA));
        }
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
