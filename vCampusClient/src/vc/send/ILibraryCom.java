package vc.send;


import vc.common.BookInfo;
import vc.common.BookStatusInfo;
import java.util.List;

public abstract interface ILibraryCom
{
  public abstract List EnquiryAllBook(String paramString);
  
  public abstract boolean BorrowBook(int paramInt, String paramString, long paramLong1, long paramLong2);
  
  public abstract List EnquiryRecord(String paramString);
  
  public abstract boolean ReturnBook(int paramInt, String paramString, long paramLong1, long paramLong2, long paramLong3);
}
