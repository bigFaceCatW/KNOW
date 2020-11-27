//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.know.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.*;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.*;

public class WebUtils {
    public static final Charset UTF8 = Charset.forName("UTF-8");
    public static final String DEFAULT_CONTENT_TYPE = "application/json";
    public static final String DEFAULT_JSONP_CONTENT_TYPE = "application/javascript";
    public static final String TEMP_DIR_CONTEXT_ATTRIBUTE = "javax.servlet.context.tempdir";
    public static Object userInfoUtil = getUserInfoUtil();

    public WebUtils() {
    }

    public static HttpServletRequest getRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes) {
            ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes)requestAttributes;
            HttpServletRequest request = servletRequestAttributes.getRequest();
            return request;
        } else {
            return null;
        }
    }

    public static String getIpAddr() {
        HttpServletRequest request = getRequest();
        return request == null ? null : getIpAddr(request);
    }

    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        return ip;
    }

    public static boolean isMatchIp(String[] patterns) {
        String clientIp = getIpAddr();
        return PatternMatchUtils.simpleMatch(patterns, clientIp);
    }

    public static boolean isMatchIp(String[] patterns, HttpServletRequest request) {
        String clientIp = getIpAddr(request);
        return PatternMatchUtils.simpleMatch(patterns, clientIp);
    }

    public static boolean isMatchUrl(String[] patterns, HttpServletRequest request) {
        String ctx_path = request.getContextPath();
        String request_uri = request.getRequestURI();
        String action = request_uri.substring(ctx_path.length()).replaceAll("//", "/");
        return PatternMatchUtils.simpleMatch(patterns, action);
    }

    public static MultiValueMap<String, List<String>> getMultiValueMap(HttpServletRequest request) {
        Map<String, String[]> paramMap = request.getParameterMap();
        MultiValueMap<String, List<String>> valueMap = new LinkedMultiValueMap();
        Iterator var3 = paramMap.keySet().iterator();

        while(var3.hasNext()) {
            String paramKey = (String)var3.next();
            String[] values = (String[])paramMap.get(paramKey);
            valueMap.add(paramKey, Arrays.asList(values));
        }

        return valueMap;
    }

    public static Map<String, String> getSingleValueMap(HttpServletRequest request) {
        Map<String, String> result = new HashMap();
        Map<String, String[]> paramMap = request.getParameterMap();
        Iterator var3 = paramMap.keySet().iterator();

        while(var3.hasNext()) {
            String paramKey = (String)var3.next();
            String[] values = (String[])paramMap.get(paramKey);
            String value = StringUtils.collectionToDelimitedString(Arrays.asList(values), ",");
            result.put(paramKey, value);
        }

        return result;
    }

    public static void writeJson(Object obj, HttpServletRequest request, HttpServletResponse response) throws IOException {
        OutputStream out = response.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        String text = mapper.writeValueAsString(obj);
        String jsonpFunction = getJsonpParameterValue(request);
        response.setCharacterEncoding("UTF-8");
        if (jsonpFunction != null) {
            response.setContentType("application/javascript");
            text = jsonpFunction + "(" + text + ")";
        } else {
            response.setContentType("application/json");
        }

        byte[] bytes = text.getBytes(UTF8);
        out.write(bytes);
    }

    private static String getJsonpParameterValue(HttpServletRequest request) {
        Set<String> jsonpParameterNames = new LinkedHashSet(Arrays.asList("jsonp", "callback"));
        Iterator var2 = jsonpParameterNames.iterator();

        String value;
        do {
            if (!var2.hasNext()) {
                return null;
            }

            String name = (String)var2.next();
            value = request.getParameter(name);
        } while(StringUtils.isEmpty(value));

        return value;
    }

    public static void download(HttpServletResponse response, InputStream in, String fileName, HttpServletRequest request) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        String header = request.getHeader("User-Agent").toUpperCase();
        if (!header.contains("MSIE") && !header.contains("TRIDENT") && !header.contains("EDGE")) {
            fileName = new String(fileName.getBytes(), "ISO8859-1");
        } else {
            fileName = URLEncoder.encode(fileName, "utf-8");
            fileName = fileName.replace("+", "%20");
        }

        response.setContentType("multipart/form-data");
        response.setHeader("Content-disposition", "attachment; filename=" + fileName);
        ServletOutputStream out = response.getOutputStream();
        BufferedInputStream bis = new BufferedInputStream(in);
        BufferedOutputStream bos = new BufferedOutputStream(out);
        byte[] buff = new byte[2048];

        int bytesRead;
        while(-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
            bos.write(buff, 0, bytesRead);
        }

        bis.close();
        bos.close();
    }

    public static void download(HttpServletResponse response, InputStream in, String fileName) throws IOException {
        String attachName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
        response.setContentType("multipart/form-data");
        response.setHeader("Content-disposition", "attachment; filename=" + attachName);
        ServletOutputStream out = response.getOutputStream();
        BufferedInputStream bis = new BufferedInputStream(in);
        BufferedOutputStream bos = new BufferedOutputStream(out);
        byte[] buff = new byte[2048];

        int bytesRead;
        while(-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
            bos.write(buff, 0, bytesRead);
        }

        bis.close();
        bos.close();
    }

    public static void view(HttpServletResponse response, InputStream in, String fileName, String contentType) throws IOException {
        response.setContentType(contentType);
        ServletOutputStream out = response.getOutputStream();
        BufferedInputStream bis = new BufferedInputStream(in);
        BufferedOutputStream bos = new BufferedOutputStream(out);
        byte[] buff = new byte[2048];

        int bytesRead;
        while(-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
            bos.write(buff, 0, bytesRead);
        }

        bis.close();
        bos.close();
    }

    public static File getTempDir(ServletContext servletContext) {
        Assert.notNull(servletContext, "ServletContext must not be null");
        return (File)servletContext.getAttribute("javax.servlet.context.tempdir");
    }

    public static String urlencoder(String str, String enc) {
        String encStr = null;

        try {
            encStr = URLEncoder.encode(str, enc);
            return encStr;
        } catch (Exception var4) {
            return "";
        }
    }



    public static String getToken() {
        Object originalToken = getOriginalToken();
        return originalToken != null && originalToken instanceof String ? (String)originalToken : null;
    }

    private static Object getUserInfoUtil() {
        try {
            ClassLoader classLoader = WebUtils.class.getClassLoader();
            Class userInfoUtilClz = classLoader.loadClass("com.unicom.microserv.cs.pvc.certclient.security.certificationclient.util.SpringSecurityUtil");
            return userInfoUtilClz.newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException var2) {
            var2.printStackTrace();
            return null;
        }
    }

    private static Object getOriginalUserInfo() {
        if (userInfoUtil == null) {
            return null;
        } else {
            try {
                Method method = userInfoUtil.getClass().getDeclaredMethod("getUserInfo", (Class[])null);
                return method.invoke(userInfoUtil);
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException var1) {
                var1.printStackTrace();
                return null;
            }
        }
    }

    private static Object getOriginalToken() {
        if (userInfoUtil == null) {
            return null;
        } else {
            try {
                Method method = userInfoUtil.getClass().getDeclaredMethod("getToken", (Class[])null);
                return method.invoke(userInfoUtil);
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException var1) {
                var1.printStackTrace();
                return null;
            }
        }
    }
}
