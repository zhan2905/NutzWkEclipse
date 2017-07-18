package cn.wizzer.common.util;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Lang;
import org.nutz.lang.Strings;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.Mvcs;
/**
 * Created by Wizzer.cn on 2015/7/4.
 */
@IocBean
public class StringUtil {

	private static final Log log = Logs.get();
	
	public static final String EQUAL_SIGN 				= "=";
	public static final String EQUAL 					= "equal";
	public static final String LIKE 					= "like";
	public static final String LESSTHAN_SIGN			= "<";
	public static final String LESSTHAN					= "less";
	public static final String GREATERTHAN_SIGN 		= ">";
	public static final String GREATERTHAN  			= "greater";
	public static final String LESSTHANEQUAL_SIGN 		= "<=";
	public static final String LESSTHANEQUAL 			= "lessEqual";
	public static final String GREATERTHANEQUAL_SIGN 	= ">=";
	public static final String GREATERTHANEQUAL			= "greaterEqual";
	public static final String NOTEQUAL_SIGN 			= "!=";
	public static final String NOTEQUAL 				= "notEqual";
	public static final String BETWEEN 					= "between";
    private static final Pattern IPV4_PATTERN =
            Pattern.compile(
                    "^(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}$");

    private static final Pattern IPV6_STD_PATTERN =
            Pattern.compile(
                    "^(?:[0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}$");

    private static final Pattern IPV6_HEX_COMPRESSED_PATTERN =
            Pattern.compile(
                    "^((?:[0-9A-Fa-f]{1,4}(?::[0-9A-Fa-f]{1,4})*)?)::((?:[0-9A-Fa-f]{1,4}(?::[0-9A-Fa-f]{1,4})*)?)$");

	private final static Pattern emailer = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
    
    public static boolean isIPv4Address(final String input) {
        return IPV4_PATTERN.matcher(input).matches();
    }

    public static boolean isIPv6StdAddress(final String input) {
        return IPV6_STD_PATTERN.matcher(input).matches();
    }

    public static boolean isIPv6HexCompressedAddress(final String input) {
        return IPV6_HEX_COMPRESSED_PATTERN.matcher(input).matches();
    }

    public static boolean isIPv6Address(final String input) {
        return isIPv6StdAddress(input) || isIPv6HexCompressedAddress(input);
    }

    /**
     * 计算MD5密码
     *
     * @param loginname
     * @param password
     * @param createAt
     * @return
     */
    public static String getPassword(String loginname, String password, int createAt) {
        String p = Lang.md5(Lang.md5(password) + loginname + createAt);
        return 'w' + p.substring(0, p.length() - 1);
    }

    /**
     * 获得用户远程地址
     */
    public static String getRemoteAddr() {
        HttpServletRequest request = Mvcs.getReq();
        String remoteAddr = request.getHeader("X-Real-IP");
        if (Strings.isNotBlank(remoteAddr)) {
            remoteAddr = request.getHeader("X-Forwarded-For");
        } else if (Strings.isNotBlank(remoteAddr)) {
            remoteAddr = request.getHeader("Proxy-Client-IP");
        } else if (Strings.isNotBlank(remoteAddr)) {
            remoteAddr = request.getHeader("WL-Proxy-Client-IP");
        }
        String ip = remoteAddr != null ? remoteAddr : Strings.sNull(request.getRemoteAddr());
        if (isIPv4Address(ip) || isIPv6Address(ip)) {
            return ip;
        }
        return "";
    }

    /**
     * 去掉URL中?后的路径
     *
     * @param p
     * @return
     */
    public static String getPath(String p) {
        if (Strings.sNull(p).contains("?")) {
            return p.substring(0, p.indexOf("?"));
        }
        return Strings.sNull(p);
    }

    /**
     * 获得父节点ID
     *
     * @param s
     * @return
     */
    public static String getParentId(String s) {
        if (!Strings.isEmpty(s) && s.length() > 4) {
            return s.substring(0, s.length() - 4);
        }
        return "";
    }

