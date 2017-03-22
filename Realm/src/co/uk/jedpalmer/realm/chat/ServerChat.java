package co.uk.jedpalmer.realm.chat;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * Deals with messages server -> player
 */
public class ServerChat {

    public ServerChat(){
    }

    /**
     * Sends out a localised message server -> player dependant on location and range of blocks
     * Returns true if the message was seen by a player
     */
    public boolean sendLocalisedServerMessage(String message, Location location, double range){
        boolean seen = false;

        for (Player receiver : Bukkit.getOnlinePlayers()) {
            if(location.distance(receiver.getLocation()) <= range){
                receiver.sendRawMessage(message);
                seen = true;
            }
        }

        return seen;
    }
}
