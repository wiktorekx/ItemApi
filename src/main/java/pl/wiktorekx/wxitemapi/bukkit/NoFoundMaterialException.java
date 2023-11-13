package pl.wiktorekx.wxitemapi.bukkit;

public class NoFoundMaterialException extends RuntimeException {
    private final String materialName;

    public NoFoundMaterialException(String materialName) {
        this.materialName = materialName;
    }

    public NoFoundMaterialException(String message, String materialName) {
        super(message);
        this.materialName = materialName;
    }

    public NoFoundMaterialException(String message, Throwable cause, String materialName) {
        super(message, cause);
        this.materialName = materialName;
    }

    public NoFoundMaterialException(Throwable cause, String materialName) {
        super(cause);
        this.materialName = materialName;
    }

    public NoFoundMaterialException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String materialName) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.materialName = materialName;
    }

    public String getMaterialName() {
        return materialName;
    }
}
