package pl.wiktorekx.wxitemapi.utils;

import org.bukkit.Bukkit;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MinecraftVersion {
    private static final Pattern VERSION_PATTERN = Pattern.compile(".*\\(MC: (.*)\\)");
    private static MinecraftVersion INSTANCE;
    private final int major;
    private final int minor;
    private final int path;
    private final String nmsVersion;

    public static boolean support(int minor){
        return getMinor() >= minor;
    }

    public static boolean support(int major, int minor, int path){
        return getMajor() >= major && getMinor() >= minor && getPath() >= path;
    }

    public static int getMajor() {
        return getInstance().major;
    }

    public static int getMinor() {
        return getInstance().minor;
    }

    public static int getPath() {
        return getInstance().path;
    }

    public static String getNmsVersion() {
        return getInstance().nmsVersion;
    }

    public static String getVersion(){
        return getMajor() + "." + getMinor() + (getPath() == 0 ? "" : ("." + getPath()));
    }

    private static MinecraftVersion getInstance(){
        if(INSTANCE == null) INSTANCE = new MinecraftVersion();
        return INSTANCE;
    }

    private MinecraftVersion() {
        Matcher matcher = VERSION_PATTERN.matcher(Bukkit.getServer().getVersion());
        if(!matcher.matches()) throw new RuntimeException("Version no match");
        String[] version = matcher.group(1).split("\\.", 3);
        this.major = Integer.parseInt(version[0]);
        this.minor = version.length > 1 ? Integer.parseInt(version[1]) : 0;
        this.path = version.length > 2 ? Integer.parseInt(version[2]) : 0;
        this.nmsVersion = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
    }

    @Override
    public String toString() {
        return "MinecraftVersion(" + getVersion() + ", " + getNmsVersion() + ")";
    }
}