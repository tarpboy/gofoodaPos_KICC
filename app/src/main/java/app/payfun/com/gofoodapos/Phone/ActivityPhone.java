package app.payfun.com.gofoodapos.Phone;

import android.Manifest;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import app.payfun.com.gofoodapos.Bluetooth.BluetoothService;
import app.payfun.com.gofoodapos.Bluetooth.Command;
import app.payfun.com.gofoodapos.Bluetooth.DeviceListActivity;
import app.payfun.com.gofoodapos.Bluetooth.PrinterCommand;
import app.payfun.com.gofoodapos.Define;
import app.payfun.com.gofoodapos.Network.ApiPart;
import app.payfun.com.gofoodapos.Network.ConnectInfo;
import app.payfun.com.gofoodapos.Network.ConnectPost;
import app.payfun.com.gofoodapos.Network.ConnectValue;
import app.payfun.com.gofoodapos.Network.PrintLog;
import app.payfun.com.gofoodapos.OrderAdapter;
import app.payfun.com.gofoodapos.Popup.NoticeBtnOnePopup;
import app.payfun.com.gofoodapos.Popup.NoticeCanclePopup;
import app.payfun.com.gofoodapos.Popup.NoticeOrderNumPopup;
import app.payfun.com.gofoodapos.Popup.OnEventCancelListener;
import app.payfun.com.gofoodapos.Popup.OnEventOkListener;
import app.payfun.com.gofoodapos.Popup.OnEventOtherListener;
import app.payfun.com.gofoodapos.Popup.ReceiptPopup;
import app.payfun.com.gofoodapos.Popup.ReceiptTypePopup;
import app.payfun.com.gofoodapos.R;



public class ActivityPhone extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {


    ReceiptPopup receiptPopup = null;

    private static final int PERMISSION_SEND_SMS = 123;
    EditText tv_phone_num;
    Button t9_key_0;
    Button t9_key_1;
    Button t9_key_2;
    Button t9_key_3;
    Button t9_key_4;
    Button t9_key_5;
    Button t9_key_6;
    Button t9_key_7;
    Button t9_key_8;
    Button t9_key_9;
    Button t9_key_clear;
    ImageView t9_key_backspace;


    Button bt_home;
    Button bt_send_sms;

    TextView tv_order_num_text;
    TextView tv_order_num;
    TextView tv_total_value;
    TextView tv_total_price;
    RecyclerView recyclerview_order;
    OrderAdapter adapter_order;

    String tid;
    String business_license_no;
    String order_num;
    String total_value;
    String total_price;
    String disp_order_no;
    String order_type;
    ArrayList<HashMap<String, String>> order_list;


    RelativeLayout rl_progress;
    ProgressBar progressBar;


    boolean isTouched = false;
    Button bt_notice_other;


    //************프린트 시작**************
    Button bt_print;
    Button bt_bluetuooth;

    Button bt_bluetooth_state;

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

    public Button btnCash;


    public String isBussinuess = "";


//    BluetoothAdapter mBluetoothAdapter;

    //////////////////////////////////////////////////////////////////


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);

        mstrCashID = "";
        isBussinuess = "";

        mContext = this;
        SharedPreferences pref = getSharedPreferences("GOFOODA", MODE_PRIVATE);
        business_license_no = pref.getString("business_license_no", "");
        tid = pref.getString("tid", "");

        Intent intent = getIntent();
        order_num = intent.getStringExtra("order_num");
