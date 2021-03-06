package com.yunguo.houserowner.adpter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.yunguo.ImageLoderUtils.CircalurImage;
import com.yunguo.houserowner_app.R;

public class MyPersonInfoExpandableListAdapter extends BaseExpandableListAdapter{

	private List<String> groups;
	private List<List<Map<String,String>>> child;
	private Context context;
	private  DisplayImageOptions options;
	
	public MyPersonInfoExpandableListAdapter(List<String> groups,List<List<Map<String,String>>> child,Context context){
		this.groups = groups;
		this.child = child;
		this.context = context;
		 options = new DisplayImageOptions.Builder()
			.showStubImage(R.drawable.ic_stub)
			.showImageForEmptyUri(R.drawable.ic_empty)
			.showImageOnFail(R.drawable.ic_error)
			.cacheInMemory()
			.cacheOnDisc()
			.displayer(new RoundedBitmapDisplayer(1))
			.build();
		
	}

	@Override
	public int getGroupCount() {
		return groups.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return child.get(groupPosition).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return  groups.get(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return child.get(groupPosition).get(childPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
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
		      R.layout.personinfo_group, null);
		    
		    groupHolder = new GroupHolder();
		    groupHolder.txt = (TextView) convertView.findViewById(R.id.HouseName);
		    // groupHolder.img = (ImageView) convertView
		    // .findViewById(R.id.img);
		    convertView.setTag(groupHolder);
		   } else {
		    groupHolder = (GroupHolder) convertView.getTag();
		   }
		   groupHolder.txt.setText("���䣺"+groups.get(groupPosition));
		   return convertView;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
			ItemHolder itemHolder = null;
		   if (convertView == null) {
			   convertView = LayoutInflater.from(context).inflate(
		      R.layout.personinfo_child, null);
		    itemHolder = new ItemHolder();
		    
		    itemHolder.UserName = (TextView) convertView.findViewById(R.id.UserName);
		    itemHolder.starttime = (TextView) convertView.findViewById(R.id.starttime);
		    itemHolder.endtime = (TextView) convertView.findViewById(R.id.endtime);
		    itemHolder.imgloder = (CircalurImage) convertView.findViewById(R.id.imgloder);
		    convertView.setTag(itemHolder);
		   } else {
		    itemHolder = (ItemHolder) convertView.getTag();
		   }
		   itemHolder.UserName.setText(child.get(groupPosition).get(childPosition).get("UserName")+"");
		   itemHolder.starttime.setText(child.get(groupPosition).get(childPosition).get("CheckInTime")+"");
		   itemHolder.endtime.setText(child.get(groupPosition).get(childPosition).get("CheckOutTime")+"");  
		   
		   
		   ImageLoader.getInstance().displayImage("http://120.25.65.125:8118/PersonImage/"+child.get(groupPosition).get(childPosition).get("IdCardNo")+"/"+child.get(groupPosition).get(childPosition).get("IdCardNo")+".jpg", itemHolder.imgloder, options);  
		
		   return convertView;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}
	
	class GroupHolder {
		  public TextView txt;
	 }

	class ItemHolder {
		  public TextView UserName,starttime,endtime;
		  public CircalurImage imgloder;
	 }
}