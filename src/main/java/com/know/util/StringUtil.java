//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.know.util;

import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.BreakIterator;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
    private static final char[] QUOTE_ENCODE = "&quot;".toCharArray();
    private static final char[] AMP_ENCODE = "&amp;".toCharArray();
    private static final char[] LT_ENCODE = "&lt;".toCharArray();
    private static final char[] GT_ENCODE = "&gt;".toCharArray();
    private static MessageDigest digest = null;
    private static Random randGen = new Random();
    private static char[] numbersAndLetters = "0123456789abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    private static final char[] zeroArray = "0000000000000000000000000000000000000000000000000000000000000000".toCharArray();
    private static StringUtil instance;

    public StringUtil() {
    }

    public static synchronized StringUtil getInstance() {
        if (instance == null) {
            instance = new StringUtil();
        }

        return instance;
    }

    public Enumeration<String> split(String source, String sign) {
        Vector<String> ar = new Vector();
        String st = source;

        while(true) {
            int ipos = st.indexOf(sign);
            if (ipos < 0) {
                ar.addElement(st.substring(0));
                return ar.elements();
            }

            ar.addElement(st.substring(0, ipos));
            st = st.substring(ipos + 1);
        }
    }

    public static boolean inStr(String source, String desc) {
        return source.indexOf(desc) >= 0;
    }

    public static String decodeGB(String source, String charset) {
        try {
            return new String(source.getBytes(charset), "GB2312");
        } catch (Exception var3) {
            return source;
        }
    }

    public static String decodeGB(String source) {
        try {
            return new String(source.getBytes("ISO8859-1"), "GBK");
        } catch (Exception var2) {
            return source;
        }
    }

    public static String encodeGB(String source, String charset) {
        try {
            return new String(source.getBytes("GB2312"), charset);
        } catch (Exception var3) {
            return source;
        }
    }

    public static String encodeGB(String source) {
        try {
            return new String(source.getBytes("GBK"), "ISO8859-1");
        } catch (Exception var2) {
            return source;
        }
    }

    public static String replace(String source, char str1, String str2) {
        if (source == null) {
            return source;
        } else {
            String desc = "";

            for(int i = 0; i < source.length(); ++i) {
                if (source.charAt(i) == str1) {
                    desc = desc + str2;
                } else {
                    desc = desc + String.valueOf(source.charAt(i));
                }
            }

            return desc;
        }
    }

    public static String replace(String source, String str1, String str2) {
        if (source == null) {
            return source;
        } else {
            String desc = "";
            int i = 0;

            while(i < source.length()) {
                if (source.startsWith(str1, i)) {
                    desc = desc + str2;
                    i += str1.length();
                } else {
                    desc = desc + String.valueOf(source.charAt(i));
                    ++i;
                }
            }

            return desc;
        }
    }

    public static boolean isNullStr(String s) {
        return s == null || s.trim().length() <= 0;
    }

    public static String killNull(Object inObj) {
        return inObj == null ? "" : inObj.toString().trim();
    }

    public static String killNull(Object inObj, String toStr) {
        return inObj == null ? toStr : inObj.toString().trim();
    }

    public static String gbToIso(String gStr) {
        try {
            return gStr == null ? null : new String(gStr.getBytes("GB2312"), "ISO8859-1");
        } catch (UnsupportedEncodingException var2) {
            return gStr;
        }
    }

    public static String isoToGb(String iStr) {
        try {
            return iStr == null ? null : new String(iStr.getBytes("ISO8859-1"), "GB2312");
        } catch (UnsupportedEncodingException var2) {
            return iStr;
        }
    }

    public static Vector<String> stringToVecor(String sourceString, String strInterval) {
        Vector<String> returnStr = new Vector();
        if (sourceString != "" && strInterval != "") {
            int found_str;
            for(found_str = sourceString.indexOf(strInterval); found_str >= 0; found_str = sourceString.indexOf(strInterval)) {
                returnStr.addElement(sourceString.substring(0, found_str));
                sourceString = sourceString.substring(found_str + strInterval.length());
            }

            if (found_str < 0) {
                returnStr.addElement(sourceString);
            }

            return returnStr;
        } else {
            returnStr.addElement(sourceString);
            return returnStr;
        }
    }

    public static String encodeString(String str) {
        return new String(Base64.encodeBase64(str.getBytes()));
    }

    public static String decodeString(String str) {
        return new String(Base64.decodeBase64(str));
    }

    public static final String stripTags(String in) {
        if (in == null) {
            return null;
        } else {
            int i = 0;
            int last = 0;
            char[] input = in.toCharArray();
            int len = input.length;

            StringBuffer out;
            for(out = new StringBuffer((int)((double)len * 1.3D)); i < len; ++i) {
                char ch = input[i];
                if (ch <= '>') {
                    if (ch == '<') {
                        if (i + 3 < len && input[i + 1] == 'b' && input[i + 2] == 'r' && input[i + 3] == '>') {
                            i += 3;
                        } else {
                            if (i > last) {
                                out.append(input, last, i - last);
                            }

                            last = i + 1;
                        }
                    } else if (ch == '>') {
                        last = i + 1;
                    }
                }
            }

            if (last == 0) {
                return in;
            } else {
                if (i > last) {
                    out.append(input, last, i - last);
                }

                return out.toString();
            }
        }
    }

    public static final String escapeHTMLTags(String in) {
        if (in == null) {
            return null;
        } else {
            int i = 0;
            int last = 0;
            char[] input = in.toCharArray();
            int len = input.length;

            StringBuffer out;
            for(out = new StringBuffer((int)((double)len * 1.3D)); i < len; ++i) {
                char ch = input[i];
                if (ch <= '>') {
                    if (ch == '<') {
                        if (i > last) {
                            out.append(input, last, i - last);
                        }

                        last = i + 1;
                        out.append(LT_ENCODE);
                    } else if (ch == '>') {
                        if (i > last) {
                            out.append(input, last, i - last);
                        }

                        last = i + 1;
                        out.append(GT_ENCODE);
                    }
                }
            }

            if (last == 0) {
                return in;
            } else {
                if (i > last) {
                    out.append(input, last, i - last);
                }

                return out.toString();
            }
        }
    }

    public static final synchronized String hash(String data) {
        if (digest == null) {
            try {
                digest = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException var3) {
            }
        }

        try {
            digest.update(data.getBytes("utf-8"));
        } catch (UnsupportedEncodingException var2) {
        }

        return encodeHex(digest.digest());
    }

    public static final String encodeHex(byte[] bytes) {
        StringBuffer buf = new StringBuffer(bytes.length * 2);

        for(int i = 0; i < bytes.length; ++i) {
            if ((bytes[i] & 255) < 16) {
                buf.append("0");
            }

            buf.append(Long.toString((long)(bytes[i] & 255), 16));
        }

        return buf.toString();
    }


    private static final byte hexCharToByte(char ch) {
        switch(ch) {
            case '0':
                return 0;
            case '1':
                return 1;
            case '2':
                return 2;
            case '3':
                return 3;
            case '4':
                return 4;
            case '5':
                return 5;
            case '6':
                return 6;
            case '7':
                return 7;
            case '8':
                return 8;
            case '9':
                return 9;
            case ':':
            case ';':
            case '<':
            case '=':
            case '>':
            case '?':
            case '@':
            case 'A':
            case 'B':
            case 'C':
            case 'D':
            case 'E':
            case 'F':
            case 'G':
            case 'H':
            case 'I':
            case 'J':
            case 'K':
            case 'L':
            case 'M':
            case 'N':
            case 'O':
            case 'P':
            case 'Q':
            case 'R':
            case 'S':
            case 'T':
            case 'U':
            case 'V':
            case 'W':
            case 'X':
            case 'Y':
            case 'Z':
            case '[':
            case '\\':
            case ']':
            case '^':
            case '_':
            case '`':
            default:
                return 0;
            case 'a':
                return 10;
            case 'b':
                return 11;
            case 'c':
                return 12;
            case 'd':
                return 13;
            case 'e':
                return 14;
            case 'f':
                return 15;
        }
    }

    public static String encodeBase64(String data) {
        byte[] bytes = (byte[])null;

        try {
            bytes = data.getBytes("ISO-8859-1");
        } catch (UnsupportedEncodingException var3) {
        }

        return encodeBase64(bytes);
    }

    public static String encodeBase64(byte[] data) {
        int len = data.length;
        StringBuffer ret = new StringBuffer((len / 3 + 1) * 4);

        for(int i = 0; i < len; ++i) {
            int c = data[i] >> 2 & 63;
            ret.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".charAt(c));
            c = data[i] << 4 & 63;
            ++i;
            if (i < len) {
                c |= data[i] >> 4 & 15;
            }

            ret.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".charAt(c));
            if (i < len) {
                c = data[i] << 2 & 63;
                ++i;
                if (i < len) {
                    c |= data[i] >> 6 & 3;
                }

                ret.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".charAt(c));
            } else {
                ++i;
                ret.append('=');
            }

            if (i < len) {
                c = data[i] & 63;
                ret.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".charAt(c));
            } else {
                ret.append('=');
            }
        }

        return ret.toString();
    }

    public static String decodeBase64(String data) {
        byte[] bytes = (byte[])null;

        try {
            bytes = data.getBytes("ISO-8859-1");
        } catch (UnsupportedEncodingException var3) {
        }

        return decodeBase64(bytes);
    }

    public static String decodeBase64(byte[] data) {
        int len = data.length;
        StringBuffer ret = new StringBuffer(len * 3 / 4);

        for(int i = 0; i < len; ++i) {
            int c = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".indexOf(data[i]);
            ++i;
            int c1 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".indexOf(data[i]);
            c = c << 2 | c1 >> 4 & 3;
            ret.append((char)c);
            ++i;
            if (i < len) {
                c = data[i];
                if (61 == c) {
                    break;
                }

                c = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".indexOf(c);
                c1 = c1 << 4 & 240 | c >> 2 & 15;
                ret.append((char)c1);
            }

            ++i;
            if (i < len) {
                c1 = data[i];
                if (61 == c1) {
                    break;
                }

                c1 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".indexOf(c1);
                c = c << 6 & 192 | c1;
                ret.append((char)c);
            }
        }

        return ret.toString();
    }

    public static final String[] toLowerCaseWordArray(String text) {
        if (text != null && text.length() != 0) {
            ArrayList<String> wordList = new ArrayList();
            BreakIterator boundary = BreakIterator.getWordInstance();
            boundary.setText(text);
            int start = 0;

            for(int end = boundary.next(); end != -1; end = boundary.next()) {
                String tmp = text.substring(start, end).trim();
                tmp = replace(tmp, "+", "");
                tmp = replace(tmp, "/", "");
                tmp = replace(tmp, "\\", "");
                tmp = replace(tmp, "#", "");
                tmp = replace(tmp, "*", "");
                tmp = replace(tmp, ")", "");
                tmp = replace(tmp, "(", "");
                tmp = replace(tmp, "&", "");
                if (tmp.length() > 0) {
                    wordList.add(tmp);
                }

                start = end;
            }

            return (String[])((String[])wordList.toArray(new String[wordList.size()]));
        } else {
            return new String[0];
        }
    }

    public static final String randomString(int length) {
        if (length < 1) {
            return null;
        } else {
            char[] randBuffer = new char[length];

            for(int i = 0; i < randBuffer.length; ++i) {
                randBuffer[i] = numbersAndLetters[randGen.nextInt(71)];
            }

            return new String(randBuffer);
        }
    }

    public static final String chopAtWord(String string, int length) {
        if (string != null && string.length() != 0) {
            char[] charArray = string.toCharArray();
            int sLength = string.length();
            if (length < sLength) {
                sLength = length;
            }

            int i;
            for(i = 0; i < sLength - 1; ++i) {
                if (charArray[i] == '\r' && charArray[i + 1] == '\n') {
                    return string.substring(0, i + 1);
                }

                if (charArray[i] == '\n') {
                    return string.substring(0, i);
                }
            }

            if (charArray[sLength - 1] == '\n') {
                return string.substring(0, sLength - 1);
            } else if (string.length() <= length) {
                return string;
            } else {
                for(i = length - 1; i > 0; --i) {
                    if (charArray[i] == ' ') {
                        return string.substring(0, i).trim();
                    }
                }

                return string.substring(0, length);
            }
        } else {
            return string;
        }
    }

    public static final String chopAtWordSubstring(String string, int length) {
        if (string != null && string.length() != 0) {
            char[] charArray = string.toCharArray();
            int sLength = string.length();
            if (length < sLength) {
                sLength = length;
            }

            for(int i = 0; i < sLength - 1; ++i) {
                if (charArray[i] == '\r' && charArray[i + 1] == '\n') {
                    return string.substring(0, i + 1);
                }

                if (charArray[i] == '\n') {
                    return string.substring(0, i);
                }
            }

            if (charArray[sLength - 1] == '\n') {
                return string.substring(0, sLength - 1);
            } else if (string.length() <= length) {
                return string;
            } else {
                return string.substring(0, length) + "...";
            }
        } else {
            return string;
        }
    }

    public static String chopAtWordsAround(String input, String[] wordList, int numChars) {
        if (input != null && !"".equals(input.trim()) && wordList != null && wordList.length != 0 && numChars != 0) {
            String lc = input.toLowerCase();

            for(int i = 0; i < wordList.length; ++i) {
                int pos = lc.indexOf(wordList[i]);
                if (pos > -1) {
                    int beginIdx = pos - numChars;
                    if (beginIdx < 0) {
                        beginIdx = 0;
                    }

                    int endIdx = pos + numChars;
                    if (endIdx > input.length() - 1) {
                        endIdx = input.length() - 1;
                    }

                    char[] chars = input.toCharArray();

                    do {
                        --beginIdx;
                    } while(beginIdx > 0 && chars[beginIdx] != ' ' && chars[beginIdx] != '\n' && chars[beginIdx] != '\r');

                    while(endIdx < input.length() && chars[endIdx] != ' ' && chars[endIdx] != '\n' && chars[endIdx] != '\r') {
                        ++endIdx;
                    }

                    return input.substring(beginIdx, endIdx);
                }
            }

            return input.substring(0, input.length() >= 200 ? 200 : input.length());
        } else {
            return null;
        }
    }

    public static String wordWrap(String input, int width, Locale locale) {
        if (input == null) {
            return "";
        } else if (width < 5) {
            return input;
        } else if (width >= input.length()) {
            return input;
        } else {
            StringBuffer buf = new StringBuffer(input);
            boolean endOfLine = false;
            int lineStart = 0;

            for(int i = 0; i < buf.length(); ++i) {
                if (buf.charAt(i) == '\n') {
                    lineStart = i + 1;
                    endOfLine = true;
                }

                if (i > lineStart + width - 1) {
                    if (!endOfLine) {
                        int limit = i - lineStart - 1;
                        BreakIterator breaks = BreakIterator.getLineInstance(locale);
                        breaks.setText(buf.substring(lineStart, i));
                        int end = breaks.last();
                        if (end == limit + 1 && !Character.isWhitespace(buf.charAt(lineStart + end))) {
                            end = breaks.preceding(end - 1);
                        }

                        if (end != -1 && end == limit + 1) {
                            buf.replace(lineStart + end, lineStart + end + 1, "\n");
                            lineStart += end;
                        } else if (end != -1 && end != 0) {
                            buf.insert(lineStart + end, '\n');
                            lineStart = lineStart + end + 1;
                        } else {
                            buf.insert(i, '\n');
                            lineStart = i + 1;
                        }
                    } else {
                        buf.insert(i, '\n');
                        lineStart = i + 1;
                        endOfLine = false;
                    }
                }
            }

            return buf.toString();
        }
    }

    public static final String highlightWords(String string, String[] words, String startHighlight, String endHighlight) {
        if (string != null && words != null && startHighlight != null && endHighlight != null) {
            StringBuffer regexp = new StringBuffer();

            for(int x = 0; x < words.length; ++x) {
                regexp.append(words[x]);
                if (x != words.length - 1) {
                    regexp.append("|");
                }
            }

            regexp.insert(0, "s/\\b(");
            regexp.append(")\\b/");
            regexp.append(startHighlight);
            regexp.append("$1");
            regexp.append(endHighlight);
            regexp.append("/igm");
            return null;
        } else {
            return null;
        }
    }

    public static final String escapeForXML(String string) {
        if (string == null) {
            return null;
        } else {
            int i = 0;
            int last = 0;
            char[] input = string.toCharArray();
            int len = input.length;

            StringBuffer out;
            for(out = new StringBuffer((int)((double)len * 1.3D)); i < len; ++i) {
                char ch = input[i];
                if (ch <= '>') {
                    if (ch == '<') {
                        if (i > last) {
                            out.append(input, last, i - last);
                        }

                        last = i + 1;
                        out.append(LT_ENCODE);
                    } else if (ch == '&') {
                        if (i > last) {
                            out.append(input, last, i - last);
                        }

                        last = i + 1;
                        out.append(AMP_ENCODE);
                    } else if (ch == '"') {
                        if (i > last) {
                            out.append(input, last, i - last);
                        }

                        last = i + 1;
                        out.append(QUOTE_ENCODE);
                    } else if (ch != '\n' && ch != '\r' && ch != '\t' && ch < ' ') {
                        if (i > last) {
                            out.append(input, last, i - last);
                        }

                        last = i + 1;
                    }
                }
            }

            if (last == 0) {
                return string;
            } else {
                if (i > last) {
                    out.append(input, last, i - last);
                }

                return out.toString();
            }
        }
    }

    public static final String unescapeFromXML(String string) {
        string = replace(string, "&lt;", "<");
        string = replace(string, "&gt;", ">");
        string = replace(string, "&quot;", "\"");
        return replace(string, "&amp;", "&");
    }

    public static final String zeroPadString(String string, int length) {
        if (string != null && string.length() <= length) {
            StringBuffer buf = new StringBuffer(length);
            buf.append(zeroArray, 0, length - string.length()).append(string);
            return buf.toString();
        } else {
            return string;
        }
    }

    public static final String dateToMillis(Date date) {
        return zeroPadString(Long.toString(date.getTime()), 15);
    }

    /** @deprecated */
    public static String getMimeType(String strExt) {
        strExt = strExt.toLowerCase();
        String strMimeType = "application/octet-stream";
        if (!"htm".equals(strExt) && !"html".equals(strExt)) {
            if ("txt".equals(strExt)) {
                strMimeType = "text/plain";
            } else if ("rtf ".equals(strExt)) {
                strMimeType = "application/rtf";
            } else if ("pdf".equals(strExt)) {
                strMimeType = "application/pdf";
            } else if ("doc".equals(strExt)) {
                strMimeType = "application/msword";
            } else if (!"ppt".equals(strExt) && !"ppz".equals(strExt) && !"pps".equals(strExt) && !"pot".equals(strExt)) {
                if ("gtar".equals(strExt)) {
                    strMimeType = "application/x-gtar";
                } else if ("tar".equals(strExt)) {
                    strMimeType = "application/x-tar";
                } else if ("zip".equals(strExt)) {
                    strMimeType = "application/zip";
                } else if ("gif".equals(strExt)) {
                    strMimeType = "application/rtf";
                } else if ("png".equals(strExt)) {
                    strMimeType = "application/rtf";
                } else if (!"jpeg".equals(strExt) && !"jpg".equals(strExt) && !"jpe".equals(strExt)) {
                    if ("tiff".equals(strExt) || "tif".equals(strExt)) {
                        strMimeType = "application/tiff";
                    }
                } else {
                    strMimeType = "image/jpeg";
                }
            } else {
                strMimeType = "application/mspowerpoint ";
            }
        } else {
            strMimeType = "text/html";
        }

        return strMimeType;
    }

    public static List<String> stringToList(String str, String split) {
        List<String> r = new ArrayList();
        if (str != null && !str.trim().equals("")) {
            str = leftTrim(str, split);
            int pre = 0;

            String left;
            for(int index = str.indexOf(split, pre); index >= 0; index = str.indexOf(split, pre)) {
                left = str.substring(pre, index);
                if (left != null && !left.trim().equals("")) {
                    r.add(left);
                }

                pre = index + split.length();
            }

            left = str.substring(pre);
            if (left != null && !left.trim().equals("")) {
                r.add(left);
            }
        }

        return r;
    }

    private static String leftTrim(String s1, String s2) {
        while(s1.startsWith(s2)) {
            s1 = s1.substring(s2.length());
        }

        return s1;
    }

    public static String listToString(List<?> list, String link) {
        StringBuffer sb = new StringBuffer();
        Iterator iterator = list.iterator();

        while(iterator.hasNext()) {
            sb.append(iterator.next()).append(link);
        }

        String rt = sb.toString();
        if (rt.length() > link.length()) {
            rt = rt.substring(0, rt.length() - link.length());
        }

        return rt;
    }

    public static String round(String numberStr, int decimalIndex) {
        String desStr = numberStr.substring(0, numberStr.lastIndexOf(".") + 1 + decimalIndex + 1);
        BigDecimal deSource = new BigDecimal(desStr);
        String newStr = String.valueOf(deSource.setScale(decimalIndex, 4).doubleValue());
        return new String(newStr);
    }

    public static String unicode2Char(String str) {
        int index = 0;
        String result = "";

        while(true) {
            while(index < str.length() - 1) {
                if (str.charAt(index) == '%' && str.charAt(index + 1) == 'u') {
                    result = result + (char)Integer.parseInt(str.substring(index + 2, index + 6), 16);
                    index += 6;
                } else {
                    result = result + str.charAt(index);
                    ++index;
                }
            }

            if (index <= str.length() - 1) {
                result = result + str.charAt(str.length() - 1);
            }

            return result.replaceAll("%20", " ");
        }
    }

    public static String getPointStr(String str, int length) {
        if (str != null && !"".equals(str)) {
            if (length <= 0) {
                return str;
            } else {
                if (getStrLength(str) > length) {
                    str = getLeftStr(str, length - 2);
                }

                return str;
            }
        } else {
            return "";
        }
    }

    public static String getLeftStr(String str, int length) {
        if (str != null && !"".equals(str)) {
            int index = 0;
            int strLength = str.length();
            if (length < 0) {
                length = 0;
            }

            for(char[] charArray = str.toCharArray(); index < length && index < strLength; ++index) {
                if (charArray[index] >= 12288 && charArray[index] < '\u9fff' || charArray[index] >= '豈') {
                    --length;
                }

                if (charArray[index] == '&') {
                    if (strLength > index + 3 && charArray[index + 1] == 'l' && charArray[index + 2] == 't' && charArray[index + 3] == ';') {
                        length += 3;
                        index += 3;
                    }

                    if (strLength > index + 4 && charArray[index + 1] == '#' && charArray[index + 2] == '4' && charArray[index + 3] == '6' && charArray[index + 4] == ';') {
                        length += 4;
                        index += 4;
                    }

                    if (strLength > index + 5 && charArray[index + 1] == 'n' && charArray[index + 2] == 'b' && charArray[index + 3] == 's' && charArray[index + 4] == 'p' && charArray[index + 5] == ';') {
                        length += 5;
                        index += 5;
                    }

                    if (strLength > index + 5 && charArray[index + 1] == 'q' && charArray[index + 2] == 'u' && charArray[index + 3] == 'o' && charArray[index + 4] == 't' && charArray[index + 5] == ';') {
                        length += 5;
                        index += 5;
                    }

                    if (strLength > index + 6 && charArray[index + 1] == 'a' && charArray[index + 2] == 'c' && charArray[index + 3] == 'u' && charArray[index + 4] == 't' && charArray[index + 5] == 'e' && charArray[index + 6] == ';') {
                        length += 6;
                        index += 6;
                    }

                    if (strLength > index + 6 && charArray[index + 1] == 'c' && charArray[index + 2] == 'e' && charArray[index + 3] == 'd' && charArray[index + 4] == 'i' && charArray[index + 5] == 'l' && charArray[index + 6] == ';') {
                        length += 6;
                        index += 6;
                    }
                }
            }

            String returnStr = str.substring(0, index);
            returnStr = returnStr + "..";
            return returnStr;
        } else {
            return "";
        }
    }

    public static int getStrLength(String str) {
        if (str != null && !"".equals(str)) {
            char[] charArray = str.toCharArray();
            int length = 0;
            int strLength = str.length();

            for(int i = 0; i < charArray.length; ++i) {
                if ((charArray[i] < 13312 || charArray[i] >= '\u9fff') && charArray[i] < '豈') {
                    if (charArray[i] == '&') {
                        if (strLength > i + 3 && charArray[i + 1] == 'l' && charArray[i + 2] == 't' && charArray[i + 3] == ';') {
                            ++length;
                            i += 3;
                        }

                        if (strLength > i + 4 && charArray[i + 1] == '#' && charArray[i + 2] == '4' && charArray[i + 3] == '6' && charArray[i + 4] == ';') {
                            ++length;
                            i += 4;
                        }

                        if (strLength > i + 5 && charArray[i + 1] == 'n' && charArray[i + 2] == 'b' && charArray[i + 3] == 's' && charArray[i + 4] == 'p' && charArray[i + 5] == ';') {
                            ++length;
                            i += 5;
                        }

                        if (strLength > i + 5 && charArray[i + 1] == 'q' && charArray[i + 2] == 'u' && charArray[i + 3] == 'o' && charArray[i + 4] == 't' && charArray[i + 5] == ';') {
                            ++length;
                            i += 5;
                        }

                        if (strLength > i + 6 && charArray[i + 1] == 'a' && charArray[i + 2] == 'c' && charArray[i + 3] == 'u' && charArray[i + 4] == 't' && charArray[i + 5] == 'e' && charArray[i + 6] == ';') {
                            ++length;
                        }

                        if (strLength > i + 6 && charArray[i + 1] == 'c' && charArray[i + 2] == 'e' && charArray[i + 3] == 'd' && charArray[i + 4] == 'i' && charArray[i + 5] == 'l' && charArray[i + 6] == ';') {
                            ++length;
                            i += 6;
                        }
                    } else {
                        ++length;
                    }
                } else {
                    length += 2;
                }
            }

            return length;
        } else {
            return 0;
        }
    }

    public static String unescape(String src) {
        if (src == null) {
            return null;
        } else {
            StringBuffer tmp = new StringBuffer();
            tmp.ensureCapacity(src.length());
            int lastPos = 0;
            boolean var3 = false;

            while(lastPos < src.length()) {
                int pos = src.indexOf("%", lastPos);
                if (pos == lastPos) {
                    char ch;
                    if (src.charAt(pos + 1) == 'u') {
                        ch = (char)Integer.parseInt(src.substring(pos + 2, pos + 6), 16);
                        tmp.append(ch);
                        lastPos = pos + 6;
                    } else {
                        ch = (char)Integer.parseInt(src.substring(pos + 1, pos + 3), 16);
                        tmp.append(ch);
                        lastPos = pos + 3;
                    }
                } else if (pos == -1) {
                    tmp.append(src.substring(lastPos));
                    lastPos = src.length();
                } else {
                    tmp.append(src.substring(lastPos, pos));
                    lastPos = pos;
                }
            }

            return tmp.toString();
        }
    }

    public static String transforStr(String str) {
        return str.replaceAll("'", "''");
    }

    public static String doRepEsc(String str) {
        return str == null ? str : str.replaceAll("\\\\", "\\\\\\\\").replaceAll("\"", "\\\\\\\"");
    }

    public static String doReptoBr(String str) {
        if (str == null) {
            return str;
        } else {
            Pattern pattern = Pattern.compile("(\r\n|\r|\n|\n\r)");
            Matcher matcher = pattern.matcher(str.toString());
            return matcher.replaceAll("<br>");
        }
    }

    public static String doReptoNull(String str) {
        if (str == null) {
            return str;
        } else {
            Pattern pattern = Pattern.compile("(\r\n|\r|\n|\n\r)");
            Matcher matcher = pattern.matcher(str.toString());
            return matcher.replaceAll("");
        }
    }

    public static String doReptoEncode(String str) {
        if (str != null && !"".equals(str.trim())) {
            Pattern pattern = Pattern.compile("(\r\n)");
            Matcher matcher = pattern.matcher(str.toString());
            str = matcher.replaceAll("#%#RNNEWLINEANDENTERFORFORMITEMENCODE#%#");
            pattern = Pattern.compile("(\n\r)");
            matcher = pattern.matcher(str.toString());
            str = matcher.replaceAll("#%#RNENTERANDNEWLINEFORFORMITEMENCODE#%#");
            pattern = Pattern.compile("(\r)");
            matcher = pattern.matcher(str.toString());
            str = matcher.replaceAll("#%#RNNEWLINEFORFORMITEMENCODE#%#");
            pattern = Pattern.compile("(\n)");
            matcher = pattern.matcher(str.toString());
            str = matcher.replaceAll("#%#RNENTERFORFORMITEMENCODE#%#");
            return str;
        } else {
            return str;
        }
    }

    public static String doReptoDecode(String str) {
        if (str != null && !"".equals(str.trim())) {
            Pattern pattern = Pattern.compile("(#%#RNNEWLINEANDENTERFORFORMITEMENCODE#%#)");
            Matcher matcher = pattern.matcher(str.toString());
            str = matcher.replaceAll("\r\n");
            pattern = Pattern.compile("(#%#RNENTERANDNEWLINEFORFORMITEMENCODE#%#)");
            matcher = pattern.matcher(str.toString());
            str = matcher.replaceAll("\n\r");
            pattern = Pattern.compile("(#%#RNNEWLINEFORFORMITEMENCODE#%#)");
            matcher = pattern.matcher(str.toString());
            str = matcher.replaceAll("\r");
            pattern = Pattern.compile("(#%#RNENTERFORFORMITEMENCODE#%#)");
            matcher = pattern.matcher(str.toString());
            str = matcher.replaceAll("\n");
            return str;
        } else {
            return str;
        }
    }

    public static String bytes2String(byte[] bytes) {
        return bytes == null ? "" : new String(bytes);
    }

    public static String null2String(String str) {
        return str == null ? "" : str;
    }

    public static String addWidth(String args0, String args1) {
        if (isNullStr(args0)) {
            return "";
        } else {
            int sindex = args0.indexOf("style");
            int iindex;
            String start;
            String end;
            if (sindex < 0) {
                iindex = args0.indexOf("id");
                if (iindex >= 0) {
                    start = args0.substring(0, iindex);
                    end = args0.substring(iindex);
                    return start + " style = \"width:" + args1 + "\" " + end;
                } else {
                    return args0;
                }
            } else {
                args0 = args0.replaceAll("=", " = ").replaceAll(" +", "&nbsp;");
                iindex = args0.indexOf("style&nbsp;=&nbsp;\"") + "style&nbsp;=&nbsp;\"".length();
                start = args0.substring(0, iindex);
                end = args0.substring(iindex);
                String result = start + "width:" + args1 + ";" + end;
                return result.replaceAll("&nbsp;", " ");
            }
        }
    }

    public static String[] splitString(String str, String split) {
        if (str != null && !"".equals(str)) {
            if (split == null || "".equals(split)) {
                split = ",";
            }

            return str.split(split);
        } else {
            return new String[0];
        }
    }

    public static String replaceRT2BR(String in) {
        return replace(replace(replace(replace(in, "\r\n", "<br>"), "\r", "<br>"), "\n", "<br>"), "  ", "&nbsp;");
    }

    public static String subString(String oldStr, int len) {
        if (isNullStr(oldStr)) {
            return "";
        } else if (oldStr.length() <= 4) {
            return oldStr;
        } else {
            String newStr = oldStr.substring(0, len);
            if (newStr.endsWith(".")) {
                newStr = newStr.substring(0, newStr.length() - 1);
            }

            return newStr;
        }
    }

    public static Object getter(Object obj, String att) {
        try {
            Method method = obj.getClass().getMethod("get" + captureName(att));
            return method.invoke(obj);
        } catch (Exception var3) {
            return null;
        }
    }

    public static Object getter(Object obj, String att, Object defaultValue) {
        Object object = getter(obj, att);
        if (object == null) {
            object = defaultValue;
        }

        return object;
    }

    public static String captureName(String name) {
        name = name.substring(0, 1).toUpperCase() + name.substring(1);
        return name;
    }

    public static boolean isBlank(String str) {
        return str == null || str.trim().length() == 0 || "null".equals(str);
    }

    public static boolean isNullOrEmpty(Object obj) {
        if (obj == null) {
            return true;
        } else if (obj instanceof CharSequence) {
            return ((CharSequence)obj).length() == 0;
        } else if (obj instanceof Collection) {
            return ((Collection)obj).isEmpty();
        } else if (obj instanceof Map) {
            return ((Map)obj).isEmpty();
        } else if (!(obj instanceof Object[])) {
            return false;
        } else {
            Object[] object = (Object[])((Object[])obj);
            if (object.length == 0) {
                return true;
            } else {
                boolean empty = true;

                for(int i = 0; i < object.length; ++i) {
                    if (!isNullOrEmpty(object[i])) {
                        empty = false;
                        break;
                    }
                }

                return empty;
            }
        }
    }
}
