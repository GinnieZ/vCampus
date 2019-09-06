package vc.send;
/** ÒøĞĞ
* @author 09017406
* ¿âÄË¡¤°¢Õ¨Ìâ
*/
import java.util.List;

public abstract interface IBank {
	public abstract double checkAccount(String paramString);
	public abstract boolean transferAccount(String paramString1,double paramDouble1, String paramString2, String paramString3, double paramDouble2);

}
