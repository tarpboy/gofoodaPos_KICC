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
import java.util.HashMap;

import app.payfun.com.gofoodapos.R;


public class MenuOriginAddPopup extends Dialog implements View.OnClickListener {

	private String mBody2 = "";

	private OnEventOkListener mOnEventPopupListener;
	private OnEventCancelListener mOnEventCancelListener;



	private Context mContext;
	NumberFormat formatter = new DecimalFormat("#0");


	Button bt_question_confirm, bt_question_cancle, question_cancle;
	EditText et_ingredient, et_origin, et_amount;


	String getData;


	public MenuOriginAddPopup(Context context, String body2, String data, OnEventOkListener onEventPopupListener) {
		super(context, R.style.Theme_Dialog);



		mContext = context;

		mBody2 = body2;
		getData = data;

//		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.popup_menu_origin_add);
		mOnEventPopupListener = onEventPopupListener;


		bt_question_confirm = (Button)findViewById(R.id.bt_question_confirm);
		bt_question_cancle = (Button)findViewById(R.id.bt_question_cancle);
		question_cancle = (Button)findViewById(R.id.question_cancle);

		et_ingredient = (EditText)findViewById(R.id.et_ingredient);
		et_origin = (EditText)findViewById(R.id.et_origin);
		et_amount = (EditText)findViewById(R.id.et_amount);


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
					HashMap<String, String> originData = new HashMap<>();
					originData.put("Ingredient", et_ingredient.getText().toString());
					originData.put("Origin", et_origin.getText().toString());
					originData.put("Amount", et_amount.getText().toString());

					clickOk(originData);
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

	private void clickOk(HashMap<String,String> addOrigin) {
		if (mOnEventPopupListener != null)
			mOnEventPopupListener.onOk(addOrigin);

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
