package app.payfun.com.gofoodapos.Network;

import android.content.Context;

/**
 * Created by JuHH on 15. 11. 5..
 */
public class ApiInfo {

    // Context
    protected Context context;
    // host 정보
    private String host = null;


//    public static final String SERVER_URL = "http://10.106.13.154:8080";
//    public static final String SERVER_URL = "http://210.89.182.230:8080"; //테스트
//    public static final String SERVER_URL = "http://39.118.70.198:8080";// 덕희대리
//    public static final String SERVER_URL = "http://172.20.10.3:8080";// 한성대리님
    public static final String SERVER_URL = "http://45.119.146.132:8080";// 리얼



    // 싱크턴
    public ApiInfo(Context context) {

        this.context = context;

        initHostValue();
    }

    private void initHostValue() {

        host = SERVER_URL;
    }

    protected String getUrl(String apiValue) {

        return host + apiValue;
    }

    // Url을 생성해서 반환
    protected String getUrlIntoSession(String apiValue) {

            return host + apiValue;
    }

    //다음지도 주소변환시 사용
    protected String getDaumUrlInfo(){
//        return "https://apis.daum.net/local/geo/coord2detailaddr?apikey=" + CollectLocationInfo.MAP_KEY + "&inputCoordSystem=WGS84&output=json&";
        return "";
    }
}
