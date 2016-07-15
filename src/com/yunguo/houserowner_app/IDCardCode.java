package com.yunguo.houserowner_app;

import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.IDCardReader.idcardreader.lib.IDCardRecog;
import com.IDCardReader.utility.FileUtility;
import com.IDCardReader.utility.ImageProcess;
import com.yunguo.Bean.PitureBean;

public class IDCardCode extends Activity implements OnClickListener {

	public static final int REQUEST_CODE_CAMERA = 1001;
	public static final int REQUEST_CODE_back_CAMERA = 1003;
	public static final int REQUEST_CODE_GALLERY = 1002;

	public static final int MAX_IDCARD_SIZE = 1920 * 1080;
	private Button btn_recog_fornt, btn_recog_back;
	private Button btn_jump;
	private static String SD_Path, DirPath;
	private static String Front_OriPath = "", Back_OriPath= "", OriPath = "";
	private ImageView iv_img_front, iv_img_back;
	private TextView tv_name, tv_nation, tv_sex, tv_birth;
	private TextView tv_address, tv_number, tv_office, tv_date;
	private Bitmap picBitmapOri, picBitmapOri_back;
	private boolean isImage;
	private boolean frontisImage = false, backisImage = false;

	private ProgressDialog pd;

	private IDCardRecog idCardRecog;
	private String apixKey = "7ab2bd9799ef46c77da7f98be0a4f447";

