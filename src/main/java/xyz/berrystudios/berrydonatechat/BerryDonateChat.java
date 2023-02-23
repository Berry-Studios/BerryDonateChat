package xyz.berrystudios.berrydonatechat;

import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public final class BerryDonateChat extends Plugin implements Listener {

    private final Logger logger = super.getLogger();
    private static BerryDonateChat plugin;

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
                » Website: https://berrystudios.xyz/
                » Twitter: https://twitter.com/berrystud1os
                » Discord: https://discord.gg/JgPqJqk""");

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
                » Website: https://berrystudios.xyz/
                » Twitter: https://twitter.com/berrystud1os
                » Discord: https://discord.gg/JgPqJqk""");
    }

    public static BerryDonateChat getPlugin() {
        return plugin;
    }

    public List<String> getToggled() {
        return toggled;
    }

    @EventHandler
    public void on(PlayerDisconnectEvent event) {
        BerryDonateChat.getPlugin().getToggled().remove(event.getPlayer().getName());
    }

}
