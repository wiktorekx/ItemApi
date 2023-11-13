package pl.wiktorekx.wxitemapi.utils;

import org.bukkit.Bukkit;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MinecraftVersion {
    private static MinecraftVersion INSTANCE;
    private static final Pattern VERSION_PATTERN = Pattern.compile(".*\\(MC: (.*)\\)");
    private final int major;
    private final int minor;
    private final int path;
    private final String nmsVersion;

    public static MinecraftVersion getInstance(){
        if(INSTANCE == null) INSTANCE = new MinecraftVersion();
        return INSTANCE;
    }

    public static boolean support(int minor){
        return getInstance().getMinor() >= minor;
    }

    public static boolean support(int major, int minor, int path){
        MinecraftVersion minecraftVersion = getInstance();
        return minecraftVersion.getMajor() >= major && minecraftVersion.getMinor() >= minor && minecraftVersion.getPath() >= path;
    }

    private MinecraftVersion() {
        Matcher matcher = VERSION_PATTERN.matcher(Bukkit.getServer().getVersion());
        if(!matcher.matches()) throw new RuntimeException("Version no math");
        String[] version = matcher.group(1).split("\\.", 3);
        this.major = Integer.parseInt(version[0]);
        this.minor = version.length > 1 ? Integer.parseInt(version[1]) : 0;
        this.path = version.length > 2 ? Integer.parseInt(version[2]) : 0;
        this.nmsVersion = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
    }

    public int getMajor() {
        return major;
    }

    public int getMinor() {
        return minor;
    }

    public int getPath() {
        return path;
    }

    public String getVersion(){
        return major + "." + minor + (path == 0 ? "" : ("." + path));
    }

    public String getNmsVersion() {
        return nmsVersion;
    }

    @Override
    public String toString() {
        return "MinecraftVersion(" + getVersion() + ", " + getNmsVersion() + ")";
    }
}