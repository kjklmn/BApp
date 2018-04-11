package com.cjym.yunmabao.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.cjym.yunmabao.application.MainApplication;
import com.cjym.yunmabao.config.AppConfig;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class StringUtils {

	private static String PREFIX = "\\u";
//	private static BDHSToast toast = null;
	/**
	 * hexString2Bytes
	 */
	public static String toStringHex(String s) {
		byte[] baKeyword = new byte[s.length() / 2];
		for (int i = 0; i < baKeyword.length; i++) {
			try {
				baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			s = new String(baKeyword, "GBK");
//			s = new String(baKeyword);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return s;
	}

//	public static void showToast(Context context,String msg) {
//		if(toast == null) {
//
//			toast = new BDHSToast(MyApplication.getInstance().getApplicationContext());
//		}
//		toast.show(msg);
//	}
//
//	private static BDHSToast toastTest = null;
//	public static BDHSToast showToastTest(Context context,String msg) {
//		toastTest = new BDHSToast(context.getApplicationContext());
//		return toastTest;
//	}

	//
	public static String HexStringToString(String hexstr) {
		byte[] b = new byte[hexstr.length() / 2];
		int j = 0;
		for (int i = 0; i < b.length; i++) {
			char c0 = hexstr.charAt(j++);
			char c1 = hexstr.charAt(j++);
			b[i] = (byte) ((parse(c0) << 4) | parse(c1));
		}
		return new String(b);
	}

	private static int parse(char c) {
		if (c >= 'a')
			return (c - 'a' + 10) & 0x0f;
		if (c >= 'A')
			return (c - 'A' + 10) & 0x0f;
		return (c - '0') & 0x0f;
	}

	private static final char[] hex = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D','E', 'F' };
	//private final static byte[] hex = {0,1,2,3,4,5,6,7,8,9,0xA,0xB,0xC,0xD,0xE,0xF};//"0123456789ABCDEF".getBytes(); 

	//
	public static final String bytesToHexStr(byte[] data,int begin,int len)
	{
		if((data == null) || (len <= 0) || (data.length < begin + len))
		{
			return null;
		}

		StringBuffer buffer = new StringBuffer(len * 2);

		for (int i = begin; i < begin+len; i++)
		{
			buffer.append(hex[(data[i] >>> 4) & 0x0F]);
			buffer.append(hex[data[i] & 0x0F]);
//			buffer.append(" ");
		}

		return buffer.toString();
	}

	//"\u521d\u59cb\u5316\u8bbe\u5907\u5931\u8d25\uff0c\u95e8\u5e97ID\u6216\u5e8f\u5217\u53f7\u4e0d\u6b63\u786e"
	public static String ascii2Native(String str) {
		StringBuilder sb = new StringBuilder();
		int begin = 0;
		int index = str.indexOf(PREFIX);
		while (index != -1) {
			sb.append(str.substring(begin, index));
			sb.append(ascii2Char(str.substring(index, index + 6)));
			begin = index + 6;
			index = str.indexOf(PREFIX, begin);
		}
		sb.append(str.substring(begin));
		return sb.toString();
	}

	private static char ascii2Char(String str) {
		if (str.length() != 6) {
			throw new IllegalArgumentException(
					"Ascii string of a native character must be 6 character.");
		}
		if (!PREFIX.equals(str.substring(0, 2))) {
			throw new IllegalArgumentException(
					"Ascii string of a native character must start with \"\\u\".");
		}
		String tmp = str.substring(2, 4);
		int code = Integer.parseInt(tmp, 16) << 8;
		tmp = str.substring(4, 6);
		code += Integer.parseInt(tmp, 16);
		return (char) code;
	}


	public static boolean isMobile(String mobile) {

		String regExp = "^1[3|4|5|7|8]\\d{9}$";

		Pattern p = Pattern.compile(regExp);

		Matcher m = p.matcher(mobile);

		return m.find();//boolean
	}

	public static final int index = 1;//冒号
	public static String parseRule (String data,String key,int space) throws ArrayIndexOutOfBoundsException {
		if(data != null && key != null) {
			int firstKeyPos = data.indexOf(key) + key.length();
			if(data.indexOf(key) >= 0){
				int spaceAfterKey =  data.indexOf(" ", firstKeyPos+index+space);
				int newLine = data.indexOf("\n", firstKeyPos+index+space);
				String value = null;
				if(spaceAfterKey > 0 && spaceAfterKey < newLine) {//空格在换行之前
					value = data.substring(firstKeyPos+index+space, spaceAfterKey).trim();
				} else {//换行在空格之前
					if(data.indexOf("\n",firstKeyPos) > 0){
						spaceAfterKey =  data.indexOf("\n", firstKeyPos);
						if(spaceAfterKey > 0) {
							value = data.substring(firstKeyPos+index+space, spaceAfterKey).trim();
						} else {

						}
					}
				}
				if(AppConfig.DEBUG) {
					LogUtils.d("parseString", "全部数据 = " + data + ";" + "\nkey = " + key + ";\nvalue = " + value + ";\nkey位置 = " + firstKeyPos +
							";\nkey与金额之间的空隙 = " + (index + space)
							+ ";\n金额之后的位置 = " + spaceAfterKey
							+ ";\n");
				}
				return value;
			}
		}
		return "0";
	}

	//
	public static boolean isWellFormed(String mobile) {

		String regExp = "^[A-Za-z0-9]+$";

		Pattern p = Pattern.compile(regExp);

		Matcher m = p.matcher(mobile);

		return m.find();//boolean
	}

//	public static String getDeviceId() {
//		String device_id = android.provider.Settings.Secure.getString(MyApplication.getInstance().getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
////		LogUtils.w("device_id","ANDROID_ID = "+device_id);
//		return device_id;
//	}
//	public static String getDeviceId() {
//		TelephonyManager TelephonyMgr = (TelephonyManager) MyApplication.getInstance().getSystemService(MyApplication.getInstance().TELEPHONY_SERVICE);
//		String szImei = TelephonyMgr.getDeviceId();//null
//
//		WifiManager wm = (WifiManager) MyApplication.getInstance().getSystemService(Context.WIFI_SERVICE);
//		String m_szWLANMAC = wm.getConnectionInfo().getMacAddress();
//		String m_szLongID = szImei + m_szWLANMAC;
//		// compute md5
//		MessageDigest m = null;
//		try {
//			m = MessageDigest.getInstance("MD5");
//		} catch (NoSuchAlgorithmException e) {
//			e.printStackTrace();
//		}
//		m.update(m_szLongID.getBytes(), 0, m_szLongID.length());
//		// get md5 bytes
//		byte p_md5Data[] = m.digest();
//		// create a hex string
//		String m_szUniqueID = new String();
//		for (int i = 0; i < p_md5Data.length; i++) {
//			int b = (0xFF & p_md5Data[i]);
//			if (b <= 0xF)
//				m_szUniqueID += "0";
//			m_szUniqueID += Integer.toHexString(b);
//			m_szUniqueID = m_szUniqueID.toUpperCase();
//		}
//
////		String ANDROID_ID = android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
//		LogUtils.w("StringUtils","android.os.Build.SERIAL = "+android.os.Build.SERIAL);
//		LogUtils.w("StringUtils","szImei = "+szImei);
//		LogUtils.w("StringUtils","m_szWLANMAC = "+m_szWLANMAC);
//		LogUtils.w("StringUtils","m_szUniqueID = "+m_szUniqueID);
//		return m_szUniqueID;
//	}

//	public static String runScript(String js, String functionName, Object[] functionParams,Context mContext) throws Exception {
//		org.mozilla.javascript.Context rhino = org.mozilla.javascript.Context.enter();
//		rhino.setOptimizationLevel(-1);
//		try {
//			Scriptable scope = rhino.initStandardObjects();
//
//			ScriptableObject.putProperty(scope, "javaContext", org.mozilla.javascript.Context.javaToJS(mContext, scope));
//			ScriptableObject.putProperty(scope, "javaLoader", org.mozilla.javascript.Context.javaToJS(Ticket.class.getClassLoader(), scope));
//
//			rhino.evaluateString(scope, js/*JAVA_CALL_JS_FUNCTION*/, "MainActivity", 1, null);
//
//			Function function = (Function) scope.get(functionName, scope);
//
//			Object result = function.call(rhino, scope, scope, functionParams);
//			if (result instanceof String) {
//				return (String) result;
//			} else if (result instanceof NativeJavaObject) {
//				return (String) ((NativeJavaObject) result).getDefaultValue(String.class);
//			} else if (result instanceof NativeObject) {
//				return (String) ((NativeObject) result).getDefaultValue(String.class);
//			}
//			return result.toString();//(String) function.call(rhino, scope, scope, functionParams);
//		} finally {
//			org.mozilla.javascript.Context.exit();
//		}
//	}

	public static boolean isNetworkConnected() {
		if (MainApplication.getInstance() != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) MainApplication.getInstance().getApplicationContext()
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
			if (mNetworkInfo != null) {
				return mNetworkInfo.isAvailable();
			}

		}
		return false;
	}

	//判断点击的是否在V内
	public static boolean isShouldHideInput(View v, MotionEvent event)
	{
		// TODO Auto-generated method stub
		if (v != null && (v instanceof EditText)) {
			int[] leftTop = { 0, 0 };
			//获取输入框当前的location位置
			v.getLocationInWindow(leftTop);
			int left = leftTop[0];
			int top = leftTop[1];
			int bottom = top + v.getHeight();
			int right = left + v.getWidth();
			if (event.getX() > left && event.getX() < right
					&& event.getY() > top && event.getY() < bottom) {
				// 点击的是输入框区域，保留点击EditText的事件
				return false;
			} else {
				return true;
			}
		}
		return false;
	}

	//把负数变为正数
	public static int Trim(byte data) {
		int temp;
		if (data < 0) {
			temp = data & 0x7f;//最高位清零
			temp = temp | 0x80;//最高位设为1
		} else {
			temp = data;//
		}
		return temp;
	}

	/**
	 * 大陆号码或香港号码均可
	 */
	public static boolean isPhoneLegal(String str)  throws PatternSyntaxException{
		return isChinaPhoneLegal(str) || isHKPhoneLegal(str);
	}

	/**
	 * 大陆手机号码11位数，匹配格式：前三位固定格式+后8位任意数
	 * 此方法中前三位格式有：
	 * 13+任意数
	 * 15+除4的任意数
	 * 18+除1和4的任意数
	 * 17+除9的任意数
	 * 147
	 */
	public static boolean isChinaPhoneLegal(String str) throws PatternSyntaxException {
		String regExp = "^((13[0-9])|(15[^4])|(18[0-3,5-9])|(17[0-8])|(147))\\d{8}$";
		Pattern p = Pattern.compile(regExp);
		Matcher m = p.matcher(str);
		return m.matches();
	}

	/**
	 * 香港手机号码8位数，5|6|8|9开头+7位任意数
	 */
	public static boolean isHKPhoneLegal(String str)throws PatternSyntaxException {
		String regExp = "^(5|6|8|9)\\d{7}$";
		Pattern p = Pattern.compile(regExp);
		Matcher m = p.matcher(str);
		return m.matches();
	}

	public static String replaceByStars(String ori,int start,int offsize) {
		String result = "";
		if(ori != null && ori.length() >= (start+offsize)) {
			if(offsize > 0) {
				StringBuffer sb = new StringBuffer(ori);
				StringBuffer stars = new StringBuffer();
				for(int i=0;i<offsize;i++) {
					stars.append("*");
				}
				sb.replace(start, start+offsize, stars.toString());
				return sb.toString();
			} else {
				return ori;
			}

		}
		return result;
	}
}
