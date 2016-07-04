package com.yunguo.houserowner.adpter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.yunguo.Util.HTTPUtil;
import com.yunguo.houserowner_app.R;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AddPermissionAdapter extends BaseAdapter{

		private List<Map<String,String>> list = new ArrayList<Map<String,String>>();
	    private Context context;
	    private Map<String,String> map;
	    private  ViewHolder viewHolder = null;

	    public AddPermissionAdapter(List<Map<String,String>> list,Context context){
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
	            convertView = LayoutInflater.from(context).inflate(R.layout.addpermisson_adapter, null);
	            viewHolder =new ViewHolder();
	            convertView.setTag(viewHolder);
	        } else {
	            viewHolder = (ViewHolder) convertView.getTag();
	        }
	        map = list.get(position);
	        
	        viewHolder.SerialName = (TextView) convertView.findViewById(R.id.SerialName);
	        viewHolder.opendoorlinear = (LinearLayout) convertView.findViewById(R.id.opendoorlinear);
	        
	        viewHolder.SerialName.setText(map.get("SerialName"));
	        
	        return convertView;
	    }
	    class ViewHolder{
	        TextView SerialName;
	        LinearLayout opendoorlinear;
	    }
	}
