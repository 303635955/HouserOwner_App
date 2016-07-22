package com.yunguo.houserowner.adpter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;

import com.yunguo.Bean.SetUpRent;
import com.yunguo.houserowner_app.CardPowerMessageActivity;
import com.yunguo.houserowner_app.R;

public class MyHouseInfoExpandableListAdapter extends BaseExpandableListAdapter{
	
	private List<Map<String,String>> list;
	private Context context;
	public MyHouseInfoExpandableListAdapter(List<Map<String,String>> list,Context context){
		this.list = list;
		this.context = context;
	}

	@Override
	public int getGroupCount() {
		return list.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return  list.get(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return list.get(groupPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return groupPosition;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		GroupHolder groupHolder = null;
		   if (convertView == null) {
			   convertView = LayoutInflater.from(context).inflate(
		      R.layout.houseinfo_group, null);
		    
		    groupHolder = new GroupHolder();
		    groupHolder.txt = (TextView) convertView.findViewById(R.id.HouseName);
		    // groupHolder.img = (ImageView) convertView
		    // .findViewById(R.id.img);
		    convertView.setTag(groupHolder);
		   } else {
		    groupHolder = (GroupHolder) convertView.getTag();
		   }
		   groupHolder.txt.setText(list.get(groupPosition).get("HouseName")+"");
		   return convertView;
	}

	@Override
	public View getChildView(final int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		ItemHolder itemHolder = null;
		   if (convertView == null) {
			   convertView = LayoutInflater.from(context).inflate(
		      R.layout.houseinfo_child, null);
		    itemHolder = new ItemHolder();
		    
		    itemHolder.HouseName = (TextView) convertView.findViewById(R.id.HouseName);
		    itemHolder.Floor = (TextView) convertView.findViewById(R.id.Floor);
		    itemHolder.Number = (TextView) convertView.findViewById(R.id.Number);
		    itemHolder.Usage = (TextView) convertView.findViewById(R.id.Usage);
		    itemHolder.Householder = (TextView) convertView.findViewById(R.id.Householder);
		    itemHolder.Tel = (TextView) convertView.findViewById(R.id.Tel);
		    itemHolder.Address = (TextView) convertView.findViewById(R.id.Address);
		    itemHolder.break_apply = (Button) convertView.findViewById(R.id.Break_Apply);
		    itemHolder.power = (Button) convertView.findViewById(R.id.HousePower);
		    convertView.setTag(itemHolder);
		   } else {
		    itemHolder = (ItemHolder) convertView.getTag();
		   }
		   
		   itemHolder.HouseName.setText(list.get(groupPosition).get("HouseName")+"");
		   itemHolder.Floor.setText(list.get(groupPosition).get("Floor")+"");
		   itemHolder.Number.setText(list.get(groupPosition).get("Number")+"");
		   itemHolder.Usage.setText(list.get(groupPosition).get("Usage")+"");
		   itemHolder.Householder.setText(list.get(groupPosition).get("Householder")+"");
		   itemHolder.Tel.setText(list.get(groupPosition).get("Tel")+"");
		   itemHolder.Address.setText(list.get(groupPosition).get("Address")+"");
		   itemHolder.break_apply.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent3 = new Intent();
					intent3.setAction("RemoteBreakDoorActivity");
					SetUpRent.getSetUpRent().setHouseId(list.get(groupPosition).get("HouseId"));
					intent3.putExtra("type", "1");
					context.startActivity(intent3);
				}
			   });
			   itemHolder.power.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent intent3 = new Intent();
						intent3.setAction("RemoteBreakDoorActivity");
						SetUpRent.getSetUpRent().setHouseId(list.get(groupPosition).get("HouseId"));
						intent3.putExtra("type", "2");
						context.startActivity(intent3);
					}
				   });
		   return convertView;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return false;
	}
	
	class GroupHolder {
		  public TextView txt;
	 }

	class ItemHolder {
		  public TextView HouseId,HouseName,Floor,Number,Usage,Householder,Tel,Address;
		  public Button break_apply,power;
	 }
}