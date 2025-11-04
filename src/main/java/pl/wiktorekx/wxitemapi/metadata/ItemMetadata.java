package pl.wiktorekx.wxitemapi.metadata;

import java.util.*;
import java.util.function.BiConsumer;

public final class ItemMetadata {
    private final Map<ItemMetadataKey<Object>, Object> metadataMap;

    public static ItemMetadata create() {
        return new ItemMetadata(new HashMap<>());
    }

    public static ItemMetadata fromMap(Map<ItemMetadataKey<Object>, Object> metadataMap) {
        return new ItemMetadata(new HashMap<>(metadataMap));
    }

    private ItemMetadata(Map<ItemMetadataKey<Object>, Object> metadataMap) {
        this.metadataMap = metadataMap;
    }

    @SuppressWarnings("unchecked")
    public <T> T getMetadata(ItemMetadataKey<T> key) {
        return (T) metadataMap.get(Objects.requireNonNull(key, "key is null"));
    }

    public boolean hasMetadata(ItemMetadataKey<?> key){
        return metadataMap.containsKey(Objects.requireNonNull(key, "key is null"));
    }

    public Map<ItemMetadataKey<Object>, Object> toMap() {
        return new HashMap<>(metadataMap);
    }

    @SuppressWarnings("unchecked")
    public <T> ItemMetadata setMetadata(ItemMetadataKey<T> key, T value) {
        Map<ItemMetadataKey<Object>, Object> map = toMap();
        map.put((ItemMetadataKey<Object>) Objects.requireNonNull(key, "key is null"), Objects.requireNonNull(value, "value is null"));
        return new ItemMetadata(map);
    }

    public ItemMetadata removeMetadata(ItemMetadataKey<?> key) {
        if(!hasMetadata(key)) {
            return this;
        }
        Map<ItemMetadataKey<Object>, Object> map = toMap();
        map.remove(Objects.requireNonNull(key, "key is null"));
        return new ItemMetadata(map);
    }

    @Override
    public String toString() {
        StringJoiner stringJoiner = new StringJoiner(", ");
        toMap().forEach((key, value) -> stringJoiner.add(key.getName() + "=\"" + value + "\""));
        return "ItemMetadata{" + stringJoiner + "}";
    }
}
