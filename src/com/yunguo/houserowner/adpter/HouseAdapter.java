package com.yunguo.houserowner.adpter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.yunguo.Bean.HouseBean;
import com.yunguo.houserowner_app.R;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

public class HouseAdapter extends BaseAdapter {

	private List<HouseBean> list = new ArrayList<HouseBean>();
	private Context context;
	private ViewHolder viewHolder = null;
	private LayoutInflater mInflater;

	public HouseAdapter(List<HouseBean> data, Context context) {
		this.list = data;
		this.context = context;
		mInflater = LayoutInflater.from(context);
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
			convertView = LayoutInflater.from(context).inflate(
					R.layout.house_main_adpter, null);
			viewHolder = new ViewHolder();

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.house = (TextView) convertView
				.findViewById(R.id.houser_name);
		viewHolder.house_adress = (TextView) convertView
				.findViewById(R.id.houser_adress);

		viewHolder.house.setText(list.get(position).getHouseName());
		viewHolder.house_adress.setText(list.get(position).getHouseAdress());
		return convertView;
	}

	class ViewHolder {
		TextView house, house_adress;
	}

}
