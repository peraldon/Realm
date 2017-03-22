package co.uk.jedpalmer.realmcore.cooldown;

import co.uk.jedpalmer.realmcore.utils.TimeComparator;
import org.bukkit.entity.Player;

import java.util.HashMap;


/**
 * Manages the storing of cooldowns for a player
 * Supports multiple types (ie, multiple chat channels)
 */
public class CooldownManager <E extends Enum>{
    private HashMap<E, HashMap<Player, Long>> cooldownMap = new HashMap<E, HashMap<Player, Long>>();
    private TimeComparator cooldowns = new TimeComparator();

    /**
     * Adds a cooldown for a player of a specific type and time
     * Will replace an existing cooldown
     * Returns true if successful, or false if not
     */
    public boolean addCooldown(E type, Player player, int time){
        if(cooldownMap.containsKey(type)){
            cooldownMap.get(type).put(player, System.currentTimeMillis() + (time * 1000));
            return true;
        } else {
            return false;
        }
    }

    /**
     * Reduces a cooldown for a player of a specific type and time
     * Returns false if there isn't a cooldown to reduce, otherwise
     * returns true
     */
    public boolean reduceCooldown(E type, Player player, int time){
        if(isCooldown(type, player)){
            cooldownMap.get(type).put(player, cooldownMap.get(type).get(player) - (time * 1000));
            return true;
        } else {
            return false;
        }
    }

    /**
     * Removes a cooldown for a player of a specific type and time
     * Returns false if there isn't a cooldown to remove, otherwise
     * returns true
     */
    public boolean removeCooldown(E type, Player player){
        if(isCooldown(type, player)){
            cooldownMap.get(type).remove(player);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Prints out a cooldown of a player and type
     * Outputs a string to send to console or player
     */
    public String printCooldown(E type, Player player){
        if(isCooldown(type, player)) {
            return cooldowns.timeLeftString(System.currentTimeMillis(), cooldownMap.get(type).get(player));
        }
        return null;
    }

    /**
     * Prints out a cooldown of a player and type
     * Outputs an int array for editing
     */
    public int[] getCooldown(E type, Player player){
        if(isCooldown(type, player)) {
            return cooldowns.timeLeft(System.currentTimeMillis(), cooldownMap.get(type).get(player));
        }
        return null;
    }

    /**
     * Checks if a specific player has a specific cooldown
     * True if they do, otherwise false
     * Will also remove the entry on the Hashmap if there is no longer a cooldown
     */
    public boolean isCooldown(E type, Player player){
        if(cooldownMap.get(type).containsKey(player)){
            if(System.currentTimeMillis() > cooldownMap.get(type).get(player)){
                cooldownMap.get(type).remove(player);
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    /**
     * For initialisation of the CooldownManager
     * Adds a new type of Cooldown, dependant on existing enum types
     * Returns true if successfully added, otherwise returns false
     */
    public boolean addCooldownType(E type){
        if(!cooldownMap.containsKey(type)){
            cooldownMap.put(type, new HashMap<Player, Long>());
            return true;
        } else {
            return false;
        }
    }

    /**
     * For cleaning up of the CooldownManager
     * Removes an old type of Cooldown, dependant on existing enum types
     * Returns true if successfully deleted, otherwise returns false
     */
    public boolean removeCooldownType(E type){
        if(cooldownMap.containsKey(type)){
            cooldownMap.remove(type);
            return true;
        } else {
            return false;
        }
    }
}
