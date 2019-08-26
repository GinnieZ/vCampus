package vc.server;

import vc.common.CourseInfo;
import vc.common.CourseSelectedInfo;
import vc.common.MsgType;
import vc.common.UserInfo;
import vc.helper.Login;
import vc.helper.Course;
import vc.view.ServerView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;

public class ClientThread extends Thread implements MsgType {
	private ServerThread currentServer;
	private Socket client;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	public String curUser;

	public ClientThread(Socket s, ServerThread st) {
		this.client = s;
		this.currentServer = st;
		this.curUser = "";
		try {
			this.ois = new ObjectInputStream(this.client.getInputStream());
			this.oos = new ObjectOutputStream(this.client.getOutputStream());

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
			case 6:
				Course(cmd);
				break;
			}
		}
	}

	public void close() {
		if (this.client != null) {
			try {
				this.oos.close();
				this.ois.close();
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

	private void Course(int cmd) {
		CourseInfo courseInfo = null;
		CourseSelectedInfo csInfo = null;
		Course cs = new Course();
		if (cmd / 10 == 60) {
			try {
				if (cmd != 601) {
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
}