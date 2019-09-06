package vc.sendImpl;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

import vc.common.MsgType;
import vc.common.OnlineClassInfo;
import vc.common.OnlineClassSelectedInfo;
import vc.helper.SocketHelper;
import vc.send.IOnlineClass;
import vc.send.ISelectCourse;

public class IOnlineClassImpl implements IOnlineClass, MsgType {
	ObjectInputStream is;
	ObjectOutputStream os;
	DataOutputStream dos;
	DataInputStream dis;
	public IOnlineClassImpl(SocketHelper sockethelper) {
		this.is = sockethelper.getIs();
		this.os = sockethelper.getOs();
		this.dos = sockethelper.getDos();
		this.dis = sockethelper.getDis();
	}

	public List EnquiryAllClass() {
		try {
			this.os.writeInt(1001);
			this.os.flush();
			try {
				if (this.is.readInt() == 10011) {
					return Arrays.asList((OnlineClassInfo[]) this.is.readObject());
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
	
	public boolean addClass(OnlineClassInfo mClass) {
		try {
			this.os.writeInt(1002);
			this.os.flush();

			this.os.writeObject(mClass);
			this.os.flush();
			if (this.is.readInt() == 10021) {
				return true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean deleteClass(OnlineClassInfo mClass) {
		try {
			this.os.writeInt(1003);
			this.os.flush();
			this.os.writeObject(mClass);
			this.os.flush();
			if (this.is.readInt() == 10031) {
				return true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean modifyClass(OnlineClassInfo mClass) {
		try {
			this.os.writeInt(1004);
			this.os.flush();
			this.os.writeObject(mClass);
			this.os.flush();
			if (this.is.readInt() == 10041) {
				return true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public String[] EnquiryStudent(OnlineClassInfo mClass) {
		// return students who select certain Class
		try {
			this.os.writeInt(1005);
			this.os.flush();
			this.os.writeObject(mClass);
			this.os.flush();
			try {
				if (this.is.readInt() == 10051) {
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
	
	public List EnquiryClassById(String mString) {
		try {
			this.os.writeInt(1006);
			this.os.flush();
			this.os.writeObject(mString);
			this.os.flush();
			try {
				if (this.is.readInt() == 10061) {
					return Arrays.asList((OnlineClassInfo[]) this.is.readObject());
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
	
	public List EnquiryClassByName(String mString) {
		try {
			this.os.writeInt(1007);
			this.os.flush();
			this.os.writeObject(mString);
			this.os.flush();
			try {
				if (this.is.readInt() == 10071) {
					return Arrays.asList((OnlineClassInfo[]) this.is.readObject());
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
	
	public List EnquiryClassByTeacher(String mString) {
		try {
			this.os.writeInt(1008);
			this.os.flush();
			this.os.writeObject(mString);
			this.os.flush();
			try {
				if (this.is.readInt() == 10081) {
					return Arrays.asList((OnlineClassInfo[]) this.is.readObject());
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

	public boolean selectClass(String subNum, String id, int currentPeriod) {
		OnlineClassSelectedInfo temp = new OnlineClassSelectedInfo(subNum, id, currentPeriod);
		try {
			this.os.writeInt(1011);
			this.os.flush();
			this.os.writeObject(temp);
			System.out.println("IOnlineClassImpl: " + temp.getId() + " " + temp.getSelector());
			this.os.flush();
			if (this.is.readInt() == 10111) {
				return true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean cancelClass(String subNum, String id, int currentPeriod) {
		OnlineClassSelectedInfo temp = new OnlineClassSelectedInfo(subNum, id, currentPeriod);
		try {
			this.os.writeInt(1012);
			this.os.flush();

			this.os.writeObject(temp);
			this.os.flush();
			if (this.is.readInt() == 10121) {
				return true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public List EnquirySelectClass(String stuId) {
		OnlineClassSelectedInfo temp = new OnlineClassSelectedInfo("", stuId, 0);
		System.out.println(temp.getSelector());
		try {
			this.os.writeInt(1013);
			this.os.flush();
			this.os.writeObject(temp);
			this.os.flush();
			try {
				if (this.is.readInt() == 10131) {
					return Arrays.asList((OnlineClassInfo[]) this.is.readObject());
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

	public List EnquiryTeacherClass() {
		try {
			this.os.writeInt(1041);
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

	public List EnquirySelectClassCredit(String id) {
		OnlineClassSelectedInfo temp = new OnlineClassSelectedInfo("", id, 0);
		try {
			this.os.writeInt(1013);
			this.os.flush();
			this.os.writeObject(temp);
			this.os.flush();
			try {
				if (this.is.readInt() == 10131) {
					return Arrays.asList((OnlineClassInfo[]) this.is.readObject());
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

	public List EnquirySelectStudent(OnlineClassInfo id) {
		try {
			this.os.writeInt(1014);
			this.os.flush();
			this.os.writeObject(id);
			this.os.flush();
			try {
				if (this.is.readInt() == 10141) {
					System.out.println("Receive 10141!");
					return Arrays.asList((OnlineClassSelectedInfo[]) this.is.readObject());
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
	
	public List getClassSummary(String mString, int currentPeriod) {
		try {
			this.os.writeInt(1041);
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
	
	public boolean downloadNotes(String mString, int currentPeriod, String path) {

		FileOutputStream fos = null;
		try {
			String objPath = "db/OnlineClass/" + mString + "/" + currentPeriod + "/notes.txt";
			this.os.writeInt(1021);
			this.os.flush();
			this.os.writeObject(objPath);
			this.os.flush();

			fos = new FileOutputStream(path);
			fos = new FileOutputStream(path);	
			
			int length = 0;
			byte[] getByte = new byte[1024];
			System.out.println("准备接收文件");
			while ((length = this.dis.read(getByte)) != -1) {
				if(length < 1024)
				{
					int tempL = 1024 - length;
					System.out.println(tempL);
					fos.write(getByte, 0, tempL);
					fos.flush();
					break;
				}
				System.out.println(length);
				fos.write(getByte, 0, length);
				fos.flush();
				try {
					Thread.sleep((long) 0.001);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
            } 
	        fos.flush();
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.getStackTrace();
			return false;
		}
		finally {
			if(fos != null){
            try {
    			System.out.println("文件接收完毕");
    			fos.close();
    			} catch (IOException e) {
    				e.printStackTrace();
    				}           
            }
		}
		return true;
	}
	
	public boolean downloadVideo(String mString, int currentPeriod, String path) {

		FileOutputStream fos = null;
		try {
			String objPath = "db/OnlineClass/" + mString + "/" + currentPeriod + "/video.mp4";
			this.os.writeInt(1021);
			this.os.flush();
			this.os.writeObject(objPath);
			this.os.flush();
			
			fos = new FileOutputStream(path);	
			
			int length = 0;
			byte[] getByte = new byte[1024];
			System.out.println("准备接收视频");
			while ((length = this.dis.read(getByte)) != -1) {
				if(length < 1024)
				{
					int tempL = 1024 - length;
					System.out.println(tempL);
					fos.write(getByte, 0, tempL);
					fos.flush();
					break;
				}
				System.out.println(length);
				fos.write(getByte, 0, length);
				fos.flush();
				try {
					Thread.sleep((long) 0.001);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
            } 
	        fos.flush();
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.getStackTrace();
			return false;
		}
		finally {
			if(fos != null){
            try {
    			System.out.println("视频接收完毕");
    			fos.close();
    			} catch (IOException e) {
    				e.printStackTrace();
    				}           
            }
		}
		return true;
	}
	
	public boolean forward(String courseID, String studentID, int CurrentPeriod)
	{
		OnlineClassSelectedInfo temp = new OnlineClassSelectedInfo(courseID, studentID, CurrentPeriod);
		try {
			this.os.writeInt(1015);
			this.os.flush();
			this.os.writeObject(temp);
			System.out.println("IOnlineClassImpl: " + temp.getId() + " " + temp.getSelector());
			this.os.flush();
			if (this.is.readInt() == 10151) {
				return true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
}
