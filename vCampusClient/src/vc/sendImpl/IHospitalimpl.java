package vc.sendImpl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.ResultSet;

import vc.common.CourseInfo;
import vc.common.MedcineInfo;
import vc.common.PatientInfo;
import vc.common.UserInfo;
import vc.helper.SocketHelper;

public class IHospitalimpl {
	ObjectInputStream is;
	ObjectOutputStream os;
	String id;
	
	public IHospitalimpl(String StudentId, SocketHelper sockethelper) {
		id = StudentId;
		this.is = sockethelper.getIs();
		this.os = sockethelper.getOs();
	}
	
	public int register(PatientInfo patient) {
		try {
			this.os.writeInt(802);
			this.os.flush();
			
			this.os.writeObject(patient);
			this.os.flush();
			int register = this.is.readInt();
			//rs = register;
			if (this.is.readInt() == 8021) {
				//需要客户端发送俩次
				//System.out.println("挂号成功，您的号码为" + register);
				return register;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return -1;
	}

	public boolean addMedcine(MedcineInfo medcine) {
		try {
			this.os.writeInt(823);
			this.os.flush();

			this.os.writeObject(medcine);
			this.os.flush();
			if (this.is.readInt() == 8231) {
				return true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean deleteMedcine(MedcineInfo medcine) {
		try {
			this.os.writeInt(809);
			this.os.flush();

			this.os.writeObject(medcine);
			this.os.flush();
			if (this.is.readInt() == 8091) {
				return true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean pay(PatientInfo info) {
		try {
			this.os.writeInt(804);
			this.os.flush();

			this.os.writeObject(info);
			this.os.flush();
			if (this.is.readInt() == 8041) {
				return true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	public PatientInfo getUserInfo(UserInfo user) throws ClassNotFoundException {
		try {
			this.os.writeInt(805);
			this.os.flush();
			
			this.os.writeObject(user);
			this.os.flush();
			if (this.is.readInt() == 8051) {
				PatientInfo info = (PatientInfo)this.is.readObject();
				return info;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/*
	public UserInfo getUserInfo1(UserInfo user) throws ClassNotFoundException {
		try {
			this.os.writeInt(807);
			this.os.flush();
			this.os.writeObject(user);
			this.os.flush();
			if (this.is.readInt() == 8071) {
				patient = (PatientInfo)this.is.readObject();
				return patient;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	*/
	public PatientInfo getPatientInfo(PatientInfo patient) throws ClassNotFoundException {
		try {
			this.os.writeInt(807);
			this.os.flush();
			this.os.writeObject(patient);
			this.os.flush();
			if (this.is.readInt() == 8071) {
				patient = (PatientInfo)this.is.readObject();
				return patient;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public MedcineInfo getMedcineInfo(MedcineInfo medcine) throws ClassNotFoundException {
		try {
			this.os.writeInt(803);
			this.os.flush();
			this.os.writeObject(medcine);
			this.os.flush();
			if (this.is.readInt() == 8031) {
				medcine = (MedcineInfo)this.is.readObject();
				return medcine;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean modifyPatientInfo(PatientInfo patient) throws ClassNotFoundException {
		try {
			this.os.writeInt(810);
			this.os.flush();

			this.os.writeObject(patient);
			this.os.flush();
			if (this.is.readInt() == 8101) {
				//patient = (PatientInfo)this.is.readObject();
				return true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean addPatient(PatientInfo patient) throws ClassNotFoundException {
		try {
			this.os.writeInt(811);
			this.os.flush();

			this.os.writeObject(patient);
			this.os.flush();
			if (this.is.readInt() == 8101) {
				//patient = (PatientInfo)this.is.readObject();
				return true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean purchase(MedcineInfo medcine) {
		try {
			this.os.writeInt(806);
			this.os.flush();

			this.os.writeObject(medcine);
			this.os.flush();
			if (this.is.readInt() == 8061) {
				return true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public String[] readMDay(String id) {
		try {
			this.os.writeInt(820);
			this.os.flush();
			this.os.writeObject(id);
			this.os.flush();
			String[] day = (String[]) this.is.readObject();
			//String[] mhis = (String[]) this.is.readObject();
			if (this.is.readInt() == 8201) {
				return day;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String[] readMHistory(String id) {
		try {
			this.os.writeInt(821);
			this.os.flush();
			this.os.writeObject(id);
			this.os.flush();
			//String[] day = (String[]) this.is.readObject();
			//String[] mhis = (String[]) this.is.readObject();
			String[] rs = (String[]) this.is.readObject();
			if (this.is.readInt() == 8211) {
				return rs;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean addMHistory(String id,String MHistory) {
		try {
			this.os.writeInt(822);
			this.os.flush();
			this.os.writeObject(id);
			this.os.flush();
			this.os.writeObject(MHistory);
			this.os.flush();
			if (this.is.readInt() == 8221) {
				return true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
}
