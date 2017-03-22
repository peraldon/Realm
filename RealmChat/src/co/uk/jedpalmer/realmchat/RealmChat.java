package co.uk.jedpalmer.realmchat;

import co.uk.jedpalmer.realmchat.listeners.ChatListener;
import co.uk.jedpalmer.realmchat.listeners.PlayerListener;
import co.uk.jedpalmer.realmcore.utils.FileAccessor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;


public class RealmChat extends JavaPlugin{
    Plugin plugin;
    FileAccessor data;

    //Initilisation of base uses
    private final PluginManager manager = getServer().getPluginManager();
    private final FileConfiguration config = this.getConfig();


    @Override
    public void onEnable(){

        //Register events
        manager.registerEvents(new ChatListener(this), this);
        manager.registerEvents(new PlayerListener(this), this);

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

    }

};


