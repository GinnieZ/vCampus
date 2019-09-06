package vc.sendImpl;

import vc.common.CourseInfo;
import vc.common.CourseSelectedInfo;
import vc.common.MsgType;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import vc.helper.SocketHelper;
import vc.send.ISelectCourse;

public class ISelectCourseImpl implements ISelectCourse, MsgType {
	ObjectInputStream is;
	ObjectOutputStream os;

	public ISelectCourseImpl(SocketHelper sockethelper) {
		this.is = sockethelper.getIs();
		this.os = sockethelper.getOs();
	}

	public List EnquiryAllCourse() {
		try {
			this.os.writeInt(601);
			this.os.flush();
			try {
				if (this.is.readInt() == 6011) {
					return Arrays.asList((CourseInfo[]) this.is.readObject());
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			return null;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean addCourse(CourseInfo mCourse) {
		try {
			this.os.writeInt(602);
			this.os.flush();

			this.os.writeObject(mCourse);
			this.os.flush();
			if (this.is.readInt() == 6021) {
				return true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean deleteCourse(CourseInfo mCourse) {
		try {
			this.os.writeInt(603);
			this.os.flush();
			this.os.writeObject(mCourse);
			this.os.flush();
			if (this.is.readInt() == 6031) {
				return true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean modifyCourse(CourseInfo mCourse) {
		try {
			this.os.writeInt(604);
			this.os.flush();
			this.os.writeObject(mCourse);
			this.os.flush();
			if (this.is.readInt() == 6041) {
				return true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public String[] EnquiryStudent(CourseInfo mCourse) {
		// return students who select certain course
		try {
			this.os.writeInt(605);
			this.os.flush();
			this.os.writeObject(mCourse);
			this.os.flush();
			try {
				if (this.is.readInt() == 6051) {
					return (String[]) (this.is.readObject());
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			return null;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List EnquiryCourseById(String mString) {
		try {
			this.os.writeInt(606);
			this.os.flush();
			this.os.writeObject(mString);
			this.os.flush();
			try {
				if (this.is.readInt() == 6061) {
					return Arrays.asList((CourseInfo[]) this.is.readObject());
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			return null;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List EnquiryCourseByName(String mString) {
		try {
			this.os.writeInt(607);
			this.os.flush();
			this.os.writeObject(mString);
			this.os.flush();
			try {
				if (this.is.readInt() == 6071) {
					return Arrays.asList((CourseInfo[]) this.is.readObject());
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			return null;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List EnquiryCourseByTeacher(String mString) {
		try {
			this.os.writeInt(608);
			this.os.flush();
			this.os.writeObject(mString);
			this.os.flush();
			try {
				if (this.is.readInt() == 6081) {
					return Arrays.asList((CourseInfo[]) this.is.readObject());
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			return null;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean selectCourse(String subNum, String id) {
		CourseSelectedInfo temp = new CourseSelectedInfo(subNum, id);
		try {
			this.os.writeInt(611);
			this.os.flush();
			this.os.writeObject(temp);
			System.out.println("ISelectCourseImpl: " + temp.getId() + " " + temp.getSelector());
			this.os.flush();
			if (this.is.readInt() == 6111) {
				return true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean cancelCourse(String subNum, String id) {
		CourseSelectedInfo temp = new CourseSelectedInfo(subNum, id);
		try {
			this.os.writeInt(612);
			this.os.flush();

			this.os.writeObject(temp);
			this.os.flush();
			if (this.is.readInt() == 6121) {
				return true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public List EnquirySelectCourse(String stuId) {
		CourseSelectedInfo temp = new CourseSelectedInfo("", stuId);
		System.out.println(temp.getSelector());
		try {
			this.os.writeInt(613);
			this.os.flush();
			this.os.writeObject(temp);
			this.os.flush();
			try {
				if (this.is.readInt() == 6131) {
					return Arrays.asList((CourseInfo[]) this.is.readObject());
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			return null;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List EnquiryTeacherCourse() {
		try {
			this.os.writeInt(641);
			this.os.flush();
			try {
				return (List) this.is.readObject();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			return null;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List EnquirySelectCourseCredit(String id) {
		CourseSelectedInfo temp = new CourseSelectedInfo("", id);
		try {
			this.os.writeInt(613);
			this.os.flush();
			this.os.writeObject(temp);
			this.os.flush();
			try {
				if (this.is.readInt() == 6131) {
					return Arrays.asList((CourseInfo[]) this.is.readObject());
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			return null;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List EnquirySelectStudent(CourseInfo teacher) {
		try {
			this.os.writeInt(614);
			this.os.flush();
			this.os.writeObject(teacher);
			this.os.flush();
			try {
				if (this.is.readInt() == 6141) {
					System.out.println("Receive 6141!");
					return Arrays.asList((CourseSelectedInfo[]) this.is.readObject());
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			return null;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List getStuCourse(String mString) {
		try {
			this.os.writeInt(635);
			this.os.flush();
			this.os.writeObject(mString);
			this.os.flush();
			try {
				return (List) this.is.readObject();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			return null;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}



}
