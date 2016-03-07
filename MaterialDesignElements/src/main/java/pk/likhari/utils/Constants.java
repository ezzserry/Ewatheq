package pk.likhari.utils;


import pk.likhari.R;
import android.app.Activity;
import android.provider.BaseColumns;

public class Constants {
	public static final String F_ID = BaseColumns._ID;
	public static final String F_ITEM_ID = BaseColumns._ID;
	public static final String F_NAME = "item_title";
	public static final String F_PATH = "item_mp3_file";
	public static final String F_DURATION = "item_duration";
	public static final String F_CATEGORY = "item_category";
	public static final String F_LANGUAGE = "item_language";
	public static final String F_SUBCATEGORY = "item_subcategory";
	public static final String F_FAVORITE = "item_favorite";
	public static final String F_DOWNLOAD = "item_download";
	public static final String F_time = "item_time";
	public static  String EXHIBITOR_ID = "exhibitor_id" ;
	public static final int BOOK_LIST_RANGE = 100;
	public static boolean  IS_TESTING = false;
	public final static String[] LANGUAGE_CODES={"ar","en"};
	public final static int LANGUAGE_ID_ENG=2;
	public final static int LANGUAGE_ID_AR=1;
	public final static String LANGUAGE_STRING_AR="Arabic";
	public final static String LANGUAGE_STRING_ENG="English";
	public final static int FONT_SIZE_SMALLEST=1;
	public final static int FONT_SIZE_2=2;
	public final static int FONT_SIZE_3=3;
	public final static int FONT_SIZE_DEFAULT=4;
	public final static int FONT_SIZE_5=5;
	public final static int FONT_SIZE_6=6;
	public final static int FONT_SIZE_LARGEST=7;

	public static final String TABLE = "FavoriteFiles";
	public static final String TABLE_DOWNLOAD = "DownloadFiles";
	public static final String URL_ONLINE_PDF_VIEWER = "http://ecssrbooks.com/nacms/FileViewer/ViewLivePDF?pdf=";
	public static String GOOGLE_PLAY_BASE64_ENCODEDPUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAvvLnmx3IcSVH+wpe6Pla8QWCnVWv98IgPWm1sEYvENisQtb2v85Z9eOHsT5j76BeSfFQ5nsrFVLamZLE2cq1cgEVNP8VePj7fBeImokqmU/74hsLxdClM3lBhH0dyWK9laFvoiYAF+XYQkxCLFlA1d74mrjOjZYFs8p6FD5YovZ51Zt0kAtKFgp0bqzCyicT/MwiVYRRxQN9U9hupXQAbJ5VVBgtBFHiTPHhdKdirXaEmTbBTw7LedhlagUOx0S9V2HDHE+582NUXAIYCnLy7b53Jwd6L9YtvWOAwucy2QVt6xrIUhvN/0yoywSAI8D/glVEpbTcFY9DTmkHKkccSwIDAQAB";

	public final static String LANGUAGES_IDS = "language_id";
	public final static String Network_wif = "network_wifi";
	public final static String USER_ACCOUNT = "user_account";
	public final static String CATREGORY_TITLE = "category_title";
	public final static String BUFFER_TIME = "buffer_time";
	public final static String FONT_SIZE = "font_size";
	public final static String FONT_SIZE_TEXT = "font_text";
	public final static String CAR_MODE = "font_text";
	public final static String STR_PACKAGE_NAME = "com.almaqal";
	public final static String DOCUMENT_ARCHIVE_THUMBNAIL = "http://www.ncdr.ae/ncdr/ncdr-app/NCDRApp/News/Archives/DocumentArchives/documentThumbnail.jpg";
	public final static String URL_NATIONAL_ARCHIVE = "https://www.google.com/maps/place/National+Archives/@24.420891,54.447746,1037m/data=!3m1!1e3!4m2!3m1!1s0x0:0xd424b3eb74c7037c?hl=en-US";

	public final static String URL_AL_MAQAL_BASE_URL = "https://al-maqal.com/AlmaqalServices.svc/";
	public final static String URL_AL_MAQAL_GET_ISSUES_PART = "GetIssue?";
	public final static String URL_AL_MAQAL_YEAR_PART = "year=";
	public final static String URL_AL_MAQAL_EMAIL_PART = "email=";
	
	public final static String URL_AL_MAQAL_DEVICE_NAME_PART = "device_name=";
	public final static String URL_AL_MAQAL_OS_VERSION_PART = "os_version=";
	public final static String URL_AL_MAQAL_USER_ANEM_PART = "user_name=";
	public final static String URL_AL_MAQAL_EMEI_PART = "emei=";
	public final static String URL_AL_MAQAL_PUERCHASED_ITEM_PRICE_PART = "purchaseditemprice=";
	public final static String URL_AL_MAQAL_PURCHASED_ITEM_GOOGLE_PLAY_ID_PART = "purchaseditemgoogleplayid=";
	public final static String URL_AL_MAQAL_SUBSCRIPTION_ID_PART = "subscriptionid=";
	public final static String URL_AL_MAQAL_PURCHASED_ITEM_TIME_PART = "purchaseditemtime=";
	
