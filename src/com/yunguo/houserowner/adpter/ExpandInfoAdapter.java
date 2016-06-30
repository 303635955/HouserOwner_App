package com.yunguo.houserowner.adpter;

import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.ExpandableListContextMenuInfo;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunguo.houserowner.adpter.HouseAdapter.ViewHolder;
import com.yunguo.houserowner_app.R;

public class ExpandInfoAdapter extends BaseExpandableListAdapter {
	LayoutInflater mInflater;
	Activity activity;
	
	Context f;
	private ViewHolder viewHolder = null;
	
	public List<String> group;
	public List<List<String>> child;
	Bitmap mIcon1;
	public ExpandInfoAdapter(Activity a,List<String> group,List<List<String>> child) {
		activity = a;
		this.group = group;
		this.child = child;
		mIcon1 = BitmapFactory.decodeResource(activity.getResources(),R.drawable.media_tape);
		mInflater = LayoutInflater.from(activity);
	}

/*	public ExpandInfoAdapter(Context context,List<String> group,List<List<String>> child) {
		f = context;
		this.group = group;
		this.child = child;
		mInflater = LayoutInflater.from(context);
	}*/

	// ++++++++++++++++++++++++++++++++++++++++++++
	// child's stub
	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return child.get(groupPosition).get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return child.get(groupPosition).size();
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		// return
		// getChildViewStub(child.get(groupPosition).get(childPosition)
		// .toString());
		return getView(groupPosition, childPosition, convertView, parent);
	}

	public TextView getChildViewStub(String s) {
		// Layout parameters for the ExpandableListView
		AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
				ViewGroup.LayoutParams.FILL_PARENT, 64);
		TextView text = new TextView(activity);
		text.setLayoutParams(lp);
		text.setTextSize(40);
		text.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
		text.setPadding(36, 0, 0, 0);
		text.setText(s);
		return text;
	}

	public View getView(int groupPosition, int childPosition, View convertView,
			ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.houseinfo_expan_adpter, null);
			holder = new ViewHolder();
			holder.nametext = (TextView) convertView.findViewById(R.id.houseinfo_name_two);
			holder.icon = (ImageView) convertView.findViewById(R.id.icon);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.nametext.setText(child.get(groupPosition).get(childPosition));
		holder.icon.setImageBitmap(mIcon1);
		return convertView;
	}

	// ++++++++++++++++++++++++++++++++++++++++++++
	// group's stub
	@Override
	public Object getGroup(int groupPosition) {
		return group.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return group.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		return getGroupViewStub(getGroup(groupPosition).toString());
	}

	public TextView getGroupViewStub(String s) {
		// Layout parameters for the ExpandableListView
		AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
				ViewGroup.LayoutParams.FILL_PARENT,200);
		TextView text = new TextView(activity);
		text.setLayoutParams(lp);
		text.setTextSize(40);
		text.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
		text.setPadding(36, 0, 0, 0);
		text.setText(s);
		return text;
	}

	// Indicate whether Group is Expanded or Collapsed
	public void onGroupExpanded(int groupPosition) {
	}

	public void onGroupCollapsed(int groupPosition) {
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		menu.setHeaderTitle("Sample menu");
		menu.add(0, 0, 0, "menu1 order0");
		menu.add(0, 0, 1, "menu2 order1");
		menu.add(0, 1, 1, "menu3 item1");
		menu.add(1, 1, 1, "menu4 group1");
	}

	public boolean onContextItemSelected(MenuItem item) {
		/*
		 * Logs.i(tag, "GroupID" + item.getGroupId() + ", itemId :" +
		 * item.getItemId() + " order :" + item.getOrder());
		 */
		ExpandableListContextMenuInfo info = (ExpandableListContextMenuInfo) item
				.getMenuInfo();
		int type = ExpandableListView
				.getPackedPositionType(info.packedPosition);
		if (type == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
			String title = ((ViewHolder) info.targetView.getTag()).nametext.getText().toString();
			int groupPos = ExpandableListView
					.getPackedPositionGroup(info.packedPosition);
			int childPos = ExpandableListView
					.getPackedPositionChild(info.packedPosition);
			/*
			 * Toast.makeText( this, title + ": Child " + childPos +
			 * " clicked in group" + groupPos, Toast.LENGTH_SHORT).show();
			 */
			return true;
		} else if (type == ExpandableListView.PACKED_POSITION_TYPE_GROUP) {
			String titles = ((TextView) info.targetView).getText().toString();
			int groupPos = ExpandableListView.getPackedPositionGroup(info.packedPosition);
			return true;
		}
		return false;
	}
	
	class ViewHolder {
		TextView nametext;
		ImageView icon;
	}
}