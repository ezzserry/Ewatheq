package ae.ewatheq.models;

public  class KnowledgeService {
	public String Title,Cover,Description,Language,desText;

	public boolean check;
	public Object object;

	public KnowledgeService(String Title,
			String Cover, String Description,
			String Language) {

		this.Title = Title;
		this.Cover = Cover;
		this.Description = Description;
		this.Language = Language;
	

	}
	public void setDescriptionText(String desText) {
		this.desText = desText;
	}



}

