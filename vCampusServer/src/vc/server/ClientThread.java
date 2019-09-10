package vc.server;

import vc.common.BankInfo;
import vc.common.BookInfo;
import vc.common.BookStatusInfo;
import vc.helper.Bank;
import vc.common.CourseInfo;
import vc.common.CourseSelectedInfo;
import vc.common.DormChargeInf;
import vc.common.DormLivingInf;
import vc.common.DormRepairInf;
import vc.common.DormUtilityBillsInf;
import vc.common.DormVisitInf;
import vc.common.MedcineInfo;
import vc.common.MsgType;
import vc.common.OnlineClassInfo;
import vc.common.OnlineClassSelectedInfo;
import vc.common.PatientInfo;
import vc.common.StudentRollInfo;
import vc.common.UserInfo;
import vc.helper.Login;
import vc.helper.Course;
import vc.helper.OnlineClass;
import vc.helper.Student;
import vc.helper.Hospital;
import vc.helper.Library;
import vc.view.ServerView;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientThread extends Thread implements MsgType {
	private ServerThread currentServer;
	private Socket client;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private DataOutputStream dos;
	private DataInputStream dis;
	public String curUser;

	public ClientThread(Socket s, ServerThread st) {
		this.client = s;
		this.currentServer = st;
		this.curUser = "";
		try {
			this.ois = new ObjectInputStream(this.client.getInputStream());
			this.oos = new ObjectOutputStream(this.client.getOutputStream());
			this.dis = new DataInputStream(this.client.getInputStream());
			this.dos = new DataOutputStream(this.client.getOutputStream());
			ServerView.setTextArea("客户端已连接\n客户端IP：" + this.client.getInetAddress().getHostAddress() + "\n");
			System.out.println("Client connected");
			ServerView.count++;
		} catch (IOException e) {
			System.out.println("Cannot get IO stream");
			e.printStackTrace();
		}
	}

	public void run() {
		int cmd = 0;
		for (;;) {
			try {
				cmd = this.ois.readInt();
			} catch (IOException e) {
				return;
			}
			System.out.println(cmd);
			switch (cmd / 100) {
			case 0:
				System.out.println("Abnormal command");
				return;
			case 1:
				Login(cmd);
				break;
			case 3: 
		        StudentRoll(cmd);
		        break;
		    case 4: 
		        Library(cmd);
		        break;
			case 6:
				Course(cmd);
				break;
			case 8:
				Hospital(cmd);
				break;
		    case 9:
		    	Dorm(cmd);
		    	break;
			case 10:
				OnlineClass(cmd);
				break;
			}
		}
	}

	public void close() {
		if (this.client != null) {
			try {
				this.oos.close();
				this.ois.close();
				this.dos.close();
				this.dis.close();
				ServerView.setTextArea("客户端已断开连接\n客户端IP：" + this.client.getInetAddress().getHostAddress() + "\n");

				this.client.close();

				this.currentServer.closeClientConnection(this);
				ServerView.setTextNumber(this.currentServer.getSize());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void Login(int cmd) {
		UserInfo info = null;
		Login lg = new Login();
		try {
			if (cmd != 103) {
				info = (UserInfo) this.ois.readObject();
			}
		} catch (IOException e) {
			System.out.println("Cannot get message from client");
			e.printStackTrace();

			return;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		switch (cmd) {
		case 101:
			try {
				if ((!this.currentServer.searchClientConnection(info.getStuId())) && (lg.login(info))) {
					this.oos.writeInt(1011);

					this.currentServer.addClientConnection(this);
					this.curUser = info.getStuId();

					ServerView.setTextNumber(this.currentServer.getSize());
					System.out.println("Number of connected client: " + this.currentServer.getSize());
					ServerView.setTextArea("用户" + this.curUser + "已登录\n");
				} else {
					this.oos.writeInt(1012);
				}
				this.oos.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		case 102:
			try {
				int wb = lg.register(info) ? 1021 : 1022;
				this.oos.writeInt(wb);

				this.oos.flush();
				if (wb == 1021) {
					this.currentServer.addClientConnection(this);
					this.curUser = info.getStuId();

					ServerView.setTextNumber(this.currentServer.getSize());
					System.out.println("Number of connected client: " + this.currentServer.getSize());
					ServerView.setTextArea("用户" + this.curUser + "已登录\n");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case 103:
			try {
				if (this.currentServer.searchClientConnection(this.curUser)) {
					ServerView.setTextArea("用户" + this.curUser + "已退出登录\n");
					this.oos.writeInt(1031);
					close();
				} else {
					this.oos.writeInt(1032);
				}
				this.oos.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
	}

	private void StudentRoll(int cmd)
	  {
	    StudentRollInfo stuInfo = null;
	    Student sr = new Student();
	    try
	    {
	      if (cmd != 305) {
	        stuInfo = (StudentRollInfo)this.ois.readObject();
	      }
	    }
	    catch (IOException e)
	    {
	      e.printStackTrace();
	    }
	    catch (ClassNotFoundException e)
	    {
	      e.printStackTrace();
	    }
	    switch (cmd)
	    {
	    case 301: 
	      try
	      {
	        StudentRollInfo result = sr.query(stuInfo);
	        if (result != null)
	        {
	          this.oos.writeInt(3011);
	          this.oos.writeObject(result);
	        }
	        else
	        {
	          this.oos.writeInt(3012);
	        }
	        this.oos.flush();
	      }
	      catch (IOException e)
	      {
	        e.printStackTrace();
	      }
	      break;
	    case 302: 
	      try
	      {
	        int wb = sr.AddStudentView(stuInfo) ? 3021 : 3022;
	        this.oos.writeInt(wb);
	        
	        this.oos.flush();
	      }
	      catch (IOException e)
	      {
	        e.printStackTrace();
	      }
	      break;
	    case 303: 
	      try
	      {
	        int wb = sr.DeleteStudentView(stuInfo) ? 3031 : 3032;
	        this.oos.writeInt(wb);
	        
	        this.oos.flush();
	      }
	      catch (IOException e)
	      {
	        e.printStackTrace();
	      }
	      break;
	    case 304: 
	      try
	      {
	        int wb = sr.ModifyStudentView(stuInfo) ? 3041 : 3042;
	        this.oos.writeInt(wb);
	        
	        this.oos.flush();
	      }
	      catch (IOException e)
	      {
	        e.printStackTrace();
	      }
	      break;
	    case 305: 
	      try
	      {
	        StudentRollInfo[] result = sr.queryAll();
	        if (result != null)
	        {
	          this.oos.writeInt(3051);
	          this.oos.writeObject(result);
	        }
	        else
	        {
	          this.oos.writeInt(3052);
	        }
	        this.oos.flush();
	      }
	      catch (IOException e)
	      {
	        e.printStackTrace();
	      }
	      break;
	    }
	  }
	
	private void Course(int cmd) {
		CourseInfo courseInfo = null;
		CourseSelectedInfo csInfo = null;
		Course cs = new Course();
		if (cmd / 10 == 60) {
			try {
				if (cmd != 601 && cmd != 606 && cmd != 607 && cmd != 608) {
					courseInfo = (CourseInfo) this.ois.readObject();
				}
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			switch (cmd) {
			case 601:
				try {
					CourseInfo[] result = cs.queryCourse();
					if (result != null) {
						this.oos.writeInt(6011);
						this.oos.writeObject(result);
					} else {
						this.oos.writeInt(6012);
					}
					this.oos.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case 602:
				try {
					int wb = cs.addCourse(courseInfo) ? 6021 : 6022;
					this.oos.writeInt(wb);

					this.oos.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case 603:
				try {
					int wb = cs.deleteCourse(courseInfo) ? 6031 : 6032;
					this.oos.writeInt(wb);

					this.oos.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case 604:
				try {
					int wb = cs.modifyCourse(courseInfo) ? 6041 : 6042;
					this.oos.writeInt(wb);

					this.oos.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case 605:
				try {
					String[] result = cs.queryStudent(courseInfo);
					if (result != null) {
						this.oos.writeInt(6051);
						this.oos.writeObject(result);
					}
					else {
						this.oos.writeInt(6052);
					}
				}catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case 606:
				try {
					String tempId = (String) this.ois.readObject();
					CourseInfo[] result = cs.queryCourseById(tempId);
					if (result != null) {
						this.oos.writeInt(6061);
						this.oos.writeObject(result);
					}
					else {
						this.oos.writeInt(6062);
					}
				}catch (IOException | ClassNotFoundException e) {
					e.printStackTrace();
				}
				break;
			case 607:
				try {
					String tempName = (String) this.ois.readObject();
					CourseInfo[] result = cs.queryCourseByName(tempName);
					if (result != null) {
						this.oos.writeInt(6071);
						this.oos.writeObject(result);
					}
					else {
						this.oos.writeInt(6072);
					}
				}catch (IOException | ClassNotFoundException e) {
					e.printStackTrace();
				}
				break;
			case 608:
				try {
					String tempTeacher = (String) this.ois.readObject();
					CourseInfo[] result = cs.queryCourseByTeacher(tempTeacher);
					if (result != null) {
						this.oos.writeInt(6081);
						this.oos.writeObject(result);
					}
					else {
						this.oos.writeInt(6082);
					}
				}catch (IOException | ClassNotFoundException e) {
					e.printStackTrace();
				}
				break;
			}
			
		} else {
			try {
				if (cmd != 614) {
					csInfo = (CourseSelectedInfo) this.ois.readObject();
					System.out.println("read: " + csInfo.getId() + " " + csInfo.getSelector());
				}
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			switch (cmd) {
			case 611:
				try {
					int wb = cs.selectCourse(csInfo) ? 6111 : 6112;
					this.oos.writeInt(wb);

					this.oos.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case 612:
				try {
					int wb = cs.deselectCourse(csInfo) ? 6121 : 6122;
					this.oos.writeInt(wb);

					this.oos.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case 613:
				try {
					CourseInfo[] result = cs.queryCurriculum(csInfo);
					if (result != null) {
						this.oos.writeInt(6131);
						this.oos.writeObject(result);
					} else {
						this.oos.writeInt(6132);
					}
					this.oos.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case 614:
				try {
					CourseInfo cInfo = (CourseInfo) this.ois.readObject();

					CourseSelectedInfo[] result = cs.queryStatus(cInfo);
					if (result != null) {
						this.oos.writeInt(6141);
						this.oos.writeObject(result);
					} else {
						this.oos.writeInt(6142);
					}
					this.oos.flush();
				} catch (IOException | ClassNotFoundException e) {
					e.printStackTrace();
				}
				break;
			}
		}
	}
	
	private void OnlineClass(int cmd) {
		OnlineClassInfo courseInfo = null;
		OnlineClassSelectedInfo csInfo = null;
		String path = null;
		OnlineClass cs = new OnlineClass();
		if (cmd / 10 == 100) {
			try {
				if (cmd != 1001 && cmd != 1006 && cmd != 1007 && cmd != 1008) {
					courseInfo = (OnlineClassInfo) this.ois.readObject();
				}
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			switch (cmd) {
			case 1001:
				try {
					OnlineClassInfo[] result = cs.queryClass();
					if (result != null) {
						this.oos.writeInt(10011);
						this.oos.writeObject(result);
					} else {
						this.oos.writeInt(10012);
					}
					this.oos.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case 1002:
				try {
					int wb = cs.addClass(courseInfo) ? 10021 : 10022;
					this.oos.writeInt(wb);

					this.oos.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case 1003:
				try {
					int wb = cs.deleteClass(courseInfo) ? 10031 : 10032;
					this.oos.writeInt(wb);

					this.oos.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case 1004:
				try {
					int wb = cs.modifyClass(courseInfo) ? 10041 : 10042;
					this.oos.writeInt(wb);

					this.oos.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case 1005:
				try {
					String[] result = cs.queryStudent(courseInfo);
					if (result != null) {
						this.oos.writeInt(10051);
						this.oos.writeObject(result);
					}
					else {
						this.oos.writeInt(10052);
					}
				}catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case 1006:
				try {
					String tempId = (String) this.ois.readObject();
					OnlineClassInfo[] result = cs.queryClassById(tempId);
					if (result != null) {
						this.oos.writeInt(10061);
						this.oos.writeObject(result);
					}
					else {
						this.oos.writeInt(10062);
					}
				}catch (IOException | ClassNotFoundException e) {
					e.printStackTrace();
				}
				break;
			case 1007:
				try {
					String tempName = (String) this.ois.readObject();
					OnlineClassInfo[] result = cs.queryClassByName(tempName);
					if (result != null) {
						this.oos.writeInt(10071);
						this.oos.writeObject(result);
					}
					else {
						this.oos.writeInt(10072);
					}
				}catch (IOException | ClassNotFoundException e) {
					e.printStackTrace();
				}
				break;
			case 1008:
				try {
					String tempTeacher = (String) this.ois.readObject();
					OnlineClassInfo[] result = cs.queryClassByTeacher(tempTeacher);
					if (result != null) {
						this.oos.writeInt(10081);
						this.oos.writeObject(result);
					}
					else {
						this.oos.writeInt(10082);
					}
				}catch (IOException | ClassNotFoundException e) {
					e.printStackTrace();
				}
				break;
			}
			
		} else if (cmd / 10 == 101){
			try {
				if (cmd != 1014) {
					csInfo = (OnlineClassSelectedInfo) this.ois.readObject();
					System.out.println("read: " + csInfo.getId() + " " + csInfo.getSelector());
				}
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			switch (cmd) {
			case 1011:
				try {
					int wb = cs.selectClass(csInfo) ? 10111 : 10112;
					this.oos.writeInt(wb);

					this.oos.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case 1012:
				try {
					int wb = cs.deselectClass(csInfo) ? 10121 : 10122;
					this.oos.writeInt(wb);

					this.oos.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case 1013:
				try {
					OnlineClassInfo[] result = cs.queryCurriculum(csInfo);
					if (result != null) {
						this.oos.writeInt(10131);
						this.oos.writeObject(result);
					} else {
						this.oos.writeInt(10132);
					}
					this.oos.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;	
			case 1014:
				try {
					OnlineClassInfo cInfo = (OnlineClassInfo) this.ois.readObject();

					OnlineClassSelectedInfo[] result = cs.queryStatus(cInfo);
					if (result != null) {
						this.oos.writeInt(10141);
						this.oos.writeObject(result);
					} else {
						this.oos.writeInt(10142);
					}
					this.oos.flush();
				} catch (IOException | ClassNotFoundException e) {
					e.printStackTrace();
				}
				break;
			case 1015:
				try {
					int wb = cs.forwardClass(csInfo) ? 10151 : 10152;
					this.oos.writeInt(wb);

					this.oos.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			}
		}else {
			try {
				if (true) {
					path = (String) this.ois.readObject();
					System.out.println("read: " + path);
				}
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			switch (cmd) {
			case 1021:
				FileInputStream fis = null;
				byte[] sendByte = new byte[1024]; 
				try {
					
					File file = new File(path);
					fis = new FileInputStream(file);
					int length =0;
					System.out.println("准备发送文件");
					while((length=fis.read(sendByte)) != -1){
						this.dos.write(sendByte, 0 , length);
						this.dos.flush();
					}
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (FileNotFoundException e)
		        {
					System.out.println("找不到文件");
				} catch (IOException e) {
					e.printStackTrace();
				}
				finally {
					if(fis != null){
			    			System.out.println("文件发送完毕");
//			    			fis.close();          
			            }
				}

				break;
			}
		}
	}
	
	private void Bank(int cmd)
	{
	  BankInfo bankInfo = null;
	  Bank bk = new Bank();
	  try
	  {
	    bankInfo = (BankInfo)this.ois.readObject();
	  }
	  catch (IOException e)
	  {
	    e.printStackTrace();
	  }
	  catch (ClassNotFoundException e)
	  {
	    e.printStackTrace();
	  }
	  switch (cmd)
	  {
	  case 201: 
	    try
	    {
	      double result = bk.queryBalance(bankInfo);
	      System.out.println("Client Thread " + result);
//	      if (result != 0.0D)
	      if (true)
	      {
	        this.oos.writeInt(2011);
	        this.oos.writeDouble(result);
	      }
	      else
	      {
	        this.oos.writeInt(2012);
	      }
	      this.oos.flush();
	    }
	    catch (IOException e)
	    {
	      e.printStackTrace();
	    }
	  case 202: 
	    try
	    {
	      int wb = bk.transfer(bankInfo) ? 2021 : 2022;
	      this.oos.writeInt(wb);
	      
	      this.oos.flush();
	    }
	    catch (IOException e)
	    {
	      e.printStackTrace();
	    }
	  }
	}
	
	private void Hospital(int cmd){
		PatientInfo PInfo = null;
		MedcineInfo MInfo = null;
		UserInfo user = null;
		Hospital hs = new Hospital();
		String[] rs;
		String[] unpaidMedcine;
		String id = null;
		String prescription = null;
		//System.out.println(11);
		if (cmd / 100 == 8) {
			try {
				if(cmd == 821 || cmd == 820) {
					id = (String) this.ois.readObject();
				}
				if(cmd == 805) {
					user = (UserInfo) this.ois.readObject();
				}
				if(cmd == 822) {
					id = (String) this.ois.readObject();
					prescription = (String) this.ois.readObject();
				}
				if (cmd == 803 || cmd == 806 || cmd == 808 || cmd == 809 || cmd == 823) {
					//System.out.println(12);
					MInfo = (MedcineInfo) this.ois.readObject();
				}
				else if(cmd == 802 || cmd == 804 || cmd == 807 || cmd == 810 || cmd == 811) {
					//System.out.println(13);
					PInfo = (PatientInfo)this.ois.readObject();
					//System.out.println(14);
				}
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			switch (cmd) {
			/*
			case 801:
				try {
					//不知道801干嘛的
					PatientInfo[] result = hs.queryCourse();
					if (result != null) {
						this.oos.writeInt(8011);
						this.oos.writeObject(result);
					} else {
						this.oos.writeInt(8012);
					}
					this.oos.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			*/
			
			case 802:
				try {
					rs = (String[])hs.getPatientInfo(PInfo);
					PInfo.setName(rs[0]);
					PInfo.setGender(rs[1]);
					PInfo.setAge(Integer.parseInt(rs[2]));
					//int reg = hs.register(PInfo);
					//System.out.println("reg:"+reg);
					this.oos.writeInt(hs.register(PInfo));
					this.oos.flush();
					this.oos.writeInt(8021);
					this.oos.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case 803:
				try {
					rs = hs.getMedcineInfo(MInfo);
					MInfo.setName(rs[0]);
					MInfo.SetUsage(rs[1]);
					MInfo.setInstruction(rs[2]);
					this.oos.writeInt(8031);
					this.oos.flush();
					this.oos.writeObject(MInfo);
					this.oos.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case 804:
				try {
					int wb = hs.pay(PInfo) ? 8041 : 8042;
					this.oos.writeInt(wb);
					this.oos.flush();
					
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case 805:
				try {
					rs = (String[])hs.getUserInfo(user);
					PInfo = new PatientInfo(null, null, 0, 0, null, null, null);
					//System.out.println(rs.length);
					PInfo.setName(rs[0]);
					PInfo.setGender(rs[1]);
					PInfo.setAge(Integer.parseInt(rs[2]));
					PInfo.setId(rs[3]);
					unpaidMedcine = hs.searchUnpaidMedcine(PInfo);
					if(unpaidMedcine!=null) {
						PInfo.setUnpaidMedcine_2(unpaidMedcine);
					}
					this.oos.writeInt(8051);
					this.oos.flush();
					this.oos.writeObject(PInfo);
					this.oos.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case 806:
				try {
					int wb = hs.purchase(MInfo) ? 8061 : 8062;
					this.oos.writeInt(wb);
					this.oos.flush();
				} catch (IOException e){
					e.printStackTrace();
				}
				break;
			case 807:
				try {
					rs = (String[])hs.getPatientInfo(PInfo);
					//System.out.println(14);
					//System.out.println(unpaidMedcine[0]);
					//System.out.println("长度："+unpaidMedcine.length);
					PInfo.setName(rs[0]);
					PInfo.setGender(rs[1]);
					PInfo.setAge(Integer.parseInt(rs[2]));
					PInfo.setId(rs[3]);
					unpaidMedcine = hs.searchUnpaidMedcine(PInfo);
					if(unpaidMedcine!=null) {
						PInfo.setUnpaidMedcine_2(unpaidMedcine);
					}
					
					this.oos.writeInt(8071);
					this.oos.flush();
					
					this.oos.writeObject(PInfo);
					this.oos.flush();
				} catch (IOException e){
					e.printStackTrace();
				}
				break;
			case 808:
				try {
					//System.out.println(11);
					//好像没有用到
					rs = (String[])hs.getPatientInfo(PInfo);
					unpaidMedcine = hs.searchUnpaidMedcine(PInfo);
					PInfo.setName(rs[0]);
					PInfo.setGender(rs[1]);
					PInfo.setAge(Integer.parseInt(rs[2]));
					PInfo.setId(rs[3]);
					PInfo.setUnpaidMedcine_2(unpaidMedcine);
					this.oos.writeInt(8081);
					this.oos.flush();
					
					this.oos.writeObject(PInfo);
					this.oos.flush();
				} catch (IOException e){
					e.printStackTrace();
				}
				break;
			case 809:
				try {
					int wb = hs.deleteMedcine(MInfo) ? 8091 : 8092;
					this.oos.writeInt(wb);
					this.oos.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case 810:
				try {
					int wb = hs.modifyPatient(PInfo) ? 8101 : 8102;
					this.oos.writeInt(wb);
					this.oos.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case 811:
				try {
					int wb = hs.addPatient_2(PInfo) ? 8111 : 8112;
					this.oos.writeInt(wb);
					this.oos.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case 820:
				try {
					rs = hs.readDay(id);
					//hs.prescribe(id, prescription);
					this.oos.writeObject(rs);
					this.oos.flush();
					this.oos.writeInt(8201);
					this.oos.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case 821:
				try {
					rs = hs.readPrescription(id);
					this.oos.writeObject(rs);
					this.oos.flush();
					this.oos.writeInt(8211);
					this.oos.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case 822:
				try {
					hs.prescribe(id, prescription);
					this.oos.writeInt(8221);
					this.oos.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case 823:
				try {
					int wb = hs.addMedcine(MInfo) ? 8231:8232;
					this.oos.writeInt(wb);
					this.oos.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			}
		} else {
			System.out.println("未知指令");
		}
	}
	
	private void Dorm(int cmd){
		// TODO Auto-generated method stub
			DormLivingInf info =new DormLivingInf();
			DormRepairInf repairInfo = new DormRepairInf();
			DormChargeInf chargeInfo = new DormChargeInf();
			DormUtilityBillsInf billsInfo = new DormUtilityBillsInf();
			DormVisitInf visitInfo = new DormVisitInf();
			vc.helper.Dorm dr = new vc.helper.Dorm();
			try
			{
				if(cmd>=901 && cmd<905) {
					System.out.println(cmd+"进来了");
					info = (DormLivingInf)this.ois.readObject();
				}
				
				if(cmd>=905 && cmd<909) {
					repairInfo = (DormRepairInf)this.ois.readObject();
				}
				
				if(cmd==909 || cmd==910) {
					chargeInfo = (DormChargeInf)this.ois.readObject();
				}
				if(cmd == 920) {
					billsInfo = (DormUtilityBillsInf)this.ois.readObject();
				}
				if(cmd>=940 && cmd< 980) {
					visitInfo = (DormVisitInf)this.ois.readObject();
				}
			}
			   catch (IOException e)
		    {
		      e.printStackTrace();
		    }
		    catch (ClassNotFoundException e)
		    {
		      e.printStackTrace();
		    }
			
		switch(cmd) {
		//查询住宿信息
		case 901:
			try {
				DormLivingInf[] result = dr.queryDormLivingInf(info);
				if(result != null) {
					this.oos.writeInt(9011);
					System.out.println("9011");
					this.oos.writeObject(result);
				}
				else {
					System.out.println("9012");
					this.oos.writeInt(9012);
				}
		          this.oos.flush();
		        }
		        catch (IOException e)
		        {
		          e.printStackTrace();
		        }
			break;
			//查询宿舍修理信息
		case 904:
			try {
				if(dr.modifyDormLivingInf(info)) {
					this.oos.writeInt(9041);
					System.out.println("9041");
				}
				else {
					this.oos.writeInt(9042);
					System.out.println("9042");
				}
				this.oos.flush();
			}
		        catch (IOException e)
		        {
		          e.printStackTrace();
		        }
			break;
		//宿舍维修信息
		case 905:
			try {
				DormRepairInf[] result = dr.queryDormRepairInf(repairInfo);
				if(result != null) {
					this.oos.writeInt(9051);
					System.out.println("9051");
					this.oos.writeObject(result);
				}
				else {
					System.out.println("9052");
					this.oos.writeInt(9012);
				}
		          this.oos.flush();
			}
	        catch (IOException e)
	        {
	          e.printStackTrace();
	        }
			break;
		
		//新增宿舍维修信息即报修
		case 906:
			try {
				boolean result = dr.insertDormRepairInf(repairInfo);
				if(result) {
					this.oos.writeInt(9061);
					System.out.println("9061");
					this.oos.writeObject(result);
				}
				else {
					System.out.println("9062");
					this.oos.writeInt(9062);
				}
		          this.oos.flush();
			}
	        catch (IOException e)
	        {
	          e.printStackTrace();
	        }
			break;
			
		//水电缴纳记录查询
		case 909:
			try {
				DormChargeInf[] result = dr.queryDormChargeInf(chargeInfo);
				if(result != null) {
					this.oos.writeInt(9091);
					System.out.println("9091");
					this.oos.writeObject(result);
				}
				else {
					System.out.println("9092");
					this.oos.writeInt(9092);
				}
		          this.oos.flush();
			}
	        catch (IOException e)
	        {
	          e.printStackTrace();
	        }
			break;	
			
		//缴纳费用
		case 910:
			try {
				boolean result = dr.insertDormChargeInf(chargeInfo);
				if(result) {
					this.oos.writeInt(9101);
					System.out.println("发送了9101");
					this.oos.writeObject(result);
				}
				else {
					System.out.println("9102");
					this.oos.writeInt(9102);
				}
		          this.oos.flush();
			}
			  catch (IOException e)
	        {
	          e.printStackTrace();
	        }
			break;	
			
		//水电费用查询
		case 920:
			try {
				DormUtilityBillsInf[] result = dr.queryDormUtilityBillsInf(billsInfo);
				if(result != null) {
					this.oos.writeInt(9201);
					System.out.println("9201");
					this.oos.writeObject(result);
				}
				else {
					System.out.println("9202");
					this.oos.writeInt(9202);
				}
		          this.oos.flush();
			}
	        catch (IOException e)
	        {
	          e.printStackTrace();
	        }
			break;	
			
		case 940:
			try {
				DormVisitInf[] result = dr.queryDormVisitInf(visitInfo);
				if(result != null) {
					this.oos.writeInt(9401);
					System.out.println("9401");
					this.oos.writeObject(result);
				}
				else {
					System.out.println("9402");
					this.oos.writeInt(9402);
				}
		          this.oos.flush();
			}
	        catch (IOException e)
	        {
	          e.printStackTrace();
	        }
			break;	
		}
	}
	
	private void Library(int cmd) {
		// TODO Auto-generated method stub
	    BookInfo bookInfo = null;
	    BookStatusInfo bsInfo = null;
	    Library lb = new Library();
	    //以40开头的表示与BookInfo操作相关
	    if (cmd / 10 == 40)
	    {
	      //获得客户端返回的图书信息
	      try
	      {
	        bookInfo = (BookInfo)this.ois.readObject();
	      }
	      catch (ClassNotFoundException e1)
	      {
	        e1.printStackTrace();
	      }
	      catch (IOException e1)
	      {
	        e1.printStackTrace();
	      }
	      switch (cmd)
	      {
	      //查询
	      case 401: {
	        try
	        {
	          //debug
	          System.out.println("查阅图书："+bookInfo.getName());
	          BookInfo[] result = lb.queryBook(bookInfo);
	          if (result != null)
	          {
	            this.oos.writeInt(4011); //查询成功
	            this.oos.writeObject(result);
	            //查询单本图书有图片:按名字查询或按id查询
	            if((!bookInfo.getName().equals(""))|(bookInfo.getId()!=0))
	            {
		            File file = new File("images\\"+result[0].getName()+".jpg");
		            if(!file.exists())
		            	file = new File("images\\无.jpg");
		            FileInputStream fis = new FileInputStream(file);
		            byte[] sendBytes = new byte[1024];
		            int length = 0;
	                while((length=fis.read(sendBytes)) != -1){
	                	this.dos.write(sendBytes, 0, length);
	                	this.dos.flush();
	                }
	          }
	          }
	          
	          else
	          {
	            this.oos.writeInt(4012); //查询失败
	          }
	          this.oos.flush();
	        }
	        catch (FileNotFoundException e)
	        {
				System.out.println("找不到文件");
			}
	        catch (IOException e)
	        {
	          e.printStackTrace();
	        }
	        break;
	      }
	      //增添
	      case 402: {
	        try
	        {
	          int writeBack = lb.addBook(bookInfo) ? 4021 : 4022;
	          this.oos.writeInt(writeBack);
	          this.oos.flush();
	        }
	        catch (IOException e)
	        {
	          e.printStackTrace();
	        }
	        break;
	      }
	      //删除
	      case 403: {
	        try
	        {
	        int writeBack = lb.deleteBook(bookInfo) ? 4031 : 4032;
		    this.oos.writeInt(writeBack);
		    this.oos.flush();
	        }
	        catch (IOException e)
	        {
	          e.printStackTrace();
	        }
	        break;
	      }
	      //修改
	      case 404: {
	        try
	        {
	          int writeBack = lb.modifyBook(bookInfo) ? 4041 : 4042;
	          this.oos.writeInt(writeBack);
	          this.oos.flush();
	        }
	        catch (IOException e)
	        {
	          e.printStackTrace();
	        }
	        break;
	      }
	      }
	    }
	    //以41开头的表示与BookStatusInfo操作相关
	    else
	    {
	      //获得客户端传递的图书状态信息
	      try
	      {
	        bsInfo = (BookStatusInfo)this.ois.readObject();
	      }
	      catch (ClassNotFoundException e1)
	      {
	        e1.printStackTrace();
	      }
	      catch (IOException e1)
	      {
	        e1.printStackTrace();
	      }
	      switch (cmd)
	      {
	      //借阅
	      case 411: {
	        try
	        {
	          int writeBack = lb.borrowBook(bsInfo) ? 4111 : 4112;
	          this.oos.writeInt(writeBack);
	          this.oos.flush();
	        }
	        catch (IOException e)
	        {
	          e.printStackTrace();
	        }
	        break;
	      }
	      //归还
	      case 412: {
	        try
	        {
	          int writeBack = lb.returnBook(bsInfo) ? 4121 : 4122;
	          this.oos.writeInt(writeBack);
	          this.oos.flush();
	        }
	        catch (IOException e)
	        {
	          e.printStackTrace();
	        }
	        break;
	      }
	      //图书状态查询
	      case 413: {
	        try
	        {
	          BookStatusInfo[] result = lb.queryStatus(bsInfo);
	          if (result != null)
	          {
	            this.oos.writeInt(4131);
	            this.oos.writeObject(result);
	          }
	          else
	          {
	            this.oos.writeInt(4132);
	          }
	          this.oos.flush();
	        }
	        catch (IOException e)
	        {
	          e.printStackTrace();
	        }
	        break;
	      }
	    //图书状态修改
	      case 414: {
	        try
	        {
	        	int writeBack = lb.modifyBookStatus(bsInfo) ? 4141 : 4142;
		        this.oos.writeInt(writeBack);
		        this.oos.flush();
	        }
	        catch (IOException e)
	        {
	          e.printStackTrace();
	        }
	        break;
	      }
	      }
	    }
	}
}
