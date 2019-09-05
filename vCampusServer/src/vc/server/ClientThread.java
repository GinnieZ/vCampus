package vc.server;

import vc.common.BankInfo;
import vc.helper.Bank;
import vc.common.CourseInfo;
import vc.common.CourseSelectedInfo;
import vc.common.MsgType;
import vc.common.OnlineClassInfo;
import vc.common.OnlineClassSelectedInfo;
import vc.common.StudentRollInfo;
import vc.common.UserInfo;
import vc.helper.Login;
import vc.helper.Course;
import vc.helper.OnlineClass;
import vc.helper.Student;
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
			case 6:
				Course(cmd);
				break;
			case 9:
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
		if (cmd / 10 == 90) {
			try {
				if (cmd != 901 && cmd != 906 && cmd != 907 && cmd != 908) {
					courseInfo = (OnlineClassInfo) this.ois.readObject();
				}
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			switch (cmd) {
			case 901:
				try {
					OnlineClassInfo[] result = cs.queryClass();
					if (result != null) {
						this.oos.writeInt(9011);
						this.oos.writeObject(result);
					} else {
						this.oos.writeInt(9012);
					}
					this.oos.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case 902:
				try {
					int wb = cs.addClass(courseInfo) ? 9021 : 9022;
					this.oos.writeInt(wb);

					this.oos.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case 903:
				try {
					int wb = cs.deleteClass(courseInfo) ? 9031 : 9032;
					this.oos.writeInt(wb);

					this.oos.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case 904:
				try {
					int wb = cs.modifyClass(courseInfo) ? 9041 : 9042;
					this.oos.writeInt(wb);

					this.oos.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case 905:
				try {
					String[] result = cs.queryStudent(courseInfo);
					if (result != null) {
						this.oos.writeInt(9051);
						this.oos.writeObject(result);
					}
					else {
						this.oos.writeInt(9052);
					}
				}catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case 906:
				try {
					String tempId = (String) this.ois.readObject();
					OnlineClassInfo[] result = cs.queryClassById(tempId);
					if (result != null) {
						this.oos.writeInt(9061);
						this.oos.writeObject(result);
					}
					else {
						this.oos.writeInt(9062);
					}
				}catch (IOException | ClassNotFoundException e) {
					e.printStackTrace();
				}
				break;
			case 907:
				try {
					String tempName = (String) this.ois.readObject();
					OnlineClassInfo[] result = cs.queryClassByName(tempName);
					if (result != null) {
						this.oos.writeInt(9071);
						this.oos.writeObject(result);
					}
					else {
						this.oos.writeInt(9072);
					}
				}catch (IOException | ClassNotFoundException e) {
					e.printStackTrace();
				}
				break;
			case 908:
				try {
					String tempTeacher = (String) this.ois.readObject();
					OnlineClassInfo[] result = cs.queryClassByTeacher(tempTeacher);
					if (result != null) {
						this.oos.writeInt(9081);
						this.oos.writeObject(result);
					}
					else {
						this.oos.writeInt(9082);
					}
				}catch (IOException | ClassNotFoundException e) {
					e.printStackTrace();
				}
				break;
			}
			
		} else if (cmd / 10 == 91){
			try {
				if (cmd != 914) {
					csInfo = (OnlineClassSelectedInfo) this.ois.readObject();
					System.out.println("read: " + csInfo.getId() + " " + csInfo.getSelector());
				}
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			switch (cmd) {
			case 911:
				try {
					int wb = cs.selectClass(csInfo) ? 9111 : 9112;
					this.oos.writeInt(wb);

					this.oos.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case 912:
				try {
					int wb = cs.deselectClass(csInfo) ? 9121 : 9122;
					this.oos.writeInt(wb);

					this.oos.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case 913:
				try {
					OnlineClassInfo[] result = cs.queryCurriculum(csInfo);
					if (result != null) {
						this.oos.writeInt(9131);
						this.oos.writeObject(result);
					} else {
						this.oos.writeInt(9132);
					}
					this.oos.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;	
			case 914:
				try {
					OnlineClassInfo cInfo = (OnlineClassInfo) this.ois.readObject();

					OnlineClassSelectedInfo[] result = cs.queryStatus(cInfo);
					if (result != null) {
						this.oos.writeInt(9141);
						this.oos.writeObject(result);
					} else {
						this.oos.writeInt(9142);
					}
					this.oos.flush();
				} catch (IOException | ClassNotFoundException e) {
					e.printStackTrace();
				}
				break;
			case 915:
				try {
					int wb = cs.forwardClass(csInfo) ? 9151 : 9152;
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
			case 921:
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
	      if (result != 0.0D)
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
}