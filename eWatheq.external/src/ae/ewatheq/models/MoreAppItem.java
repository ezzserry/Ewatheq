package ae.ewatheq.models;

public class MoreAppItem {
	public String APP_NAME_ENGLISH, APP_LINK, APP_DESCRIPTION_ENGLISH,
	APP_ICON, APP_NAME_ARABIC, APP_DESCRIPTION_ARABIC;
	int APP_ID;

	public boolean check;
	public Object object;

	public MoreAppItem(int APP_ID, String APP_NAME_ENGLISH,
			String APP_LINK, String APP_DESCRIPTION_ENGLISH,
			String APP_ICON, String APP_NAME_ARABIC,
			String APP_DESCRIPTION_ARABIC) {

		this.APP_ID = APP_ID;
		this.APP_NAME_ENGLISH = APP_NAME_ENGLISH;
		this.APP_LINK = APP_LINK;
		this.APP_DESCRIPTION_ENGLISH = APP_DESCRIPTION_ENGLISH;
		this.APP_ICON = APP_ICON;
		this.APP_NAME_ARABIC = APP_NAME_ARABIC;
		this.APP_DESCRIPTION_ARABIC = APP_DESCRIPTION_ARABIC;

	}

	/*
	 * @Override public String toString() { if (object != null) { return
	 * object.toString(); } return null; }
	 */
}