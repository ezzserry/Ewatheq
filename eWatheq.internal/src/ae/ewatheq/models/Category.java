package ae.ewatheq.models;

public class Category {
	private int category_id;
	private String category_name;
	
	
	public Category() {
		// TODO Auto-generated constructor stub
		category_id = 0;
		category_name = "";
	}
	
	public Category(int cat_id, String cat_name) {
		// TODO Auto-generated constructor stub
		category_id = cat_id;
		category_name = cat_name;
	}
	
	
	public int getCategoryId()
	{
		return category_id;
	}
	public String getCategoryName()
	{
		if (this.category_name == null)
			return "";
		return category_name;
	}
	public void setCategoryId(int a_id)
	{
		category_id = a_id;
	}
	public void setCategoryName(String a_name)
	{
		category_name = a_name;
	}
}
