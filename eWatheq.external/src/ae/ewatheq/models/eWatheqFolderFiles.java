package ae.ewatheq.models;

import java.io.Serializable;



public class eWatheqFolderFiles implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int folderFilesId;
	private String folderFilesDate;
	private String folderFilesDescription;
	private String folderFilesFolderServerId;
	private String folderFilesServerId;
	private String folderFilesName;
	private String folderFilesTitle;
	private String folderFilesThumbPath;
	private String folderFilesGrantAccessToNA;
	private String folderFilesType;







	public eWatheqFolderFiles() {
		// TODO Auto-generated constructor stub
		folderFilesId = -1;
		folderFilesDate = "";
		folderFilesDescription = "";
		folderFilesFolderServerId = "";
		folderFilesServerId = "";
		folderFilesGrantAccessToNA = "";
		folderFilesName = "";
		folderFilesThumbPath = "";
		folderFilesTitle = "";
		folderFilesType = "";

	}



	



	public int getfolderFilesId()
	{
		
		return folderFilesId;

	}
	
	public String getfolderFilesDate()
	{
		if (this.folderFilesDate == null)
			return "";
		return folderFilesDate;

	}
	
	public String getfolderFilesDescription()
	{
		if (this.folderFilesDescription == null)
			return "";
		return folderFilesDescription;

	}
	public String getfolderFilesFolderServerId()
	{
		if (this.folderFilesFolderServerId == null)
			return "";
		return folderFilesFolderServerId;

	}
	public String getfolderFilesServerId()
	{
		if (this.folderFilesServerId == null)
			return "";
		return folderFilesServerId;

	}
	public String getfolderFilesGrantAccessToNA()
	{
		if (this.folderFilesGrantAccessToNA == null)
			return "";
		return folderFilesGrantAccessToNA;

	}
	public boolean getfolderFilesGrantAccessToNABool()
	{
		if (this.folderFilesGrantAccessToNA == null)
			return false;
		try{
			return Boolean.parseBoolean(folderFilesGrantAccessToNA);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			try{
				return Integer.parseInt(folderFilesGrantAccessToNA) == 0? false:true;
			}
			catch (Exception e)
			{
				e.printStackTrace();

			}	
		}
		return false;
	}
	public String getfolderFilesName()
	{
		if (this.folderFilesName == null)
			return "";
		return folderFilesName;
	}
	public String getfolderFilesType()
	{
		if (this.folderFilesType == null)
			return "";
		return folderFilesType;
	}
	public boolean getfolderFilesIsDocument()
	{
		if (this.folderFilesType == null || this.folderFilesType.isEmpty() )
			return false;
		else if (this.folderFilesType.equalsIgnoreCase("pdf") )
			return true;
		else 
			return false;
	}
	public String getfolderFilesThumbPath()
	{
		if (this.folderFilesThumbPath == null)
			return "";
		return folderFilesThumbPath;
	}
	public String getfolderFilesTitle()
	{
		if (this.folderFilesTitle == null)
			return "";
		return folderFilesTitle;
	}
	
	public void setfolderFilesId(int folderFilesId)
	{
		this.folderFilesId = folderFilesId;
	}
	public void setfolderFilesDate(String folderFilesDate)
	{
		this.folderFilesDate = folderFilesDate;
	}
	public void setfolderFilesDescription(String folderFilesDescription)
	{
		this.folderFilesDescription = folderFilesDescription;
	}
	public void setfolderFilesFolderServerId(String folderFilesFolderServerId)
	{
		this.folderFilesFolderServerId= folderFilesFolderServerId;
	}
	public void setfolderFilesServerId(String folderFilesServerId)
	{
		this.folderFilesServerId= folderFilesServerId;
	}
	public void setfolderFilesGrantAccessToNA(String folderFilesGrantAccessToNA)
	{
		this.folderFilesGrantAccessToNA = folderFilesGrantAccessToNA;
	}
	public void setfolderFilesName(String folderFilesName)
	{
		this.folderFilesName= folderFilesName;
	}
	public void setfolderFilesThumbPath(String folderFilesThumbPath)
	{
		this.folderFilesThumbPath = folderFilesThumbPath;
	}
	
	public void setfolderFilesTitle(String folderFilesTitle)
	{
		this.folderFilesTitle = folderFilesTitle;
	}
}
