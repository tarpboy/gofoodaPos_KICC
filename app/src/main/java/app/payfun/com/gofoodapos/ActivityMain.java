package app.payfun.com.gofoodapos;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import app.payfun.com.gofoodapos.Bluetooth.BluetoothService;
import app.payfun.com.gofoodapos.Bluetooth.DeviceListActivity;
import app.payfun.com.gofoodapos.Kicc.MakePrintMessage;
import app.payfun.com.gofoodapos.Network.ApiInfo;
import app.payfun.com.gofoodapos.Network.ApiPart;
import app.payfun.com.gofoodapos.Network.ConnectInfo;
import app.payfun.com.gofoodapos.Network.ConnectPost;
import app.payfun.com.gofoodapos.Network.ConnectValue;
import app.payfun.com.gofoodapos.Network.PrintLog;
import app.payfun.com.gofoodapos.Phone.ActivityPhone;
import app.payfun.com.gofoodapos.Popup.CashPopup;
import app.payfun.com.gofoodapos.Popup.NoticeBtnOnePopup;
import app.payfun.com.gofoodapos.Popup.OnEventOkListener;
import app.payfun.com.gofoodapos.Popup.OnPasswordOkListener;
import app.payfun.com.gofoodapos.Popup.QuestionPopup;
import app.payfun.com.gofoodapos.Popup.ReceiptPopup;

public class ActivityMain extends AppCompatActivity implements MenuGroupAdapter.OnItemClicked, View.OnClickListener, AdapterView.OnItemSelectedListener {


    ReceiptPopup receiptPopup = null;
    PasswordDialog passwordDialog = null;



    RelativeLayout rl_video;


    //************프린트 시작**************
//    Button bt_print;
//    Button bt_bluetuooth;
//    private static final int REQUEST_ENABLE_BT = 2;
//    BluetoothService mService = null;
//    BluetoothDevice con_dev = null;
//    private static final int REQUEST_CONNECT_DEVICE = 100;


    // Name of the connected device
    private String mConnectedDeviceName = null;
    // Local Bluetooth adapter
    private BluetoothAdapter mBluetoothAdapter = null;
    // Member object for the services
    private BluetoothService mService = null;




    //////////////////////////////////////////////////////////////////
    public static final int RESULT_OK = 0;
    public static final int RESULT_REFUSE = 1;
    public static final int RESULT_COMM_ERROR_CANCEL = 2;
    public static final int RESULT_COMM_ERROR = 3;
    public static final int RESULT_CONNECT_ERROR = 4;
    public static final int RESULT_USER_CANCEL = 5;

    public static final String EXTERNAL_CALL_FLAG = "mode";
    public static final String EXTERNAL_CALL_TRANTYPE = "trantype";
    public static final String EXTERNAL_CALL_AMOUNT = "amount";
    public static final String EXTERNAL_CALL_INST = "installment";
    public static final String EXTERNAL_CALL_CASHTYPE = "cashtype";
    public static final String EXTERNAL_CALL_PERSONID = "personid";
    public static final String EXTERNAL_CALL_RETURNURL = "retrnurl";
    public static final String EXTERNAL_CALL_BIZNO = "businessno";
    public static final String EXTERNAL_CALL_CATID = "catid";
    public static final String EXTERNAL_CALL_TRANNO = "tranno";
    public static final String EXTERNAL_CALL_RECEIPTMODE = "receiptmode";
    public static final String EXTERNAL_CALL_AUTHED_NO = "authedno";
    public static final String EXTERNAL_CALL_AUTHED_DATE = "autheddate";
    public static final String EXTERNAL_CALL_DONGLETYPE = "dongletype";
    public static final String EXTERNAL_CALL_DONGLECMD = "donglecmd";
    public static final String EXTERNAL_CALL_SURTAX = "surtax";
    public static final String EXTERNAL_CALL_SERVICETIP = "servicetip";
    public static final String EXTERNAL_CALL_TOTALAMOUNT = "totalamount";
    public static final String EXTERNAL_CALL_CASHID = "cashid";

    public static final String EXTERNAL_ANSWER_TRANCODE = "trancode";
    public static final String EXTERNAL_ANSWER_APPNO = "approvalno";
    public static final String EXTERNAL_ANSWER_APPDATE = "approvaldate";
    public static final String EXTERNAL_ANSWER_CARDNO = "cardno";
    public static final String EXTERNAL_ANSWER_TERMINALID = "catid";
    public static final String EXTERNAL_ANSWER_TRANNO = "tranno";
    public static final String EXTERNAL_ANSWER_RECEIPTNO = "receiptno";
    public static final String EXTERNAl_ANSWER_TOTALAMOUNT = "totalamount";
    public static final String EXTERNAL_ANSWER_SURTAX = "surtax";
    public static final String EXTERNAL_ANSWER_SEVICETIP = "servicetip";
    public static final String EXTERNAL_ANSWER_OUTPUTMSG = "outmessage";

    public static final String EXTERNAL_DEFINE_DONGLE_NONE = "0";
    public static final String EXTERNAL_DEFINE_DONGLE_M220 = "1";

    private EditText medtReceipt;

    private ArrayList<String> marraylistCatid;
    private String mstrCATID = null;

    private ArrayList<String> marraylistDngType;
    private ArrayList<String> marraySubTranType;
    private String mstrDongleType = null;

    private String mstrCashID = null;
    private View mviewTarget = null;
    private boolean misAppInput = false;

    private Spinner mspCatid = null;
    private Spinner mspDngType = null;
    private Spinner mspSubTranType = null;

    private int mSubTranType = 0;

    // LOOP TEST
    private final static int MAX_LOOP_COUNT = 20;
    private int mLoopCount = 0;


    //    public ArrayList<HashMap<String, String>> arryPayment = new ArrayList<>();
    public Context mContext;
    //////////////////////////////////////////////////////////////////




    ImageView iv_animation;
    RelativeLayout rl_back;
    RecyclerView rv_menu_group;
    ArrayList<HashMap<String, String>> menu_group_list;
    MenuGroupAdapter adapter_menu_group;


    ArrayList<HashMap<String, String>> arryMenu = new ArrayList<HashMap<String, String>>();
    ArrayList<ArrayList<HashMap<String, String>>> arryMenuOrigin = new ArrayList<ArrayList<HashMap<String, String>>>();
    private LinearLayout ll_menu;



    RecyclerView recyclerview_order;
    static ArrayList<HashMap<String, String>> order_list;
    OrderAdapter adapter_order;


    Button bt_clear_order;
    TextView tv_total_value;
    TextView tv_total_price;

    ImageView iv_test;
    Button iv_receit_list;


    Button bt_cash;
    Button bt_card;
    Button iv_order_cancel;
    Button iv_cash_cancel;

    Button bt_bluetooth_state;



    SliderLayout sl_ad;
    VideoView videoView;

    String business_license_no;
    String tid;

    String TRAN_NUM = "";

    ProgressBar progressBar;
    RelativeLayout rl_progress;


//    BluetoothAdapter mBluetoothAdapter;

    protected PowerManager.WakeLock mWakeLock;



    LinearLayout ll_all_screen;



    @Override
    public void onDestroy() {
        this.mWakeLock.release();

        super.onDestroy();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        rl_video = (RelativeLayout)findViewById(R.id.rl_video);
        rl_video.setVisibility(View.GONE);


        ll_all_screen = (LinearLayout)findViewById(R.id.ll_all_screen);
        ll_all_screen.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                isDayAfter();

                return false;
            }
        });


        rl_back = (RelativeLayout)findViewById(R.id.rl_back);
        iv_animation = (ImageView)findViewById(R.id.iv_animation);
        iv_animation.setVisibility(View.GONE);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        rl_progress = (RelativeLayout)findViewById(R.id.rl_progress);

        final PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        this.mWakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "My Tag");
        this.mWakeLock.acquire();



        SharedPreferences pref = getSharedPreferences("GOFOODA", MODE_PRIVATE);
        business_license_no = pref.getString("business_license_no", "");
        tid = pref.getString("tid", "");

        mContext = this;



        //************프린트 시작**************
//        bt_print = (Button)findViewById(R.id.bt_print);
//        bt_print.setOnClickListener(this);
//        bt_bluetuooth = (Button)findViewById(R.id.bt_bluetuooth);
//        bt_bluetuooth.setOnClickListener(this);



        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        // If the adapter is null, then Bluetooth is not supported
        if (mBluetoothAdapter == null) {
            Toast.makeText(mContext, "Bluetooth is not available",    Toast.LENGTH_LONG).show();
//            mService.stop();
//            finish();
        }
        mService = new BluetoothService(mContext, mHandler);



//        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
//        if (mBluetoothAdapter == null) {
//            // Device does not support Bluetooth
////            Toast.makeText(mContext,"블루투스 기능을 지원하지 않습니다.", Toast.LENGTH_SHORT).show();
//
//        } else {
//            if (!mBluetoothAdapter.isEnabled()) {
//                // Bluetooth is not enable :)
////                Toast.makeText(mContext,"블루투스 기능을 켜주세요.", Toast.LENGTH_SHORT).show();
//            }
//            else
//            {
//
//                mService = new BluetoothService(this, mHandler);
//                if( mService.isAvailable() == false ){
//                    Toast.makeText(this, "Bluetooth is not available", Toast.LENGTH_LONG).show();
//                    if(mBluetoothAdapter.isEnabled())
//                    {
//                        mService.stop();
//                    }
//
//                    finish();
//                }
//            }
//        }




        ///////////////////////////////////////////////////////////////
        /////////////////SMARTRO APP START ////////////////////////////
        ///////////////////////////////////////////////////////////////

        ArrayAdapter<String> adapter = null;
        ArrayAdapter<String> adapter1 = null;

        setContentLayout();

        // CAID
        EditText ctrlCATID = (EditText)findViewById(R.id.EditTextCATID);
        ctrlCATID.setText(tid);
//        ctrlCATID.setText("1079518002");

        EditText ctrlCompNo = (EditText)findViewById(R.id.EditTextCompanyNO);
        ctrlCompNo.setText(business_license_no);
//        ctrlCompNo.setText("1258627961");

        EditText ctrlAmount = (EditText)findViewById(R.id.EditTextAmount);
        ctrlAmount.setText("100");

        initComments(findViewById(R.id.edtReceipt));

        //
        //동글타입
        //
        marraylistDngType = new ArrayList<String>();

        marraylistDngType.add("0 - 비사용");
        marraylistDngType.add("1 - M220 이어폰");
        marraylistDngType.add("2 - M230 블루투스");
        marraylistDngType.add("3 - M220 블루투스");
        marraylistDngType.add("4 - M240 이어폰");
        marraylistDngType.add("5 - M241 블루투스");
        marraylistDngType.add("6 - M250 블루투스");


        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, marraylistDngType);
        mspDngType = (Spinner)findViewById(R.id.spinner2);
        mspDngType.setPrompt("동글 타입 선택");
        mspDngType.setAdapter(adapter);
        mspDngType.setOnItemSelectedListener(this);
        mspDngType.setTag("DNGTYPE");




        marraySubTranType = new ArrayList<String>();

        marraySubTranType.add("0 - 신용");
        marraySubTranType.add("1 - 신용취소");
        marraySubTranType.add("2 - 현금");
        marraySubTranType.add("3 - 현금취소");

        adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, marraySubTranType);
        mspSubTranType = (Spinner)findViewById(R.id.spinner3);
        mspSubTranType.setPrompt("재조회거래타입");
        mspSubTranType.setAdapter(adapter1);
        mspSubTranType.setOnItemSelectedListener(this);
        mspSubTranType.setTag("SUBTRANTYPE");


        ///////////////////////////////////////////////////////////////
        /////////////////SMARTRO APP END ////////////////////////////
        ///////////////////////////////////////////////////////////////





















        bt_bluetooth_state = (Button)findViewById(R.id.bt_bluetooth_state);
        bt_bluetooth_state.setBackground(getResources().getDrawable(android.R.drawable.presence_invisible));



        ll_menu = (LinearLayout)findViewById(R.id.ll_menu);

        rv_menu_group = (RecyclerView)findViewById(R.id.rv_menu_group);
        recyclerview_order = (RecyclerView)findViewById(R.id.recyclerview_order);

        tv_total_value = (TextView)findViewById(R.id.tv_total_value);
        tv_total_price = (TextView)findViewById(R.id.tv_total_price);


        sl_ad = (SliderLayout)findViewById(R.id.sl_ad);
        videoView = (VideoView)findViewById(R.id.videoView);
        sl_ad.setVisibility(View.VISIBLE);
        sl_ad.stopAutoCycle();
        videoView.setVisibility(View.GONE);


        tv_total_value.setText("개");
        tv_total_price.setText("원");


        bt_clear_order = (Button)findViewById(R.id.bt_clear_order);
        bt_clear_order.setOnClickListener(this);
        iv_cash_cancel = (Button)findViewById(R.id.iv_cash_cancel);
        iv_cash_cancel.setOnClickListener(this);


        bt_cash = (Button)findViewById(R.id.bt_cash);
        bt_card = (Button)findViewById(R.id.bt_card);
        bt_cash.setOnClickListener(this);
        bt_card.setOnClickListener(this);



        iv_test = (ImageView)findViewById(R.id.iv_test);
        iv_test.setOnClickListener(this);


        iv_receit_list = (Button)findViewById(R.id.iv_receit_list);
        iv_receit_list.setOnClickListener(this);

        iv_order_cancel = (Button)findViewById(R.id.iv_order_cancel);
        iv_order_cancel.setOnClickListener(this);

        LinearLayoutManager mLayoutManager_regi = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        menu_group_list = new ArrayList<>();
        order_list = new ArrayList<>();


        rv_menu_group.setLayoutManager(mLayoutManager_regi);
        adapter_menu_group = new MenuGroupAdapter();
        adapter_menu_group.setLinearLayoutManager(mLayoutManager_regi);
        adapter_menu_group.setRecyclerView(rv_menu_group);
        rv_menu_group.setAdapter(adapter_menu_group);
        adapter_menu_group.setOnClick(this);
        adapter_menu_group.addAll(menu_group_list);



        LinearLayoutManager orderLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerview_order.setLayoutManager(orderLayoutManager);
        adapter_order = new OrderAdapter();
        adapter_order.setLinearLayoutManager(orderLayoutManager);
        adapter_order.setRecyclerView(recyclerview_order);
        recyclerview_order.setAdapter(adapter_order);
        adapter_order.addAll(order_list);



        //Jonathan 190918 KICC 연동
        savebmp("background_kicc.png",R.drawable.background_kicc);
        savebmp("close_kicc.png",R.drawable.close_kicc);
        savebmp("card_kicc.png",R.drawable.card_kicc);



        addSliderAd();

        getMenuGroupInfo();


    }



    public void addSliderAd()
    {

        for(int i = 0 ; i < 2 ;i++)
        {
            final int idx = i;
            TextSliderView textSliderView = new TextSliderView(mContext);
            textSliderView
                    .image(R.drawable.gofooda)
                    .setScaleType(BaseSliderView.ScaleType.FitCenterCrop);
            textSliderView.bundle(new Bundle());


            sl_ad.addSlider(textSliderView);

        }
        sl_ad.setCurrentPosition(0);
        sl_ad.setPresetTransformer(SliderLayout.Transformer.DepthPage);
        sl_ad.setPresetIndicator(SliderLayout.PresetIndicators.Center_Top);

        sl_ad.startAutoCycle();

    }



    //************프린트 시작**************
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Define.MESSAGE_STATE_CHANGE:
//                    if (DEBUG)
//                        Log.i(TAG, "MESSAGE_STATE_CHANGE: " + msg.arg1);
                    switch (msg.arg1) {
                        case BluetoothService.STATE_CONNECTED:
                            Toast.makeText(mContext, "프린트 인쇄 가능!", Toast.LENGTH_SHORT).show();
//                            Print_Test();//
                            break;
                        case BluetoothService.STATE_CONNECTING:
//                            mTitle.setText(R.string.title_connecting);
                            break;
                        case BluetoothService.STATE_LISTEN:
                        case BluetoothService.STATE_NONE:
//                            mTitle.setText(R.string.title_not_connected);
                            break;
                    }
                    break;
                case Define.MESSAGE_WRITE:

                    break;
                case Define.MESSAGE_READ:

                    break;
                case Define.MESSAGE_DEVICE_NAME:
                    // save the connected device's name
                    mConnectedDeviceName = msg.getData().getString(Define.DEVICE_NAME);
                    bt_bluetooth_state.setBackground(getResources().getDrawable(android.R.drawable.presence_online));
