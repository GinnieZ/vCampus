package vc.send;

import java.util.List;

import vc.common.StudentRollInfo;

public abstract interface IStudent
{
  public abstract List EnquiryStuById(StudentRollInfo paramStudentRollInfo);
  
  public abstract List EnquiryAllStu(StudentRollInfo stu);
  
  public abstract boolean AddStudentView(StudentRollInfo paramStudentRollInfo);
  
  public abstract boolean DeleteStudentView(StudentRollInfo paramStudentRollInfo);
  
  public abstract boolean ModifyStudentView(StudentRollInfo paramStudentRollInfo);
}
