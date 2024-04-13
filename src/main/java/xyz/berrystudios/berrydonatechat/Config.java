package xyz.berrystudios.berrydonatechat;

import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Config {

    private final File file;
    private Configuration configuration;

    public Config(String configPath) {
        this.file = new File(BerryDonateChat.getPlugin().getDataFolder() + "/" + configPath);

        try {
            createAndLoadConfig();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void createAndLoadConfig() throws IOException {
        if (!this.file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
        }
        this.configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
    }

    /**
     * <b>Make sure to save the config after you're done with all changes!</b>
     */
    public void set(String path, Object value) {
        configuration.set(path, value);
    }

    /**
     * <b>If value is being set, it'll auto save.</b>
     */
    @SuppressWarnings("unchecked")
    @NotNull
    public <T> T getOrSet(String path, T value) {
        Object o = configuration.get(path);
        if (o == null || o.equals("")) {
            configuration.set(path, value);
            try {
                save();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return value;
        } else return (T) o;
    }

    @Nullable
    public List<String> getStringList(String path) {
        return configuration.getStringList(path);
    }

    public void save() throws IOException {
        ConfigurationProvider.getProvider(YamlConfiguration.class).save(configuration, file);
    }
}
