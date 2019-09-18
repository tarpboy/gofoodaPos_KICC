package app.payfun.com.gofoodapos;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.Window;

import java.util.ArrayList;
import java.util.HashMap;

import app.payfun.com.gofoodapos.Login.ActivityLogin;
import app.payfun.com.gofoodapos.Popup.NoticePopup;
import app.payfun.com.gofoodapos.Popup.OnEventOkListener;
import app.payfun.com.gofoodapos.WebConnect.ConnectInsertLoginInfo;
import app.payfun.com.gofoodapos.WebConnect.ConnectVersionCheck;
import app.payfun.com.gofoodapos.WebConnect.InterfaceAsyncResponse;
import app.payfun.com.gofoodapos.WebConnect.InterfaceAsyncResponseMap;


public class ActivitySplash extends Activity implements View.OnClickListener  {

    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {



//        File folder = new File(Environment.getExternalStorageDirectory() +
//                File.separator + "ONWEDDINGIMAGE");
//
//        boolean success = true;
//        if (!folder.exists()) {
//            success = folder.mkdirs();
//        }
//
//
//        if (success) {
//            // Do something on success
//
//            for(int i = 0 ; i < 200 ; i++)
//            {
//                File folder1 = new File(Environment.getExternalStorageDirectory() +
//                        File.separator + "ONWEDDINGIMAGE" + File.separator +
//                        "PF" + String.format("%05d", i));
//                folder1.mkdirs();
//            }
//
//        } else {
//            // Do something else on failure
//        }




        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);


        mContext = this;


//        versionCheck();


            new Handler().postDelayed(new Runnable(){
                @Override
                public void run() {
                                /* Create an Intent that will start the Menu-Activity. */
                    Intent mainIntent = new Intent(ActivitySplash.this, ActivityLogin.class);
                    ActivitySplash.this.startActivity(mainIntent);
                    ActivitySplash.this.finish();
                }
            }, 2000);






    }


    public void versionCheck()
    {
        HashMap<String,String> LoadDataMap = new HashMap<String, String>();

        PackageInfo pInfo = null;
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        final String localVersion = pInfo.versionName;


        final ConnectVersionCheck versionCheck = new ConnectVersionCheck(this, new InterfaceAsyncResponseMap() {
            @Override
            public void processFinish(ArrayList<HashMap<String, String>> output) {
                Log.e("Jonathan", "db version :: " + output.get(0).get("VERSION") + " local version :: " + localVersion);

                String dbVersion = output.get(0).get("VERSION");

                if(localVersion.equals(dbVersion))
                {
                    SharedPreferences pref = getSharedPreferences("ONWEDDING", MODE_PRIVATE);
                    String ONWEDDING_ID = pref.getString("ONWEDDING_ID", "");
                    insertConnectionInfo(ONWEDDING_ID);

                }
                else
                {
                    NoticePopup popup = new NoticePopup(mContext, "앱 업데이트를 잊으셨군요~^^", new OnEventOkListener() {
                        @Override
                        public void onOk() {
                            final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                            try {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                            } catch (android.content.ActivityNotFoundException anfe) {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                            }

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
                }



            }
        });
        versionCheck.execute(LoadDataMap);






    }


    public void insertConnectionInfo(final String user_id)
    {

        String android_id = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

        HashMap<String,String> LoadDataMap = new HashMap<String, String>();
        LoadDataMap.put("DEVICE_MODEL", Build.MODEL);
        LoadDataMap.put("DEVICE_SDK", Build.VERSION.SDK);
        LoadDataMap.put("DEVICE_VER", Build.VERSION.RELEASE);
        LoadDataMap.put("DEVICE_UUID", android_id);
        LoadDataMap.put("USER_ID", user_id);

        final ConnectInsertLoginInfo mainHomeGetPlannerData = new ConnectInsertLoginInfo(this, new InterfaceAsyncResponse() {
            @Override
            public void processFinish(String output) {

                try
                {

                    new Handler().postDelayed(new Runnable(){
                        @Override
                        public void run() {
                                /* Create an Intent that will start the Menu-Activity. */
                            Intent mainIntent = new Intent(ActivitySplash.this, ActivityMain.class);
                            ActivitySplash.this.startActivity(mainIntent);
                            ActivitySplash.this.finish();
                        }
                    }, 2000);


                }
                catch (Exception e)
                {

                }
            }

        });

        mainHomeGetPlannerData.execute(LoadDataMap);

    }


    @Override
    public void onBackPressed() {

    }


    @Override
    public void onClick(View view) {

    }
}

