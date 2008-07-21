package test;

import java.util.Arrays;
import lib.SObject;

public class CopyOfGrid
extends SObject {
    int ballA = 48;
    int gap = 16;
    int w;
    int h;
    int[][] filled = new int[8][9];

    public CopyOfGrid(int x, int y) {
        super(x, y);
        this.w = (this.ballA + this.gap) * this.filled.length;
        this.h = this.ballA * this.filled[0].length;
        int[][] nArray = this.filled;
        int n = this.filled.length;
        int n2 = 0;
        while (n2 < n) {
            int[] i = nArray[n2];
            Arrays.fill(i, -1);
            ++n2;
        }
    }

    public void setValue(int x, int y, int value) {
        this.filled[x][y] = value;
    }

    public int getValue(int x, int y) {
        return this.filled[x][y];
    }

    public int[] getField(int x, int y) {
        int posX = (int)((double)(x - this.x + this.gap) / (double)(this.ballA + this.gap));
        int posY = (int)((double)(this.y + this.h - y + this.ballA) / (double)this.ballA);
        return new int[]{posX, posY};
    }

    public boolean isEmpty(int posX, int posY) {
        if (posX < this.filled.length && posY < this.filled[0].length) {
            return this.filled[posX][posY] == -1;
        }
        return true;
    }

    public boolean isEmpty(int[] pos) {
        return this.isEmpty(pos[0], pos[1]);
    }

    public void printGrid() {
        System.out.println(this.filled[0].length);
        int i = this.filled[0].length;
        while (i > 0) {
            int ii = 0;
            while (ii < this.filled.length) {
                System.out.print(" " + this.filled[ii][i - 1]);
                ++ii;
            }
            System.out.println("");
            --i;
        }
    }

    public boolean isOverGrid(int x, int y) {
        return y > this.y;
    }

    public int[] getFieldPosOnScreen(int posX, int posY) {
        return new int[]{this.x + this.gap + posX * (this.ballA + this.gap), this.y + this.h - posY * this.ballA};
    }

    public int[] getFieldPos(int posX, int posY) {
        return new int[]{this.gap + posX * (this.ballA + this.gap), this.h - posY * this.ballA};
    }

    public int[] getFieldPosOnScreen(int[] pos) {
        return this.getFieldPosOnScreen(pos[0], pos[1]);
    }

    public void checkCollsion() {
    }
}
