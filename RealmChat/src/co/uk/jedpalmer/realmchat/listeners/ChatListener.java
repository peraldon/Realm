package co.uk.jedpalmer.realmchat.listeners;

import co.uk.jedpalmer.realmchat.chat.PlayerChat;
import co.uk.jedpalmer.realmchat.chat.ChatTypes;
import co.uk.jedpalmer.realmcore.cooldown.CooldownManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.Plugin;


public class ChatListener implements Listener {
    private Plugin plugin;
    private PlayerChat realmChat;
    private FileConfiguration config;
    private CooldownManager<ChatTypes> cooldown = new CooldownManager<ChatTypes>();


    /**
     * Initilisation of ChatListener
     */
    public ChatListener(Plugin instance) {
        plugin = instance;

        config = plugin.getConfig();

        realmChat = new PlayerChat(plugin);

        //Populate the cooldownManager with TYPES
        cooldown.addCooldownType(ChatTypes.GLOBAL);
        cooldown.addCooldownType(ChatTypes.TALK);
        cooldown.addCooldownType(ChatTypes.WHISPER);
        cooldown.addCooldownType(ChatTypes.SHOUT);
    }

    /**
     * Intercepts player messages, and looks for channel types
     * Will always cancel the initial event
     */
    @EventHandler
    public void AsyncPlayerChat(AsyncPlayerChatEvent event) {
        String message = event.getMessage();
        Player sender = event.getPlayer();
        boolean heard = false;

        //User is shouting?
        if (message.charAt(0) == '!') {
            //Remove the channel command
            message = message.substring(1);

            if(message.length()==0){
                //QOL
                event.setCancelled(true);
                return;
            }

            if (!cooldown.isCooldown(ChatTypes.SHOUT, sender)) {
                //Can send the message

                for (Player receiver : Bukkit.getOnlinePlayers()) {
                    if (realmChat.sendMessage(message, sender, receiver, ChatTypes.SHOUT)) {
                        heard = true;
                    }
                }

                //Update cooldown
                cooldown.addCooldown(ChatTypes.SHOUT, sender, config.getInt("chat.range.shout.cooldown"));

                //Alert player if no one heard them
                if (!heard) {
                    sender.sendRawMessage(ChatColor.GRAY + "No one heard you...");
                }

                //Log chat
                System.out.println(sender.getName() + " - " + message);
                event.setCancelled(true);

            } else {
                sender.sendRawMessage(ChatColor.RED + "You cannot shout for " + cooldown.printCooldown(ChatTypes.SHOUT, sender));
                event.setCancelled(true);
            }

            //User is whispering?
        } else if (message.charAt(0) == '~') {
            //Remove the channel command
            message = message.substring(1);

            if(message.length()==0){
                //QOL
                event.setCancelled(true);
                return;
            }

            if (!cooldown.isCooldown(ChatTypes.WHISPER, sender)) {
                //Can send the message

                for (Player receiver : Bukkit.getOnlinePlayers()) {
                    if (realmChat.sendMessage(message, sender, receiver, ChatTypes.WHISPER)) {
                        heard = true;
                    }
                }

                //Update cooldown
                cooldown.addCooldown(ChatTypes.SHOUT, sender, config.getInt("chat.range.shout.cooldown"));

                //Alert player if no one heard them
                if (!heard) {
                    sender.sendRawMessage(ChatColor.GRAY + "No one heard you...");
                }

                //Log chat
                System.out.println(sender.getName() + " - " + message);
                event.setCancelled(true);

            } else {
                sender.sendRawMessage(ChatColor.RED + "You cannot whisper for " + cooldown.printCooldown(ChatTypes.WHISPER, sender));
                event.setCancelled(true);
            }

        } else if (message.charAt(0) == '#') {
            //Remove the channel command
            message = message.substring(1);

            if(message.length()==0){
                //QOL
                event.setCancelled(true);
                return;
            }

            if (!cooldown.isCooldown(ChatTypes.GLOBAL, sender)) {
                //Can send the message

                realmChat.sendGlobalMessage(message, sender);

                //Update cooldown
                cooldown.addCooldown(ChatTypes.GLOBAL, sender, config.getInt("chat.range.global.cooldown"));


                //Log chat
                System.out.println("[G] " + sender.getName() + " - " + message);
                event.setCancelled(true);

            } else {
                sender.sendRawMessage(ChatColor.RED + "You cannot speak in global for " + cooldown.printCooldown(ChatTypes.GLOBAL, sender));
                event.setCancelled(true);
            }


        } else {

            if (!cooldown.isCooldown(ChatTypes.TALK, sender)) {
                //Can send the message

                for (Player receiver : Bukkit.getOnlinePlayers()) {
                    if (realmChat.sendMessage(message, sender, receiver, ChatTypes.TALK)) {
                        heard = true;
                    }
                }

                //Update cooldown
                cooldown.addCooldown(ChatTypes.TALK, sender, config.getInt("chat.range.talk.cooldown"));

                //Alert player if no one heard them
                if (!heard) {
                    sender.sendRawMessage(ChatColor.GRAY + "No one heard you...");
                }

                //Log chat
                System.out.println(sender.getName() + " - " + message);
                event.setCancelled(true);
            }  else {

                sender.sendRawMessage(ChatColor.RED + "You cannot speak for " + cooldown.printCooldown(ChatTypes.TALK, sender));
                event.setCancelled(true);
            }
        }
    }

}



