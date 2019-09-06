package vc.common;

import java.io.Serializable;

public class BookInfo
  implements Serializable
{
  private static final long serialVersionUID = 4L;
  private int id;
  private String name;
  private String isbn;
  private String author;
  private String pub;
  private long pubDate;
  private String pos;
  private boolean isBorrowed;
  
  public BookInfo(int id, String isbn, String name, String author, String pub, long pubDate, String pos,boolean isBorrowed)
  {
    this.id = id;
    this.name = name;
    this.isbn = isbn;
    this.author = author;
    this.pub = pub;
    this.pubDate = pubDate;
    this.pos = pos;
    this.isBorrowed = isBorrowed;
  }
  

public int getId()
  {
    return this.id;
  }
  
  public void setId(int id)
  {
    this.id = id;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String name)
  {
    this.name = name;
  }
  
  public String getAuthor()
  {
    return this.author;
  }
  
  public void setAuthor(String author)
  {
    this.author = author;
  }
  
  public String getPub()
  {
    return this.pub;
  }
  
  public void setPub(String pub)
  {
    this.pub = pub;
  }
  
  public long getPubDate()
  {
    return this.pubDate;
  }
  
  public void setPubDate(long pubDate)
  {
    this.pubDate = pubDate;
  }
  
  public String getPos()
  {
    return this.pos;
  }
  
  public void setPos(String pos)
  {
    this.pos = pos;
  }
  
  public boolean isBorrowed()
  {
    return this.isBorrowed;
  }
  
  public void setBorrowed(boolean isBorrowed)
  {
    this.isBorrowed = isBorrowed;
  }
  
  public String getIsbn()
  {
    return this.isbn;
  }
  
  public void setIsbn(String isbn)
  {
    this.isbn = isbn;
  }
}
