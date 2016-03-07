package ae.ewatheq.models;

public class eWatheqFolderWithArray {

	private String folderId;
	private String folderName;
	private String folderServerId;
	private String folderNumberOfFiles;



	public eWatheqFolderWithArray() {
		// TODO Auto-generated constructor stub
		folderId = "";
		folderName = "";
		folderServerId = "";
		folderNumberOfFiles = "";
	}



	public eWatheqFolderWithArray(String folderId, String folderName,String folderServerId,String folderNumberOfFiles) {
		// TODO Auto-generated constructor stub
		this.folderId = folderId;
		this.folderName = folderName;
		this.folderServerId = folderServerId;
		this.folderNumberOfFiles = folderNumberOfFiles;
	}



	public String getfolderId()
	{
		if (this.folderId == null)
			return "";
		return folderId;

	}
	public String getfolderName()
	{
		if (this.folderName == null)
			return "";
		return folderName;
	}
	public String getfolderServerId()
	{
		if (this.folderServerId == null)
			return "";
		return folderServerId;
	}
	public String getfolderNumberOfFiles()
	{
		if (this.folderNumberOfFiles == null)
			return "";
		return folderNumberOfFiles;
	}
	public int getfolderNumberOfFilesInt()
	{
		if (this.folderNumberOfFiles != null)
		{
			try {
				return Integer.parseInt(folderNumberOfFiles);
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		return 0;
	}
	public void setfolderId(String folderId)
	{
		this.folderId = folderId;
	}
	public void setfolderName(String folderName)
	{
		this.folderName = folderName;
	}
	public void setfolderServerId(String folderServerId)
	{
		this.folderServerId = folderServerId;
	}
	public void setfolderNumberOfFiles(String folderNumberOfFiles)
	{
		this.folderNumberOfFiles = folderNumberOfFiles;
	}
}
