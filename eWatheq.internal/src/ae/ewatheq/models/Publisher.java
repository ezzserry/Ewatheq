package ae.ewatheq.models;

public class Publisher {
	
	private int publisher_id;
	private String publisher_name;
	
	
	public Publisher() {
		// TODO Auto-generated constructor stub
		publisher_id = 0;
		publisher_name = "";
	}
	
	
	
	
	public Publisher(int pub_id, String pub_name) {
		// TODO Auto-generated constructor stub
		publisher_id = pub_id;
		publisher_name = pub_name;
	}
	
	
	
	public int getPublisherId()
	{
		return publisher_id;
	}
	public String getPublisherName()
	{
		if (this.publisher_name == null)
			return "";
		return publisher_name;
	}
	public void setPublisherId(int a_id)
	{
		publisher_id = a_id;
	}
	public void setPublisherName(String a_name)
	{
		publisher_name = a_name;
	}
}
