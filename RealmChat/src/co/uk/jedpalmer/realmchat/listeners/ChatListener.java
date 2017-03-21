package co.uk.jedpalmer.realmchat.listeners;

import co.uk.jedpalmer.realmchat.chat.PlayerChat;
import co.uk.jedpalmer.realmchat.chat.Types;
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
    Plugin plugin;
    PlayerChat realmChat;
    FileConfiguration config;
    CooldownManager cooldown = new CooldownManager();


    /**
     * Initilisation of ChatListener
     */
    public ChatListener(Plugin instance) {
        plugin = instance;

        config = plugin.getConfig();

        realmChat = new PlayerChat(plugin);

        //Populate the cooldownManager with TYPES
        cooldown.addCooldownType(Types.GLOBAL);
        cooldown.addCooldownType(Types.TALK);
        cooldown.addCooldownType(Types.WHISPER);
        cooldown.addCooldownType(Types.SHOUT);
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

            if (!cooldown.isCooldown(Types.SHOUT, sender)) {
                //Can send the message

                for (Player receiver : Bukkit.getOnlinePlayers()) {
                    if (realmChat.sendMessage(message, sender, receiver, Types.SHOUT)) {
                        heard = true;
                    }
                }

                //Update cooldown
                cooldown.addCooldown(Types.SHOUT, sender, config.getInt("chat.range.shout.cooldown"));

                //Alert player if no one heard them
                if (!heard) {
                    sender.sendRawMessage(ChatColor.GRAY + "No one heard you...");
                }

                //Log chat
                System.out.println(sender.getName() + " - " + message);
                event.setCancelled(true);

            } else {
                sender.sendRawMessage(ChatColor.RED + "You cannot shout for " + cooldown.printCooldown(Types.SHOUT, sender));
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

            if (!cooldown.isCooldown(Types.WHISPER, sender)) {
                //Can send the message

                for (Player receiver : Bukkit.getOnlinePlayers()) {
                    if (realmChat.sendMessage(message, sender, receiver, Types.WHISPER)) {
                        heard = true;
                    }
                }

                //Update cooldown
                cooldown.addCooldown(Types.SHOUT, sender, config.getInt("chat.range.shout.cooldown"));

                //Alert player if no one heard them
                if (!heard) {
                    sender.sendRawMessage(ChatColor.GRAY + "No one heard you...");
                }

                //Log chat
                System.out.println(sender.getName() + " - " + message);
                event.setCancelled(true);

            } else {
                sender.sendRawMessage(ChatColor.RED + "You cannot whisper for " + cooldown.printCooldown(Types.WHISPER, sender));
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

            if (!cooldown.isCooldown(Types.GLOBAL, sender)) {
                //Can send the message

                realmChat.sendGlobalMessage(message, sender);

                //Update cooldown
                cooldown.addCooldown(Types.GLOBAL, sender, config.getInt("chat.range.global.cooldown"));


                //Log chat
                System.out.println("[G] " + sender.getName() + " - " + message);
                event.setCancelled(true);

            } else {
                sender.sendRawMessage(ChatColor.RED + "You cannot speak in global for " + cooldown.printCooldown(Types.GLOBAL, sender));
                event.setCancelled(true);
            }


        } else {

            if (!cooldown.isCooldown(Types.TALK, sender)) {
                //Can send the message

                for (Player receiver : Bukkit.getOnlinePlayers()) {
                    if (realmChat.sendMessage(message, sender, receiver, Types.TALK)) {
                        heard = true;
                    }
                }

                //Update cooldown
                cooldown.addCooldown(Types.TALK, sender, config.getInt("chat.range.talk.cooldown"));

                //Alert player if no one heard them
                if (!heard) {
                    sender.sendRawMessage(ChatColor.GRAY + "No one heard you...");
                }

                //Log chat
                System.out.println(sender.getName() + " - " + message);
                event.setCancelled(true);
            }  else {

                sender.sendRawMessage(ChatColor.RED + "You cannot speak for " + cooldown.printCooldown(Types.TALK, sender));
                event.setCancelled(true);
            }
        }
    }

}



