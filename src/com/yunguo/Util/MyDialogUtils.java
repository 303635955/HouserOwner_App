package com.yunguo.Util;

import com.yunguo.houserowner_app.R;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class MyDialogUtils extends Dialog{
	private static final int CHANGE_TITLE_WHAT = 1;
	private static final int MAX_SUFFIX_NUMBER = 3;
	private static final int CHNAGE_TITLE_DELAYMILLIS = 300;
	private static final char SUFFIX = '.';
	
	private ImageView loadimg;
	private TextView titletxt;
	private AnimationDrawable animaition;
	
	
	private Handler handler = new Handler() {
		private int num = 0;
		public void handleMessage(android.os.Message msg) {
		if (msg.what == CHANGE_TITLE_WHAT) {
		StringBuilder builder = new StringBuilder();
		if (num >= MAX_SUFFIX_NUMBER) {
		num = 0;
		}
		num++;
		for (int i = 0; i < num; i++) {
		builder.append(SUFFIX);
		}
		titletxt.setText("请求中"+builder.toString());
		if (isShowing()) {
		handler.sendEmptyMessageDelayed(CHANGE_TITLE_WHAT, CHNAGE_TITLE_DELAYMILLIS);
		}
		else {
		num = 0;
		}
		}
		};
		};

	public MyDialogUtils(Context context) {
		super(context);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE); 
		setContentView(R.layout.lodingopendoor_dialog);
		initfindView();
		initAnim();
	}
	
	public void initfindView(){
		loadimg = (ImageView) findViewById(R.id.loadimg);
		loadimg.setBackgroundResource(R.anim.gifload);
		titletxt = (TextView) findViewById(R.id.titletxt);
		
		
	}
	
	public void initAnim(){
		animaition = (AnimationDrawable) loadimg.getBackground();
		animaition.setOneShot(false);
	}
	
	@Override
	public void show() {
		animaition.start();
		handler.sendEmptyMessage(CHANGE_TITLE_WHAT);
		super.show();
	}
	
	@Override
	public void dismiss() {
		super.dismiss();
		animaition.stop();
	}
	
	@Override
	public void setTitle(CharSequence title) {
		super.setTitle(title);
		if (TextUtils.isEmpty(title)) {
			titletxt.setText("正在加载");
			}
			else {
			titletxt.setText(title);
			}
	}


}
