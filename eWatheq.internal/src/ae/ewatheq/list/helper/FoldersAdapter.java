package ae.ewatheq.list.helper;



import java.util.List;




import ae.ewatheq.FilesActivity;
import ae.ewatheq.internal.R;
import ae.ewatheq.models.eWatheqFolder;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;




public class FoldersAdapter extends  BaseAdapter {
	private LayoutInflater inflater; 
	private Context context ;
	private List<eWatheqFolder> folderList ;
	private int selectedFolderPosition;


	private boolean isTablet;

	public FoldersAdapter(Context aContext,List<eWatheqFolder> list, int selectedFolderPosition) {
		context = aContext;
		folderList =  list;
		isTablet = context.getResources().getBoolean(R.bool.isTablet);
		inflater = LayoutInflater.from(this.context);
		this.selectedFolderPosition = selectedFolderPosition;
	}
	public int getCount() {
		return folderList.size();
	}
	public eWatheqFolder getItem(int position) {
		return folderList.get(position);
	}
	public void setSelectedFolder(int selectedFolderPosition) {
		this.selectedFolderPosition = selectedFolderPosition;
	}
	public void updateFolder(eWatheqFolder folder) {
		if (folder != null)
		{
			for (int i = 0 ; i<folderList.size() ; i++)
			{
				if (folder.CategoryID.equalsIgnoreCase(folderList.get(i).CategoryID))
				{
					folderList.set(i, folder);
					break;
				}
			}
		}
	}
	
	public long getItemId(int position) {
		return position;
	}
	public View getView(int position, View convertView, ViewGroup parent) {
		eWatheqFolder folder = getItem(position);
		View view = null;
		if (convertView != null) {
			view = convertView;
		} else {
			view = inflater.inflate(R.layout.lout_folder_cell, parent,false);
		}
		TextView folderTitle = (TextView) view.findViewById(R.id.tv_folder_title);
		folderTitle.setTag(folder);
		folderTitle.setOnClickListener(changeTitleOnClickListner);
		/*folderTitle.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				((HomeActivity)context).showFolderTitleChangeDialog(folder);
			}
		});*/
		RelativeLayout folderCell = (RelativeLayout) view.findViewById(R.id.rl_lv_folder_cell);
		TextView folderNof = (TextView) view.findViewById(R.id.tv_folder_nof);
		folderTitle.setText(folder.CategoryName);
		folderNof.setText(folder.NoOfFiles);
		if (isTablet){
			if (position == selectedFolderPosition)
			{
				RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)folderCell.getLayoutParams();
				params.setMargins(20, 0, 0, 0);
			}
			else 
			{
				RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)folderCell.getLayoutParams();
				params.setMargins(0, 0, 20, 0);
			}
		}
		folderCell.setBackgroundResource(getFolderBackgroundResourceId(position));
		return view;
	}
	private OnClickListener changeTitleOnClickListner = new OnClickListener() {
		@Override
		public void onClick(View v) {
			eWatheqFolder folder = (eWatheqFolder) v.getTag();
			((FilesActivity)context).showFolderTitleChangeDialog(folder);
		}
	};
	public static int getFolderBackgroundResourceId(int position )
	{
		int resId = R.drawable.ic_folder_0;
		if (position == 0)
		{
			resId = R.drawable.ic_folder_0;
		}
		else if (position == 1)
		{
			resId = R.drawable.ic_folder_1;
		}
		else if (position == 2)
		{
			resId = R.drawable.ic_folder_2;
		}
		else if (position == 3)
		{
			resId = R.drawable.ic_folder_3;
		}
		else if (position == 4)
		{
			resId = R.drawable.ic_folder_4;
		}
		else if (position == 5)
		{
			resId = R.drawable.ic_folder_5;
		}
		else if (position == 6)
		{
			resId = R.drawable.ic_folder_6;
		}
		return resId;
	}
	public static int getFolderTopHeaderTitleBackgroundResourceId(int position )
	{
		int resId = R.drawable.bg_selected_folder_title_0;
		if (position == 0)
		{
			resId = R.drawable.bg_selected_folder_title_0;
		}
		else if (position == 1)
		{
			resId = R.drawable.bg_selected_folder_title_1;
		}
		else if (position == 2)
		{
			resId = R.drawable.bg_selected_folder_title_2;
		}
		else if (position == 3)
		{
			resId = R.drawable.bg_selected_folder_title_3;
		}
		else if (position == 4)
		{
			resId = R.drawable.bg_selected_folder_title_4;
		}
		else if (position == 5)
		{
			resId = R.drawable.bg_selected_folder_title_5;
		}
		else if (position == 6)
		{
			resId = R.drawable.bg_selected_folder_title_6;
		}
		return resId;
	}
	public static int getFolderTopHeaderButtonBackgroundResourceId(int position )
	{
		int resId = R.drawable.bg_selected_folder_btn_lout_0;
		if (position == 0)
		{
			resId = R.drawable.bg_selected_folder_btn_lout_0;
		}
		else if (position == 1)
		{
			resId = R.drawable.bg_selected_folder_btn_lout_1;
		}
		else if (position == 2)
		{
			resId = R.drawable.bg_selected_folder_btn_lout_2;
		}
		else if (position == 3)
		{
			resId = R.drawable.bg_selected_folder_btn_lout_3;
		}
		else if (position == 4)
		{
			resId = R.drawable.bg_selected_folder_btn_lout_4;
		}
		else if (position == 5)
		{
			resId = R.drawable.bg_selected_folder_btn_lout_5;
		}
		else if (position == 6)
		{
			resId = R.drawable.bg_selected_folder_btn_lout_6;
		}
		return resId;
	}
}