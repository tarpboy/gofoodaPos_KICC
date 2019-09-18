package app.payfun.com.gofoodapos.Network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by ComAg on 16. 3. 14..
 */
public class ConnectInfo {

    /**
     * 리턴 타입 : (S : 성공, E : 실패)
     */
    public static String returnType = "rtntype";
    // 성공
    public static String returnSuccess = "S";
    // 실패
    public static String returnError = "E";
    /**
     * 에러 메시지
     */
    public static String errorMessage = "errmsg";
    /**
     * 결과 값
     */
    public static String resultValue = "result";
    /**
     * API 통신 에러 메시지 모음
     */
    // Connect 타임 아웃
    public static String MSG_CONNECT_TIMEOUT = "서버 연결에 실패했습니다.\n다시 시도해주세요.(M1)";
    // 타임아웃
    public static String MSG_SOCTET_TIMEOUT = "서버에 응답이 없습니다.\n다시 시도해주세요.(M2)";
    // 네트워크 접속이 안됨
    public static String MSG_NETWORK_FIAL = "서버 연결에 실패했습니다.\n네트워크 연결 상태를 확인해주세요.(M)";
    // 도메인 에러
    public static String MSG_DOMAIN_ERROR = "도메인이 잘 못되었습니다.\n관리자에게 문의해주세요(M)";

    /**
     * 인터넷 사용여부 테스트
     *
     * @param _context
     * @return
     */
    public static boolean isNetworkConnectCheck(Context _context) {
        try {
            ConnectivityManager manager = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = manager.getActiveNetworkInfo();

            if (networkInfo != null && networkInfo.isConnected())
                return true;
            else
                return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public static enum errorCode {
        NotFound404, InternetFail, ServerError
    }

    public enum RequestType {
        MAP, LIST
    }
}
