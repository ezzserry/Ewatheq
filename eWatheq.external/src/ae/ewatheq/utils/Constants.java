package ae.ewatheq.utils;

import ae.ewatheq.external.R;

public class Constants {

	public final static String[] LANGUAGE_CODES={"ar","en"};
	public final static int LANGUAGE_ID_ENG=2;
	public final static int LANGUAGE_ID_AR=1;


	public final static String LANGUAGES_IDS = "language_id";
	public final static String Network_wif = "network_wifi";

	public static boolean IS_TESTING = false; 

	public final static int[] MENU_IC ={R.drawable.ic_files_normal,
										R.drawable.ic_about_us_normal,
										R.drawable.ic_settings_normal
										};
	public final static int[] MENU_IC_SELECTED ={
		R.drawable.ic_files_selected,
		R.drawable.ic_about_us_selected,
		R.drawable.ic_settings_selected
		};

	public static String URL_WEBSITE_BASE = "http://7arfbooks.com";
	public static String URL_SERVICES = "/services/";
	public static String URL_ID_SCAN_REGISTER = URL_WEBSITE_BASE+URL_SERVICES+"Register?";
	public static String URL_MANUAL_REGISTER = URL_WEBSITE_BASE+URL_SERVICES+"PendingRegister?";
	
	public static String URL_LOGIN = URL_WEBSITE_BASE+URL_SERVICES+"Login?";
	public static String URL_GET_CATEGORY = URL_WEBSITE_BASE+URL_SERVICES+"GetCategory?";
	public static String URL_GET_FILES = URL_WEBSITE_BASE+URL_SERVICES+"GetFiles?";
	public static String URL_EDIT_FILE = URL_WEBSITE_BASE+URL_SERVICES+"EditFile?";
	public static String URL_ADD_FILE = URL_WEBSITE_BASE+URL_SERVICES+"AddFile?";
	public static String URL_PDF_VIEWER = URL_WEBSITE_BASE+"/pdfviewer?";
	public static String URL_DELETE_FILE = URL_WEBSITE_BASE+URL_SERVICES+"DeleteFile?";
	public static String URL_SHARE_WITH_NA = URL_WEBSITE_BASE+URL_SERVICES+"SetFileShared?";
	public static String URL_EDIT_CATEGORY = URL_WEBSITE_BASE+URL_SERVICES+"EditCategory?";
	public static String URL_QOUTA = URL_WEBSITE_BASE+URL_SERVICES+"GetUserQuota?";
	public static String URL_FORGOT_PASSWORD= URL_WEBSITE_BASE+URL_SERVICES+"ForgetPassword?";
	public static String URL_PARAMETER_TITLE = "title";
	public static String URL_PARAMETER_DESCRIPTION = "description";
	public static String URL_PARAMETER_FILE_TYPE = "filetype";
	public static String URL_PARAMETER_FILE_EXTENSION = "fileextension";
	public static String URL_PARAMETER_ACTUAL_DATE = "AcualDate";
	public static String URL_PARAMETER_CATEGORY_ID = "categoryID";
	public static String URL_PARAMETER_CATEGORY_NAME = "categoryName";
	public static String URL_PARAMETER_FILE_SIZE = "filesize";
	public static String URL_PARAMETER_FILE_DATA = "filedata";
	
	
	public static String URL_PARAMETER_USERNAME = "username";
	public static String URL_PARAMETER_NAME = "Name";
	public static String URL_PARAMETER_FULL_NAME = "FullName";
	public static String URL_PARAMETER_EMAIL = "email";
	public static String URL_PARAMETER_AGE = "age";
	public static String URL_PARAMETER_GENDER = "gender";
	public static String URL_PARAMETER_EMIRATES_ID = "emiratiesID";
	public static String URL_PARAMETER_PASSWORD = "password";
	public static String URL_PARAMETER_DATE_OF_BIRTH = "date_of_birth";
	public static String URL_PARAMETER_NATINALITY_ID = "nationalityID";
	public static String URL_PARAMETER_EQQUAL = "=";
	public static String URL_PARAMETER_AND = "&";
	
	
	
	public static String URL_PARAMETER_FILE_ID = "FileId";
	public static String URL_PARAMETER_MESSAGE = "message";
	public static String URL_PARAMETER_USER_ID = "UserID";
	
	
	
	