//        business_license_no = intent.getStringExtra("business_license_no");
        tid = intent.getStringExtra("tid");
        total_value = intent.getStringExtra("total_value");
        total_price = intent.getStringExtra("total_price");
        disp_order_no = intent.getStringExtra("disp_order_no");
        order_type = intent.getStringExtra("order_type");


        order_list = (ArrayList<HashMap<String, String>>)intent.getSerializableExtra("order_list");

        tv_order_num_text = (TextView)findViewById(R.id.tv_order_num_text);
        tv_order_num = (TextView)findViewById(R.id.tv_order_num);
        tv_total_value = (TextView)findViewById(R.id.tv_total_value);
        tv_total_price = (TextView)findViewById(R.id.tv_total_price);
        recyclerview_order = (RecyclerView)findViewById(R.id.recyclerview_order);
        bt_notice_other = (Button)findViewById(R.id.bt_notice_other);
        bt_notice_other.setOnClickListener(this);

        tv_order_num.setText(disp_order_no);
        tv_total_value.setText(total_value);
        tv_total_price.setText(total_price);

        rl_progress = (RelativeLayout)findViewById(R.id.rl_progress);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        rl_progress.setVisibility(View.GONE);


        btnCash = (Button)findViewById(R.id.btnCash);
        btnCash.setOnClickListener(this);







        //************프린트 시작**************
        bt_print = (Button)findViewById(R.id.bt_print);
        bt_print.setOnClickListener(this);
        bt_bluetuooth = (Button)findViewById(R.id.bt_bluetuooth);
        bt_bluetuooth.setOnClickListener(this);
        bt_bluetooth_state = (Button)findViewById(R.id.bt_bluetooth_state);
        bt_bluetooth_state.setBackground(getResources().getDrawable(android.R.drawable.presence_invisible));


        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
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
                // If the adapter is null, then Bluetooth is not supported
                if (mBluetoothAdapter == null) {
                    Toast.makeText(mContext, "Bluetooth is not available",    Toast.LENGTH_LONG).show();
                    mService.stop();
                }
                mService = new BluetoothService(mContext, mHandler);


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
            }
        }







        final Handler handler1  = new Handler();
        final Runnable runnable1 = new Runnable() {
            @Override
            public void run() {

                if(!isTouched)
                {
                    if(mBluetoothAdapter != null)
                    {
                        if(mBluetoothAdapter.isEnabled())
                        {
                            mService.stop();
                        }

                        finish();
                    }
                }
            }
        };


        handler1.postDelayed(runnable1, 8000);









        final float startSize = 51; // Size in pixels
        final float endSize = 90;

        final float startSize2 = 19; // Size in pixels
        final float endSize2 = 30;
        long animationDuration = 1000; // Animation duration in ms

        ValueAnimator animator = ValueAnimator.ofFloat(startSize, endSize);
        ValueAnimator animator2 = ValueAnimator.ofFloat(startSize2, endSize2);
        animator.setDuration(animationDuration);
        animator2.setDuration(animationDuration);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float animatedValue = (float) valueAnimator.getAnimatedValue();
                tv_order_num.setTextSize(animatedValue);
            }
        });

        animator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float animatedValue = (float) valueAnimator.getAnimatedValue();
                tv_order_num_text.setTextSize(animatedValue);
            }
        });

        animator.setRepeatCount(Animation.INFINITE);
        animator.start();
        animator2.setRepeatCount(Animation.INFINITE);
        animator2.start();



        LinearLayoutManager orderLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerview_order.setLayoutManager(orderLayoutManager);
        adapter_order = new OrderAdapter();
        adapter_order.setLinearLayoutManager(orderLayoutManager);
        adapter_order.setRecyclerView(recyclerview_order);
        recyclerview_order.setAdapter(adapter_order);
        adapter_order.addAll(order_list);




        tv_phone_num = (EditText)findViewById(R.id.tv_phone_num);
        t9_key_0 = (Button)findViewById(R.id.t9_key_0);
        t9_key_1 = (Button)findViewById(R.id.t9_key_1);
        t9_key_2 = (Button)findViewById(R.id.t9_key_2);
        t9_key_3 = (Button)findViewById(R.id.t9_key_3);
        t9_key_4 = (Button)findViewById(R.id.t9_key_4);
        t9_key_5 = (Button)findViewById(R.id.t9_key_5);
        t9_key_6 = (Button)findViewById(R.id.t9_key_6);
        t9_key_7 = (Button)findViewById(R.id.t9_key_7);
        t9_key_8 = (Button)findViewById(R.id.t9_key_8);
        t9_key_9 = (Button)findViewById(R.id.t9_key_9);
        t9_key_clear = (Button)findViewById(R.id.t9_key_clear);
        t9_key_backspace = (ImageView)findViewById(R.id.t9_key_backspace);

        bt_home = (Button)findViewById(R.id.bt_home);
        bt_send_sms = (Button)findViewById(R.id.bt_send_sms);



        t9_key_0.setOnClickListener(this);
        t9_key_1.setOnClickListener(this);
        t9_key_2.setOnClickListener(this);
        t9_key_3.setOnClickListener(this);
        t9_key_4.setOnClickListener(this);
        t9_key_5.setOnClickListener(this);
        t9_key_6.setOnClickListener(this);
        t9_key_7.setOnClickListener(this);
        t9_key_8.setOnClickListener(this);
        t9_key_9.setOnClickListener(this);
        t9_key_clear.setOnClickListener(this);
        t9_key_backspace.setOnClickListener(this);

        bt_home.setOnClickListener(this);
        bt_send_sms.setOnClickListener(this);

        Define.mp1.stop();
        Define.mp1 = MediaPlayer.create(this, R.raw.hporremember);
        Define.mp1.start();




        if("CARD".equals(order_type))
        {
            bt_notice_other.setVisibility(View.INVISIBLE);
        }
        else
        {
            bt_notice_other.setVisibility(View.VISIBLE);
        }



        final NoticeOrderNumPopup orderNotData = new NoticeOrderNumPopup(this, disp_order_no, new OnEventOkListener() {
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


        final Handler handler  = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (orderNotData.isShowing()) {
                    orderNotData.dismiss();
                }
            }
        };

        orderNotData.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                handler.removeCallbacks(runnable);
            }
        });

        handler.postDelayed(runnable, 4000);


















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








        if(mBluetoothAdapter != null)
        {
            if(mBluetoothAdapter.isEnabled())
            {
                String address = pref.getString("address", "");
                if(!"".equals(address))
                {
                    BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
                    mService.connect(device);

//                con_dev = mService.getDevByMac(address);
//                mService.connect(con_dev);
                }
            }

        }




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
//                    Toast.makeText(mContext, msg.getData().getString(Define.TOAST), Toast.LENGTH_SHORT).show();
                    break;
                case Define.MESSAGE_CONNECTION_LOST:    //蓝牙已断开连接
                    bt_bluetooth_state.setBackground(getResources().getDrawable(android.R.drawable.presence_invisible));
