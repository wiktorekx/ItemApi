package pl.wiktorekx.wxitemapi.metadata;

import java.util.*;
import java.util.function.BiConsumer;

public class ItemMetadata {
    private final Map<ItemMetadataKey<Object>, Object> metadataMap = new HashMap<>();

    @SuppressWarnings("unchecked")
    public <T> T getMetadata(ItemMetadataKey<T> key) {
        return (T) metadataMap.get(Objects.requireNonNull(key, "key is null"));
    }

    public boolean hasMetadata(ItemMetadataKey<?> key){
        return metadataMap.containsKey(Objects.requireNonNull(key, "key is null"));
    }

    @SuppressWarnings("unchecked")
    public <T> void setMetadata(ItemMetadataKey<T> key, T value) {
        metadataMap.put((ItemMetadataKey<Object>) Objects.requireNonNull(key, "key is null"), Objects.requireNonNull(value, "value is null"));
    }

    public void removeMetadata(ItemMetadataKey<?> key) {
        metadataMap.remove(Objects.requireNonNull(key, "key is null"));
    }

    public void forEach(BiConsumer<ItemMetadataKey<Object>, Object> consumer) {
        Objects.requireNonNull(consumer, "consumer is null");
        for(Map.Entry<ItemMetadataKey<Object>, Object> entry : entrySet()){
            consumer.accept(entry.getKey(), entry.getValue());
        }
    }

    public Set<Map.Entry<ItemMetadataKey<Object>, Object>> entrySet() {
        return metadataMap.entrySet();
    }

    public void clear() {
        metadataMap.clear();
    }

    public ItemMetadata clone() {
        ItemMetadata itemMetadata = new ItemMetadata();
        forEach(itemMetadata::setMetadata);
        return itemMetadata;
    }

    @Override
    public String toString() {
        StringJoiner stringJoiner = new StringJoiner(", ");
        forEach((key, value) -> stringJoiner.add(key.getName() + "=\"" + value + "\""));
        return "ItemMetadata{" + stringJoiner + "}";
    }
}