	public static final String TABLE_FOLDERS = "MyFolders";
	public static final String TABLE_FILES = "MyFiles";
	public static final String KEY_FOLDER_ID = "folder_id";
	public static final String KEY_FOLDER_NAME = "folder_name";
	public static final String KEY_FOLDERS_SERVER_ID = "folder_server_id";
	public static final String KEY_FOLDERS_NUMBER_OF_FILES = "folder_number_of_files";
	public static final String KEY_FOLDERS_UPDATED_COUNTER = "folder_updated_counter";
	public static final String KEY_FILE_ID= "file_id";
	public static final String KEY_FILE_NAME = "file_name";
	public static final String KEY_FILE_LINK = "file_link";
	public static final String KEY_FILE_TITLE = "file_title";
	public static final String KEY_FILE_TYPE_ID = "file_type_id";
	public static final String KEY_FILE_DESCRIPTION = "file_description";
	public static final String KEY_FILE_DATE = "file_date";
	public static final String KEY_FILE_FOLDER_SERVER_ID = "file_folder_server_id";
	public static final String KEY_FILE_GRANT_ACCESS_TO_NA = "file_grant_access_na";
	public static final String KEY_FILE_THUMB_PATH = "file_thumb_path";
	
	
	/*PDF constants*/
	public static final String book_ID = "book_id";

	public static final String Book_Table_Name = "MyLibrary";
	
	public static final String NameEnglish = "NameEnglish";
	public static final String NameArabic = "NameArabic";
	public static final String BOOK_LANGUAGE = "BookLanguage";
	public static final String LOCAL_DIR = "ae.ewatheq" ;
	public static final String LOCAL_DIR_THUMB = "thumbs" ;
	public static final String LOCAL_DIR_TEMP = "temp" ;
	public static final String PACKAGE_NAME = "ae.ewatheq" ;
	
	public static String SHARED_PREFERENCES_USER_EMAIL = "sp_user_email";
	public static String SHARED_PREFERENCES_USER_ID = "sp_user_id";
	public static String SHARED_PREFERENCES_USER_PASSWORD = "sp_user_password";
	public static String SHARED_PREFERENCES_USER_NAME= "sp_user_name";
	public static String SHARED_PREFERENCES_USER_AGE= "sp_user_age";
	public static String SHARED_PREFERENCES_USER_DOB= "sp_user_dob";
	public static String SHARED_PREFERENCES_GENDER = "sp_user_gender";
	public static String SHARED_PREFERENCES_USER_TOTAL_QOUTA = "sp_user_total_qouta";
	public static String SHARED_PREFERENCES_USER_AVAILABLE_QOUTA = "sp_user_available_qouta";
	public static String SHARED_PREFERENCES_USER_USED_QOUTA  = "sp_user_used_qouta";
	public static String SHARED_PREFERENCES_USER_EMIRATES_ID  = "sp_emirates_id";
	public static String SHARED_PREFERENCES_KEEP_ME_LOGGED_IN = "sp_keep_me_logged_in";
	public static String SERVER_RESPONSE_STATUS_SUCCESS = "1";
	public static String SERVER_RESPONSE_STATUS_FAIL_GENERAL_ERROR = "0";
	public static String SERVER_RESPONSE_STATUS_FAIL_WRONG_CREDENTIALS = "2";
	public static String SERVER_RESPONSE_STATUS_FAIL_EMIRATES_ID_ALREADY_EXIST = "2";
	public static String SERVER_RESPONSE_STATUS_FAIL_WRONG_EMIRATES_ID = "3";
	public static String SERVER_RESPONSE_STATUS_FAIL_EMAIL_ALREADY_EXIST = "4";


	
	public static String DATE_FORMAT_FOR_SERVER = "MM/dd/yyyy";
	public static String DATE_FORMAT_ON_SCREEN = "d MMM, yyyy";
	public static String SHARED_PREFERENCES_NAME = "eWatheq_preferences";
	public static String SHARED_PREFERENCES_APP_VERSION = "app_version";
	public static String SHARED_PREFERENCES_IS_HOME_SCREEN_OPENED_SECOND_TIME = "is_home_screen_open_second_time";
	public static int SHARED_PREFERENCES_MODE = 0;
	public static String SHARED_PREFERENCES_NEED_TO_UPDATE_DB = "update_db";
	public static final String EMAIL_PATTERN = 
			"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
					+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	/*PDF constants*/
	public static int SHOW_SCREEN = 1;
	public static int SHOW_LOADING = 2;
	public static int SHOW_NO_FILES = 3;
	public static final double EMIRATES_ID_CARD_RATIO = 0.63;
	public static final int EMIRATES_ID_CARD_WIDTH = 500;
	public static final int EMIRATES_ID_CARD_HEIGHT = 315;
	public static String FILE_TYPE_FOR_SERVER_PHOTO= "1";
	public static String FILE_TYPE_FOR_SERVER_PDF= "2";

	public static String LOCAL_FILE_EXTENSION= "png";
	
	public static String URL_GOOGLE_DOCS= "https://docs.google.com/gview?embedded=true&url=";
	public static String SHARED_PREFRENCES_SELECTED_FOLDER_SERVER_ID = "key_selected_folder_server_id";
	public static String KEY_FILE_PATH= "key_file_path";
	public static String KEY_IS_MANUAL_REGISTER= "key_file_path";
	
}
//eWatheqCalDroidLib
//eWatheqFloatingButtonLib
//eWatheqViewPagerIndicatorlibrary
