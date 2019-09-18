package app.payfun.com.gofoodapos.Popup;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import app.payfun.com.gofoodapos.R;


public class NoticeCanclePopup extends Dialog implements View.OnClickListener {

	private String mBody2 = "";

	private OnEventOkListener mOnEventPopupListener;
	private OnEventCancelListener mOnEventCancelListener;



	private Context mContext;
	NumberFormat formatter = new DecimalFormat("#0");


	TextView tv_notice_popup;
	Button bt_notice_confirm;
	Button bt_notice_cancel;

	public NoticeCanclePopup(Context context, String body2, OnEventOkListener onEventPopupListener, OnEventCancelListener onEventCancelListener) {
		super(context, R.style.Theme_Dialog);



		mContext = context;

		mBody2 = body2;

//		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.notice_popup);
		mOnEventPopupListener = onEventPopupListener;
		mOnEventCancelListener = onEventCancelListener;


		tv_notice_popup = (TextView)findViewById(R.id.tv_notice_popup);
		bt_notice_confirm = (Button)findViewById(R.id.bt_notice_confirm);
		bt_notice_cancel = (Button)findViewById(R.id.bt_notice_cancel);


		if(mBody2.length() > 20)
		{
			tv_notice_popup.setTextSize(13);
		}


		tv_notice_popup.setText(mBody2);



		bt_notice_confirm.setText("예");
		bt_notice_cancel.setText("아니요\n문자만 받을게요.");
		bt_notice_confirm.setOnClickListener(this);
		bt_notice_cancel.setOnClickListener(this);


	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);



	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub


		switch (v.getId()) {


			case R.id.bt_notice_confirm:

//				dismiss();
				clickOk();
				break;




			case R.id.bt_notice_cancel:

				clickCancel();
//				dismiss();

				break;




		}


	}

	private void clickOk() {
		if (mOnEventPopupListener != null)
			mOnEventPopupListener.onOk();

		dismiss();
	}

	private void clickCancel() {
		if (mOnEventCancelListener != null)
			mOnEventCancelListener.onCancel();

		dismiss();
	}


	public void setOnCancelListener(OnEventCancelListener onEventCancelListener) {
		mOnEventCancelListener = onEventCancelListener;
	}









}
