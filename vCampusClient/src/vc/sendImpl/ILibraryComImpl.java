package vc.sendImpl;

import vc.common.BookInfo;
import vc.common.BookStatusInfo;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.List;
import vc.helper.SocketHelper;
import vc.send.ILibraryCom;

public class ILibraryComImpl
  implements ILibraryCom
{
  SocketHelper socketHelper = new SocketHelper();
  ObjectInputStream is;
  ObjectOutputStream os;
  DataInputStream dis;
  FileOutputStream fos;
  String id;
  
  public ILibraryComImpl(String ComId, SocketHelper sockethelper)
  {
    this.is = sockethelper.getIs();
    this.os = sockethelper.getOs();
    this.dis = sockethelper.getDis();
    this.id = ComId;
  }
  //按书名搜索图书
  public List EnquiryAllBook(String bookName)
  {
  	System.out.println("用户:"+id+"搜索图书："+bookName);
    try
    {
      BookInfo bookTemp = new BookInfo(0, "", bookName, "", "", 0, "", false);
      this.os.writeInt(401);
      this.os.flush();
      this.os.writeObject(bookTemp);
      this.os.flush();
      try
      {
    	//搜索成功
        if (this.is.readInt() == 4011) {
        	List<BookInfo> tmpBookInfo = Arrays.asList((BookInfo[])this.is.readObject());
            fos = new FileOutputStream(new File("src\\vc\\images",bookName+".jpg"));
            byte[] inputByte = new byte[1024];
            int length = 0;
            System.out.println("开始接收数据...");
            //当length小于1024时停止循环
            while ((length = dis.read(inputByte, 0, inputByte.length)) == 1024) {
            	System.out.println(length);
                fos.write(inputByte, 0, length);
                fos.flush();
            }
            //接受最后的数据
            fos.write(inputByte, 0, length);
            fos.flush();
            System.out.println(length);
            return tmpBookInfo;
        }
      }
      catch (ClassNotFoundException e)
      {
        e.printStackTrace();
      }
      finally {
			if(fos != null){
          try {
  			System.out.println("图片接收完毕");
  			fos.close();
  			} catch (IOException e) {
  				e.printStackTrace();
  				}           
          }
		}
      return null;
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    return null;
  }
  //借阅图书
  public boolean BorrowBook(int bookId, String bookName, long borrowDate, long returnDate)
  {
	  System.out.println("用户:"+id+"借阅图书：书号："+bookId);
    try
    {
      BookStatusInfo bookStatusTemp = new BookStatusInfo(bookId, bookName, id, borrowDate, returnDate, 0, false);
      this.os.writeInt(411);
      this.os.flush();
      this.os.writeObject(bookStatusTemp);
      this.os.flush();
      //成功借阅
      if (this.is.readInt() == 4111) {
        return true;
      }
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    return false;
  }
  //按用户id查阅借阅记录
  public List EnquiryRecord(String userId)
  {
	System.out.println("用户:"+id+"查阅借阅记录");
    try
    {
      BookStatusInfo bookStatusTemp = new BookStatusInfo(0, "", userId, 0, 0, 0, false);
      this.os.writeInt(413);
      this.os.flush();
      this.os.writeObject(bookStatusTemp);
      this.os.flush();
      try
      {
    	//查询成功
        if (this.is.readInt() == 4131) {
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
  //归还图书，传入完整图书状态信息
  public boolean ReturnBook(int bookId, String bookName,long borrowDate,long returnDate, long actualReturnDate)
  {
	System.out.println("用户:"+id+"归还图书："+bookId+" "+bookName+" "+id+" "+borrowDate+" "+returnDate+" "+actualReturnDate);
    try
    {
      BookStatusInfo bookStatusTemp;
      if(actualReturnDate>returnDate)
    	  bookStatusTemp = new BookStatusInfo(bookId, bookName, id, borrowDate, returnDate, actualReturnDate, true);
      else
    	  bookStatusTemp = new BookStatusInfo(bookId, bookName, id, borrowDate, returnDate, actualReturnDate, false);     
      this.os.writeInt(412);
      this.os.flush();
      this.os.writeObject(bookStatusTemp);
      this.os.flush();
      if (this.is.readInt() == 4121) {
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