	private Map<String, String> list = new HashMap<String, String>();
	String time;
	private Button backbut;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_idcard_photo_recog);
		
		PitureBean.savafile().setObjectNull(); 	//清空缓存
		
		findview();
		// 设置点击事件
		setclick();
	}

	public void findview() {
		btn_recog_fornt = (Button) this.findViewById(R.id.btn_recog_front);
		btn_recog_fornt.setOnClickListener(this);
		btn_recog_back = (Button) this.findViewById(R.id.btn_recog_back);
		btn_recog_back.setOnClickListener(this);

		btn_jump = (Button) findViewById(R.id.btn_jump);
		btn_jump.setOnClickListener(this);

		tv_name = (TextView) this.findViewById(R.id.tv_name);
		tv_nation = (TextView) this.findViewById(R.id.tv_nation);
		tv_sex = (TextView) this.findViewById(R.id.tv_sex);
		tv_birth = (TextView) this.findViewById(R.id.tv_birth);
		tv_address = (TextView) this.findViewById(R.id.tv_address);
		tv_number = (TextView) this.findViewById(R.id.tv_number);

		tv_office = (TextView) this.findViewById(R.id.tv_office);
		tv_date = (TextView) this.findViewById(R.id.tv_date);

		iv_img_front = (ImageView) this.findViewById(R.id.iv_pic);
		iv_img_back = (ImageView) this.findViewById(R.id.iv_pic_back);

		backbut = (Button) this.findViewById(R.id.backbut);

	}

	public void setclick() {
		iv_img_front.setClickable(true);
		iv_img_back.setClickable(true);
		iv_img_front.setOnClickListener(this);
		iv_img_back.setOnClickListener(this);
		backbut.setOnClickListener(this);

		SD_Path = Environment.getExternalStorageDirectory().getAbsolutePath();
		DirPath = SD_Path + File.separator + "APIX" + File.separator
				+ "IDCardReader";
		Front_OriPath = DirPath + File.separator + time + "_1.jpg";
		Back_OriPath = DirPath + File.separator + time + "_2.jpg";
		
		// OriPath = DirPath + File.separator + "oriphoto.jpg";
		long currentTime = System.currentTimeMillis();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date(currentTime);
		time = formatter.format(date);

		isImage = true;

		File dir = new File(DirPath);
		dir.mkdirs();

		idCardRecog = new IDCardRecog(apixKey, uiHandler);
	}

	private void showFrontResult(Bundle bundle) {
		Toast.makeText(getApplicationContext(), "识别成功", Toast.LENGTH_SHORT)
				.show();
		if (isImage == false) {
			frontisImage = false;
		}
		tv_name.setText(bundle.getString("name"));
		tv_sex.setText(bundle.getString("sex"));
		tv_nation.setText(bundle.getString("nation"));
		tv_birth.setText(bundle.getString("birth"));
		tv_address.setText(bundle.getString("address"));
		tv_number.setText(bundle.getString("number"));

		list.put("Name", bundle.getString("name"));
		list.put("Sex", bundle.getString("sex"));
		list.put("Nation", bundle.getString("nation"));
		list.put("Birthday", bundle.getString("birth"));
		list.put("IdCardLocation", bundle.getString("address"));
		list.put("IdCardNo", bundle.getString("number"));

		iv_img_back.setClickable(true);
		iv_img_back.setOnClickListener(this);
	}

	private void showBackResult(Bundle bundle) {
		Toast.makeText(getApplicationContext(), "识别成功", Toast.LENGTH_SHORT)
				.show();
		if (isImage == false) {
			backisImage = false;
		}
		tv_office.setText(bundle.getString("office"));
		tv_date.setText(bundle.getString("date"));

		list.put("IdCardAuthority", bundle.getString("office"));
		list.put("date", bundle.getString("date"));

	}

	private void showError(Bundle bundle) {
		String message = bundle.getString("message");
		if (message == null) {
			message = "未能正确识别，请重新拍摄";
		}
		Toast.makeText(getApplication(), message, Toast.LENGTH_SHORT).show();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		String sdStatus = Environment.getExternalStorageState();

		try {
			if (resultCode == Activity.RESULT_OK) {
				if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) {
					return;
				}
				if (requestCode == REQUEST_CODE_CAMERA) {
					
					if (picBitmapOri != null && !picBitmapOri.isRecycled())
						picBitmapOri.recycle();
					picBitmapOri = ImageProcess.extractThumbNail(Front_OriPath,
							MAX_IDCARD_SIZE)
							.copy(Bitmap.Config.ARGB_8888, true);
					if (picBitmapOri != null) {
						isImage = true;
						iv_img_front.setImageBitmap(picBitmapOri);
						iv_img_front.setBackgroundColor(Color.TRANSPARENT);
					}
					frontisImage = true;
				}
				if (requestCode == REQUEST_CODE_back_CAMERA) {
					
					if (picBitmapOri_back != null
							&& !picBitmapOri_back.isRecycled())
						picBitmapOri_back.recycle();
					picBitmapOri_back = ImageProcess.extractThumbNail(
							Back_OriPath, MAX_IDCARD_SIZE).copy(
							Bitmap.Config.ARGB_8888, true);
					if (picBitmapOri_back != null) {
						isImage = true;
						iv_img_back.setImageBitmap(picBitmapOri_back);
						iv_img_back.setBackgroundColor(Color.TRANSPARENT);
					}
					backisImage = true;
				}

				if (requestCode == REQUEST_CODE_GALLERY) {
					if (frontisImage != true) {
						Uri selectUri = data.getData();
						String[] proj = { MediaStore.Images.Media.DATA };
						Cursor cursor = managedQuery(selectUri, proj, null,
								null, null);
						int column_index = cursor
								.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
						cursor.moveToFirst();
						String selectedPath = cursor.getString(column_index);
						FileUtility.copyFile(selectedPath, Front_OriPath);
						if (picBitmapOri != null && !picBitmapOri.isRecycled())
							picBitmapOri.recycle();
						picBitmapOri = ImageProcess.extractThumbNail(
								Front_OriPath, MAX_IDCARD_SIZE).copy(
								Bitmap.Config.ARGB_8888, true);
						if (picBitmapOri != null) {
							isImage = true;
							iv_img_front.setImageBitmap(picBitmapOri);
							iv_img_front.setBackgroundColor(Color.TRANSPARENT);
						}
						frontisImage = true;
					} else {
						Uri selectUri = data.getData();
						String[] proj = { MediaStore.Images.Media.DATA };
						Cursor cursor = managedQuery(selectUri, proj, null,
								null, null);
						int column_index = cursor
								.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
						cursor.moveToFirst();
						String selectedPath = cursor.getString(column_index);
						FileUtility.copyFile(selectedPath, Back_OriPath);
						if (picBitmapOri_back != null
								&& !picBitmapOri_back.isRecycled())
							picBitmapOri_back.recycle();
						picBitmapOri_back = ImageProcess.extractThumbNail(
								Back_OriPath, MAX_IDCARD_SIZE).copy(
								Bitmap.Config.ARGB_8888, true);
						if (picBitmapOri_back != null) {
							isImage = true;
							iv_img_back.setImageBitmap(picBitmapOri_back);
							iv_img_back.setBackgroundColor(Color.TRANSPARENT);
						}
						backisImage = true;
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressLint("HandlerLeak")
	private Handler uiHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			Bundle data = msg.getData();
			if (pd != null) {
				pd.dismiss();
			}
			switch (msg.what) {
			// 取图线程
			case 0:
				showError(data);
				break;
			case 1:
				showFrontResult(data);
				break;
			case 2:
				showBackResult(data);
				break;
			default:
				break;
			}
		}
	};

	/**
	 * 序列化map供Bundle传递map使用 Created on 13-12-9.
	 */
	/*
	 * public class SerializableMap implements Serializable {
	 * 
	 * private Map<String, String> map;
	 * 
	 * public Map<String, String> getMap() { return map; }
	 * 
	 * public void setMap(Map<String, String> map) { this.map = map; } }
	 */

	public static String transMapToString(Map map) {
		JSONObject obj = new JSONObject(map);
		String date = "param=" + obj.toString();
		return date;
	}

	/**
	 * 
	 * @param 对回传的数据进行处理后传给页面
	 *            yzq 2016.6
	 */
	@SuppressWarnings("unused")
	public void dataControl(String sur, String sex, String nation) {
		String[] str = { "汉", "蒙古", "回", "藏", "维吾尔", "苗", "彝", "壮", "布依", "朝鲜",
				"满", "侗", "瑶", "白", "土家", "哈尼", "哈萨克", "傣", "黎", "傈僳", "佤",
				"畲", "高山", "拉祜", "水", "东乡", "纳西", "景颇", "柯尔克孜", "土", "达斡尔",
				"仫佬", "羌", "布朗", "撒拉", "毛南", "仡佬", "锡伯", "阿昌", "普米", "塔吉克",
				"怒", "乌孜别克", "俄罗斯", "鄂温克", "德昂", "保安", "裕固", "京", "塔塔尔", "独龙",
				"鄂伦春", "赫哲", "门巴", "珞巴族", "基诺", "穿青", "外国血统", "其它" };
		String begin, end, newbegin, newend;
		if (sur == null) {
			list.put("IdCardBeginTime", "");
			list.put("IdCardEndTime", "");
		} else {
			begin = sur.substring(0, sur.indexOf("-"));
			newbegin = begin.substring(0, 4) + "-" + begin.substring(4, 6)
					+ "-" + begin.substring(6, begin.length());
			end = sur.substring(sur.indexOf("-") + 1, sur.length());
			newend = end.substring(0, 4) + "-" + end.substring(4, 6) + "-"
					+ end.substring(6, end.length());
			list.put("IdCardBeginTime", newbegin);
			list.put("IdCardEndTime", newend);
		}
		if (sex == null) {
			list.put("Sex", "1");
		} else {
			if (sex.equals("男")) {
				list.put("Sex", "1");
			} else if (sex.equals("女")) {
				list.put("Sex", "2");
			}
		}
		if (nation == null) {
			list.put("Nation", "1");
		} else {
			for (int i = 0; i < str.length; i++) {
				if (str[i].equals(nation)) {
					if (i == 57) {
						list.put("Nation", "59");
						break;
					}
					if (i == 58) {
						list.put("Nation", "98");
						break;
					}
					if (i == 59) {
						list.put("Nation", "99");
						break;
					}
					list.put("Nation", "" + (++i));
				}
			}
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		/*
		 * case R.id.btnPhoto: if (Environment.getExternalStorageState().equals(
		 * Environment.MEDIA_MOUNTED)) { Intent localIntent = new Intent();
		 * localIntent.setType("image/*");
		 * localIntent.setAction("android.intent.action.GET_CONTENT"); Intent
		 * localIntent2 = Intent.createChooser(localIntent, "选择图片");
		 * startActivityForResult(localIntent2, REQUEST_CODE_GALLERY); } else {
		 * Toast.makeText(getApplication(), "找不到SD卡！！",
		 * Toast.LENGTH_SHORT).show(); } break;
		 */
		case R.id.btn_recog_front:
		
			if (frontisImage == false) {
				Toast.makeText(getApplication(), "请拍照或从相册添加图片",
						Toast.LENGTH_SHORT).show();
				return;
			}
			pd = ProgressDialog.show(IDCardCode.this, "", "识别中...", true);
			idCardRecog.recogFront(Front_OriPath);
			break;
		case R.id.btn_recog_back:
			if (backisImage == false) {
				Toast.makeText(getApplication(), "请拍照或从相册添加图片",
						Toast.LENGTH_SHORT).show();
				return;
			}
			if (list.size() > 1 && list.size() < 9) {
				pd = ProgressDialog.show(IDCardCode.this, "", "识别中...", true);
				idCardRecog.recogBack(Back_OriPath);
			} else {
				Toast.makeText(getApplication(), "请先识别正面", Toast.LENGTH_SHORT)
						.show();
			}
			break;
		case R.id.btn_jump:
			if (list.size() >= 1 && list.size() <= 8) {
				PitureBean pitureBean = PitureBean.savafile();
			/*	pitureBean.setImgfile_path(Front_OriPath);
				pitureBean.setBack_imgfile_path(Back_OriPath);
				pitureBean.setBack_romoteName(time + "_1.jpg");
				pitureBean.setRomoteName(time + "_2.jpg");
				pitureBean.setIdCardNo(tv_number.getText().toString());*/
				if(frontisImage){
					pitureBean.setImgfile_path(Front_OriPath);
					pitureBean.setRomoteName(time + "_2.jpg");
				}else{
					pitureBean.setImgfile_path(Front_OriPath);
					pitureBean.setRomoteName("");
				}
				if(backisImage){
					pitureBean.setBack_imgfile_path(Back_OriPath);
					pitureBean.setBack_romoteName(time + "_1.jpg");
				}else{
					pitureBean.setBack_imgfile_path("");
					pitureBean.setBack_romoteName("");
				}
				list.put("Name", tv_name.getText().toString());
				list.put("Sex", tv_sex.getText().toString());
				list.put("Nation", tv_nation.getText().toString());
				list.put("Birthday", tv_birth.getText().toString());
				list.put("IdCardLocation", tv_address.getText().toString());
				list.put("IdCardNo", tv_number.getText().toString());
				list.put("date", tv_date.getText().toString());
				list.put("IdCardAuthority", tv_office.getText().toString());
				Intent intent = new Intent(getApplicationContext(),
						RegistRentPerson.class);
				Bundle extras = new Bundle();
				extras.putSerializable("LoginedIpInfoS", (Serializable) list);// /java.util.HashMap.
				intent.putExtras(extras);
				Front_OriPath= "";
				Back_OriPath = "";
				startActivity(intent);
			}
			if (list.isEmpty()) {
				PitureBean pitureBean = PitureBean.savafile();
				if(frontisImage){
					pitureBean.setImgfile_path(Front_OriPath);
					pitureBean.setRomoteName(time + "_2.jpg");
				}else{
					pitureBean.setImgfile_path(Front_OriPath);
					pitureBean.setRomoteName("");
				}
				if(backisImage){
					pitureBean.setBack_imgfile_path(Back_OriPath);
					pitureBean.setBack_romoteName(time + "_1.jpg");
				}else{
					pitureBean.setBack_imgfile_path("");
					pitureBean.setBack_romoteName("");
				}
				pitureBean.setIdCardNo(tv_number.getText().toString());
				Intent intent = new Intent(getApplicationContext(),
						RegistRentPerson.class);
				startActivity(intent);
			}
			break;
		case R.id.iv_pic:
			frontisImage = false;
			if (Environment.getExternalStorageState().equals(
					Environment.MEDIA_MOUNTED)) {
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				intent.putExtra(MediaStore.EXTRA_OUTPUT,
						Uri.fromFile(new File(Front_OriPath)));
				startActivityForResult(intent, REQUEST_CODE_CAMERA);
			} else {
				Toast.makeText(getApplication(), "找不到SD卡！！", Toast.LENGTH_SHORT)
						.show();
			}
			break;
		case R.id.iv_pic_back:
			backisImage = false;
			if (Environment.getExternalStorageState().equals(
					Environment.MEDIA_MOUNTED)) {
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				intent.putExtra(MediaStore.EXTRA_OUTPUT,
						Uri.fromFile(new File(Back_OriPath)));
				startActivityForResult(intent, REQUEST_CODE_back_CAMERA);
			} else {
				Toast.makeText(getApplication(), "找不到SD卡！！", Toast.LENGTH_SHORT)
						.show();
			}
			break;
		case R.id.backbut:
			finish();
			break;
		}

	}
}
