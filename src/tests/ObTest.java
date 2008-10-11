package tests;

import java.awt.Point;

public class ObTest {
    String s = "X";
    Point p = new Point(8, 8);

    public ObTest() {
        System.out.println(this.p.x);
        this.p = this.modP(this.p);
        System.out.println(this.p.x);
        System.out.println(this.s);
        this.modS(this.s);
        System.out.println(this.s);
    }

    public void modS(String sn) {
        sn = sn.toLowerCase();
    }

    public Point modP(Point p) {
        p.setLocation(1, 1);
        return p;
    }

    public static void main(String[] args) {
        new ObTest();
    }
}
