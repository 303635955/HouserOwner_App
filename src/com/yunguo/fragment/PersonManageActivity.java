package com.yunguo.fragment;

import java.util.Map;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.yunguo.Bean.SetUpRent;
import com.yunguo.ImageLoderUtils.CircalurImage;
import com.yunguo.houserowner_app.AddRentCardActivity;
import com.yunguo.houserowner_app.CardMessageActivity;
import com.yunguo.houserowner_app.R;
import com.yunguo.houserowner_app.RenewalActivity;
import com.yunguo.houserowner_app.ShowOneImage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class PersonManageActivity extends Activity{
	
	private TextView DoorId,UserName,Age,Sex,Birthday,Tel,inTime,enTime;
	private ImageView backimg;
	private Map<String,String> map = null;
	private Button openpermissions;
	private Button cardreport;
	private Button registerCard;
	private Button myhouse;
	private Button mytenant;
	private Button addcard,Frozen,Relieve;
	
	private CircalurImage imgloder;
	private  DisplayImageOptions options;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.personmanage_activity);
		
		/**
		 * ��ȡ����
		 */
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		map = (Map<String, String>) bundle.get("person");
		
		findView();
		setData();
		setOnClick();
	}
	
	public void findView(){
		DoorId = (TextView) findViewById(R.id.DoorId);
		UserName = (TextView) findViewById(R.id.UserName);
		Age = (TextView) findViewById(R.id.Age);
		Sex = (TextView) findViewById(R.id.Sex);
		Birthday = (TextView) findViewById(R.id.Birthday);
		Tel = (TextView) findViewById(R.id.Tel);
		inTime = (TextView) findViewById(R.id.inTime);
		enTime = (TextView) findViewById(R.id.enTime);
		openpermissions = (Button) findViewById(R.id.openpermissions);
		
		cardreport = (Button) findViewById(R.id.cardreport);
		registerCard = (Button) findViewById(R.id.registerCard);
		myhouse = (Button) findViewById(R.id.myhouse);
		mytenant= (Button) findViewById(R.id.mytenant);
		Frozen = (Button) findViewById(R.id.Cardice);
		addcard = (Button) findViewById(R.id.addtenantcard);
		Relieve = (Button) findViewById(R.id.relivecard);
		
		
		imgloder = (CircalurImage) findViewById(R.id.imgloder);
		options = new DisplayImageOptions.Builder()
		.showStubImage(R.drawable.ic_stub)
		.showImageForEmptyUri(R.drawable.ic_empty)
		.showImageOnFail(R.drawable.ic_error)
		.cacheInMemory()
		.cacheOnDisc()
		.displayer(new RoundedBitmapDisplayer(1))
		.build();
		
		backimg = (ImageView) findViewById(R.id.backimg);
	}
	
	public void setData(){
		DoorId.setText(map.get("DoorId"));
		String username = map.get("UserName");
		UserName.setText(username);
		
		String age = map.get("Sex");
		if(age.equals("1")){
			age = "��";
		}else{
			age = "Ů";
		}
		Age.setText(map.get("Age"));
		Sex.setText(age);
		Birthday.setText(map.get("Birthday"));
		Tel.setText(map.get("Tel"));
		inTime.setText(map.get("CheckInTime"));
		enTime.setText(map.get("CheckOutTime"));
		
		
		 ImageLoader.getInstance().displayImage("http://120.25.65.125:8118/PersonImage/"+map.get("IdCardNo")+"/"+map.get("IdCardNo")+".jpg",imgloder, options);
	}
	
	public void setOnClick(){
		backimg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		imgloder.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent();
				intent.putExtra("url","http://120.25.65.125:8118/PersonImage/"+map.get("IdCardNo")+"/"+map.get("IdCardNo")+".jpg");
				intent.setClass(PersonManageActivity.this, ShowOneImage.class);
				startActivity(intent);
			}
		});
		
		openpermissions.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(PersonManageActivity.this,CardMessageActivity.class);
				intent.putExtra("type","Ȩ��");
				intent.putExtra("UserId",map.get("UserId"));
				intent.putExtra("CardStatus","1");
				startActivity(intent);
			}
		});
		
		cardreport.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(PersonManageActivity.this,CardMessageActivity.class);
				intent.putExtra("type","��ʧ");
				intent.putExtra("UserId",map.get("UserId"));
				intent.putExtra("CardStatus","1");
				startActivity(intent);
			}
		});
		
		registerCard.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(PersonManageActivity.this,CardMessageActivity.class);
				intent.putExtra("type","����");
				intent.putExtra("UserId",map.get("UserId"));
				intent.putExtra("CardStatus","2");
				intent.putExtra("HouseId",SetUpRent.getSetUpRent().getHouseId());
				startActivity(intent);
			}
		});
		
		myhouse.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(PersonManageActivity.this,CardMessageActivity.class);
				intent.putExtra("type","����");
				intent.putExtra("HouseId",SetUpRent.getSetUpRent().getHouseId());
				intent.putExtra("CardStatus","1");
				intent.putExtra("UserId",map.get("UserId"));
				intent.putExtra("DoorId",map.get("DoorId"));
				intent.putExtra("enTime",map.get("CheckOutTime"));
				startActivity(intent);
			}
		});
		
		mytenant.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(PersonManageActivity.this,CardMessageActivity.class);
				intent.putExtra("type","ע��");
				intent.putExtra("CardStatus","1");
				intent.putExtra("UserId",map.get("UserId"));
				startActivity(intent);
			}
		});
		
		Frozen.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(PersonManageActivity.this,CardMessageActivity.class);
				intent.putExtra("type","����");
				intent.putExtra("CardStatus","1");
				intent.putExtra("UserId",map.get("UserId"));
				intent.putExtra("HouseId",SetUpRent.getSetUpRent().getHouseId());
				
				startActivity(intent);
			}
		});
		
		addcard.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(PersonManageActivity.this,AddRentCardActivity.class);
				intent.putExtra("UserId",map.get("UserId"));
				startActivity(intent);
			}
		});
		
		Relieve.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(PersonManageActivity.this,CardMessageActivity.class);
				intent.putExtra("type","�ⶳ");
				intent.putExtra("CardStatus","4");
				intent.putExtra("UserId",map.get("UserId"));
				intent.putExtra("HouseId",SetUpRent.getSetUpRent().getHouseId());
				startActivity(intent);
			}
		});
	}
}
