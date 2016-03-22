package utils;

import java.util.Calendar;

/**
 * This may need a different home, unsure where it belongs
 *
 * Created by ford.arnett on 1/26/16.
 */
public class VideoUtils {

    public static String getVideoTimeFromCalendar(Calendar cal){
        String result;
        result = cal.get(Calendar.HOUR) == 0 ? "00:" : cal.get(Calendar.HOUR) + ":";
        result += cal.get(Calendar.MINUTE) == 0 ? "00:" : cal.get(Calendar.MINUTE) + ":";
        result += cal.get(Calendar.SECOND) == 0 ? "00" : cal.get(Calendar.SECOND) + "";

        return result;
    }
}
