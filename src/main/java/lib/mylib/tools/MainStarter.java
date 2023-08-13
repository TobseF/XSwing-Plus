/*
 * @version 0.0 21.02.2009
 * @author Tobse F
 */
package lib.mylib.tools;

public class MainStarter extends MethodStarter {

    public MainStarter(Class<?> classToInvoke, String[] args) {
        super(classToInvoke, "main", new Object[]{args});
    }

    public MainStarter(String classToInvoke, String[] args) throws ClassNotFoundException {
        super(classToInvoke, "main", new Object[]{args});
    }

}