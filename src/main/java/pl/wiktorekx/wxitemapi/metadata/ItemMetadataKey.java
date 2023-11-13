package pl.wiktorekx.wxitemapi.metadata;

import java.util.Objects;

public final class ItemMetadataKey<T> {
    private final String name;

    public static <T> ItemMetadataKey<T> create(String name) {
        return new ItemMetadataKey<>(Objects.requireNonNull(name, "name is null"));
    }

    private ItemMetadataKey(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "ItemMetadataKey(\"" + getName() + "\")";
    }
}
