package app.payfun.com.gofoodapos;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import app.payfun.com.gofoodapos.Popup.OnPasswordOkListener;

/**
 * Created by jonathan on 2018. 4. 22..
 */

public class PasswordDialog extends Dialog implements View.OnClickListener {


    private OnPasswordOkListener mOnEventPopupListener;



    EditText et_password;
    Button bt_notice_confirm;


    public PasswordDialog(Context context, OnPasswordOkListener onEventPopupListener) {
        super(context);

        mOnEventPopupListener = onEventPopupListener;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE); //타이틀 바 삭제
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setContentView(R.layout.password_dialog);


        et_password = (EditText)findViewById(R.id.et_password);
        bt_notice_confirm = (Button)findViewById(R.id.bt_notice_confirm);

        bt_notice_confirm.setOnClickListener(this);



    }



    private void clickOk() {
        if (mOnEventPopupListener != null)
            mOnEventPopupListener.onOk(Define.nullCheck(et_password.getText().toString()));

        dismiss();
    }


    @Override
    public void onClick(View v) {

        if(v.equals(bt_notice_confirm))
        {
            clickOk();
        }




    }
}