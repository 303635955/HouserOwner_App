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
import android.graphics.drawable.LayerDrawable;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AddPermissionAdapter extends BaseAdapter{

		private List<Map<String,String>> list = new ArrayList<Map<String,String>>();
	    private Context context;
	    private Map<String,String> map;
	    private  ViewHolder viewHolder = null;
	    private boolean isChice[];
	    private int tmpnum = 0;

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
	    public View getView(final int position, View convertView, ViewGroup parent) {
	       
	        if (convertView == null) {
	            convertView = LayoutInflater.from(context).inflate(R.layout.addpermisson_adapter, null);
	            viewHolder =new ViewHolder();
	            convertView.setTag(viewHolder);
	        } else {
	            viewHolder = (ViewHolder) convertView.getTag();
	        }
	        map = list.get(position);
	        
	        tmpnum++;
	        if(tmpnum == list.size()+1){
	        	isChice = new boolean[list.size()];
	        	for (int i = 0; i < list.size(); i++) {
	        		isChice[i] = false;
				}
	        }
	        
	        viewHolder.SerialName = (TextView) convertView.findViewById(R.id.SerialName);
	        viewHolder.opendoorlinear = (LinearLayout) convertView.findViewById(R.id.opendoorlinear);
	        viewHolder.selecd = (ImageView) convertView.findViewById(R.id.selecd);
	        
	        if(tmpnum > list.size()){
	        	if(isChice[position]){
	        		viewHolder.selecd.setImageResource(R.drawable.choice_2_1);
	        	}else{
	        		viewHolder.selecd.setImageResource(R.drawable.choice_2);
	        	}
	        }
	        
	        viewHolder.SerialName.setText(map.get("SerialName"));
	        
	        return convertView;
	    }
	    class ViewHolder{
	        TextView SerialName;
	        LinearLayout opendoorlinear;
	        ImageView selecd;
	    }
	    
	    public void chiceState(int post)
	    {
	    	if(isChice[post]){
	    		isChice[post]=false;
	    	}else {
	    		isChice[post]=true;
			}
	    	this.notifyDataSetChanged();
	    }
	    
	    public boolean[] getchiceState(){
			return isChice;
	    }
	    
	}
