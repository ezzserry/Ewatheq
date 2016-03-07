package ae.ewatheq.models;

import java.io.Serializable;
import java.util.List;

public class AboutNa implements Serializable{

	private static final long serialVersionUID = -6099312954099962806L;
	public String title;
	public String photoName;
	public String htmlFileName;
	public int fragmentType;
	public List<AboutNaSub> subItems;
	public AboutNa(String title, String photoName,String htmlFileName, List<AboutNaSub> subItems) {
		this.title = title;
		this.photoName = photoName;
		this.htmlFileName = htmlFileName;
		this.subItems = subItems;
		this.fragmentType = 0;
	}
	public AboutNa(String title, String photoName,String htmlFileName,int fragmentType, List<AboutNaSub> subItems) {
		this.title = title;
		this.photoName = photoName;
		this.htmlFileName = htmlFileName;
		this.fragmentType = fragmentType;
		this.subItems = subItems;
	}
}
