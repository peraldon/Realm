package co.uk.jedpalmer.realmcore;

import co.uk.jedpalmer.realmcore.listeners.PlayerListener;
import co.uk.jedpalmer.realmcore.player.PlayerManager;
import co.uk.jedpalmer.realmcore.utils.FileAccessor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class RealmCore extends JavaPlugin{
    private Plugin plugin;
    private FileAccessor data;
    private PlayerManager playerManager;

    @Override
    public void onEnable(){
        plugin = this;

        data = new FileAccessor(plugin, "playerdata.yml");

        playerManager = new PlayerManager(plugin, data);

        //Register listeners
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerListener(playerManager), this);

    }

    @Override
    public void onDisable(){
        //Save everyone currently online
        for(Player player : Bukkit.getOnlinePlayers()){
            playerManager.savePlayer(player);
        }
    }


}
