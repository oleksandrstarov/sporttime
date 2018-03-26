package converter.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class Utils {

    public static int convertTime(String timeString) {

        DateFormat df = new SimpleDateFormat("HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            System.out.println(timeString + " " + (int) df.parse(timeString).getTime() / 100);
            return (int) df.parse(timeString).getTime() / 100; // tenth of seconds
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return 0;
        }
    }
}
