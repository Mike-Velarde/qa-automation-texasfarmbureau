package webinteractions;

import com.bottlerocket.utils.ErrorHandler;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by ford.arnett on 11/17/15.
 */
public class WebsiteConnection {

    /**
     * Adapted from http://stackoverflow.com/questions/3584210/preferred-java-way-to-ping-a-http-url-for-availability
     *
     * @param url
     * @param timeout
     * @return
     */
    public static int ping(String url, int timeout) {
        // Otherwise an exception may be thrown on invalid SSL certificates:
        url = url.replaceFirst("^https", "http");

        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setConnectTimeout(timeout);
            connection.setReadTimeout(timeout);
            connection.setRequestMethod("GET");

            return connection.getResponseCode();
        }

        catch (IOException exception) {
            ErrorHandler.printErr("Error when contacting " + url, exception);
            return -1;
        }
    }

    public static boolean responseAcceptable(int responseCode) {
        return (responseCode >= 200 && responseCode <=399);
    }
}
