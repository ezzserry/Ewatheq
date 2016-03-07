package com.sqlite;


import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;



import ae.ewatheq.models.BookJSON;
import ae.ewatheq.models.BookJSONWithCategory;
import ae.ewatheq.models.BookRecord;
import ae.ewatheq.models.eWatheqFile;
import ae.ewatheq.models.eWatheqFolder;
import ae.ewatheq.models.eWatheqFolderFiles;
import ae.ewatheq.utils.Constants;
import ae.ewatheq.utils.EWatheqUtils;
import ae.ewatheq.utils.MaskMediaUtils;
import ae.ewatheq.external.R;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper {
	private final static String DATABASE_NAME = "book_db";
	private final static int DATABASE_VERSION = 1;
	private final static String TABLE_NAME = "book_mark";
	private final static String TABLE_SETUP = "book_setup";
	public final static String FIELD_ID = "_id";
	public final static String FIELD_FILENAME = "filename";
	public final static String FIELD_BOOKMARK = "bookmark";
	public final static String FONT_SIZE = "fontsize";
	public final static String ROW_SPACE = "rowspace";
	public final static String COLUMN_SPACE = "columnspace";
	private Context context;
	private boolean isTablet;
	private String strLangId;
	private int langId;


	public DbHelper(Context context) {

		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.context = context;
		isTablet = context.getResources().getBoolean(R.bool.isTablet);

		strLangId =MaskMediaUtils.GetSharedParameter(context, Constants.LANGUAGES_IDS);
		if (strLangId!=null)
		{
			langId = Integer.parseInt(strLangId);
		}
		else
		{
			langId =Constants.LANGUAGE_ID_ENG;
		}
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		StringBuffer sqlCreateCountTb = new StringBuffer();
		sqlCreateCountTb.append("create table ").append(TABLE_NAME)
		.append("(_id integer primary key autoincrement,")
		.append(" filename text,").append(" bookmark text);");
		db.execSQL(sqlCreateCountTb.toString());
		String sql = "insert into " + TABLE_NAME
				+ "(filename,bookmark) values('���°ٿ�.txt','0')";

		StringBuffer setupTb = new StringBuffer();
		setupTb.append("create table ").append(TABLE_SETUP)
		.append("(_id integer primary key autoincrement,")
		.append(" fontsize text,").append(" rowspace text,")
		.append(" columnspace text);");
		db.execSQL(setupTb.toString());

		/*String sqlBook = String
				.format("create table %s"
						+ "(%s text primary key,%s text,%s text,%s text,%s text,%s text,%s text,%s text,%s text,%s text)",
						Constants.Book_Table_Name, Constants.book_ID,
						Constants.book_title,Constants.BOOK_LANGUAGE,
						Constants.book_category,Constants.file_size, Constants.thumbail_path,
						Constants.file_path, Constants.NameArabic,
						Constants.NameEnglish, Constants.F_time);*/


		String setup = "insert into " + TABLE_SETUP
				+ "(fontsize,rowspace,columnspace) values('6','0','0')";

		//db.execSQL(sqlBook);
		db.execSQL(setup);
		createFoldersTable(db);
		createFilesTable(db);
	}

	private void createFoldersTable(SQLiteDatabase db)
	{
		String sqlCreateFolderTable = String
				.format("create table %s"
						+ "(%s text primary key,%s text,%s text, %s text, %s text)",
						Constants.TABLE_FOLDERS , Constants.KEY_FOLDER_ID,
						Constants.KEY_FOLDER_NAME,Constants.KEY_FOLDERS_SERVER_ID,
						Constants.KEY_FOLDERS_NUMBER_OF_FILES,
						Constants.KEY_FOLDERS_UPDATED_COUNTER);
		db.execSQL(sqlCreateFolderTable);


	}
	private void createFilesTable(SQLiteDatabase db)
	{


		String sqlCreateFolderTable = String
				.format("create table %s"
						+ "(%s text primary key ,"
						+ "%s text,"
						+ "%s text, "
						+ "%s text, "
						+ "%s text,"
						+ " %s text, "
						+ "%s text, "
						+ "%s text, "
						+ "%s text, "
						+ "%s text)",
						Constants.TABLE_FILES,
						Constants.KEY_FILE_ID,
						Constants.KEY_FILE_FOLDER_SERVER_ID,
						Constants.KEY_FILE_DATE,
						Constants.KEY_FILE_DESCRIPTION,
						Constants.KEY_FILE_LINK,
						Constants.KEY_FILE_NAME,
						Constants.KEY_FILE_GRANT_ACCESS_TO_NA,
						Constants.KEY_FILE_THUMB_PATH,
						Constants.KEY_FILE_TITLE,
						Constants.KEY_FILE_TYPE_ID);
		db.execSQL(sqlCreateFolderTable);
	}
	private void dropFoldersTable(SQLiteDatabase db)
	{


		String sql = " DROP TABLE IF EXISTS " + Constants.TABLE_FOLDERS;
		db.execSQL(sql);
	}
	private void dropFilesTable(SQLiteDatabase db)
	{


		String sql = " DROP TABLE IF EXISTS " + Constants.TABLE_FILES;
		db.execSQL(sql);
	}
	public List<eWatheqFolder> getFoldersInfo() {
		return getFoldersInfo("");
	}
	public List<eWatheqFolder> getFoldersInfo(String folderServerId) {
		String where = Constants.KEY_FOLDERS_SERVER_ID+ "=?";
		String[] whereValue = { folderServerId };

		List<eWatheqFolder> folders = new ArrayList<eWatheqFolder>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor;
		if (folderServerId != null && !folderServerId.isEmpty())
			cursor = db.query(Constants.TABLE_FOLDERS, null, where, whereValue,
					null, null, Constants.KEY_FOLDERS_SERVER_ID + " ASC");
		else
			cursor = db.query(Constants.TABLE_FOLDERS, null, null, null,
					null, null, Constants.KEY_FOLDERS_SERVER_ID + " ASC");

		if (cursor == null || cursor.isClosed() ||cursor.getCount()==0 )
		{
			//insertDefaultFolders();
			if (cursor != null && !cursor.isClosed() )
				cursor.close();
			cursor = db.query(Constants.TABLE_FOLDERS, null, null, null,
					null, null, Constants.KEY_FOLDER_NAME + " ASC");
		}
		if (cursor != null && !cursor.isClosed()) {
			int count = cursor.getCount();
			cursor.moveToPosition(0);
			for (int i = 0; i < count; i++) {
				try {
					if (cursor != null) {
						eWatheqFolder folder= new eWatheqFolder();
						folder.CategoryID = cursor.getString(cursor.getColumnIndex(Constants.KEY_FOLDERS_SERVER_ID));
						folder.CategoryName = cursor.getString(cursor.getColumnIndex(Constants.KEY_FOLDER_NAME));
						folder.folderId = cursor.getString(cursor.getColumnIndex(Constants.KEY_FOLDER_ID));
						folder.NoOfFiles = cursor.getString(cursor.getColumnIndex(Constants.KEY_FOLDERS_NUMBER_OF_FILES));
						folder.UpdateCounter= cursor.getString(cursor.getColumnIndex(Constants.KEY_FOLDERS_UPDATED_COUNTER));

						folders.add(folder);

					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				cursor.moveToNext();
			}
		}
		cursor.close();
		return folders;
	}
	public List<eWatheqFile> getFiles(String folderServerId) {
		String where = Constants.KEY_FILE_FOLDER_SERVER_ID+ "=?";
		String[] whereValue = { folderServerId };
		List<eWatheqFile> files = new ArrayList<eWatheqFile>();

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(Constants.TABLE_FILES, null, where, whereValue,
				null, null, Constants.KEY_FILE_NAME + " ASC");
		
		if (cursor != null && !cursor.isClosed()) {
			int count = cursor.getCount();
			cursor.moveToPosition(0);
			for (int i = 0; i < count; i++) {
				try {
					if (cursor != null) {
						eWatheqFile file = new eWatheqFile();
						file.CategoryID = cursor.getString(cursor.getColumnIndex(Constants.KEY_FILE_FOLDER_SERVER_ID));
						file.Date = cursor.getString(cursor.getColumnIndex(Constants.KEY_FILE_DATE));
						file.Description= cursor.getString(cursor.getColumnIndex(Constants.KEY_FILE_DESCRIPTION));
						file.FileID = cursor.getString(cursor.getColumnIndex(Constants.KEY_FILE_ID));
						file.FileLink = cursor.getString(cursor.getColumnIndex(Constants.KEY_FILE_LINK));
						file.FileNamewithextension = cursor.getString(cursor.getColumnIndex(Constants.KEY_FILE_NAME));
						file.IsShared = cursor.getString(cursor.getColumnIndex(Constants.KEY_FILE_GRANT_ACCESS_TO_NA));
						file.Thumbnaillink = cursor.getString(cursor.getColumnIndex(Constants.KEY_FILE_THUMB_PATH));
						file.Title = cursor.getString(cursor.getColumnIndex(Constants.KEY_FILE_TITLE));
						
						file.TypeID = cursor.getString(cursor.getColumnIndex(Constants.KEY_FILE_TYPE_ID));


						files.add(file);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				cursor.moveToNext();
			}
		}
		cursor.close();
		return files;
	}
	public int getFilesCounter(String folderServerId) {
		String where = Constants.KEY_FILE_FOLDER_SERVER_ID+ "=?";
		String[] whereValue = { folderServerId };
		

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(Constants.TABLE_FILES, null, where, whereValue,
				null, null, Constants.KEY_FILE_NAME + " ASC");
		int count = -1;
		if (cursor != null && !cursor.isClosed()) {
			 count = cursor.getCount();	
		}
		cursor.close();
		return count;
	}
	public void insertFile(eWatheqFile file) {
		ContentValues values = new ContentValues();

		values.put(Constants.KEY_FILE_FOLDER_SERVER_ID, file.CategoryID);
		values.put(Constants.KEY_FILE_DATE, file.Date);
		values.put(Constants.KEY_FILE_DESCRIPTION, file.Description);
		values.put(Constants.KEY_FILE_ID, file.FileID);
		values.put(Constants.KEY_FILE_LINK , file.FileLink);
		values.put(Constants.KEY_FILE_NAME, file.FileNamewithextension);
		values.put(Constants.KEY_FILE_GRANT_ACCESS_TO_NA , file.IsShared);
		values.put(Constants.KEY_FILE_THUMB_PATH , file.Thumbnaillink);
		values.put(Constants.KEY_FILE_TITLE, file.Title);
		values.put(Constants.KEY_FILE_TYPE_ID, file.TypeID);

		SQLiteDatabase db = this.getWritableDatabase();
		long insert = db.insertWithOnConflict(Constants.TABLE_FILES, null, values,
				SQLiteDatabase.CONFLICT_IGNORE);
		Log.e("", ""+insert);
	}
	public void insertFolder(eWatheqFolder folder, SQLiteDatabase db) {
		ContentValues values = new ContentValues();

		values.put(Constants.KEY_FOLDER_NAME, folder.CategoryName);
		values.put(Constants.KEY_FOLDERS_SERVER_ID, folder.CategoryID);
		values.put(Constants.KEY_FOLDERS_NUMBER_OF_FILES, folder.NoOfFiles);
		values.put(Constants.KEY_FOLDERS_UPDATED_COUNTER, folder.UpdateCounter);

		long insert = db.insertWithOnConflict(Constants.TABLE_FOLDERS, null, values,
				SQLiteDatabase.CONFLICT_IGNORE);
		Log.e("", ""+insert);

	}
	public void insertFolder(List<eWatheqFolder> folders) {

		
		
		if (folders.size() >0)
		{
			deleteAllFolders();
		}
		SQLiteDatabase db = this.getWritableDatabase();
		for (int i=0;i<folders.size();i++){
			eWatheqFolder folder = folders.get(i);
	
			insertFolder(folder,db);
		}
		db.close();
	}
	/*public void insertDefaultFolders() {
		String [] defaultFolders = context.getResources().getStringArray(R.array.default_folders);
		for (int i = 0; i<defaultFolders.length; i++)
		{
			ContentValues values = new ContentValues();
			values.put(Constants.KEY_FOLDER_ID, i);
			values.put(Constants.KEY_FOLDER_NAME, defaultFolders[i]);
			values.put(Constants.KEY_FOLDERS_SERVER_ID, i);
			values.put(Constants.KEY_FOLDERS_NUMBER_OF_FILES, 0);
			values.put(Constants.KEY_FOLDERS_UPDATED_COUNTER, 0);
			SQLiteDatabase db = this.getWritableDatabase();
			long insert = db.insertWithOnConflict(Constants.TABLE_FOLDERS, null, values,
					SQLiteDatabase.CONFLICT_IGNORE);
			Log.e("", ""+insert);
		}

	}*/
	public void updateFile(eWatheqFile file) {
		SQLiteDatabase db = this.getWritableDatabase();
		String where = Constants.KEY_FILE_ID + "=?";
		String[] whereValue = { ""+file.FileID };
		ContentValues values = new ContentValues();
		values.put(Constants.KEY_FILE_FOLDER_SERVER_ID, file.CategoryID);
		values.put(Constants.KEY_FILE_DATE, file.Date);
		values.put(Constants.KEY_FILE_DESCRIPTION, file.Description);

		values.put(Constants.KEY_FILE_LINK , file.FileLink);
		values.put(Constants.KEY_FILE_NAME, file.FileNamewithextension);
		values.put(Constants.KEY_FILE_GRANT_ACCESS_TO_NA , file.IsShared);
		values.put(Constants.KEY_FILE_THUMB_PATH , file.Thumbnaillink);
		values.put(Constants.KEY_FILE_TITLE, file.Title);
		values.put(Constants.KEY_FILE_TYPE_ID, file.TypeID);

		db.update(Constants.TABLE_FILES , values, where, whereValue);
	}
	public void updateFolder(eWatheqFolder folder) {
		SQLiteDatabase db = this.getWritableDatabase();
		String where = Constants.KEY_FOLDERS_SERVER_ID+ "=?";
		String[] whereValue = { folder.CategoryID};
		ContentValues cv = new ContentValues();
		cv.put(Constants.KEY_FOLDER_NAME, folder.CategoryName);
		cv.put(Constants.KEY_FOLDERS_UPDATED_COUNTER , folder.UpdateCounter);
		cv.put(Constants.KEY_FOLDERS_NUMBER_OF_FILES, folder.NoOfFiles);
		db.update(Constants.TABLE_FOLDERS, cv, where, whereValue);
		db.close();
	}
	public void deleteFolder(eWatheqFolder folder) {
		SQLiteDatabase db = this.getWritableDatabase();
		String where = Constants.KEY_FOLDER_ID + "=?";
		String[] whereValue = { folder.CategoryID};

		db.delete(Constants.TABLE_FOLDERS, where, whereValue);
		db.close();
	}
	public void deleteFile(eWatheqFile file) {
		SQLiteDatabase db = this.getWritableDatabase();
		String where = Constants.KEY_FILE_ID+ "=?";
		String[] whereValue = { file.FileID};

		db.delete(Constants.TABLE_FILES, where, whereValue);
		db.close();
	}
	public void deleteAllFolders() {
		SQLiteDatabase db = this.getWritableDatabase();
		

		db.delete(Constants.TABLE_FOLDERS, null, null);
		db.close();
	}
	public void deleteAllFiles() {
		SQLiteDatabase db = this.getWritableDatabase();
		

		db.delete(Constants.TABLE_FILES, null, null);
		db.close();
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		String sql = " DROP TABLE IF EXISTS " + TABLE_NAME;
		db.execSQL(sql);
		db.execSQL("drop table if exists" + Constants.Book_Table_Name);
		dropFoldersTable(db);
		dropFilesTable(db);
		onCreate(db);
	}


	public Cursor select() {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null,
				" _id desc");
		return cursor;
	}



	public void deleteDownload(String primaryKey) {
		SQLiteDatabase db = this.getReadableDatabase();
		db.delete(Constants.Book_Table_Name, Constants.book_ID + "=" + primaryKey, null);

	}
	public int isUserAvailableDownload(int item_id) {
		int number = 0;
		Cursor c = null;
		try {

			SQLiteDatabase db = this.getReadableDatabase();

			c = db.rawQuery("select " + Constants.book_ID + " from "
					+ Constants.Book_Table_Name + " where " + Constants.book_ID
					+ "=?", new String[] { String.valueOf(item_id) });

			if (c.getCount() != 0)
				number = c.getCount();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (c != null)
				c.close();
		}
		return number;
	}

	/*public List<BookRecord> getAllBookInfo() {
		List<BookRecord> books = new ArrayList<BookRecord>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(Constants.Book_Table_Name, null, null, null,
				null, null, Constants.F_time + " DESC");
		if (cursor != null && !cursor.isClosed()) {

			int count = cursor.getCount();
			cursor.moveToPosition(0);
			for (int i = 0; i < count; i++) {
				try {

					if (cursor != null) {

						String path=cursor.getString(cursor
								.getColumnIndex(Constants.file_path));
						if (path != null && path.contains("/"))
						{
							path= path.substring(path.lastIndexOf("/")+1);
						}
						File file = new File(
								Environment.getExternalStorageDirectory()+"/"
										+ Constants.LOCAL_DIR+"/"+path);
						if(!file.exists()){
							deleteDownload(	cursor.getString(cursor
									.getColumnIndex(Constants.book_ID)));
						}

						else{
							BookRecord info = new BookRecord(

									cursor.getString(cursor.getColumnIndex(Constants.book_ID)),
									cursor.getString(cursor.getColumnIndex(Constants.book_title)),
									cursor.getString(cursor.getColumnIndex(Constants.BOOK_LANGUAGE)),
									cursor.getString(cursor.getColumnIndex(Constants.book_category)),
									cursor.getString(cursor.getColumnIndex(Constants.file_size)),
									cursor.getString(cursor.getColumnIndex(Constants.thumbail_path)),
									cursor.getString(cursor.getColumnIndex(Constants.file_path)),
									cursor.getString(cursor.getColumnIndex(Constants.NameArabic)),
									cursor.getString(cursor.getColumnIndex(Constants.NameEnglish)),
									cursor.getString(cursor.getColumnIndex(Constants.F_time))

									);

							books.add(info);
						}
						cursor.moveToNext();
					}

					// parse data here
				} catch (Exception e) {
					// TODO Auto-generated catch block
					// e.printStackTrace();
				}
			}
		}
		cursor.close();

		return books;
	}*/
	public boolean isBookDownloaded(BookJSON book) {
		int count=0;
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(true, Constants.Book_Table_Name, new String[] {
				Constants.book_ID}, 
				Constants.book_ID+ "=?" , 
				new String[] {book.BookId},
				null, null,null , null);



		if (cursor != null && !cursor.isClosed()) {

			count = cursor.getCount();
			if (count>0)
			{
				String path=book.FileURL;
				if (path != null && path.contains("/"))
				{
					path= path.substring(path.lastIndexOf("/")+1);
				}
				File file = new File(
						Environment.getExternalStorageDirectory()+"/"
								+ Constants.LOCAL_DIR+"/"+path);
				if(file.exists()){
					return true;
				}
			}
			cursor.close();
		}


		return false;
	}
	public String[] getAllMyLibraryBookCategories() {
		String [] categories;
		SQLiteDatabase db = this.getReadableDatabase();
		String selecet = "SELECT DISTINCT " + Constants.NameArabic+" , "+  Constants.NameEnglish+" FROM " + Constants.Book_Table_Name;
		Cursor cursor = db.rawQuery(selecet, null);
		/*Cursor cursor = db.query(Constants.Book_Table_Name, null, null, null,
				null, null, Constants.F_time + " DESC");*/
		categories = new String [cursor.getCount()+1];
		categories[0] = context.getString(R.string.str_all);
		if (cursor != null && !cursor.isClosed()) {

			int count = cursor.getCount();
			cursor.moveToPosition(0);
			for (int i = 0; i < count; i++) {
				try {

					if(langId == Constants.LANGUAGE_ID_AR)
						categories[i+1] = cursor.getString(cursor.getColumnIndex(Constants.NameArabic));
					else 
						categories[i+1] = cursor.getString(cursor.getColumnIndex(Constants.NameEnglish));

					cursor.moveToNext();
				}
				catch (Exception e) {
					// TODO Auto-generated catch block
					// e.printStackTrace();
				}
				// parse data here
			} 

		}
		cursor.close();

		return categories;
	}
	public void deleteSelectedBooks(List<BookRecord> books) {
		SQLiteDatabase db = this.getReadableDatabase();
		//return db.delete(Constants.Book_Table_Name, Constants.book_ID+ " = ?",new String[] { bus_num });
		for(int i = 0 ; i < books.size();i++)
		{
			BookRecord book = books.get(i);
			try{

				if (db.delete(Constants.Book_Table_Name, Constants.book_ID+ " = ?",new String[] { book.book_id })>0)
				{
					EWatheqUtils.deleteFileFromLocal(book.book_path);
					EWatheqUtils.deleteFileFromLocal(book.thumbail_path);
				}
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}

	}



	public long insert(String Title) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues cv = new ContentValues();

		cv.put(FIELD_BOOKMARK, Title);
		long row = db.insert(TABLE_NAME, null, cv);
		return row;
	}

	/*public void insertBook(BookJSON BookRecord, BookJSONWithCategory category) {

		ContentValues values = new ContentValues();

		Calendar c = Calendar.getInstance();

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String formattedDate = df.format(c.getTime());

		values.put(Constants.book_ID, BookRecord.BookId);
		values.put(Constants.book_title, BookRecord.BookTitle);
		values.put(Constants.BOOK_LANGUAGE, BookRecord.Language.NameEnglish);
		if(category!=null&&category.CategoryId!=null){
			values.put(Constants.book_category, category.CategoryId);
		}
		else{
			values.put(Constants.book_category, "");
		}
		values.put(Constants.file_size, "");

		if (isTablet) {
			values.put(Constants.thumbail_path, BookRecord.CoverThumbURLTablet.URL);
		} else {
			values.put(Constants.thumbail_path, BookRecord.CoverThumbURLPhone.URL);

		}
		values.put(Constants.file_path, BookRecord.FileURL);


		if(category!=null&&category.NameArabic!=null){
			values.put(Constants.NameArabic, category.NameArabic);
		}
		else{
			values.put(Constants.NameArabic, "");
		}
		if(category!=null&&category.NameEnglish!=null){
			values.put(Constants.NameEnglish, category.NameEnglish);
		}
		else{
			values.put(Constants.NameEnglish, "");
		}




		values.put(Constants.F_time, formattedDate);

		// db.insert(TABLE,null, values);
		// db = db.getWritableDatabase();

		SQLiteDatabase db = this.getWritableDatabase();
		long insert = db.insertWithOnConflict(Constants.Book_Table_Name, null, values,
				SQLiteDatabase.CONFLICT_IGNORE);
		Log.e("", ""+insert);
	}*/

	public long insert(String filename, String bookmark) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put(FIELD_FILENAME, filename);
		cv.put(FIELD_BOOKMARK, bookmark);
		long row = db.insert(TABLE_NAME, null, cv);
		return row;
	}

	public void deleteBook(String primaryKey) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(Constants.Book_Table_Name, Constants.book_ID + "="
				+ primaryKey, null);

	}


	public void delete(int id) {
		SQLiteDatabase db = this.getWritableDatabase();
		String where = FIELD_ID + "=?";
		String[] whereValue = { Integer.toString(id) };
		db.delete(TABLE_NAME, where, whereValue);
	}


	public void update(int id, String filename, String bookmark) {
		SQLiteDatabase db = this.getWritableDatabase();
		String where = FIELD_ID + "=?";
		String[] whereValue = { Integer.toString(id) };
		ContentValues cv = new ContentValues();
		cv.put(FIELD_FILENAME, filename);
		cv.put(FIELD_BOOKMARK, bookmark);
		db.update(TABLE_NAME, cv, where, whereValue);
	}


	public void updateSetup(int id, String fontsize, String rowspace,
			String columnspace) {
		SQLiteDatabase db = this.getWritableDatabase();
		String where = FIELD_ID + "=?";
		String[] whereValue = { Integer.toString(id) };
		ContentValues cv = new ContentValues();
		cv.put(FONT_SIZE, fontsize);
		cv.put(ROW_SPACE, rowspace);
		cv.put(COLUMN_SPACE, columnspace);
		db.update(TABLE_SETUP, cv, where, whereValue);
	}
}
