package vc.sendImpl;

import java.awt.List;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;

import vc.common.MsgType;
import vc.common.DormChargeInf;
import vc.common.DormLivingInf;
import vc.common.DormRepairInf;
import vc.common.DormUtilityBillsInf;
import vc.common.DormVisitInf;
import vc.helper.SocketHelper;
import vc.send.IDorm;

public class IDormImpl 
implements MsgType,IDorm
{
	ObjectInputStream is;
	ObjectOutputStream os;

	public IDormImpl(SocketHelper sockethelper) {
		// TODO Auto-generated constructor stub this.sockethelper
		 this.is = sockethelper.getIs();
		 this.os = sockethelper.getOs();
	}

	@Override
	public java.util.List<DormLivingInf> QueryDormLivingInf(DormLivingInf paramDormLivingInf) {
		// TODO Auto-generated method stub
	    try
	    {
	      this.os.writeInt(901);
	      this.os.flush();
	      this.os.writeObject(paramDormLivingInf);
	      this.os.flush();
	      if (this.is.readInt() == 9011) {
	    	  System.out.println("Client接收到了9011");
	    		 try {
					return  Arrays.asList((DormLivingInf[])this.is.readObject());
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	      }
	    }
	    catch (IOException e)
	    {
	      e.printStackTrace();
	    }
	    System.out.println("没有接收到信息");
	    return null;
	}

	@Override
	public java.util.List<DormRepairInf> QueryDormRepairInf(DormRepairInf paramDormRepairInf) {
		// TODO Auto-generated method stub
	    try
	    {
	      this.os.writeInt(905);
	      this.os.flush();
	      this.os.writeObject(paramDormRepairInf);
	      this.os.flush();
	      if (this.is.readInt() == 9051) {
	    	  System.out.println("Client接收到了9051");
	    		 try {
	    			 return  Arrays.asList((DormRepairInf[])this.is.readObject());
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	      }
	    }
	    catch (IOException e)
	    {
	      e.printStackTrace();
	    }
	    System.out.println("没有接收到信息");
		return null;
	}

	@Override
	public java.util.List<DormChargeInf> QueryDormChargeInf(DormChargeInf paramDormChargeInf) {
		// TODO Auto-generated method stub
	    try
	    {
	      this.os.writeInt(909);
	      this.os.flush();
	      this.os.writeObject(paramDormChargeInf);
	      this.os.flush();
	      if (this.is.readInt() == 9091) {
	    	  System.out.println("Client接收到了9091");
	    		 try {
	    			 return  Arrays.asList((DormChargeInf[])this.is.readObject());
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	      }
	    }
	    catch (IOException e)
	    {
	      e.printStackTrace();
	    }
	    System.out.println("没有收到信息");
		return null;
	}
	
	/**
	 新增宿舍维修信息
	 * @param paramDormRepairInf
	 * @return boolean
	 */
	@Override
	public boolean AddDormRepairInf(DormRepairInf paramDormRepairInf) {
	    try
	    {
	      this.os.writeInt(906);
	      this.os.flush();
	      this.os.writeObject(paramDormRepairInf);
	      this.os.flush();
	      if (this.is.readInt() == 9061) {
	    	  System.out.println("Client收到了9061");
	    		 return  true;
	      	}
	    }
	    catch (IOException e)
	    {
	      e.printStackTrace();
	    }
	    System.out.println("没有接收到信息");
		return false;
	}
	

	@Override
	public java.util.List<DormVisitInf> QueryDormVisitInf(DormVisitInf paramDormVisitInf) {
		// TODO Auto-generated method stub
	    try
	    {
	      this.os.writeInt(940);
	      this.os.flush();
	      this.os.writeObject(paramDormVisitInf);
	      this.os.flush();
	      if (this.is.readInt() == 9401) {
	    	  System.out.println("Client收到了9401");
	    		 try {
	    			 return  Arrays.asList((DormVisitInf[])this.is.readObject());
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	      }
	    }
	    catch (IOException e)
	    {
	      e.printStackTrace();
	    }
	    System.out.println("没有接收到信息");
		return null;
	}

	@Override
	public boolean ModifyDormLivingInf(DormLivingInf paramDormLivingInf) {
		// TODO Auto-generated method stub
		 try
		    {
		      this.os.writeInt(904);
		      this.os.flush();
		      this.os.writeObject(paramDormLivingInf);
		      this.os.flush();
		      if (this.is.readInt() == 9041) {
		    	  System.out.println("Client收到了9041");
		    		return true;
		      }
		    }
		    catch (IOException e)
		    {
		      e.printStackTrace();
		    }
			return false;
	}

	@Override
	public boolean AddDormChargeInf(DormChargeInf paramDormChargeInf) {
		// TODO Auto-generated method stub
		 try
		    {
		      this.os.writeInt(910);
		      System.out.println("发送了910");
		      this.os.flush();
		      this.os.writeObject(paramDormChargeInf);
		      this.os.flush();
		      if (this.is.readInt() == 9101) {
		    	  System.out.println("Client接收了9101");
		    		return true;
		      }
		    }
		    catch (IOException e)
		    {
		      e.printStackTrace();
		    }
			return false;
	}

	/**
	 每月水电费用信息查询
	 * @param DormUtilityBillsInf
	 * @return List<DormUtilityBillsInf>
	 */
	@Override
	public java.util.List<DormUtilityBillsInf>  QueryDormUtilityBillsInf(DormUtilityBillsInf paramDormUtilityBillsInf) {
	    try
	    {
	      this.os.writeInt(920);
	      this.os.flush();
	      this.os.writeObject(paramDormUtilityBillsInf);
	      this.os.flush();
	      if (this.is.readInt() == 9201) {
	    	  System.out.println("Client收到了9201");
	    		 try {
	    			 return  Arrays.asList((DormUtilityBillsInf[])this.is.readObject());
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	      }
	    }
	    catch (IOException e)
	    {
	      e.printStackTrace();
	    }
	    System.out.println("没有接收到信息");
		return null;
	}
}
