package pl.wiktorekx.wxitemapi;

import pl.wiktorekx.wxitemapi.metadata.DefaultItemMetadataKeys;
import pl.wiktorekx.wxitemapi.metadata.ItemMetadata;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public final class Item {
    private final String material;
    private final int amount;
    private final ItemMetadata metadata;

    public static Item create(String material) {
        return create(material, ItemMetadata.create());
    }

    public static Item create(String material, ItemMetadata metadata) {
        return create(material, 1, metadata);
    }

    public static Item create(String material, int amount) {
        return create(material, amount, ItemMetadata.create());
    }

    public static Item create(String material, int amount, ItemMetadata metadata) {
        Objects.requireNonNull(material, "Material is null");
        Objects.requireNonNull(metadata, "Metadata is null");
        return new Item(material, amount, metadata);
    }

    private Item(String material, int amount, ItemMetadata metadata) {
        this.material = material;
        this.amount = amount;
        this.metadata = metadata;
    }

    public String getMaterial() {
        return material;
    }

    public int getAmount() {
        return amount;
    }

    public ItemMetadata getMetadata() {
        return metadata;
    }

    public Item setMaterial(String material) {
        Objects.requireNonNull(material, "Material is null");
        return new Item(material, getAmount(), getMetadata());
    }

    public Item setAmount(int amount) {
        return new Item(getMaterial(), amount, getMetadata());
    }

    public Item setMetadata(ItemMetadata itemMetadata) {
        Objects.requireNonNull(itemMetadata, "Metadata is null");
        return new Item(getMaterial(), getAmount(), itemMetadata);
    }

    public Item replaceMetadata(Function<ItemMetadata, ItemMetadata> replaceFunction) {
        return setMetadata(replaceFunction.apply(getMetadata()));
    }

    public String getDisplayName() {
        return getMetadata().getMetadata(DefaultItemMetadataKeys.DISPLAY_NAME);
    }

    public Item setDisplayName(String displayName) {
        return replaceMetadata(itemMetadata -> displayName != null ?
                itemMetadata.setMetadata(DefaultItemMetadataKeys.DISPLAY_NAME, displayName) :
                itemMetadata.removeMetadata(DefaultItemMetadataKeys.DISPLAY_NAME)
        );
    }

    public List<String> getLore() {
        List<String> lore = getMetadata().getMetadata(DefaultItemMetadataKeys.LORE);
        return lore != null ? new ArrayList<>(lore) : new ArrayList<>();
    }

    public Item setLore(List<String> lore) {
        return replaceMetadata(itemMetadata -> lore != null ?
                itemMetadata.setMetadata(DefaultItemMetadataKeys.LORE, lore) :
                itemMetadata.removeMetadata(DefaultItemMetadataKeys.LORE)
        );
    }

    public Item addLore(String... lore) {
        if(lore.length > 0) {
            List<String> loreList = getLore();
            loreList.addAll(Arrays.asList(lore));
            return setLore(loreList);
        }
        return this;
    }
}
