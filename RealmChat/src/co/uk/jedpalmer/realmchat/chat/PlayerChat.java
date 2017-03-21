package co.uk.jedpalmer.realmchat.chat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;


/**
 * Deals with PlayerChat channels
 * Manages the range of chats, and visual appearance of them
 */
public class PlayerChat {
    private Plugin plugin;
    private FileConfiguration config;

    /**
     * Initialization of PlayerChat
     */
    public PlayerChat (Plugin instance){
        plugin = instance;
        config = plugin.getConfig();
    }


    /**
     * Sends a message player -> player dependant on it's type
     * Type dictates range for normal and fuzzy
     * Returns true if the message was received by another player (not the sender)
     */
    public boolean sendMessage(String message, Player sender, Player receiver, ChatTypes type) {

        if (type == ChatTypes.SHOUT) {
            //Is receiver the player?
            if (receiver.equals(sender)) {
                receiver.sendRawMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + sender.getName() + ChatColor.DARK_RED + " - " + ChatColor.RED + message.toUpperCase());
                return false;
            }

            //Send normal messages
            if (receiver.getLocation().distance(sender.getLocation()) <= config.getInt("chat.range.shout.normal")) {
                receiver.sendRawMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + sender.getName() + ChatColor.DARK_RED + " - " + ChatColor.RED + message.toUpperCase());
                return true;

                //Send fuzzy messages
            } else if (receiver.getLocation().distance(sender.getLocation()) > config.getInt("chat.range.shout.normal") && receiver.getLocation().distance(sender.getLocation()) <= config.getInt("chat.range.shout.fuzzy")) {
                receiver.sendRawMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + "??? " + ChatColor.DARK_RED + " - " + ChatColor.RED + message.toUpperCase());
                return true;
            }

        } else if (type == ChatTypes.TALK) {
            //Is receiver the player?
            if (receiver.equals(sender)) {
                receiver.sendRawMessage(ChatColor.WHITE + sender.getName() + " - " + ChatColor.GRAY + message);
                return false;
            }

            //Send normal messages
            if (receiver.getLocation().distance(sender.getLocation()) <= config.getInt("chat.range.talk.normal")) {
                receiver.sendRawMessage(ChatColor.WHITE + sender.getName() + " - " + ChatColor.GRAY + message);
                return true;

                //Send fuzzy messages
            } else if (receiver.getLocation().distance(sender.getLocation()) > config.getInt("chat.range.talk.normal") && receiver.getLocation().distance(sender.getLocation()) <= config.getInt("chat.range.talk.fuzzy")) {
                receiver.sendRawMessage(ChatColor.GRAY + "??? - " + ChatColor.DARK_GRAY + ChatColor.ITALIC + message);
                return true;
            }

        } else if (type == ChatTypes.WHISPER) {
            //Is receiver the player?
            if (receiver.equals(sender)) {
                receiver.sendRawMessage(ChatColor.GRAY + sender.getName() + " - " + ChatColor.GRAY + message);
                return false;
            }

            //Send normal messages
            if (receiver.getLocation().distance(sender.getLocation()) <= config.getInt("chat.range.whisper.normal")) {
                receiver.sendRawMessage(ChatColor.GRAY + sender.getName() + " - " + ChatColor.GRAY + message);
                return true;

                //Send fuzzy messages
            } else if (receiver.getLocation().distance(sender.getLocation()) > config.getInt("chat.range.whisper.normal") && receiver.getLocation().distance(sender.getLocation()) <= config.getInt("chat.range.whisper.fuzzy")) {
                receiver.sendRawMessage(ChatColor.GRAY + "Somebody is whispering around you.");
                return true;
            } else {
                System.out.println("Invalid chat type specified!");
                return false;
            }

        }
        return false;

    }

    /**
     * Sends a global message out to everyone on the server
     */
    public void sendGlobalMessage(String message, Player sender){
        for (Player receiver : Bukkit.getOnlinePlayers()) {
            receiver.sendRawMessage(ChatColor.AQUA + "" + ChatColor.BOLD + "[G] " + ChatColor.WHITE + sender.getName() + " - " + ChatColor.GRAY + message);
        }
    }
}
