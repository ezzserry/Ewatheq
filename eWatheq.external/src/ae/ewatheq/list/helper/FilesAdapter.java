package ae.ewatheq.list.helper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import ae.ewatheq.external.R;
import ae.ewatheq.models.eWatheqFile;
import ae.ewatheq.utils.Constants;
import ae.ewatheq.utils.EWatheqUtils;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class FilesAdapter extends BaseAdapter{



	private LayoutInflater inflater = null;
	private int shelfHeight = 0,emptyHeight = 0;
	private List<eWatheqFile> filesList;
	private ArrayList<eWatheqFile> filesArrayList;
	private Activity activity;


	private DisplayImageOptions options;
	public static int SHOW_ALL_FILES = 1;
	public static int SHOW_PHOTOS_FILES = 2;
	public static int SHOW_DOCUMENTS_FILES = 3;

	public FilesAdapter(Context context,List<eWatheqFile> files) {
		this.inflater = LayoutInflater.from(context);
		this.activity =  (Activity) context;
		this.filesList = files;
		this.filesArrayList = new ArrayList<eWatheqFile>();
		this.filesArrayList.addAll(filesList);
		Bitmap bitmap = BitmapFactory.decodeResource(activity.getResources(), R.drawable.pdf_general_book_cover_view_default_03);

		this.shelfHeight = bitmap.getHeight();
		


		options = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.ic_image_loading_deafault)
		.showImageForEmptyUri(R.drawable.ic_image_loading_deafault)
		.showImageOnFail(R.drawable.ic_image_loading_deafault)
		.cacheInMemory(false)
		.cacheOnDisk(true)
		.considerExifParams(true)
		.bitmapConfig(Bitmap.Config.RGB_565)
		.build();
	}

	public void UpdateData (List<eWatheqFile> files)
	{
		if (files != null && filesList.size()>0){
			if (filesList != null && filesList.size()>0)
			{
				filesList.clear();
			}
			if (filesArrayList != null && filesArrayList.size()>0)
			{
				filesArrayList.clear();
			}
			this.filesList = files;
			this.filesArrayList = new ArrayList<eWatheqFile>();
			this.filesArrayList.addAll(filesList);
		}
	}
	@Override
	public int getCount() {	

		return filesList.size();
	}
	@Override
	public eWatheqFile getItem(int position) {
		return filesList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {



		eWatheqFile file = getItem(position);


		final ViewHolder holder;
		View view = convertView;
		if (view == null) {
			view = inflater.inflate(R.layout.lout_home_screen_file_cell, parent, false);
			holder = new ViewHolder();
			assert view != null;
			holder.title = (TextView) view.findViewById(R.id.tv_title);
			holder.date = (TextView) view.findViewById(R.id.tv_date);
			holder.imageView = (ImageView) view.findViewById(R.id.iv_image);
			holder.rlImage = (RelativeLayout) view.findViewById(R.id.rl_image);
			holder.rlShadow = (RelativeLayout) view.findViewById(R.id.rl_shadow);

			holder.progressBar = (ProgressBar) view.findViewById(R.id.progress);

			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		view.getLayoutParams().height = shelfHeight ;
		
		holder.rlShadow.getLayoutParams().height = shelfHeight;
		


		if (!file.isDocument()){
			Bitmap bmp=null;


			File thumbFile = isFileExistInLocal(file);

			if (thumbFile != null && thumbFile.exists()){
				try {
					bmp = BitmapFactory.decodeFile(thumbFile.getAbsolutePath());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (bmp == null )
			{

				ImageLoader.getInstance().displayImage(Constants.URL_WEBSITE_BASE+file.Thumbnaillink, holder.imageView, options, new SimpleImageLoadingListener() {
					@Override
					public void onLoadingStarted(String imageUri, View view) {
						holder.progressBar.setProgress(0);
						holder.progressBar.setVisibility(View.VISIBLE);
					}

					@Override
					public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
						holder.progressBar.setVisibility(View.GONE);
					}

					@Override
					public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
						holder.progressBar.setVisibility(View.GONE);
						
							saveThumbnail(loadedImage, imageUri);
					}
				}, new ImageLoadingProgressListener() {
					@Override
					public void onProgressUpdate(String imageUri, View view, int current, int total) {
						holder.progressBar.setProgress(Math.round(100.0f * current / total));
					}
				});
			}
			else 
			{
				holder.imageView.setImageBitmap(bmp);
				holder.progressBar.setVisibility(View.GONE);
			}
		}
		else 
		{
			holder.imageView.setImageResource(R.drawable.ic_pdf_deafault);
			holder.progressBar.setVisibility(View.GONE);
		}
		holder.title.setText(file.Title);
		holder.date.setText(EWatheqUtils.getFormattedDate(file.Date, Constants.DATE_FORMAT_FOR_SERVER, Constants.DATE_FORMAT_ON_SCREEN));		
		return view;

	}
	public static void saveThumbnail(Bitmap bmp,String name)
	{
		String fileId ="";
		if (name != null && name.contains("="))
		{
			fileId = name.substring(name.lastIndexOf("=")+1);
		}
		if (fileId.isEmpty())
		{
			fileId = "image";
		}

		if (bmp != null){
			File thumbDir = EWatheqUtils.getThumbDirectory();

			File file = new File(thumbDir, fileId+"."+Constants.LOCAL_FILE_EXTENSION);
			FileOutputStream fOut = null;
			try {
				fOut = new FileOutputStream(file);

				bmp.compress(Bitmap.CompressFormat.PNG, 85, fOut);
				if (fOut != null)
				{	
					fOut.flush();
					fOut.close();
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}


	}
	public int completeFilter(String searchText,String date, int showOption) {
		if (searchText != null)
			searchText = searchText.toLowerCase(Locale.getDefault());
		filesList.clear();
		for (eWatheqFile file : filesArrayList) 
		{
			if (searchText != null && searchText.length() > 0 && !(file.Description.toLowerCase(Locale.getDefault()).contains(searchText) )
					&& !(file.Title.toLowerCase(Locale.getDefault()).contains(searchText) )) 
			{
				continue;
			}
			else if (date != null && date.length() > 0 && !(file.Date.toLowerCase(Locale.getDefault()).contains(date) )) 
			{
				continue;
			}
			else if (showOption == SHOW_DOCUMENTS_FILES && !file.isDocument())
			{
				continue;
			}
			else if (showOption == SHOW_PHOTOS_FILES && file.isDocument())
			{
				continue;
			}

			else 
			{
				filesList.add(file);
			}
		}
		return filesList.size();
	}
	public static File isFileExistInLocal(eWatheqFile file)
	{
		File thumbFile = null;
		String name =  file.Thumbnaillink;
		try {
			
			String fileId ="";
			if (name != null && name.contains("="))
			{
				fileId = name.substring(name.lastIndexOf("=")+1);
			}
			if (fileId.isEmpty())
			{
				fileId = "image";
			}
			
			
			


			final String fileName = fileId+"."+Constants.LOCAL_FILE_EXTENSION;

			thumbFile = new File(EWatheqUtils.getThumbDirectory(),fileName);
			if (thumbFile.exists())
				return thumbFile;

		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}

		return null;
	}
	static class ViewHolder {
		TextView title;
		TextView date;
		ImageView imageView;
		RelativeLayout rlImage;
		RelativeLayout rlShadow;
		ProgressBar progressBar;
	}


}
