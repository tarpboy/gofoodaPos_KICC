package app.payfun.com.gofoodapos.Login;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import app.payfun.com.gofoodapos.ActivityMain;
import app.payfun.com.gofoodapos.Define;
import app.payfun.com.gofoodapos.Network.ApiPart;
import app.payfun.com.gofoodapos.Network.ConnectInfo;
import app.payfun.com.gofoodapos.Network.ConnectPost;
import app.payfun.com.gofoodapos.Network.ConnectValue;
import app.payfun.com.gofoodapos.Network.PrintLog;
import app.payfun.com.gofoodapos.Popup.NoticeBtnOnePopup;
import app.payfun.com.gofoodapos.Popup.NoticePopup;
import app.payfun.com.gofoodapos.Popup.OnEventOkListener;
import app.payfun.com.gofoodapos.R;


/**
 * Created by Jonathan on 2016. 10. 6..
 */
public class FragmentLogin extends Fragment implements View.OnClickListener {


    Button btn_login_action;
    TextView tv_forgot_pass;
    TextView tv_regi;

    EditText etv_login_id, etv_login_pw;

    LinearLayout bt_login_back;

    CheckBox cb_save, cb_print_yn;

    RelativeLayout rl_progress;
    ProgressBar progressBar;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.framgent_login, container, false);


        rl_progress = (RelativeLayout)v.findViewById(R.id.rl_progress);
        progressBar = (ProgressBar)v.findViewById(R.id.progressBar);
        rl_progress.setVisibility(View.GONE);


        int PERMISSION_ALL = 1;
        String[] PERMISSIONS = { Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.WRITE_CONTACTS, Manifest.permission.SEND_SMS};

        if(!hasPermissions(getContext(), PERMISSIONS)){
            ActivityCompat.requestPermissions(getActivity(), PERMISSIONS, PERMISSION_ALL);
        }


        cb_save = (CheckBox)v.findViewById(R.id.cb_save);
        cb_print_yn =(CheckBox)v.findViewById(R.id.cb_print_yn);


        btn_login_action = (Button)v.findViewById(R.id.btn_login_action);
        btn_login_action.setOnClickListener(this);



        etv_login_id = (EditText)v.findViewById(R.id.etv_login_id);
        etv_login_pw = (EditText)v.findViewById(R.id.etv_login_pw);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            etv_login_id.setText(bundle.getString("EMAIL", ""));
        }


        SharedPreferences pref = getActivity().getSharedPreferences("GOFOODA", getActivity().MODE_PRIVATE);
        String bln = pref.getString("business_license_no", "");
        String pass = pref.getString("pass", "");
        String save = pref.getString("save","");
        if("Y".equals(save))
        {
            cb_save.setChecked(true);
            etv_login_id.setText(bln);
            etv_login_pw.setText(pass);
        }
        else
        {
            cb_save.setChecked(false);
        }



        String use_print = pref.getString("use_print", "");
        if("Y".equals(use_print))
        {
            cb_print_yn.setChecked(true);
        }
        else
        {
            cb_print_yn.setChecked(false);
        }










        return v;
    }



    @Override
    public void onClick(View v) {


        if (SystemClock.elapsedRealtime() - Define.mLastClickTime < 1000) {
            return;
        }
        Define.mLastClickTime = SystemClock.elapsedRealtime();

        switch (v.getId())
        {



            case R.id.btn_login_action:


                checkLogin();

//                Intent mainIntent = new Intent(getContext(), ActivityMain.class);
//                startActivity(mainIntent);
//                getActivity().finish();

                break;






        }
    }







    public static boolean hasPermissions(Context context, String... permissions) {
        Log.e("Jonathan", "11111");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            Log.e("Jonathan", "2222");
            for (String permission : permissions) {
                Log.e("Jonathan", "3333 :: " + permission);
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    Log.e("Jonathan", "4444");
                    return false;
                }
            }
        }
        return true;
    }



