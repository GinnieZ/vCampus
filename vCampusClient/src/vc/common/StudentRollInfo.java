package vc.common;

import java.io.Serializable;

public class StudentRollInfo
  implements Serializable
{
  private static final long serialVersionUID = 10L;
  private String id;
  private String name;
  private String age;
  private String gender;
  private String birthday;
  private String birthPlace;
  private String entranceTime;
  private String photo;
  private String nation;
  private String department;
  private String major;
  private String dormitory;
  
  public StudentRollInfo(String id, String name, String age, String gender, String birthday, String birthPlace, String entranceTime, String photo, String nation, String department, String major, String dormitory)
  {
    this.id = id;
    this.name = name;
    this.age = age;
    this.gender = gender;
    this.birthday = birthday;
    this.birthPlace = birthPlace;
    this.entranceTime = entranceTime;
    this.photo = photo;
    this.nation = nation;
    this.department = department;
    this.major = major;
    this.dormitory = dormitory;
  }
  
  public String getId()
  {
    return this.id;
  }
  
  public void setId(String id)
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
  
  public String getAge()
  {
    return this.age;
  }
  
  public void setAge(String age)
  {
    this.age = age;
  }
  
  public String getGender()
  {
    return this.gender;
  }
  
  public void setGender(String gender)
  {
    this.gender = gender;
  }
  
  public String getBirthday()
  {
    return this.birthday;
  }
  
  public void setBirthday(String birthday)
  {
    this.birthday = birthday;
  }
  
  public String getBirthPlace()
  {
    return this.birthPlace;
  }
  
  public void setBirthPlace(String birthPlace)
  {
    this.birthPlace = birthPlace;
  }
  
  public String getEntranceTime()
  {
    return this.entranceTime;
  }
  
  public void setEntranceTime(String entranceTime)
  {
    this.entranceTime = entranceTime;
  }
  
  public String getPhoto()
  {
    return this.photo;
  }
  
  public void setPhoto(String photo)
  {
    this.photo = photo;
  }
  
  public String getNation()
  {
    return this.nation;
  }
  
  public void setNation(String nation)
  {
    this.nation = nation;
  }
  
  public String getDepartment()
  {
    return this.department;
  }
  
  public void setDepartment(String department)
  {
    this.department = department;
  }
  
  public String getMajor()
  {
    return this.major;
  }
  
  public void setMajor(String major)
  {
    this.major = major;
  }
  
  public String getDormitory()
  {
    return this.dormitory;
  }
  
  public void setDormitory(String dormitory)
  {
    this.dormitory = dormitory;
  }
}
