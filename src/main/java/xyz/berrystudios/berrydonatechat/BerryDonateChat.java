package xyz.berrystudios.berrydonatechat;

import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@SuppressWarnings("ConstantConditions")
public final class BerryDonateChat extends Plugin implements Listener {

    private final Logger logger = super.getLogger();
    private static BerryDonateChat plugin;
    private static Config config;

    //Saving toggled users
    private String saveType;
    private Config toggledConfig;
    private SQL mySql;

    public static final String COMMAND_PERMISSION = "berrydonatechat.command";
    public static final String COLOR_PERMISSION = "berrydonatechat.command.color";
    private final List<String> toggled = new ArrayList<>();

    @Override
    public void onEnable() {
        plugin = this;
        logger.info("""

                 /$$$$$$$                                                 /$$$$$$   /$$                     /$$ /$$                   \s
                | $$__  $$                                               /$$__  $$ | $$                    | $$|__/                   \s
                | $$  \\ $$  /$$$$$$   /$$$$$$   /$$$$$$  /$$   /$$      | $$  \\__//$$$$$$   /$$   /$$  /$$$$$$$ /$$  /$$$$$$   /$$$$$$$
                | $$$$$$$  /$$__  $$ /$$__  $$ /$$__  $$| $$  | $$      |  $$$$$$|_  $$_/  | $$  | $$ /$$__  $$| $$ /$$__  $$ /$$_____/
                | $$__  $$| $$$$$$$$| $$  \\__/| $$  \\__/| $$  | $$       \\____  $$ | $$    | $$  | $$| $$  | $$| $$| $$  \\ $$|  $$$$$$\s
                | $$  \\ $$| $$_____/| $$      | $$      | $$  | $$       /$$  \\ $$ | $$ /$$| $$  | $$| $$  | $$| $$| $$  | $$ \\____  $$
                | $$$$$$$/|  $$$$$$$| $$      | $$      |  $$$$$$$      |  $$$$$$/ |  $$$$/|  $$$$$$/|  $$$$$$$| $$|  $$$$$$/ /$$$$$$$/
                |_______/  \\_______/|__/      |__/       \\____  $$       \\______/   \\___/   \\______/  \\_______/|__/ \\______/ |_______/\s
                                                         /$$  | $$                                                                    \s
                                                        |  $$$$$$/                                                                    \s
                                                         \\______/                                                                     \s
                """);
        logger.info("""
                Made by Berry Studios. Check out our products at:
                » Twitter: https://twitter.com/berrystud1os
                » Discord: https://discord.gg/JgPqJqk""");

        config = new Config("config.yml");
        saveType = config.getOrSet("save-toggled.mode", "yml");
        if (saveType.equalsIgnoreCase("mysql")) {
            try {
                mySql = new SQL(config);
                mySql.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS toggledusers (" +
                        "id INTEGER PRIMARY KEY AUTO_INCREMENT," +
                        "player VARCHAR(16) NOT NULL);").executeUpdate();
                ResultSet result = mySql.getConnection().prepareStatement("SELECT * FROM toggledusers;").executeQuery();
                while (result.next()) toggled.add(result.getString("player"));
            } catch (SQLException err) {
                err.printStackTrace();
                getLogger().severe("Failed to connect to MySQL server! Please recheck your provided data or use the yml mode to save toggled users. Stopping server...");
                getProxy().stop();
            }
        } else if (saveType.equalsIgnoreCase("yml")) {
            toggledConfig = new Config("toggled-users.yml");
            if (!toggledConfig.getStringList("users").isEmpty()) {
                toggled.addAll(toggledConfig.getStringList("users"));
            }
        } else {
            getLogger().severe("Invalid mode provided for saving toggled users! Stopping server...");
            getProxy().stop();
        }

        getProxy().getPluginManager().registerCommand(this, new DonateChatCommand());
        getProxy().getPluginManager().registerListener(this, this);
    }

    @Override
    public void onDisable() {
        logger.info("""

                 /$$$$$$$                                                 /$$$$$$   /$$                     /$$ /$$                   \s
                | $$__  $$                                               /$$__  $$ | $$                    | $$|__/                   \s
                | $$  \\ $$  /$$$$$$   /$$$$$$   /$$$$$$  /$$   /$$      | $$  \\__//$$$$$$   /$$   /$$  /$$$$$$$ /$$  /$$$$$$   /$$$$$$$
                | $$$$$$$  /$$__  $$ /$$__  $$ /$$__  $$| $$  | $$      |  $$$$$$|_  $$_/  | $$  | $$ /$$__  $$| $$ /$$__  $$ /$$_____/
                | $$__  $$| $$$$$$$$| $$  \\__/| $$  \\__/| $$  | $$       \\____  $$ | $$    | $$  | $$| $$  | $$| $$| $$  \\ $$|  $$$$$$\s
                | $$  \\ $$| $$_____/| $$      | $$      | $$  | $$       /$$  \\ $$ | $$ /$$| $$  | $$| $$  | $$| $$| $$  | $$ \\____  $$
                | $$$$$$$/|  $$$$$$$| $$      | $$      |  $$$$$$$      |  $$$$$$/ |  $$$$/|  $$$$$$/|  $$$$$$$| $$|  $$$$$$/ /$$$$$$$/
                |_______/  \\_______/|__/      |__/       \\____  $$       \\______/   \\___/   \\______/  \\_______/|__/ \\______/ |_______/\s
                                                         /$$  | $$                                                                    \s
                                                        |  $$$$$$/                                                                    \s
                                                         \\______/                                                                     \s
                """);
        logger.info("""
                Thank you for trusting Berry Studios. Check out our products at:
                » Twitter: https://twitter.com/berrystud1os
                » Discord: https://discord.gg/JgPqJqk""");

        if (saveType.equalsIgnoreCase("yml")) {
            toggledConfig.set("users", toggled);
            try {
                toggledConfig.save();
            } catch (IOException err) {
                err.printStackTrace();
            }
        }
    }

    public List<String> getToggled() {
        return toggled;
    }

    public Config getConfig() {
        return config;
    }

    public void toggle(String player, boolean remove) {
        if (!remove) {
            if (saveType.equalsIgnoreCase("mysql")) {
                try {
                    mySql.getConnection().prepareStatement(String.format("INSERT INTO toggledusers (player) VALUES ('%s');", player)).executeUpdate();
                } catch (SQLException err) {
                    err.printStackTrace();
                }
            }
            toggled.add(player);
        } else {
            if (saveType.equalsIgnoreCase("mysql")) {
                try {
                    mySql.getConnection().prepareStatement(String.format("DELETE FROM toggledusers WHERE player = '%s';", player)).executeUpdate();
                } catch (SQLException err) {
                    err.printStackTrace();
                }
            }
            toggled.remove(player);
        }
    }

    public static BerryDonateChat getPlugin() {
        return plugin;
    }
}
