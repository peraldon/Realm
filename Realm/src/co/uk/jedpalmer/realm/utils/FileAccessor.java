package co.uk.jedpalmer.realm.utils;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class FileAccessor {
    private Plugin plugin;
    private FileConfiguration data;
    private File dataFile;
    private File dataFolder;
    private String fileName;

    /**
     * Initializes FileAccessor for a specific file
     */
    public FileAccessor(Plugin plugin, String fileName){
        this.plugin = plugin;
        this.fileName = fileName;
        this.dataFolder = plugin.getDataFolder();
        this.dataFile = new File(dataFolder, fileName);

        setupFile();

    }

    /**
     * Tries to make empty directories and files if they don't exist
     */
    public void setupFile(){
        //Try to make the folder if it doesn't exist
        if(!plugin.getDataFolder().exists()){
            try{
                dataFolder.mkdir();
            } catch (SecurityException e){
                e.printStackTrace();
            }
        }

        //Try to make the file if it doesn't exist
        try {
            if(dataFile.createNewFile()) {
                System.out.println("Created file");
            } else {
                System.out.println("File already exists!");
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Reloads file from disk - will return true if successful
     */
    public boolean reloadData(){
        if(dataFile == null || dataFolder == null){
            System.out.println("Directories not set up properly.");
            return false;
        }

        data = YamlConfiguration.loadConfiguration(dataFile);
        System.out.println("Reloaded file " + dataFile.getName());
        return true;
    }

    /**
     * Grabs the in-memory config
     */
    public FileConfiguration getConfig(){
        //if it's not currently loaded, load it
        if (data == null){
            this.reloadData();
        }

        return data;
    }

    /**
     * Saves initial base data to drive
     * Returns true if successful
     */
    public boolean saveDefaultData(){

        if(data != null && dataFile != null){
            try {
                data.save(dataFile);

                System.out.println("Saved data");

            } catch (IOException e){
                e.printStackTrace();
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * Saves in-memory date to file
     * Returns true if successful
     * */
   public boolean saveData(){

       if(data != null && dataFile != null){
           try {
               data.save(dataFile);

               System.out.println("Saved data");

           } catch (IOException e){
               e.printStackTrace();
           }
           return true;
       } else {
           return false;
       }
   }

    /**
     * Returns all keys in memory from a specific path
     * Set identical to true if you want to include the path in the set
     * */
    public Set<String> getKeys (String path, boolean identical) {
        Set<String> allKeys = data.getKeys(true);
        Set<String> output = new HashSet<String>();

        if (identical) {
            for (String key : allKeys) {
                if (key.contains(path)) {
                    output.add(key);
                }
            }
        } else {
            for (String key : allKeys) {
                if (key.contains(path)) {
                    if (!key.equals(path)) {
                        output.add(key.replace(path, ""));
                    } else {
                        continue;
                    }
                }
            }
        }
        return output;
    }
}
