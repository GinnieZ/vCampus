package vc.send;

import vc.common.CourseInfo;
import java.util.List;

public abstract interface ISelectCourse
{
  public abstract boolean addCourse(CourseInfo paramCourseInfo);
  
  public abstract boolean modifyCourse(CourseInfo paramCourseInfo);
  
  public abstract boolean deleteCourse(CourseInfo paramCourseInfo);
  
  public abstract boolean selectCourse(String paramString, String id);
  
  public abstract boolean cancelCourse(String paramString, String id);
  
  public abstract List EnquiryCourseById(String paramString);
  
  public abstract List EnquirySelectCourse(String paramString);
  
  public abstract List EnquiryTeacherCourse();
  
  public abstract List EnquiryAllCourse();
  
  public abstract List EnquirySelectCourseCredit(String id);
  
  public abstract List EnquirySelectStudent(CourseInfo paramCourseInfo);
  
  public abstract String[] EnquiryStudent(CourseInfo paramCourseInfo);
}

