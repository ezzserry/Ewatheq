package ae.ewatheq.models;

public class Author {
	private int author_id;
	private String author_name;
	
	
	public Author() {
		// TODO Auto-generated constructor stub
		author_id = 0;
		author_name = "";
	}
	public Author(int aut_id, String aut_name) {
		// TODO Auto-generated constructor stub
		author_id = aut_id;
		author_name = aut_name;
	}
	
	public int getAuthorId()
	{
		return author_id;
	}
	public String getAuthorName()
	{
		if (this.author_name == null)
			return "";
		return author_name;
	}
	public void setAuthorId(int a_id)
	{
		 author_id = a_id;
	}
	public void setAuthorName(String a_name)
	{
		 author_name = a_name;
	}
}
