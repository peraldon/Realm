package co.uk.jedpalmer.realm.chat;

import co.uk.jedpalmer.realm.utils.FileAccessor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.*;

/**
 * Deals with PlayerChatManager channels
 * Manages the range of chats, and visual appearance of them
 */
public class PlayerChatManager {
    private Plugin plugin;
    private FileAccessor data;

    private Map<String, ChatChannel> chatChannels;


    /**
     * Initialization of PlayerChatManager
     */
    public PlayerChatManager(Plugin instance){
        this.plugin = instance;
        this.data = new FileAccessor(instance, "chats.yml");
        this.chatChannels = new HashMap<String, ChatChannel>();

        loadChatChannels();
    }

    /**
     * Loads all chat channels from config, and will create a default channel if one does not exist
     */
    public void loadChatChannels(){
        List<String> channelNames = new ArrayList<String>();
        channelNames.addAll(data.getConfig().getKeys(false));

        if(channelNames.contains("default")){
            System.out.println("Default chat already configured. Loading all channels.");
            for(int i = 0; channelNames.size() > i; i++){
                chatChannels.put(channelNames.get(i), new ChatChannel(data, channelNames.get(i)));
            }

        } else {
            //No default channel! Time to make one
            System.out.println("No default chat recognised! Making a new one now.");

            /**
             * default:
             *  cooldown: 5
             *  set_1:
             *      range: 25
             *      visual: %playerName &7- &f%message
             *  set_2:
             *      range: 50
             *      visual: &7??? - &7%message
             *  symbol:
             *      ''
             */

            data.getConfig().set("default.cooldown", 5);
            data.getConfig().set("default.set_1.range", 25);
            data.getConfig().set("default.set_1.visual", "%playerName &7- &f%message");
            data.getConfig().set("default.set_2.range", 50);
            data.getConfig().set("default.set_2.visual", "&7??? - &7%message");
            data.getConfig().set("default.symbol", ' ');
            data.saveData();

            //Load default channel
            chatChannels.put("default", new ChatChannel(data, "default"));
        }
    }

    /**
     * Returns a specific ChatChannel object
     */
    public ChatChannel getChatChannel(String name){
        return chatChannels.get(name);
    }

    public List<String> getChatChannelNames(){
        List<String> output = new ArrayList<String>();

        output.addAll(chatChannels.keySet());

        return output;
    }
}