//                    Toast.makeText(mContext, "Connected to " + mConnectedDeviceName, Toast.LENGTH_SHORT).show();
                    break;
                case Define.MESSAGE_TOAST:
//                    Toast.makeText(mContext, msg.getData().getString(Define.TOAST), Toast.LENGTH_SHORT) .show();
                    break;
                case Define.MESSAGE_CONNECTION_LOST:    //蓝牙已断开连接
                    bt_bluetooth_state.setBackground(getResources().getDrawable(android.R.drawable.presence_invisible));
//                    Toast.makeText(mContext, "Device connection was lost", Toast.LENGTH_SHORT).show();
                    break;
                case Define.MESSAGE_UNABLE_CONNECT:     //无法连接设备
                    bt_bluetooth_state.setBackground(getResources().getDrawable(android.R.drawable.presence_invisible));
//                    Toast.makeText(mContext, "Unable to connect device", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };



//    private final  Handler mHandler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                case BluetoothService.MESSAGE_STATE_CHANGE:
//                    switch (msg.arg1) {
//                        case BluetoothService.STATE_CONNECTED:   //������
//                            Toast.makeText(getApplicationContext(), "프린트 인쇄 가능!",
//                                    Toast.LENGTH_SHORT).show();
////                            bt_bluetuooth.setVisibility(View.GONE);
////                            bt_print.setVisibility(View.VISIBLE);
//                            break;
//                        case BluetoothService.STATE_CONNECTING:  //��������
//                            Log.d("Jonathan","STATE_CONNECTING");
//                            break;
//                        case BluetoothService.STATE_LISTEN:     //�������ӵĵ���
//                        case BluetoothService.STATE_NONE:
//                            Log.d("Jonathan","STATE_NONE");
//                            break;
//                    }
//                    break;
//                case BluetoothService.MESSAGE_CONNECTION_LOST:    //�����ѶϿ�����
////                    Toast.makeText(getApplicationContext(), "Device connection was lost",
////                            Toast.LENGTH_SHORT).show();
////                    bt_bluetuooth.setVisibility(View.VISIBLE);
////                    bt_print.setVisibility(View.GONE);
//                    break;
//                case BluetoothService.MESSAGE_UNABLE_CONNECT:     //�޷������豸
////                    Toast.makeText(getApplicationContext(), "Unable to connect device",
////                            Toast.LENGTH_SHORT).show();
//                    break;
//            }
//        }
//    };




    ///////////////////////////////////////////////////////////////
    /////////////////SMARTRO APP START ////////////////////////////
    ///////////////////////////////////////////////////////////////


    public void initComments(final View view) {
        EditText comment = (EditText) view.findViewById(R.id.edtReceipt);

        comment.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(final View v, final MotionEvent motionEvent) {
                if (v.getId() == R.id.edtReceipt) {
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                    switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
                        case MotionEvent.ACTION_UP:
                            v.getParent().requestDisallowInterceptTouchEvent(
                                    false);
                            break;
                    }
                }
                return false;
            }
        });

    }


    private void showCashIDInput(View v)
    {
        AlertDialog.Builder alertInput = new AlertDialog.Builder(this);
        mviewTarget = v;

        alertInput.setTitle("식별번호");
        alertInput.setMessage("현금영수증 식별번호 입력..");

        final EditText input = new EditText(this);
        alertInput.setView(input);

        alertInput.setPositiveButton("미리입력", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int whichButton)
            {
                mstrCashID = input.getText().toString();
                viewclicklistenerClick.onClick(mviewTarget);
                misAppInput = false;
            }
        });

        alertInput.setNegativeButton("APP 입력", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int whichButton)
            {
                mstrCashID = "X";
                misAppInput = true;
                viewclicklistenerClick.onClick(mviewTarget);
            }
        });

        alertInput.show();
    }

    private void goTransaction(View v, HashMap<String, String> getHash)
    {

        Intent intent = getPackageManager().getLaunchIntentForPackage("com.smartro.secapps.freepay");
        if (intent == null)
        {
            if(mBluetoothAdapter.isEnabled())
            {
                mService.stop();
            }

            intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setData(Uri.parse("market://details?id=" + "com.smartro.secapps.freepay"));
            startActivity(intent);
            return;
        }


        if(getHash != null && getHash.containsKey("order_no"))
        {
            cancleOrderNo = Define.nullCheck(getHash.get("order_no"));
            if("".equals(cancleOrderNo))
            {
                Toast.makeText(this, "주문번호가 잘못되었습니다. 관리자에게 연락해주세요.", Toast.LENGTH_SHORT).show();
                return;
            }
        }




        Intent intentTerm = new Intent(Intent.ACTION_VIEW);
        String strStringParams  = null;

        String strExMode   		= EXTERNAL_CALL_FLAG + "=";
        String strExAmount 		= EXTERNAL_CALL_AMOUNT + "=";
        String strExTranType 	= EXTERNAL_CALL_TRANTYPE + "=";
        String strExCashType 	= EXTERNAL_CALL_CASHTYPE + "=";
        String strExBizNo		= EXTERNAL_CALL_BIZNO + "=";
        String strExCATID		= EXTERNAL_CALL_CATID + "=";
        String strExReceiptMode = EXTERNAL_CALL_RECEIPTMODE + "=";
        String strExAuthNo		= EXTERNAL_CALL_AUTHED_NO + "=";
        String strExAuthDate	= EXTERNAL_CALL_AUTHED_DATE + "=";
        String strExDongleType  = EXTERNAL_CALL_DONGLETYPE + "=";
        String strExDongleCMD   = EXTERNAL_CALL_DONGLECMD + "=";
        String strExTotalAmount = EXTERNAL_CALL_TOTALAMOUNT + "=";
        String strExSurtax 		= EXTERNAL_CALL_SURTAX + "=";
        String strExInstallment = EXTERNAL_CALL_INST + "=";
        String strExCashID		= EXTERNAL_CALL_CASHID + "=";

		/* 주의!! Transaction Number는 자체 관리가 되어야 합니다.
		 * CAT ID별로 Transaction Number가 달라지기 때문에 관리되지 않으면 '거래 일련 번호 오류'가 발생될 수 있으므로,
		 * 거래 일련 번호는 CAT ID 마다 관리되어야 합니다.
		 */
        String strExTranNo 	    = EXTERNAL_CALL_TRANNO + "=";

		/*
		 * DongleType
		 *  "0" - 동글 사용 안 함
		 *  "1" - SMT M220
		 *  "2" - SMT M230
		 */
//        strExDongleType += mstrDongleType;
        strExDongleType += "5";

		/* ReceiptMode
		 * 영수증 화면 모드
		 *  "0" - MMS / Email 전송 시 영수증 화면 유지
		 *  "1" - MMS / Email 전송 시 영수증 화면 닫음
		 */
        strExReceiptMode += "2";

		/* Mode
		 * "normal" - APP 연동
		 * "web" - WEB 연동 (Retrul URL) 필요
		 * "pg" - PG거래
		 */
        strExMode   += "normal";
//        strExMode   += "pg";

		/*
		 * Amount 세팅
		 * Amount - 상품금액
		 * Surtax - 부가세
		 * TotalAmount - 총 금액
		 */
        //strExTotalAmount += "1004";
        EditText ctrlAmount = (EditText)findViewById(R.id.EditTextAmount);
//        String amount = ctrlAmount.getText().toString();
        String amount = tv_total_price.getText().toString().replace("원", "").replaceAll(",", "");
        if(getHash != null)
        {
            amount = getHash.get("totalamount");

        }

        strExTotalAmount += amount;
        strExSurtax += (int)(Integer.valueOf(amount) * 0.1 );
        strExAmount += (int)(Integer.valueOf(amount) - (int)(Integer.valueOf(amount)* 0.1));

        Log.e("Jonathan", " amount :: " + amount + "    ::    " + (Integer.valueOf(amount)) + "    ::    " + (Integer.valueOf(amount) * 0.1 ));
        Log.e("Jonathan", " strExSurtax :: " + strExSurtax);
        Log.e("Jonathan", " strExAmount :: " + strExAmount);

//        strExSurtax += (Integer.valueOf(amount) * 0.1 );
//        strExAmount += (Integer.valueOf(amount) - (Integer.valueOf(amount) * 0.1 ));


		/* TranType
		 * "card" - CARD 승인
		 * "card_cancel" - CARD 취소
		 * "cash" - CASH 승인
		 * "cash_cancel" - CASH 취소
		 */

		/*
		 * 취소시 승인일시/승인번호
		 */
        EditText edtAuthNo = (EditText)findViewById(R.id.edtAuthNo);
        EditText edtAuthDate = (EditText)findViewById(R.id.edtAuthDate);

        if(getHash != null)
        {
            strExAuthNo += getHash.get("approvalno");
            strExAuthDate += getHash.get("approvaldate");
        }

//        strExAuthNo += edtAuthNo.getText().toString();
//        strExAuthDate += edtAuthDate.getText().toString();

        if (v.getId() == R.id.iv_test)
        {
            strExTranType += "card";

            strExInstallment += "3";

            //할부개월이 있는 경우
            //strStringParams = "smartroapp://freepaylink?" + strExMode + "&" + strExAmount + "&" + strExTranType + "&" + strExInstallment;
            strStringParams = "smartroapp://freepaylink?" + strExMode + "&" + strExAmount + "&" + strExTranType;
        }
        else if (v.getId() == R.id.bt_card)
        {
            strExTranType += "card";

            strExInstallment += "3";

            //할부개월이 있는 경우
            //strStringParams = "smartroapp://freepaylink?" + strExMode + "&" + strExAmount + "&" + strExTranType + "&" + strExInstallment;
            strStringParams = "smartroapp://freepaylink?" + strExMode + "&" + strExAmount + "&" + strExTranType;
        }
        else if (v.getId() == R.id.iv_order_cancel)
        {
            strExTranType += "card_cancel";

            strExInstallment += "3";


            Log.e("Jonathan", "strExAuthNo :: " + strExAuthNo + " strExAuthDate :: " + strExAuthDate);
            //할부개월이 있는 경우
            //strStringParams = "smartroapp://freepaylink?" + strExMode + "&" + strExAmount + "&" + strExTranType + "&" + strExAuthNo + "&" + strExAuthDate  + "&" + strExInstallment;
            strStringParams = "smartroapp://freepaylink?" + strExMode + "&" + strExAmount + "&" + strExTranType + "&" + strExAuthNo + "&" + strExAuthDate;
        }
        else if (v.getId() == R.id.btnCash)
        {
            strExTranType += "cash";
			/* Cash Type
			 * "0" - 소비자 소득공제
			 * "1" - 사업자 지출증빙
			 * "2" - 가맹점 자진발급
			 * "3" - 간이 영수증 (서버 통신 안 함)
			 */
            strExCashType += "0";
			/*
			 * Cash ID
			 * 소득공제 - 휴대폰번호
			 * 지출증빙 - 사업자번호
			 */
            if (mstrCashID == null)
            {
                showCashIDInput(v);
                return;
            }
            else
            {
                if (!misAppInput)
                {
                    strExCashID += mstrCashID;
                    strStringParams = "smartroapp://freepaylink?" + strExMode + "&" + strExAmount + "&" + strExTranType + "&" + strExCashType + "&" + strExCashID;
                }
                else
                {
                    strStringParams = "smartroapp://freepaylink?" + strExMode + "&" + strExAmount + "&" + strExTranType + "&" + strExCashType;
                }
            }


            mstrCashID = null;
        }
        else if (v.getId() == R.id.btnCashCancel)
        {
            strExTranType += "cash_cancel";
			/* Cash Type
			 * "0" - 소비자 소득공제
			 * "1" - 사업자 지출증빙
			 * "2" - 가맹점 자진발급
			 * "3" - 간이 영수증 (서버 통신 안 함)
			 */
            strExCashType += "0";
			/*
			 * Cash ID
			 * 소득공제 - 휴대폰번호
			 * 지출증빙 - 사업자번호
			 */
            if (mstrCashID == null)
            {
                showCashIDInput(v);
                return;
            }
            else
            {
                if (!misAppInput)
                {
                    strExCashID += mstrCashID;
                    strStringParams = "smartroapp://freepaylink?" + strExMode + "&" + strExAmount + "&" + strExTranType + "&" + strExCashType + "&" + strExCashID;
                }
                else
                {
                    strStringParams = "smartroapp://freepaylink?" + strExMode + "&" + strExAmount + "&" + strExTranType + "&" + strExCashType;
                }
                strStringParams += "&" + strExAuthNo + "&" + strExAuthDate;
            }

            mstrCashID = null;
        }
        else if (v.getId() == R.id.btnDongleReg)
        {
			/* Dongle Command
			 * strExDongleCMD = "0001" //동글 등록
			 */
            strExDongleCMD += "0001";

            strStringParams = "smartroapp://freepaylink?" + strExMode + "&" + strExDongleCMD;
        }
        else if (v.getId() == R.id.btnReprint)
        {
            strExTranType += "reprint";

            //할부개월이 있는 경우
            //strStringParams = "smartroapp://freepaylink?" + strExMode + "&" + strExAmount + "&" + strExTranType + "&" + strExInstallment;
            strStringParams = "smartroapp://freepaylink?" + strExMode + "&" + strExAmount + "&" + strExTranType;
        }
        else if (v.getId() == R.id.btnLastQuery)
        {
            strExTranType += "lastquery";

            strStringParams = "smartroapp://freepaylink?" + strExMode + "&" + strExAmount + "&" + strExTranType + "&" + strExAuthNo + "&" + strExAuthDate;

            String subTranList[] = { "card", "card_cancel", "cash", "cash_cancel" };

            strStringParams += "&subtran=" + subTranList[mSubTranType];

            if(1 < mSubTranType)
                strStringParams += "&" + EXTERNAL_CALL_CASHTYPE + "=" + "0";

        }

        /**> 금액을 지정한다. **/
        strStringParams += "&" + strExAmount + "&" + strExSurtax + "&" + strExTotalAmount;

        /**> 거래 일련번호를 직접 지정한다. **/
        SharedPreferences pref = this.getSharedPreferences("GOFOODA", this.MODE_PRIVATE);
        String timnum = new SimpleDateFormat("HHmm").format(new Date());
        String tranno = pref.getString("tranno", timnum);
        Log.e("Jonathan", "tranno ::3 " + tranno);

        strExTranNo += tranno;


        /**> 별도로 사용할 사업자 번호 / CAT ID 설정. **/

		/*
		if (mstrCATID.equals("4353679001"))
		{
			strExBizNo += "2897500047";			//사업자번호
		}
		else if (mstrCATID.equals("4388588001"))
		{
			strExBizNo += "5262300194";			//사업자번호
		}
		// 에이퍼스
		else if (mstrCATID.equals("4300084001"))
		{
			strExBizNo += "7444600054";			//사업자번호
		}
		// 82와
		else if (mstrCATID.equals("4472229001"))
		{
			strExBizNo += "1935900014";			//사업자번호
		}
		else
		{
			strExBizNo += "2178114493";			//사업자번호
		}
		*/

        // 단말기 번호
        EditText ctrlCATID = (EditText)findViewById(R.id.EditTextCATID);
        mstrCATID = ctrlCATID.getText().toString();
        strExCATID += mstrCATID;

        // 사업자 번호
        EditText ctrlCompNo = (EditText)findViewById(R.id.EditTextCompanyNO);
        strExBizNo += ctrlCompNo.getText().toString();


        strStringParams += "&" + strExTranNo + "&" + strExBizNo + "&" + strExCATID;

        /**> 영수증 모드를 넣는다. **/
        strStringParams += "&" + strExReceiptMode;

        /**> 동글 타입을 넣는다. **/
        strStringParams += "&" + strExDongleType;

        // NO CVM
        EditText ctrlNoCVM = (EditText)findViewById(R.id.EditTextNoCVM);
        String strNOCVMValue = ctrlNoCVM.getText().toString();

        if(null!=strNOCVMValue && 0!=strNOCVMValue.length())
            strStringParams += "&" + "nocvm=" + strNOCVMValue;

        // 종이 영수증 출력
        EditText ctrlPaperPrint = (EditText)findViewById(R.id.EditTextPaperPrint);
        String strPaperPrint = ctrlPaperPrint.getText().toString();

        if(null!=strPaperPrint && 0!=strPaperPrint.length())
            strStringParams += "&" + "paper_receipt=" + strPaperPrint;

        // NFC 사용 옵션
        EditText ctrlNFC = (EditText)findViewById(R.id.EditTextNFC);
        String strNFC = ctrlNFC.getText().toString();

        if(null!=strNFC && 0!=strNFC.length())
            strStringParams += "&" + "nfc=" + strNFC;

        medtReceipt.setText("연동 중...");

        Log.e("Jonathan", " strStringParams :: " + strStringParams);

        TRAN_NUM = "";

        if(mBluetoothAdapter.isEnabled())
        {
            mService.stop();
        }

        Log.e("M150", strStringParams);
        intentTerm.addCategory(Intent.CATEGORY_BROWSABLE);
        intentTerm.addCategory(Intent.CATEGORY_DEFAULT);
        intentTerm.setData(Uri.parse(strStringParams));
        startActivityForResult(intentTerm, RESULT_OK);
    }

    View.OnClickListener viewclicklistenerClick = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            goTransaction(v, null);
        }
    };


    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        String strTemp = null;

        if (arg0.getTag() == null)
            return;

        if (arg0.getTag().equals("CATID"))
        {
            strTemp = marraylistCatid.get(arg2);

            if (strTemp != null)
            {
                mstrCATID = strTemp.substring(0, 10);
//                Toast.makeText(this, "선택된 CAT-ID: " + mstrCATID, Toast.LENGTH_SHORT).show();
            }
        }
        else if (arg0.getTag().equals("DNGTYPE"))
        {
            strTemp = marraylistDngType.get(arg2);

            if (strTemp != null)
            {
                mstrDongleType = strTemp.substring(0, 1);
//                Toast.makeText(this, "선택된 동글: " + mstrDongleType, Toast.LENGTH_SHORT).show();
            }
        }
        else if (arg0.getTag().equals("SUBTRANTYPE"))
        {
            mSubTranType = arg2;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        mstrCATID = "5010131112";
        mstrDongleType = "5";
    }




    private void setContentLayout()
    {
        Button btnCredit = (Button)findViewById(R.id.btnCredit);
        btnCredit.setOnClickListener(viewclicklistenerClick);

        Button btnCreditCancel = (Button)findViewById(R.id.btnCreditCancel);
        btnCreditCancel.setOnClickListener(viewclicklistenerClick);

        Button btnCash = (Button)findViewById(R.id.btnCash);
        btnCash.setOnClickListener(viewclicklistenerClick);

        Button btnCashCancel = (Button)findViewById(R.id.btnCashCancel);
        btnCashCancel.setOnClickListener(viewclicklistenerClick);

        Button btnDongleReg = (Button)findViewById(R.id.btnDongleReg);
        btnDongleReg.setOnClickListener(viewclicklistenerClick);

        Button btnReprint = (Button)findViewById(R.id.btnReprint);
        btnReprint.setOnClickListener(viewclicklistenerClick);

        Button btnLastQuery = (Button)findViewById(R.id.btnLastQuery);
        btnLastQuery.setOnClickListener(viewclicklistenerClick);

        medtReceipt = (EditText)findViewById(R.id.edtReceipt);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {

        if(requestCode == Define.REQUEST_CONNECT_DEVICE)
        {
            Log.e("Jonathan", "bluetooth2");
            switch (requestCode) {

                case Define.REQUEST_ENABLE_BT:      //���������
                    Log.e("Jonathan", "bluetooth3");
                    if (resultCode == Activity.RESULT_OK) {   //�����Ѿ���
                        Log.e("Jonathan", "bluetooth4");
                        Toast.makeText(this, "Bluetooth open successful", Toast.LENGTH_LONG).show();
                    } else {                 //�û������������
                        Log.e("Jonathan", "bluetooth5");
                        if(mBluetoothAdapter.isEnabled())
                        {
                            mService.stop();
                        }

                        finish();
                    }
                    break;
                case  Define.REQUEST_CONNECT_DEVICE:
                    if (resultCode == Activity.RESULT_OK) {
                        Log.e("Jonathan", "bluetooth6");
                        String address = data.getExtras().getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);

//                        con_dev = mService.getDevByMac(address);
//                        mService.connect(con_dev);

                        if (BluetoothAdapter.checkBluetoothAddress(address)) {
                            BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
                            // Attempt to connect to the device
                            mService.connect(device);

                            SharedPreferences pref = mContext.getSharedPreferences("GOFOODA", MODE_PRIVATE);
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putString("address", address);
                            editor.commit();

                        }
//                        SharedPreferences pref = getSharedPreferences("GOFOODA", MODE_PRIVATE);
//                        SharedPreferences.Editor editor = pref.edit();
//                        editor.putString("address", address);
//                        editor.commit();
                    }
                    break;
            }
        }
        else
        {


            if(mBluetoothAdapter.isEnabled())
            {
                mService.stop();
            }

            SharedPreferences pref = getSharedPreferences("GOFOODA", MODE_PRIVATE);
            String address = pref.getString("address", "");
            if(!"".equals(address))
            {
                BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
                // Attempt to connect to the device
                mService.connect(device);
//                con_dev = mService.getDevByMac(address);
//                mService.connect(con_dev);
            }


            String strResult = "";
            String strKey = "";

            final HashMap<String, String> hashMap = new HashMap<>();
            try
            {
                if(null!=data)
                {
                    Iterator<String> iteratorTemp = data.getExtras().keySet().iterator();


                    while (iteratorTemp.hasNext())
                    {
                        strKey = iteratorTemp.next();
                        strResult += "Key(" + strKey + ") Value [" + data.getStringExtra(strKey) + "] \n";
                        hashMap.put(strKey, data.getStringExtra(strKey));
                    }
//                arryPayment.add(hashMap);

                    Log.e("Jonathan", " strResult :: " +strResult);

                    EditText edtAuthNo = (EditText)findViewById(R.id.edtAuthNo);
                    EditText edtAuthDate = (EditText)findViewById(R.id.edtAuthDate);

                    edtAuthDate.setText(data.getStringExtra(EXTERNAL_ANSWER_APPDATE));
                    edtAuthNo.setText(data.getStringExtra(EXTERNAL_ANSWER_APPNO));

				/*
				 * 다음 거래를 위해 거래 일련번호를 1 증가 시킨다.
				 */
                    String strTemp = data.getStringExtra(EXTERNAL_ANSWER_TRANNO);
                    if (strTemp != null)
                    {
                        EditText edtTranNo = (EditText)findViewById(R.id.edtTranNo);
                        edtTranNo.setText(String.format("%04d", Integer.parseInt(strTemp) + 1));
                    }

                    medtReceipt.setText(strResult);
                }
                else
                    medtReceipt.setText("결과 수신 실패");



                SharedPreferences.Editor editor = pref.edit();
                if(resultCode == 0)
                {
                    String timnum = new SimpleDateFormat("HHmm").format(new Date());
                    String tranno = pref.getString("tranno", timnum);
                    Log.e("Jonathan", "tranno ::1 " + tranno);
                    editor.putString("tranno", String.format("%04d", Integer.parseInt(tranno) + 1));
                    Log.e("Jonathan", "tranno ::2 " + String.format("%04d", Integer.parseInt(tranno) + 1));
                    editor.commit();


                    if(!hashMap.get("trantype").contains("card_cancel"))
                    {
                        hashMap.put("ONLY", "Y");

                        receiptPopup = new ReceiptPopup(this, hashMap, mService, new OnEventOkListener() {
                            @Override
                            public void onOk() {
                                setOrderInfo("CARD", hashMap);

                            }

                            @Override
                            public void onOk(String addMenu) {

                                setOrderInfo("CARD", hashMap);

                            }

                            @Override
                            public void onOk(HashMap<String, String> addOrigin) {

                                setOrderInfo("CARD", hashMap);

                            }
                        });
                        receiptPopup.setCanceledOnTouchOutside(false);
                        receiptPopup.show();

                        final Handler handler  = new Handler();
                        final Runnable runnable = new Runnable() {
                            @Override
                            public void run() {
                                if (receiptPopup.isShowing()) {
                                    receiptPopup.dismiss();
                                    setOrderInfo("CARD", hashMap);
                                }
                            }
                        };
                        receiptPopup.setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                handler.removeCallbacks(runnable);
                            }
                        });

                        handler.postDelayed(runnable, 5000);

//                    saveCardPayInfo(hashMap);
                    }
                    else
                    {


                        Define.mp1.stop();
                        Define.mp1 = MediaPlayer.create(this, R.raw.cancle_complete);
                        Define.mp1.start();

                        hashMap.put("ONLY", "Y");

                        receiptPopup = new ReceiptPopup(this, hashMap, mService, new OnEventOkListener() {
                            @Override
                            public void onOk() {

                                updateCardInfo(hashMap);

                            }

                            @Override
                            public void onOk(String addMenu) {

                                updateCardInfo(hashMap);

                            }

                            @Override
                            public void onOk(HashMap<String, String> addOrigin) {

                                updateCardInfo(hashMap);

                            }
                        });
                        receiptPopup.setCanceledOnTouchOutside(false);
                        receiptPopup.show();
                        final Handler handler  = new Handler();
                        final Runnable runnable = new Runnable() {
                            @Override
                            public void run() {
                                if (receiptPopup.isShowing()) {
                                    receiptPopup.dismiss();
                                    updateCardInfo(hashMap);
                                }
                            }
                        };
                        receiptPopup.setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                handler.removeCallbacks(runnable);
                            }
                        });

                        handler.postDelayed(runnable, 5000);



                    }


                }
                else
                {
                    String msg = "";
                    if(resultCode == 1)
                    {
                        String timnum = new SimpleDateFormat("HHmm").format(new Date());
                        String tranno = pref.getString("tranno", timnum);
                        editor.putString("tranno", String.format("%04d", Integer.parseInt(tranno) + 1));
                        editor.commit();

                        msg = "거래 거절 (카드사 거절)";

                    }

                    if(resultCode == 2)
                        msg = "통신 오류로 인한 자동 취소";

                    if(resultCode == 3)
                        msg = "단순 통신 오류";

                    if(resultCode == 4)
                        msg = "서버 접속 오류";

                    if(resultCode == 5)
                        msg = "사용자 취소";

                    if(resultCode == 6)
                        msg = "망 취소 실패 됨\n" +
                                "(통신오류,2nd AC실패등에의해서망취소상황발생 시 내부적으로 망취소 하나, 망취소 자체가 실패하는 경우 “수기” 취소 진행이 필요합니다.)";




                    NoticeBtnOnePopup orderNotData = new NoticeBtnOnePopup(this, msg, new OnEventOkListener() {
                        @Override
                        public void onOk() {


                            bt_clear_order.performClick();

                        }

                        @Override
                        public void onOk(String addMenu) {

                            bt_clear_order.performClick();

                        }

                        @Override
                        public void onOk(HashMap<String, String> addOrigin) {

                            bt_clear_order.performClick();
                        }
                    });
                    orderNotData.setCanceledOnTouchOutside(false);
                    orderNotData.show();

                }




            }
            catch (Exception e) { e.printStackTrace(); };
        }

    }

    ///////////////////////////////////////////////////////////////
    /////////////////SMARTRO APP END //////////////////////////////
    ///////////////////////////////////////////////////////////////








    public void getCompanyInfo()
    {

        if(!isNetworkAvailable())
        {
            popupInternet();
            return;

        }


        rl_progress.setVisibility(View.VISIBLE);

        ConnectValue connectValue = new ConnectValue();
//        connectValue.addConnectMap("business_license_no", business_license_no);
        connectValue.addConnectMap("tid", tid);

        ApiPart apiPart = new ApiPart(this);
        apiPart.selectDevice(connectValue, new ConnectPost(this) {
            @Override
            public void completeSync(String tag, StringBuffer result) {

                Log.e("Jonatha", " result :: " + result);

                rl_progress.setVisibility(View.GONE);

                ArrayList<HashMap<String, String>> arrayDevice = new ArrayList<HashMap<String, String>>();


                try {

                    JSONArray jsonArray = new JSONArray(String.valueOf(result));
                    for(int i = 0 ; i < jsonArray.length() ; i++)
                    {
//                        Log.e("jonathan", "jsonArray : " + jsonArray.get(i));
                        JSONObject rowJson = (JSONObject) jsonArray.get(i);
                        HashMap<String, String> getDataMap = new HashMap<String, String>();

                        Iterator<String> key = rowJson.keys();
                        while (key.hasNext())
                        {
                            String DataKey = key.next();
                            getDataMap.put(DataKey, rowJson.get(DataKey).toString());
//                            Log.e("jonathan", "DataKey : " + DataKey + " val :: " + rowJson.get(DataKey).toString());

                        }
                        arrayDevice.add(getDataMap);

                    }


                    for(int i = 0 ; i < arrayDevice.size() ; i++)
                    {
                        if("POS".equals(arrayDevice.get(i).get("type")))
                        {
                            if(!arrayDevice.get(i).containsKey("pos_no"))
                            {
                                NoticeBtnOnePopup orderNotData = new NoticeBtnOnePopup(mContext, "TID 번호가 없습니다. \n관리자에게 문의해주세요.", new OnEventOkListener() {
                                    @Override
                                    public void onOk() {
                                        Log.e("Jonathan", "111");
                                        finish();
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
                                return;
                            }
                            else
                            {
                                SharedPreferences pref = getSharedPreferences("GOFOODA", MODE_PRIVATE);
                                SharedPreferences.Editor editor = pref.edit();
                                editor.putString("pos_no", arrayDevice.get(i).get("pos_no"));
                                editor.commit();

                                EditText ctrlCATID = (EditText)findViewById(R.id.EditTextCATID);
                                ctrlCATID.setText(pref.getString("pos_no", ""));

                            }
                        }
                    }

                    getMenuGroupInfo();


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });
    }







    public void playVideo()
    {
        if(order_list.size() > 0)
        {
            sl_ad.setVisibility(View.GONE);
        }
        else
        {
            sl_ad.setVisibility(View.VISIBLE);
        }
    }









    public void remake()
    {
//        sl_ad.setVisibility(View.VISIBLE);
        playVideo();

        arryMenu.clear();
        arryMenuOrigin.clear();
        String menuGroup = menu_group_list.get(mCurrentPosition).get("depth1_grp_no").toString();
        getMenuInfo(menuGroup);
    }




    public void totalSum()
    {
        int totalValue = 0;
        int totalPrice = 0;



        if(order_list.size() == 0)
        {
            Define.mp1.stop();
            Define.mp1 = MediaPlayer.create(this, R.raw.selectmenu);
            Define.mp1.start();
        }



        if(order_list.size() == 1)
        {
            int value = Integer.valueOf(order_list.get(0).get("value").toString());
            if(value == 1)
            {
                Define.mp1.stop();
                Define.mp1 = MediaPlayer.create(this, R.raw.ifmenuselect);
                Define.mp1.start();
            }
        }


        for(int i = 0 ; i < order_list.size() ; i++)
        {
            int value = Integer.valueOf(order_list.get(i).get("value").toString());
            int price = Integer.valueOf(order_list.get(i).get("price").toString());

            totalValue += value;
            totalPrice += (value*price);

        }

        tv_total_value.setText(totalValue + "개");
        tv_total_price.setText(Define.formatToMoney(String.valueOf(totalPrice)));



    }







    public void makeButton1()
    {
        ll_menu.removeAllViews();



        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics ();
        display.getMetrics(outMetrics);
        DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
        int dpWidth = Math.round(displayMetrics.widthPixels / displayMetrics.density);

        Configuration configuration = getResources().getConfiguration();
        int screenWidthDp = configuration.screenWidthDp;

        int llWidth = ll_menu.getWidth();


        int dpMenuWidth = (llWidth)*9/30;
        int dpMenuHight = (llWidth)*9/30;
        int colum =3;


        if(arryMenuOrigin.size() == 1)
        {
            dpMenuWidth = (llWidth)*9/10;
            dpMenuHight = (llWidth)*9/10 * 6/5;
            colum =1;
        }
        else if(arryMenuOrigin.size() == 2)
        {
            dpMenuWidth = (llWidth)*9/20;
            dpMenuHight = (llWidth)*9/20* 3/2;
            colum =2;
        }
        else if(arryMenuOrigin.size() == 3)
        {
            dpMenuWidth = (llWidth)*9/30;
            dpMenuHight = (llWidth)*9/30* 13/10;
            colum =3;
        }
        else
        {
            dpMenuWidth = (llWidth)*9/30;
            dpMenuHight = (llWidth)*9/30* 13/10;
            colum =3;
        }


        final int csvDataSize = arryMenu.size();
        int rowSize = csvDataSize/colum;



        for(int j = 0 ; j < rowSize+1 ; j++)
        {
            LinearLayout linearLayout1 = new LinearLayout(this);
            LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params1.setMargins(0,10,0,0);
            linearLayout1.setOrientation(LinearLayout.HORIZONTAL);
            linearLayout1.setLayoutParams(params1);


            for(int k = 0 ; k < colum ; k++)
            {
                if(j*colum+k == csvDataSize)
                {
                    break;
                }


                final int f_k = k;
                final int f_j = j;
                final int f_colum = colum;




                final LinearLayout relativeLayout = new LinearLayout(this);
                LinearLayout.LayoutParams rl_params = new LinearLayout.LayoutParams(dpMenuWidth, dpMenuHight);
                if(k == 1)
                {
                    rl_params.setMargins((llWidth)*1/30,0,(llWidth)*1/30,0);
                }
                relativeLayout.setOrientation(LinearLayout.VERTICAL);
                relativeLayout.setBackgroundResource(R.drawable.edittext_round_3);
                relativeLayout.setLayoutParams(rl_params);
                relativeLayout.setTag(arryMenu.get((f_j*f_colum)+f_k).get("use_yn"));







                final LinearLayout ll_origin = new LinearLayout(this);
                LinearLayout.LayoutParams ll_origin_params = new LinearLayout.LayoutParams(dpMenuWidth, dpMenuHight*3/5);
                ll_origin.setLayoutParams(ll_origin_params);
                ll_origin.setGravity(Gravity.CENTER);
                ll_origin.setBackgroundResource(R.color.blackalpha);

                TextView tv_origin = new TextView(this);
                LinearLayout.LayoutParams tv_origin_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                tv_origin_params.gravity = Gravity.CENTER;
                tv_origin.setLayoutParams(tv_origin_params);
                tv_origin.setTextColor(this.getResources().getColor(R.color.white));
                tv_origin.setTypeface(tv_origin.getTypeface(), Typeface.BOLD);
                tv_origin.setTextSize(13);
                String origin = "";
                for(int i = 0 ;i < arryMenuOrigin.get(((j*colum)+k)).size() ;i++)
                {
                    origin += arryMenuOrigin.get((j*colum)+k).get(i).get("material_name");
                    origin += "(" + arryMenuOrigin.get((j*colum)+k).get(i).get("weight") + "g)";
                    origin += "-";
                    origin += arryMenuOrigin.get((j*colum)+k).get(i).get("origin");
                    origin += "\n";
                }
                tv_origin.setText(origin);
                ll_origin.addView(tv_origin);
                ll_origin.setVisibility(View.GONE);


                RelativeLayout rl_image = new RelativeLayout(this);
                LinearLayout.LayoutParams rl_params2 = new LinearLayout.LayoutParams(dpMenuWidth, dpMenuHight*3/5);
                rl_image.setLayoutParams(rl_params2);


                Button bt_origin = new Button(this);
                LinearLayout.LayoutParams bt_origin_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                bt_origin_params.gravity = Gravity.RIGHT;
                bt_origin.setLayoutParams(bt_origin_params);
                bt_origin.setBackgroundResource(R.color.default_blue);
                bt_origin.setTextColor(this.getResources().getColor(R.color.white));
                bt_origin.setTextSize(19);
                bt_origin.setText("원산지");
                bt_origin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if(ll_origin.getVisibility() == View.VISIBLE)
                        {
                            ll_origin.setVisibility(View.GONE);
                        }
                        else
                        {
                            ll_origin.setVisibility(View.VISIBLE);
                        }

                    }
                });


                ImageView imageView = new ImageView(this);
                LinearLayout.LayoutParams iv_params = new LinearLayout.LayoutParams(dpMenuWidth,  dpMenuHight*3/5);
                iv_params.gravity = Gravity.CENTER_VERTICAL;
                iv_params.gravity = Gravity.TOP;
                imageView.setLayoutParams(iv_params);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

                HashMap<String, String> singleData = new HashMap<>();
                if(j*colum+k != csvDataSize)
                {
                    imageView.setTag(arryMenu.get((j*colum)+k).get("use_yn"));
                    singleData = arryMenu.get((j*colum)+k);
//                    Picasso.with(this).load(R.drawable.box_pic).fit().into(imageView);
                }

                SharedPreferences pref = mContext.getSharedPreferences("GOFOODA", MODE_PRIVATE);
//                String business_license_no = pref.getString("business_license_no", "");
//                String imagUrl = ApiInfo.SERVER_URL + "/getImage.do?business_license_no=" + business_license_no+"&imageId=";
                String imagUrl = ApiInfo.SERVER_URL + "/getImage.do?tid=" + tid+"&imageId=";
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                if("".equals(arryMenu.get((j*colum)+k).get("pic_name")))
                {
                    imageView.setImageResource(R.drawable.box_pic);
                }
                else
                {
                    Picasso.with(mContext).load(imagUrl + arryMenu.get((j*colum)+k).get("pic_name")).into(imageView);
                }

                final HashMap<String, String > orderHash = arryMenu.get((j*colum)+k);
                Log.e("Jonathan", "orderHash :: " + orderHash);


                relativeLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


//                        Animation animSequential = AnimationUtils.loadAnimation(getApplicationContext(),
//                                R.anim.move);
//                        // Sequential
//                        view.startAnimation(animSequential);

                        int leftAll = rl_back.getLeft();
                        int bottomAll = rl_back.getBottom();

                        int[] locationRc = new int[2];
                        recyclerview_order.getLocationOnScreen(locationRc);
                        int xRc = locationRc[0];
                        int yRc = locationRc[1];


                        TranslateAnimation translateAnimation = new TranslateAnimation(leftAll, 0, 0, bottomAll);
                        translateAnimation.setDuration(800);
                        translateAnimation.setInterpolator(new AccelerateInterpolator());
                        view.startAnimation(translateAnimation);



                        int[] location = new int[2];
                        view.getLocationOnScreen(location);
                        int x = location[0];
                        int y = location[1];



                        iv_animation.setTranslationX(x);
                        iv_animation.setTranslationY(y);

                        iv_animation.animate().translationX(xRc).translationY(yRc).setDuration(800).setInterpolator(new AccelerateDecelerateInterpolator()).start();

                        translateAnimation.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {
                                iv_animation.setVisibility(View.VISIBLE);

                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                Animation shake = AnimationUtils.loadAnimation(mContext, R.anim.shake);
                                recyclerview_order.startAnimation(shake);
                                iv_animation.setVisibility(View.GONE);
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });





                        if("N".equals(view.getTag().toString()))
                        {
                            return;
                        }



                        HashMap<String, String > orderHash = new HashMap<String, String>();
                        orderHash = arryMenu.get((f_j*f_colum)+f_k);
                        for(int i = 0 ; i < order_list.size() ; i++)
                        {
                            String menu_no = order_list.get(i).get("menu_no").toString();
                            if(menu_no.equals(orderHash.get("menu_no").toString()))
                            {
                                String value = order_list.get(i).get("value").toString();
                                int intValue = Integer.valueOf(value);
                                intValue++;
                                orderHash.put("value", String.valueOf(intValue));
                                order_list.get(i).put("value", String.valueOf(intValue));

                                break;
                            }

                        }

                        if(!orderHash.containsKey("value"))
                        {
                            orderHash.put("value", "1");
                            order_list.add(orderHash);
                        }

                        adapter_order.addAll(order_list);
                        totalSum();

//                        sl_ad.setVisibility(View.VISIBLE);
                        playVideo();

                    }
                });


                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View view) {

                        Log.e("Jonathan", "image click");

                        relativeLayout.performClick();

                    }
                });




                TextView bt_text = new TextView(this);
                LinearLayout.LayoutParams bt_text_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                bt_text_params.gravity = Gravity.CENTER_HORIZONTAL;
                bt_text.setLayoutParams(bt_text_params);
                bt_text.setTextColor(this.getResources().getColor(R.color.black));
                bt_text.setTextSize(19);
                bt_text.setText(arryMenu.get((j*colum)+k).get("menu_name"));


                TextView bt_text_eng = new TextView(this);
                LinearLayout.LayoutParams bt_text_eng_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                bt_text_eng_params.gravity = Gravity.CENTER_HORIZONTAL;
                bt_text_eng.setLayoutParams(bt_text_eng_params);
                bt_text_eng.setTextColor(this.getResources().getColor(R.color.black));
                bt_text_eng.setTextSize(19);
                bt_text_eng.setText("(" + arryMenu.get((j*colum)+k).get("menu_eng_name") + ")");


                LinearLayout ll_line = new LinearLayout(this);
                LinearLayout.LayoutParams ll_line_param = new LinearLayout.LayoutParams(100, 2);
                ll_line_param.setMargins(0,10,0,0);
                ll_line_param.gravity = Gravity.CENTER_HORIZONTAL;
                ll_line.setOrientation(LinearLayout.HORIZONTAL);
                ll_line.setLayoutParams(ll_line_param);
                ll_line.setBackgroundColor(this.getResources().getColor(R.color.defaul_red));



                TextView bt_price = new TextView(this);
                LinearLayout.LayoutParams bt_price_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                bt_price_params.setMargins(0,10,0,0);
                bt_price_params.gravity = Gravity.CENTER_HORIZONTAL;
                bt_price.setLayoutParams(bt_price_params);
                bt_price.setTextColor(this.getResources().getColor(R.color.black));
                bt_price.setTextSize(26);
                bt_price.setText(Define.formatToMoney(arryMenu.get((j*colum)+k).get("price")));





                //품절 상품
                String use_yn = arryMenu.get((j*colum)+k).get("use_yn");
                final LinearLayout ll_sold_out = new LinearLayout(this);
                LinearLayout.LayoutParams ll_sold_out_params = new LinearLayout.LayoutParams(dpMenuWidth, dpMenuHight);
                if("N".equals(use_yn))
                {
                    ll_sold_out.setLayoutParams(ll_sold_out_params);
                    ll_sold_out.setGravity(Gravity.CENTER);
                    ll_sold_out.setBackgroundResource(R.color.blackalpha);

                    TextView tv_sold_out = new TextView(this);
                    LinearLayout.LayoutParams tv_sold_out_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    tv_origin_params.gravity = Gravity.CENTER;
                    tv_sold_out.setLayoutParams(tv_sold_out_params);
                    tv_sold_out.setTextColor(this.getResources().getColor(R.color.white));
                    tv_sold_out.setTypeface(tv_sold_out.getTypeface(), Typeface.BOLD);
                    tv_sold_out.setTextSize(18);
                    String soldout = "품절";
                    tv_sold_out.setText(soldout);
                    ll_sold_out.addView(tv_sold_out);
                }



                rl_image.addView(imageView);
                rl_image.addView(bt_origin);
                rl_image.addView(ll_origin);
                rl_image.addView(ll_sold_out);

                relativeLayout.addView(rl_image);
                relativeLayout.addView(bt_text);
                relativeLayout.addView(bt_text_eng);
                relativeLayout.addView(ll_line);
                relativeLayout.addView(bt_price);


                linearLayout1.addView(relativeLayout);

            }


            ll_menu.addView(linearLayout1);

        }



    }








    //그룹 메뉴 가져오기
    public void getMenuGroupInfo()
    {


        if(!isNetworkAvailable())
        {
            popupInternet();
            return;

        }

        rl_progress.setVisibility(View.VISIBLE);
        menu_group_list.clear();

        ConnectValue connectValue = new ConnectValue();
//        connectValue.addConnectMap("business_license_no", business_license_no);
        connectValue.addConnectMap("tid", tid);

        ApiPart apiPart = new ApiPart(this);
        apiPart.selectMenu1Info(connectValue, new ConnectPost(this) {
            @Override
            public void completeSync(String tag, StringBuffer result) {

                Log.e("Jonatha", " result :: " + result);
                rl_progress.setVisibility(View.GONE);

                try {

                    JSONArray jsonArray = new JSONArray(String.valueOf(result));
                    for(int i = 0 ; i < jsonArray.length() ; i++)
                    {
                        JSONObject rowJson = (JSONObject) jsonArray.get(i);

                        String depth1_grp_no = rowJson.getString("depth1_grp_no");
                        String depth1_grp_name = rowJson.getString("depth1_grp_name");
                        String depth1_grp_eng_name = rowJson.getString("depth1_grp_eng_name");
                        String menu_type = rowJson.getString("menu_type");

                        HashMap<String, String> hashMap = new HashMap<>();
                        hashMap.put("depth1_grp_no", depth1_grp_no);
                        hashMap.put("depth1_grp_name", depth1_grp_name);
                        hashMap.put("depth1_grp_eng_name", depth1_grp_eng_name);
                        hashMap.put("menu_type", menu_type);
                        menu_group_list.add(hashMap);

                    }

                    adapter_menu_group.addAll(menu_group_list);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if(menu_group_list.size() > 0)
                {
                    String menuGroup = menu_group_list.get(0).get("depth1_grp_no").toString();
                    getMenuInfo(menuGroup);
                }



            }
        });

    }






    //메뉴 가져오기
    public void getMenuInfo(String menuGroup)
    {

        if(!isNetworkAvailable())
        {
            popupInternet();
            return;

        }

        rl_progress.setVisibility(View.VISIBLE);
        ConnectValue connectValue = new ConnectValue();
        connectValue.addConnectMap("depth1_grp_no", menuGroup);

        ApiPart apiPart = new ApiPart(this);
        apiPart.selectMenuOriginInfo(connectValue, new ConnectPost(this) {
            @Override
            public void completeSync(String tag, StringBuffer result) {

                Log.e("Jonatha", " result :: " + result);
                rl_progress.setVisibility(View.GONE);

                try {

                    JSONArray jsonArray = new JSONArray(String.valueOf(result));
                    for(int i = 0 ; i < jsonArray.length() ; i++)
                    {

                        JSONObject rowJson = (JSONObject) jsonArray.get(i);


                        String menu_name ="";
                        String menu_eng_name ="";
                        String menu_no = "";
                        String depth1_grp_no = "";
                        String pic_name = "";
                        String price = "";
                        String use_yn = "";
                        JSONArray jsonOrigin = new JSONArray();


                        if(rowJson.has("menu_name"))
                        {
                            menu_name = rowJson.getString("menu_name");
                        }

                        if(rowJson.has("menu_eng_name"))
                        {
                            menu_eng_name = rowJson.getString("menu_eng_name");
                        }

                        if(rowJson.has("menu_no"))
                        {
                            menu_no = rowJson.getString("menu_no");
                        }

                        if(rowJson.has("depth1_grp_no"))
                        {
                            depth1_grp_no = rowJson.getString("depth1_grp_no");
                        }

                        if(rowJson.has("pic_name"))
                        {
                            pic_name = rowJson.getString("pic_name");
                        }

                        if(rowJson.has("price"))
                        {
                            price = rowJson.getString("price");
                        }

                        if(rowJson.has("use_yn"))
                        {
                            use_yn = rowJson.getString("use_yn");
                        }

                        ArrayList<HashMap<String, String>> originArray = new ArrayList<HashMap<String, String>>();

                        if(rowJson.has("OriginList"))
                        {

                            jsonOrigin = new JSONArray(rowJson.getString("OriginList"));
                            for(int j = 0 ; j < jsonOrigin.length() ; j++)
                            {

                                JSONObject rowOrigin = (JSONObject) jsonOrigin.get(j);
                                HashMap<String, String> rowOriginHash = new HashMap<String, String>();

                                String material_no = rowOrigin.getString("material_no");
                                String orgin_menu_no = rowOrigin.getString("menu_no");
                                String material_name = rowOrigin.getString("material_name");
                                String origin = rowOrigin.getString("origin");
                                String weight = rowOrigin.getString("weight");

                                rowOriginHash.put("material_no", material_no);
                                rowOriginHash.put("menu_no", orgin_menu_no);
                                rowOriginHash.put("material_name", material_name);
                                rowOriginHash.put("origin", origin);
                                rowOriginHash.put("weight", weight);
                                originArray.add(rowOriginHash);


                                Log.e("Jonathan", " rowOrigin :: " + rowOrigin.toString());
                            }
                        }
                        arryMenuOrigin.add(originArray);



                        HashMap<String, String> hashMap = new HashMap<>();
                        hashMap.put("menu_name", menu_name);
                        hashMap.put("menu_eng_name", menu_eng_name);
                        hashMap.put("depth1_grp_no", depth1_grp_no);
                        hashMap.put("menu_no", menu_no);
                        hashMap.put("pic_name", pic_name);
                        hashMap.put("price", price);
                        hashMap.put("use_yn", use_yn);

                        arryMenu.add(hashMap);


                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }



                makeButton1();
//                JSONObject jsonObject = validResultData(tag, result);

//                arryMenuAll.add(hashMap);
//                mAdapter.addAll(arryMenuAll);


            }
        });

    }





    public void setOrderInfo(final String orderType, final HashMap<String, String> getHash)
    {


        if(!isNetworkAvailable())
        {
            popupInternet();
            return;
        }

        rl_progress.setVisibility(View.VISIBLE);

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String currentDateandTime = sdf.format(new Date());

        ArrayList<HashMap<String, String>> order_simple_list = new ArrayList<>();
        int totalPrice = 0;
        for(int i = 0 ; i < order_list.size() ; i++)
        {
            String stPrice = order_list.get(i).get("price").toString();
            String stValue = order_list.get(i).get("value").toString();
            int intPrice = Integer.valueOf(stPrice);
            int intValue = Integer.valueOf(stValue);
            totalPrice += (intPrice*intValue);

            HashMap<String, String> simpleMap = new HashMap<>();
            simpleMap.put("menu_no", order_list.get(i).get("menu_no").toString());
            simpleMap.put("cnt", order_list.get(i).get("value").toString());
            order_simple_list.add(simpleMap);
        }

        TelephonyManager tManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        String uuid = tManager.getDeviceId();

        String android_id = Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.ANDROID_ID);
        Log.e("Jonathan", "android_id :: " + android_id);
        if("".equals(uuid) || "null".equals(uuid) || uuid == null)
        {
            uuid = android_id;
        }


        ConnectValue connectValue = new ConnectValue();
//        connectValue.addConnectMap("disp_order_no", "");
        connectValue.addConnectMap("user_id", tid);
//        connectValue.addConnectMap("business_license_no", business_license_no);
        connectValue.addConnectMap("tid", tid);
//        connectValue.addConnectMap("discount_cpn_no", "");
        connectValue.addConnectMap("uuid", uuid);
        connectValue.addConnectMap("total_price", ""+totalPrice);
//        connectValue.addConnectMap("discount_price", "");
        connectValue.addConnectMap("total_pay_price", ""+totalPrice);
        connectValue.addConnectMap("order_status", "OC");
        connectValue.addConnectMap("order_time", currentDateandTime);
//        connectValue.addConnectMap("cooking_start_time", "");
//        connectValue.addConnectMap("cooking_end_time", "");
        connectValue.addConnectMap("order_cancel_yn", "N");
        connectValue.addConnectMap("reg_by", tid);
        connectValue.addConnectMap("pay_type", orderType);
        connectValue.addConnectList("orderDetailList", order_simple_list);



        ApiPart apiPart = new ApiPart(this);
        final int finalTotalPrice = totalPrice;
        apiPart.regiOrder(connectValue, new ConnectPost(this) {
            @Override
            public void completeSync(String tag, StringBuffer result) {

                Log.e("Jonatha", "push result :: " + result);

                rl_progress.setVisibility(View.GONE);

                JSONObject jsonObject = validResultData(tag, result);
                try {

                    if(jsonObject.has("responseStatus"))
                    {
                        String responseStatus = jsonObject.getString("responseStatus");
                        if(!"FAIL".equals(responseStatus))
                        {
                            String responseMessage  = jsonObject.getString("responseMessage");
                            String order_no = responseMessage.replace("order_no=", "");
                            Log.e("Jonathan","order_no :: " + order_no);
//                            sendPush(orderType, order_no);
                            setPayInfo(orderType, order_no, String.valueOf(finalTotalPrice), getHash);

                        }
                    }



                } catch (JSONException e) {


                    if("CARD".equals(orderType))
                    {
                        setOrderInfo("CARD", getHash);
                    }
                    else if("CASH".equals(orderType))
                    {
                        setOrderInfo("CASH", new HashMap<String, String>());
                    }

                    e.printStackTrace();
                }



            }
        });

    }





    public void setPayInfo(final String orderType, final String orderNum, final String finalTotalPrice, final HashMap<String, String> getHash)
    {

        if(!isNetworkAvailable())
        {
            popupInternet();
            return;

        }


        rl_progress.setVisibility(View.VISIBLE);
        ConnectValue connectValue = new ConnectValue();
        connectValue.addConnectMap("order_no", orderNum);
        connectValue.addConnectMap("user_id", tid);
//        connectValue.addConnectMap("user_hp", "");
//        connectValue.addConnectMap("business_license_no", business_license_no);
        connectValue.addConnectMap("tid", tid);
        connectValue.addConnectMap("total_pay_price", finalTotalPrice);
        connectValue.addConnectMap("status", "S");
        connectValue.addConnectMap("pay_method", orderType);




        ApiPart apiPart = new ApiPart(this);
        apiPart.regiPay(connectValue, new ConnectPost(this) {
            @Override
            public void completeSync(String tag, StringBuffer result) {

                Log.e("Jonatha", "push result :: " + result);
                rl_progress.setVisibility(View.GONE);
                JSONObject jsonObject = validResultData(tag, result);
                try {

                    if(jsonObject.has("responseStatus"))
                    {
                        String responseStatus = jsonObject.getString("responseStatus");
                        if(!"FAIL".equals(responseStatus))
                        {
                            sendPush(orderType, orderNum);
                            if(!getHash.isEmpty())
                            {
                                saveCardPayInfo(getHash, orderNum);
                            }

                        }
                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }
        });


    }





    public void sendPush(final String orderType, String order_num)
    {

        if(!isNetworkAvailable())
        {
            popupInternet();
            return;

        }

        rl_progress.setVisibility(View.VISIBLE);

        final String mOrder_no = order_num;

        double totalPrice = 0;
        String menu_list = "";
        for(int i = 0 ;i < order_list.size() ; i++)
        {
            String price = order_list.get(i).get("price").toString();
            String value = order_list.get(i).get("value").toString();

            double intPrice = (Double.valueOf(price) * Integer.valueOf(value));
            totalPrice = totalPrice + intPrice;


            menu_list += order_list.get(i).get("menu_name").toString() + " (" + value + "개)" ;
            if(i != order_list.size()-1)
            {
                menu_list += "\n ";
            }
        }

        String StringTotalPrice = Define.formatToMoney(String.valueOf(totalPrice));

        menu_list += "\n\n" + StringTotalPrice ;

        Log.e("Jonathan", " order_list :: " + order_list);


        String type = "";
        if("CASH".equals(orderType))
        {
            type = "(현금)";
        }
        else
        {
            type = "(카드)";
        }


        ConnectValue connectValue = new ConnectValue();
//        connectValue.addConnectMap("business_license_no", business_license_no);
        connectValue.addConnectMap("tid", tid);
        connectValue.addConnectMap("title", "주문이 등록되었습니다." + " " + type);
        connectValue.addConnectMap("type", "CMP");
        connectValue.addConnectMap("msg", menu_list);

        ApiPart apiPart = new ApiPart(this);
        apiPart.sendPush(connectValue, new ConnectPost(this) {
            @Override
            public void completeSync(String tag, StringBuffer result) {

//                rl_progress.setVisibility(View.GONE);
//                Log.e("Jonatha", "push result :: " + result);
//
//                getOrderData(mOrder_no, orderType);

            }
        });


        rl_progress.setVisibility(View.GONE);
        getOrderData(mOrder_no, orderType);

    }




    public void sendCanclePush()
    {

        if(!isNetworkAvailable())
        {
            popupInternet();
            return;

        }

        rl_progress.setVisibility(View.VISIBLE);

        ConnectValue connectValue = new ConnectValue();
        connectValue.addConnectMap("tid", tid);
        connectValue.addConnectMap("title", "취소");
        connectValue.addConnectMap("type", "CMP");
        connectValue.addConnectMap("msg", "주문이 취소되었습니다.");

        ApiPart apiPart = new ApiPart(this);
        apiPart.sendPush(connectValue, new ConnectPost(this) {
            @Override
            public void completeSync(String tag, StringBuffer result) {

                rl_progress.setVisibility(View.GONE);
                Log.e("Jonatha", "push result :: " + result);


            }
        });
    }

    public void getOrderData(final String order_no, final  String orderType)
    {

        rl_progress.setVisibility(View.VISIBLE);

        ConnectValue connectValue = new ConnectValue();
        connectValue.addConnectMap("tid", tid);
        connectValue.addConnectMap("order_no", order_no);
        Log.e("Jonathan", "여기가 느린거지??1");

        ApiPart apiPart = new ApiPart(this);
        apiPart.selectOrder(connectValue, new ConnectPost(this) {
            @Override
            public void completeSync(String tag, StringBuffer result) {

                Log.e("Jonathan", "여기가 느린거지??2");

                rl_progress.setVisibility(View.GONE);
                Log.e("Jonatha", "getOrderData result :: " + result);
                ArrayList<HashMap<String, String>> getRegiData = new ArrayList<>();


                try {
                    JSONArray jsonArray = new JSONArray(String.valueOf(result));

                    for(int i = 0 ; i < jsonArray.length() ; i++)
                    {
//                        Log.e("jonathan", "jsonArray : " + jsonArray.get(i));
                        JSONObject rowJson = (JSONObject) jsonArray.get(i);
                        HashMap<String, String> getDataMap = new HashMap<String, String>();

                        Iterator<String> key = rowJson.keys();
                        while (key.hasNext())
                        {
                            String DataKey = key.next();
                            getDataMap.put(DataKey, rowJson.get(DataKey).toString());
//                            Log.e("jonathan", "DataKey : " + DataKey + " val :: " + rowJson.get(DataKey).toString());

                        }
                        getRegiData.add(getDataMap);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                if(mBluetoothAdapter != null)
                {
                    if(mBluetoothAdapter.isEnabled())
                    {
                        mService.stop();
                    }
                }
                Intent mainIntent = new Intent(ActivityMain.this, ActivityPhone.class);
                mainIntent.putExtra("total_value", tv_total_value.getText().toString());
                mainIntent.putExtra("total_price", tv_total_price.getText().toString());
                mainIntent.putExtra("order_num", order_no);
                mainIntent.putExtra("disp_order_no", getRegiData.get(0).get("disp_order_no"));
                mainIntent.putExtra("tid", tid);
                mainIntent.putExtra("order_list", order_list);
                mainIntent.putExtra("order_type", orderType);




                ActivityMain.this.startActivity(mainIntent);


                bt_clear_order.performClick();
//                sl_ad.setVisibility(View.VISIBLE);
                playVideo();



            }
        });

    }


    public void startNewActivity(Context context, String packageName) {
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
        if (intent != null) {
            // We found the activity now start the activity
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } else {
            // Bring user to the market or let them choose an app?
            intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setData(Uri.parse("market://details?id=" + packageName));
            context.startActivity(intent);
        }
    }




    int mCurrentPosition = 0;

    @Override
    public void onItemClick(int position) {

        mCurrentPosition = position;
        arryMenu.clear();
        arryMenuOrigin.clear();
        String menuGroup = menu_group_list.get(position).get("depth1_grp_no").toString();
        getMenuInfo(menuGroup);
//        makeButton1();

    }

    @Override
    protected void onResume() {
        super.onResume();







        if(receiptPopup != null)
        {
            if(!receiptPopup.isShowing())
            {
                Define.mp1.stop();
                Define.mp1 = MediaPlayer.create(this, R.raw.selectmenu);
                Define.mp1.start();
            }
        }


        sl_ad.setVisibility(View.VISIBLE);
//        pauseMediaVideo();
//        playMediaVideo();

    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {


            case R.id.iv_test:

//                goTransaction(v, "");

                break;


            case R.id.bt_card:




                if(order_list.size() == 0)
                {
                    NoticeBtnOnePopup orderNotData = new NoticeBtnOnePopup(this, "주문을 먼저 해주세요", new OnEventOkListener() {
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
                    orderNotData.setCanceledOnTouchOutside(false);
                    orderNotData.show();

                    return;
                }
                goTransaction(bt_card, null);




                break;



            case R.id.bt_cash:



                if(order_list.size() == 0)
                {

                    Define.mp1.stop();
                    Define.mp1 = MediaPlayer.create(this, R.raw.selectmenu);
                    Define.mp1.start();


                    NoticeBtnOnePopup orderNotData = new NoticeBtnOnePopup(this, "주문을 먼저 해주세요", new OnEventOkListener() {
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
                    orderNotData.setCanceledOnTouchOutside(false);
                    orderNotData.show();

                    return;
                }
                else
                {

                    Define.mp1.stop();
                    Define.mp1 = MediaPlayer.create(this, R.raw.cashtocook);
                    Define.mp1.start();

                    final CashPopup popup = new CashPopup(this, tv_total_price.getText().toString(), new OnEventOkListener() {
                        @Override
                        public void onOk() {
                            Log.e("Jonathan", "cashPopup1");
                            setOrderInfo("CASH", new HashMap<String, String>());

                        }

                        @Override
                        public void onOk(String addMenu) {
                            Log.e("Jonathan", "cashPopup2");
                            setOrderInfo("CASH", new HashMap<String, String>());
                        }

                        @Override
                        public void onOk(HashMap<String, String> addOrigin) {
                            Log.e("Jonathan", "cashPopup3");
                            setOrderInfo("CASH", new HashMap<String, String>());
                        }
                    });

                    popup.setCanceledOnTouchOutside(false);
                    popup.show();



                    final Handler handler  = new Handler();
                    final Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            if (popup.isShowing()) {
                                popup.dismiss();
                                setOrderInfo("CASH", new HashMap<String, String>());
                            }
                        }
                    };

                    popup.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            handler.removeCallbacks(runnable);
                        }
                    });

                    handler.postDelayed(runnable, 5000);



                }




                break;




            case R.id.bt_clear_order:


                Define.mp1.stop();
                Define.mp1 = MediaPlayer.create(this, R.raw.selectmenu);
                Define.mp1.start();


                order_list.clear();
                adapter_order.addAll(order_list);
                totalSum();
                remake();


                break;



            case R.id.iv_cash_cancel:


                getCashInfo();



                break;


            case R.id.iv_receit_list:


                if(mBluetoothAdapter.isEnabled())
                {
                    mService.stop();
                }

                SharedPreferences pref = getSharedPreferences("GOFOODA", MODE_PRIVATE);
                String address = pref.getString("address", "");
                if(!"".equals(address))
                {
//                    con_dev = mService.getDevByMac(address);
//                    mService.connect(con_dev);

                    BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
                    mService.connect(device);

                    getCardPayInfo(iv_receit_list);
                }
                else
                {

                    String use_print = pref.getString("use_print", "");

                    if("Y".equals(use_print))
                    {
                        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                        if (mBluetoothAdapter == null) {
                            // Device does not support Bluetooth
                            Toast.makeText(mContext,"블루투스 기능을 지원하지 않습니다.", Toast.LENGTH_SHORT).show();

                        } else {
                            if (!mBluetoothAdapter.isEnabled()) {
                                // Bluetooth is not enable :)
                                Toast.makeText(mContext,"블루투스 기능을 켜주세요.", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Intent serverIntent = new Intent(mContext, DeviceListActivity.class);
                                startActivityForResult(serverIntent, Define.REQUEST_CONNECT_DEVICE);
                            }
                        }



                    }
                    else
                    {
                        Toast.makeText(mContext,"로그인 화면에서 프린터 사용유무를 체크하지 않았습니다.", Toast.LENGTH_SHORT).show();
                    }

//                    Intent serverIntent = new Intent(mContext, DeviceListActivity.class);
//                    startActivityForResult(serverIntent,REQUEST_CONNECT_DEVICE);
                }



                break;



            case R.id.iv_order_cancel:


                passwordDialog = new PasswordDialog(this, new OnPasswordOkListener() {

                    @Override
                    public void onOk(String password) {

                        if(password.equals(business_license_no))
                        {
                            Define.mp1.stop();
                            Define.mp1 = MediaPlayer.create(mContext, R.raw.canclelist);
                            Define.mp1.start();

                            getCardPayInfo(iv_order_cancel);
                        }
                        else
                        {
                            showPassWrong();
                            Toast.makeText(mContext, "비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT).show();
                        }


                    }

                });
                passwordDialog.setCanceledOnTouchOutside(false);
                passwordDialog.show();






                break;


        }



    }



    public void showPassWrong()
    {
        SimpleDialog simpleDialog = new SimpleDialog(this, new OnPasswordOkListener() {

            @Override
            public void onOk(String password) {



            }

        });
        simpleDialog.setCanceledOnTouchOutside(false);
        simpleDialog.show();



    }






    public void saveCardPayInfo(HashMap<String, String> getHash, String order_no)
    {

        if(!isNetworkAvailable())
        {
            popupInternet();
            return;

        }

        rl_progress.setVisibility(View.VISIBLE);

        ConnectValue connectValue = new ConnectValue();

        connectValue.addConnectMap("tid", tid);
//        connectValue.addConnectMap("business_license_no", business_license_no);
        connectValue.addConnectMap("order_no", order_no);
        connectValue.addConnectMap("shopTelNo", Define.nullCheck(getHash.get("shopTelNo")));
        connectValue.addConnectMap("resultval", Define.nullCheck(getHash.get("resultval")));
        connectValue.addConnectMap("amount", Define.nullCheck(getHash.get("amount")));
        connectValue.addConnectMap("cardno", Define.nullCheck(getHash.get("cardno")));
        connectValue.addConnectMap("surtax", Define.nullCheck(getHash.get("surtax")));
        connectValue.addConnectMap("tranno", Define.nullCheck(getHash.get("tranno")));
        connectValue.addConnectMap("printmessage", Define.nullCheck(getHash.get("printmessage")));
        connectValue.addConnectMap("shopName", Define.nullCheck(getHash.get("shopName")));
        connectValue.addConnectMap("totalamount", Define.nullCheck(getHash.get("totalamount")));
        connectValue.addConnectMap("merchantno", Define.nullCheck(getHash.get("merchantno")));
        connectValue.addConnectMap("serverres", Define.nullCheck(getHash.get("serverres")));
        connectValue.addConnectMap("outmessage", Define.nullCheck(getHash.get("outmessage")));
        connectValue.addConnectMap("shopAddress", Define.nullCheck(getHash.get("shopAddress")));
        connectValue.addConnectMap("mode", Define.nullCheck(getHash.get("mode")));
        connectValue.addConnectMap("catid", Define.nullCheck(getHash.get("catid")));
        connectValue.addConnectMap("approvaldate", Define.nullCheck(getHash.get("approvaldate")));
        connectValue.addConnectMap("shopOwnerName", Define.nullCheck(getHash.get("shopOwnerName")));
        connectValue.addConnectMap("approvalno", Define.nullCheck(getHash.get("approvalno")));
        connectValue.addConnectMap("acquiercode", Define.nullCheck(getHash.get("acquiercode")));
        connectValue.addConnectMap("acquiername", Define.nullCheck(getHash.get("acquiername")));
        connectValue.addConnectMap("tranuniqe", Define.nullCheck(getHash.get("tranuniqe")));
        connectValue.addConnectMap("receipt", Define.nullCheck(getHash.get("receipt")));
        connectValue.addConnectMap("trantype", Define.nullCheck(getHash.get("trantype")));
        connectValue.addConnectMap("issuercode", Define.nullCheck(getHash.get("issuercode")));
        connectValue.addConnectMap("issuername", Define.nullCheck(getHash.get("issuername")));
        connectValue.addConnectMap("dongleInfo", Define.nullCheck(getHash.get("dongleInfo")));
        connectValue.addConnectMap("dongletype", Define.nullCheck(getHash.get("dongletype")));
        connectValue.addConnectMap("businessno", Define.nullCheck(getHash.get("businessno")));
        connectValue.addConnectMap("receiptmode", Define.nullCheck(getHash.get("receiptmode")));
        connectValue.addConnectMap("installment", Define.nullCheck(getHash.get("installment")));






        ApiPart apiPart = new ApiPart(this);
        apiPart.setCardPay(connectValue, new ConnectPost(this) {
            @Override
            public void completeSync(String tag, StringBuffer result) {

                rl_progress.setVisibility(View.GONE);
                Log.e("Jonatha", "1setCardPay result :: " + result);
//                JSONObject jsonObject = validResultData(tag, result);
                try {
                    JSONObject jsonObject = new JSONObject(result.toString());

                    if(jsonObject.has("responseStatus"))
                    {
                        String responseStatus = jsonObject.getString("responseStatus");
                        if(!"FAIL".equals(responseStatus))
                        {
//                            setOrderInfo("CARD");
                        }
                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }
        });


    }



    String cancleOrderNo = "";
    public void updateCardInfo(final HashMap<String, String> newHash)
    {
        if(!isNetworkAvailable())
        {
            popupInternet();
            return;

        }

        rl_progress.setVisibility(View.VISIBLE);
        ConnectValue connectValue = new ConnectValue();

        connectValue.addConnectMap("tid", tid);
//        connectValue.addConnectMap("business_license_no", business_license_no);
        connectValue.addConnectMap("order_no", cancleOrderNo);
        connectValue.addConnectMap("shopTelNo", Define.nullCheck(newHash.get("shopTelNo")));
        connectValue.addConnectMap("resultval", Define.nullCheck(newHash.get("resultval")));
        connectValue.addConnectMap("amount", Define.nullCheck(newHash.get("amount")));
        connectValue.addConnectMap("cardno", Define.nullCheck(newHash.get("cardno")));
        connectValue.addConnectMap("surtax", Define.nullCheck(newHash.get("surtax")));
        connectValue.addConnectMap("tranno", Define.nullCheck(newHash.get("tranno")));
        connectValue.addConnectMap("printmessage", Define.nullCheck(newHash.get("printmessage")));
        connectValue.addConnectMap("shopName", Define.nullCheck(newHash.get("shopName")));
        connectValue.addConnectMap("totalamount", Define.nullCheck(newHash.get("totalamount")));
        connectValue.addConnectMap("merchantno", Define.nullCheck(newHash.get("merchantno")));
        connectValue.addConnectMap("serverres", Define.nullCheck(newHash.get("serverres")));
        connectValue.addConnectMap("outmessage", Define.nullCheck(newHash.get("outmessage")));
        connectValue.addConnectMap("shopAddress", Define.nullCheck(newHash.get("shopAddress")));
        connectValue.addConnectMap("mode", Define.nullCheck(newHash.get("mode")));
        connectValue.addConnectMap("catid", Define.nullCheck(newHash.get("catid")));
        connectValue.addConnectMap("approvaldate", Define.nullCheck(newHash.get("approvaldate")));
        connectValue.addConnectMap("shopOwnerName", Define.nullCheck(newHash.get("shopOwnerName")));
        connectValue.addConnectMap("approvalno", Define.nullCheck(newHash.get("approvalno")));
        connectValue.addConnectMap("acquiercode", Define.nullCheck(newHash.get("acquiercode")));
        connectValue.addConnectMap("acquiername", Define.nullCheck(newHash.get("acquiername")));
        connectValue.addConnectMap("tranuniqe", Define.nullCheck(newHash.get("tranuniqe")));
        connectValue.addConnectMap("receipt", Define.nullCheck(newHash.get("receipt")));
        connectValue.addConnectMap("trantype", Define.nullCheck(newHash.get("trantype")));
        connectValue.addConnectMap("issuercode", Define.nullCheck(newHash.get("issuercode")));
        connectValue.addConnectMap("issuername", Define.nullCheck(newHash.get("issuername")));
        connectValue.addConnectMap("dongleInfo", Define.nullCheck(newHash.get("dongleInfo")));
        connectValue.addConnectMap("dongletype", Define.nullCheck(newHash.get("dongletype")));
        connectValue.addConnectMap("businessno", Define.nullCheck(newHash.get("businessno")));
        connectValue.addConnectMap("receiptmode", Define.nullCheck(newHash.get("receiptmode")));
        connectValue.addConnectMap("installment", Define.nullCheck(newHash.get("installment")));




        ApiPart apiPart = new ApiPart(this);
        apiPart.updateCardPay(connectValue, new ConnectPost(this) {
            @Override
            public void completeSync(String tag, StringBuffer result) {

                rl_progress.setVisibility(View.GONE);
                Log.e("Jonatha", "updateCardPay result :: " + result);

                JSONObject jsonObject = validResultData(tag, result);
                try {

                    if(jsonObject.has("responseStatus"))
                    {
                        String responseStatus = jsonObject.getString("responseStatus");
                        if(!"FAIL".equals(responseStatus))
                        {
                            updateOrder(newHash);
//                            selectPay(cancleOrderNo);
                        }
                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }
        });


    }




    //카드 취소시 -> 주문 취소
    public void updateOrder(HashMap<String, String> newHash)
    {
        ConnectValue connectValue = new ConnectValue();
        connectValue.addConnectMap("tid", tid);
        connectValue.addConnectMap("order_no", cancleOrderNo);
        connectValue.addConnectMap("order_status", "CC");



        ApiPart apiPart = new ApiPart(this);
        apiPart.updateOrder(connectValue, new ConnectPost(this) {
            @Override
            public void completeSync(String tag, StringBuffer result) {

                progressBar.setVisibility(View.GONE);
                selectPay(cancleOrderNo);

            }


        });
    }



    private Date yesterday() {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return cal.getTime();
    }




    public void selectPay(String order_no)
    {

        if(!isNetworkAvailable())
        {
            popupInternet();
            return;

        }

        rl_progress.setVisibility(View.VISIBLE);

        final ArrayList<HashMap<String, String>> getPayCancleData = new ArrayList<>();


        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd");
        String formattedDate = df.format(c);
        String formattedDate_1 = df.format(yesterday());


        ConnectValue connectValue = new ConnectValue();
        connectValue.addConnectMap("tid", tid);
        connectValue.addConnectMap("order_no", order_no);
        connectValue.addConnectMap("start_search_date", formattedDate_1);
        connectValue.addConnectMap("end_search_date", formattedDate);

        ApiPart apiPart = new ApiPart(this);
        apiPart.selectPay(connectValue, new ConnectPost(this) {
            @Override
            public void completeSync(String tag, StringBuffer result) {

                rl_progress.setVisibility(View.GONE);
                Log.e("Jonatha", "2setCardPay result :: " + result);

//                JSONObject jsonObject = validResultData(tag, result);
                try {

                    JSONArray jsonArray = new JSONArray(String.valueOf(result));
                    for(int i = 0 ; i < jsonArray.length() ; i++)
                    {
//                        Log.e("jonathan", "jsonArray : " + jsonArray.get(i));
                        JSONObject rowJson = (JSONObject) jsonArray.get(i);
                        HashMap<String, String> getDataMap = new HashMap<String, String>();

                        Iterator<String> key = rowJson.keys();
                        while (key.hasNext())
                        {
                            String DataKey = key.next();
                            getDataMap.put(DataKey, rowJson.get(DataKey).toString());
//                            Log.e("jonathan", "DataKey : " + DataKey + " val :: " + rowJson.get(DataKey).toString());

                        }
                        getPayCancleData.add(getDataMap);
                    }



                    Collections.sort(getPayCancleData, new Comparator<HashMap<String, String>>() {
                        @Override
                        public int compare(HashMap<String, String> rhs, HashMap<String, String> lhs) {
                            return lhs.get("reg_date").compareTo(rhs.get("reg_date"));
                        }
                    });



                    updatePayCard(getPayCancleData);



                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }
        });

    }

//'929','1079518002','1079518002','40',NULL,'200','S','CARD','2018-03-21 23:06:06',NULL,NULL,NULL



    public void updatePayCard(ArrayList<HashMap<String, String>> getPayCancleData)
    {

        if(!isNetworkAvailable())
        {
            popupInternet();
            return;

        }

        rl_progress.setVisibility(View.VISIBLE);

        ConnectValue connectValue = new ConnectValue();
        connectValue.addConnectMap("pay_no", getPayCancleData.get(0).get("pay_no"));
        connectValue.addConnectMap("order_no", getPayCancleData.get(0).get("order_no"));
        connectValue.addConnectMap("tid", tid);
        connectValue.addConnectMap("status", "C");


        ApiPart apiPart = new ApiPart(this);
        apiPart.updatePay(connectValue, new ConnectPost(this) {
            @Override
            public void completeSync(String tag, StringBuffer result) {

                rl_progress.setVisibility(View.GONE);
                Log.e("Jonatha", "3setCardPay result :: " + result);
                JSONObject jsonObject = validResultData(tag, result);
                try {

                    if(jsonObject.has("responseStatus"))
                    {
                        String responseStatus = jsonObject.getString("responseStatus");
                        if(!"FAIL".equals(responseStatus))
                        {
                            sendCanclePush();
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }
        });
    }



    public void getCashInfo()
    {

        if(!isNetworkAvailable())
        {
            popupInternet();
            return;

        }


        final ArrayList<HashMap<String, String>> getCashData = new ArrayList<>();

        rl_progress.setVisibility(View.VISIBLE);

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd");
        String formattedDate = df.format(c);
        String formattedDate_1 = df.format(yesterday());


        ConnectValue connectValue = new ConnectValue();
        connectValue.addConnectMap("tid", tid);
//        connectValue.addConnectMap("order_no", order_no);
        connectValue.addConnectMap("start_search_date", formattedDate);
        connectValue.addConnectMap("end_search_date", formattedDate);

        ApiPart apiPart = new ApiPart(this);
        apiPart.selectPay(connectValue, new ConnectPost(this) {
            @Override
            public void completeSync(String tag, StringBuffer result) {

                rl_progress.setVisibility(View.GONE);
                Log.e("Jonatha", "2setCardPay result :: " + result);

//                JSONObject jsonObject = validResultData(tag, result);
                try {

                    JSONArray jsonArray = new JSONArray(String.valueOf(result));
                    for(int i = 0 ; i < jsonArray.length() ; i++)
                    {
//                        Log.e("jonathan", "jsonArray : " + jsonArray.get(i));
                        JSONObject rowJson = (JSONObject) jsonArray.get(i);
                        HashMap<String, String> getDataMap = new HashMap<String, String>();

                        Iterator<String> key = rowJson.keys();
                        while (key.hasNext())
                        {
                            String DataKey = key.next();
                            getDataMap.put(DataKey, rowJson.get(DataKey).toString());
//                            Log.e("jonathan", "DataKey : " + DataKey + " val :: " + rowJson.get(DataKey).toString());

                        }
                        getCashData.add(getDataMap);
                    }



                    Collections.sort(getCashData, new Comparator<HashMap<String, String>>() {
                        @Override
                        public int compare(HashMap<String, String> rhs, HashMap<String, String> lhs) {
                            return lhs.get("reg_date").compareTo(rhs.get("reg_date"));
                        }
                    });


                    popupCashPayList(getCashData);



                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }
        });


    }





    public void popupCashPayList(final ArrayList<HashMap<String, String>> cashList)
    {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(ActivityMain.this);
        builderSingle.setTitle("현금거래 내역");




        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ActivityMain.this, android.R.layout.select_dialog_singlechoice);
        for(int i = 0 ; i < cashList.size() ; i++)
        {
//            Log.e("Jonathan", "arryPayment :: " + cashList.get(i));
            String data = "\n";
            if(cashList.get(i).containsKey("shopName"))
            {
                data += "결제처 :: " + cashList.get(i).get("shopName").toString() + "\n";
            }
            if(cashList.get(i).containsKey("outmessage"))
            {
                data += cashList.get(i).get("outmessage").toString() + "\n\n";
            }
            if(cashList.get(i).containsKey("issuername"))
            {
                data += cashList.get(i).get("issuername").toString() + "\n";
            }
            if(cashList.get(i).containsKey("cardno"))
            {
                data += "카드번호 :: " + cashList.get(i).get("cardno").toString() + "\n";
            }
            if(cashList.get(i).containsKey("totalamount"))
            {
                data += "결제금액 :: " + cashList.get(i).get("totalamount").toString() + "원" + "\n";
            }



            arrayAdapter.add(data);
        }


        builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });



        builderSingle.setPositiveButton("프린터 설정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                SharedPreferences pref = getSharedPreferences("GOFOODA", MODE_PRIVATE);
                String use_print = pref.getString("use_print", "");

                if("Y".equals(use_print))
                {
                    BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                    if (mBluetoothAdapter == null) {
                        // Device does not support Bluetooth
                        Toast.makeText(mContext,"블루투스 기능을 지원하지 않습니다.", Toast.LENGTH_SHORT).show();

                    } else {
                        if (!mBluetoothAdapter.isEnabled()) {
                            // Bluetooth is not enable :)
                            Toast.makeText(mContext,"블루투스 기능을 켜주세요.", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Intent serverIntent = new Intent(mContext, DeviceListActivity.class);
                            startActivityForResult(serverIntent,Define.REQUEST_CONNECT_DEVICE);
                        }
                    }



                }
                else
                {
                    Toast.makeText(mContext,"로그인 화면에서 프린터 사용유무를 체크하지 않았습니다.", Toast.LENGTH_SHORT).show();
                }


//                Intent serverIntent = new Intent(mContext, DeviceListActivity.class);
//                startActivityForResult(serverIntent,REQUEST_CONNECT_DEVICE);

            }
        });




        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String strName = arrayAdapter.getItem(which);
                final HashMap<String, String> hashMap = cashList.get(which);
                AlertDialog.Builder builderInner = new AlertDialog.Builder(ActivityMain.this);
                builderInner.setMessage(strName);
                builderInner.setTitle("아래 내역의 현금 영수증을 취소하시겠습니까?");
                builderInner.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,int which) {
//                        goTransaction(iv_order_cancel, hashMap );
                        dialog.dismiss();
                    }
                });
                builderInner.show();
            }
        });
//                builderSingle.show();



        AlertDialog dialog = builderSingle.create();

        // Get the alert dialog ListView instance
        ListView listView = dialog.getListView();

        // Set the divider color of alert dialog list view
        listView.setDivider(new ColorDrawable(Color.BLACK));

        // Set the divider height of alert dialog list view
        listView.setDividerHeight(2);

        // Finally, display the alert dialog
        dialog.show();
    }










    public void getCardPayInfo(final Button bt_card)
    {

        if(!isNetworkAvailable())
        {
            popupInternet();
            return;

        }


        final ArrayList<HashMap<String, String>> cancleList = new ArrayList<>();

        rl_progress.setVisibility(View.VISIBLE);

        ConnectValue connectValue = new ConnectValue();
        connectValue.addConnectMap("tid", tid);
//        connectValue.addConnectMap("business_license_no", business_license_no);


        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        String formattedDate = df.format(c);

        connectValue.addConnectMap("start_search_date", formattedDate);
        connectValue.addConnectMap("end_search_date", formattedDate);





        ApiPart apiPart = new ApiPart(this);
        apiPart.getCardPay(connectValue, new ConnectPost(this) {
            @Override
            public void completeSync(String tag, StringBuffer result) {

                rl_progress.setVisibility(View.GONE);
                Log.e("Jonatha", "4setCardPay result :: " + result);

//                JSONObject jsonObject = validResultData(tag, result);
                try {

                    JSONArray jsonArray = new JSONArray(String.valueOf(result));

                    Date c = Calendar.getInstance().getTime();
                    SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
                    String formattedDate = df.format(c);


                    for(int i = 0 ; i < jsonArray.length() ; i++)
                    {
//                        Log.e("jonathan", "jsonArray : " + jsonArray.get(i));
                        JSONObject rowJson = (JSONObject) jsonArray.get(i);
                        HashMap<String, String> getDataMap = new HashMap<String, String>();

                        Iterator<String> key = rowJson.keys();
                        while (key.hasNext())
                        {
                            String DataKey = key.next();
                            getDataMap.put(DataKey, rowJson.get(DataKey).toString());
//                            Log.e("jonathan", "DataKey : " + DataKey + " val :: " + rowJson.get(DataKey).toString());

                        }

//                        Log.e("Jonathan", "approvaldate :: " + getDataMap.get("approvaldate").substring(0,8) + " formattedDate :: " + formattedDate);
                        if(getDataMap.get("trantype").equals("card") && getDataMap.get("approvaldate").substring(0,8).equals(formattedDate))
                        {
                            cancleList.add(getDataMap);
                        }

                    }


                    CountTask count = new CountTask(cancleList, bt_card);
                    count.execute();
//                    popupCardPayList(cancleList);

                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }
        });


    }








    class CountTask extends AsyncTask<Integer, Integer, Void>{



        ArrayList<HashMap<String, String>> cancleList;
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(ActivityMain.this);
        Button bt_card;

        public CountTask(ArrayList<HashMap<String, String>> cancleList, Button bt_card)
        {
            rl_progress.setVisibility(View.VISIBLE);
            this.cancleList = cancleList;
            this.bt_card = bt_card;
        }


        @Override
        protected Void doInBackground(Integer... params) {







            Collections.sort(cancleList, new Comparator<HashMap<String, String>>() {
                @Override
                public int compare(HashMap<String, String> rhs, HashMap<String, String> lhs) {
                    return lhs.get("approvaldate").compareTo(rhs.get("approvaldate"));
                }
            });




            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ActivityMain.this, android.R.layout.select_dialog_singlechoice);
            for(int i = 0 ; i < cancleList.size() ; i++)
            {
//                Log.e("Jonathan", "arryPayment :: " + cancleList.get(i));
                String data = "\n";

                if(cancleList.get(i).containsKey("disp_order_no"))
                {
                    data += "주문번호 :: " + cancleList.get(i).get("disp_order_no").toString() + "\n";
                }

                if(cancleList.get(i).containsKey("approvaldate"))
                {
                    SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String dateTime = "";
                    try {
                        Date date = format.parse(cancleList.get(i).get("approvaldate").toString());
                        dateTime = dateFormat.format(date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    data += "결제시간 :: " + dateTime + "\n";
                }
                if(cancleList.get(i).containsKey("shopName"))
                {
                    data += "결제처 :: " + cancleList.get(i).get("shopName").toString() + "\n";
                }
                if(cancleList.get(i).containsKey("outmessage"))
                {
                    data += cancleList.get(i).get("outmessage").toString() + "\n\n";
                }
                if(cancleList.get(i).containsKey("issuername"))
                {
                    data += cancleList.get(i).get("issuername").toString() + "\n";
                }
                if(cancleList.get(i).containsKey("cardno"))
                {
                    data += "카드번호 :: " + cancleList.get(i).get("cardno").toString() + "\n";
                }
                if(cancleList.get(i).containsKey("totalamount"))
                {
                    data += "결제금액 :: " + cancleList.get(i).get("totalamount").toString() + "원" + "\n";
                }



                arrayAdapter.add(data);
            }


            builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });


            builderSingle.setPositiveButton("프린터 설정", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    SharedPreferences pref = getSharedPreferences("GOFOODA", MODE_PRIVATE);
                    String use_print = pref.getString("use_print", "");

                    if("Y".equals(use_print))
                    {
                        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                        if (mBluetoothAdapter == null) {
                            // Device does not support Bluetooth
                            Toast.makeText(mContext,"블루투스 기능을 지원하지 않습니다.", Toast.LENGTH_SHORT).show();

                        } else {
                            if (!mBluetoothAdapter.isEnabled()) {
                                // Bluetooth is not enable :)
                                Toast.makeText(mContext,"블루투스 기능을 켜주세요.", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Intent serverIntent = new Intent(mContext, DeviceListActivity.class);
                                startActivityForResult(serverIntent,Define.REQUEST_CONNECT_DEVICE);
                            }
                        }



                    }
                    else
                    {
                        Toast.makeText(mContext,"로그인 화면에서 프린터 사용유무를 체크하지 않았습니다.", Toast.LENGTH_SHORT).show();
                    }


//                    Intent serverIntent = new Intent(mContext, DeviceListActivity.class);
//                    startActivityForResult(serverIntent,REQUEST_CONNECT_DEVICE);

                }
            });



            if(bt_card.equals(iv_order_cancel))
            {
                builderSingle.setTitle("카드거래 내역");
                builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String strName = arrayAdapter.getItem(which);
                        final HashMap<String, String> hashMap = cancleList.get(which);
                        AlertDialog.Builder builderInner = new AlertDialog.Builder(ActivityMain.this);
                        builderInner.setMessage(strName);
                        builderInner.setTitle("아래 내역을 취소하시겠습니까?");
                        builderInner.setPositiveButton("예", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,int which) {
                                goTransaction(iv_order_cancel, hashMap );
                                dialog.dismiss();
                            }
                        });
                        builderInner.show();
                    }
                });
            }
            else if(bt_card.equals(iv_receit_list))
            {
                builderSingle.setTitle("영수증 발급");
                Log.e("Jonathan", "영수증 발급");
                builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String strName = arrayAdapter.getItem(which);
                        final HashMap<String, String> hashMap = cancleList.get(which);
                        AlertDialog.Builder builderInner = new AlertDialog.Builder(ActivityMain.this);
                        builderInner.setMessage(strName);
                        builderInner.setTitle("다음 내역의 영수증을 출력하시겠습니까?");
                        builderInner.setPositiveButton("예", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,int which) {
//                                goTransaction(iv_order_cancel, hashMap );
                                showRecieptPopup(hashMap);
                                dialog.dismiss();
                            }
                        });
                        builderInner.show();
                    }
                });
            }




            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            rl_progress.setVisibility(View.GONE);


            AlertDialog dialog = builderSingle.create();

            // Get the alert dialog ListView instance
            ListView listView = dialog.getListView();

            // Set the divider color of alert dialog list view
            listView.setDivider(new ColorDrawable(Color.BLACK));

            // Set the divider height of alert dialog list view
            listView.setDividerHeight(2);

            // Finally, display the alert dialog
            dialog.show();
        }
    }



    public void showRecieptPopup(final HashMap<String, String> hashMap)
    {

        hashMap.put("ONLY", "Y");

        receiptPopup = new ReceiptPopup(this, hashMap, mService, new OnEventOkListener() {
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
        receiptPopup.setCanceledOnTouchOutside(false);
        receiptPopup.show();


    }






    public void popupCardPayList(final ArrayList<HashMap<String, String>> cancleList)
    {



        AlertDialog.Builder builderSingle = new AlertDialog.Builder(ActivityMain.this);
        builderSingle.setTitle("카드거래 내역");



        Collections.sort(cancleList, new Comparator<HashMap<String, String>>() {
            @Override
            public int compare(HashMap<String, String> rhs, HashMap<String, String> lhs) {
                return lhs.get("approvaldate").compareTo(rhs.get("approvaldate"));
            }
        });




        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ActivityMain.this, android.R.layout.select_dialog_singlechoice);
        for(int i = 0 ; i < cancleList.size() ; i++)
        {
//            Log.e("Jonathan", "arryPayment :: " + cancleList.get(i));
            String data = "\n";

            if(cancleList.get(i).containsKey("disp_order_no"))
            {
                data += "주문번호 :: " + cancleList.get(i).get("disp_order_no").toString() + "\n";
            }

            if(cancleList.get(i).containsKey("approvaldate"))
            {
                SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String dateTime = "";
                try {
                    Date date = format.parse(cancleList.get(i).get("approvaldate").toString());
                    dateTime = dateFormat.format(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                data += "결제시간 :: " + dateTime + "\n";
            }
            if(cancleList.get(i).containsKey("shopName"))
            {
                data += "결제처 :: " + cancleList.get(i).get("shopName").toString() + "\n";
            }
            if(cancleList.get(i).containsKey("outmessage"))
            {
                data += cancleList.get(i).get("outmessage").toString() + "\n\n";
            }
            if(cancleList.get(i).containsKey("issuername"))
            {
                data += cancleList.get(i).get("issuername").toString() + "\n";
            }
            if(cancleList.get(i).containsKey("cardno"))
            {
                data += "카드번호 :: " + cancleList.get(i).get("cardno").toString() + "\n";
            }
            if(cancleList.get(i).containsKey("totalamount"))
            {
                data += "결제금액 :: " + cancleList.get(i).get("totalamount").toString() + "원" + "\n";
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
                final HashMap<String, String> hashMap = cancleList.get(which);
                AlertDialog.Builder builderInner = new AlertDialog.Builder(ActivityMain.this);
                builderInner.setMessage(strName);
                builderInner.setTitle("아래 내역을 취소하시겠습니까?");
                builderInner.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,int which) {
                        goTransaction(iv_order_cancel, hashMap );
                        dialog.dismiss();
                    }
                });
                builderInner.show();
            }
        });
