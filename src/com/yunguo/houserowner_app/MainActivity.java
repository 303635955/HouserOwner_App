package com.yunguo.houserowner_app;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.yunguo.Util.HTTPUtil;
import com.yunguo.fragment.HouseInfoFragment;
import com.yunguo.fragment.MainFragment;
import com.yunguo.fragment.PersonInfoFragment;
import com.yunguo.houserowner.adpter.ViewPagerAdpter;

public class MainActivity extends FragmentActivity {
	private ViewPager mPager;
	private ViewPagerAdpter myadpter;
    private List<Fragment> fragmentList;
    public final static int num = 3 ; 
    
    private static HTTPUtil httppost =new HTTPUtil();
    Fragment homeFragment;
    Fragment personFragment;
    Fragment sortFragment;
    public static String value ;
    
    /** 管理fragment */
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private RadioGroup radioGroup;
    Bundle bundle1 = new Bundle();
    static String list;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Intent intent = getIntent();  
	    //从Intent当中根据key取得value  
	    value = intent.getStringExtra("testIntent");
	    Toast.makeText(getApplicationContext(), value, Toast.LENGTH_SHORT).show();
		initView();
	}
	
	public void initView(){
		fragmentManager = getSupportFragmentManager();
        radioGroup = (RadioGroup)findViewById(R.id.radioGroup1);
        ((RadioButton)radioGroup.findViewById(R.id.radio0)).setChecked(true);
        
        transaction = fragmentManager.beginTransaction();
        Fragment fragment = new MainFragment();
        transaction.replace(R.id.fragment_viewpager, fragment);
        transaction.commit();
		
		radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
	            @Override
	            public void onCheckedChanged(RadioGroup group, int checkedId) {
	            	switch (checkedId) {
					case R.id.radio0:
						transaction = fragmentManager.beginTransaction();
		                Fragment homeFragment = new MainFragment();
		                transaction.replace(R.id.fragment_viewpager, homeFragment);
		                transaction.commit();
						break;
					case R.id.radio1:
						if(null == sortFragment){//可以避免切换的时候重复创建
							sortFragment = new HouseInfoFragment();
				        }
						transaction = fragmentManager.beginTransaction();
		                Bundle data = new Bundle();
		                data.putString("TEXT",value);
		                sortFragment.setArguments(data);
		                transaction.replace(R.id.fragment_viewpager, sortFragment);
		                transaction.commit();
						break;
					case R.id.radio2:
						if(null == personFragment){//可以避免切换的时候重复创建
							personFragment = new PersonInfoFragment();
				        }
						transaction = fragmentManager.beginTransaction();
		                Bundle data2 = new Bundle();
		                data2.putString("TEXT",value);
		                personFragment.setArguments(data2);
		                transaction.replace(R.id.fragment_viewpager, personFragment);
		                transaction.commit();
						break;
	            	}
	                
	            }
	        });
	}
	
	public Handler handler2 = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				Toast.makeText(getApplicationContext(), "数据传输失败", Toast.LENGTH_SHORT).show();
				break;
			case 1:
				Intent intent = new Intent(getApplicationContext(),MainActivity.class);
				startActivity(intent);
				break;
			}
		};
	};

	public static Thread thread2 = new Thread() {
		@Override
		public void run() {
			String url = "http://192.168.1.147:8118/HouseMobileApp/HouseListView";
			Map<String, String> loginmap = new HashMap<String, String>();
			loginmap.put("ownerId", value);
			JSONObject js= new JSONObject(loginmap);
			String str = js.toString();
			String res = httppost.PostStringToUrl(url,str);
			try {
				JSONObject josn = new JSONObject(res);
				Object object = josn.get("houses");
				list = object.toString();
			} catch (JSONException e) {
				e.printStackTrace();
			}
		};
	};
}
