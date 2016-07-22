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

public class MyHouseCardPowerExpandableListAdapter extends BaseAdapter{

		private List<Map<String,String>> list = new ArrayList<Map<String,String>>();
	    private Context context;
	    private Map<String,String> map;
	    private  ViewHolder viewHolder = null;

	    public MyHouseCardPowerExpandableListAdapter(List<Map<String,String>> list,Context context){
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
	            convertView = LayoutInflater.from(context).inflate(R.layout.cardpowermessge_adapter, null);
	            viewHolder =new ViewHolder();
	            convertView.setTag(viewHolder);
	        } else {
	            viewHolder = (ViewHolder) convertView.getTag();
	        }
	        map = list.get(position);
	        
	        viewHolder.CardNo = (TextView) convertView.findViewById(R.id.powerCardNo);
	        viewHolder.BeginTime = (TextView) convertView.findViewById(R.id.powerBeginTime);
	        viewHolder.EndTime = (TextView) convertView.findViewById(R.id.powerEndTime);
	        viewHolder.Power = (TextView) convertView.findViewById(R.id.powertext);
	        
	        viewHolder.CardNo.setText("卡号:"+map.get("CardNo"));
	        viewHolder.BeginTime.setText("开始时间:   "+map.get("BeginTime"));
	        viewHolder.EndTime.setText("到期时间:   "+map.get("EndTime"));
	        viewHolder.Power.setText("权限:   "+map.get("CriPri"));
	        
	        
	        return convertView;
	    }
	    class ViewHolder{
	        TextView CardNo,BeginTime,EndTime,Power;
	    }

	}
