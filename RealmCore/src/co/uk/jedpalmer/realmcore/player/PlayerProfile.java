package co.uk.jedpalmer.realmcore.player;

import co.uk.jedpalmer.realmcore.utils.FileAccessor;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class PlayerProfile {
    private Map<String, Long> playerProfile = new HashMap<String, Long>();

    /**
     * Starts a new PlayerProfile
     */
    public PlayerProfile (boolean isNew, FileAccessor data, Player player){

        if(isNew){
            //Create player
            long newAttribute = 0;
            playerProfile.put("kills", newAttribute);
            playerProfile.put("deaths", newAttribute);

        } else{
            //Load player
            System.out.println("Loading player profile..");
            playerProfile.put("kills", data.getConfig().getLong(player.getUniqueId().toString() + ".kills"));
            playerProfile.put("deaths", data.getConfig().getLong(player.getUniqueId().toString() + ".deaths"));
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
