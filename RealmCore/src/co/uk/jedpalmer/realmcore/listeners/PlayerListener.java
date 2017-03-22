package co.uk.jedpalmer.realmcore.listeners;

import co.uk.jedpalmer.realmcore.player.PlayerManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Created by peraldon on 22/03/2017.
 */
public class PlayerListener implements Listener{

    PlayerManager playerManager;

    public PlayerListener(PlayerManager playerManager){
        this.playerManager = playerManager;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        playerManager.loadPlayer(event.getPlayer());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        playerManager.savePlayer(event.getPlayer());
        playerManager.unloadPlayer(event.getPlayer());

        playerManager.getPlayer(event.getPlayer());
        System.out.println("There are " + playerManager.loadedPlayers() + " loaded players.");
    }

}
