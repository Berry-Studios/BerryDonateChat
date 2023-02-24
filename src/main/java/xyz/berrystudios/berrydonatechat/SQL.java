package xyz.berrystudios.berrydonatechat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQL {

    private final Connection connection;

    public SQL(Config config) throws SQLException {
        String host = config.getOrSet("save-toggled.host", "");
        int port = config.getOrSet("save-toggled.port", 0);
        String username = config.getOrSet("save-toggled.username", "");
        String password = config.getOrSet("save-toggled.password", "");
        String database = config.getOrSet("save-toggled.database", "");
        String url = "jdbc:mysql://" + host + ":" + port + "/" + database;

        synchronized (this) {
            connection = DriverManager.getConnection(url, username, password);
            BerryDonateChat.getPlugin().getLogger().info("Connected to MySQL Database!");
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
