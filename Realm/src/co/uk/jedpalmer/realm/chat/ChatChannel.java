package co.uk.jedpalmer.realm.chat;

import co.uk.jedpalmer.realm.utils.FileAccessor;

import java.util.ArrayList;
import java.util.List;

/**
 * Object that holds and loads a chat channel and it's attribute
 */
public class ChatChannel {
    private List<Integer> range;
    private List<String> visual;
    private char symbol;
    private String channelName;

    /**
     * Initializes and loads a chat channel
     * @param data The chats.yml configuration file
     * @param channelName Need to get the channelNames from chats.yml before initialising this class
     */
    public ChatChannel(FileAccessor data, String channelName){
        this.range = new ArrayList<Integer>();
        this.visual = new ArrayList<String>();
        this.channelName = channelName;

        System.out.println("Loading channel " + channelName);

        //Get a list of all saved attributes
        int setAmount = data.getKeys(channelName, false, true).size() - 2;

        System.out.print("setAmount = " + setAmount);
        System.out.print(data.getKeys(channelName, false, true));


        for(int i = 0; setAmount > i ; i++){
            range.add(data.getConfig().getInt(channelName + ".set_" + i));
            visual.add(data.getConfig().getString(channelName + ".set_" + i));
        }

        if(!channelName.equals("default")){
            symbol = data.getConfig().getCharacterList(channelName + ".symbol").get(0);
        }


        if(range.size() != visual.size()){
            System.out.println("The amount of ranges and size of the registered chat channel does match - will cause issues!");
        }

        if(range.size() == 0 || visual.size() == 0){
            System.out.println("Did not load chat channel properly - are you sure it's configured correctly?");
        }

        System.out.println();

    }


    /**
     * Returns a specific loaded range
     */
    public int getRange(int index){
        return range.get(index);
    }

    /**
     * Returns all loaded ranges
     */
    public List<Integer> getRange(){
        return range;
    }

    /**
     * Returns a specific loaded visual
     */
    public String getVisual(int index){
        return visual.get(index);
    }

    /**
     * Returns all loaded visuals
     */
    public List<String> getVisual(){
        return visual;
    }

    /**
     * Returns the symbol char
     */
    public char getSymbol(){
        return symbol;
    }

    public String getName() {
        return channelName;
    }
}
