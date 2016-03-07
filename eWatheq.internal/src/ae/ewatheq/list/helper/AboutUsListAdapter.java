package ae.ewatheq.list.helper;

import ae.ewatheq.utils.Constants;
import ae.ewatheq.internal.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;



public class AboutUsListAdapter extends BaseAdapter {
	private String[] menuTitles;
	
	private Activity a;
	
	public AboutUsListAdapter(Activity activity,String[] menuTitles) {
		this.a=activity;
		
		
		this.menuTitles = menuTitles;
	

	}
	
	

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return menuTitles.length;///////return size of list
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;///// dont return null here
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;/////////return position as itemID
	}

	@SuppressLint("ViewHolder") @Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater layout = a.getLayoutInflater();
		View view= layout.inflate(R.layout.lout_about_us_list_item,parent,false);
		

		TextView tvMenuItemName = (TextView) view.findViewById(R.id.tv_about_us_list_item);
		//ImageView ivMenuItem = (ImageView) view.findViewById(R.id.iv_menu_item);
		
		
		tvMenuItemName.setText(menuTitles[position]);
		
		
	
		return view;
		
	}


}