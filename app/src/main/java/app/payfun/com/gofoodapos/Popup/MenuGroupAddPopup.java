package app.payfun.com.gofoodapos.Popup;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import app.payfun.com.gofoodapos.R;


public class MenuGroupAddPopup extends Dialog implements View.OnClickListener {

	private String mBody2 = "";

	private OnEventOkListener mOnEventPopupListener;
	private OnEventCancelListener mOnEventCancelListener;



	private Context mContext;
	NumberFormat formatter = new DecimalFormat("#0");


	Button bt_question_confirm, bt_question_cancle, question_cancle;
	EditText et_group_name;


	String getData;


	public MenuGroupAddPopup(Context context, String body2, String data, OnEventOkListener onEventPopupListener) {
		super(context, R.style.Theme_Dialog);



		mContext = context;

		mBody2 = body2;
		getData = data;

//		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.popup_menu_group_add);
		mOnEventPopupListener = onEventPopupListener;


		bt_question_confirm = (Button)findViewById(R.id.bt_question_confirm);
		bt_question_cancle = (Button)findViewById(R.id.bt_question_cancle);
		question_cancle = (Button)findViewById(R.id.question_cancle);
		et_group_name = (EditText)findViewById(R.id.et_group_name);


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
					clickOk(et_group_name.getText().toString());
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

	private void clickOk(String addMenu) {
		if (mOnEventPopupListener != null)
			mOnEventPopupListener.onOk(addMenu);

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