//                builderSingle.show();



        AlertDialog dialog = builderSingle.create();

        // Get the alert dialog ListView instance
        ListView listView = dialog.getListView();

        // Set the divider color of alert dialog list view
        listView.setDivider(new ColorDrawable(Color.BLACK));

        // Set the divider height of alert dialog list view
        listView.setDividerHeight(2);

        // Finally, display the alert dialog
        dialog.show();
    }








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
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }



    public void popupInternet()
    {
        NoticeBtnOnePopup orderNotData = new NoticeBtnOnePopup(this, "인터넷 연결이 되지 않았습니다.\n 확인하시고 다시 시도해주세요.", new OnEventOkListener() {
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




    public void showQuestionPopup( String body, String data )
    {

        QuestionPopup popup = new QuestionPopup(this, body, data, new OnEventOkListener() {

            @Override
            public void onOk() {
                if(mBluetoothAdapter.isEnabled())
                {
                    mService.stop();
                }

                moveTaskToBack(true);
                finish();
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                android.os.Process.killProcess(android.os.Process.myPid());
            }

            @Override
            public void onOk(String addMenu) {
                if(mBluetoothAdapter.isEnabled())
                {
                    mService.stop();
                }

                moveTaskToBack(true);
                finish();
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                android.os.Process.killProcess(android.os.Process.myPid());

            }

            @Override
            public void onOk(HashMap<String, String> addOrigin) {
                if(mBluetoothAdapter.isEnabled())
                {
                    mService.stop();
                }

                moveTaskToBack(true);
                finish();
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                android.os.Process.killProcess(android.os.Process.myPid());

            }
        });
        popup.setCanceledOnTouchOutside(false);
        popup.show();
    }


    @Override
    public void onBackPressed() {

        Log.e("Jonathan","onBackPressed : " + getFragmentManager().getBackStackEntryCount());

        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
//            this.finish();
            showQuestionPopup("종료하시겠습니까?", "");
        } else {
            getSupportFragmentManager().popBackStack();

        }
    }





    public static String STRSAVEPATH = "";
    public static String SAVEFILEPATH = "";




    private File makeDirectory(String dir_path){
        File dir = new File(dir_path);
        if (!dir.exists())
        {
            dir.mkdirs();
            Log.i( "Jonathan" , "!dir.exists" );
        }else{
            Log.i( "Jonathan" , "dir.exists" );
        }

        return dir;
    }



    private File makeFile(File dir , String file_path){
        File file = null;
        boolean isSuccess = false;
        if(dir.isDirectory()){
            file = new File(file_path);
            if(file!=null&&!file.exists()){
                Log.i( "Jonathan" , "!file.exists" );
                try {
                    isSuccess = file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally{
                    Log.i("Jonathan", "파일생성 여부 = " + isSuccess);
                }
            }else{
                Log.i( "Jonathan" , "file.exists" );
            }
        }
        return file;
    }








    public void isDayAfter()
    {
        SharedPreferences pref = getSharedPreferences("GOFOODA", MODE_PRIVATE);

        String nowDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
        String loginDate = pref.getString("loginDate", "");

        if(!nowDate.equals(loginDate))
        {

            NoticeBtnOnePopup orderNotData = new NoticeBtnOnePopup(this, "로그인 후 하루가 지났습니다. \n다시 로그인 해주세요.", new OnEventOkListener() {
                @Override
                public void onOk() {

                    exitApp();

                }

                @Override
                public void onOk(String addMenu) {

                    exitApp();

                }

                @Override
                public void onOk(HashMap<String, String> addOrigin) {

                    exitApp();

                }
            });
            orderNotData.setCanceledOnTouchOutside(false);
            orderNotData.show();
            return;
        }
    }


    public void exitApp()
    {

        SharedPreferences pref = getSharedPreferences("GOFOODA", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("loginDate", new SimpleDateFormat("yyyMMdd").format(new Date()));
        editor.commit();


        moveTaskToBack(true);
        finish();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        android.os.Process.killProcess(android.os.Process.myPid());
    }




    public void pauseMediaVideo()
    {
        videoView.setVisibility(View.GONE);
        videoView.stopPlayback();

    }


    public void playMediaVideo()
    {

        videoView.setVisibility(View.GONE);
        rl_video.setVisibility(View.GONE);

        MediaController mediacontroller = new MediaController(mContext);
        mediacontroller.setAnchorView(videoView);
        mediacontroller.setVisibility(View.GONE);

        String videoName = "video_gofooda";
        Uri video = Uri.parse("android.resource://"+ getPackageName() + "/raw/" + videoName);
        videoView.setMediaController(mediacontroller);
        videoView.setVideoURI(video);


        videoView.setOnErrorListener(mOnErrorListener);
        videoView.requestFocus();

        videoView.getDuration();
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            public void onPrepared(MediaPlayer mp) {

                mp.setLooping(true);
//                if(mp.isPlaying()) {
//                    mp.pause();
//                }
                mp.start();


            }
        });

        sl_ad.setVisibility(View.GONE);
        videoView.setVisibility(View.VISIBLE);


    }


    private MediaPlayer.OnErrorListener mOnErrorListener = new MediaPlayer.OnErrorListener() {

        @Override
        public boolean onError(MediaPlayer mp, int what, int extra) {
            // Your code goes here
            Log.e("Jonathan", "Video Error Hello");
            if(order_list.size() > 0)
            {
                pauseMediaVideo();
            }
            else
            {
                rl_video.setVisibility(View.VISIBLE);
                pauseMediaVideo();
                new Handler().postDelayed(new Runnable() {
                    @Override public void run() {
                        playMediaVideo();
                    }
                }, 100);
            }




            return true;
        }
    };













    //************KICC 연동**************
    //Jonathan 190918 KICC 연동

    private MakePrintMessage printMessage;


    public void setTranData(String tran_types, String ADD_FIELD){

        ComponentName compName = new ComponentName("kr.co.kicc.easycarda","kr.co.kicc.easycarda.CallPopup");

        Intent intent = new Intent(Intent.ACTION_MAIN);
//        intent.putExtra("TRAN_NO", this.tranno.getText().toString());
        intent.putExtra("TRAN_TYPE", tran_types);
        if("M8".equals(tran_types)) {
            intent.putExtra("TERMINAL_TYPE", "CE");
            intent.putExtra("TEXT_DECLINE", "포인트 조회가 거절되었습니다");
        }
        else
            intent.putExtra("TERMINAL_TYPE", "40");
//        intent.putExtra("TOTAL_AMOUNT",this.amount.getText().toString());
//        intent.putExtra("TAX",this.tax.getText().toString());
        intent.putExtra("TAX_OPTION","A");
//        intent.putExtra("TIP",this.tip.getText().toString());
        //intent.putExtra("TIP_OPTION","N");
        if("D4".equals(tran_types)||"B4".equals(tran_types)) {
//            intent.putExtra("APPROVAL_NUM", this.appr_num.getText().toString());
//            intent.putExtra("APPROVAL_DATE", this.appr_date.getText().toString());
//            intent.putExtra("TRAN_SERIALNO", this.tran_serialno.getText().toString());
        }
        if("B1".equals(tran_types) || "B2".equals(tran_types) || "B3".equals(tran_types) || "B4".equals(tran_types)) {
            intent.putExtra("CASHAMOUNT", "00");
        }else {
//            intent.putExtra("INSTALLMENT", this.installment.getText().toString());
        }
        if("PT".equals(tran_types)){
            String printmsg = printMessage.receiptPrint("160516120000", "IC신용구매", false, false, 1004, 91, 0, 0, "1234-56**-****-1234", "테스트점", "1234567890", "홍길동", "02-1234-5678", "1234567", "서울 테스트구 테스트동", "발급사", "00001111", "", "", "12345678", "매입사", "", "", true);
            try {
                intent.putExtra("PRINT_DATA", printmsg.getBytes("EUC-KR"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        intent.putExtra("ADD_FIELD",ADD_FIELD);
        intent.putExtra("TIMEOUT","30");

        intent.putExtra("TEXT_PROCESS","결제 진행중입니다");
        intent.putExtra("TEXT_COMPLETE", "결제가 완료되었습니다");
//		intent.putExtra("TEXT_FALLBACK", "카드의 마그네틱부분으로\n읽어주세요");
//
//		intent.putExtra("TEXT_MAIN_SIZE", 60);
//		intent.putExtra("IMG_BG_WIDTH", 1000);
//		intent.putExtra("IMG_CARD_WIDTH", 600);
//		intent.putExtra("IMG_CLOSE_WIDTH", 100);

//		intent.putExtra("FALLBACK_FLAG","N");


//        if(rb2.isChecked()) {

            intent.putExtra("IMG_BG_PATH", "/sdcard/kicc/background_gray.png");
            intent.putExtra("TEXT_MAIN_SIZE", 25);
            intent.putExtra("TEXT_MAIN_COLOR", "#FFFFFF");
            intent.putExtra("TEXT_SUB1_SIZE", 15);
            intent.putExtra("TEXT_SUB1_COLOR", "#FFFF00");
            intent.putExtra("TEXT_SUB2_SIZE", 12);
            intent.putExtra("TEXT_SUB2_COLOR", "#00FFFF");
            intent.putExtra("TEXT_SUB3_SIZE", 20);
            intent.putExtra("TEXT_SUB3_COLOR", "#FF00FF");
//        }

//        if(rb3.isChecked()) {

            intent.putExtra("IMG_BG_PATH", "/sdcard/kicc/background_kicc.png");
            intent.putExtra("IMG_CARD_PATH", "/sdcard/kicc/card_kicc.png");
            intent.putExtra("IMG_CLOSE_PATH", "/sdcard/kicc/close_kicc.png");
            intent.putExtra("TEXT_MAIN_SIZE", 18);
            intent.putExtra("TEXT_MAIN_COLOR", "#303030");
            intent.putExtra("TEXT_SUB1_SIZE", 12);
            intent.putExtra("TEXT_SUB1_COLOR", "#ff752a");
            intent.putExtra("TEXT_SUB2_SIZE", 10);
            intent.putExtra("TEXT_SUB2_COLOR", "#ff752a");
            intent.putExtra("TEXT_SUB3_SIZE", 16);
            intent.putExtra("TEXT_SUB3_COLOR", "#909090");
//        }

//		if(barcode.getText().toString().isEmpty()==false)
//		{
//			intent.putExtra("DONGLE_FLAG",this.dongleflag.getText().toString());
//			intent.putExtra("BARCODE",this.barcode.getText().toString());
//
//		}

        intent.setComponent(compName);
        printInent(intent);
        startActivityForResult(intent, 1);
    }



    public static void printInent(Intent i) {
        try {
            //Log.e("KTC","-------------------------------------------------------");
            //util.saveLog("-------------------------------------------------------");
            if (i != null) {
                Bundle extras = i.getExtras();
                if (extras != null) {
                    Set keys = extras.keySet();

                    for (String _key : extras.keySet()) {
                        Log.e("KTC","key=" + _key + " : " + extras.get(_key));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }

    public void savebmp(String filename, int drawable_id)
    {
        Bitmap bm = BitmapFactory.decodeResource(getApplicationContext().getResources(), drawable_id);
        File dir = new File(Environment.getExternalStorageDirectory() + File.separator + "kicc");

        boolean doSave = true;
        if (!dir.exists()) {
            doSave = dir.mkdirs();
        }

        if (doSave) {
            saveBitmapToFile(dir,filename,bm,Bitmap.CompressFormat.PNG,100);
        }
        else {
            Log.e("app","Couldn't create target directory.");
        }
    }

    public boolean saveBitmapToFile(File dir, String fileName, Bitmap bm,
                                    Bitmap.CompressFormat format, int quality) {

        File imageFile = new File(dir,fileName);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(imageFile);

            bm.compress(format,quality,fos);

            fos.close();

            return true;
        }
        catch (IOException e) {
            Log.e("app",e.getMessage());
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return false;
    }



}
