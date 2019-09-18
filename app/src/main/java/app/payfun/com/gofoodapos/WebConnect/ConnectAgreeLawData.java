package app.payfun.com.gofoodapos.WebConnect;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import app.payfun.com.gofoodapos.Popup.ProgressPopup;


/**
 * Created by Jonathan on 2016. 8. 18..
 */
public class ConnectAgreeLawData extends AsyncTask<HashMap<String,String>, String, String> {

//  AsyncTask< 1, 2, 3 > 에서
//  1번 파라미터는 protected 3 doInBackground(1... params)으로 들어간다
//  2번 파라미터는
//  3번 파라미터는 protected void onPostExecute(3 result)  으로 들어간다  3번은 doInBackground 의 return 형태이기도 해야 한다

    Integer sdata = 0;
    HashMap<String,String> getData = new HashMap<String,String>();
    Context context;

    public InterfaceAsyncResponse interfaceAsyncResponse = null;



    public ConnectAgreeLawData(Context context, InterfaceAsyncResponse delegate)
    {
        this.context = context;
        this.interfaceAsyncResponse = delegate;

        dialog = new ProgressPopup(context);
    }


    private ProgressPopup dialog;


    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();

        this.dialog.setMessage("로딩중...");
        this.dialog.show();
    }

    @Override
    protected String doInBackground(HashMap<String,String>... params) {
        // TODO Auto-generated method stub
        JSONObject result = new JSONObject();
        getData = params[0];
        ArrayList<HashMap<String, String>> arryItem = new ArrayList<HashMap<String, String>>();
        String content= "";

        try {

            result = WebApi.OtherAgreeLaw(context, getData);

            JSONArray jArr = result.getJSONArray("getData");

            for(int i = 0 ; i < jArr.length() ; i++)
            {
                HashMap<String, String> getDataMap = new HashMap<String, String>();

                Iterator<String> key = jArr.getJSONObject(i).keys();
                while (key.hasNext())
                {
                    String DataKey = key.next();
                    getDataMap.put(DataKey, jArr.getJSONObject(i).get(DataKey).toString());
                    if("CONTENT".equals(DataKey))
                    {
                        content = jArr.getJSONObject(i).get(DataKey).toString();
                    }
                }

                arryItem.add(getDataMap);
            }



        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }


        return  content;
    }



    @Override
    protected void onPostExecute(String result) {

        super.onPostExecute(result);

        if (dialog.isShowing()) {
            dialog.dismiss();
        }

        if(result == null){

            return;
        }


        interfaceAsyncResponse.processFinish(result);


    }





}
