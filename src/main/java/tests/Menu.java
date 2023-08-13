/*
 * @version 0.0 06.06.2008
 * @author Tobse F
 */
package tests;

import org.newdawn.slick.*;
import org.newdawn.slick.opengl.TextureImpl;
import org.newdawn.slick.opengl.renderer.Renderer;
import org.newdawn.slick.opengl.renderer.SGL;
import org.newdawn.slick.util.InputAdapter;

import java.util.ArrayList;
import java.util.List;

public class Menu {

    private static final SGL GL = Renderer.get();

    private final Input input;
    private final MenuListener listener;
    private List<String> entries = new ArrayList<String>();
    private int currentIndex, yOffset;
    private boolean wrapAround;
    private boolean stopAnimation, isAnimationWrapping, disableUpAnimation,
            disableDownAnimation;
    private int animationDirection, animationTime, animationSpeed;
    private int regularSpeed = 125, wrapSpeed = 1000;
    private float fastMultiplier = 6;
    private int x, y, width, height;
    private final Font font;
    private final int entryHeight;
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

        input.addListener(new InputAdapter() {

            @Override
            public void keyPressed(int key, char c) {
                switch (key) {
                    case 201: // pageup
                        stopAnimation();
                        currentIndex -= Math.max(1, Math.floor(height / (float) entryHeight) - 1);
                        if (currentIndex < 0) {
                            currentIndex += entries.size();
                        }
                        fireEntryChanged();
                        break;
                    case 209: // pagedown
                        stopAnimation();
                        currentIndex += Math.max(1, Math.floor(height / (float) entryHeight) - 1);
                        if (currentIndex >= entries.size()) {
                            currentIndex -= entries.size();
                        }
                        fireEntryChanged();
                        break;
                    case Input.KEY_HOME:
                        stopAnimation();
                        currentIndex = 0;
                        fireEntryChanged();
                        break;
                    case Input.KEY_END:
                        stopAnimation();
                        currentIndex = entries.size() - 1;
                        fireEntryChanged();
                        break;
                    case Input.KEY_ENTER:
                        if (listener != null) {
                            listener.entrySelected();
                        }
                        break;
                }
            }
        });

