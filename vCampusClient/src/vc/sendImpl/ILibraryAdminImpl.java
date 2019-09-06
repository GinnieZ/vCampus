package vc.sendImpl;

import vc.common.BookInfo;
import vc.common.BookStatusInfo;
import vc.common.MsgType;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import vc.helper.SocketHelper;
import vc.send.ILibraryAdmin;

public class ILibraryAdminImpl
  implements ILibraryAdmin, MsgType
{
  SocketHelper sockethelper = new SocketHelper();
  ObjectInputStream is;
  ObjectOutputStream os;
  
  public ILibraryAdminImpl(SocketHelper sockethelper)
  {
	this.sockethelper = sockethelper;
    this.is = sockethelper.getIs();
    this.os = sockethelper.getOs();
  }
  public List EnquiryAllBook()
  {
	  try
	    {
	      BookInfo bookTemp = new BookInfo(0, "", "", "", "", 0, "", false);
	      this.os.writeInt(401);
	      this.os.flush();
	      this.os.writeObject(bookTemp);
	      this.os.flush();
	      try
	      {
	        if (this.is.readInt() == 4011) {
	        	System.out.println("!!!!LIBRARY_BOOK_QUERY_ALL_SUCCESS)");
	            return Arrays.asList((BookInfo[])this.is.readObject());
	        }
	      }
	      catch (ClassNotFoundException e)
	      {
	        e.printStackTrace();
	      }
	      return null;
	    }
	    catch (IOException e)
	    {
	      e.printStackTrace();
	    }
	    return null;
  }  
  public List EnquiryABook(String bookName)
  {
	  try
	    {
	      BookInfo bookTemp = new BookInfo(0, "", bookName, "", "", 0, "", false);
	      this.os.writeInt(401);
	      this.os.flush();
	      this.os.writeObject(bookTemp);
	      this.os.flush();
	      try
	      {
	        if (this.is.readInt() == 4011) {
	        	System.out.println("!!!!LIBRARY_BOOK_QUERY_A_SUCCESS)");
	            return Arrays.asList((BookInfo[])this.is.readObject());
	        }
	      }
	      catch (ClassNotFoundException e)
	      {
	        e.printStackTrace();
	      }
	      return null;
	    }
	    catch (IOException e)
	    {
	      e.printStackTrace();
	    }
	    return null;
  }
  public List EnquiryABookById(int bookId)
  {
	  try
	    {
	      BookInfo bookTemp = new BookInfo(bookId, "", "", "", "", 0, "", false);
	      this.os.writeInt(401);
	      this.os.flush();
	      this.os.writeObject(bookTemp);
	      this.os.flush();
	      try
	      {
	        if (this.is.readInt() == 4011) {
	        	System.out.println("!!!!LIBRARY_BOOK_QUERY_BY_ID_SUCCESS)");
	          return Arrays.asList((BookInfo[])this.is.readObject());
	        }
	      }
	      catch (ClassNotFoundException e)
	      {
	        e.printStackTrace();
	      }
	      return null;
	    }
	    catch (IOException e)
	    {
	      e.printStackTrace();
	    }
	    return null;
  }
  public List EnquiryBookStatus(String bookName)
  {
	  try
	    {
	      BookStatusInfo bookTemp = new BookStatusInfo(0, bookName,  "", 0, 0, 0, false);
	      this.os.writeInt(413);
	      this.os.flush();
	      this.os.writeObject(bookTemp);
	      this.os.flush();
	      try
	      {
	        if (this.is.readInt() == 4131) {
	        	System.out.println("!!!!LIBRARY_BOOK_STATUS_QUERY_SUCCESS)");
	            return Arrays.asList((BookStatusInfo[])this.is.readObject());
	        }
	      }
	      catch (ClassNotFoundException e)
	      {
	        e.printStackTrace();
	      }
	      return null;
	    }
	    catch (IOException e)
	    {
	      e.printStackTrace();
	    }
	    return null;
  }
  
  public boolean addBook(String isbn, String name, String author, String pub, long pubDate, String pos, int num)
  {
    boolean flag = true;
    try
    {
      for (int i = 0; i < num; i++)
      {
        this.os.writeInt(402);
        this.os.flush();
        BookInfo bookTemp = new BookInfo(0,name,isbn,author,pub,pubDate,pos,false);
        this.os.writeObject(bookTemp);
        this.os.flush();
        if (this.is.readInt() == 4021) {
          flag &= true;
          System.out.println("!!!!LIBRARY_BOOK_ADD_"+i+"_SUCCESS)");
        } else {
          flag &= false;
          System.out.println("!!!!LIBRARY_BOOK_ADD_"+i+"_FAILED)");
        }
      }
      return flag;
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    return false;
  }
  
  public boolean deleteBook(int id)
  {
    try
    {
      this.os.writeInt(403);
      this.os.flush();
      BookInfo b = new BookInfo(id, "", "", "", "", 0, "", false);
      this.os.writeObject(b);
      this.os.flush();
      //É¾³ý³É¹¦
      if (this.is.readInt() == 4031) {
    	  System.out.println("!!!!LIBRARY_BOOK_DELETE_SUCCESS)");
          return true;
      }     
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    return false;
  }
  
  public boolean modifyBook(int id, String isbn, String name, String author, String pub, long pubDate, String pos, boolean isBorrowed)
  {
    try
    {
      this.os.writeInt(404);
      this.os.flush();
      BookInfo bookTemp = new BookInfo(id,isbn,name,author,pub,pubDate,pos,isBorrowed);
      this.os.writeObject(bookTemp);
      this.os.flush();
      if (this.is.readInt() == 4041)
      {
    	  System.out.println("!!!!LIBRARY_BOOK_MODIFY_SUCCESS)");
        return true;
      }
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    return false;
  }
  
  public boolean modifyBookStatus(int id, String name, String borrower, long borrowedDate, long returnDate, long actualReturnDate, boolean isOvertime)
  {
    try
    {
      this.os.writeInt(414);
      this.os.flush();
      BookStatusInfo bookStatusTemp = new BookStatusInfo(id,name,borrower,borrowedDate,returnDate,actualReturnDate,isOvertime);
      this.os.writeObject(bookStatusTemp);
      this.os.flush();
      if (this.is.readInt() == 4141)
      {
        System.out.println("!!!!LIBRARY_BOOKSTATUS_MODIFY_SUCCESS)");
        return true;
      }
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    return false;
  }
  
}
