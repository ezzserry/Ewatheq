package ae.ewatheq.list.helper;

import java.util.ArrayList;

import ae.ewatheq.utils.MaskMediaUtils;
import ae.ewatheq.external.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class LanguageAdapter   extends BaseAdapter{



	private Context context;
	private String[] LanguageArray;
	ViewHolder holder;
	protected LayoutInflater fInflater;
	public LanguageAdapter(Context context, String[] LanguageArray)
	{
		this.context = context;
		this.LanguageArray = LanguageArray;
		//	check = new int[this.assignments.size()];
		if(context!=null){


			fInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}
	}
	@Override
	public int getCount() {
		return LanguageArray.length;
	}
	@Override
	public Object getItem(int i) {
		return LanguageArray[i];
	}
	@Override
	public long getItemId(int i) {
		return (long) i;
	}

	private static class ViewHolder {

		TextView languageName;

	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View lView = convertView;
		// if (lView == null) {
		//
		// lView = fInflater.inflate(R.layout.menu, parent, false);
		// }

		if (lView == null) {

			holder = new ViewHolder();

			lView = fInflater.inflate(R.layout.language_item,parent, false);
			holder.languageName = (TextView) lView
					.findViewById(R.id.language_name);
		
			
			
			
			//holder.languageName;
			lView.setTag(holder);
		} else {

			holder = (ViewHolder) lView.getTag();
		}








		holder.languageName.setText(LanguageArray[position]);
		

		return lView;
	}
}
