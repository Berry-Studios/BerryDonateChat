package xyz.berrystudios.berrydonatechat;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class DonateChatCommand extends Command {

    private final String prefix = ChatColor.DARK_PURPLE + "Donate-Chat " + ChatColor.DARK_GRAY + "» ";

    public DonateChatCommand() {
        super("donatechat", BerryDonateChat.COMMAND_PERMISSION, "dchat", "dc");
    }

    @SuppressWarnings("deprecation")
    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof ProxiedPlayer player) {

            if (args.length == 1 && args[0].equalsIgnoreCase("toggle")) {
                if (BerryDonateChat.getPlugin().getToggled().contains(player.getName())) {
                    BerryDonateChat.getPlugin().getToggled().remove(player.getName());
                    player.sendMessage(prefix + ChatColor.GREEN + "Enabled donate chat!");
                } else {
                    BerryDonateChat.getPlugin().getToggled().add(player.getName());
                    player.sendMessage(prefix + ChatColor.GREEN + "Disabled donate chat!");
                }
                return;
            }

            if (BerryDonateChat.getPlugin().getToggled().contains(player.getName())) {
                player.sendMessage(prefix + ChatColor.RED + "You have disabled donate chat. Toggle it again to send messages!");
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
                        proxiedPlayer.sendMessage(prefix + player.getDisplayName() + ChatColor.DARK_GRAY + ": " + ChatColor.WHITE + finalMessage);
                    }
                });
            } else {
                player.sendMessage(prefix + ChatColor.WHITE + "Command usages are:\n" +
                        ChatColor.GREEN + "/donatechat toggle " + ChatColor.WHITE + "- " + ChatColor.GOLD + "Toggle enable/disable to view donate chat (Resets once you leave the server)\n" +
                        ChatColor.GREEN + "/donatechat [message] " + ChatColor.WHITE + "- " + ChatColor.GOLD + "Send your message through donate chat");
            }
        } else {
            sender.sendMessage("This command can only be executed by players!");
        }
    }
}
