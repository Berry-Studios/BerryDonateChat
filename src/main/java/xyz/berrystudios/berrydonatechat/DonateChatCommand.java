package xyz.berrystudios.berrydonatechat;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.ArrayList;
import java.util.Arrays;

public class DonateChatCommand extends Command {

    private final String prefix;
    private final String chat;
    private final String toggleEnable;
    private final String toggleDisable;
    private final String toggleNotEnabled;
    private final String commandUsage;

    public DonateChatCommand() {
        super(
                BerryDonateChat.getPlugin().getConfig().getOrSet("command.command", "donatechat"),
                BerryDonateChat.COMMAND_PERMISSION,
                BerryDonateChat.getPlugin().getConfig().getOrSet("command.aliases", new ArrayList<>(Arrays.asList("dc", "dchat")))
                        .toArray(new String[0])
        );

        this.prefix = BerryDonateChat.getPlugin().getConfig().getOrSet("prefix", "&2&lDonate-Chat");
        this.chat = BerryDonateChat.getPlugin().getConfig().getOrSet("chat", "{prefix} &f{player_name} &7» &f{message}");
        this.toggleEnable = BerryDonateChat.getPlugin().getConfig().getOrSet("user-toggle-message.enabled", "{prefix} &7» &aEnabled donate chat!");
        this.toggleDisable = BerryDonateChat.getPlugin().getConfig().getOrSet("user-toggle-message.disabled", "{prefix} &7» &aDisabled donate chat!");
        this.toggleNotEnabled = BerryDonateChat.getPlugin().getConfig().getOrSet("user-toggle-message.not-enabled", "{prefix} &7» &cYou have disabled donate chat. Toggle it again to send messages!");
        this.commandUsage = BerryDonateChat.getPlugin().getConfig().getOrSet("command-usage", "{prefix} &7» &fCommand usages are:\n" +
                "&a/donatechat toggle &7- &fToggle enable/disable to view donate chat.\n" +
                "&a/donatechat [message] &7- &fSend your message through donate chat.");
    }

    @SuppressWarnings("deprecation")
    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer player)) {
            sender.sendMessage("This command can only be executed by players!");
            return;
        }

        if (args.length == 1 && args[0].equalsIgnoreCase("toggle")) {
            if (BerryDonateChat.getPlugin().getToggled().contains(player.getName())) {
                BerryDonateChat.getPlugin().toggle(player.getName(), true);
                player.sendMessage(this.format(toggleEnable));
            } else {
                BerryDonateChat.getPlugin().toggle(player.getName(), false);
                player.sendMessage(this.format(toggleDisable));
            }
            return;
        }

        if (BerryDonateChat.getPlugin().getToggled().contains(player.getName())) {
            player.sendMessage(this.format(toggleNotEnabled));
            return;
        }

        if (args.length >= 1) {
            String message = ChatColor.translateAlternateColorCodes('&', String.join(" ", args));
            if (!player.hasPermission(BerryDonateChat.COLOR_PERMISSION)) {
                message = message.replaceAll(String.valueOf(ChatColor.COLOR_CHAR), "");
            }

            String finalMessage = message;
            BerryDonateChat.getPlugin().getProxy().getPlayers().forEach(proxiedPlayer -> {
                if (proxiedPlayer.hasPermission(BerryDonateChat.COMMAND_PERMISSION) && !BerryDonateChat.getPlugin().getToggled().contains(proxiedPlayer.getName())) {
                    proxiedPlayer.sendMessage(this.formatChat(chat, finalMessage, player));
                }
            });
        } else {
            player.sendMessage(this.format(commandUsage));
        }
    }

    private String format(String message) {
        String formattedMessage = ChatColor.translateAlternateColorCodes('&', message);
        String formattedPrefix = ChatColor.translateAlternateColorCodes('&', prefix);
        return formattedMessage
                .replace("{prefix}", formattedPrefix);
    }

    private String formatChat(String chat, String message, ProxiedPlayer player) {
        String formattedChat = ChatColor.translateAlternateColorCodes('&', chat);
        String formattedPrefix = ChatColor.translateAlternateColorCodes('&', prefix);

        return formattedChat
                .replace("{prefix}", formattedPrefix)
                .replace("{player_name}", player.getDisplayName())
                .replace("{message}", message);
    }
}
