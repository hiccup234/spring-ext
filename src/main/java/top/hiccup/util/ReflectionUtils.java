package top.hiccup.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * 反射工具类
 *
 * @author wenhy
 * @date 2018/9/19
 */
public class ReflectionUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReflectionUtils.class);

    /**
     * 获取某个对象的属性的值
     * @param clazz
     * @param object
     * @param filedName
     * @return
     */
    public static Object getFieldValue(Class clazz, Object object, String filedName) {
        Field field = null;
        Field modifiersField = null;
        boolean isAccessible = false;
        int modifiersBak = Modifier.FINAL;
        try {
            field = clazz.getDeclaredField(filedName);
            isAccessible = field.isAccessible();
            modifiersBak = field.getModifiers();
            // 通过反射直接修改Field类的modifiers字段的访问权限
            modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            if (Modifier.isFinal(modifiersBak)) {
                // 如果判断出字段是被final修饰的，则直接修改修饰符modifiers的值
                // 这里不能直接设置为Modifier.PUBLIC，因为Modifier的isFinal方法： return (mod & FINAL) != 0;
                modifiersField.setInt(field, modifiersBak & ~Modifier.FINAL);
            }
            field.setAccessible(true);
            return field.get(object);
        } catch (NoSuchFieldException e) {
            LOGGER.error("ReflectionUtils NoSuchFieldException: ", e);
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            LOGGER.error("ReflectionUtils IllegalAccessException: ", e);
            throw new RuntimeException(e);
        } finally {
            // 这里一定要记得设置回原访问权限
            try {
                modifiersField.setInt(field, modifiersBak);
            } catch (IllegalAccessException e) {
                LOGGER.error("ReflectionUtils IllegalAccessException: ", e);
                throw new RuntimeException(e);
            }
            modifiersField.setAccessible(false);
            field.setAccessible(isAccessible);
        }
    }

    /**
     * set某个对象的属性的值
     * @param clazz
     * @param object
     * @param filedName
     * @param filedValue
     */
    public static void setFieldValue(Class clazz, Object object, String filedName, Object filedValue) {
        Field field = null;
        Field modifiersField = null;
        boolean isAccessible = false;
        int modifiersBak = Modifier.FINAL;
        try {
            field = clazz.getDeclaredField(filedName);
            isAccessible = field.isAccessible();
            modifiersBak = field.getModifiers();
            modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            if (Modifier.isFinal(modifiersBak)) {
                modifiersField.setInt(field, modifiersBak & ~Modifier.FINAL);
            }
            field.setAccessible(true);
            field.set(object, filedValue);
        } catch (NoSuchFieldException e) {
            LOGGER.error("ReflectionUtils NoSuchFieldException: ", e);
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            LOGGER.error("ReflectionUtils IllegalAccessException: ", e);
            throw new RuntimeException(e);
        } finally {
            try {
                modifiersField.setInt(field, modifiersBak);
            } catch (IllegalAccessException e) {
                LOGGER.error("ReflectionUtils IllegalAccessException: ", e);
                throw new RuntimeException(e);
            }
            modifiersField.setAccessible(false);
            field.setAccessible(isAccessible);
        }
    }
}
