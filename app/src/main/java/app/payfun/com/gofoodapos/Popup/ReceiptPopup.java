package app.payfun.com.gofoodapos.Popup;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Iterator;

import app.payfun.com.gofoodapos.Bluetooth.BluetoothService;
import app.payfun.com.gofoodapos.Bluetooth.Command;
import app.payfun.com.gofoodapos.Bluetooth.PrinterCommand;
import app.payfun.com.gofoodapos.Define;
import app.payfun.com.gofoodapos.R;


public class ReceiptPopup extends Dialog implements View.OnClickListener {

	private String mBody2 = "";

	private OnEventOkListener mOnEventPopupListener;
	private OnEventCancelListener mOnEventCancelListener;
	BluetoothService mService = null;



	LinearLayout ll_reciept;
	RelativeLayout rl_reciept;

	private Context mContext;
	NumberFormat formatter = new DecimalFormat("#0");


	TextView tv_comp_name;
	TextView tv_comp_num;
	TextView tv_comp_ceo_name;
	TextView tv_comp_phone;
	TextView tv_comp_address;
	TextView tv_tid_num;
	TextView tv_serical_num;
	TextView tv_pay_date;
	TextView tv_card_num;
	TextView tv_confirm_num;
	TextView tv_agency_num;
	TextView tv_card_type;
	TextView tv_card_pay_type;
	TextView tv_paper_num;
	TextView tv_price;
	TextView tv_tax;
	TextView tv_total_price;
	TextView tv_title;


	TextView tv_card_type_n;
	TextView tv_paper_num_n;
	TextView tv_agency_num_n;


	Button bt_sms;
	Button bt_print;
	Button bt_notice_confirm;


	HashMap<String, String> getHash = new HashMap<>();

