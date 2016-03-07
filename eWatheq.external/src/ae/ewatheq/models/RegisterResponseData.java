package ae.ewatheq.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;



public class RegisterResponseData implements Serializable{
	
	private static final long serialVersionUID = -6099312954099962806L;
	public static String  strUserID="UserID";
	public static String  strName="Name";
	public static String  strEmail="Email";
	public static String  strAge="Age";
	
	
	public static String  strGender="Gender";
	public static String  strEmiratesID="EmiratesID";
	public static String  strDateOfBirth="DateOfBirth";
	public static String  strQuota="Quota";
	public static String  strCategories="Categories";
	


	public String UserID;
	public String Name;
	public String Email;
	public String Age;
	public String Gender;
	public String EmiratesID;
	public String DateOfBirth;
	public UserQouta Quota;
	public List<eWatheqFolder> Categories;
	
	

	public void removeNull(){
		if (this.UserID == null)
			this.UserID = "";
		if (this.Name == null)
			this.Name = "";
		if (this.Email == null)
			this.Email = "";
		if (this.Age == null)
			this.Age = "";
		if (this.Gender == null)
			this.Gender = "";
		if (this.EmiratesID == null)
			this.EmiratesID = "";
		if (this.DateOfBirth == null)
			this.DateOfBirth = "";
		if (this.Quota == null)
			this.Quota = new UserQouta();
		if (this.Categories == null)
			this.Categories = new ArrayList<eWatheqFolder>();
		
		
	}
	
	
	
	
	
}
