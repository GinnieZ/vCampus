package vc.send;

import java.util.List;

public abstract interface ILibraryAdmin
{
  public abstract List EnquiryAllBook();
  public abstract List EnquiryABook(String paramString);
  public abstract List EnquiryABookById(int paramInt);
  public abstract List EnquiryBookStatus(String paramString);
  public abstract boolean addBook(String paramString1, String paramString2, String paramString3, String paramString4, long paramLong5, String paramString6, int paramInt); 
  public abstract boolean deleteBook(int paramInt);
  public abstract boolean modifyBook(int paramInt, String paramString1, String paramString2, String paramString3, String paramString4, long paramLong5, String paramString6, boolean paramBoolean);
  public abstract boolean modifyBookStatus(int paramInt, String paramString1, String paramString2, long paramLong1, long paramLong2, long paramLong3, boolean paramBoolean);
}