	public ReceiptPopup(Context context, HashMap<String, String> getHash, BluetoothService service, OnEventOkListener onEventPopupListener) {
		super(context, R.style.Theme_AppCompat_Light);

		mContext = context;


		mService = service;
		this.getHash = getHash;

		StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
		StrictMode.setVmPolicy(builder.build());
		builder.detectFileUriExposure();

		Iterator<String> iteratorTemp = getHash.keySet().iterator();


		while (iteratorTemp.hasNext())
		{
			String strKey = iteratorTemp.next();
			Log.e("Jonathan", "getHash key :: " + strKey + " value :: " + getHash.get(strKey));
		}


		ll_reciept = (LinearLayout)findViewById(R.id.ll_reciept);
//		ll_reciept.setDrawingCacheEnabled(true);
		rl_reciept = (RelativeLayout)findViewById(R.id.rl_reciept);


//		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.receipt_popup);
		mOnEventPopupListener = onEventPopupListener;
//		trantype value :: card_cancel
//		trantype value :: card

		tv_title = (TextView)findViewById(R.id.tv_title);
		tv_comp_name = (TextView)findViewById(R.id.tv_comp_name);
		tv_comp_num = (TextView)findViewById(R.id.tv_comp_num);
		tv_comp_ceo_name = (TextView)findViewById(R.id.tv_comp_ceo_name);
		tv_comp_phone = (TextView)findViewById(R.id.tv_comp_phone);
		tv_comp_address = (TextView)findViewById(R.id.tv_comp_address);
		tv_tid_num = (TextView)findViewById(R.id.tv_tid_num);
		tv_serical_num = (TextView)findViewById(R.id.tv_serical_num);
		tv_pay_date = (TextView)findViewById(R.id.tv_pay_date);
		tv_card_num = (TextView)findViewById(R.id.tv_card_num);
		tv_confirm_num = (TextView)findViewById(R.id.tv_confirm_num);
		tv_agency_num = (TextView)findViewById(R.id.tv_agency_num);
		tv_card_type = (TextView)findViewById(R.id.tv_card_type);
		tv_card_pay_type = (TextView)findViewById(R.id.tv_card_pay_type);
		tv_paper_num = (TextView)findViewById(R.id.tv_paper_num);
		tv_price = (TextView)findViewById(R.id.tv_price);
		tv_tax = (TextView)findViewById(R.id.tv_tax);
		tv_total_price = (TextView)findViewById(R.id.tv_total_price);

		tv_card_type_n = (TextView)findViewById(R.id.tv_card_type_n);
		tv_paper_num_n = (TextView)findViewById(R.id.tv_paper_num_n);
		tv_agency_num_n = (TextView)findViewById(R.id.tv_agency_num_n);
		bt_sms = (Button)findViewById(R.id.bt_sms);
		bt_sms.setOnClickListener(this);
		bt_sms.setVisibility(View.INVISIBLE);

		bt_print = (Button)findViewById(R.id.bt_print);
		bt_print.setOnClickListener(this);




		if(getHash.containsKey("ONLY"))
		{
			if("Y".equals(getHash.get("ONLY")))
			{
				bt_sms.setVisibility(View.VISIBLE);
			}
		}



		if(getHash.get("outmessage").toString().contains("정상승인"))
		{
			tv_title.setText("IC신용승인");
			tv_price.setText(getHash.get("amount").toString());
			tv_tax.setText(getHash.get("surtax").toString());
			tv_total_price.setText(getHash.get("totalamount").toString());
		}
		else if(getHash.get("outmessage").toString().contains("정상취소"))
		{
			tv_title.setText("IC신용취소");
			tv_price.setText("-"+getHash.get("amount").toString());
			tv_tax.setText("-"+getHash.get("surtax").toString());
			tv_total_price.setText("-"+getHash.get("totalamount").toString());
		}


		if(getHash.containsKey("shopName"))
		{
			tv_comp_name.setText(getHash.get("shopName").toString());
		}
		else
		{
			tv_comp_name.setText("");
		}

		if(getHash.containsKey("businessno"))
		{
			tv_comp_num.setText(getHash.get("businessno").toString());
		}
		else
		{
			tv_comp_num.setText("");
		}

		if(getHash.containsKey("shopOwnerName"))
		{
			tv_comp_ceo_name.setText(getHash.get("shopOwnerName").toString());
		}
		else
		{
			tv_comp_ceo_name.setText("");
		}

		if(getHash.containsKey("shopTelNo"))
		{
			tv_comp_phone.setText(getHash.get("shopTelNo").toString());
		}
		else
		{
			tv_comp_phone.setText("");
		}

		if(getHash.containsKey("shopAddress"))
		{
			tv_comp_address.setText(getHash.get("shopAddress").toString());
		}
		else
		{
			tv_comp_address.setText("");
		}


		if(getHash.containsKey("catid"))
		{
			tv_tid_num.setText(getHash.get("catid").toString());
		}
		else
		{
			tv_tid_num.setText("");
		}


		if(getHash.containsKey("tranno"))
		{
			tv_serical_num.setText(getHash.get("tranno").toString());
		}
		else
		{
			tv_serical_num.setText("");
		}

		if(getHash.containsKey("approvaldate"))
		{
			tv_pay_date.setText(getHash.get("approvaldate").toString());
		}
		else
		{
			tv_pay_date.setText("");
		}


		if(getHash.containsKey("cardno"))
		{
			tv_card_num.setText(getHash.get("cardno").toString());
		}
		else
		{
			tv_card_num.setText("");
		}


		if(getHash.containsKey("approvalno"))
		{
			tv_confirm_num.setText(getHash.get("approvalno").toString());
		}
		else
		{
			tv_confirm_num.setText("");
		}


		if(getHash.containsKey("merchantno"))
		{
			tv_agency_num.setText(getHash.get("merchantno").toString());
		}
		else
		{
			tv_agency_num.setText("");
		}


		if(getHash.containsKey("acquiername"))
		{
			tv_card_type.setText(getHash.get("acquiername").toString());
		}
		else
		{
			tv_card_type.setText("");
		}


		if(getHash.containsKey("tranuniqe"))
		{
			tv_paper_num.setText(getHash.get("tranuniqe").toString());
		}
		else
		{
			tv_paper_num.setText("");
		}



		if(getHash.get("trantype").toString().equals("cash"))
		{
			tv_title.setText("현금 매출 전표");
			tv_card_type_n.setText("현금(소득공제)");
			tv_paper_num_n.setVisibility(View.GONE);
			tv_agency_num_n.setVisibility(View.GONE);
			tv_card_pay_type.setVisibility(View.GONE);


			if(getHash.containsKey("cashtype"))
			{
				if("1".equals(getHash.get("cashtype").toString()))
				{
					tv_card_type_n.setText("현금(지출증빙)");
				}
				else if("2".equals(getHash.get("cashtype").toString()))
				{
					tv_card_type_n.setText("현금(자진발급)");
				}
			}
		}


//		tv_card_pay_type.setText(getHash.get("acquiername").toString());






		bt_notice_confirm = (Button)findViewById(R.id.bt_notice_confirm);


		bt_notice_confirm.setOnClickListener(this);


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


				dismiss();

				break;


			case R.id.bt_print:


				String msg = "";

				byte[] cmd = new byte[3];
				cmd[0] = 0x1b;
				cmd[1] = 0x21;





				if(getHash.containsKey("disp_order_no"))
				{
					if(getHash.containsKey("ONLY"))
					{
						if("Y".equals(getHash.get("ONLY")))
						{
//							cmd[2] |= 0x10;
//							mService.write(cmd);
//							mService.sendMessage("주문번호 : " + getHash.get("disp_order_no") + "\n\n", "EUC-KR");


							SendDataByte(PrinterCommand.POS_Print_Text("주문번호 : " + getHash.get("disp_order_no") + "\n\n", Define.KOREAN, 0, 0, 0, 0));
							SendDataByte(Command.LF);


						}
					}
					else
					{
//						cmd[2] |= 0x10;
//						mService.write(cmd);
//						mService.sendMessage("주문번호 : " + getHash.get("disp_order_no") + "\n\n", "EUC-KR");

						SendDataByte(PrinterCommand.POS_Print_Text("주문번호 : " + getHash.get("disp_order_no") + "\n\n", Define.KOREAN, 0, 0, 0, 0));
						SendDataByte(Command.LF);

					}
				}



				cmd[2] &= 0xEF;
				mService.write(cmd);


				msg += "[고객용]                   \n";
				msg += "------------------------------\n";





				if(getHash.get("trantype").toString().equals("cash"))
				{
					//현금 영수증
					//현금(소득공제)
					msg += "*********현금 매출 전표********\n";
					msg += "         현금(소득공제)         \n";

					if(getHash.containsKey("cashtype"))
					{
						if("1".equals(getHash.get("cashtype").toString()))
						{
							//현금(지출증빙) 영수증
							msg += "         현금(지출증빙)         \n";
						}
						else if("2".equals(getHash.get("cashtype").toString()))
						{
							//현금(자진발급) 영수증
							msg += "         현금(자진발급)         \n";

						}
					}
				}
				else if(getHash.get("trantype").toString().equals("card"))
				{
					//카드 결제 영수증
					if(getHash.get("outmessage").toString().contains("정상승인"))
					{
						msg += "*********IC신용승인********\n";

					}
					//카드 취소 영수증
					else if(getHash.get("outmessage").toString().contains("정상취소"))
					{
						msg += "*********IC신용취소********\n";
					}

				}


				msg += getHash.get("shopName") + "    " + getHash.get("businessno") + "\n";


				if(getHash.containsKey("shopOwnerName"))
				{
					msg += getHash.get("shopOwnerName").toString();
				}
				else
				{
					msg += "-대표자명-";

				}


				msg += "   ";


				if(getHash.containsKey("shopTelNo"))
				{
					msg += getHash.get("shopTelNo").toString();
				}
				else
				{
					msg += "-전화번호-";
				}

				msg += "\n";


				if(getHash.containsKey("shopAddress"))
				{
					msg += getHash.get("shopAddress").toString();
				}
				else
				{
					msg += "-사업자 주소-";
				}

				msg += "\n";
				msg += "------------------------------\n";


				if(getHash.containsKey("catid"))
				{
					msg += "단말기번호 : " + getHash.get("catid").toString();
				}
				else
				{
					msg += "-";
				}

				msg += "\n";


				if(getHash.containsKey("tranno"))
				{
					msg += "일련번호 : " + getHash.get("tranno").toString();
				}
				else
				{
					msg += "-";
				}


				msg += "\n";


				if(getHash.containsKey("approvaldate"))
				{
					msg += "거래일시 : " + getHash.get("approvaldate").toString();
				}
				else
				{
					msg += "-";
				}

				msg += "\n";

				if(getHash.containsKey("cardno"))
				{
					msg += "카드번호 : " + getHash.get("cardno").toString();
				}
				else
				{
					msg += "-";
				}

				msg += "\n";

				if(getHash.containsKey("approvalno"))
				{
					msg += "승인번호 : " + getHash.get("approvalno").toString();
				}
				else
				{
					msg += "-";
				}

				msg += "\n";


				if(getHash.get("trantype").toString().equals("card"))
				{
					if(getHash.containsKey("merchantno"))
					{
						msg += "가맹번호 : " + getHash.get("merchantno").toString();
					}
					else
					{
						msg += "-";
					}

					msg += "\n";

					if(getHash.containsKey("acquiername"))
					{
						msg += "카드종류 : " + getHash.get("acquiername").toString();
					}
					else
					{
						msg += "-";
					}

					msg += "\n";



					if(getHash.containsKey("tranuniqe"))
					{
						msg += "전표번호 : " + getHash.get("tranuniqe").toString();
					}
					else
					{
						msg += "-";
					}


				}


				msg += "\n";
				msg += "------------------------------\n";



				if(getHash.get("outmessage").toString().contains("정상승인"))
				{
					msg += "부가세 물품가액 : " + getHash.get("amount").toString() + "원";
					msg += "\n";
					msg += "부가가치세 : " + getHash.get("surtax").toString() + "원";;
					msg += "\n";
					msg += "합계금액 : " + getHash.get("totalamount").toString() + "원";;
					msg += "\n";
				}
				else if(getHash.get("outmessage").toString().contains("정상취소"))
				{
					msg += "부가세 물품가액 : " + "-" + getHash.get("amount").toString() + "원";
					msg += "\n";
					msg += "부가가치세 : " + "-" + getHash.get("surtax").toString()+ "원";
					msg += "\n";
					msg += "합계금액 : " + "-" + getHash.get("totalamount").toString()+ "원";
					msg += "\n";
				}

				msg += "\n";
				msg += "\n";





				SendDataByte(PrinterCommand.POS_Print_Text(msg, Define.KOREAN, 0, 0, 0, 0));
				SendDataByte(Command.LF);

//				mService.sendMessage(msg, "EUC-KR");
//				mService.stop();


				break;


			case R.id.bt_sms:


//				View v1 = ll_reciept.getRootView();
//				v1.buildDrawingCache();
//				v1.setDrawingCacheEnabled(true);
//
//				Bitmap saveBitmap = v1.getDrawingCache();
//				Bitmap saveBitmap = Bitmap.createBitmap(rl_reciept.getWidth(), rl_reciept.getHeight(), Bitmap.Config.ARGB_8888);
//				Canvas c = new Canvas(saveBitmap);
//				ll_reciept.draw(c);
//				imageView.setImageBitmap(screenshot);


				View v1 = this.getWindow().getDecorView().getRootView();

				v1.setDrawingCacheEnabled(true);
				Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
				v1.setDrawingCacheEnabled(false);



				try
				{

					String path = Environment.getExternalStorageDirectory()+"/GOFOODA/";
					String name = "CAPTURE_GOFOODA.png";



					//폴더 생성
					File dir = makeDirectory(path);
					File imageFile = makeFile(dir, (path+name));

//					String mPath = "/data/data/com.rohit.test/test.jpg";
//					File imageFile = new File(mPath);

					FileOutputStream outputStream = new FileOutputStream(imageFile);
					int quality = 100;
					bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
					outputStream.flush();
					outputStream.close();

					Intent intent = new Intent(Intent.ACTION_SEND);
					intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(imageFile));
					intent.setType("image/png");
					getContext().startActivity(intent);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}



				break;




		}


	}



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




	private void SendDataByte(byte[] data) {

		if (mService.getState() != BluetoothService.STATE_CONNECTED) {
			Toast.makeText(mContext, "블루투스 프린터를 연결하십시오.", Toast.LENGTH_SHORT)
					.show();
			return;
		}
		mService.write(data);
	}





}
