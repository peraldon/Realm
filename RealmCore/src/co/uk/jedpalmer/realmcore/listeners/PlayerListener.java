package co.uk.jedpalmer.realmcore.listeners;

import co.uk.jedpalmer.realmcore.player.PlayerManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener{

    private PlayerManager playerManager;

    public PlayerListener(PlayerManager playerManager){
        this.playerManager = playerManager;
    }

    /**
     * Loads the player's profile on join
     */
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        playerManager.loadPlayer(event.getPlayer());
    }

    /**
     * Saves and then unloads the player on quit
     */
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        playerManager.savePlayer(event.getPlayer());
        playerManager.unloadPlayer(event.getPlayer());

    }

}
