package co.uk.jedpalmer.realm.chat;

import co.uk.jedpalmer.realm.cooldown.CooldownManager;
import co.uk.jedpalmer.realm.utils.ColorExchange;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

/**
 * Created by peraldon on 23/03/2017.
 */
public class PlayerMessages {
    private Plugin plugin;
    private PlayerChatManager playerChatManger;
    private CooldownManager cooldownManager;
    private ColorExchange colourExchange;

    /**
     * Deals with player -> player messages, chat channels, processing and cooldowns
     * @param instance Forward the plugin instance from main file
     */
    public PlayerMessages(Plugin instance){
        this.plugin = instance;
        this.playerChatManger = new PlayerChatManager(plugin);
        this.cooldownManager = new CooldownManager();
        this.colourExchange = new ColorExchange();

        //Create cooldowns for the channels
        for(String channelName : playerChatManger.getChatChannelNames()){
            cooldownManager.addCooldownType(channelName);
        }

    }

    /**
     * @param player The Player who's sending the message
     * @param message The unprocessed message that's being sent
     * @return Returns 0 if not seen, 1 if seen and 2 if on cooldown
     */
    public int sendPlayerMessage(Player player, String message){
        int status = 0;
        String targetChannelName = "default";

        //Iterate through the channelNames, if none match, it defaults to 'default', as seen above
        for(String channelName : playerChatManger.getChatChannelNames()){
            if(playerChatManger.getChatChannel(channelName).getSymbol() == message.charAt(0)){
                targetChannelName = channelName;
            }
        }

        //Let's see if it's on cooldown
        if(cooldownManager.isCooldown(targetChannelName, player)){
            player.sendRawMessage(ChatColor.RED + "You cannot speak in this channel for " + cooldownManager.printCooldown(targetChannelName, player));
            status = 2;
            return status;
        } else {
            //Not cooldown, time to send!
            for(int i = 0; playerChatManger.getChatChannel(targetChannelName).getRange().size() > i ; i++){

                System.out.println("iterator is at " + i);

                //Iterates through all ranges
                String format = playerChatManger.getChatChannel(targetChannelName).getVisual(i);
                String output = "";

                System.out.println(format);

                //Iterates through format, moving it over to output and processing any special characters
                while(!format.equals("")){

                    //Colour code detected!
                    if(format.charAt(0) == '&'){
                        String id = format.substring(0,1);
                        //Add to output
                        output = output + colourExchange.getChatColor(id);
                        //Remove from format
                        format = format.substring(2, format.length());

                    //Special identifier detected!
                    } else if (format.charAt(0) == '%'){
                        String identifier = format.substring(1, format.indexOf(" "));

                        //Let's work out what identifier it is
                        switch (identifier){
                            case "message":
                                output = output + message;
                            case "playerName":
                                output = output + player.getName();
                        }

                        //Let's now remove it from format (this should remove any false identifiers
                        format = format.substring(1, format.indexOf(" "));
                    } else {
                        //Nothing special
                        output = output + format.charAt(0);
                        //Remove from format
                        format = format.substring(1, format.length());
                    }
                }
                //Message is now ready to be sent out, let's also see if it was seen by a player
                if(messageSend(output, player, playerChatManger.getChatChannel(targetChannelName).getRange(i))){
                    status = 1;
                }
            }
        }
        return status;
    }

    /**
     * Sends a message player -> player
     * @param message The fully filtered, complete text message
     * @param sender The Player who's sending the message
     * @param range The range of the message (-1 for global)
     * @return Returns true if it was seen by a player, otherwise it's false
     */
    private boolean messageSend(String message, Player sender, int range) {
        boolean hasSeen = false;

        //Cycle through the players
        for (Player player : Bukkit.getOnlinePlayers()) {
            //If the sender sees it, it shouldn't count as seen
            if(player == sender){
                player.sendRawMessage(message);
                continue;
            }
            //Checks for global message
            if (range == -1) {
                player.sendRawMessage(message);
                hasSeen = true;
            } else {
                //Checks distance and world
                if (sender.getLocation().distance(player.getLocation()) <= range && sender.getWorld() == player.getWorld()) {
                    player.sendRawMessage(message);
                    hasSeen = true;
                }
            }
        }
        return hasSeen;
    }
}
