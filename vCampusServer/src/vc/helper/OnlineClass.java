package vc.helper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import vc.common.CourseInfo;
import vc.common.CourseSelectedInfo;
import vc.common.OnlineClassInfo;
import vc.common.OnlineClassSelectedInfo;
import vc.db.OnlineClassModel;
import vc.db.OnlineClassSelectedModel;

public class OnlineClass {
	private OnlineClassModel cModel;
	private OnlineClassSelectedModel csModel;

	public OnlineClass() {
		this.cModel = new OnlineClassModel();
		this.csModel = new OnlineClassSelectedModel();
	}

	public OnlineClassInfo[] queryClass() {
		try {
			ResultSet rs = (ResultSet) this.cModel.search(null);
			Vector<OnlineClassInfo> v = new Vector();
			while (rs.next()) {
				OnlineClassInfo temp = new OnlineClassInfo(rs.getString("ID"), rs.getString("ClassName"),
						rs.getString("teacher"), rs.getInt("period"));
				v.add(temp);
			}
			return (OnlineClassInfo[]) v.toArray(new OnlineClassInfo[rs.getRow()]);
		} catch (SQLException e) {
			System.out.println("Database exception");
			e.printStackTrace();
		}
		return null;
	}

	public String[] queryStudent(OnlineClassInfo info) {
		List<String> l = new ArrayList<>();
		try {
			OnlineClassSelectedInfo temp = new OnlineClassSelectedInfo(info.getId(), "", 0);
			ResultSet rs = (ResultSet) this.csModel.search(temp);
			if (rs != null) {
				while (rs.next()) {
					l.add(rs.getString("selector"));
				}

				return (String[]) l.toArray(new String[l.size()]);
			}
		} catch (SQLException e) {
			System.out.println("Database exception");
			e.printStackTrace();
		}
		return null;
	}

	public OnlineClassInfo[] queryClassById(String tempId) {
		try {
			OnlineClassInfo searchId = new OnlineClassInfo(tempId, "", "", 0);
			ResultSet rs = (ResultSet) this.cModel.search(searchId);
			Vector<OnlineClassInfo> v = new Vector();
			if (rs != null) {
				while (rs.next()) {
					OnlineClassInfo temp = new OnlineClassInfo(rs.getString("ID"), rs.getString("ClassName"),
							rs.getString("teacher"), rs.getInt("period"));
					v.add(temp);
				}
				return (OnlineClassInfo[]) v.toArray(new OnlineClassInfo[rs.getRow()]);
			}
		} catch (SQLException e) {
			System.out.println("Database exception");
			e.printStackTrace();
		}
		return null;
	}
	
	public OnlineClassInfo[] queryClassByName(String tempN) {
		try {
			OnlineClassInfo searchName = new OnlineClassInfo("", tempN, "", 0);
			ResultSet rs = (ResultSet) this.cModel.search(searchName);
			Vector<OnlineClassInfo> v = new Vector();
			if (rs != null) {
				while (rs.next()) {
					OnlineClassInfo temp = new OnlineClassInfo(rs.getString("ID"), rs.getString("ClassName"),
							rs.getString("teacher"), rs.getInt("period"));
					v.add(temp);
				}
				return (OnlineClassInfo[]) v.toArray(new OnlineClassInfo[rs.getRow()]);
			}
		} catch (SQLException e) {
			System.out.println("Database exception");
			e.printStackTrace();
		}
		return null;
	}
	
	public OnlineClassInfo[] queryClassByTeacher(String tempT) {
		try {
			OnlineClassInfo searchTeacher = new OnlineClassInfo("", "", tempT, 0);
			ResultSet rs = (ResultSet) this.cModel.search(searchTeacher);
			Vector<OnlineClassInfo> v = new Vector();
			if (rs != null) {
				while (rs.next()) {
					OnlineClassInfo temp = new OnlineClassInfo(rs.getString("ID"), rs.getString("ClassName"),
							rs.getString("teacher"), rs.getInt("period"));
					v.add(temp);
				}
				return (OnlineClassInfo[]) v.toArray(new OnlineClassInfo[rs.getRow()]);
			}
		} catch (SQLException e) {
			System.out.println("Database exception");
			e.printStackTrace();
		}
		return null;
	}

	public boolean addClass(OnlineClassInfo info) {
		return this.cModel.insert(info);
	}

	public boolean deleteClass(OnlineClassInfo info) {
		return this.cModel.delete(info);
	}

	public boolean modifyClass(OnlineClassInfo info) {
		return this.cModel.modify(info);
	}

	public boolean selectClass(OnlineClassSelectedInfo info) {
		return this.csModel.insert(info);
	}

	public boolean deselectClass(OnlineClassSelectedInfo info) {
		return this.csModel.delete(info);
	}

	public OnlineClassSelectedInfo[] queryStatus(OnlineClassInfo info) {
		try {
			ResultSet rs = (ResultSet) this.cModel.search(info);
			Vector<OnlineClassSelectedInfo> v = new Vector();
			if (rs.next()) {
				OnlineClassSelectedInfo temp = new OnlineClassSelectedInfo(rs.getString("ID"), null, 0);

				rs = (ResultSet) this.csModel.search(temp);
				while (rs.next()) {
					OnlineClassSelectedInfo temp1 = new OnlineClassSelectedInfo(rs.getString("ID"), 
							rs.getString("selector"), rs.getInt("currentPeriod"));
					v.add(temp1);
				}
			}
			return (OnlineClassSelectedInfo[]) v.toArray(new OnlineClassSelectedInfo[rs.getRow()]);
		} catch (SQLException e) {
			System.out.println("Database exception");
			e.printStackTrace();
		}
		return null;
	}
	
	public OnlineClassInfo[] queryCurriculum(OnlineClassSelectedInfo info) {
		try {
			ResultSet rs = (ResultSet) this.csModel.search(info);
			Vector<OnlineClassInfo> v = new Vector();
			Vector<OnlineClassInfo> v1 = new Vector();
			while (rs.next()) {
				OnlineClassInfo temp = new OnlineClassInfo(rs.getString("ID"), "", "", 0);

				v1.add(temp);
			}
			for (int i = 0; i < v1.size(); i++) {
				rs = (ResultSet) this.cModel.search(v1.get(i));
				if (rs.next()) {
					OnlineClassInfo temp = new OnlineClassInfo(rs.getString("ID"), rs.getString("className"),
							rs.getString("teacher"),
							rs.getInt("period"));
					v.add(temp);
				}
			}
			return (OnlineClassInfo[]) v.toArray(new OnlineClassInfo[rs.getRow()]);
		} catch (SQLException e) {
			System.out.println("Database exception");
			e.printStackTrace();
		}
		return null;
	}
	

	public boolean forwardClass(OnlineClassSelectedInfo info) {
		OnlineClassInfo searchId = new OnlineClassInfo(info.getId(), "", "", 0);
		ResultSet rs = (ResultSet) this.cModel.search(searchId);
		if (rs != null) {
			try {
				while (rs.next()) {
					if(rs.getInt("period") == info.getCurrentPeriod())
					{
						System.out.println("全部学完！");
						return false;
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		OnlineClassSelectedInfo newInfo = new OnlineClassSelectedInfo(info.getId(), info.getSelector(), info.getCurrentPeriod() + 1);
		return this.csModel.modify(newInfo);
	}
	
}
