package app.payfun.com.gofoodapos.Network;

import android.content.Context;

/**
 * Created by ComAg on 16. 3. 14..
 * 부품 관련 API 모음
 */
public class ApiPart extends ApiInfo {

    // 사업자 등록
    private String API_REGI = "/company/setInfo.do";

    // 사업자 정보 조회
    private String API_SELECT_COMPANY_INFO = "/company/getInfo.do";


    // 로그인
    private String API_LOGIN = "/company/login.do";

    // 사업자 중복체크
    private String API_COMPANY_EXIST_CHECK = "/company/existCompany.do";



    // 디바이스 등록
    private String API_REGI_DEVICE = "/device/setInfo.do";

    // 디바이스 조회
    private String API_GET_DEVICE = "/device/getInfoList.do";

    // 디바이스 수정
    private String API_UPDATE_DEVICE = "/device/updateInfo.do";


    //대메뉴 등록
    private String API_REGI_MENU1 = "/depth1menu/setInfo.do";

    //대메뉴 수정
    private String API_UPDATE_MENU1 = "/depth1menu/updateInfo.do";

    //대메뉴 조회
    private String API_SELECT_MENU1 = "/depth1menu/getInfoList.do";



    //메뉴 등록
    private String API_REGI_MENU = "/menu/setInfo.do";

    //메뉴 수정
    private String API_UPDATE_MENU = "/menu/updateInfo.do";

    //메뉴 조회
    private String API_SELECT_MENU = "/menu/getInfoList.do";



    //원산지 등록
    private String API_REGI_ORIGIN = "/menuOrigin/setInfo.do";




    //메뉴랑 원산지랑 같이 등록
    private String API_REGI_MENU_ORIGIN = "/menu/setInfoWithOriginList.do";


    //메뉴랑 원산지랑 같이 조회
    private String API_SELECT_MENU_ORIGIN = "/menu/getInfoWithOriginList.do";




    //푸시 보내기
//    private String API_PUSH = "/push/sendPushByUUID.do";
    private String API_PUSH = "/push/sendPushByBLN.do";







    //주문등록
    private String API_REGI_ORDER = "/order/setInfo.do";

    //주문조회
    private String API_SELECT_ORDER = "/order/getInfoList.do";

    //주문수정
    private String API_UPDATE_ORDER = "/order/updateInfo.do";




    //결재 등록
    private String API_REGI_PAY = "/payment/setInfo.do";


    //결재 조회
    private String API_SELECT_PAY = "/payment/getInfoList.do";


    //결재 수정
    private String API_UPDATE_PAY = "/payment/updateInfo.do";


    //카드 결재 등록
    private String API_CARD_SET_PAY = "/cardPay/setInfo.do";



    //카드 결재 조회
    private String API_CARD_GET_PAY = "/cardPay/getInfoList.do";


    //카드 결재 수정
    private String API_CARD_UDATE_PAY = "/cardPay/updateInfo.do";








    //디바이스 정보 조회
    private String API_SELECT_DEVICE = "/device/getInfoList.do";



    public ApiPart(Context context) {
        super(context);
    }



    public void selectCompanyInfo(ConnectValue connectValue, ConnectSync connectSync) {

        connectSync.setConnectValue(connectValue);
        connectSync.execute(API_SELECT_COMPANY_INFO, getUrlIntoSession(API_SELECT_COMPANY_INFO), "json");
    }



    public void login(ConnectValue connectValue, ConnectSync connectSync) {

        connectSync.setConnectValue(connectValue);
        connectSync.execute(API_LOGIN, getUrlIntoSession(API_LOGIN), "json");
    }

    public void registerCompanyExistCheck(ConnectValue connectValue, ConnectSync connectSync) {

        connectSync.setConnectValue(connectValue);
        connectSync.execute(API_COMPANY_EXIST_CHECK, getUrlIntoSession(API_COMPANY_EXIST_CHECK), "json");
    }



    public void registerCompanyInfo(ConnectValue connectValue, ConnectSync connectSync) {

        connectSync.setConnectValue(connectValue);
        connectSync.execute(API_REGI, getUrlIntoSession(API_REGI), "json");
    }


    public void registerDeviceInfo(ConnectValue connectValue, ConnectSync connectSync) {

        connectSync.setConnectValue(connectValue);
        connectSync.execute(API_REGI_DEVICE, getUrlIntoSession(API_REGI_DEVICE), "json");
    }


    public void getDeviceInfo(ConnectValue connectValue, ConnectSync connectSync) {

        connectSync.setConnectValue(connectValue);
        connectSync.execute(API_GET_DEVICE, getUrlIntoSession(API_GET_DEVICE), "json");
    }

    public void updateDeviceInfo(ConnectValue connectValue, ConnectSync connectSync) {

        connectSync.setConnectValue(connectValue);
        connectSync.execute(API_UPDATE_DEVICE, getUrlIntoSession(API_UPDATE_DEVICE), "json");
    }







    public void registerMenu1Info(ConnectValue connectValue, ConnectSync connectSync) {

        connectSync.setConnectValue(connectValue);
        connectSync.execute(API_REGI_MENU1, getUrlIntoSession(API_REGI_MENU1), "json");
    }


