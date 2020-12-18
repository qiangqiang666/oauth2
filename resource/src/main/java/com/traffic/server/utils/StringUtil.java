package com.traffic.server.utils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil extends org.springframework.util.StringUtils {

	public static String lineCode = "yj_kk";

	public static final String DEFAULT_QUERY_REGEX = "[`!$^&*+=|{}';'\",<>/?~！#￥%……&*——|{}【】()‘；：”“'。，、？]";

	// 回车
	public static final String DEFAULT_N="\n";
	// tab 缩进
	public static final String DEFAULT_T="\t";
	// 换行
	public static final String DEFAULT_R="\r";
	// 换成黑点
	public static final String DEFAULT_B="\b";

	/**
	 * Check whether the given {@code CharSequence} contains actual
	 * <em>text</em>.
	 * <p>
	 * More specifically, this method returns {@code true} if the
	 * {@code CharSequence} is not {@code null}, its length is greater than 0,
	 * and it contains at least one non-whitespace character.
	 * <p>
	 * 
	 * <pre class="getCode">
	 * StringUtils.isBlank(null) = true
	 * StringUtils.isBlank("") = true
	 * StringUtils.isBlank(" ") = true
	 * StringUtils.isBlank("12345") = false
	 * StringUtils.isBlank(" 12345 ") = false
	 * </pre>
	 *
	 * @param cs
	 *            the {@code CharSequence} to check (may be {@code null})
	 * @return {@code true} if the {@code CharSequence} is not {@code null}, its
	 *         length is greater than 0, and it does not contain whitespace only
	 * @see Character#isWhitespace
	 */
	public static boolean isBlank(final CharSequence cs) {
		return !StringUtil.isNotBlank(cs);
	}

	/**
	 * <p>
	 * Checks if a CharSequence is not empty (""), not null and not whitespace
	 * only.
	 * </p>
	 * <p>
	 * 
	 * <pre>
	 * StringUtils.isNotBlank(null)      = false
	 * StringUtils.isNotBlank("")        = false
	 * StringUtils.isNotBlank(" ")       = false
	 * StringUtils.isNotBlank("bob")     = true
	 * StringUtils.isNotBlank("  bob  ") = true
	 * </pre>
	 *
	 * @param cs
	 *            the CharSequence to check, may be null
	 * @return {@code true} if the CharSequence is not empty and not null and
	 *         not whitespace
	 * @see Character#isWhitespace
	 */
	public static boolean isNotBlank(final CharSequence cs) {
		return StringUtil.hasText(cs);
	}

	/**
	 * Convert a {@code Collection} into a delimited {@code String} (e.g. CSV).
	 * <p>
	 * Useful for {@code toString()} implementations.
	 *
	 * @param coll
	 *            the {@code Collection} to convert
	 * @param delim
	 *            the delimiter to use (typically a ",")
	 * @return the delimited {@code String}
	 */
	public static String join(Collection<?> coll, String delim) {
		return StringUtil.collectionToDelimitedString(coll, delim);
	}

	/**
	 * Convert a {@code String} array into a delimited {@code String} (e.g.
	 * CSV).
	 * <p>
	 * Useful for {@code toString()} implementations.
	 *
	 * @param arr
	 *            the array to display
	 * @param delim
	 *            the delimiter to use (typically a ",")
	 * @return the delimited {@code String}
	 */
	public static String join(Object[] arr, String delim) {
		return StringUtil.arrayToDelimitedString(arr, delim);
	}

	/**
	 * 生成uuid
	 *
	 * @return UUID
	 */
	public static String getUUId() {
		return UUID.randomUUID().toString();
	}

	public static String get8NUmber() {
		String number = "";
		for (int i = 0; i < 8; i++) {
			number += (((int) (1 + Math.random() * 10)));
		}
		return number;
	}

	/**
	 * 32位MD5加密
	 *
	 * @param str
	 * @return
	 */
	public static String getMD5Str(String str) {
		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(str.getBytes(StandardCharsets.UTF_8));
		} catch (NoSuchAlgorithmException e) {
			//System.out.println("NoSuchAlgorithmException caught!");
			System.exit(-1);
		}

        byte[] byteArray = messageDigest.digest();

		StringBuffer md5StrBuff = new StringBuffer();

		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
				md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
			else
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
		}
		return md5StrBuff.toString();
	}

	/*
	 * 获取IP
	 */
	public static String getIp(HttpServletRequest request) {
		String ipAddress = null;
		// ipAddress = this.getRequest().getRemoteAddr();
		ipAddress = request.getHeader("x-forwarded-for");
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getRemoteAddr();
			if (ipAddress.equals("127.0.0.1")) {
				// 根据网卡取本机配置的IP
				InetAddress inet = null;
				try {
					inet = InetAddress.getLocalHost();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
				ipAddress = inet.getHostAddress();
			}

		}
		// 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
		if (ipAddress != null && ipAddress.length() > 15) {
			// "***.***.***.***".length() = 15
			if (ipAddress.indexOf(",") > 0) {
				ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
			}
		}
		return ipAddress;
	}

