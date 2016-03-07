package ae.ewatheq.db.helper;
/*package com.adibf.db.helper;

public class adibfDataBaseHelperBackup {

}
package com.adibf.db.helper;



import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;




import java.util.Vector;

import com.adibf.models.Author;
import com.adibf.models.Book;
import com.adibf.models.Category;
import com.adibf.models.Country;
import com.adibf.models.Publisher;
import com.adibf.models.Year;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class AdibfDatabaseHelper extends SQLiteOpenHelper {

	// Logcat tag
	private static final String LOG = "DatabaseHelper";

	
	private static AdibfDatabaseHelper _dbHelper;
	 
    //The Android's default system path of your application database.
    //private static String DB_PATH = "/data/data/YOUR_PACKAGE/databases/";
    private static String DB_PATH = "/data/data/com.adibf/adibf_multi_tables/";
    //database name
    private static String DB_NAME = "adibf_multi_tables";
    //reference of database
    private SQLiteDatabase _adibfSqliteDb;
    //
    private String _searchToken;
 
    //
    private final Context _context;
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "adibf_multi_tables";

	// Table Names
	private static final String TABLE_BOOK = "BOOK";
	private static final String TABLE_AUTHOR = "author";
	private static final String TABLE_CATEGORY = "category";
	private static final String TABLE_COUNTRY = "country";
	private static final String TABLE_PUBLISHER = "publisher";
	private static final String TABLE_YEAR = "year";


	// BOOK Table - column nmaes
	private static final String KEY_BOOK_ID = "book_id";
	private static final String KEY_BOOK_NAME = "BOOK_TITLE";
	private static final String KEY_BOOK_AUTHER = "BOOK_AUTHOR";
	
	private static final String KEY_BOOK_YEAR = "BOOK_YEAR";
	
	private static final String KEY_BOOK_CATEGORY = "BOOK_CATEGORY";
	
	private static final String KEY_BOOK_COUNTRY = "BOOK_COUNTRY";
	
	private static final String KEY_BOOK_PUBLISHER_ID = "BOOK_PUBLISHER";
	
	


	// AUTHOR Table - column nmaes
		
	private static final String KEY_AUTHER_ID = "author_id";
	private static final String KEY_AUTHER_NAME = "author_name";
		
		
	// YEAR Table - column nmaes
		
	private static final String KEY_YEAR_ID = "year_id";
	private static final String KEY_YEAR_NAME = "year_name";
		
	
	// CATEGORY Table - column nmaes
	
	private static final String KEY_CATEGORY_ID = "category_id";
	private static final String KEY_CATEGORY_NAME = "category_name";
	
	
	// COUNTRY Table - column nmaes
	
	private static final String KEY_COUNTRY_ID = "country_id";
	private static final String KEY_COUNTRY_NAME = "country_name";
	
	
	// PUBLISHER Table - column nmaes
	
	private static final String KEY_PUBLISHER_ID = "publisher_id";
	private static final String KEY_PUBLISHER_NAME = "publisher_name";
	
	
	

	// Table Create Statements
	// BOOK table create statement
	private static final String CREATE_TABLE_BOOK = "CREATE TABLE "
			+ TABLE_BOOK + "(" + KEY_BOOK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_BOOK_NAME+ " TEXT," + 
			KEY_BOOK_AUTHER_ID + " INTEGER," + KEY_BOOK_AUTHER_NAME+ " TEXT" +
			KEY_BOOK_CATEGORY_ID + " INTEGER," + KEY_BOOK_CATEGORY_NAME+ " TEXT" +
			KEY_BOOK_COUNTRY_ID + " INTEGER," + KEY_BOOK_COUNTRY_NAME+ " TEXT" +
			KEY_BOOK_PUBLISHER_ID + " INTEGER," + KEY_BOOK_PUBLISHER_NAME+ " TEXT" +
			KEY_BOOK_YEAR_ID + " INTEGER," + KEY_BOOK_YEAR_NAME+ " TEXT" +")";

	
	
	// AUTHOR table create statement
	private static final String CREATE_TABLE_AUTHOR = "CREATE TABLE " + TABLE_AUTHOR
			+ "(" + KEY_AUTHER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_AUTHER_NAME + " TEXT"+ 
			")";
	
	
	// CATEGORY table create statement
	private static final String CREATE_TABLE_CATEGORY = "CREATE TABLE " + TABLE_CATEGORY
				+ "(" + KEY_CATEGORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_CATEGORY_NAME + " TEXT"+ 
				")";
		
		
	// YEAR table create statement
	private static final String CREATE_TABLE_YEAR = "CREATE TABLE " + TABLE_YEAR
				+ "(" + KEY_YEAR_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_YEAR_NAME + " TEXT"+ 
				")";
	
	// COUNTRY table create statement
	private static final String CREATE_TABLE_COUNTRY = "CREATE TABLE " + TABLE_COUNTRY
					+ "(" + KEY_COUNTRY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_COUNTRY_NAME + " TEXT"+ 
					")";
		
	// PUBLISHER table create statement
	private static final String CREATE_TABLE_PUBLISHER = "CREATE TABLE " + TABLE_PUBLISHER
							+ "(" + KEY_PUBLISHER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_PUBLISHER_NAME + " TEXT"+ 
							")";

	
	
	

	public AdibfDatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	private AdibfDatabaseHelper(Context context)
    {
        super(context, DB_NAME, null, DATABASE_VERSION);
        this._context = context;
      
    }
    public static AdibfDatabaseHelper getInstance(Context context)
    {
        if(_dbHelper == null)
        {
            _dbHelper = new AdibfDatabaseHelper(context);
        }
       return _dbHelper;
    }

	@Override
	public void onCreate(SQLiteDatabase db) {
		// creating required tables
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// on upgrade drop older tables
		
	}
	public void createDataBase() throws IOException
    {
        boolean dbExist = checkDataBase();
 
        if(dbExist)
        {
            //do nothing - database already exist
        }else{
 
            this.getReadableDatabase();
 
            try {
 
                copyDataBase();
 
            } catch (IOException e) {
 
                throw new Error("Error copying database");
 
            }
        }
 
    }
 
    private boolean checkDataBase()
    {
        SQLiteDatabase checkDB = null;
 
        try
        {
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
 
        }catch(SQLiteException e)
        {
            //database does't exist yet.
        }
        if(checkDB != null)
        {
            checkDB.close();
        }
 
        return checkDB != null ? true : false;
    }
    private void copyDataBase() throws IOException
    {
        //Open your local db as the input stream
        InputStream myInput = _context.getAssets().open(DB_NAME);
 
        // Path to the just created empty db
        String outFileName = DB_PATH + DB_NAME;
 
        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);
 
        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0)
        {
            myOutput.write(buffer, 0, length);
        }
        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }
	// ------------------------ "todos" table methods ----------------//

	
	 * Creating a book
	 
	public Book createBook(Book book) {
		
		
		SQLiteDatabase db = this.getWritableDatabase();
		
		
		book.setAuthor(createAuthor(db ,book.getAuthor().getAuthorName()));
		book.setCategory(createCategory(db ,book.getCategory().getCategoryName()));
		book.setCountry(createCountry(db ,book.getCountry().getCountryName()));
		book.setPublisher(createPublisher(db ,book.getPublisher().getPublisherName()));
		book.setYear(createYear(db ,book.getYear().getYearName()));
		
		
		
		
		
		
		
		
		
		
		ContentValues values = new ContentValues();
		values.put(KEY_BOOK_NAME, book.getBookName());
		
		values.put(KEY_BOOK_AUTHER_ID, book.getAuthor().getAuthorId());
		values.put(KEY_BOOK_AUTHER_NAME, book.getAuthor().getAuthorName());
		
		
		values.put(KEY_BOOK_CATEGORY_ID, book.getCategory().getCategoryId());
		values.put(KEY_BOOK_CATEGORY_NAME, book.getCategory().getCategoryName());
		
		values.put(KEY_BOOK_COUNTRY_ID, book.getCountry().getCountryId());
		values.put(KEY_BOOK_COUNTRY_NAME, book.getCountry().getCountryName());
		
		values.put(KEY_BOOK_PUBLISHER_ID, book.getPublisher().getPublisherId());
		values.put(KEY_BOOK_PUBLISHER_NAME, book.getPublisher().getPublisherName());
		
		values.put(KEY_BOOK_YEAR_ID, book.getYear().getYearId());
		values.put(KEY_BOOK_YEAR_NAME, book.getYear().getYearName());
		


		// insert row
		long book_id = db.insert(TABLE_BOOK, null, values);
		book.setBookId((int)book_id); 
		// insert tag_ids
		

		return book;
	}
	
	
	
	 * Creating a book
	 
	public Author createAuthor(SQLiteDatabase db , String author_name) {
		
		if (author_name == null || author_name.equalsIgnoreCase(""))
			return new Author();
		
		
		

		ContentValues values = new ContentValues();
		values.put(KEY_AUTHER_NAME, author_name);
		

		// insert row
		long aut_id = db.insert(TABLE_AUTHOR, null, values);
		
		// insert tag_ids
		Author author = new Author((int) aut_id, author_name);

		return author ;
	}
	
	
	
	 * Creating a book
	 
	public Category createCategory(SQLiteDatabase db ,String cat_name) {
		
		if (cat_name == null || cat_name.equalsIgnoreCase(""))
			return new Category();
		
		

		ContentValues values = new ContentValues();
		values.put(KEY_CATEGORY_NAME, cat_name);
		

		// insert row
		long cat_id = db.insert(TABLE_CATEGORY, null, values);
		
		// insert tag_ids
		Category category = new Category((int) cat_id, cat_name);

		return category ;
	}
	
	
	
	 * Creating a book
	 
	public Country createCountry(SQLiteDatabase db ,String country_name) {
		
		

		if (country_name == null || country_name.equalsIgnoreCase(""))
			return new Country();
		
		
		
		

		ContentValues values = new ContentValues();
		values.put(KEY_COUNTRY_NAME, country_name);
		

		// insert row
		long cou_id = db.insert(TABLE_COUNTRY, null, values);
		
		// insert tag_ids
		Country country = new Country((int) cou_id, country_name);

		return country ;
	}
	
	
	
	 * Creating a book
	 
	public Publisher createPublisher(SQLiteDatabase db ,String publisher_name) {
		
		
		if (publisher_name == null || publisher_name.equalsIgnoreCase(""))
			return new Publisher();
		
		
		
		

		ContentValues values = new ContentValues();
		values.put(KEY_PUBLISHER_NAME, publisher_name);
		

		// insert row
		long pub_id = db.insert(TABLE_PUBLISHER, null, values);
		
		// insert tag_ids
		Publisher author = new Publisher((int) pub_id, publisher_name);

		return author ;
	}
	
	
	
	 * Creating a book
	 
	public Year createYear(SQLiteDatabase db ,String year_name) {
		
		
		if (year_name == null || year_name.equalsIgnoreCase(""))
			return new Year();
		
		
		
		ContentValues values = new ContentValues();
		values.put(KEY_AUTHER_NAME, year_name);
		

		// insert row
		long year_id = db.insert(TABLE_AUTHOR, null, values);
		
		// insert tag_ids
		Year author = new Year((int) year_id, year_name);

		return author ;
	}
	
	
	
	
	
	
	
	
	
	
	
	 public void openDataBase() throws SQLException{
    	 
	    	//Open the database
	        String myPath = DB_PATH + DB_NAME;
	        _adibfSqliteDb = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
	 
	    }
	
	

	
	 * get single todo
	 
	public Book getBook(long book_id) {
		 openDataBase();

		String selectQuery = "SELECT  * FROM " + TABLE_BOOK + " WHERE "
				+ KEY_BOOK_ID + " = " + book_id;

		Log.e(LOG, selectQuery);

		Cursor c = _adibfSqliteDb.rawQuery(selectQuery, null);

		if (c != null)
			c.moveToFirst();

		Book book = new Book();
		
		
		
		book.setAuthor(aut);AuthorId(c.getInt(c.getColumnIndex(KEY_BOOK_AUTHER_ID)));
		author.setAuthorName(c.getString(c.getColumnIndex(KEY_BOOK_AUTHER_NAME)));
		
		book.setBookId(c.getInt(c.getColumnIndex(KEY_BOOK_ID)));
		book.setBookName(c.getString(c.getColumnIndex(KEY_BOOK_NAME)));
		
		
		category.setCategoryId(c.getInt(c.getColumnIndex(KEY_BOOK_CATEGORY_ID)));
		category.setCategoryName(c.getString(c.getColumnIndex(KEY_BOOK_CATEGORY_NAME)));
		
		
		country.setCountryId(c.getInt(c.getColumnIndex(KEY_BOOK_COUNTRY_ID)));
		country.setCountryName(c.getString(c.getColumnIndex(KEY_BOOK_COUNTRY_NAME)));
		
		
		publisher.setPublisherId(c.getInt(c.getColumnIndex(KEY_BOOK_PUBLISHER_ID)));
		publisher.setPublisherName(c.getString(c.getColumnIndex(KEY_BOOK_PUBLISHER_NAME)));
		
		
		year.setYearId(c.getInt(c.getColumnIndex(KEY_BOOK_YEAR_ID)));
		year.setYearName(c.getString(c.getColumnIndex(KEY_BOOK_YEAR_NAME)));
		
		book.setAuthor(author);
		book.setCategory(category);
		book.setCountry(country);
		book.setPublisher(publisher);
		book.setYear(year);
		
		
		

		return book;
	}

	*//**
	 * getting all Books
	 * *//*
	public List<Book> getAllBooks() {
		List<Book> books = new ArrayList<Book>();
		String selectQuery = "SELECT  * FROM " + TABLE_BOOK;

		Log.e(LOG, selectQuery);

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (c.moveToFirst()) {
			do {
				Todo td = new Todo();
				td.setId(c.getInt((c.getColumnIndex(KEY_ID))));
				td.setNote((c.getString(c.getColumnIndex(KEY_TODO))));
				td.setCreatedAt(c.getString(c.getColumnIndex(KEY_CREATED_AT)));
				
				Book book = new Book();
				Author author = new Author();
				Category category = new Category();
				Country country = new Country();
				Publisher publisher = new Publisher();
				Year year = new Year();
				
				
				author.setAuthorId(c.getInt(c.getColumnIndex(KEY_BOOK_AUTHER_ID)));
				author.setAuthorName(c.getString(c.getColumnIndex(KEY_BOOK_AUTHER_NAME)));
				
				book.setBookId(c.getInt(c.getColumnIndex(KEY_BOOK_ID)));
				book.setBookName(c.getString(c.getColumnIndex(KEY_BOOK_NAME)));
				
				
				category.setCategoryId(c.getInt(c.getColumnIndex(KEY_BOOK_CATEGORY_ID)));
				category.setCategoryName(c.getString(c.getColumnIndex(KEY_BOOK_CATEGORY_NAME)));
				
				
				country.setCountryId(c.getInt(c.getColumnIndex(KEY_BOOK_COUNTRY_ID)));
				country.setCountryName(c.getString(c.getColumnIndex(KEY_BOOK_COUNTRY_NAME)));
				
				
				publisher.setPublisherId(c.getInt(c.getColumnIndex(KEY_BOOK_PUBLISHER_ID)));
				publisher.setPublisherName(c.getString(c.getColumnIndex(KEY_BOOK_PUBLISHER_NAME)));
				
				
				year.setYearId(c.getInt(c.getColumnIndex(KEY_BOOK_YEAR_ID)));
				year.setYearName(c.getString(c.getColumnIndex(KEY_BOOK_YEAR_NAME)));
				
				book.setAuthor(author);
				book.setCategory(category);
				book.setCountry(country);
				book.setPublisher(publisher);
				book.setYear(year);
				

				// adding to todo list
				books.add(book);
			} while (c.moveToNext());
		}

		return books;
	}

	
	

	
	 * getting Books count
	 
	public int getBookCount() {
		String countQuery = "SELECT  * FROM " + TABLE_BOOK;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);

		int count = cursor.getCount();
		cursor.close();

		// return count
		return count;
	}

	
	 * Updating a Book
	 
	public int updateBook(Book book) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_BOOK_AUTHER_NAME, book.getAuthor().getAuthorName());
		values.put(KEY_BOOK_NAME, book.getBookName());
		values.put(KEY_BOOK_CATEGORY_NAME, book.getCategory().getCategoryName());
		values.put(KEY_BOOK_COUNTRY_NAME, book.getCountry().getCountryName());
		values.put(KEY_BOOK_PUBLISHER_NAME, book.getPublisher().getPublisherName());
		values.put(KEY_BOOK_YEAR_NAME, book.getPublisher().getPublisherName());
		

		// updating row
		return db.update(TABLE_BOOK, values, KEY_BOOK_ID + " = ?",
				new String[] { String.valueOf(book.getBookId()) });
	}

	
	 * Deleting a todo
	 
	public void delete(long tado_id) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_TODO, KEY_ID + " = ?",
				new String[] { String.valueOf(tado_id) });
	}

	// ------------------------ "tags" table methods ----------------//

	
	 * Creating tag
	 
	
	// closing database
	public void closeDB() {
		SQLiteDatabase db = this.getReadableDatabase();
		if (db != null && db.isOpen())
			db.close();
	}

	
	
}












































package com.adibf.db.helper;



import java.util.ArrayList;

import java.util.List;


import com.adibf.models.Author;
import com.adibf.models.Book;
import com.adibf.models.Category;
import com.adibf.models.Country;
import com.adibf.models.Publisher;
import com.adibf.models.Year;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class AdibfDatabaseHelper extends SQLiteOpenHelper {

	// Logcat tag
	private static final String LOG = "DatabaseHelper";

	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "adibfManager";

	// Table Names
	private static final String TABLE_BOOK = "book";
	private static final String TABLE_AUTHOR = "author";
	private static final String TABLE_CATEGORY = "category";
	private static final String TABLE_COUNTRY = "country";
	private static final String TABLE_PUBLISHER = "publisher";
	private static final String TABLE_YEAR = "year";


	// BOOK Table - column nmaes
	private static final String KEY_BOOK_ID = "book_id";
	private static final String KEY_BOOK_NAME = "book_name";
	private static final String KEY_BOOK_AUTHER_ID = "book_author_id";
	private static final String KEY_BOOK_AUTHER_NAME = "book_author_name";
	private static final String KEY_BOOK_YEAR_ID = "book_year_id";
	private static final String KEY_BOOK_YEAR_NAME = "book_year_name";
	private static final String KEY_BOOK_CATEGORY_ID = "book_category_id";
	private static final String KEY_BOOK_CATEGORY_NAME = "book_category_name";
	private static final String KEY_BOOK_COUNTRY_ID = "book_country_id";
	private static final String KEY_BOOK_COUNTRY_NAME = "book_country_name";
	private static final String KEY_BOOK_PUBLISHER_ID = "book_publisher_id";
	private static final String KEY_BOOK_PUBLISHER_NAME = "book_publisher_name";
	


	// AUTHOR Table - column nmaes
		
	private static final String KEY_AUTHER_ID = "author_id";
	private static final String KEY_AUTHER_NAME = "author_name";
		
		
	// YEAR Table - column nmaes
		
	private static final String KEY_YEAR_ID = "year_id";
	private static final String KEY_YEAR_NAME = "year_name";
		
	
	// CATEGORY Table - column nmaes
	
	private static final String KEY_CATEGORY_ID = "category_id";
	private static final String KEY_CATEGORY_NAME = "category_name";
	
	
	// COUNTRY Table - column nmaes
	
	private static final String KEY_COUNTRY_ID = "country_id";
	private static final String KEY_COUNTRY_NAME = "country_name";
	
	
	// PUBLISHER Table - column nmaes
	
	private static final String KEY_PUBLISHER_ID = "publisher_id";
	private static final String KEY_PUBLISHER_NAME = "publisher_name";
	
	
	

	// Table Create Statements
	// BOOK table create statement
	private static final String CREATE_TABLE_BOOK = "CREATE TABLE "
			+ TABLE_BOOK + "(" + KEY_BOOK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_BOOK_NAME+ " TEXT," + 
			KEY_BOOK_AUTHER_ID + " INTEGER," + KEY_BOOK_AUTHER_NAME+ " TEXT" +
			KEY_BOOK_CATEGORY_ID + " INTEGER," + KEY_BOOK_CATEGORY_NAME+ " TEXT" +
			KEY_BOOK_COUNTRY_ID + " INTEGER," + KEY_BOOK_COUNTRY_NAME+ " TEXT" +
			KEY_BOOK_PUBLISHER_ID + " INTEGER," + KEY_BOOK_PUBLISHER_NAME+ " TEXT" +
			KEY_BOOK_YEAR_ID + " INTEGER," + KEY_BOOK_YEAR_NAME+ " TEXT" +")";

	
	
	// AUTHOR table create statement
	private static final String CREATE_TABLE_AUTHOR = "CREATE TABLE " + TABLE_AUTHOR
			+ "(" + KEY_AUTHER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_AUTHER_NAME + " TEXT"+ 
			")";
	
	
	// CATEGORY table create statement
	private static final String CREATE_TABLE_CATEGORY = "CREATE TABLE " + TABLE_CATEGORY
				+ "(" + KEY_CATEGORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_CATEGORY_NAME + " TEXT"+ 
				")";
		
		
	// YEAR table create statement
	private static final String CREATE_TABLE_YEAR = "CREATE TABLE " + TABLE_YEAR
				+ "(" + KEY_YEAR_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_YEAR_NAME + " TEXT"+ 
				")";
	
	// COUNTRY table create statement
	private static final String CREATE_TABLE_COUNTRY = "CREATE TABLE " + TABLE_COUNTRY
					+ "(" + KEY_COUNTRY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_COUNTRY_NAME + " TEXT"+ 
					")";
		
	// PUBLISHER table create statement
	private static final String CREATE_TABLE_PUBLISHER = "CREATE TABLE " + TABLE_PUBLISHER
							+ "(" + KEY_PUBLISHER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_PUBLISHER_NAME + " TEXT"+ 
							")";

	
	
	

	public AdibfDatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		// creating required tables
		db.execSQL(CREATE_TABLE_AUTHOR);
		db.execSQL(CREATE_TABLE_BOOK);
		db.execSQL(CREATE_TABLE_CATEGORY);
		db.execSQL(CREATE_TABLE_COUNTRY);
		db.execSQL(CREATE_TABLE_PUBLISHER);
		db.execSQL(CREATE_TABLE_YEAR);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// on upgrade drop older tables
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_AUTHOR);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOK);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_COUNTRY);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PUBLISHER);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_YEAR);

		// create new tables
		onCreate(db);
	}

	// ------------------------ "todos" table methods ----------------//

	
	 * Creating a book
	 
	public Book createBook(Book book) {
		
		
		SQLiteDatabase db = this.getWritableDatabase();
		
		
		book.setAuthor(createAuthor(db ,book.getAuthor().getAuthorName()));
		book.setCategory(createCategory(db ,book.getCategory().getCategoryName()));
		book.setCountry(createCountry(db ,book.getCountry().getCountryName()));
		book.setPublisher(createPublisher(db ,book.getPublisher().getPublisherName()));
		book.setYear(createYear(db ,book.getYear().getYearName()));
		
		
		
		
		
		
		
		
		
		
		ContentValues values = new ContentValues();
		values.put(KEY_BOOK_NAME, book.getBookName());
		
		values.put(KEY_BOOK_AUTHER_ID, book.getAuthor().getAuthorId());
		values.put(KEY_BOOK_AUTHER_NAME, book.getAuthor().getAuthorName());
		
		
		values.put(KEY_BOOK_CATEGORY_ID, book.getCategory().getCategoryId());
		values.put(KEY_BOOK_CATEGORY_NAME, book.getCategory().getCategoryName());
		
		values.put(KEY_BOOK_COUNTRY_ID, book.getCountry().getCountryId());
		values.put(KEY_BOOK_COUNTRY_NAME, book.getCountry().getCountryName());
		
		values.put(KEY_BOOK_PUBLISHER_ID, book.getPublisher().getPublisherId());
		values.put(KEY_BOOK_PUBLISHER_NAME, book.getPublisher().getPublisherName());
		
		values.put(KEY_BOOK_YEAR_ID, book.getYear().getYearId());
		values.put(KEY_BOOK_YEAR_NAME, book.getYear().getYearName());
		


		// insert row
		long book_id = db.insert(TABLE_BOOK, null, values);
		book.setBookId((int)book_id); 
		// insert tag_ids
		

		return book;
	}
	
	
	
	 * Creating a book
	 
	public Author createAuthor(SQLiteDatabase db , String author_name) {
		
		if (author_name == null || author_name.equalsIgnoreCase(""))
			return new Author();
		
		
		

		ContentValues values = new ContentValues();
		values.put(KEY_AUTHER_NAME, author_name);
		

		// insert row
		long aut_id = db.insert(TABLE_AUTHOR, null, values);
		
		// insert tag_ids
		Author author = new Author((int) aut_id, author_name);

		return author ;
	}
	
	
	
	 * Creating a book
	 
	public Category createCategory(SQLiteDatabase db ,String cat_name) {
		
		if (cat_name == null || cat_name.equalsIgnoreCase(""))
			return new Category();
		
		

		ContentValues values = new ContentValues();
		values.put(KEY_CATEGORY_NAME, cat_name);
		

		// insert row
		long cat_id = db.insert(TABLE_CATEGORY, null, values);
		
		// insert tag_ids
		Category category = new Category((int) cat_id, cat_name);

		return category ;
	}
	
	
	
	 * Creating a book
	 
	public Country createCountry(SQLiteDatabase db ,String country_name) {
		
		

		if (country_name == null || country_name.equalsIgnoreCase(""))
			return new Country();
		
		
		
		

		ContentValues values = new ContentValues();
		values.put(KEY_COUNTRY_NAME, country_name);
		

		// insert row
		long cou_id = db.insert(TABLE_COUNTRY, null, values);
		
		// insert tag_ids
		Country country = new Country((int) cou_id, country_name);

		return country ;
	}
	
	
	
	 * Creating a book
	 
	public Publisher createPublisher(SQLiteDatabase db ,String publisher_name) {
		
		
		if (publisher_name == null || publisher_name.equalsIgnoreCase(""))
			return new Publisher();
		
		
		
		

		ContentValues values = new ContentValues();
		values.put(KEY_PUBLISHER_NAME, publisher_name);
		

		// insert row
		long pub_id = db.insert(TABLE_PUBLISHER, null, values);
		
		// insert tag_ids
		Publisher author = new Publisher((int) pub_id, publisher_name);

		return author ;
	}
	
	
	
	 * Creating a book
	 
	public Year createYear(SQLiteDatabase db ,String year_name) {
		
		
		if (year_name == null || year_name.equalsIgnoreCase(""))
			return new Year();
		
		
		
		ContentValues values = new ContentValues();
		values.put(KEY_AUTHER_NAME, year_name);
		

		// insert row
		long year_id = db.insert(TABLE_AUTHOR, null, values);
		
		// insert tag_ids
		Year author = new Year((int) year_id, year_name);

		return author ;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
	 * get single todo
	 
	public Book getBook(long book_id) {
		SQLiteDatabase db = this.getReadableDatabase();

		String selectQuery = "SELECT  * FROM " + TABLE_BOOK + " WHERE "
				+ KEY_BOOK_ID + " = " + book_id;

		Log.e(LOG, selectQuery);

		Cursor c = db.rawQuery(selectQuery, null);

		if (c != null)
			c.moveToFirst();

		Book book = new Book();
		Author author = new Author();
		Category category = new Category();
		Country country = new Country();
		Publisher publisher = new Publisher();
		Year year = new Year();
		
		
		author.setAuthorId(c.getInt(c.getColumnIndex(KEY_BOOK_AUTHER_ID)));
		author.setAuthorName(c.getString(c.getColumnIndex(KEY_BOOK_AUTHER_NAME)));
		
		book.setBookId(c.getInt(c.getColumnIndex(KEY_BOOK_ID)));
		book.setBookName(c.getString(c.getColumnIndex(KEY_BOOK_NAME)));
		
		
		category.setCategoryId(c.getInt(c.getColumnIndex(KEY_BOOK_CATEGORY_ID)));
		category.setCategoryName(c.getString(c.getColumnIndex(KEY_BOOK_CATEGORY_NAME)));
		
		
		country.setCountryId(c.getInt(c.getColumnIndex(KEY_BOOK_COUNTRY_ID)));
		country.setCountryName(c.getString(c.getColumnIndex(KEY_BOOK_COUNTRY_NAME)));
		
		
		publisher.setPublisherId(c.getInt(c.getColumnIndex(KEY_BOOK_PUBLISHER_ID)));
		publisher.setPublisherName(c.getString(c.getColumnIndex(KEY_BOOK_PUBLISHER_NAME)));
		
		
		year.setYearId(c.getInt(c.getColumnIndex(KEY_BOOK_YEAR_ID)));
		year.setYearName(c.getString(c.getColumnIndex(KEY_BOOK_YEAR_NAME)));
		
		book.setAuthor(author);
		book.setCategory(category);
		book.setCountry(country);
		book.setPublisher(publisher);
		book.setYear(year);
		
		
		

		return book;
	}

	*//**
	 * getting all Books
	 * *//*
	public List<Book> getAllBooks() {
		List<Book> books = new ArrayList<Book>();
		String selectQuery = "SELECT  * FROM " + TABLE_BOOK;

		Log.e(LOG, selectQuery);

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (c.moveToFirst()) {
			do {
				Todo td = new Todo();
				td.setId(c.getInt((c.getColumnIndex(KEY_ID))));
				td.setNote((c.getString(c.getColumnIndex(KEY_TODO))));
				td.setCreatedAt(c.getString(c.getColumnIndex(KEY_CREATED_AT)));
				
				Book book = new Book();
				Author author = new Author();
				Category category = new Category();
				Country country = new Country();
				Publisher publisher = new Publisher();
				Year year = new Year();
				
				
				author.setAuthorId(c.getInt(c.getColumnIndex(KEY_BOOK_AUTHER_ID)));
				author.setAuthorName(c.getString(c.getColumnIndex(KEY_BOOK_AUTHER_NAME)));
				
				book.setBookId(c.getInt(c.getColumnIndex(KEY_BOOK_ID)));
				book.setBookName(c.getString(c.getColumnIndex(KEY_BOOK_NAME)));
				
				
				category.setCategoryId(c.getInt(c.getColumnIndex(KEY_BOOK_CATEGORY_ID)));
				category.setCategoryName(c.getString(c.getColumnIndex(KEY_BOOK_CATEGORY_NAME)));
				
				
				country.setCountryId(c.getInt(c.getColumnIndex(KEY_BOOK_COUNTRY_ID)));
				country.setCountryName(c.getString(c.getColumnIndex(KEY_BOOK_COUNTRY_NAME)));
				
				
				publisher.setPublisherId(c.getInt(c.getColumnIndex(KEY_BOOK_PUBLISHER_ID)));
				publisher.setPublisherName(c.getString(c.getColumnIndex(KEY_BOOK_PUBLISHER_NAME)));
				
				
				year.setYearId(c.getInt(c.getColumnIndex(KEY_BOOK_YEAR_ID)));
				year.setYearName(c.getString(c.getColumnIndex(KEY_BOOK_YEAR_NAME)));
				
				book.setAuthor(author);
				book.setCategory(category);
				book.setCountry(country);
				book.setPublisher(publisher);
				book.setYear(year);
				

				// adding to todo list
				books.add(book);
			} while (c.moveToNext());
		}

		return books;
	}

	
	

	
	 * getting Books count
	 
	public int getBookCount() {
		String countQuery = "SELECT  * FROM " + TABLE_BOOK;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);

		int count = cursor.getCount();
		cursor.close();

		// return count
		return count;
	}

	
	 * Updating a Book
	 
	public int updateBook(Book book) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_BOOK_AUTHER_NAME, book.getAuthor().getAuthorName());
		values.put(KEY_BOOK_NAME, book.getBookName());
		values.put(KEY_BOOK_CATEGORY_NAME, book.getCategory().getCategoryName());
		values.put(KEY_BOOK_COUNTRY_NAME, book.getCountry().getCountryName());
		values.put(KEY_BOOK_PUBLISHER_NAME, book.getPublisher().getPublisherName());
		values.put(KEY_BOOK_YEAR_NAME, book.getPublisher().getPublisherName());
		

		// updating row
		return db.update(TABLE_BOOK, values, KEY_BOOK_ID + " = ?",
				new String[] { String.valueOf(book.getBookId()) });
	}

	
	 * Deleting a todo
	 
	public void delete(long tado_id) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_TODO, KEY_ID + " = ?",
				new String[] { String.valueOf(tado_id) });
	}

	// ------------------------ "tags" table methods ----------------//

	
	 * Creating tag
	 
	
	// closing database
	public void closeDB() {
		SQLiteDatabase db = this.getReadableDatabase();
		if (db != null && db.isOpen())
			db.close();
	}

	
	
}

*/