package com.know.oauth2.util;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.MapUtils;
import org.quartz.utils.PropertiesParser;
import org.springframework.util.Assert;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @Author: FaceCat
 * @Date: 2020/12/23 17:41
 */
public class BeanUtilsEx extends BeanUtils {

    public BeanUtilsEx() {
    }



    public static void mergeProperties(Object dest, Object orig) {
        Assert.notNull(dest, "dest is Null");
        Assert.notNull(orig, "orig is Null");

        try {
            BeanInfo destBeanInfo = Introspector.getBeanInfo(dest.getClass());
            PropertyDescriptor[] propertyDescriptors = destBeanInfo.getPropertyDescriptors();

            for(int i = 0; i < propertyDescriptors.length; ++i) {
                PropertyDescriptor descriptor = propertyDescriptors[i];
                String propertyName = descriptor.getName();
                if (!propertyName.equals("class")) {
                    Object origResult = BeanUtils.getProperty(orig, propertyName);
                    if (origResult != null) {
                        Method writeMethod = descriptor.getWriteMethod();
                        writeMethod.invoke(dest, origResult);
                    }
                }
            }
        } catch (Exception var9) {
            var9.printStackTrace();
        }

    }


    public static String findValFromMapBean(Map map, String key, String fieldName) {
        Assert.notNull(key, "key param is null");
        Assert.notNull(fieldName, "fieldName param is null");
        if (ObjectUtils.isEmpty(map)) {
            return null;
        } else {
            StringBuffer content = new StringBuffer();
            Object obj = map.get(key);
            if (ObjectUtils.isNotEmpty(obj)) {
                String fieldval = null;
                if (Map.class.isAssignableFrom(obj.getClass())) {
                    fieldval = MapUtils.getString((Map)obj, fieldName);
                } else if ("String,Object,Integer,Boolean,Long,Chat,List".contains(obj.getClass().getSimpleName())) {
                    fieldval = obj.toString();
                } else {
                    fieldval = getProperty(obj, fieldName);
                }

                if (ObjectUtils.isNotEmpty(fieldval)) {
                    content.append(content.length() == 0 ? fieldval : "," + fieldval);
                }
            }

            return content.toString();
        }
    }

    public static String getProperty(Object bean, String name) {
        try {
            return BeanUtils.getProperty(bean, name);
        } catch (Exception var3) {
            return null;
        }
    }

    public static void setBeanProps(Object obj, Properties props) throws Exception {
        Enumeration keys = props.keys();

        while(keys.hasMoreElements()) {
            String name = (String)keys.nextElement();
            String c = name.substring(0, 1).toUpperCase(Locale.US);
            String methName = "set" + c + name.substring(1);
            Method setMeth = getSetMethod(methName, obj.getClass());

            try {
                if (setMeth == null) {
                    throw new NoSuchMethodException("No setter for property '" + name + "'");
                }

                Class<?>[] params = setMeth.getParameterTypes();
                if (params.length != 1) {
                    throw new NoSuchMethodException("No 1-argument setter for property '" + name + "'");
                }

                PropertiesParser refProps = new PropertiesParser(props);
                if (params[0].equals(Integer.TYPE)) {
                    setMeth.invoke(obj, refProps.getIntProperty(name));
                } else if (params[0].equals(Long.TYPE)) {
                    setMeth.invoke(obj, refProps.getLongProperty(name));
                } else if (params[0].equals(Float.TYPE)) {
                    setMeth.invoke(obj, refProps.getFloatProperty(name));
                } else if (params[0].equals(Double.TYPE)) {
                    setMeth.invoke(obj, refProps.getDoubleProperty(name));
                } else if (params[0].equals(Boolean.TYPE)) {
                    setMeth.invoke(obj, refProps.getBooleanProperty(name));
                } else {
                    if (!params[0].equals(String.class)) {
                        throw new NoSuchMethodException("No primitive-type setter for property '" + name + "'");
                    }

                    setMeth.invoke(obj, refProps.getStringProperty(name));
                }
            } catch (NumberFormatException var10) {
                throw new Exception("Could not parse property '" + name + "' into correct data type: " + var10.toString());
            }
        }

    }

    public static <T> Method getSetMethod(String name, Class<T> clz) {
        Method[] methods = clz.getDeclaredMethods();
        Method[] var3 = methods;
        int var4 = methods.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            Method method = var3[var5];
            if (method.getName().equals(name)) {
                return method;
            }
        }

        return null;
    }


    public static Map convertBean(Object bean) throws IntrospectionException, IllegalAccessException, InvocationTargetException {
        Map returnMap = new HashMap();
        BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();

        for(int i = 0; i < propertyDescriptors.length; ++i) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();
            if (!propertyName.equals("class")) {
                Method readMethod = descriptor.getReadMethod();
                Object result = readMethod.invoke(bean);
                if (result != null) {
                    returnMap.put(propertyName, result);
                } else {
                    returnMap.put(propertyName, "");
                }
            }
        }

        return returnMap;
    }

    public static Object convertMap(Class type, Map map) throws IntrospectionException, IllegalAccessException, InstantiationException, InvocationTargetException {
        BeanInfo beanInfo = Introspector.getBeanInfo(type);
        Object obj = type.newInstance();
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();

        for(int i = 0; i < propertyDescriptors.length; ++i) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();
            if (map.containsKey(propertyName)) {
                Object value = map.get(propertyName);
                Object[] args = new Object[]{value};
                descriptor.getWriteMethod().invoke(obj, args);
            }
        }

        return obj;
    }



}
