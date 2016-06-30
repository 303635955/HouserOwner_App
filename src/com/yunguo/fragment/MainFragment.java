package com.yunguo.fragment;

import java.io.File;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.VideoView;

import com.yunguo.houserowner_app.R;
import com.yunguo.houserowner_app.RegistRentPerson;

public class MainFragment extends Fragment {
	private View view;
	
	private Button regist;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.mediaplay, null);
		/*VideoView videoView = (VideoView) view.findViewById(R.id.videoView);
		// ��õ�path����:/storage/emulated/0/DCIM
		File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
		// ƴ������·��
		File f = new File(path, "/Camera/test.mp4");
		// ��ʱ��f.getAbsolutePath()=/storage/emulated/0/DCIM//\Camera/test.mp4
		videoView.setVideoPath(f.getAbsolutePath());
		// ��ʼ������Ƶ
		videoView.start();
		// VideiView�񽹵�
		// videoView.requestFocus();*/
		initview();
		return view;
	}
	
	
	public void  initview(){
		regist =(Button) view.findViewById(R.id.regist_rent);
		
		regist.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(),RegistRentPerson.class);
				startActivity(intent);
			}
		});
	}
	
}
