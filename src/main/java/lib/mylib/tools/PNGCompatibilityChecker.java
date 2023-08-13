/*
 * @version 0.0 08.03.2009
 * @author Tobse F
 */
package lib.mylib.tools;

import lib.mylib.swing.SwingUtils;
import org.newdawn.slick.*;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Gui where you can select PNG files which are loaded with Slick. Useful to see if they are
 * readable without warinings.
 *
 * @author Tobse
 */
public class PNGCompatibilityChecker extends JFrame {

    public PNGCompatibilityChecker() {
        setSize(200, 200);
        setTitle("PNGCompatibilityChecker");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        SwingUtils.setCoolLookAndFeel();
        SwingUtils.setLocationToCenter(this);
        JButton setResFolder = new JButton("Set res folder");
        setResFolder.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                chooseFile();
            }
        });
        add(setResFolder);
        setVisible(true);
    }

    private void chooseFile() {
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fc.setMultiSelectionEnabled(true);
        fc.setFileFilter(new FileFilter() {

            @Override
            public boolean accept(File f) {
                return f.isDirectory() || f.getName().toLowerCase().endsWith(".png");
                // return f.getName().toLowerCase().endsWith(".png");
            }

            @Override
            public String getDescription() {
                return "PNG files";
            }
        });

        int state = fc.showOpenDialog(null);

        if (state == JFileChooser.APPROVE_OPTION) {
            File[] files = fc.getSelectedFiles();
            System.out.println("check images: " + files.length);
            checkImages(files);
        } else {
            System.out.println("abort file selection");
            System.out.println(state);
        }
    }

    private void checkImages(File[] files) {

        BasicGame_ImageLoading game = new BasicGame_ImageLoading(files);
        try {
            AppGameContainer gameStarter = new AppGameContainer(game);
            gameStarter.start();

        } catch (SlickException e) {
            e.printStackTrace();
            System.out.println("arn");
        }
    }

    private class BasicGame_ImageLoading extends BasicGame {

        File[] files;

        public BasicGame_ImageLoading(File[] files) {
            super("Image Loader");
            this.files = files;
        }

        @Override
        public void init(GameContainer container) throws SlickException {
            for (File file : files) {
                try {
                    System.out.println(file.getAbsolutePath());
                    new Image(file.getAbsolutePath());
                } catch (SlickException e) {
                    System.out.println("Can't read: " + file.getAbsolutePath());
                    e.printStackTrace();
                }
            }
            container.exit();
        }

        @Override
        public void update(GameContainer container, int delta) throws SlickException {
        }

        @Override
        public void render(GameContainer container, Graphics g) throws SlickException {
        }

    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        new PNGCompatibilityChecker();

    }

}
