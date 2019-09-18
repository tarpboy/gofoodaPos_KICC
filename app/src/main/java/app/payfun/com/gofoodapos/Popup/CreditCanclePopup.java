package app.payfun.com.gofoodapos.Popup;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import app.payfun.com.gofoodapos.R;


public class CreditCanclePopup extends Dialog implements View.OnClickListener {

	private String mBody2 = "";

	private OnEventOkListener mOnEventPopupListener;
	private OnEventCancelListener mOnEventCancelListener;



	private Context mContext;
	NumberFormat formatter = new DecimalFormat("#0");


	TextView tv_notice_popup;
	Button bt_notice_confirm;
	Button bt_notice_cancel;


	EditText et_tran_num;


	public CreditCanclePopup(Context context, String body2, OnEventOkListener onEventPopupListener) {
		super(context, R.style.Theme_Dialog);



		mContext = context;

		mBody2 = body2;

//		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.credit_cancle_popup);
		mOnEventPopupListener = onEventPopupListener;


		tv_notice_popup = (TextView)findViewById(R.id.tv_notice_popup);
		bt_notice_confirm = (Button)findViewById(R.id.bt_notice_confirm);
		bt_notice_cancel = (Button)findViewById(R.id.bt_notice_cancel);

		et_tran_num = (EditText)findViewById(R.id.et_tran_num);



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

				String tran_num = et_tran_num.getText().toString();

				clickOk(tran_num);
				break;




			case R.id.bt_notice_cancel:


				dismiss();

				break;




		}


	}

	private void clickOk(String tran_num) {
		if (mOnEventPopupListener != null)
			mOnEventPopupListener.onOk(tran_num);

		dismiss();
	}

	private void clickCancel() {
		if (mOnEventCancelListener != null)
			mOnEventCancelListener.onCancel();

//		dismiss();
	}


	public void setOnCancelListener(OnEventCancelListener onEventCancelListener) {
		mOnEventCancelListener = onEventCancelListener;
	}









}
