package test;

import java.util.ArrayList;
import java.util.List;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.opengl.TextureImpl;
import org.newdawn.slick.opengl.renderer.Renderer;
import org.newdawn.slick.opengl.renderer.SGL;
import org.newdawn.slick.util.InputAdapter;

public class Menu {
    private static SGL GL = Renderer.get();
    private Input input;
    private MenuListener listener;
    private List entries = new ArrayList();
    private int currentIndex;
    private int yOffset;
    private boolean wrapAround;
    private boolean stopAnimation;
    private boolean isAnimationWrapping;
    private boolean disableUpAnimation;
    private boolean disableDownAnimation;
    private int animationDirection;
    private int animationTime;
    private int animationSpeed;
    private int regularSpeed = 125;
    private int wrapSpeed = 1000;
    private float fastMultiplier = 6.0f;
    private int x;
    private int y;
    private int width;
    private int height;
    private Font font;
    private int entryHeight;
    private int gradientHeight;

    public Menu(Font font, Input input, final MenuListener listener) {
        if (font == null) {
            throw new IllegalArgumentException("font cannot be null.");
        }
        if (input == null) {
            throw new IllegalArgumentException("input cannot be null.");
        }
        this.font = font;
        this.input = input;
        this.listener = listener;
        input.addListener(new InputAdapter(){

            public void keyPressed(int key, char c) {
                switch (key) {
                    case 201: {
                        Menu.this.stopAnimation();
                        Menu menu = Menu.this;
                        menu.currentIndex = (int)((double)menu.currentIndex - Math.max(1.0, Math.floor((float)Menu.this.height / (float)Menu.this.entryHeight) - 1.0));
                        if (Menu.this.currentIndex < 0) {
                            Menu menu2 = Menu.this;
                            menu2.currentIndex = menu2.currentIndex + Menu.this.entries.size();
                        }
                        Menu.this.fireEntryChanged();
                        break;
                    }
                    case 209: {
                        Menu.this.stopAnimation();
                        Menu menu = Menu.this;
                        menu.currentIndex = (int)((double)menu.currentIndex + Math.max(1.0, Math.floor((float)Menu.this.height / (float)Menu.this.entryHeight) - 1.0));
                        if (Menu.this.currentIndex >= Menu.this.entries.size()) {
                            Menu menu3 = Menu.this;
                            menu3.currentIndex = menu3.currentIndex - Menu.this.entries.size();
                        }
                        Menu.this.fireEntryChanged();
                        break;
                    }
                    case 199: {
                        Menu.this.stopAnimation();
                        Menu.this.currentIndex = 0;
                        Menu.this.fireEntryChanged();
                        break;
                    }
                    case 207: {
                        Menu.this.stopAnimation();
                        Menu.this.currentIndex = Menu.this.entries.size() - 1;
                        Menu.this.fireEntryChanged();
                        break;
                    }
                    case 28: {
                        if (listener == null) break;
                        listener.entrySelected();
                    }
                }
            }
        });
        this.entryHeight = font.getLineHeight();
    }

