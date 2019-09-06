package vc.send;

import vc.common.OnlineClassInfo;

import java.util.List;

public abstract interface IOnlineClass
{
  public abstract boolean addClass(OnlineClassInfo paramClassInfo);
  
  public abstract boolean modifyClass(OnlineClassInfo paramClassInfo);
  
  public abstract boolean deleteClass(OnlineClassInfo paramClassInfo);
  
  public abstract boolean selectClass(String paramString, String id, int currentPeriod);
  
  public abstract boolean cancelClass(String paramString, String id, int currentPeriod);
  
  public abstract List EnquiryClassById(String paramString);
  
  public abstract List EnquirySelectClass(String paramString);
  
  public abstract List EnquiryTeacherClass();
  
  public abstract List EnquiryAllClass();
  
  public abstract List EnquirySelectClassCredit(String id);
  
  public abstract List EnquirySelectStudent(OnlineClassInfo paramClassInfo);
  
  public abstract String[] EnquiryStudent(OnlineClassInfo paramClassInfo);
  
  public abstract List getClassSummary(String mString, int currentPeriod);
  
  public abstract boolean downloadNotes(String mString, int currentPeriod, String path);
}

