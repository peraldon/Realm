package co.uk.jedpalmer.realmchat.listeners;

import co.uk.jedpalmer.realmchat.chat.ServerChat;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAchievementAwardedEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

/**
 * Listens to general player activity
 */
public class PlayerListener implements Listener{
    Plugin plugin;

    ServerChat serverChat = new ServerChat();

    //Initialisation
    public PlayerListener(Plugin instance){
        plugin = instance;
    }

    /**
     * Cancels the normal playerJoin message, and replaces it with a localised message
     */
    @EventHandler
    public void onPlayerJoin (PlayerJoinEvent event){

        serverChat.sendLocalisedServerMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + event.getPlayer().getName() + " has woken up nearby.",event.getPlayer().getLocation(),20);

        //Let's stop the join message
        event.setJoinMessage("");
    }

    /**
     * Cancels the normal playerQuit message, and replaces it with a localised message
     */
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){

        serverChat.sendLocalisedServerMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + event.getPlayer().getName() + " has gone to sleep.",event.getPlayer().getLocation(),20);

        //Let's stop the leave message
        event.setQuitMessage("");
    }

    /**
     * Stops AchievementGet messages
     */
    @EventHandler
    public void onPlayerAchievementAwarded(PlayerAchievementAwardedEvent event){
        event.setCancelled(true);
    }
}
