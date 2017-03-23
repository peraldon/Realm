package co.uk.jedpalmer.realm.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by peraldon on 22/03/2017.
 */
public class ReadableDate {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm dd MM, yyyy");
    Date output;

    public String getReadableDate(Long input){
        output = new Date(input);
        return output.toString();
    }
}
