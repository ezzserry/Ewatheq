package pk.likhari.list.helper;

import java.util.ArrayList;
import java.util.List;

import pk.likhari.R;
import pk.likhari.models.ArticleJSON;
import pk.likhari.models.DailyArticleJSON;
import pk.likhari.utils.AlmaqalUtils;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class DailyArticleListAdapter extends BaseAdapter{
	private Context context;
	private List<ArticleJSON> articlesList ;
	private ArrayList<ArticleJSON> articlesArraylist;
	private ViewHolder holder;
	protected LayoutInflater fInflater;
	public DailyArticleListAdapter(Context context, List<ArticleJSON> articles)
	{
		this.context = context;
		articlesList = new ArrayList<ArticleJSON>();
		articlesArraylist = new ArrayList<ArticleJSON>();
		articlesList.addAll(articles) ;	
		if (articlesList != null)
			this.articlesArraylist.addAll(articlesList);
		if(this.context != null){
			fInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}
	}
	@Override
	public int getCount() {
		return articlesList.size();
	}
	@Override
	public ArticleJSON getItem(int i) {
		return articlesList.get(i);
	}
	
	
	@Override
	public long getItemId(int i) {
		return (long) i;
	}

	private static class ViewHolder {

		private	TextView tvTitle;
		private	TextView tvAuthor;
		private	TextView tvViews;
		private	TextView tvLikes;
		private	TextView tvNameChar;
		private	View viewRod;
		

	
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View lView = convertView;
		// if (lView == null) {
		//
		// lView = fInflater.inflate(R.layout.menu, parent, false);
		// }

		ArticleJSON article = getItem(position);
		
		if (lView == null) {
			holder = new ViewHolder();
			lView = fInflater.inflate(R.layout.lout_daily_article_list_item,parent, false);
			holder.tvTitle = (TextView) lView
					.findViewById(R.id.tv_title);
			holder.tvAuthor= (TextView) lView
					.findViewById(R.id.tv_author);
			holder.tvLikes= (TextView) lView
					.findViewById(R.id.tv_likes);
			holder.tvViews= (TextView) lView
					.findViewById(R.id.tv_views);
			/*holder.tvPage = (TextView) lView
					.findViewById(R.id.tv_page);*/
			holder.tvNameChar= (TextView) lView
					.findViewById(R.id.tv_name_char);
			holder.viewRod = (View) lView
					.findViewById(R.id.view_rod);
			lView.setTag(holder);
			
		} else {

			holder = (ViewHolder) lView.getTag();
		}

		
		

		holder.tvTitle.setText(article.title);
		holder.tvLikes.setText(article.rating_plus);
		holder.tvViews.setText(article.no_of_views);
		
	//	holder.tvPage.setText(article.ArticleAuthor+" - ص "+article.ArticlePageNumber);
		/*if (article.ArticleAuthor != null && !article.ArticleAuthor.isEmpty())
			holder.tvNameChar.setText(article.ArticleAuthor.substring(0, 2));*/
		holder.tvNameChar.setBackgroundResource(getCircleDrawbleOpt((position%7)+1));
		holder.viewRod.setBackgroundResource(getTextOpt((position%7)+1));
		//AlmaqalUtils.applyFontAdenBold(context, holder.tvTitle);
		//AlmaqalUtils.applyFontSeeraRegular(context, holder.tvPage);
		
		return lView;	
	}
	public int completeFilter(String sourceId) {
		
		articlesList.clear();
		for (ArticleJSON article : articlesArraylist) 
		{
			if (sourceId != null && sourceId.length() > 0 && !(article.source_id.equalsIgnoreCase(sourceId) )) 
			{
				continue;
			}
			else 
			{
				articlesList.add(article);
			}
		}
		return articlesList.size();
	}
	public static int getCircleDrawbleOpt(int rand)
	{
		if (rand ==1)
			return R.drawable.bg_circle_opt_1;
		else if (rand ==2)
			return R.drawable.bg_circle_opt_2;
		else if (rand ==3)
			return R.drawable.bg_circle_opt_3;
		else if (rand ==4)
			return R.drawable.bg_circle_opt_4;
		else if (rand ==5)
			return R.drawable.bg_circle_opt_5;
		else if (rand ==6)
			return R.drawable.bg_circle_opt_6;
		else if (rand ==7)
			return R.drawable.bg_circle_opt_7;
		else 
			return R.drawable.bg_circle_opt_1;
	}
	public static int getTextOpt(int rand)
	{
		if (rand ==1)
			return R.color.color_opt_1;
		else if (rand ==2)
			return R.color.color_opt_2;
		else if (rand ==3)
			return R.color.color_opt_3;
		else if (rand ==4)
			return R.color.color_opt_4;
		else if (rand ==5)
			return R.color.color_opt_5;
		else if (rand ==6)
			return R.color.color_opt_6;
		else if (rand ==7)
			return R.color.color_opt_7;
		else 
			return R.color.color_opt_1;
	}
}