    public void updateMenu1Info(ConnectValue connectValue, ConnectSync connectSync) {

        connectSync.setConnectValue(connectValue);
        connectSync.execute(API_UPDATE_MENU1, getUrlIntoSession(API_UPDATE_MENU1), "json");
    }


    public void selectMenu1Info(ConnectValue connectValue, ConnectSync connectSync) {

        connectSync.setConnectValue(connectValue);
        connectSync.execute(API_SELECT_MENU1, getUrlIntoSession(API_SELECT_MENU1), "json");
    }





    public void registerMenuInfo(ConnectValue connectValue, ConnectSync connectSync) {

        connectSync.setConnectValue(connectValue);
        connectSync.execute(API_REGI_MENU, getUrlIntoSession(API_REGI_MENU), "json");
    }


    public void updateMenuInfo(ConnectValue connectValue, ConnectSync connectSync) {

        connectSync.setConnectValue(connectValue);
        connectSync.execute(API_UPDATE_MENU, getUrlIntoSession(API_UPDATE_MENU), "json");
    }


    public void selectMenuInfo(ConnectValue connectValue, ConnectSync connectSync) {

        connectSync.setConnectValue(connectValue);
        connectSync.execute(API_SELECT_MENU, getUrlIntoSession(API_SELECT_MENU), "json");
    }






    public void registerOriginInfo(ConnectValue connectValue, ConnectSync connectSync) {

        connectSync.setConnectValue(connectValue);
        connectSync.execute(API_REGI_ORIGIN, getUrlIntoSession(API_REGI_ORIGIN), "json");
    }




    public void selectMenuOriginInfo(ConnectValue connectValue, ConnectSync connectSync) {

        connectSync.setConnectValue(connectValue);
        connectSync.execute(API_SELECT_MENU_ORIGIN, getUrlIntoSession(API_SELECT_MENU_ORIGIN), "json");
    }




    public void registerMenuOriginInfo(ConnectValue connectValue, ConnectSync connectSync) {

        connectSync.setConnectValue(connectValue);
        connectSync.execute(API_REGI_MENU_ORIGIN, getUrlIntoSession(API_REGI_MENU_ORIGIN), "json");
    }




    public void sendPush(ConnectValue connectValue, ConnectSync connectSync) {

        connectSync.setConnectValue(connectValue);
        connectSync.execute(API_PUSH, getUrlIntoSession(API_PUSH), "json");
    }




    public void regiOrder(ConnectValue connectValue, ConnectSync connectSync) {

        connectSync.setConnectValue(connectValue);
        connectSync.execute(API_REGI_ORDER, getUrlIntoSession(API_REGI_ORDER), "json");
    }


    public void updateOrder(ConnectValue connectValue, ConnectSync connectSync) {

        connectSync.setConnectValue(connectValue);
        connectSync.execute(API_UPDATE_ORDER, getUrlIntoSession(API_UPDATE_ORDER), "json");
    }




    public void selectOrder(ConnectValue connectValue, ConnectSync connectSync) {

        connectSync.setConnectValue(connectValue);
        connectSync.execute(API_SELECT_ORDER, getUrlIntoSession(API_SELECT_ORDER), "json");
    }







    public void regiPay(ConnectValue connectValue, ConnectSync connectSync) {

        connectSync.setConnectValue(connectValue);
        connectSync.execute(API_REGI_PAY, getUrlIntoSession(API_REGI_PAY), "json");
    }



    public void selectPay(ConnectValue connectValue, ConnectSync connectSync) {

        connectSync.setConnectValue(connectValue);
        connectSync.execute(API_SELECT_PAY, getUrlIntoSession(API_SELECT_PAY), "json");
    }



    public void updatePay(ConnectValue connectValue, ConnectSync connectSync) {

        connectSync.setConnectValue(connectValue);
        connectSync.execute(API_UPDATE_PAY, getUrlIntoSession(API_UPDATE_PAY), "json");
    }





    public void selectDevice(ConnectValue connectValue, ConnectSync connectSync) {

        connectSync.setConnectValue(connectValue);
        connectSync.execute(API_SELECT_DEVICE, getUrlIntoSession(API_SELECT_DEVICE), "json");
    }



    public void setCardPay(ConnectValue connectValue, ConnectSync connectSync) {

        connectSync.setConnectValue(connectValue);
        connectSync.execute(API_CARD_SET_PAY, getUrlIntoSession(API_CARD_SET_PAY), "json");
    }



    public void getCardPay(ConnectValue connectValue, ConnectSync connectSync) {

        connectSync.setConnectValue(connectValue);
        connectSync.execute(API_CARD_GET_PAY, getUrlIntoSession(API_CARD_GET_PAY), "json");
    }


    public void updateCardPay(ConnectValue connectValue, ConnectSync connectSync) {

        connectSync.setConnectValue(connectValue);
        connectSync.execute(API_CARD_UDATE_PAY, getUrlIntoSession(API_CARD_UDATE_PAY), "json");
    }




}
