package ae.ewatheq.models;

import java.io.Serializable;


public class AboutNaSub implements Serializable{
	
	private static final long serialVersionUID = -6099312954099962806L;
	public String title;
	public String photoName;
	public String htmlFileName;
	public int fragmentType;
	public AboutNaSub(String title, String photoName,String htmlFileName) {
		this.title = title;
		this.photoName = photoName;
		this.htmlFileName = htmlFileName;
		this.fragmentType = 0;
	}
	public AboutNaSub(String title, String photoName,String htmlFileName,int fragmentType) {
		this.title = title;
		this.photoName = photoName;
		this.htmlFileName = htmlFileName;
		this.fragmentType = fragmentType;
	}
		
	
}
