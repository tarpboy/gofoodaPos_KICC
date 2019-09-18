package app.payfun.com.gofoodapos.Network;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.Locale;

/**
 * Created by JuHH on 15. 11. 3..
 */
public abstract class ConnectPost extends ConnectSync {

    public ConnectPost(Context context) {
        super(context);
    }

    public ConnectPost(Context context, String charsetName) {
        super(context, charsetName);
    }

    @Override
    StringBuffer excuteConnect(String domain, String property) throws IOException {

        HttpURLConnection urlConnection = null;

        urlConnection = connectInfo(domain);

        if (urlConnection == null) {
            return resultValue;
        }

        urlConnection.setRequestMethod("POST");
        urlConnection.setDoInput(true);
        urlConnection.setDoOutput(true);
        urlConnection.setConnectTimeout(5000);
        urlConnection.setReadTimeout(5000);

        if (property.toUpperCase(Locale.getDefault()).equals("JSON")) {
            urlConnection.setRequestProperty("Content-Type", "application/json");
        }

        //여기서 connect 안될 수 있다 그래서 E로 떨어져야 하는데...
        urlConnection.connect();

        OutputStream outputStream = urlConnection.getOutputStream();

        if (property.toUpperCase(Locale.getDefault()).equals("JSON"))
            outputStream.write(getJsonValues().getBytes());
//            else
//                outputStream.write(getUrlValues().toString().getBytes());
        outputStream.flush();

        resultValue = new StringBuffer();
        if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
            for (; ; ) {
                String stringLine = bufferedReader.readLine();
                if (stringLine == null) break;
                resultValue.append(stringLine + '\n');
            }
            bufferedReader.close();
        }

        outputStream.close();
        urlConnection.disconnect();

        return resultValue;
    }
}
