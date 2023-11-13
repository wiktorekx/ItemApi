package pl.wiktorekx.wxitemapi;

import pl.wiktorekx.wxitemapi.metadata.DefaultItemMetadataKeys;
import pl.wiktorekx.wxitemapi.metadata.ItemMetadata;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public final class Item {
    private String material;
    private int amount = 1;
    private int data;
    private ItemMetadata itemMetadata;

    public Item() {
        this("AIR");
    }

    public Item(String material) {
        setMaterial(material);
    }

    public Item(String material, int amount) {
        setMaterial(material);
        setAmount(amount);
    }

    public Item(String material, int amount, ItemMetadata metadata) {
        setMaterial(material);
        setAmount(amount);
        setMetadata(metadata);
    }

    public Item(String material, int amount, int data) {
        setMaterial(material);
        setAmount(amount);
        setData(data);
    }

    public Item(String material, int amount, int data, ItemMetadata metadata) {
        setMaterial(material);
        setAmount(amount);
        setData(data);
        setMetadata(metadata);
    }

    public String getMaterial() {
        return material;
    }

    public Item setMaterial(String material) {
        this.material = Objects.requireNonNull(material, "material is null");
        return this;
    }

    public int getAmount() {
        return amount;
    }

    public Item setAmount(int amount) {
        this.amount = amount;
        return this;
    }

    public int getData() {
        return data;
    }

    public Item setData(int data) {
        this.data = data;
        return this;
    }

    public ItemMetadata getMetadata() {
        if(itemMetadata == null) itemMetadata = new ItemMetadata();
        return itemMetadata;
    }

    public Item setMetadata(ItemMetadata metadata) {
        this.itemMetadata = Objects.requireNonNull(metadata, "metadata is null");
        return this;
    }

    public String getDisplayName() {
        return getMetadata().getMetadata(DefaultItemMetadataKeys.DISPLAY_NAME);
    }

    public Item setDisplayName(String displayName) {
        if(displayName != null) {
            getMetadata().setMetadata(DefaultItemMetadataKeys.DISPLAY_NAME, displayName);
        } else {
            getMetadata().removeMetadata(DefaultItemMetadataKeys.DISPLAY_NAME);
        }
        return this;
    }

    public List<String> getLore() {
        List<String> lore = getMetadata().getMetadata(DefaultItemMetadataKeys.LORE);
        return lore != null ? new ArrayList<>(lore) : new ArrayList<>();
    }

    public Item setLore(List<String> lore) {
        if(lore != null) {
            getMetadata().setMetadata(DefaultItemMetadataKeys.LORE, new ArrayList<>(lore));
        } else {
            getMetadata().removeMetadata(DefaultItemMetadataKeys.LORE);
        }
        return this;
    }

    public Item addLore(String... lore) {
        if(lore.length > 0) {
            List<String> loreList = getLore();
            loreList.addAll(Arrays.asList(lore));
            setLore(loreList);
        }
        return this;
    }

    public Item clone() {
        return new Item(getMaterial(), getAmount(), getData(), getMetadata().clone());
    }
}
