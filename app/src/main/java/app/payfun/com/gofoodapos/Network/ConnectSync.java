package app.payfun.com.gofoodapos.Network;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Created by JuHH on 15. 11. 3..
 */
public abstract class ConnectSync extends AsyncTask<String, Void, StringBuffer> {

    Context context;
    // Encode 값
    String charsetName;
    // 결과 값
    StringBuffer resultValue;
    // Tag 값
    String saveTag;
    // Body 정보
    ConnectValue dataInfos;
    public ConnectSync(Context context) {
        // 기본값으로 HTTP 통신을 사용
        this(context, "UTF-8");
    }

    public ConnectSync(Context context, String charsetName) {
        this.context = context;
        this.charsetName = charsetName;

        resultValue = new StringBuffer();
    }

    // 웹에 접속해서 정보를 가져온다.
    abstract StringBuffer excuteConnect(String domain, String property) throws IOException;

    // 최종 결과값을 반환한다.
    public abstract void completeSync(String tag, StringBuffer result);

    /**
     * Parameter 정보를 담은 클래스 추가
     *
     * @param connectValue
     */
    public void setConnectValue(ConnectValue connectValue) {



        dataInfos = connectValue;


//        Set<String> ss = dataInfos.getConnectValue().get(0).keySet();
//        Iterator<String> iter = ss.iterator();
//        while (iter.hasNext()) {
//            String key = iter.next();
//            try {
//                Object value = dataInfos.getConnectValue().get(0).get(key);
//                Log.e("Jonathan", "arryMenuAll1  : key : " + key + " value : " + value);
//            } catch (Exception e) {
//                // Something went wrong!
//            }
//        }


    }

    /**
     * 전달받은 값이 들어있는지 체크한다.
     *
     * @param name
     * @param value
     */
//    private void chooseInputData(String name, String value, InputDataType type) {
//
//        if (name == null || value == null)
//            new NullPointerException("name or value is not null");
//
//        if (name.trim().length() == 0 || value.trim().length() == 0)
//            new RuntimeException("name or value is not empty");
//
//        ConnectValue connectValue = new ConnectValue(name, value);
//
//        if (type == InputDataType.TypeHeader) {
//
//            headerInfos.add(connectValue);
//        } else if (type == InputDataType.TypeBody) {
//
//            bodyInfos.add(connectValue);
//        }
//
//        dataInfos =
//    }

    /**
     * Header 데이타에 추가할 정보
     *
     * @param name  : key 값
     * @param value : 사용할 값
     */
//    public void addHeader(String name, String value) {
//
//        // 전달 받은 데이타를 체크
//        chooseInputData(name, value, InputDataType.TypeHeader);
//    }

    /**
     * Parameter 데이타에 추가할 정보
     *
     * @param name  : Key 값
     * @param value : 사용되는 값
     */
//    public void addParameter(String name, String value) {
//
//        // 전달 받은 데이타를 체크
//        chooseInputData(name, value, InputDataType.TypeBody);
//    }

    /**
     * Parameter 데이터를 한꺼번에 추가한다
     * @param params
     */
//    public void setParameters(ArrayList<ConnectValue> params) {
//
//        bodyInfos.clear();
//        bodyInfos.addAll(params);
//    }

    /**
     * JsonObject 형태로 값을 반환한다
     *
     * @return
     */
    protected String getJsonValues() {




        if (dataInfos == null) {

            return "";
        }




        ArrayList<HashMap<String, Object>> paramsInfo = dataInfos.getConnectValue();
        ConnectInfo.RequestType requestType = dataInfos.getRequestValueType();


        if (requestType == ConnectInfo.RequestType.MAP) {
            if (paramsInfo.size() == 1) {

                JSONObject jsonObject = new JSONObject(paramsInfo.get(0));
                try {
                    PrintLog.print("INPUT START JSON", "=================================================");
                    String log = jsonObject.toString(4);
                    while (log.length() > 0) {
                        if (log.length() > 4000) {
                            PrintLog.print("INPUT JSON", log.substring(0, 4000));
                            log = log.substring(4000);
                        } else {
                            PrintLog.print("INPUT JSON", log);
                            break;
                        }
                    }

                } catch (JSONException e) {
                    PrintLog.print("INPUT JSON", "INPUT DATA ERROR");
                    e.printStackTrace();
                }

                String jsonString = jsonObject.toString();
                jsonString = jsonString.trim();

                return jsonString;
            } else {

                JSONArray jsonArray = new JSONArray(paramsInfo);

                return jsonArray.toString();
            }
        } else if (requestType == ConnectInfo.RequestType.LIST) {

            JSONArray jsonArray = new JSONArray(paramsInfo);

            try {
                PrintLog.print("INPUT START JSON", "=================================================");
                String log = jsonArray.toString(4);
                while (log.length() > 0) {
                    if (log.length() > 4000) {
                        PrintLog.print("INPUT JSON", log.substring(0, 4000));
                        log = log.substring(4000);
                    } else {
                        PrintLog.print("INPUT JSON", log);
                        break;
                    }
                }

            } catch (JSONException e) {
                PrintLog.print("INPUT JSON", "INPUT DATA ERROR");
                e.printStackTrace();
            }

            return jsonArray.toString();
        }

        return "";
    }

//    protected StringBuffer getUrlValues() {
//
//        StringBuffer sbTemp = new StringBuffer();
//        boolean isFirst = true;
//
//        for (ConnectValue connectValue : bodyInfos) {
//
//            if (isFirst)
//                isFirst = false;
//            else
//                sbTemp.append("&");
//
//            PrintLog.print("Parameter", "[" + connectValue.getName() + " : " + connectValue.getValue() + "]");
//
//            try {
//                sbTemp.append(URLEncoder.encode(connectValue.getName(), charsetName));
//                sbTemp.append("=");
//                sbTemp.append(URLEncoder.encode(connectValue.getValue(), charsetName));
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            }
//        }
//
//        return sbTemp;
//    }

    /**
     * 에러 메시지 생성
     *
     * @param message
     * @return
     */
    protected void makeErrorMessage(String message) {

        HashMap<String, String> errorMessages = new HashMap<String, String>();
        errorMessages.put(ConnectInfo.returnType, ConnectInfo.returnError);
        errorMessages.put(ConnectInfo.errorMessage, message);
        resultValue.append(new JSONObject(errorMessages).toString());
    }

