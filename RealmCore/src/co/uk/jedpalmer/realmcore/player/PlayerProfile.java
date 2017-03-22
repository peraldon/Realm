package co.uk.jedpalmer.realmcore.player;

import co.uk.jedpalmer.realmcore.utils.FileAccessor;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by peraldon on 21/03/2017.
 */
public class PlayerProfile {
    private Map<String, Long> playerProfile = new HashMap<String, Long>();

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

            System.out.println("kills " + playerProfile.get("kills"));
            System.out.println("deaths " + playerProfile.get("deaths"));
        }
    }


    public Map<String, Long> getPlayerProfile(){
        return playerProfile;
    }

    public void cleanPlayerProfile(){
        playerProfile = null;
    }
}