//    public void checkLogin()
//    {
//
//        if(!isNetworkAvailable())
//        {
//            popupInternet();
//            return;
//        }
//
//
//        ConnectValue connectValue = new ConnectValue();
//        connectValue.addConnectMap("business_license_no", etv_login_id.getText().toString());
//        connectValue.addConnectMap("pass", etv_login_pw.getText().toString());
//
//        ApiPart apiPart = new ApiPart(getContext());
//        apiPart.login(connectValue, new ConnectPost(getContext()) {
//            @Override
//            public void completeSync(String tag, StringBuffer result) {
//
//                Log.e("Jonatha", " result :: " + result);
//
//                JSONObject jsonObject = validResultData(tag, result);
//                try {
//                    Log.e("Jonathan", " jsonObject :: " + jsonObject.toString());
////                    Log.e("Jonathan", " jsonObject :: " + jsonObject.getString("business_license_no"));
//
//
//                    if(jsonObject.has("status"))
//                    {
//                        if("FAIL".equals(jsonObject.getString("status")))
//                        {
//
//                            final NoticePopup popup = new NoticePopup(getContext(), jsonObject.getString("message"), new OnEventOkListener() {
//                                @Override
//                                public void onOk() {
//
//                                }
//
//                                @Override
//                                public void onOk(String addMenu) {
//
//                                }
//
//                                @Override
//                                public void onOk(HashMap<String, String> addOrigin) {
//
//                                }
//
//
//                            });
//                            popup.setCanceledOnTouchOutside(false);
//                            popup.show();
//                            return;
//                        }
//                    }
//
//
//
//                    HashMap<String, String> getDataMap = new HashMap<String, String>();
//
//                    Iterator<String> key = jsonObject.keys();
//                    while (key.hasNext())
//                    {
//                        String DataKey = key.next();
//                        getDataMap.put(DataKey, jsonObject.get(DataKey).toString());
//                    }
//
//
//
//                    String business_license_no = Define.nullCheck(getDataMap.get("business_license_no"));
//                    String business_name = Define.nullCheck(getDataMap.get("business_name"));
//                    String rep_name = Define.nullCheck(getDataMap.get("rep_name"));
//                    String hp = Define.nullCheck(getDataMap.get("hp"));
//                    String pic_name = Define.nullCheck(getDataMap.get("pic_name"));
//                    String email = Define.nullCheck(getDataMap.get("email"));
//                    String pos_no = Define.nullCheck(getDataMap.get("pos_no"));
//
//                    String CurrentString = Define.nullCheck(getDataMap.get("reg_date"));
//                    String modi_date = "";
//                    String modi_time = "";
//                    if(!"".equals(CurrentString))
//                    {
//                        String[] separated = CurrentString.split(" ");
//
//                        modi_date = separated[0] + " " + separated[1] + separated[2];
//                        modi_time = separated[3] + " " + separated[4];
//
//                    }
//
//
//
//
//
//                    SharedPreferences pref = getActivity().getSharedPreferences("GOFOODA", getActivity().MODE_PRIVATE);
//                    SharedPreferences.Editor editor = pref.edit();
//                    editor.putString("business_license_no", business_license_no);
//                    editor.putString("business_name", business_name);
//                    editor.putString("rep_name", rep_name);
//                    editor.putString("hp", hp);
//                    editor.putString("pic_name", pic_name);
//                    editor.putString("email", email);
//
//                    editor.putString("modi_date", modi_date);
//                    editor.putString("modi_time", modi_time);
//                    editor.putString("pos_no", pos_no);
//
//
//                    if(cb_save.isChecked())
//                    {
//                        editor.putString("save", "Y");
//                    }
//                    else
//                    {
//                        editor.putString("save", "");
//                    }
//
//
//
//                    editor.commit();
//
//
////                    if("".equals(pos_no))
////                    {
////                        NoticeBtnOnePopup orderNotData = new NoticeBtnOnePopup(getContext(), "TID 번호가 없습니다. \n관리자에게 문의해주세요.", new OnEventOkListener() {
////                            @Override
////                            public void onOk() {
////                                Log.e("Jonathan", "111");
////                                getActivity().finish();
////                            }
////
////                            @Override
////                            public void onOk(String addMenu) {
////                                Log.e("Jonathan", "222");
////                            }
////
////                            @Override
////                            public void onOk(HashMap<String, String> addOrigin) {
////                                Log.e("Jonathan", "333");
////                            }
////                        });
////                        orderNotData.setCanceledOnTouchOutside(false);
////                        orderNotData.show();
////                        return;
////                    }
//
//
//                    regiDeviceInfo(business_license_no);
//
//
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//
//            }
//        });
//
//    }






    int loginErrorCount = 0;


    public void checkLogin()
    {


        rl_progress.setVisibility(View.VISIBLE);


        ConnectValue connectValue = new ConnectValue();
        connectValue.addConnectMap("business_license_no", etv_login_id.getText().toString());
        connectValue.addConnectMap("pass", etv_login_pw.getText().toString());

        ApiPart apiPart = new ApiPart(getContext());
        apiPart.login(connectValue, new ConnectPost(getContext()) {
            @Override
            public void completeSync(String tag, StringBuffer result) {

                rl_progress.setVisibility(View.GONE);
                Log.e("Jonatha", " result :: " + result);




                JSONObject jsonObject = validResultData(tag, result);

                try
                {
                    String returnType = "";
                    String errorMessage = "";
                    if (jsonObject.has(ConnectInfo.returnType))
                        returnType = Define.nullCheck(jsonObject.getString(ConnectInfo.returnType));


                    if("E".equals(returnType))
                    {
                        loginErrorCount++;
                        if(loginErrorCount < 4)
                        {
                            checkLogin();
                        }
                        else
                        {
                            popupInternet();
                        }
                    }


                }
                catch (Exception e)
                {

                }



                try {
                    Log.e("Jonathan", " jsonObject :: " + jsonObject.toString());
//                    Log.e("Jonathan", " jsonObject :: " + jsonObject.getString("business_license_no"));
                    loginErrorCount = 0;

                    if(jsonObject.has("responseStatus"))
                    {
                        if("FAIL".equals(jsonObject.getString("responseStatus")))
                        {

                            final NoticePopup popup = new NoticePopup(getContext(), jsonObject.getString("responseMessage"), new OnEventOkListener() {
                                @Override
                                public void onOk() {

                                }

                                @Override
                                public void onOk(String addMenu) {

                                }

                                @Override
                                public void onOk(HashMap<String, String> addOrigin) {

                                }

                            });
                            popup.setCanceledOnTouchOutside(false);
                            popup.show();
                            return;
                        }
                    }



                    HashMap<String, String> getDataMap = new HashMap<String, String>();
                    Iterator<String> key = jsonObject.keys();
                    while (key.hasNext())
                    {
                        String DataKey = key.next();
                        getDataMap.put(DataKey, jsonObject.get(DataKey).toString());
                    }


                    final ArrayList<HashMap<String, String>> tidList = new ArrayList<HashMap<String, String>>();
                    JSONArray tidListJson = new JSONArray(String.valueOf(jsonObject.getJSONArray("tidList")));
                    for(int i = 0 ; i < tidListJson.length() ; i++)
                    {
                        JSONObject rowJson = (JSONObject)tidListJson.get(i);
                        HashMap<String, String> dataMap = new HashMap<String, String>();

                        Iterator<String> key1 = rowJson.keys();
                        while (key1.hasNext())
                        {
                            String DataKey = key1.next();
                            dataMap.put(DataKey, rowJson.get(DataKey).toString());
                        }
                        tidList.add(dataMap);
                    }





                    final String business_license_no = Define.nullCheck(getDataMap.get("business_license_no"));
                    String business_name = Define.nullCheck(getDataMap.get("business_name"));
                    String rep_name = Define.nullCheck(getDataMap.get("rep_name"));
                    String hp = Define.nullCheck(getDataMap.get("hp"));
                    String pic_name = Define.nullCheck(getDataMap.get("pic_name"));
                    String email = Define.nullCheck(getDataMap.get("email"));

                    String CurrentString = Define.nullCheck(getDataMap.get("reg_date"));
                    String modi_date = "";
                    String modi_time = "";
                    if(!"".equals(CurrentString))
                    {
                        String[] separated = CurrentString.split(" ");

                        modi_date = separated[0] ;
                        modi_time = separated[1] ;

                    }




                    SharedPreferences pref = getActivity().getSharedPreferences("GOFOODA", getActivity().MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("business_license_no", business_license_no);
                    editor.putString("pass", etv_login_pw.getText().toString());
                    editor.putString("business_name", business_name);
                    editor.putString("rep_name", rep_name);
                    editor.putString("hp", hp);
                    editor.putString("pic_name", pic_name);
                    editor.putString("email", email);

                    editor.putString("modi_date", modi_date);
                    editor.putString("modi_time", modi_time);


                    editor.putString("loginDate", new SimpleDateFormat("yyyMMdd").format(new Date()));


                    if(cb_save.isChecked())
                    {
                        editor.putString("save", "Y");
                    }
                    else
                    {
                        editor.putString("save", "");
                    }



                    if(cb_print_yn.isChecked())
                    {
                        editor.putString("use_print", "Y");
                    }
                    else
                    {
                        editor.putString("use_print", "N");
                    }




                    editor.commit();





                    if(tidList.size() ==0)
                    {
                        Toast.makeText(getContext(), "TID번호가 발급되지 않았습니다. 관리자에게 문의하세요.", Toast.LENGTH_LONG).show();
                        return;
                    }








                    if(tidList.size() > 1)
                    {

                        AlertDialog.Builder builderSingle = new AlertDialog.Builder(getContext());
                        builderSingle.setTitle("TID 선택");

                        Collections.sort(tidList, new Comparator<HashMap<String, String>>() {
                            @Override
                            public int compare(HashMap<String, String> rhs, HashMap<String, String> lhs) {
                                return lhs.get("tid").compareTo(rhs.get("tid"));
                            }
                        });

                        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.select_dialog_singlechoice);
                        for(int i = 0 ; i < tidList.size() ; i++)
                        {
                            Log.e("Jonathan", "arryPayment :: " + tidList.get(i));
                            String data = "\n";
//                        if(tidList.get(i).containsKey("tid"))
//                        {
//                            data += "TID :: " + tidList.get(i).get("tid").toString() + "\n";
//                        }
                            if(tidList.get(i).containsKey("tid_desc"))
                            {
                                data += tidList.get(i).get("tid_desc").toString() ;
                            }

                            if(tidList.get(i).containsKey("tid"))
                            {
                                data += " [" + tidList.get(i).get("tid").toString() + "]" + "\n";
                            }


                            arrayAdapter.add(data);
                        }


                        builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });




                        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String strName = arrayAdapter.getItem(which);
                                final HashMap<String, String> hashMap = tidList.get(which);
                                SharedPreferences pref = getActivity().getSharedPreferences("GOFOODA", getActivity().MODE_PRIVATE);
                                SharedPreferences.Editor editor = pref.edit();
                                editor.putString("tid", hashMap.get("tid").toString());
                                editor.commit();
//                                regiDeviceInfo(business_license_no, hashMap.get("tid").toString());
                                getDeviceInfo(hashMap.get("tid").toString());
                                dialog.dismiss();
                            }
                        });



                        AlertDialog dialog = builderSingle.create();
                        ListView listView = dialog.getListView();
                        listView.setDivider(new ColorDrawable(Color.BLACK));
                        listView.setDividerHeight(2);
                        dialog.show();
                    }
                    else
                    {
                        SharedPreferences pref1 = getActivity().getSharedPreferences("GOFOODA", getActivity().MODE_PRIVATE);
                        SharedPreferences.Editor editor1 = pref1.edit();
                        editor1.putString("tid", tidList.get(0).get("tid").toString());
                        editor1.commit();
//                        regiDeviceInfo(business_license_no, tidList.get(0).get("tid").toString());
                        getDeviceInfo(tidList.get(0).get("tid").toString());
                    }















                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });


    }









    //여기....


    //uuid가 이거인것 가져오기
    public void getDeviceInfo(final String tid) {


        TelephonyManager tManager = (TelephonyManager)getActivity().getSystemService(Context.TELEPHONY_SERVICE);
        String uuid = tManager.getDeviceId();

        String android_id = Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID);
        Log.e("Jonathan", "android_id :: " + android_id);
        if("".equals(uuid) || "null".equals(uuid) || uuid == null)
        {
            uuid = android_id;
        }

        ConnectValue connectValue = new ConnectValue();
        connectValue.addConnectMap("uuid", uuid);
