package ae.ewatheq.list.helper;

import ae.ewatheq.utils.Constants;
import ae.ewatheq.external.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;



public class DrawerAdapter extends BaseAdapter {
	private String[] menuTitles;
	
	private Activity a;
	private int selectedItem = 0;
	public DrawerAdapter(Activity activity,String[] menuTitles, int selectedPosition) {
		this.a=activity;
		selectedItem = selectedPosition;
		
		this.menuTitles = menuTitles;
	

	}
	public void setSelectedItem(int position)
	{
		this.selectedItem = position;
		notifyDataSetChanged();
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
		View view= layout.inflate(R.layout.lout_drawer_list_item,parent,false);
		

		TextView tvMenuItemName = (TextView) view.findViewById(R.id.tv_menu_item);
		ImageView ivMenuItem = (ImageView) view.findViewById(R.id.iv_menu_item);
		
		
		tvMenuItemName.setText(menuTitles[position]);
		if (position==selectedItem)
		{
			ivMenuItem.setImageResource(Constants.MENU_IC_SELECTED[position]);
			tvMenuItemName.setTextColor(a.getResources().getColor(R.color.ewatheq_color_primary));
		}
		else
		{
			ivMenuItem.setImageResource(Constants.MENU_IC[position]);
			tvMenuItemName.setTextColor(a.getResources().getColor(R.color.default_grey_dark));						
		}
	
		return view;
		
	}


}