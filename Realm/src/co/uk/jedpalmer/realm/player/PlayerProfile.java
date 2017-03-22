package co.uk.jedpalmer.realm.player;

import co.uk.jedpalmer.realm.utils.FileAccessor;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PlayerProfile {
    private Map<String, Long> playerProfile = new HashMap<String, Long>();

    /**
     * Starts a new PlayerProfile
     */
    public PlayerProfile (boolean isNew, FileAccessor data, Player player){

        if(isNew){
            //Create new player
            long newAttribute = 0;
            playerProfile.put("lastLogin", newAttribute);
            playerProfile.put("totalPlaytime", newAttribute);

        } else{
            //Load player
            System.out.println("Loading " + player.getName() + "'s profile..");
            String uuid = player.getUniqueId().toString();

            //Get a list of all saved attributes
            Set<String> playerAttributes = data.getKeys(uuid, false);

            //Iterate through them and load all to memory
            for(String key : playerAttributes){
                playerProfile.put(key, data.getConfig().getLong(uuid + key));
            }

            System.out.println("Loaded " + playerProfile.size() + " player attributes.");
        }
    }

    /**
     * Returns a player's profile
     */
    public Map<String, Long> getPlayerProfile(){
        return playerProfile;
    }





    /**
     * Wipes the player's profile
     */
    public void cleanPlayerProfile(){
        playerProfile = null;
    }
}