	public final static String URL_AL_MAQAL_MONTH_PART= "month=";
	public final static String URL_AL_MAQAL_GET_ARTICLE = "GetArticle?";
	public final static String URL_AL_MAQAL_GET_ANDROID_SUBSCRIPTIONS = "GetSubscriptions";
	public final static String URL_AL_MAQAL_GET_PURCHSED_SUBSCRIPTIONS = "getPurchasedSubscription?";
	public final static String URL_AL_MAQAL_GET_ADD_NEW_PURCHASED_ITEM = "AddNewPurchasedItem?";
	public final static String URL_AL_MAQAL_ID= "id=";
	public final static String URL_AL_MAQAL_SEARCH_ARTICLE = "SearchArticle?";
	public final static String URL_AL_MAQAL_DATA = "data=";
	public final static String URL_AL_MAQAL_START = "start=";
	public final static String URL_AL_MAQAL_END = "end=";
	

	public static String TAG_PURCHASED_SUBSCRIPTIONS = "purchased_subscriptions";
	public static String ALMAQAL_FIRST_DAY = "02/09/2015";

	public final static int[] MENU_SELECTED_TEXT_COLOR ={R.color.color_light_blue,
		R.color.color_orange,
		R.color.color_pink,
		R.color.color_blue,
		R.color.color_orange,
		R.color.color_pink,
		R.color.color_pink,
		R.color.color_orange,
		R.color.color_blue,
		R.color.color_light_blue
	};

	public static final String EMAIL_PATTERN = 
			"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
					+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	public static final String DATE_FORMAT_MMDDYYYY = "MM/dd/yyyy";
	public static final String DATE_FORMAT_DDMMYYYY = "dd/MM/yyyy";
			
	public static final String APP_ID = "APP_ID";
	public static final String APP_NAME_ENGLISH = "APP_NAME_ENGLISH";
	public static final String APP_LINK = "APP_LINK";
	public static final String APP_DESCRIPTION_ENGLISH = "APP_DESCRIPTION_ENGLISH";

	public static final String APP_ICON = "APP_ICON";
	public static final String APP_NAME_ARABIC = "APP_NAME_ARABIC";
	public static final String APP_DESCRIPTION_ARABIC = "APP_DESCRIPTION_ARABIC";

	public static final String TABLE_MOREAPP= "MORE_APP";
	public static String DB_PATH ;
	public static String DB_NAME = "AudioDatabase.sqlite";
	public static String DB_MoreAppNAME = "MoreApps.sqlite";

	public static String URL_WEBSITE_BASE = "http://www.ncdr.ae:81/";
	public static String URL_SERVICES = "Service/";
	public static String URL_GET_BOOKS_ARABIC = URL_WEBSITE_BASE+URL_SERVICES+"GetBooks/Arabic";
	public static String URL_GET_BOOKS_ENGLISH = URL_WEBSITE_BASE+URL_SERVICES+"GetBooks/English";
	public static String URL_GET_TEST_ISSUES_URL = "https://al-maqal.com/AlmaqalServices.svc/GetIssue?year=2015&month=4";
	public static String URL_GET_PHOTOS = URL_WEBSITE_BASE+URL_SERVICES+"GetPhotos";
	public static String URL_GET_PHOTOS_CATEGORIES = URL_WEBSITE_BASE+URL_SERVICES+"GetCategories/Photos";
	public static String URL_GET_DOCUMENTS = URL_WEBSITE_BASE+URL_SERVICES+"GetDocuments";
	public static String URL_GET_DOCUMENTS_CATEGORIES = URL_WEBSITE_BASE+URL_SERVICES+"GetCategories/Documents";


	/*this.BookDate = BookDate;
	this.IssueId = IssueId;
	this.IssueNumber = IssueNumber;
	this.PdfLink = PdfLink;
	this.PdfPassword = PdfPassword;
	this.Thumb = Thumb;*/


	/*PDF constants*/
	public static final String AL_MAQAL_ISSUE_LOCAL_TABLE = "issue_table";
	public static final String AL_MAQAL_BookDate = "BookDate";
	public static final String AL_MAQAL_IssueId = "IssueId";
	public static final String AL_MAQAL_IssueNumber = "IssueNumber";
	public static final String AL_MAQAL_PdfLink = "PdfLink";
	public static final String AL_MAQAL_PdfPassword = "PdfPassword";
	public static final String AL_MAQAL_Thumb = "Thumb";


	public static final String LOCAL_DIR = "com.almaqal" ;
	
	public final static int FONT_TYPE_SF_ADEN_BOLD = 1;
	public final static int FONT_TYPE_SULTAN_SEERA_MEDIUM = 2;
	public final static int FONT_TYPE_SULTAN_SEERA_REGULAR = 3;
}
