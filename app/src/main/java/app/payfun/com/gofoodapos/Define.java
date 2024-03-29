package app.payfun.com.gofoodapos;

import android.media.MediaPlayer;

import java.text.NumberFormat;

/**
 * Created by Jonathan on 2017. 1. 8..
 */
public class Define {



    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;
    public static final int MESSAGE_CONNECTION_LOST = 6;
    public static final int MESSAGE_UNABLE_CONNECT = 7;



    //*****************************************************
    public static final int REQUEST_CONNECT_DEVICE = 1;
    public static final int REQUEST_ENABLE_BT = 2;
    public static final int REQUEST_CHOSE_BMP = 3;
    public static final int REQUEST_CAMER = 4;


    //*****************************************************
    public static final String CHINESE = "GBK";
    public static final String THAI = "CP874";
    public static final String KOREAN = "EUC-KR";
    public static final String BIG5 = "BIG5";




    public static final String DEVICE_NAME = "device_name";
    public static final String TOAST = "toast";


    public static final int REQUEST_CAMERA_PERMISSION = 1;


    public static long mLastClickTime = 0;

    public static int REQUEST_FROM_HOME = 111;
    public static int PLANER_DETAIL_OK = 123;



    public static MediaPlayer mp1 = new MediaPlayer();

    public static String nullCheck(Object value) {

        return value == null ? "" : value.toString().trim().equals("null") ? "" : value.toString().trim();
    }


    /**
     * 숫자에 ,를 붙여서 반환한다.
     *
     * @param value
     * @return
     */
    public static String formatToMoney(String value) {

        long numberTemp = 0;
        try {
            if (Define.nullCheck(value).length() > 0) {
                if (value.indexOf(".") > -1) {
                    float chNumber = Float.valueOf(value);
                    numberTemp = (long) chNumber;
                } else {
                    numberTemp = Long.valueOf(value);
                }

                StringBuffer sb = new StringBuffer();
                sb.append(NumberFormat.getNumberInstance().format(numberTemp)).append("원");
                return sb.toString().length() == 0 ? "0원" : sb.toString();
            }
        } catch (NumberFormatException e) {
//            e.printStackTrace();
        }

        return "0원";
    }



}
