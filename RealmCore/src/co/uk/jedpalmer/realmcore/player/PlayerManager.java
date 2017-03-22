package co.uk.jedpalmer.realmcore.player;


import co.uk.jedpalmer.realmcore.utils.FileAccessor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;

public class PlayerManager {
    Plugin plugin;
    FileAccessor data;
    private Map<String, Map<String, Long>> playerMap = new HashMap<String, Map<String, Long>>();

    public PlayerManager(Plugin plugin, FileAccessor data){
        this.plugin = plugin;
        this.data = data;
    }

    public boolean hasData(Player player){

        if(playerMap.containsKey(player.getUniqueId().toString())){
            return true;
        } else {
            return false;
        }
    }

    public boolean loadPlayer(Player player){
        System.out.println("Trying to load " + player.getName() + "'s data.");

        if(playerMap.containsKey(player.getUniqueId().toString())){
            System.out.println("Tried to load player " + player.getName() + "'s data, even though it's already loaded!");
            return false;
        } else {

            if(data.getConfig().contains(player.getUniqueId().toString())){
                //Load player
                System.out.println("Found " + player.getName() + "'s data, now loading.");
                PlayerProfile playerProfile = new PlayerProfile(false, data, player);
                playerMap.put(player.getUniqueId().toString(), playerProfile.getPlayerProfile());
                return true;
            } else {
                //Create player profile
                System.out.println("New player " + player.getName() + "! Creating profile now");
                PlayerProfile playerProfile = new PlayerProfile(true, data, player);
                playerMap.put(player.getUniqueId().toString(), playerProfile.getPlayerProfile());
                return true;
            }
        }
    }

    public Map<String, Long> getPlayer(Player player){

        if(playerMap.containsKey(player.getUniqueId().toString())){
            //Player is safe to send
            System.out.println("Player " + player.getName());
            System.out.println("Kills " + player.getName());
            System.out.println("Deaths " + player.getName());
            return playerMap.get(player.getUniqueId().toString());
        } else {
            //Player isn't safe to send
            System.out.println("Tried to grab player " + player.getName() + "'s data, but it's not available");
            return null;
        }
    }

    public boolean savePlayer(Player player){

        if(playerMap.containsKey(player.getUniqueId().toString())){
            //Player is safe to save
            data.getConfig().set(player.getUniqueId().toString(), playerMap.get(player.getUniqueId().toString()));
            data.saveData();
            return true;
        } else {
            //Player isn't safe to save
            System.out.println("Tried to save player " + player.getName() + "'s data, but it's not available");
            return false;
        }
    }

    public boolean unloadPlayer(Player player){
        if(playerMap.containsKey(player.getUniqueId().toString())){
            //Player is safe to unload
            playerMap.remove(player.getUniqueId().toString());

            System.out.println(playerMap);

            return true;
        } else {
            //Player isn't safe to save
            System.out.println("Tried to unload player " + player.getName() + "'s data, but it's not available");
            return false;
        }
    }

    public int loadedPlayers(){
        return playerMap.size();
    }
}
