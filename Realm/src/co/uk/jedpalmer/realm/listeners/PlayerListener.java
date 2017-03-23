package co.uk.jedpalmer.realm.listeners;

import co.uk.jedpalmer.realm.chat.ServerChat;
import co.uk.jedpalmer.realm.player.PlayerManager;
import co.uk.jedpalmer.realm.utils.ReadableDate;
import co.uk.jedpalmer.realm.utils.ReadableDuration;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAchievementAwardedEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener{
    private PlayerManager playerManager;
    private ServerChat serverChat;
    private ReadableDate readableDate;
    private ReadableDuration readableDuration;

    public PlayerListener(PlayerManager playerManager){
        this.playerManager = playerManager;
        this.serverChat = new ServerChat();
        this.readableDate = new ReadableDate();
        this.readableDuration = new ReadableDuration();
    }

    /**
     * Loads the player's profile on join
     */
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        playerManager.loadPlayer(event.getPlayer());

        //Send player last login, and then update
        if(playerManager.getPlayerAttribute(event.getPlayer(), "lastLogin") != 0){
            serverChat.sendPlayerMessage(ChatColor.BLUE + "You last logged on " + readableDate.getReadableDate(playerManager.getPlayerAttribute(event.getPlayer(), "lastLogin")), event.getPlayer());
            serverChat.sendPlayerMessage(ChatColor.BLUE + "You have played for a total of " + readableDuration.getReadableDuration(playerManager.getPlayerAttribute(event.getPlayer(), "totalPlaytime")), event.getPlayer());
        }
        playerManager.setPlayerAttribute(event.getPlayer(), "lastLogin", System.currentTimeMillis());

        serverChat.sendLocalisedServerMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + event.getPlayer().getName() + " has woken up nearby.", event.getPlayer().getLocation(), 20);




        //Let's stop the join message
        event.setJoinMessage("");
    }

    /**
     * Saves and then unloads the player on quit
     */
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        playerManager.increasePlayerAttribute(event.getPlayer(), "totalPlaytime", System.currentTimeMillis() - playerManager.getPlayerAttribute(event.getPlayer(), "lastLogin"));
        playerManager.savePlayer(event.getPlayer());
        playerManager.unloadPlayer(event.getPlayer());

        serverChat.sendLocalisedServerMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + event.getPlayer().getName() + " has gone to sleep.", event.getPlayer().getLocation(), 20);

        //Let's stop the leave message
        event.setQuitMessage("");

    }

    @EventHandler
    public void onPlayerAchievementAwarded(PlayerAchievementAwardedEvent event){
        event.setCancelled(true);
    }
}
