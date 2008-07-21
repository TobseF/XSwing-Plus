package test;

import org.newdawn.slick.util.InputAdapter;
import test.Menu;

final class Menu$1
extends InputAdapter {
    private final Menu.MenuListener val$listener;

    Menu$1(Menu.MenuListener menuListener) {
        this.val$listener = menuListener;
    }

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
                if (this.val$listener == null) break;
                this.val$listener.entrySelected();
            }
        }
    }
}
