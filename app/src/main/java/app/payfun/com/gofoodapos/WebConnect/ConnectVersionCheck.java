package app.payfun.com.gofoodapos.WebConnect;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by Jonathan on 2016. 8. 18..
 */
public class ConnectVersionCheck extends AsyncTask<HashMap<String,String>, String, ArrayList<HashMap<String, String>>> {

//  AsyncTask< 1, 2, 3 > 에서
//  1번 파라미터는 protected 3 doInBackground(1... params)으로 들어간다
//  2번 파라미터는
//  3번 파라미터는 protected void onPostExecute(3 result)  으로 들어간다  3번은 doInBackground 의 return 형태이기도 해야 한다

    Integer sdata = 0;
    HashMap<String,String> getData = new HashMap<String,String>();
    Context context;

    public InterfaceAsyncResponseMap interfaceAsyncResponseMap = null;



    public ConnectVersionCheck(Context context, InterfaceAsyncResponseMap delegate)
    {
        this.context = context;
        this.interfaceAsyncResponseMap = delegate;

    }




    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();

    }

    @Override
    protected ArrayList<HashMap<String, String>> doInBackground(HashMap<String,String>... params) {
        // TODO Auto-generated method stub
        JSONObject result = new JSONObject();
        getData = params[0];
        ArrayList<HashMap<String, String>> arryItem = new ArrayList<HashMap<String, String>>();

        try {

            result = WebApi.VersionCheck(context, getData);

            JSONArray jArr = result.getJSONArray("getData");


            for(int i = 0 ; i < jArr.length() ; i++)
            {
                HashMap<String, String> getDataMap = new HashMap<String, String>();
                Iterator<String> key = jArr.getJSONObject(i).keys();
                while (key.hasNext())
                {
                    String DataKey = key.next();
                    getDataMap.put(DataKey, jArr.getJSONObject(i).get(DataKey).toString());

                }

                arryItem.add(getDataMap);
            }




        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
        if(arryItem.size() > 0)
        {
            return arryItem;
        }
        else
        {
            return null;
        }
    }



    @Override
    protected void onPostExecute(ArrayList<HashMap<String, String>> result) {

        super.onPostExecute(result);


        if(result == null){

            return;
        }


        interfaceAsyncResponseMap.processFinish(result);


    }





}