    /**
     * 得到n位随机数
     *
     * @param s
     * @return
     */
    public static String getRndNumber(int s) {
        Random ra = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s; i++) {
            sb.append(String.valueOf(ra.nextInt(8)));
        }
        return sb.toString();
    }

    /**
     * 判断是否以字符串开头
     *
     * @param str
     * @param s
     * @return
     */
    public boolean startWith(String str, String s) {
        return Strings.sNull(str).startsWith(Strings.sNull(s));
    }

    /**
     * 判断是否包含字符串
     *
     * @param str
     * @param s
     * @return
     */
    public boolean contains(String str, String s) {
        return Strings.sNull(str).contains(Strings.sNull(s));
    }
    
    private final static ThreadLocal<SimpleDateFormat> dateFormater = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		}
	};

	private final static ThreadLocal<SimpleDateFormat> dateFormater2 = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd");
		}
	};
	
	private final static ThreadLocal<SimpleDateFormat> dateFormater3 = new ThreadLocal<SimpleDateFormat>(){
		@Override
		protected SimpleDateFormat initialValue(){
			return new SimpleDateFormat("yyyyMMddHHmmss");
		}
	};
	
	public static boolean isDate(String sdate){
		return toDate(sdate) != null;
	}
	
	public static boolean isDatetime(String sdatetime){
		return toDatetime(sdatetime) != null;
	}

	/**
	 * 灏嗗瓧绗︿覆杞綅鏃ユ湡绫诲瀷
	 * @param sdate
	 * @return
	 */
	public static Date toDate(String sdate) {
		try {
			return dateFormater2.get().parse(sdate);
		} catch (ParseException e) {
			return null;
		}
	}
	
	/**
	 * 灏嗗瓧绗︿覆杞綅鏃ユ湡绫诲瀷
	 * @param sdate
	 * @return
	 */
	public static Date toDatetime(String sdate) {
		try {
			return dateFormater.get().parse(sdate);
		} catch (ParseException e) {
			return null;
		}
	}
	
	public static Timestamp toCompactTimestamp(String str){
		try	{
			return new Timestamp(dateFormater3.get().parse(str).getTime());
		} catch (ParseException e){
			return null;
		}
	}
	/**
	 * 灏嗘棩鏈熺被鍨嬭浆浣嶅瓧绗︿覆
	 * @param sdate
	 * @return
	 */
	public static String fmtDate(Date date)
    {
		return fmtDate(date, "yyyy-MM-dd");
    }
	
	public static String fmtDate(Date date, String format){
		String dateStr ="";
		
		if(date !=null)
		{
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			dateStr = sdf.format(date);
		}
		return dateStr;
	}
	
	public static String fmtDate_(Date date)
    {
		return fmtDate(date, "yyyy-MM-dd HH:mm:ss");
    }
		
	public static String fmtHandM(Date date)
    {
		return fmtDate(date, "HH:mm");
    }
	
	public static String getFileName(Date date)
    {
		return fmtDate(date, "yyyyMMddHHmmssSSS");
    }
	
	public static String getNowMonth(Date date)
    {
		return fmtDate(date, "yyyy-MM");
    }
	
	/**
	 * 浠ュ弸濂界殑鏂瑰紡鏄剧ず鏃堕棿
	 * @param sdate
	 * @return
	 */
	public static String friendly_time(String sdate) {
		Date time = toDate(sdate);
		if(time == null) {
			return "Unknown";
		}
		String ftime = "";
		Calendar cal = Calendar.getInstance();
		
		//鍒ゆ柇鏄惁鏄悓涓�澶�
		String curDate = dateFormater2.get().format(cal.getTime());
		String paramDate = dateFormater2.get().format(time);
		if(curDate.equals(paramDate)){
			int hour = (int)((cal.getTimeInMillis() - time.getTime())/3600000);
			if(hour == 0)
				ftime = Math.max((cal.getTimeInMillis() - time.getTime()) / 60000,1)+"鍒嗛挓鍓�";
			else 
				ftime = hour+"灏忔椂鍓�";
			return ftime;
		}
		
		long lt = time.getTime()/86400000;
		long ct = cal.getTimeInMillis()/86400000;
		int days = (int)(ct - lt);		
		if(days == 0){
			int hour = (int)((cal.getTimeInMillis() - time.getTime())/3600000);
			if(hour == 0)
				ftime = Math.max((cal.getTimeInMillis() - time.getTime()) / 60000,1)+"鍒嗛挓鍓�";
			else 
				ftime = hour+"灏忔椂鍓�";
		}
		else if(days == 1){
			ftime = "鏄ㄥぉ";
		}
		else if(days == 2){
			ftime = "鍓嶅ぉ";
		}
		else if(days > 2 && days <= 10){ 
			ftime = days+"澶╁墠";			
		}
		else if(days > 10){			
			ftime = dateFormater2.get().format(time);
		}
		return ftime;
	}
	
	/**
	 * 鍒ゆ柇缁欏畾瀛楃涓叉椂闂存槸鍚︿负浠婃棩
	 * @param sdate
	 * @return boolean
	 */
	public static boolean isToday(String sdate){
		boolean b = false;
		Date time = toDate(sdate);
		Date today = new Date();
		if(time != null){
			String nowDate = dateFormater2.get().format(today);
			String timeDate = dateFormater2.get().format(time);
			if(nowDate.equals(timeDate)){
				b = true;
			}
		}
		return b;
	}
	
	/**
	 * 鍒ゆ柇缁欏畾瀛楃涓叉槸鍚︾┖鐧戒覆銆�
	 * 绌虹櫧涓叉槸鎸囩敱绌烘牸銆佸埗琛ㄧ銆佸洖杞︾銆佹崲琛岀缁勬垚鐨勫瓧绗︿覆
	 * 鑻ヨ緭鍏ュ瓧绗︿覆涓簄ull鎴栫┖瀛楃涓诧紝杩斿洖true
	 * @param input
	 * @return boolean
	 */
	public static boolean isEmpty( String input ) 
	{
		if ( input == null || "".equals( input ) )
			return true;
		
		for ( int i = 0; i < input.length(); i++ ) 
		{
			char c = input.charAt( i );
			if ( c != ' ' && c != '\t' && c != '\r' && c != '\n' )
			{
				return false;
			}
		}
		return true;
	}

	/**
	 * 鍒ゆ柇鏄笉鏄竴涓悎娉曠殑鐢靛瓙閭欢鍦板潃
	 * @param email
	 * @return
	 */
	public static boolean isEmail(String email){
		if(email == null || email.trim().length()==0) 
			return false;
	    return emailer.matcher(email).matches();
	}
	/**
	 * 瀛楃涓茶浆鏁存暟
	 * @param str
	 * @param defValue
	 * @return
	 */
	public static int toInt(String str, int defValue) {
		try{
			return Integer.parseInt(str);
		}catch(Exception e){}
		return defValue;
	}
	/**
	 * 瀵硅薄杞暣鏁�
	 * @param obj
	 * @return 杞崲寮傚父杩斿洖 0
	 */
	public static int toInt(Object obj) {
		if(obj==null) return 0;
		return toInt(obj.toString(),0);
	}
	/**
	 * 瀵硅薄杞暣鏁�
	 * @param obj
	 * @return 杞崲寮傚父杩斿洖 0
	 */
	public static long toLong(String obj) {
		try{
			return Long.parseLong(obj);
		}catch(Exception e){}
		return 0;
	}
	/**
	 * 瀛楃涓茶浆甯冨皵鍊�
	 * @param b
	 * @return 杞崲寮傚父杩斿洖 false
	 */
	public static boolean toBool(String b) {
		try{
			return Boolean.parseBoolean(b);
		}catch(Exception e){}
		return false;
	}
	
	
	public static String left(String str, int length){
			try{
			if(null == str)
				return null;
			int len = str.getBytes("utf-8").length;
			if(len <= length)
				return str;
			return new String(str.getBytes("utf-8"), 0, length);
		} catch(Exception e){
//			e.printStackTrace();
			log.error("left."+Thread.currentThread().getStackTrace()[1].getMethodName()+"> error:寮傚父", e);
			return str;
		}
	}
	
	public static String transferLike(String s){
		if(null == s)
			return null;
		return "%" + s.replace("%", "\\%") + "%";
	}
	
	//add by tianjq
	public static String transferLike_(String s){
		if(null == s)
			return null;
		return s.replace("%", "\\%") + "%";
	}
	
	public static Date transferDate(String format, String s){
		DateFormat formatter = new SimpleDateFormat(format);
		try{
			Date result = formatter.parse(s);
			return result;
		}catch(Exception e){//e.printStackTrace();
		log.error("transferDate."+Thread.currentThread().getStackTrace()[1].getMethodName()+"> error:寮傚父", e);}
		return null;
	}
	
	public static String getExceptionStackTrace(Exception ex){
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		ex.printStackTrace(pw);
		return sw.toString();
	}
	
	public static String getFormatDate(){
		long c = System.currentTimeMillis();
		SimpleDateFormat format1 = new SimpleDateFormat();
		format1.applyPattern("yyyyMMdd");
		String date = format1.format(c);
		return date;
	}
	
	public static String getOrderCode(Long id){
		String id_=id.toString();
		if(id_.length()==1)
		{
			id_="000"+id_;
		}else if(id_.length()==2)
		{
			id_="00"+id_;
		}
		else if(id_.length()==3)
		{
			id_="10"+id_;
		}
		long c = System.currentTimeMillis();
		SimpleDateFormat format1 = new SimpleDateFormat();
		format1.applyPattern("yyMMdd");
		String date = format1.format(c);
		return "T"+date+id_;
	}
	
	public static Date getEndTime(Date date,Long hours){
		 SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		 return new Date((date.getTime()+hours * 60 * 60 * 1000));		
	}
	
	public static String getfmtDate(Date date){		
		SimpleDateFormat sdf=new SimpleDateFormat("yyMMdd");  		  
		String str=sdf.format(date);
		return str;
	}
	
	public static String getOrderCode_(Date date, Long id){
		String id_=id.toString();
		if(id_.length()==1)
		{
			id_="000"+id_;
		}else if(id_.length()==2)
		{
			id_="00"+id_;
		}
		else if(id_.length()==3)
		{
			id_="10"+id_;
		}
		SimpleDateFormat sdf=new SimpleDateFormat("yyMMdd");  
		//java.util.Date date=new java.util.Date();  
		String str=sdf.format(date);
		return "T"+str+id_;
	}
	
	public static File createFilePath(String path,String fileName){

		File f = new File(path);
		if(!f.exists()){
		  f.mkdirs();
		} 

		File file = new File(f,fileName);
		if(!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
				log.error("createFilePath."+Thread.currentThread().getStackTrace()[1].getMethodName()+"> error:寮傚父", e);
			}
		}
		return file;
		
	}

	public static String join(String[] array, String join, String delimiter){
		StringBuffer result = new StringBuffer();
		for(String item : array){
			result.append(delimiter).append(item).append(delimiter).append(join);
		}
		if(array.length>0)
			result.setLength(result.length() - join.length());
		return result.toString();
	}
	
	public static String getCityCode(String tel){
		
		return tel.substring(0,tel.length()-8);
				 
	}
	
	public static boolean isFuture(String dateStr)
    {
		boolean result =false;
		
		if(Long.parseLong(dateStr.replace("-", ""))> Long.parseLong(fmtDate(new Date()).replace("-", "")))
		{
			result = true;
		}
		return result;
    }
	
	public static String getRandomNum(int length) {  
        if (length <= 0) {  
            length = 1;  
        }  
        StringBuilder res = new StringBuilder();  
        Random random = new Random();  
        int i = 0;  
        while (i < length) {  
            res.append(random.nextInt(10));  
            i++;  
        }  
        return res.toString();  
    }
	
	public static boolean isTelNo(String str){
		
		if(str.length()==8)
		{
			for (int i = str.length();--i>=0;){   
				if (!Character.isDigit(str.charAt(i)))
				{
				    return false;
				}
			}
			return true;
		}else
		{
			return false;
		}
		  
	}
}
