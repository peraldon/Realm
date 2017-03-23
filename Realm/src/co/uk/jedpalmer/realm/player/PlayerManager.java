package co.uk.jedpalmer.realm.player;

import co.uk.jedpalmer.realm.utils.FileAccessor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerManager  <P extends Player>{
    private FileAccessor data;
    private Map<Player, PlayerProfile> playerMap = new HashMap<Player, PlayerProfile>();

    /**
     * Initialises the playerManager
     */
    public PlayerManager(FileAccessor data){
        this.data = data;
    }

    /**
     * Returns true if the player is loaded on the server
     */
    public boolean isLoaded(P player){
        return playerMap.containsKey(player);
    }

    /**
     * Returns true upon the player data of a specific data is loaded, otherwise returns false
     */
    public boolean loadPlayer(P player){

        //Is the player already loaded?
        if(playerMap.containsKey(player)){
            System.out.println("Tried to load player " + player.getName() + "'s data, even though it's already loaded!");
            return false;
        } else {

            if(data.getConfig().contains(player.getUniqueId().toString())){
                //Load player
                PlayerProfile playerProfile = new PlayerProfile(false, data, player);
                playerMap.put(player, playerProfile);

                return true;
            } else {
                //Create player profile
                PlayerProfile playerProfile = new PlayerProfile(true, data, player);
                playerMap.put(player, playerProfile);

                return true;
            }
        }
    }

    /**
     * Returns a specific player's data
     */
    public PlayerProfile getPlayer(P player){

        if(playerMap.containsKey(player)){
            //Player is safe to send
            return playerMap.get(player);
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
        attribute = "." + attribute;

        if(playerMap.containsKey(player) && getPlayer(player).getPlayerProfile().containsKey(attribute)){
            //PlayerAttribute is safe to send
            return playerMap.get(player).getPlayerProfile().get(attribute);
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

        if(playerMap.containsKey(player)){
            //Player is safe to save
            List<String> playerAttributes = new ArrayList<String>(getPlayer(player).getPlayerAttributes());

            System.out.println(player.getName() + " has " + playerAttributes.size() + " attributes to save.");

            for(int i = 0; playerAttributes.size() > i; i++){
                data.getConfig().set(uuid + "." + playerAttributes.get(i), playerMap.get(player).getPlayerAttribute(playerAttributes.get(i)));
            }

            data.saveData();

            for(int i = 0; playerAttributes.size() > i; i++){
                System.out.println("Saved " + uuid + "." + playerAttributes.get(i) + " as " + playerMap.get(player).getPlayerAttribute(playerAttributes.get(i)));
            }

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
        if(playerMap.containsKey(player)){
            //Player is safe to unload
            playerMap.remove(player);
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
        attribute = "." + attribute;
        if(playerMap.containsKey(player)){
            if(playerMap.get(player).hasAttribute(attribute)){
                playerMap.get(player).getPlayerProfile().put(attribute, number);
                return true;
            } else {
                playerMap.get(player).getPlayerProfile().put(attribute, number);
                System.out.println("Just set an attribute that isn't loaded on the player");
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
        attribute = "." + attribute;
        if(playerMap.get(player).hasAttribute(attribute)){
            playerMap.get(player).getPlayerProfile().put(attribute, playerMap.get(player).getPlayerAttribute(attribute) + number);
            return true;
        } else {
            playerMap.get(player).getPlayerProfile().put(attribute, number);
            return false;
        }
    }

    /**
     * Decreases a players attribute, returns false if it creates a new attribute
     */
    public boolean decreasePlayerAttribute(P player, String attribute, Long number){
        attribute = "." + attribute;
        if(playerMap.get(player).hasAttribute(attribute)){
            playerMap.get(player).getPlayerProfile().put(attribute, playerMap.get(player).getPlayerAttribute(attribute) - number);
            return true;
        } else {
            playerMap.get(player).getPlayerProfile().put(attribute, number);
            return false;
        }
    }
}
