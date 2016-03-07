package ae.ewatheq.models;

public class UploadFileResponse {
	private boolean uploadSuccess;
	private String response;
	
	
	public UploadFileResponse() {
		// TODO Auto-generated constructor stub
		uploadSuccess = false;
		response = "";
	}
	public UploadFileResponse(boolean uploadSuccess, String response) {
		// TODO Auto-generated constructor stub
		this.uploadSuccess = uploadSuccess;
		this.response = response;
	}
	
	public boolean getUploadSuccess()
	{
		return uploadSuccess;
	}
	public String getResponse()
	{
		if (this.response == null)
			return "";
		return response;
	}
	public void setuploadSuccess(boolean uploadSuccess)
	{
		this.uploadSuccess = uploadSuccess;
	}
	public void setresponse(String response)
	{
		this.response = response;
	}
}
