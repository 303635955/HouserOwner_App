package com.yunguo.houserowner_app;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
  
public class ShowOneImage extends Activity{
	private ImageView imageView,backimg;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_one_image);
		imageView=(ImageView) findViewById(R.id.image);
		backimg = (ImageView) findViewById(R.id.backimg);
		
		
		ImageLoader.getInstance().loadImage(getIntent().getStringExtra("url"), new SimpleImageLoadingListener(){
			public void onLoadingComplete(String imageUri, android.view.View view, android.graphics.Bitmap loadedImage) {
				imageView.setImageBitmap(loadedImage);
			};
		public void onLoadingFailed(String imageUri, android.view.View view, com.nostra13.universalimageloader.core.assist.FailReason failReason) {
			Toast.makeText(ShowOneImage.this,"此用户没有上传图片额", Toast.LENGTH_LONG).show();
		};
		@Override
	    public void onLoadingStarted(String imageUri, View view) {
			
	    }
	    @Override
	    public void onLoadingCancelled(String imageUri, View view) {
	    }
		}
		);
		
		backimg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	
	}

}
