package co.uk.jedpalmer.realm;

import co.uk.jedpalmer.realm.listeners.ChatListener;
import co.uk.jedpalmer.realm.listeners.PlayerListener;
import co.uk.jedpalmer.realm.player.PlayerManager;
import co.uk.jedpalmer.realm.utils.FileAccessor;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class Realm extends JavaPlugin{
    private Plugin plugin;
    private FileAccessor data;
    private FileConfiguration config;
    private PlayerManager playerManager;
    private PluginManager manager;

    @Override
    public void onEnable(){
        //Populate base objects
        plugin = this;
        data = new FileAccessor(plugin, "playerdata.yml");
        config = plugin.getConfig();
        playerManager = new PlayerManager(plugin, data);
        manager =  getServer().getPluginManager();

        //Register listeners
        manager.registerEvents(new PlayerListener(playerManager), this);
        manager.registerEvents(new ChatListener(plugin), this);

        //Sets up initial config
        config.addDefault("chat.range.talk.normal", 25);
        config.addDefault("chat.range.talk.fuzzy", 50);
        config.addDefault("chat.range.talk.cooldown", 5);
        config.addDefault("chat.range.shout.normal", 45);
        config.addDefault("chat.range.shout.fuzzy", 90);
        config.addDefault("chat.range.shout.cooldown", 20);
        config.addDefault("chat.range.whisper.normal", 5);
        config.addDefault("chat.range.whisper.fuzzy", 15);
        config.addDefault("chat.range.whisper.cooldown", 5);
        config.addDefault("chat.range.global.cooldown", 30);
        config.options().copyDefaults(true);
        saveConfig();
    }

    @Override
    public void onDisable(){
        //Save everyone currently online
        for(Player player : Bukkit.getOnlinePlayers()){
            playerManager.savePlayer(player);
        }
    }


}
