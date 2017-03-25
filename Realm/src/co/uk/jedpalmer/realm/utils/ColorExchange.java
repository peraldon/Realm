package co.uk.jedpalmer.realm.utils;


import org.bukkit.ChatColor;

public class ColorExchange {


    /**
     * Turn an ID into a Bukkit.ChatColor
     * @param id The colour ID (such as "&7")
     * @return Returns the corresponding bukkit ChatColor
     * If id is not valid, it will return ChatColor.RESET
     */
    public ChatColor getChatColor(String id){
        switch(id){
            case "&0":
                return ChatColor.BLACK;
            case "&1":
                return ChatColor.DARK_BLUE;
            case "&2":
                return ChatColor.DARK_GREEN;
            case "&3":
                return ChatColor.DARK_AQUA;
            case "&4":
                return ChatColor.DARK_RED;
            case "&5":
                return ChatColor.DARK_PURPLE;
            case "&6":
                return ChatColor.GOLD;
            case "&7":
                return ChatColor.GRAY;
            case "&8":
                return ChatColor.DARK_GRAY;
            case "&9":
                return ChatColor.BLUE;
            case "&a":
                return ChatColor.GREEN;
            case "&b":
                return ChatColor.AQUA;
            case "&c":
                return ChatColor.RED;
            case "&d":
                return ChatColor.LIGHT_PURPLE;
            case "&e":
                return ChatColor.YELLOW;
            case "&f":
                return ChatColor.WHITE;
            case "&k":
                return ChatColor.MAGIC;
            case "&l":
                return ChatColor.BOLD;
            case "&m":
                return ChatColor.STRIKETHROUGH;
            case "&n":
                return ChatColor.UNDERLINE;
            case "&o":
                return ChatColor.ITALIC;
            case "&r":
                return ChatColor.RESET;
        }

        //If it fails
        return ChatColor.RESET;
    }


}