        entryHeight = font.getLineHeight();
    }

    public void setBounds(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void render(Graphics g) throws SlickException {
        if (entries.size() == 0) {
            return;
        }

        g.translate(x, y);
        g.setWorldClip(0, 0, width, height);

        int entryCount = entries.size();
        int startY = height / 2 - entryHeight / 2;

        boolean entriesWrap = entriesWrap();

        g.setBackground(Color.red);
        g.clear();

        renderEntrySelection(g, width / 2, startY);

        g.setFont(font);
        int entryY = startY + yOffset + 1;
        int entryIndex = currentIndex;
        for (int i = 0; i < entryCount; i++) {
            renderEntry(g, entries.get(entryIndex), entryY);
            entryY += entryHeight;
            if (entryY > height) {
                break;
            }
            entryIndex++;
            if (entryIndex == entryCount) {
                if (!entriesWrap) {
                    break;
                }
                entryIndex = 0;
            }
        }

        entryY = startY + yOffset - entryHeight;
        entryIndex = currentIndex - 1;
        for (int i = 0; i < entryCount; i++) {
            if (entryIndex == -1) {
                if (!entriesWrap) {
                    break;
                }
                entryIndex = entryCount - 1;
            }
            renderEntry(g, entries.get(entryIndex), entryY);
            entryY -= entryHeight;
            if (entryY < -entryHeight) {
                break;
            }
            entryIndex--;
        }

        if (gradientHeight > 0) {
            g.clearAlphaMap();
            g.setDrawMode(Graphics.MODE_ALPHA_MAP);

            TextureImpl.bindNone();
            GL.glBegin(SGL.GL_QUADS);
            new Color(1, 1, 1, 1).bind();
            GL.glVertex2f(0, height - gradientHeight);
            GL.glVertex2f(width, height - gradientHeight);
            Color.white.bind();
            GL.glVertex2f(width, height);
            GL.glVertex2f(0, height);

            Color.white.bind();
            GL.glVertex2f(0, 0);
            GL.glVertex2f(width, 0);
            new Color(1, 1, 1, 1).bind();
            GL.glVertex2f(width, gradientHeight);
            GL.glVertex2f(0, gradientHeight);
            GL.glEnd();

            g.setDrawMode(Graphics.MODE_ALPHA_BLEND);

            g.setColor(Color.red);
            g.fillRect(0, 0, width, height);

            g.setDrawMode(Graphics.MODE_NORMAL);
        }

        g.clearWorldClip();
        g.resetTransform();
    }

    protected void renderEntrySelection(Graphics g, int x, int y) {
        g.setColor(new Color(1f, 1f, 1f, 0.4f));
        int currentEntryWidth = font.getWidth(getSelectedEntry().toString());
        g.fillRect(0, y, currentEntryWidth + 7, entryHeight);
        g.setColor(Color.white);
    }

    protected void renderEntry(Graphics g, Object entry, int y) {
        g.drawString(entry.toString(), 1, y);
    }

    private void stopAnimation() {
        yOffset = 0;
        animationDirection = 0;
        animationTime = 0;
    }

    public void fireEntryChanged() {
        if (listener != null) {
            listener.entryChanged(entries.get(currentIndex));
        }
    }

    private boolean entriesWrap() {
        return wrapAround
                && entries.size() > Math.ceil((int) Math.floor(height / entryHeight));
    }

    private void animate(int direction) {
        if (entries.size() < 2) {
            return;
        }

        stopAnimation = false;

        int oldAnimationDirection = animationDirection;
        animationDirection = direction;

        isAnimationWrapping = false;
        if (!entriesWrap()) {
            if ((animationDirection > 0 && currentIndex == 0)
                    || (animationDirection < 0 && currentIndex == entries.size() - 1)) {
                isAnimationWrapping = true;
            }
        }

        if (animationDirection > 0) {
            currentIndex = currentIndex == 0 ? entries.size() - 1 : currentIndex - 1;
        } else {
            currentIndex = currentIndex == entries.size() - 1 ? 0 : currentIndex + 1;
        }
        fireEntryChanged();

        boolean reverseOldAnimation = oldAnimationDirection != 0
                && oldAnimationDirection != direction;
        if (reverseOldAnimation) {
            animationTime = animationSpeed - animationTime;
        }

        if (isAnimationWrapping) {
            animationSpeed = wrapSpeed;
        } else {
            animationSpeed = regularSpeed;
        }

        if (!reverseOldAnimation) {
            animationTime = animationSpeed;
        }
    }

    public void update(int delta) throws SlickException {
        if (input.isKeyDown(Input.KEY_UP)) {
            if (!disableUpAnimation && animationDirection != 1) {
                animate(1);
            }
        } else {
            if (animationDirection == 1) {
                stopAnimation = true;
            }
            disableUpAnimation = false;
        }
        if (input.isKeyDown(Input.KEY_DOWN)) {
            if (!disableDownAnimation && animationDirection != -1) {
                animate(-1);
            }
        } else {
            if (animationDirection == -1) {
                stopAnimation = true;
            }
            disableDownAnimation = false;
        }

        if (animationDirection != 0 || animationTime > 0) {
            boolean useFastMultiplier = input.isKeyDown(Input.KEY_RCONTROL)
                    || input.isKeyDown(Input.KEY_LCONTROL);
            animationTime -= delta * (useFastMultiplier ? fastMultiplier : 1);
            if (animationTime <= 0) {
                boolean entriesWrap = entriesWrap();
                if (!entriesWrap && animationDirection > 0 && currentIndex == 0) {
                    stopAnimation();
                    disableUpAnimation = true;
                } else if (!entriesWrap && animationDirection < 0
                        && currentIndex == entries.size() - 1) {
                    stopAnimation();
                    disableDownAnimation = true;
                } else if (stopAnimation) {
                    stopAnimation();
                } else {
                    yOffset = 0;
                    int negativeTimeOver = animationTime;
                    animate(animationDirection);
                    animationTime += negativeTimeOver;
                    if (animationTime <= 0) {
                        animationTime = animationSpeed;
                    }
                }
            }
            int distance = isAnimationWrapping ? height + entryHeight : entryHeight;
            yOffset = -(int) (distance * (animationTime / (float) animationSpeed))
                    * animationDirection;
            if (isAnimationWrapping && Math.abs(yOffset) > height / 2 + entryHeight) {
                yOffset -= (height + entryHeight * (entries.size() - 1) + entryHeight)
                        * -animationDirection;
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
        if (currentIndex < 0 || currentIndex >= entries.size()) {
            return null;
        }
        return entries.get(currentIndex);
    }

    public List<String> getEntries() {
        return entries;
    }

    public void setEntries(List<String> entries) {
        this.entries = entries;
    }

    public interface MenuListener {

        void entryChanged(Object entry);

        void entrySelected();
    }
}
