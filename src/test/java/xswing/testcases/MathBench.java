/*
 * @version 0.0 13.02.2011
 * @author 	Tobse F
 */
package xswing.testcases;


import org.junit.Test;

public class MathBench {
    int[] listI = new int[10000];
    float[] listF = new float[10000];
    double[] listD = new double[10000];

    @Test
    public void testInt() {
        for (int i = 0; i < 10000; i++) {
            listI[i] = i * i;
        }
    }

    @Test
    public void testFloat() {
        for (int i = 0; i < 10000; i++) {
            listF[i] = ((float) i) * ((float) i);
        }
    }

    @Test
    public void testDouble() {
        for (int i = 0; i < 10000; i++) {
            listD[i] = ((double) i) * ((double) i);
        }
    }

    @Test
    public void testInt2() {
        for (int i = 0; i < 10000; i++) {
            listI[i] = i / (i + 1);
        }
    }

    @Test
    public void testFloat2() {
        for (int i = 0; i < 10000; i++) {
            listF[i] = ((float) i) / ((float) i + 1);
        }
    }

    @Test
    public void testDouble2() {
        for (int i = 0; i < 10000; i++) {
            listD[i] = ((double) i) / ((double) i + 1);
        }
    }

}
