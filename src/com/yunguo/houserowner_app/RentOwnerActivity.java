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
	private SelectPicPopupWindow menuWindow; // 自定义的头像编辑弹出框
	
	private String picPath = "";
	private Uri photoUri;
	/** 使用照相机拍照获取图片 */
	public static final int SELECT_PIC_BY_TACK_PHOTO = 1;
	/** 使用相册中的图片 */
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
		//添加点击事件
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
	 * 输入校验
	 */
	public void InputCheckout(){
		form = new Form();
		
		NotEmptyValidator notNull= new NotEmptyValidator(this);	//非空验证
		RegExpValidator IdCarIs = new RegExpValidator(this);
		IdCarIs.setPattern("(\\d{14}[0-9a-zA-Z])|(\\d{17}[0-9a-zA-Z])");	//身份证验证
		PhoneValidator phoneValidator= new PhoneValidator(this); 
		
		Validate telValidate1 = new Validate(person_housename);
		telValidate1.addValidator(notNull);//非空验证
		
		Validate telValidate2 = new Validate(person_houseid);
		telValidate2.addValidator(notNull);//非空验证
		
		Validate telValidate3 = new Validate(person_housecardid);
		telValidate3.addValidator(notNull);//非空验证
		
		Validate telValidate4 = new Validate(person_begintime);
		telValidate4.addValidator(notNull);//非空验证
		
		Validate telValidate5 = new Validate(person_endtime);
		telValidate5.addValidator(notNull);//非空验证
		
		Validate telValidate6 = new Validate(person_call);
		telValidate6.addValidator(notNull);//非空验证
		telValidate6.addValidator(phoneValidator);//电话
		
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
					// 最后一个false表示不显示日期，如果要显示日期，最后参数可以是true或者不用输入
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
					// 最后一个false表示不显示日期，如果要显示日期，最后参数可以是true或者不用输入
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
						Toast.makeText(getApplicationContext(), "请上传头像!", Toast.LENGTH_SHORT).show();
					}
				}else{
					Toast.makeText(getApplicationContext(), "输入有误额", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		getdoorid.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				person_housecardid.setText("");
				person_housecardid.setHint("获取中……");
				new Thread(thread).start();//获取卡号
			}
		});
		
		picImg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 从页面底部弹出一个窗体，选择拍照还是从相册选择已有图片
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
	
	
	//为弹出窗口实现监听类  
		private OnClickListener itemsOnClick = new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 隐藏弹出窗口
				menuWindow.dismiss();
				
				switch (v.getId()) {
				case R.id.takePhotoBtn:// 拍照
					takePhoto();
					break;
				case R.id.pickPhotoBtn:// 相册选择图片
					pickPhoto();
					break;
				case R.id.cancelBtn:// 取消
					break;
				default:
					break;
				}
			}
		}; 
		
		
		
		/**
		 * 拍照获取图片
		 */
		private void takePhoto() {
			// 执行拍照前，应该先判断SD卡是否存在
			String SDState = Environment.getExternalStorageState();
			if (SDState.equals(Environment.MEDIA_MOUNTED)) {

				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				/***
				 * 需要说明一下，以下操作使用照相机拍照，拍照后的图片会存放在相册中的 
				 * 这里使用的这种方式有一个好处就是获取的图片是拍照后的原图
				 * 如果不使用ContentValues存放照片路径的话，拍照后获取的图片为缩略图不清晰
				 */
				ContentValues values = new ContentValues();
				photoUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
				intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, photoUri);
				startActivityForResult(intent, SELECT_PIC_BY_TACK_PHOTO);
			} else {
				Toast.makeText(this, "内存卡不存在", Toast.LENGTH_LONG).show();
			}
		}

		/***
		 * 从相册中取图片
		 */
		private void pickPhoto() {
			Intent intent = new Intent();
			// 如果要限制上传到服务器的图片类型时可以直接写如："image/jpeg 、 image/png等的类型"
			intent.setType("image/*");
			intent.setAction(Intent.ACTION_GET_CONTENT);
			startActivityForResult(intent, SELECT_PIC_BY_PICK_PHOTO);
		}
	
		
		
		@Override
		protected void onActivityResult(int requestCode, int resultCode, Intent data) {
			// 点击取消按钮
			if(resultCode == RESULT_CANCELED){
				return;
			}
			
			// 可以使用同一个方法，这里分开写为了防止以后扩展不同的需求
			switch (requestCode) {
			case SELECT_PIC_BY_PICK_PHOTO:// 如果是直接从相册获取
				doPhoto(requestCode, data);
				break;
			case SELECT_PIC_BY_TACK_PHOTO:// 如果是调用相机拍照时
				doPhoto(requestCode, data);
				break;
			}
			super.onActivityResult(requestCode, resultCode, data);
		}
		
		
		/**
		 * 选择图片后，获取图片的路径
		 * 
		 * @param requestCode
		 * @param data
		 */
		private void doPhoto(int requestCode, Intent data) {
			
			// 从相册取图片，有些手机有异常情况，请注意
			if (requestCode == SELECT_PIC_BY_PICK_PHOTO) {
				if (data == null) {
					Toast.makeText(this, "选择图片文件出错", Toast.LENGTH_LONG).show();
					return;
				}
				photoUri = data.getData();
				if (photoUri == null) {
					Toast.makeText(this, "选择图片文件出错", Toast.LENGTH_LONG).show();
					return;
				}
			}
			
			String[] pojo = { MediaColumns.DATA };
			Cursor cursor = mContext.getContentResolver().query(photoUri, pojo, null, null, null);
			if (cursor != null) {
				int columnIndex = cursor.getColumnIndexOrThrow(pojo[0]);
				cursor.moveToFirst();
				picPath = cursor.getString(columnIndex);
				
				// 4.0以上的版本会自动关闭 (4.0--14;; 4.0.3--15)
				if (Integer.parseInt(Build.VERSION.SDK) < 14) {
					cursor.close();
				}
			}
			
			// 如果图片符合要求将其上传到服务器
			if (picPath != null && !picPath.equals("")) {
				BitmapFactory.Options option = new BitmapFactory.Options();
				// 压缩图片:表示缩略图大小为原始图片大小的几分之一，1为原图
				option.inSampleSize = 4;
				// 根据图片的SDCard路径读出Bitmap
				Bitmap bm = BitmapFactory.decodeFile(picPath, option);
				// 显示在图片控件上
				picImg.setImageBitmap(bm);
				/**
				 * 保存图片路径
				 */
				PitureBean pitureBean = PitureBean.savafile();
				pitureBean.setUserImage_path(picPath);
				pitureBean.setUserImage_name(pitureBean.getIdCardNo());
				
			} else {
				Toast.makeText(this, "选择图片文件不正确", Toast.LENGTH_LONG).show();
			}

		}
		
		
	private Handler handler = new Handler(){
		@Override
		public void handleMessage(android.os.Message msg) {
			
			switch (msg.what) {
			case 0:
				Toast.makeText(getApplicationContext(), "获取失败", Toast.LENGTH_SHORT).show();
				break;
			case 1:
				person_housecardid.setText((String)msg.obj);
				Toast.makeText(getApplicationContext(), "获取成功", Toast.LENGTH_SHORT).show();
				break;
			case 2:
				person_housecardid.setHint((String)msg.obj);
				Toast.makeText(getApplicationContext(), "获取失败", Toast.LENGTH_SHORT).show();
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
					message.obj = "获取失败！";
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
