package pl.wiktorekx.wxitemapi.bukkit;

import org.bukkit.Material;

import java.util.Objects;

public final class BukkitMaterialSerializer {
    private BukkitMaterialSerializer() {
        throw new RuntimeException();
    }

    public static Material getMaterial(String material) {
        Material bukkitMaterial = Material.getMaterial(Objects.requireNonNull(material, "material is null"));
        if(bukkitMaterial == null) throw new NoFoundMaterialException("not found material " + material, material);
        return bukkitMaterial;
    }
}