//        connectValue.addConnectMap("tid", tid);



        ApiPart apiPart = new ApiPart(getContext());
        apiPart.getDeviceInfo(connectValue, new ConnectPost(getContext()) {
            @Override
            public void completeSync(String tag, StringBuffer result) {

                try {
                    JSONArray jsonArray = new JSONArray(result.toString());

                    for (int i = 0 ; i < jsonArray.length() ;i++)
                    {
                        Log.e("Jonathan", "jsonArray :: " + jsonArray.length()  + "  " + jsonArray.get(i));
                    }

                    if(jsonArray.length() > 0)
                    {
                        updateDeviceInfo(tid);
                    }
                    else
                    {
                        regiDeviceInfo(tid);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }
        });

    }








    public void updateDeviceInfo(String tid) {


        rl_progress.setVisibility(View.VISIBLE);

        TelephonyManager tManager = (TelephonyManager)getActivity().getSystemService(Context.TELEPHONY_SERVICE);
        String uuid = tManager.getDeviceId();

        String android_id = Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID);
        Log.e("Jonathan", "android_id :: " + android_id);
        if("".equals(uuid) || "null".equals(uuid) || uuid == null)
        {
            uuid = android_id;
        }


        ConnectValue connectValue = new ConnectValue();
        connectValue.addConnectMap("uuid", uuid);
        connectValue.addConnectMap("token", ActivityLogin.token);
        connectValue.addConnectMap("type", "POS");
        connectValue.addConnectMap("pos_no", tid);
        connectValue.addConnectMap("tid", tid);



        ApiPart apiPart = new ApiPart(getContext());
        apiPart.updateDeviceInfo(connectValue, new ConnectPost(getContext()) {
            @Override
            public void completeSync(String tag, StringBuffer result) {
                rl_progress.setVisibility(View.GONE);
                JSONObject jsonObject = validResultData(tag, result);
                try {
                    String responseStatus = jsonObject.getString("responseStatus");

                    if("SUCCESS".equals(responseStatus))
                    {

                        Intent mainIntent = new Intent(getContext(), ActivityMain.class);
                        startActivity(mainIntent);
                        getActivity().finish();

                    }
                    else
                    {

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }
        });

    }





    public void regiDeviceInfo( String tid) {

        rl_progress.setVisibility(View.VISIBLE);
        TelephonyManager tManager = (TelephonyManager)getActivity().getSystemService(Context.TELEPHONY_SERVICE);
        String uuid = tManager.getDeviceId();


        String android_id = Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID);
        Log.e("Jonathan", "android_id :: " + android_id);
        if("".equals(uuid) || "null".equals(uuid) || uuid == null)
        {
            uuid = android_id;
        }

        ConnectValue connectValue = new ConnectValue();
        connectValue.addConnectMap("uuid", uuid);
        connectValue.addConnectMap("token", ActivityLogin.token);
        connectValue.addConnectMap("type", "POS");
        connectValue.addConnectMap("pos_no", tid);
        connectValue.addConnectMap("tid", tid);



        ApiPart apiPart = new ApiPart(getContext());
        apiPart.registerDeviceInfo(connectValue, new ConnectPost(getContext()) {
            @Override
            public void completeSync(String tag, StringBuffer result) {
                rl_progress.setVisibility(View.GONE);
                JSONObject jsonObject = validResultData(tag, result);
                try {
                    String responseStatus = jsonObject.getString("responseStatus");

                    if("SUCCESS".equals(responseStatus))
                    {

                        Intent mainIntent = new Intent(getContext(), ActivityMain.class);
                        startActivity(mainIntent);
                        getActivity().finish();

                    }
                    else
                    {

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }
        });

    }








    /**
     * Rest Data 체크
     */
    public JSONObject validResultData(String tag, StringBuffer result) {

        String message = "";
        JSONObject jsonObject = null;

        if (result == null) {

            message = "통신에 실패했습니다.";
        } else {

            try {
                jsonObject = new JSONObject(result.toString());

                PrintLog.print("OUTPUT START JSON", "=================================================");
                // 전달 받은 값 표시
                String log = jsonObject.toString(4);
                int count = 0;
                while (log.length() > 0) {
                    if (log.length() > 4000) {
                        PrintLog.print("OUTPUT JSON", log.substring(0, 4000));
                        log = log.substring(4000);
                    } else {
                        PrintLog.print("OUTPUT JSON", log);
                        PrintLog.print("OUTPUT END JSON", "=================================================");
                        break;
                    }

//                    if (count > 2) {
//                        PrintLog.print("OUTPUT JSON", "Many Log ㅠ----ㅠ");
//                        break;
//                    }

                    count++;
                }

                String returnType = "";
                String errorMessage = "";
                if (jsonObject.has(ConnectInfo.returnType))
                    returnType = Define.nullCheck(jsonObject.getString(ConnectInfo.returnType));
                if (jsonObject.has(ConnectInfo.errorMessage))
                    errorMessage = jsonObject.getString(ConnectInfo.errorMessage);//Define.nullCheck(jsonObject.getString(ConnectInfo.errorMessage));

//                if (returnType.length() == 0) {
//                    JSONObject resultObject = jsonObject.optJSONObject(ConnectInfo.resultValue);
//                    if (resultObject.has(ConnectInfo.returnType))
//                        returnType = Define.nullCheck(resultObject.getString(ConnectInfo.returnType));
//                    if (resultObject.has(ConnectInfo.errorMessage))
//                        errorMessage = resultObject.getString(ConnectInfo.errorMessage);//Define.nullCheck(resultObject.getString(ConnectInfo.errorMessage));
//                }
//
//                // 에러일 경우에만 메시지를 보여준다.
//                if (returnType.equals(ConnectInfo.returnError)) {
//                    message = errorMessage;
//                }

            } catch (JSONException e) {

                message = e.getMessage();
                e.printStackTrace();
            }
        }

        if (message.length() > 0) {

//            AlertMessage alertFragment = new AlertMessage(getContext());
//            alertFragment.simpleAlert(Define.nullCheck(message));

            return null;
        }

        return jsonObject;
    }



    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }



    public void popupInternet()
    {
        NoticeBtnOnePopup orderNotData = new NoticeBtnOnePopup(getContext(), "인터넷 연결이 되지 않았습니다.\n 확인하시고 다시 시도해주세요.", new OnEventOkListener() {
            @Override
            public void onOk() {

                Log.e("Jonathan", "111");

            }

            @Override
            public void onOk(String addMenu) {

                Log.e("Jonathan", "222");

            }

            @Override
            public void onOk(HashMap<String, String> addOrigin) {

                Log.e("Jonathan", "333");

            }
        });
        orderNotData.setCanceledOnTouchOutside(false);
        orderNotData.show();
    }












}
