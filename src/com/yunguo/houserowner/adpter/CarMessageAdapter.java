package com.yunguo.houserowner.adpter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.yunguo.houserowner_app.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class CarMessageAdapter extends BaseAdapter{

		private List<Map<String,String>> list = new ArrayList<Map<String,String>>();
	    private Context context;
	    private Map<String,String> map;
	    private  ViewHolder viewHolder = null;

	    public CarMessageAdapter(List<Map<String,String>> list,Context context){
	        this.list = list;
	        this.context = context;
	    }

	    @Override
	    public int getCount() {
	        return list.size();
	    }

	    @Override
	    public Object getItem(int position) {
	        return list.get(position);
	    }

	    @Override
	    public long getItemId(int position) {
	        return position;
	    }

	    @Override
	    public View getView(int position, View convertView, ViewGroup parent) {
	       
	        if (convertView == null) {
	            convertView = LayoutInflater.from(context).inflate(R.layout.carmessge_adapter, null);
	            viewHolder =new ViewHolder();
	            convertView.setTag(viewHolder);
	        } else {
	            viewHolder = (ViewHolder) convertView.getTag();
	        }
	        map = list.get(position);
	        
	        viewHolder.CardNo = (TextView) convertView.findViewById(R.id.CardNo);
	        viewHolder.BeginTime = (TextView) convertView.findViewById(R.id.BeginTime);
	        viewHolder.EndTime = (TextView) convertView.findViewById(R.id.EndTime);
	        
	        viewHolder.CardNo.setText("卡号:   "+map.get("CardNo"));
	        viewHolder.BeginTime.setText("开始时间:   "+map.get("BeginTime"));
	        viewHolder.EndTime.setText("到期时间:   "+map.get("EndTime"));
	        
	        
	        return convertView;
	    }
	    class ViewHolder{
	        TextView CardNo,BeginTime,EndTime;
	    }

	}
