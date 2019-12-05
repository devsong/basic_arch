package com.gzs.learn.web.common.constant.dictmap.factory;

import java.lang.reflect.Method;

import com.gzs.learn.web.common.constant.factory.ConstantFactory;
import com.gzs.learn.web.common.constant.factory.IConstantFactory;
import com.gzs.learn.web.common.exception.BizExceptionEnum;
import com.gzs.learn.web.common.exception.BussinessException;

public class DictFieldWarpperFactory {
    public static Object createFieldWarpper(Object field, String methodName) throws BussinessException {
        return testIfParamterQualified(field, methodName, new Class[] { String.class, Integer.class, Long.class });
    }

    private static Object testIfParamterQualified(Object field, String methodName, Class<?>... possibleClazz) throws BussinessException {
        IConstantFactory me = ConstantFactory.me();
        Object result = null;
        for (Class<?> clazz : possibleClazz) {
            try {
                Method method = IConstantFactory.class.getMethod(methodName, clazz);
                Object args = adjustArgs(clazz, field);
                result = method.invoke(me, args);
                break;
            } catch (Exception ignore) {
            }
        }
        if (result == null) {
            throw new BussinessException(BizExceptionEnum.ERROR_WRAPPER_FIELD);
        }
        return result;
    }

    private static Object adjustArgs(Class<?> clazz, Object field) {
        if (field == null) {
            return null;
        }
        String clazzName = clazz.getName();
        Object obj = null;
        switch (clazzName) {
        case "java.lang.String":
            obj = field.toString();
            break;
        case "boolean":
        case "java.lang.Boolean":
            obj = Boolean.parseBoolean(field.toString());
            break;
        case "byte":
        case "java.lang.Byte":
            obj = Byte.parseByte(field.toString());
            break;
        case "short":
        case "java.lang.Short":
            obj = Short.parseShort(field.toString());
            break;
        case "int":
        case "java.lang.Integer":
            obj = Integer.parseInt(field.toString());
            break;
        case "long":
        case "java.lang.Long":
            obj = Long.parseLong(field.toString());
            break;
        default:
            break;
        }
        return obj;
    }

}
