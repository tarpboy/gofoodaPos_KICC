package app.payfun.com.gofoodapos.WebConnect;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Jonathan on 2016. 8. 18..
 */
public class ConnectInsertLoginInfo extends AsyncTask<HashMap<String,String>, String, JSONObject> {

//  AsyncTask< 1, 2, 3 > 에서
//  1번 파라미터는 protected 3 doInBackground(1... params)으로 들어간다
//  2번 파라미터는
//  3번 파라미터는 protected void onPostExecute(3 result)  으로 들어간다  3번은 doInBackground 의 return 형태이기도 해야 한다

    HashMap<String,String> getData = new HashMap<>();
    Context context;

    public InterfaceAsyncResponse interfaceAsyncResponse = null;



    public ConnectInsertLoginInfo(Context context, InterfaceAsyncResponse delegate)
    {
        this.context = context;
        this.interfaceAsyncResponse = delegate;

//        dialog = new ProgressDialog(context);
    }

//    private ProgressDialog dialog;

    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();

//        this.dialog.setMessage("저장중...");
//        this.dialog.show();
    }

    @Override
    protected JSONObject doInBackground(HashMap<String,String>... params) {
        // TODO Auto-generated method stub
        JSONObject result = new JSONObject();
        getData = params[0];

        try {

            result = WebApi.InsertConnectionInfo(context, getData);

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

//        if (dialog.isShowing()) {
//            dialog.dismiss();
//        }

        String resultmsg = "";

        if(result == null){
            resultmsg = "-1";


        }
        else
        {
            resultmsg = result.toString();
        }


        interfaceAsyncResponse.processFinish(resultmsg);





    }


}
