package spqbs.top.music.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils extends org.apache.commons.lang3.StringUtils
{
  public static boolean isBlank(String[] strs)
  {
    if ((strs == null) || (strs.length == 0)) {
      return true;
    }
    for (String string : strs) {
      if (org.apache.commons.lang3.StringUtils.isBlank(string)) {
        return true;
      }
    }
    return false;
  }

  public static boolean allIsBlank(String[] strs)
  {
    if ((strs == null) || (strs.length == 0)) {
      return true;
    }
    for (String string : strs) {
      if (org.apache.commons.lang3.StringUtils.isNotBlank(string)) {
        return false;
      }
    }
    return true;
  }

  public static boolean isEmpty(String[] strs)
  {
    if ((strs == null) || (strs.length < 0)) {
      return true;
    }
    for (String string : strs) {
      if (org.apache.commons.lang3.StringUtils.isEmpty(string)) {
        return true;
      }
    }
    return false;
  }

  public static String replaceAll(String sourceStr, Map<String, String> context)
  {
    if (isBlank(sourceStr)) {
      return sourceStr;
    }
    Pattern p = Pattern.compile("\\$\\{\\w+\\}");
    Matcher matcher = p.matcher(sourceStr);
    StringBuffer sb = new StringBuffer();
    while (matcher.find()) {
      String temp = matcher.group();
      String key = temp.substring(2, temp.length() - 1);
      String value = (String)context.get(key);
      if (value != null) {
        matcher.appendReplacement(sb, value);
      }
    }
    matcher.appendTail(sb);
    return sb.toString();
  }

  public static String replaceAllObject(String sourceStr, Map<String, Object> context)
  {
    if (isBlank(sourceStr)) {
      return sourceStr;
    }
    Pattern p = Pattern.compile("\\$\\{\\w+\\}");
    Matcher matcher = p.matcher(sourceStr);
    StringBuffer sb = new StringBuffer();
    while (matcher.find()) {
      String temp = matcher.group();
      String key = temp.substring(2, temp.length() - 1);
      Object value = context.get(key);
      if (value != null) {
        matcher.appendReplacement(sb, value.toString());
      }
    }
    matcher.appendTail(sb);
    return sb.toString();
  }

  public static String[] split(String str, String split)
  {
    if (isBlank(str)) {
      return new String[0];
    }
    return org.apache.commons.lang3.StringUtils.split(str, split);
  }

  public static String unionAsFilePath(String[] paths)
  {
    if ((paths == null) || (paths.length == 0)) {
      return "";
    }
    StringBuffer sb = new StringBuffer();

    for (int i = 0; i < paths.length; i++) {
      String path = paths[i];
      if (!isBlank(path))
      {
        if ((i != 0) && (!path.startsWith("/"))) {
          path = "/" + path;
        }
        if (path.endsWith("/")) {
          path = path.substring(0, path.length() - 1);
        }
        sb.append(path);
      }
    }
    return sb.toString();
  }

  public static String upcaseFirstChar(String str)
  {
    if (isBlank(str)) {
      return str;
    }
    return str.substring(0, 1).toUpperCase() + str.substring(1);
  }

  public static String getJavaName(String columnName)
  {
    Pattern p = Pattern.compile("_\\w");
    Matcher matcher = p.matcher(columnName.toLowerCase());
    StringBuffer sb = new StringBuffer();
    while (matcher.find()) {
      String group = matcher.group();
      matcher.appendReplacement(sb, group.substring(1).toUpperCase());
    }
    matcher.appendTail(sb);
    return sb.toString();
  }

  public static String trimAll(String source)
  {
    if (source == null) {
      return null;
    }
    if ("".equals(source.trim())) {
      return "";
    }

    return source.trim().replaceAll("\\s+", "").replaceAll(" ", "");
  }

  public static String replaceUxxx(String source)
  {
    if (source == null) {
      return null;
    }
    if ("".equals(source.trim())) {
      return "";
    }
    return source.replaceAll("\024", "").replaceAll("\027", "");
  }

  public static Map<String, Object> getUrlParams(String param)
  {
    Map map = new HashMap(0);
    if (isBlank(param)) {
      return map;
    }
    String[] params = param.split("&");
    for (int i = 0; i < params.length; i++) {
      String[] p = params[i].split("=");
      if (p.length == 2) {
        map.put(p[0], p[1]);
      }
    }
    return map;
  }

  public static String getUrlParamsByMap(Map<String, Object> map)
  {
    if (map == null) {
      return "";
    }
    StringBuffer sb = new StringBuffer();
    for (Map.Entry entry : map.entrySet()) {
      Object value = entry.getValue();
      if ((value != null) && (
        (!(value instanceof String)) || (!isBlank((String)value)))) {
        sb.append((String)entry.getKey() + "=" + String.valueOf(entry.getValue()));
        sb.append("&");
      }
    }

    String s = sb.toString();
    if (s.endsWith("&")) {
      s = substringBeforeLast(s, "&");
    }
    return s;
  }

  public static String unionAsRegx(List<String> strList)
  {
    return unionAsRegxWithChar(strList, '#');
  }

  public static String unionAsRegxWithChar(List<String> strList, char seq)
  {
    if ((strList == null) || (strList.isEmpty())) {
      return "" + seq + seq;
    }
    StringBuffer sb = new StringBuffer();
    for (String str : strList) {
      if (isNotBlank(str)) {
        sb.append(seq).append(str).append(seq).append("|");
      }
    }
    String string = sb.toString();
    if ((isNotBlank(string)) && (string.endsWith("|"))) {
      string = string.substring(0, string.length() - 1);
    }
    return string;
  }

  public static String joinFirstEnd(List<? extends Object> strList, char seq)
  {
    if (strList == null) {
      return null;
    }
    return seq + join(strList, seq) + seq;
  }

  public static String joinFirstEnd(List<? extends Object> strList)
  {
    return joinFirstEnd(strList, '#');
  }

  public static String getRandomStr()
  {
    return getRandomStr(16);
  }

  public static String getRandomStr(int num)
  {
    if (num <= 0) {
      num = 16;
    }
    String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    Random random = new Random();
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < num; i++) {
      int number = random.nextInt(base.length());
      sb.append(base.charAt(number));
    }
    return sb.toString();
  }

  public static List<Long> parseStringListToLong(List<String> strList)
  {
    if (strList == null) {
      return null;
    }
    List longList = new ArrayList();
    for (String string : strList) {
      if (!isBlank(string)) {
        longList.add(Long.valueOf(Long.parseLong(string)));
      }
    }
    return longList;
  }

  public static List<Integer> parseStringListToInteger(List<String> strList)
  {
    if (strList == null) {
      return null;
    }
    List intList = new ArrayList();
    for (String string : strList) {
      if (!isBlank(string)) {
        intList.add(Integer.valueOf(Integer.parseInt(string)));
      }
    }
    return intList;
  }

  public static int chineselength(String s)
  {
    if (s == null)
      return 0;
    char[] c = s.toCharArray();
    int len = 0;
    for (int i = 0; i < c.length; i++) {
      len++;
      if (!isLetter(c[i])) {
        len++;
      }
    }
    return len;
  }

  public static boolean isLetter(char c)
  {
    int k = 128;
    return c / k == 0;
  }
}