/*	public static void main(String[] args) throws UnsupportedEncodingException, NoSuchAlgorithmException {
		// 收到用户充值通知
		String str = "【星河娱乐】玩家[userName]发起了一笔提现申请，金额：amount元，订单编号：tradeId。".replaceAll("userName", "拉拉");
		str = str.replaceAll("amount", "1000");
		str = str.replaceAll("tradeId", "123456789");
		System.out.println(str);
	}*/

	/**
	 * 空值检查
	 *
	 * @param str
	 * @return
	 */
	public static boolean isNullStr(String str) {
		boolean flag = false;
		if (str == null || "".equals(str.trim()) || "null".equalsIgnoreCase(str) || "undefined".equalsIgnoreCase(str)) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 
	 * @Title: hidePhone
	 * @Description: 隐藏手机号
	 * @param phone
	 *            手机号
	 * @return
	 * @return String 152****4799 $1、$2、……表示正则表达式里面第一个、第二个、……括号里面的匹配内容
	 */
	public static String hidePhone(String phone) {
		if (StringUtil.isNotBlank(phone)) {
			if (11 == phone.length()) {
				return phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
			}
		}
		return phone;
	}

	/**
	 * 
	 * @Title: hideIdentity
	 * @Description:  隐藏身份证号
	 * @param identity
	 *            身份证号码
	 * @return
	 * @return String 4304*****7733 $1、$2、……表示正则表达式里面第一个、第二个、……括号里面的匹配内容
	 */
	public static String hideIdentity(String identity) {
		return identity.replaceAll("(\\d{4})\\d{10}(\\w{4})", "$1*****$2");
	}

	/**
	 * 
	 * @Title: toChinese
	 * @Description:  阿拉伯数字转成小写汉字
	 * @param string
	 *            数字(不允许小数) 123
	 * @return
	 * @return String 一百二十三
	 */
	public static String toChinese(String string) {
		String[] s1 = { "零", "一", "二", "三", "四", "五", "六", "七", "八", "九" };
		String[] s2 = { "十", "百", "千", "万", "十", "百", "千", "亿", "十", "百", "千" };

		String result = "";

		int n = string.length();
		for (int i = 0; i < n; i++) {

			int num = string.charAt(i) - '0';

			if (i != n - 1 && num != 0) {
				result += s1[num] + s2[n - 2 - i];
			} else {
				result += s1[num];
			}
		}

		return result;

	}

	/**
	 * 判断字符串中是否包含中文
	 * 
	 * @param str
	 *            待校验字符串
	 * @return 是否为中文
	 * @warn 不能校验是否为中文标点符号
	 */
	public static boolean isContainChinese(String str) {
		Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
		Matcher m = p.matcher(str);
        return m.find();
    }
	

    /**
     * 
     * @Title: ifApiVersion 
     * @Description:  将版本号格式为数字
     * @param str 版本号(例如:1.0.1 或者 1.0)
     * @return
     * @return int 版本号数字 (例如: 101 或者 100)
     */
    public static int formatApiVersionToNumber(String str){
    	String contains = ".";
    	String split = "\\.";		
    	if (StringUtil.isBlank(str) || !str.contains(contains)) {
    		return 0;
		}
    	if (str.contains(contains)) {
			String[] strs = str.split(split);
			if (strs.length == 2) {
				return Integer.parseInt((strs[0]+strs[1]+"0"));
			}
			if (strs.length == 3) {
				return Integer.parseInt((strs[0]+strs[1]+strs[2]));
			}
		}
    	return 0;
    	
    }
    /**
     * 使用java正则表达式去掉多余的.与0
     * @param s
     * @return 
     */
    public static String subZeroAndDot(String s){
        if(s.indexOf(".") > 0){
            s = s.replaceAll("0+?$", "");//去掉多余的0
            s = s.replaceAll("[.]$", "");//如最后一位是.则去掉
        }
        return s;
    }
    /**
     * 使用java正则表达式: 数字开头,数字结尾,中间只允许数字和英文逗号出现
     * @param str
     * @return
     */
    public static boolean regexCheckStr(String str){
    	if (StringUtil.isNullStr(str)){
    		return false;
		}
		if (!str.contains(",")){
			// 单个的话,如果是纯数字
            return str.matches("\\d+");
		}
		String regex = "^(\\d+,)+\\d+$";
		return str.matches(regex);
    }

	/**
	 * 获取字符串的长度，如果有中文，则每个中文字符计为2位
	 *
	 * @param value 指定的字符串
	 * @return 字符串的长度
	 */
	public static int getLength(String value) {
		int valueLength = 0;
		String chinese = "[\u0391-\uFFE5]";
		/* 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1 */
		for (int i = 0; i < value.length(); i++) {
			/* 获取一个字符 */
			String temp = value.substring(i, i + 1);
			/* 判断是否为中文字符 */
			if (temp.matches(chinese)) {
				/* 中文字符长度为2 */
				valueLength += 2;
			} else {
				/* 其他字符长度为1 */
				valueLength += 1;
			}
		}
		return valueLength;

	}

	/**
	 * 获取url中的除协议和域名之后的字符串
	 * @param headUrl url
	 * @return
	 */
	public static String subHead(String headUrl){
        Pattern p = Pattern.compile("(?<=//|)((\\w)+\\.)+\\w+");

        Matcher m = p.matcher(headUrl);
        if(m.find()){
            String str = m.group();
            return headUrl.substring(headUrl.lastIndexOf(str) + str.length());
        }
        return headUrl;
    }
	/**
	 * 判断是否含有特殊字符
	 *
	 * @param str
	 * @return true为包含，false为不包含
	 */
	public static boolean isSpecialChar(String str) {
		String regEx = "[ _`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\n|\r|\t|\b";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.find();
	}

	/**
	 * 判断查询参数中是否以特殊字符开头，如果以特殊字符开头则返回true，否则返回false
	 *
	 * @param value
	 * @return
	 */
	public static boolean specialSymbols(String value) {
		if (StringUtil.isBlank(value)) {
            return DEFAULT_N.equalsIgnoreCase(value) || DEFAULT_T.equalsIgnoreCase(value)
                    || DEFAULT_R.equalsIgnoreCase(value) || DEFAULT_B.equalsIgnoreCase(value);
        }
		Pattern pattern = Pattern.compile(getQueryRegex());
		Matcher matcher = pattern.matcher(value);

		char[] specialSymbols = getQueryRegex().toCharArray();

		boolean isStartWithSpecialSymbol = false; // 是否以特殊字符开头
		for (int i = 0; i < specialSymbols.length; i++) {
			char c = specialSymbols[i];
			if (value.indexOf(c) == 0) {
				isStartWithSpecialSymbol = true;
				break;
			}
		}
		if (value.contains(DEFAULT_N) || value.contains(DEFAULT_T) || value.contains(DEFAULT_R) || value.contains(DEFAULT_B)){
			return true;
		}
		return matcher.find() && isStartWithSpecialSymbol;
	}


	/**
	 * 获取查询过滤的非法字符
	 *
	 * @return
	 */
	protected static String getQueryRegex() {
		return DEFAULT_QUERY_REGEX;
	}
	
	/**
	 * 判断是否为正整数
	 * @param string
	 * @return
	 */
	public static boolean isNumeric(String string) {
	    Pattern pattern = Pattern.compile("[0-9]*");
	    return pattern.matcher(string).matches();
	}

    public static void main(String[] args) {
    	//System.out.println(formatApiVersionToNumber("1,3,4",",",","));
    	//System.out.println(formatApiVersionToNumber("1,3",",",","));
		//System.out.println(subHead("www.baidu/123.jpg"));
		System.out.println(isSpecialChar("111"));
    }
}
