/*
 * @version 0.0 21.02.2009
 * @author Tobse F
 */
package lib.mylib.tools;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MethodStarter {
	private MethodLauchncher lauchncher = null;

	public MethodStarter(Class<?> classToInvoke, String methodName, Object[] parameters) {
		lauchncher = new MethodLauchncher(classToInvoke, methodName, parameters);
		lauchncher.start();
	}
	
	public MethodStarter(String classToInvoke, String methodName, Object[] parameters) {
		Class<?> theClass = null;
		try {
			theClass = Class.forName(classToInvoke);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		lauchncher = new MethodLauchncher(theClass, methodName, parameters);
		lauchncher.start();
	}
	
	public boolean isMothodStarted() {
		return lauchncher.isMothodStarted();
	}

	private class MethodLauchncher extends Thread {
		private final Object[] parameters;
		private final Class<?> classToInvoke;
		private final String methodName;
		private Class<?>[] parameterTypes;
		private boolean isMothodStarted = true;

		public MethodLauchncher(Class<?> classToInvoke, String methodName, Object[] parameters) {
			this.classToInvoke = classToInvoke;
			this.parameters = parameters;
			this.methodName = methodName;
			parameterTypes = new Class[parameters.length];
			for (int i = 0; i < parameterTypes.length; i++) {
				parameterTypes[i] = parameters[i].getClass();
			}
		}

		public boolean isMothodStarted() {
			return isMothodStarted;
		}

		@Override
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
}