    public void setBounds(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void render(Graphics g) throws SlickException {
        if (this.entries.size() == 0) {
            return;
        }
        g.translate(this.x, this.y);
        g.setWorldClip(0.0f, 0.0f, this.width, this.height);
        int entryCount = this.entries.size();
        int startY = this.height / 2 - this.entryHeight / 2;
        boolean entriesWrap = this.entriesWrap();
        g.setBackground(Color.red);
        g.clear();
        this.renderEntrySelection(g, this.width / 2, startY);
        g.setFont(this.font);
        int entryY = startY + this.yOffset + 1;
        int entryIndex = this.currentIndex;
        int i = 0;
        while (i < entryCount) {
            this.renderEntry(g, this.entries.get(entryIndex), entryY);
            if ((entryY += this.entryHeight) > this.height) break;
            if (++entryIndex == entryCount) {
                if (!entriesWrap) break;
                entryIndex = 0;
            }
            ++i;
        }
        entryY = startY + this.yOffset - this.entryHeight;
        entryIndex = this.currentIndex - 1;
        i = 0;
        while (i < entryCount) {
            if (entryIndex == -1) {
                if (!entriesWrap) break;
                entryIndex = entryCount - 1;
            }
            this.renderEntry(g, this.entries.get(entryIndex), entryY);
            if ((entryY -= this.entryHeight) < 0 - this.entryHeight) break;
            --entryIndex;
            ++i;
        }
        if (this.gradientHeight > 0) {
            g.clearAlphaMap();
            g.setDrawMode(Graphics.MODE_ALPHA_MAP);
            TextureImpl.bindNone();
            GL.glBegin(7);
            new Color(1, 1, 1, 1).bind();
            GL.glVertex2f(0.0f, this.height - this.gradientHeight);
            GL.glVertex2f(this.width, this.height - this.gradientHeight);
            Color.white.bind();
            GL.glVertex2f(this.width, this.height);
            GL.glVertex2f(0.0f, this.height);
            Color.white.bind();
            GL.glVertex2f(0.0f, 0.0f);
            GL.glVertex2f(this.width, 0.0f);
            new Color(1, 1, 1, 1).bind();
            GL.glVertex2f(this.width, this.gradientHeight);
            GL.glVertex2f(0.0f, this.gradientHeight);
            GL.glEnd();
            g.setDrawMode(Graphics.MODE_ALPHA_BLEND);
            g.setColor(Color.red);
            g.fillRect(0.0f, 0.0f, this.width, this.height);
            g.setDrawMode(Graphics.MODE_NORMAL);
        }
        g.clearWorldClip();
        g.resetTransform();
    }

    protected void renderEntrySelection(Graphics g, int x, int y) {
        g.setColor(new Color(1.0f, 1.0f, 1.0f, 0.4f));
        int currentEntryWidth = this.font.getWidth(this.getSelectedEntry().toString());
        g.fillRect(0.0f, y, currentEntryWidth + 7, this.entryHeight);
        g.setColor(Color.white);
    }

    protected void renderEntry(Graphics g, Object entry, int y) {
        g.drawString(entry.toString(), 1.0f, y);
    }

    private void stopAnimation() {
        this.yOffset = 0;
        this.animationDirection = 0;
        this.animationTime = 0;
    }

    public void fireEntryChanged() {
        if (this.listener != null) {
            this.listener.entryChanged(this.entries.get(this.currentIndex));
        }
    }

    private boolean entriesWrap() {
        return this.wrapAround && (double)this.entries.size() > Math.ceil((int)Math.floor(this.height / this.entryHeight));
    }

    private void animate(int direction) {
        boolean reverseOldAnimation;
        if (this.entries.size() < 2) {
            return;
        }
        this.stopAnimation = false;
        int oldAnimationDirection = this.animationDirection;
        this.animationDirection = direction;
        this.isAnimationWrapping = false;
        if (!this.entriesWrap() && (this.animationDirection > 0 && this.currentIndex == 0 || this.animationDirection < 0 && this.currentIndex == this.entries.size() - 1)) {
            this.isAnimationWrapping = true;
        }
        this.currentIndex = this.animationDirection > 0 ? (this.currentIndex == 0 ? this.entries.size() - 1 : this.currentIndex - 1) : (this.currentIndex == this.entries.size() - 1 ? 0 : this.currentIndex + 1);
        this.fireEntryChanged();
        boolean bl = reverseOldAnimation = oldAnimationDirection != 0 && oldAnimationDirection != direction;
        if (reverseOldAnimation) {
            this.animationTime = this.animationSpeed - this.animationTime;
        }
        this.animationSpeed = this.isAnimationWrapping ? this.wrapSpeed : this.regularSpeed;
        if (!reverseOldAnimation) {
            this.animationTime = this.animationSpeed;
        }
    }

    public void update(int delta) throws SlickException {
        if (this.input.isKeyDown(200)) {
            if (!this.disableUpAnimation && this.animationDirection != 1) {
                this.animate(1);
            }
        } else {
            if (this.animationDirection == 1) {
                this.stopAnimation = true;
            }
            this.disableUpAnimation = false;
        }
        if (this.input.isKeyDown(208)) {
            if (!this.disableDownAnimation && this.animationDirection != -1) {
                this.animate(-1);
            }
        } else {
            if (this.animationDirection == -1) {
                this.stopAnimation = true;
            }
            this.disableDownAnimation = false;
        }
        if (this.animationDirection != 0 || this.animationTime > 0) {
            boolean useFastMultiplier = this.input.isKeyDown(157) || this.input.isKeyDown(29);
            this.animationTime = (int)((float)this.animationTime - (float)delta * (useFastMultiplier ? this.fastMultiplier : 1.0f));
            if (this.animationTime <= 0) {
                boolean entriesWrap = this.entriesWrap();
                if (!entriesWrap && this.animationDirection > 0 && this.currentIndex == 0) {
                    this.stopAnimation();
                    this.disableUpAnimation = true;
                } else if (!entriesWrap && this.animationDirection < 0 && this.currentIndex == this.entries.size() - 1) {
                    this.stopAnimation();
                    this.disableDownAnimation = true;
                } else if (this.stopAnimation) {
                    this.stopAnimation();
                } else {
                    this.yOffset = 0;
                    int negativeTimeOver = this.animationTime;
                    this.animate(this.animationDirection);
                    this.animationTime += negativeTimeOver;
                    if (this.animationTime <= 0) {
                        this.animationTime = this.animationSpeed;
                    }
                }
            }
            int distance = this.isAnimationWrapping ? this.height + this.entryHeight : this.entryHeight;
            this.yOffset = -((int)((float)distance * ((float)this.animationTime / (float)this.animationSpeed))) * this.animationDirection;
            if (this.isAnimationWrapping && Math.abs(this.yOffset) > this.height / 2 + this.entryHeight) {
                this.yOffset -= (this.height + this.entryHeight * (this.entries.size() - 1) + this.entryHeight) * -this.animationDirection;
            }
        }
    }

    public void setSpeeds(int regularSpeed, int wrapSpeed, float fastMultiplier) {
        this.regularSpeed = regularSpeed;
        this.wrapSpeed = wrapSpeed;
        this.fastMultiplier = fastMultiplier;
    }

    public void setWrapAround(boolean wrapAround) {
        this.wrapAround = wrapAround;
    }

    public void setGradientHeight(int gradientHeight) {
        this.gradientHeight = gradientHeight;
    }

    public Object getSelectedEntry() {
        if (this.currentIndex < 0 || this.currentIndex >= this.entries.size()) {
            return null;
        }
        return this.entries.get(this.currentIndex);
    }

    public List getEntries() {
        return this.entries;
    }

    public void setEntries(List entries) {
        this.entries = entries;
    }

    public static interface MenuListener {
        public void entryChanged(Object var1);

        public void entrySelected();
    }
}
