package app.payfun.com.gofoodapos.Popup;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import app.payfun.com.gofoodapos.R;


public class CashPopup extends Dialog implements View.OnClickListener {

	private String mBody2 = "";

	private OnEventOkListener mOnEventPopupListener;
	private OnEventCancelListener mOnEventCancelListener;



	private Context mContext;
	NumberFormat formatter = new DecimalFormat("#0");


//	TextView tv_notice_popup;
	Button bt_cash_confirm;
	TextView tv_total_price;



	public CashPopup(Context context, String body2, OnEventOkListener onEventPopupListener) {
		super(context, R.style.Theme_Dialog);



		mContext = context;

		mBody2 = body2;

		mOnEventPopupListener = onEventPopupListener;

//		requestWindowFeature(Window.FEATURE_NO_TITLE);




	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.cash_popup);



		bt_cash_confirm = (Button)findViewById(R.id.bt_cash_confirm);
		tv_total_price = (TextView)findViewById(R.id.tv_total_price);


		tv_total_price.setText(mBody2);
		bt_cash_confirm.setOnClickListener(this);



	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub


		switch (v.getId()) {


			case R.id.bt_cash_confirm:

				clickOk();
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

//		dismiss();
	}


	public void setOnCancelListener(OnEventCancelListener onEventCancelListener) {
		mOnEventCancelListener = onEventCancelListener;
	}









}
