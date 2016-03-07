package ae.ewatheq.models;

import java.util.List;

public class BooksAndTitles {
	
	
	private List<KnowledgeService> bookList ;
	
	
	
	private String[] bookTitles ;
	private int endOfList ;
	private int totalCount ;
	
	
	
	
	
	public List<KnowledgeService> getBookList()
	{
		return this.bookList;
	}
	public String[] getBookName()
	{
		return this.bookTitles;
	}
	public int getEndOfList()
	{
		return this.endOfList;
	}
	public int getTotalCount()
	{
		return this.totalCount;
	}
	
	
	
	
	
	
	public void setBookId(List<KnowledgeService> bookList)
	{
		this.bookList = bookList;
	}
	public void setBookTitles(String[] bookTitles)
	{
		this.bookTitles = bookTitles;
	}
	public void setEndOfList(int eol)
	{
		this.endOfList = eol;
	}
	public void setTotalCount(int totalCount)
	{
		this.totalCount = totalCount;
	}
	
	
	
}