//                    Toast.makeText(mContext, "Device connection was lost",  Toast.LENGTH_SHORT).show();
                    break;
                case Define.MESSAGE_UNABLE_CONNECT:     //无法连接设备
                    bt_bluetooth_state.setBackground(getResources().getDrawable(android.R.drawable.presence_invisible));
//                    Toast.makeText(mContext, "Unable to connect device",  Toast.LENGTH_SHORT).show();
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
//                            bt_bluetuooth.setVisibility(View.GONE);
//                            bt_print.setVisibility(View.VISIBLE);
//
//                            //카드일때만 주문번호 출력
////                            if("CARD".equals(order_type))
////                            {
////                                byte[] cmd = new byte[3];
////                                cmd[0] = 0x1b;
////                                cmd[1] = 0x21;
////                                cmd[2] |= 0x10;
////                                mService.write(cmd);           //��������ģʽ
////                                mService.sendMessage("주문번호 : " + disp_order_no + "\n\n\n", "EUC-KR");
////                            }
//
//
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
//                    bt_bluetuooth.setVisibility(View.VISIBLE);
//                    bt_print.setVisibility(View.GONE);
//                    break;
//                case BluetoothService.MESSAGE_UNABLE_CONNECT:     //�޷������豸
////                    Toast.makeText(getApplicationContext(), "Unable to connect device",
////                            Toast.LENGTH_SHORT).show();
//                    break;
//            }
//        }
//    };










    @Override
    public void onUserInteraction(){

        isTouched = true;
    }


    @Override
    public void onClick(View view) {



        switch (view.getId()) {

            case R.id.btnCash:

                goTransaction(view, null);

                break;

            case R.id.bt_notice_other:

                mstrCashID = null;
                isBussinuess = "N";
                btnCash.performClick();

                break;


        }




        if(view.equals(t9_key_clear) || view.equals(t9_key_backspace))
        {
            if(view.equals(t9_key_clear))
            {
                tv_phone_num.setText("");
            }
            else if(view.equals(t9_key_backspace))
            {
                Editable editable = tv_phone_num.getText();
                int charCount = editable.length();
                if (charCount > 0) {
                    editable.delete(charCount - 1, charCount);
                }
            }

        }
        else if(view.equals(bt_home))
        {
            if(mBluetoothAdapter.isEnabled())
            {
                mService.stop();
            }

            finish();
        }
        else if(view.equals(bt_send_sms))
        {
            if(tv_phone_num.getText().toString().length() < 11 )
            {
                NoticeBtnOnePopup orderNotData = new NoticeBtnOnePopup(this, "휴대폰번호를 정확히 입력해주세요.", new OnEventOkListener() {
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
            }
            else
            {


                int PERMISSION_ALL = 1;
                String[] PERMISSIONS = { Manifest.permission.SEND_SMS};

                if(!hasPermissions(this, PERMISSIONS)){
                    ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
                }
                else
                {


                    if("CARD".equals(order_type))
                    {
                        String smsMessege = "";
                        smsMessege += "주문번호 : ";
                        smsMessege += order_num;
                        smsMessege += "\n";
                        smsMessege += "잠시만 기다려주세요.";
                        smsMessege += "음식이 완료되면 문자드릴게요~";

                        sendSMS(tv_phone_num.getText().toString(), smsMessege, new HashMap<String, String>());
                    }
                    else
                    {
                        NoticeCanclePopup popup = new NoticeCanclePopup(this, "현금 영수증을 하시겠습니까?", new OnEventOkListener() {
                            @Override
                            public void onOk() {
//                                mstrCashID = tv_phone_num.getText().toString();
//                                btnCash.performClick();
                                popupRecietType();

                            }

                            @Override
                            public void onOk(String addMenu) {
//                                mstrCashID = tv_phone_num.getText().toString();
//                                btnCash.performClick();
                                popupRecietType();
                            }

                            @Override
                            public void onOk(HashMap<String, String> addOrigin) {
//                                mstrCashID = tv_phone_num.getText().toString();
//                                btnCash.performClick();
                                popupRecietType();
                            }
                        }, new OnEventCancelListener() {
                            @Override
                            public void onCancel() {

                                String smsMessege = "";
                                smsMessege += "주문번호 : ";
                                smsMessege += order_num;
                                smsMessege += "\n";
                                smsMessege += "잠시만 기다려주세요.";
                                smsMessege += "음식이 완료되면 문자드릴게요~";

                                sendSMS(tv_phone_num.getText().toString(), smsMessege, new HashMap<String, String>());


                            }
                        });

                        popup.setCanceledOnTouchOutside(false);
                        popup.show();
                    }


                }

            }
        }
        else if(view.equals(bt_bluetuooth))
        {

            if(mBluetoothAdapter.isEnabled())
            {
                mService.stop();
            }


            SharedPreferences pref = getSharedPreferences("GOFOODA", this.MODE_PRIVATE);
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
                        if(mBluetoothAdapter.isEnabled())
                        {
                            mService.stop();
                        }

                        Intent serverIntent = new Intent(mContext, DeviceListActivity.class);
                        startActivityForResult(serverIntent, Define.REQUEST_CONNECT_DEVICE);
                    }
                }



            }
            else
            {
                Toast.makeText(mContext,"로그인 화면에서 프린터 사용유무를 체크하지 않았습니다.", Toast.LENGTH_SHORT).show();
            }

//            Intent serverIntent = new Intent(mContext, DeviceListActivity.class);
//            startActivityForResult(serverIntent,REQUEST_CONNECT_DEVICE);
        }
        else if(view.equals(bt_print))
        {

            SharedPreferences pref = getSharedPreferences("GOFOODA", this.MODE_PRIVATE);
            String business_name = pref.getString("business_name", "");

            SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateTime = "";
            Date date = Calendar.getInstance().getTime();
            dateTime = dateFormat.format(date);


            String msg = "";

            byte[] cmd = new byte[3];
            cmd[0] = 0x1b;
            cmd[1] = 0x21;


                       //��������ģʽ

            cmd[2] &= 0xEF;
            mService.write(cmd);
            msg += "\n\n<" + business_name + ">";
            msg += "\n" + dateTime + "";

            cmd[2] |= 0x10;
            mService.write(cmd);
            msg += "\n\n주문번호 : " + disp_order_no + "\n\n\n\n";


//            mService.sendMessage(msg,"EUC-KR");

            SendDataByte(PrinterCommand.POS_Print_Text(msg, Define.KOREAN, 0, 0, 0, 0));
            SendDataByte(Command.LF);




//            mService.sendMessage("\n\n<" + business_name + ">", "EUC-KR");
//            mService.sendMessage("\n\n주문번호 : " + disp_order_no + "\n\n\n\n", "EUC-KR");
//            cmd[2] &= 0xEF;
//            mService.write(cmd);           //ȡ�����ߡ�����ģʽ
//
//            msg =   "*******************\n" +
//                    "프린터 테스트 \n" +
//                    "******************* \n";
//            mService.sendMessage(msg,"EUC-KR");


        }
        else
        {
            Log.e("jonathan", ((TextView)view).getText().toString());
            tv_phone_num.append(((TextView)view).getText().toString());
            return;
        }





    }


    public void popupRecietType()
    {

        ReceiptTypePopup popup = new ReceiptTypePopup(this, "현금영수증 종류를 선택해 주세요.", new OnEventOkListener() {
            @Override
            public void onOk() {
                mstrCashID = tv_phone_num.getText().toString();
                btnCash.performClick();

            }

            @Override
            public void onOk(String addMenu) {
                mstrCashID = tv_phone_num.getText().toString();
                btnCash.performClick();
            }

            @Override
            public void onOk(HashMap<String, String> addOrigin) {
                mstrCashID = tv_phone_num.getText().toString();
                btnCash.performClick();
            }
        }, new OnEventCancelListener() {
            @Override
            public void onCancel() {

                mstrCashID = null;
                isBussinuess = "Y";
                btnCash.performClick();


            }
        }, new OnEventOtherListener() {
            @Override
            public void onOther() {

                mstrCashID = null;
                isBussinuess = "N";
                btnCash.performClick();


            }

        });

        popup.setCanceledOnTouchOutside(false);
        popup.show();

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


    private void sendSMS(String phoneNumber, String message, final HashMap<String, String> hashMap) {



        rl_progress.setVisibility(View.VISIBLE);

        int PERMISSION_ALL = 1;
        String[] PERMISSIONS = { Manifest.permission.SEND_SMS};

        if(!hasPermissions(this, PERMISSIONS)){
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }
        else
        {


            if(!isNetworkAvailable())
            {
                popupInternet();
                return;
            }

//            SmsManager sms = SmsManager.getDefault();
//            sms.sendTextMessage(phoneNumber, null, message, null, null);



            ConnectValue connectValue = new ConnectValue();
            connectValue.addConnectMap("order_no", order_num);
            connectValue.addConnectMap("user_hp", phoneNumber);
//            connectValue.addConnectMap("business_license_no", business_license_no);
            connectValue.addConnectMap("tid", tid);
            connectValue.addConnectMap("modi_by", tid);


            ApiPart apiPart = new ApiPart(this);
            apiPart.updateOrder(connectValue, new ConnectPost(this) {
                @Override
                public void completeSync(String tag, StringBuffer result) {

                    rl_progress.setVisibility(View.GONE);
                    Log.e("Jonatha", "result :: " + result);
                    try {
                        JSONObject jsonObject = new JSONObject(result.toString());

                        if(jsonObject.has("responseStatus"))
                        {
                            String responseStatus = jsonObject.getString("responseStatus");
                            if(!"FAIL".equals(responseStatus))
                            {
                                if(hashMap.isEmpty())
                                {
                                    if(mBluetoothAdapter.isEnabled())
                                    {
                                        mService.stop();
                                    }

                                    finish();
                                }
                                else
                                {
                                    saveCardPayInfo(hashMap, order_num);
                                }
//                            setOrderInfo("CARD");
                            }
                        }



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            });



        }
















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






    /**
     * description : 액티비티가 remove되는경우 이벤트 발생
     */
    @Override
    protected void onDestroy() {

        super.onDestroy();


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










    public void saveCardPayInfo(HashMap<String, String> getHash, String order_no)
    {

        if(!isNetworkAvailable())
        {
            popupInternet();
            return;

        }


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

                Log.e("Jonatha", "1setCardPay result :: " + result);
//                JSONObject jsonObject = validResultData(tag, result);
                try {
                    JSONObject jsonObject = new JSONObject(result.toString());

                    if(jsonObject.has("responseStatus"))
                    {
                        String responseStatus = jsonObject.getString("responseStatus");
                        if(!"FAIL".equals(responseStatus))
                        {
                            if(mBluetoothAdapter.isEnabled())
                            {
                                mService.stop();
                            }

                            finish();
                        }
                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }
        });


    }











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
            intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setData(Uri.parse("market://details?id=" + "com.smartro.secapps.freepay"));
            startActivity(intent);
            return;
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
		 */
        strExMode   += "normal";

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
            if("Y".equals(isBussinuess))
            {
                strExCashType += "1";
            }
            else if("N".equals(isBussinuess))
            {
                strExCashType += "2";
            }
            else
            {
                strExCashType += "0";
            }

			/*
			 * Cash ID
			 * 소득공제 - 휴대폰번호
			 * 지출증빙 - 사업자번호
			 */
            if (mstrCashID == null)
            {
                strStringParams = "smartroapp://freepaylink?" + strExMode + "&" + strExAmount + "&" + strExTranType + "&" + strExCashType;
//                showCashIDInput(v);
//                return;
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

        Log.e("Jonathan", "bluetooth1");
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
                case  Define.REQUEST_CONNECT_DEVICE:     //��������ĳһ�����豸
                    if (resultCode == Activity.RESULT_OK) {   //�ѵ�������б��е�ĳ���豸��
                        Log.e("Jonathan", "bluetooth6");
                        String address = data.getExtras().getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);  //��ȡ�б������豸��mac��ַ

                        BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
                        mService.connect(device);
//                        con_dev = mService.getDevByMac(address);
//                        mService.connect(con_dev);

                        SharedPreferences pref = getSharedPreferences("GOFOODA", MODE_PRIVATE);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("address", address);
                        editor.commit();
                        Log.e("Jonathan", "bluetooth7");


                    }
                    break;
            }
        }
        else
        {
            String strResult = "";
            String strKey = "";
            isBussinuess = "";

//        Toast.makeText(this, "ResultCode = " + resultCode + ", requestCode = " + requestCode, Toast.LENGTH_LONG).show();
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



//            SimplePreferences.saveArrayPreference(arryPayment, "arryPayment", mContext);


                SharedPreferences pref = this.getSharedPreferences("GOFOODA", this.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                if(resultCode == 0)
                {
                    String timnum = new SimpleDateFormat("HHmm").format(new Date());
                    String tranno = pref.getString("tranno", timnum);
                    editor.putString("tranno", String.format("%04d", Integer.parseInt(tranno) + 1));
                    editor.commit();




                    if(hashMap.get("trantype").contains("cash"))
                    {
                        hashMap.put("disp_order_no", disp_order_no);
                        hashMap.put("ONLY", "Y");

                        receiptPopup = new ReceiptPopup(this, hashMap, mService, new OnEventOkListener() {
                            @Override
                            public void onOk() {
                                String smsMessege = "";
                                smsMessege += "주문번호 : ";
                                smsMessege += order_num;
                                smsMessege += "\n";
                                smsMessege += "잠시만 기다려주세요.";
                                smsMessege += "음식이 완료되면 문자드릴게요~";

                                sendSMS(tv_phone_num.getText().toString(), smsMessege, hashMap);

//                            saveCardPayInfo(hashMap, order_num);

                            }

                            @Override
                            public void onOk(String addMenu) {

                                String smsMessege = "";
                                smsMessege += "주문번호 : ";
                                smsMessege += order_num;
                                smsMessege += "\n";
                                smsMessege += "잠시만 기다려주세요.";
                                smsMessege += "음식이 완료되면 문자드릴게요~";

                                sendSMS(tv_phone_num.getText().toString(), smsMessege, hashMap);

//                            saveCardPayInfo(hashMap, order_num);

                            }

                            @Override
                            public void onOk(HashMap<String, String> addOrigin) {

                                String smsMessege = "";
                                smsMessege += "주문번호 : ";
                                smsMessege += order_num;
                                smsMessege += "\n";
                                smsMessege += "잠시만 기다려주세요.";
                                smsMessege += "음식이 완료되면 문자드릴게요~";

                                sendSMS(tv_phone_num.getText().toString(), smsMessege, hashMap);

//                            saveCardPayInfo(hashMap, order_num);

                            }
                        });
                        receiptPopup.setCanceledOnTouchOutside(false);
                        receiptPopup.show();

                        final Handler handler  = new Handler();
                        final Runnable runnable = new Runnable() {
                            @Override
                            public void run() {
                                if(!isTouched)
                                {
                                    if (receiptPopup.isShowing()) {
                                        receiptPopup.dismiss();

                                        String smsMessege = "";
                                        smsMessege += "주문번호 : ";
                                        smsMessege += order_num;
                                        smsMessege += "\n";
                                        smsMessege += "잠시만 기다려주세요.";
                                        smsMessege += "음식이 완료되면 문자드릴게요~";

                                        sendSMS(tv_phone_num.getText().toString(), smsMessege, hashMap);

//                                saveCardPayInfo(hashMap, order_num);
                                    }
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






                }
                else
                {



                    String msg = "";
                    if(resultCode == 1)
                    {
                        msg = "거래 거절 (카드사 거절)";
//                    if(!arryPayment.get(arryPayment.size()-1).get("outmessage").contains("기취소됨"))
//                    {
//                        String timnum = new SimpleDateFormat("HHmm").format(new Date());
//                        String tranno = pref.getString("tranno", timnum);
//                        Log.e("Jonathan", "tranno ::3 " + tranno);
//                        editor.putString("tranno", String.format("%04d", Integer.parseInt(tranno) + 1));
//                        Log.e("Jonathan", "tranno ::4 " + pref.getString("tranno", timnum));
////                        editor.putString("tranno", String.format("%04d", Integer.parseInt(val)));
//                        editor.commit();
//                    }
                        if(hashMap.get("outmessage").contains("기취소됨"))
                        {
                            String timnum = new SimpleDateFormat("HHmm").format(new Date());
                            String tranno = pref.getString("tranno", timnum);
                            Log.e("Jonathan", "tranno ::3 " + tranno);
                            editor.putString("tranno", String.format("%04d", Integer.parseInt(tranno) + 1));
                            Log.e("Jonathan", "tranno ::4 " + pref.getString("tranno", timnum));
                            editor.commit();
                        }

                        if(hashMap.get("outmessage").contains("단말일련번호중복"))
                        {
                            String timnum = new SimpleDateFormat("HHmm").format(new Date());
                            String tranno = pref.getString("tranno", timnum);
                            editor.putString("tranno", String.format("%04d", Integer.parseInt(tranno) + 1));
                            editor.commit();
                        }

                        if(hashMap.get("outmessage").contains("단말일련번호중복"))
                        {
                            String timnum = new SimpleDateFormat("HHmm").format(new Date());
                            String tranno = pref.getString("tranno", timnum);
                            editor.putString("tranno", String.format("%04d", Integer.parseInt(tranno) + 1));
                            editor.commit();
                        }

                        if(hashMap.get("outmessage").contains("확인요망"))
                        {
                            String timnum = new SimpleDateFormat("HHmm").format(new Date());
                            String tranno = pref.getString("tranno", timnum);
                            editor.putString("tranno", String.format("%04d", Integer.parseInt(tranno) + 1));
                            editor.commit();
                        }



                        String timnum = new SimpleDateFormat("HHmm").format(new Date());
                        String tranno = pref.getString("tranno", timnum);
                        Log.e("Jonathan", "tranno ::3 " + tranno);
                        editor.putString("tranno", String.format("%04d", Integer.parseInt(tranno) + 1));
                        Log.e("Jonathan", "tranno ::4 " + pref.getString("tranno", timnum));
                        editor.commit();




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

                }




            }
            catch (Exception e) { e.printStackTrace(); };
        }



    }

    ///////////////////////////////////////////////////////////////
    /////////////////SMARTRO APP END //////////////////////////////
    ///////////////////////////////////////////////////////////////



    private void SendDataByte(byte[] data) {

        if (mService.getState() != BluetoothService.STATE_CONNECTED) {
            Toast.makeText(mContext, "블루투스 프린터를 연결하십시오.", Toast.LENGTH_SHORT)
                    .show();
            return;
        }
        mService.write(data);
    }






}