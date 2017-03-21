package co.uk.jedpalmer.realmcore.cooldown;

/**
 * Outputs cooldown utilities such as time left
 */
public class Cooldowns {


    /**
     * Compares two times together, and gives out a string of it in human readable form
     * Time should be in milliseconds
     */
    public String timeLeftString(long currentTime, long goalTime){
        long timeLeft = goalTime - currentTime;
        int secs = 0;
        int mins = 0;
        int hours = 0;
        int days = 0;

        while(timeLeft >= 86400000){
            timeLeft = timeLeft - 86400000;
            days++;
        }

        while(timeLeft >= 3600000){
            timeLeft = timeLeft - 3600000;
            hours++;
        }

        while(timeLeft >= 60000){
            timeLeft = timeLeft - 60000;
            mins++;
        }

        while(timeLeft >= 1000){
            timeLeft = timeLeft - 1000;
            secs++;
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
        }

        if(output.equals("")){
            output = "less than 1 second";
        }

        return output;
    }

    /**
     * Compares two times together, and gives out an int array
     * Time should be in milliseconds
     */
    public int[] timeLeft(long currentTime, long goalTime){
        long timeLeft = goalTime - currentTime;
        int secs = 0;
        int mins = 0;
        int hours = 0;
        int days = 0;

        while(timeLeft >= 86400000){
            timeLeft = timeLeft - 86400000;
            days++;
        }

        while(timeLeft >= 3600000){
            timeLeft = timeLeft - 3600000;
            hours++;
        }

        while(timeLeft >= 60000){
            timeLeft = timeLeft - 60000;
            mins++;
        }

        while(timeLeft >= 1000){
            timeLeft = timeLeft - 1000;
            secs++;
        }

        int[] output = new int[3];

        output[0] = days;
        output[1] = hours;
        output[2] = mins;
        output[3] = secs;

        return output;
    }
}
