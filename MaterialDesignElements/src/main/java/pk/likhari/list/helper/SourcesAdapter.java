/*
 * Copyright 2013 David Schreiber
 *           2013 John Paul Nalog
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package pk.likhari.list.helper;

import java.util.List;

import pk.likhari.R;
import pk.likhari.models.SourceJSON;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;
import at.technikum.mti.fancycoverflow.FancyCoverFlowAdapter;

import com.joooonho.SelectableRoundedImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class SourcesAdapter extends FancyCoverFlowAdapter {



	private  List<SourceJSON> sourcesList;
	private ImageLoader imageLoader;
	private ViewHolder holder;
	protected LayoutInflater fInflater;
	// =============================================================================
	// Supertype overrides
	// =============================================================================

	private DisplayImageOptions options;
	private Context context;
	//private int issueHeight;
	//private int issueWidth;
	private boolean isTablet;
	public static double ISSUE_THUMB_HEIGHT_PERCENTAGE_TABLET = 0.15;
	public static double ISSUE_THUMB_HEIGHT_PERCENTAGE_PHONE = 0.40;

	public SourcesAdapter(Context context, List<SourceJSON> sourcesList) {
		// TODO Auto-generated constructor stub
		this.sourcesList=sourcesList;
		this.context = context;
		//this.issueHeight = ((Activity)context).getWindowManager().getDefaultDisplay().getHeight();
		isTablet = ((Activity)context).getResources().getBoolean(R.bool.isTablet);
		/*if (isTablet)
			this.issueHeight = (int) (this.issueHeight * ISSUE_THUMB_HEIGHT_PERCENTAGE_TABLET);
		else 
			this.issueHeight = issueHeight;
		issueWidth = (int) (issueHeight*MyLibraryFragment.ISSUE_RATIO);*/
		options = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.default_loading_thumb)
		.showImageForEmptyUri(R.drawable.default_loading_thumb)
		.showImageOnFail(R.drawable.default_loading_thumb)
		.cacheInMemory(true)
		.cacheOnDisk(true)
		.considerExifParams(true)
		.bitmapConfig(Bitmap.Config.RGB_565)
		.build();

		this.imageLoader=ImageLoader.getInstance();
		this.imageLoader.init(ImageLoaderConfiguration.createDefault(context));

		if (context != null) {

			fInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}
	}
	
	
	

	@Override
	public int getCount() {
		return sourcesList.size();
	}

	@Override
	public SourceJSON getItem(int i) {
		return sourcesList.get(i);
	}
	@Override
	public long getItemId(int i) {
		return i;
	}
	private static class ViewHolder {

		private	SelectableRoundedImageView ivThumb;
		private	TextView tvIssueNumber;
		
		

		//private ProgressBar progressBar;
	}
	@Override
	public View getCoverFlowItem(int i, View reuseableView, ViewGroup viewGroup) {
		View lView = reuseableView;
		if (lView == null) {
			holder = new ViewHolder();
			lView = fInflater.inflate(R.layout.lout_issue_list_item_round_cover,viewGroup, false);
			holder.ivThumb= (SelectableRoundedImageView) lView
					.findViewById(R.id.iv_issue_thumb);
			holder.ivThumb.setScaleType(ScaleType.CENTER_CROP);
			holder.ivThumb.setOval(true);
			holder.tvIssueNumber= (TextView) lView
					.findViewById(R.id.tv_issue_number);
			
			
			lView.setTag(holder);
		} else {
			holder = (ViewHolder) lView.getTag();
		}
		/*if (isTablet)*/
			holder.tvIssueNumber.setText(""+getItem(i).no_of_views);
		/*else 
			holder.tvIssueNumber.setText(getItem(i).IssueNumber );*/
		imageLoader.displayImage(getItem(i).logo_small, holder.ivThumb, options, new SimpleImageLoadingListener() {
			@Override
			public void onLoadingStarted(String imageUri, View view) {
				Log.d("fail", "sffsd");
			}

			@Override
			public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
				Log.d("fail", "sffsd");
			}

			@Override
			public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
				Log.d("fail", "sffsd");
			}
		}, new ImageLoadingProgressListener() {
			@Override
			public void onProgressUpdate(String imageUri, View view, int current, int total) {
				Log.d("fail", "sffsd");
			}
		});
		return lView;
	}
}
