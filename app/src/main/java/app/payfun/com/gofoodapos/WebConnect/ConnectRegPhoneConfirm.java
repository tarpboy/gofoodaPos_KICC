package app.payfun.com.gofoodapos.WebConnect;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import app.payfun.com.gofoodapos.Popup.ProgressPopup;


/**
 * Created by Jonathan on 2016. 8. 18..
 */
public class ConnectRegPhoneConfirm extends AsyncTask<HashMap<String,String>, String, JSONObject> {

//  AsyncTask< 1, 2, 3 > 에서
//  1번 파라미터는 protected 3 doInBackground(1... params)으로 들어간다
//  2번 파라미터는
//  3번 파라미터는 protected void onPostExecute(3 result)  으로 들어간다  3번은 doInBackground 의 return 형태이기도 해야 한다

    HashMap<String,String> getData = new HashMap<>();
    Integer sdata = 0;
    Context context;
    ArrayList<Map<String, String>> getMenuArray = new ArrayList<>();

    public InterfaceAsyncResponse interfaceAsyncResponse = null;



    public ConnectRegPhoneConfirm(Context context, InterfaceAsyncResponse delegate)
    {
        this.context = context;
        this.interfaceAsyncResponse = delegate;
        this.getMenuArray = getMenuArray;

        dialog = new ProgressPopup(context);
    }

    private ProgressPopup dialog;

    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();

        this.dialog.setMessage("저장중...");
        this.dialog.show();
    }

    @Override
    protected JSONObject doInBackground(HashMap<String,String>... params) {
        // TODO Auto-generated method stub
        JSONObject result = new JSONObject();
        getData = params[0];

        try {

            Log.e("Jonathan", "phoneConfirm3");
            result = WebApi.regPhoneConfirm(context, getData);

            String check = result.getString("getData");

            if("1".equals(check))
            {
//                SMSService.sendSMSAsync(getData.get("USER_CONFIRM_NUM").toString(), getData.get("USER_PHONE_NUM").toString());
            }



        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
        if(result == null|| result.equals(""))
        {
            return null;
        }
        else
        {
            return result;
        }
    }



    @Override
    protected void onPostExecute(JSONObject result) {

        super.onPostExecute(result);

        if (dialog.isShowing()) {
            dialog.dismiss();
        }


//        if(result == null){
//            return;
//        }
        String resultmsg = "";

        if(result == null){
            resultmsg = "-1";

//            NoticePopup popup = new NoticePopup(context, "네트워크 연결이 끊겼습니다.\n 네트워크를 연결 후 다시 시도해주세요", new OnEventOkListener() {
//                @Override
//                public void onOk() {
//                    // TODO Auto-generated method stub
//                }
//            });
//            popup.setCanceledOnTouchOutside(false);
//            popup.show();

        }
        else
        {
            resultmsg = result.toString();
        }


        interfaceAsyncResponse.processFinish(resultmsg);





    }


}
