package ae.ewatheq.models;

import android.provider.BaseColumns;

public class BookRecord {

	public String category_id;
	public String category_name_eng,book_id,book_title,book_language,file_size,thumbail_path,NameEnglish,NameArabic,book_path,time;
	public String category_name_ar;
	
	public boolean isSelected;
	
	
	


	public BookRecord(String book_id, String book_title, String book_language, String category_id,String file_size, String thumbail_path, String book_path,String category_name_ar, String category_name_eng, String time) {

		this.category_name_ar = category_name_ar;
		this.category_name_eng = category_name_eng;
		
		this.category_id = category_id;
		this.book_id = book_id;
		this.book_title = book_title;
		this.book_language = book_language;
		this.file_size = file_size;
		this.thumbail_path = thumbail_path;
		this.book_path = book_path;
		this.time=time;
	}

	public String getCountryId() {
		return category_id;
	}

	public String getCountryNameEng() {
		if (this.category_name_eng == null)
			return "";
		return category_name_eng;
	}

	public String getCountryNameAr() {
		if (this.category_name_ar == null)
			return "";
		return category_name_ar;
	}

	

	public void setCountryNameAr(String a_name) {
		category_name_ar = a_name;
	}

	public void setCountryNameEng(String a_name) {
		category_name_eng = a_name;
	}

}
