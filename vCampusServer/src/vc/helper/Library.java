package vc.helper;

import vc.common.BookInfo;
import vc.common.BookStatusInfo;
import vc.db.BookModel;
import vc.db.BookStatusModel;
import java.io.PrintStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class Library
{
  private BookModel bookModel;
  private BookStatusModel bsModel;
  
  public Library()
  {
    this.bookModel = new BookModel();
    this.bsModel = new BookStatusModel();
  }
  //查询
  public BookInfo[] queryBook(BookInfo info)
  {
    try
    {
      ResultSet rs = (ResultSet)this.bookModel.search(info);
      Vector<BookInfo> v = new Vector();
      while (rs.next())
      {
        BookInfo temp = new BookInfo(rs.getInt("ID"), rs.getString("ISBN"), rs.getString("bookName"), rs.getString("author"), rs.getString("pub"), 
        		rs.getLong("pubDate"), rs.getString("pos"), rs.getBoolean("isBorrowed"));
        v.add(temp);
      }
      return (BookInfo[])v.toArray(new BookInfo[rs.getRow()]);
    }
    catch (SQLException e)
    {
      System.out.println("Database exception");
      e.printStackTrace();
    }
    return null;
  }
  //增加图书
  public boolean addBook(BookInfo info)
  {
    return this.bookModel.insert(info);
  }
  //修改图书信息
  public boolean modifyBook(BookInfo info)
  {
    return this.bookModel.modify(info);
  }
  //删除图书
  public boolean deleteBook(BookInfo info)
  {
    return this.bookModel.delete(info);
  }
  //出查询图书状态
  public BookStatusInfo[] queryStatus(BookStatusInfo info)
  {
    try
    {
      ResultSet rs = (ResultSet)this.bsModel.search(info);
      Vector<BookStatusInfo> v = new Vector();
      while (rs.next())
      {
        BookStatusInfo temp = new BookStatusInfo(rs.getInt("ID"), rs.getString("bookName"), rs.getString("borrower"), rs.getLong("borrowDate"), 
          rs.getLong("returnDate"), rs.getLong("actualReturnDate"), rs.getBoolean("isOverTime"));
        v.add(temp);
      }
      return (BookStatusInfo[])v.toArray(new BookStatusInfo[rs.getRow()]);
    }
    catch (SQLException e)
    {
      System.out.println("Database exception");
      e.printStackTrace();
    }
    return null;
  }
  
  //进行图书借阅，成功借阅返回true
  public boolean borrowBook(BookStatusInfo info)
  {
	//按书名查找图书
    BookInfo temp = new BookInfo(0, "", info.getName(), "", "", 0, "", false);
    ResultSet rs = (ResultSet)this.bookModel.search(temp);
    try
    {
      while (rs.next())
      {
    	//如果有未被借阅的图书
    	if(!rs.getBoolean("isBorrowed"))
    	{
    		temp.setId(rs.getInt("ID"));
    		temp.setName(rs.getString("bookName"));
	        temp.setIsbn(rs.getString("ISBN"));
	        temp.setAuthor(rs.getString("author"));
	        temp.setPub(rs.getString("pub"));
	        temp.setPubDate(rs.getLong("pubDate"));
	        temp.setPos(rs.getString("pos"));
	        temp.setBorrowed(true);
	        info.setId(temp.getId());
	        return this.bookModel.modify(temp) && this.bsModel.insert(info);
    	}
      }
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    return false;
  }
  //归还图书，归还成功返回true
  public boolean returnBook(BookStatusInfo info)
  {
    BookInfo temp = new BookInfo(info.getId(), "", info.getName(), "", "", 0, "", false);
    boolean flag = true;
    ResultSet rs = (ResultSet)this.bookModel.search(temp);
    try
    {
      if (rs.next())
      {
    	  temp.setName(rs.getString("bookName"));
          temp.setIsbn(rs.getString("ISBN"));
          temp.setAuthor(rs.getString("author"));
          temp.setPub(rs.getString("pub"));
          temp.setPubDate(rs.getLong("pubDate"));
          temp.setPos(rs.getString("pos"));
          flag = rs.getBoolean("isBorrowed");
          temp.setBorrowed(false);
      }
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    if (flag) {
      return this.bookModel.modify(temp) && this.bsModel.modify(info);
    }
    return false;
  }
public boolean modifyBookStatus(BookStatusInfo bsInfo) {
	// TODO Auto-generated method stub
	return this.bsModel.modify(bsInfo);
}
}
