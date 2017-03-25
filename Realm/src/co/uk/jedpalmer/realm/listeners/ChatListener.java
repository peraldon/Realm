package co.uk.jedpalmer.realm.listeners;

import co.uk.jedpalmer.realm.chat.PlayerMessages;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.Plugin;


public class ChatListener implements Listener {
    private Plugin plugin;
    private FileConfiguration config;
    private PlayerMessages playerMessages;


    /**
     * Initilisation of ChatListener
     */
    public ChatListener(Plugin instance) {
        this.plugin = instance;
        this.config = plugin.getConfig();
        this.playerMessages = new PlayerMessages(plugin);
    }

    /**
     * Intercepts player messages, and looks for channel types
     * Will always cancel the initial event
     */
    @EventHandler
    public void AsyncPlayerChat(AsyncPlayerChatEvent event) {

        if(playerMessages.sendPlayerMessage(event.getPlayer(), event.getMessage()) == 0){
            event.getPlayer().sendRawMessage(ChatColor.GRAY + "No one can hear you...");
        }

    }
}



