package ae.ewatheq.models;

import java.io.Serializable;

import ae.ewatheq.utils.EWatheqUtils;



public class Video implements Serializable{
	
	
	
	
	private static final long serialVersionUID = -6099322954099962806L;
	private String videoThumb;
	private String pubDate;
	private String title;
	private String link;
	private String author;
	
	

	
	
	
	
	public String getvideoThumb()
	{
		
		if (this.videoThumb == null)
			return "";
		return this.videoThumb;
	}
	public String getpubDate()
	{
		
		if (this.pubDate == null)
			return "";
		return this.pubDate;
	}
	
	public String gettitle()
	{
		
		if (this.title == null)
			return "";
		return this.title;
	}
	public String getlink()
	{
		if (this.link == null)
			return "";
		return this.link;
	}
	
	public String getauthor()
	{
		if (this.author == null)
			return "";
		return this.author;
	}
	
	
	
	
	
	
	
	
	
	public void setvideoThumb(String videoId)
	{
		this.videoThumb = videoId;
	}
	
	public void setpubDate(String pubDate)
	{
		this.pubDate = pubDate;
	}
	public void settitle(String title)
	{
		this.title = title;
	}
	
	public void setlink(String link)
	{
		this.link = link;
		this.setvideoThumb(EWatheqUtils.GetYoutubeVideoThumb(this.link));
	}
	
	public void setauthor(String author)
	{
		this.author = author;
	}
	
	
	
	
}
