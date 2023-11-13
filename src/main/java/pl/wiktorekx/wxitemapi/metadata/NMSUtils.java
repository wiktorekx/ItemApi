package pl.wiktorekx.wxitemapi.metadata;

import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerProfile;
import pl.wiktorekx.wxitemapi.utils.MinecraftVersion;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.UUID;

public final class NMSUtils {
    private static final Class<?> PLAYER_PROFILE_CLASS;
    private static final Class<?> GAME_PROFILE_CLASS;
    private static final Class<?> PROPERTY_CLASS;
    private static final Class<?> BOOK_META_CLASS;

    static {
        try {
            String nmsVersion = MinecraftVersion.getInstance().getNmsVersion();
            PLAYER_PROFILE_CLASS = Class.forName("org.bukkit.craftbukkit." + nmsVersion + ".profile.CraftPlayerProfile");
            GAME_PROFILE_CLASS = Class.forName("com.mojang.authlib.GameProfile");
            PROPERTY_CLASS = Class.forName("com.mojang.authlib.properties.Property");
            BOOK_META_CLASS = Class.forName("org.bukkit.craftbukkit." + nmsVersion + ".inventory.CraftMetaBook");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private NMSUtils() {
        throw new RuntimeException();
    }

    public static void applySkullValue(SkullMeta skullMeta, String value) {
        try {
            Object property = PROPERTY_CLASS.getConstructor(String.class, String.class)
                    .newInstance("textures", value);
            Object gameProfile = GAME_PROFILE_CLASS.getConstructor(UUID.class, String.class)
                    .newInstance(UUID.randomUUID(), "");
            Object propertyMap = GAME_PROFILE_CLASS.getMethod("getProperties").invoke(gameProfile);
            propertyMap.getClass().getMethod("put", Object.class, Object.class)
                    .invoke(propertyMap, "textures", property);
            if(MinecraftVersion.support(20)) {
                PlayerProfile playerProfile = (PlayerProfile) PLAYER_PROFILE_CLASS.getConstructor(GAME_PROFILE_CLASS)
                        .newInstance(gameProfile);
                playerProfile.update();
                skullMeta.setOwnerProfile(playerProfile);
            } else {
                Field profileField = skullMeta.getClass().getDeclaredField("profile");
                profileField.setAccessible(true);
                profileField.set(skullMeta, gameProfile);
            }
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException |
                 NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setBookPagesComponent(BookMeta bookMeta, List<String> pagesComponent) {
        try {
            Field pagesField = BOOK_META_CLASS.getDeclaredField("pages");
            pagesField.setAccessible(true);
            pagesField.set(bookMeta, pagesComponent);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
