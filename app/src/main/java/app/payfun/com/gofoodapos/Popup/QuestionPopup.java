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


public class QuestionPopup extends Dialog implements View.OnClickListener {

	private String mBody2 = "";

	private OnEventOkListener mOnEventPopupListener;
	private OnEventCancelListener mOnEventCancelListener;



	private Context mContext;
	NumberFormat formatter = new DecimalFormat("#0");


	TextView tv_question_popup;
	Button bt_question_confirm, bt_question_cancle, question_cancle;


	String getData;


	public QuestionPopup(Context context, String body2, String data, OnEventOkListener onEventPopupListener) {
		super(context, R.style.Theme_Dialog);



		mContext = context;

		mBody2 = body2;
		getData = data;

//		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.question_popup);
		mOnEventPopupListener = onEventPopupListener;


		tv_question_popup = (TextView)findViewById(R.id.tv_question_popup);
		bt_question_confirm = (Button)findViewById(R.id.bt_question_confirm);
		bt_question_cancle = (Button)findViewById(R.id.bt_question_cancle);
		question_cancle = (Button)findViewById(R.id.question_cancle);


		tv_question_popup.setText(mBody2);

		bt_question_confirm.setOnClickListener(this);
		bt_question_cancle.setOnClickListener(this);
		question_cancle.setOnClickListener(this);




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


			case R.id.bt_question_confirm:

				if("".equals(getData))
				{
					clickOk();
				}
				else
				{

				}


				break;


			case R.id.bt_question_cancle:

				dismiss();

				break;


			case R.id.question_cancle:

				dismiss();

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
