package vc.common;

import java.io.Serializable;

public class BookStatusInfo
  implements Serializable
{
  private static final long serialVersionUID = 5L;
  private int id;
  private String name;
  private String borrower;
  private long borrowDate;
  private long returnDate;
  private long actualReturnDate;
  private boolean isOvertime;
  
  public BookStatusInfo(int id, String name, String borrower, long borrowDate, long returnDate, long actualReturnDate, boolean isOvertime)
  {
    this.id = id;
    this.name = name;
    this.borrower = borrower;
    this.borrowDate = borrowDate;
    this.returnDate = returnDate;
    this.actualReturnDate = actualReturnDate;
    this.isOvertime = isOvertime;
  }
  
  public BookStatusInfo() {
	// TODO Auto-generated constructor stub
	this.id = 0;
	this.name = "";
	this.borrower = "";
	this.borrowDate = 0;
	this.returnDate = 0;
	this.actualReturnDate = 0;
	this.isOvertime = false;	  
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
  
  public String getBorrower()
  {
    return this.borrower;
  }
  
  public void setBorrower(String borrower)
  {
    this.borrower = borrower;
  }
  
  public long getBorrowDate()
  {
    return this.borrowDate;
  }
  
  public void setBorrowDate(long borrowDate)
  {
    this.borrowDate = borrowDate;
  }
  
  public long getReturnDate()
  {
    return this.returnDate;
  }
  
  public void setReturnDate(long returnDate)
  {
    this.returnDate = returnDate;
  }
  
  public long getActualReturnDate()
  {
    return this.actualReturnDate;
  }
  
  public void setActualReturnDate(long actualReturnDate)
  {
    this.actualReturnDate = actualReturnDate;
  }
  
  public boolean isOvertime()
  {
    return this.isOvertime;
  }
  
  public void setOvertime(boolean isOvertime)
  {
    this.isOvertime = isOvertime;
  }
  
  
}
