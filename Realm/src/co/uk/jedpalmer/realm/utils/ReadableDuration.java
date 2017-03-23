package co.uk.jedpalmer.realm.utils;

/**
 * Created by peraldon on 22/03/2017.
 */
public class ReadableDuration {

    public String getReadableDuration(long time){
        double milliseconds = 0.0;
        int secs = 0;
        int mins = 0;
        int hours = 0;
        int days = 0;

        while(time >= 86400000){
            time = time - 86400000;
            days++;
        }

        while(time >= 3600000){
            time = time - 3600000;
            hours++;
        }

        while(time >= 60000){
            time = time - 60000;
            mins++;
        }

        while(time >= 1000){
            time = time - 1000;
            secs++;
        }

        if(mins == 0 && hours == 0 && days == 0 && secs < 10){
            milliseconds = secs;
            secs = 0;
            while(time >= 100){
                time = time - 100;
                milliseconds = milliseconds + 0.1;
            }
        }

        String output = "";
        if(days!=0){
            if(days==1){
                output = output + days + " day ";
            } else{
                output = output + days + " days ";
            }
        }
        if(hours!=0){
            if(hours==1){
                output = output + hours + " hour ";
            } else{
                output = output + hours + " hours ";
            }
        }
        if(mins!=0){
            if(mins==1){
                output = output + mins + " minute ";
            } else{
                output = output + mins + " minutes ";
            }
        }
        if(secs!=0){
            if(secs==1){
                output = output + secs + " second ";
            } else{
                output = output + secs + " seconds ";
            }
        } else if( milliseconds != 0){
            if(milliseconds == 0.1 || milliseconds == 1){
                output = output + milliseconds + " second ";
            } else{
                output = output + milliseconds + " seconds ";
            }
        }

        return output;
    }
}
