package app.payfun.com.gofoodapos.Push;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Jonathan on 2017-07-19.
 */

public class FirebaseNotificationID {
    private final static AtomicInteger c = new AtomicInteger(0);
    public static int getID() {
        Date now = new Date();
        int id = Integer.parseInt(new SimpleDateFormat("ddHHmmss",  Locale.KOREA).format(now));
        return id;
    }
}
