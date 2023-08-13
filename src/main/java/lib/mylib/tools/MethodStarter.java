/*
 * @version 0.0 21.02.2009
 * @author Tobse F
 */
package lib.mylib.tools;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MethodStarter {

    public MethodStarter(Class<?> classToInvoke, String methodName, Object[] parameters) {
        if (classToInvoke == null || methodName == null || methodName.isEmpty()) {
            throw new IllegalArgumentException("Class or mehod to start can't be empty");
        }
        this.classToInvoke = classToInvoke;
        this.parameters = parameters;
        this.methodName = methodName;
        parameterTypes = new Class[parameters.length];
        for (int i = 0; i < parameterTypes.length; i++) {
            parameterTypes[i] = parameters[i].getClass();
        }
        run();
    }

    public MethodStarter(String classToInvoke, String methodName, Object[] parameters) throws ClassNotFoundException {
        this(Class.forName(classToInvoke), methodName, parameters);
    }

    public boolean isMethodStarted() {
        return isMothodStarted();
    }

    private final Object[] parameters;
    private final Class<?> classToInvoke;
    private final String methodName;
    private final Class<?>[] parameterTypes;
    private boolean isMothodStarted = true;


    public boolean isMothodStarted() {
        return isMothodStarted;
    }

    public void run() {
        try {
            Method method = classToInvoke.getMethod(methodName, parameterTypes);
            method.invoke(null, parameters);
        } catch (SecurityException e) {
            e.printStackTrace();
            isMothodStarted = false;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            isMothodStarted = false;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            isMothodStarted = false;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            isMothodStarted = false;
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            isMothodStarted = false;
        }
    }
}
