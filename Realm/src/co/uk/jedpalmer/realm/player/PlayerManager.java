package co.uk.jedpalmer.realm.player;

import co.uk.jedpalmer.realm.utils.FileAccessor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerManager  <P extends Player>{
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
    public boolean isLoaded(P player){

        if(playerMap.containsKey(player.getUniqueId().toString())){
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns true upon the player data of a specific data is loaded, otherwise returns false
     */
    public boolean loadPlayer(P player){

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
    public Map<String, Long> getPlayer(P player){

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
     * Returns a specific player's attribute
     */
    public Long getPlayerAttribute(P player, String attribute){

        if(playerMap.containsKey(player.getUniqueId().toString()) && getPlayer(player).containsKey(attribute)){
            //PlayerAttribute is safe to send
            return playerMap.get(player.getUniqueId().toString()).get(attribute);
        } else {
            //Player isn't safe to send
            System.out.println("Tried to grab player " + player.getName() + "'s attribute, but it's not available");
            return null;
        }
    }
    /**
     * Saves a specific player, then writes new data to disk
     * Returns true if successful
     */
    public boolean savePlayer(P player){
        String uuid = player.getUniqueId().toString();

        if(playerMap.containsKey(player.getUniqueId().toString())){
            //Player is safe to save
            List<String> playerAttributes = new ArrayList<String>(getPlayer(player).keySet());

            System.out.println(player.getName() + " has " + playerAttributes.size() + " attributes to save.");

            for(int i = 0; playerAttributes.size() > i; i++){
                data.getConfig().set(uuid + "." + playerAttributes.get(i), playerMap.get(uuid).get(playerAttributes.get(i)));
            }

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
    public boolean unloadPlayer(P player){
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

    /**
     * Sets a players attribute, returns false if it's a new attribute
     */
    public boolean setPlayerAttribute(P player, String attribute, Long number){
        if(playerMap.containsKey(player)){
            if(playerMap.get(getPlayer(player)).containsKey(attribute)){
                playerMap.get(getPlayer(player)).put(attribute, number);
                return true;
            } else {
                playerMap.get(getPlayer(player)).put(attribute, number);
                System.out.println("Failed to setPlayerAttribute as attribute isn't loaded");
                return false;
            }
        } else {
            System.out.println("Failed to setPlayerAttribute as player isn't loaded");
            return false;
        }
    }

    /**
     * Adds to a players attribute, returns false if it creates a new attribute
     */
    public boolean increasePlayerAttribute(P player, String attribute, Long number){
        if(playerMap.get(getPlayer(player)).containsKey(attribute)){
            playerMap.get(getPlayer(player)).put(attribute, playerMap.get(getPlayer(player)).get(attribute) + number);
            return true;
        } else {
            playerMap.get(getPlayer(player)).put(attribute, number);
            return false;
        }
    }

    /**
     * Decreases a players attribute, returns false if it creates a new attribute
     */
    public boolean decreasePlayerAttribute(P player, String attribute, Long number){
        if(playerMap.get(getPlayer(player)).containsKey(attribute)){
            playerMap.get(getPlayer(player)).put(attribute, playerMap.get(getPlayer(player)).get(attribute) - number);
            return true;
        } else {
            playerMap.get(getPlayer(player)).put(attribute, number - number);
            return false;
        }
    }

}