//    private void printLog(HashMap<String, Object> map) {
//
//        HashMap<String, Object> info = map;
//        if(info.size() == 0)
//            return;
//
//        Iterator<String> keys = map.keySet().iterator();
//
//        while (keys.hasNext()) {
//
//            String key = keys.next();
//            if(map.get(key) instanceof String) {
//
//                StringBuffer sb = new StringBuffer();
//                sb.append("[").append(key).append(" :: ").append(map.get(key)).append("]");
//                PrintLog.print("InputString", sb.toString());
//            } else if(map.get(key) instanceof ArrayList) {
//
//                ArrayList<HashMap<String, Object>> lists = (ArrayList<HashMap<String, Object>>) map.get(key);
//
//                for(int i = 0; i < lists.size(); i++) {
//
//                    printLog(lists.get(i));
//                }
//            }
//        }
//    }

    /**
     * 접속 정보 셋팅
     *
     * @param domain
     * @return
     */
    protected HttpURLConnection connectInfo(String domain) {
        PrintLog.print("INPUT START", "=================================================");
        PrintLog.print("INPUT URL", domain);


        HttpURLConnection urlConnection = null;
        URL url = null;
        try {
            url = new URL(domain);

            if (domain.toUpperCase().startsWith("HTTPS")) {
                Log.w("Type", "HTTPS");
                trustAllHosts();
                urlConnection = (HttpsURLConnection) url.openConnection();
                ((HttpsURLConnection) urlConnection).setHostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                });
            } else {
                Log.w("Type", "HTTP");
                urlConnection = (HttpURLConnection) url.openConnection();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            makeErrorMessage(ConnectInfo.MSG_DOMAIN_ERROR);
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
            makeErrorMessage(ConnectInfo.MSG_SOCTET_TIMEOUT);
        } catch (ConnectException e) {
            e.printStackTrace();
            makeErrorMessage(ConnectInfo.MSG_CONNECT_TIMEOUT);
        } catch (IOException e) {
            e.printStackTrace();
            makeErrorMessage(ConnectInfo.MSG_NETWORK_FIAL);
        }

        return urlConnection;
    }

    /**
     * HTTPS 에서 사용하는 메소드
     * 모하는거지 -_-... 그냥 범용적인 인증 방식 같은데... 일반적이지 않아 0-0...
     */
    protected void trustAllHosts() {
        // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return new java.security.cert.X509Certificate[]{};
            }

            @Override
            public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws java.security.cert.CertificateException {
                // TODO Auto-generated method stub

            }

            @Override
            public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws java.security.cert.CertificateException {
                // TODO Auto-generated method stub

            }
        }};

        // Install the all-trusting trust manager
        try {
            SSLContext sc = SSLContext.getInstance("TLSv1.2");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
            e.printStackTrace();
            makeErrorMessage(e.getMessage());
        }
    }

    @Override
    protected StringBuffer doInBackground(String... params) {

        saveTag = params[0];

        StringBuffer result = null;

        // 인터넷 체크
        if (ConnectInfo.isNetworkConnectCheck(context)) {

            try {

                result = excuteConnect(params[1], params.length == 3 ? params[2] : "");
            } catch (SocketTimeoutException e) {
                e.printStackTrace();
                makeErrorMessage(ConnectInfo.MSG_SOCTET_TIMEOUT);
            } catch (ConnectException e) {
                e.printStackTrace();
                makeErrorMessage(ConnectInfo.MSG_CONNECT_TIMEOUT);
            } catch (IOException e) {
                e.printStackTrace();
                makeErrorMessage(ConnectInfo.MSG_NETWORK_FIAL);
            }
        } else {

            makeErrorMessage(ConnectInfo.MSG_NETWORK_FIAL);
        }
        return result == null ? resultValue : result;
    }

    @Override
    protected void onPostExecute(StringBuffer stringBuffer) {

        completeSync(saveTag, stringBuffer);
    }

    enum InputDataType {
        TypeHeader, TypeBody
    }
}
