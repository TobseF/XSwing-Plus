/*
 * @version 0.0 17.04.2010
 * @author Tobse F
 */
package tests;

import lib.mylib.options.DefaultArgs;
import lib.mylib.options.DefaultArgs.Args;
import lib.mylib.util.MyPropertysArgs;

public class MyPropertys2Test {

    public static void main(String[] args) {
        new MyPropertys2Test();
    }

    public MyPropertys2Test() {
        MyPropertysArgs<DefaultArgs.Args> props = new MyPropertysArgs<Args>();
        props.setNumber(Args.canceledGames, 42);
    }
}
