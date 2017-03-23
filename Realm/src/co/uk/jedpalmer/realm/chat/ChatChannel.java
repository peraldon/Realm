package co.uk.jedpalmer.realm.chat;

import co.uk.jedpalmer.realm.utils.FileAccessor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ChatChannel {
    List<Integer> range = new ArrayList<Integer>();
    List<String> visual = new ArrayList<String>();
    char symbol;

    public boolean ChatChannel(FileAccessor data, String channelName){

        System.out.println("Loading channel " + channelName);

        //Get a list of all saved attributes
        range = data.getConfig().getIntegerList(channelName + ".range");
        visual = data.getConfig().getStringList(channelName + ".visual");
        symbol = data.getConfig().getCharacterList(channelName + ".symbol").get(0);

        if(range.size() != visual.size()){
            System.out.println("The amount of ranges and size of the registered chat channel does match - will cause issues!");
            return false;
        }

        if(range.size() == 0 || visual.size() == 0){
            System.out.println("Did not load chat channel properly - are you sure it's configured correctly?");
            return false;
        }
        return true;
    }


    public int getRange(int index){
        return range.get(index);
    }

    public List<Integer> getRange(){
        return range;
    }

    public String getVisual(int index){
        return visual.get(index);
    }

    public List<String> getVisual(){
        return visual;
    }

    public char getSymbol(){
        return symbol;
    }
}
