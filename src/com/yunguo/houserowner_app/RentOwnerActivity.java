package com.yunguo.houserowner_app;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.throrinstudio.android.common.libs.validator.Form;
import com.throrinstudio.android.common.libs.validator.Validate;
import com.throrinstudio.android.common.libs.validator.validator.NotEmptyValidator;
import com.throrinstudio.android.common.libs.validator.validator.PhoneValidator;
import com.throrinstudio.android.common.libs.validator.validator.RegExpValidator;
import com.yunguo.Bean.PitureBean;
import com.yunguo.Bean.SetUpRent;
import com.yunguo.Util.DoubleDatePickerDialog;
import com.yunguo.Util.FromUtil;
import com.yunguo.Util.HTTPUtil;
import com.yunguo.Util.SelectPicPopupWindow;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.provider.MediaStore.MediaColumns;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class RentOwnerActivity extends Activity {
	private Map<String, String> house = new HashMap<String, String>();
	private Context mContext;
	private EditText person_housename;
	private EditText person_houseid;
	private EditText person_housecardid;
	private EditText person_begintime;
	private EditText person_endtime;
	private EditText person_call;
	private Button btn,btn_back;
	private Button getdoorid;
	private ImageView picImg;
	private SelectPicPopupWindow menuWindow; // �Զ����ͷ��༭������
	
	private String picPath = "";
	private Uri photoUri;
	/** ʹ����������ջ�ȡͼƬ */
	public static final int SELECT_PIC_BY_TACK_PHOTO = 1;
	/** ʹ������е�ͼƬ */
	public static final int SELECT_PIC_BY_PICK_PHOTO = 2;

	String Sextext, nation;
	String[] mItems,mItems1; 
	
	
	
	private Form form;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.personinfo_view);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.layout_titlebar);
		mContext = RentOwnerActivity.this;
		findview();
		//��ӵ���¼�
		setclick();
	}

	public void findview() {
		person_housename = (EditText) findViewById(R.id.person_housename_text);
		person_houseid = (EditText) findViewById(R.id.person_houseid_text);
		person_housecardid = (EditText) findViewById(R.id.person_housedoorid_text);
		person_begintime = (EditText) findViewById(R.id.person_begintime_text);
		person_endtime = (EditText) findViewById(R.id.person_endtime_text);
		person_call = (EditText) findViewById(R.id.call);
		
		btn_back = (Button) findViewById(R.id.house_info_one);
		btn = (Button) findViewById(R.id.house_info_two);
		getdoorid = (Button) findViewById(R.id.getdoorid);
		picImg = (ImageView) findViewById(R.id.picImg);
	}

	/**
	 * ����У��
	 */
	public void InputCheckout(){
		form = new Form();
		
		NotEmptyValidator notNull= new NotEmptyValidator(this);	//�ǿ���֤
		RegExpValidator IdCarIs = new RegExpValidator(this);
		IdCarIs.setPattern("(\\d{14}[0-9a-zA-Z])|(\\d{17}[0-9a-zA-Z])");	//���֤��֤
		PhoneValidator phoneValidator= new PhoneValidator(this); 
		
		Validate telValidate1 = new Validate(person_housename);
		telValidate1.addValidator(notNull);//�ǿ���֤
		
		Validate telValidate2 = new Validate(person_houseid);
		telValidate2.addValidator(notNull);//�ǿ���֤
		
		Validate telValidate3 = new Validate(person_housecardid);
		telValidate3.addValidator(notNull);//�ǿ���֤
		
		Validate telValidate4 = new Validate(person_begintime);
		telValidate4.addValidator(notNull);//�ǿ���֤
		
		Validate telValidate5 = new Validate(person_endtime);
		telValidate5.addValidator(notNull);//�ǿ���֤
		
		Validate telValidate6 = new Validate(person_call);
		telValidate6.addValidator(notNull);//�ǿ���֤
		telValidate6.addValidator(phoneValidator);//�绰
		
		form.addValidates(telValidate1);
		form.addValidates(telValidate2);
		form.addValidates(telValidate3);
		form.addValidates(telValidate4);
		form.addValidates(telValidate5);
		form.addValidates(telValidate6);
	}

	public void setclick() {
		person_begintime.setOnFocusChangeListener(new OnFocusChangeListener() {
			Calendar c = Calendar.getInstance();
			@Override
			public void onFocusChange(View view, boolean hasFocus) {
				// TODO Auto-generated method stub
				if(hasFocus){
					// ���һ��false��ʾ����ʾ���ڣ����Ҫ��ʾ���ڣ�������������true���߲�������
					new DoubleDatePickerDialog(RentOwnerActivity.this, 0, new DoubleDatePickerDialog.OnDateSetListener() {
						@Override
						public void onDateSet(DatePicker startDatePicker, int startYear, int startMonthOfYear,
								int startDayOfMonth, DatePicker endDatePicker, int endYear, int endMonthOfYear,
								int endDayOfMonth) {
							String textstartString = String.format("%d-%d-%d", startYear,
									startMonthOfYear + 1, startDayOfMonth);
							person_begintime.setText(textstartString);
							String textendString = String.format("%d-%d-%d", endYear,
									endMonthOfYear + 1, endDayOfMonth);
							person_endtime.setText(textendString);
						}
					}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), false).show();
				}
			}
		});
		
		person_endtime.setOnFocusChangeListener(new OnFocusChangeListener() {
			Calendar c = Calendar.getInstance();
			@Override
			public void onFocusChange(View view, boolean hasFocus) {
				// TODO Auto-generated method stub
				if(hasFocus){
					// ���һ��false��ʾ����ʾ���ڣ����Ҫ��ʾ���ڣ�������������true���߲�������
					new DoubleDatePickerDialog(RentOwnerActivity.this, 0, new DoubleDatePickerDialog.OnDateSetListener() {
						@Override
						public void onDateSet(DatePicker startDatePicker, int startYear, int startMonthOfYear,
								int startDayOfMonth, DatePicker endDatePicker, int endYear, int endMonthOfYear,
								int endDayOfMonth) {
							String textstartString = String.format("%d-%d-%d", startYear,
									startMonthOfYear + 1, startDayOfMonth);
							person_begintime.setText(textstartString);
							String textendString = String.format("%d-%d-%d", endYear,
									endMonthOfYear + 1, endDayOfMonth);
							person_endtime.setText(textendString);
						}
					}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), false).show();
				}
			}
		});
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				InputCheckout();
				if(form.validate() && FromUtil.date(person_begintime.getText().toString(), person_endtime.getText().toString())){
					if(!picPath.equals("")){
					house.put("HouseName", person_housename.getText().toString());
					house.put("RoomNo", person_houseid.getText().toString());
					house.put("CardNoStr", person_housecardid.getText().toString());
					house.put("beginTime", person_begintime.getText().toString());
					house.put("endTime", person_endtime.getText().toString());
					house.put("TelNo", person_call.getText().toString());
					SetUpRent.getSetUpRent().setHousemap(house);
					Intent intent = new Intent(getApplicationContext(),
							RentOwnerOtherActivity.class);
					startActivity(intent);
					}else{
						Toast.makeText(getApplicationContext(), "���ϴ�ͷ��!", Toast.LENGTH_SHORT).show();
					}
				}else{
					Toast.makeText(getApplicationContext(), "���������", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		getdoorid.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				person_housecardid.setText("");
				person_housecardid.setHint("��ȡ�С���");
				new Thread(thread).start();//��ȡ����
			}
		});
		
		picImg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// ��ҳ��ײ�����һ�����壬ѡ�����ջ��Ǵ����ѡ������ͼƬ
				menuWindow = new SelectPicPopupWindow(mContext, itemsOnClick);  
				menuWindow.showAtLocation(findViewById(R.id.uploadLayout), 
						Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0); 
			}
		});
		
		btn_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
	
	
	//Ϊ��������ʵ�ּ�����  
		private OnClickListener itemsOnClick = new OnClickListener() {
			@Override
			public void onClick(View v) {
				// ���ص�������
				menuWindow.dismiss();
				
				switch (v.getId()) {
				case R.id.takePhotoBtn:// ����
					takePhoto();
					break;
				case R.id.pickPhotoBtn:// ���ѡ��ͼƬ
					pickPhoto();
					break;
				case R.id.cancelBtn:// ȡ��
					break;
				default:
					break;
				}
			}
		}; 
		
		
		
		/**
		 * ���ջ�ȡͼƬ
		 */
		private void takePhoto() {
			// ִ������ǰ��Ӧ�����ж�SD���Ƿ����
			String SDState = Environment.getExternalStorageState();
			if (SDState.equals(Environment.MEDIA_MOUNTED)) {

				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				/***
				 * ��Ҫ˵��һ�£����²���ʹ����������գ����պ��ͼƬ����������е� 
				 * ����ʹ�õ����ַ�ʽ��һ���ô����ǻ�ȡ��ͼƬ�����պ��ԭͼ
				 * �����ʹ��ContentValues�����Ƭ·���Ļ������պ��ȡ��ͼƬΪ����ͼ������
				 */
				ContentValues values = new ContentValues();
				photoUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
				intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, photoUri);
				startActivityForResult(intent, SELECT_PIC_BY_TACK_PHOTO);
			} else {
				Toast.makeText(this, "�ڴ濨������", Toast.LENGTH_LONG).show();
			}
		}

		/***
		 * �������ȡͼƬ
		 */
		private void pickPhoto() {
			Intent intent = new Intent();
			// ���Ҫ�����ϴ�����������ͼƬ����ʱ����ֱ��д�磺"image/jpeg �� image/png�ȵ�����"
			intent.setType("image/*");
			intent.setAction(Intent.ACTION_GET_CONTENT);
			startActivityForResult(intent, SELECT_PIC_BY_PICK_PHOTO);
		}
	
		
		
		@Override
		protected void onActivityResult(int requestCode, int resultCode, Intent data) {
			// ���ȡ����ť
			if(resultCode == RESULT_CANCELED){
				return;
			}
			
			// ����ʹ��ͬһ������������ֿ�дΪ�˷�ֹ�Ժ���չ��ͬ������
			switch (requestCode) {
			case SELECT_PIC_BY_PICK_PHOTO:// �����ֱ�Ӵ�����ȡ
				doPhoto(requestCode, data);
				break;
			case SELECT_PIC_BY_TACK_PHOTO:// ����ǵ����������ʱ
				doPhoto(requestCode, data);
				break;
			}
			super.onActivityResult(requestCode, resultCode, data);
		}
		
		
		/**
		 * ѡ��ͼƬ�󣬻�ȡͼƬ��·��
		 * 
		 * @param requestCode
		 * @param data
		 */
		private void doPhoto(int requestCode, Intent data) {
			
			// �����ȡͼƬ����Щ�ֻ����쳣�������ע��
			if (requestCode == SELECT_PIC_BY_PICK_PHOTO) {
				if (data == null) {
					Toast.makeText(this, "ѡ��ͼƬ�ļ�����", Toast.LENGTH_LONG).show();
					return;
				}
				photoUri = data.getData();
				if (photoUri == null) {
					Toast.makeText(this, "ѡ��ͼƬ�ļ�����", Toast.LENGTH_LONG).show();
					return;
				}
			}
			
			String[] pojo = { MediaColumns.DATA };
			Cursor cursor = mContext.getContentResolver().query(photoUri, pojo, null, null, null);
			if (cursor != null) {
				int columnIndex = cursor.getColumnIndexOrThrow(pojo[0]);
				cursor.moveToFirst();
				picPath = cursor.getString(columnIndex);
				
				// 4.0���ϵİ汾���Զ��ر� (4.0--14;; 4.0.3--15)
				if (Integer.parseInt(Build.VERSION.SDK) < 14) {
					cursor.close();
				}
			}
			
			// ���ͼƬ����Ҫ�����ϴ���������
			if (picPath != null && !picPath.equals("")) {
				BitmapFactory.Options option = new BitmapFactory.Options();
				// ѹ��ͼƬ:��ʾ����ͼ��СΪԭʼͼƬ��С�ļ���֮һ��1Ϊԭͼ
				option.inSampleSize = 4;
				// ����ͼƬ��SDCard·������Bitmap
				Bitmap bm = BitmapFactory.decodeFile(picPath, option);
				// ��ʾ��ͼƬ�ؼ���
				picImg.setImageBitmap(bm);
				/**
				 * ����ͼƬ·��
				 */
				PitureBean pitureBean = PitureBean.savafile();
				pitureBean.setUserImage_path(picPath);
				pitureBean.setUserImage_name(pitureBean.getIdCardNo());
				
			} else {
				Toast.makeText(this, "ѡ��ͼƬ�ļ�����ȷ", Toast.LENGTH_LONG).show();
			}

		}
		
		
	private Handler handler = new Handler(){
		@Override
		public void handleMessage(android.os.Message msg) {
			
			switch (msg.what) {
			case 0:
				Toast.makeText(getApplicationContext(), "��ȡʧ��", Toast.LENGTH_SHORT).show();
				break;
			case 1:
				person_housecardid.setText((String)msg.obj);
				Toast.makeText(getApplicationContext(), "��ȡ�ɹ�", Toast.LENGTH_SHORT).show();
				break;
			case 2:
				person_housecardid.setHint((String)msg.obj);
				Toast.makeText(getApplicationContext(), "��ȡʧ��", Toast.LENGTH_SHORT).show();
				break;
			}
			
		};
	};
	
	private Thread thread = new Thread(){
		@Override
		public void run() {
			PostCarNo();
		};
	};
	
	public void PostCarNo(){
		Message message = new Message();
		Map<String,String> map = new HashMap<String,String>();
		map.put("HouseId", SetUpRent.getSetUpRent().getHouseId());
		try {
			JSONObject js = new JSONObject(map.toString());
			try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			String res = HTTPUtil.PostStringToUrl("http://120.25.65.125:8118/HouseMobileApp/GetHouseLastSwipeCardNo", js.toString());
			JSONObject JsonObject = new JSONObject(res);
			String ret = JsonObject.get("ret")+"";
			if(ret.equals("0") || ret.equals("") || ret == null){
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				PostCarNo();
			}else if(ret.equals("1")){
				
				String CarNo = JsonObject.getString("CardNo");
				if(CarNo.equals("0")){
					message.what = 2;
					message.obj = "��ȡʧ�ܣ�";
					handler.sendMessage(message);
					return;
				}
				message.what = 1;
				message.obj = CarNo;
				handler.sendMessage(message);
			}
		} catch (JSONException e) {
			e.printStackTrace();
			PostCarNo();
		}
	}
}
