package co.uk.jedpalmer.realmcore.player;

import co.uk.jedpalmer.realmcore.utils.FileAccessor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;

public class PlayerManager {
    private Plugin plugin;
    private FileAccessor data;
    private Map<String, Map<String, Long>> playerMap = new HashMap<String, Map<String, Long>>();

    /**
     * Initialises the playerManager
     */
    public PlayerManager(Plugin plugin, FileAccessor data){
        this.plugin = plugin;
        this.data = data;
    }

    /**
     * Returns true if the player is loaded on the server
     */
    public boolean isLoaded(Player player){

        if(playerMap.containsKey(player.getUniqueId().toString())){
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns true upon the player data of a specific data is loaded, otherwise returns false
     */
    public boolean loadPlayer(Player player){

        //Is the player already loaded?
        if(playerMap.containsKey(player.getUniqueId().toString())){
            System.out.println("Tried to load player " + player.getName() + "'s data, even though it's already loaded!");
            return false;
        } else {

            if(data.getConfig().contains(player.getUniqueId().toString())){
                //Load player
                PlayerProfile playerProfile = new PlayerProfile(false, data, player);
                playerMap.put(player.getUniqueId().toString(), playerProfile.getPlayerProfile());
                return true;
            } else {
                //Create player profile
                PlayerProfile playerProfile = new PlayerProfile(true, data, player);
                playerMap.put(player.getUniqueId().toString(), playerProfile.getPlayerProfile());
                return true;
            }
        }
    }

    /**
     * Returns a specific player's data
     */
    public Map<String, Long> getPlayer(Player player){

        if(playerMap.containsKey(player.getUniqueId().toString())){
            //Player is safe to send
            return playerMap.get(player.getUniqueId().toString());
        } else {
            //Player isn't safe to send
            System.out.println("Tried to grab player " + player.getName() + "'s data, but it's not available");
            return null;
        }
    }
    /**
     * Saves a specific player, then writes new data to disk
     * Returns true if successful
     */
    public boolean savePlayer(Player player){

        if(playerMap.containsKey(player.getUniqueId().toString())){
            //Player is safe to save
            data.getConfig().set(player.getUniqueId().toString() + ".deaths", playerMap.get(player.getUniqueId().toString()).get("deaths"));
            data.getConfig().set(player.getUniqueId().toString() + ".kills", playerMap.get(player.getUniqueId().toString()).get("kills"));

            data.saveData();
            return true;
        } else {
            //Player isn't safe to save
            System.out.println("Tried to save player " + player.getName() + "'s data, but it's not available");
            return false;
        }
    }

    /**
     * Returns true if a specific player is successfully unloaded from the server
     */
    public boolean unloadPlayer(Player player){
        if(playerMap.containsKey(player.getUniqueId().toString())){
            //Player is safe to unload
            playerMap.remove(player.getUniqueId().toString());
            return true;
        } else {
            //Player isn't safe to save
            System.out.println("Tried to unload player " + player.getName() + "'s data, but it's not available");
            return false;
        }
    }

    /**
     * Returns how many players are currently loaded
     */
    public int loadedPlayers(){
        return playerMap.size();
    }
}
