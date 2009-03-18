/*
 * @version 0.0 19.02.2009
 * @author 	Tobse F
 */
package lib.mylib.swing;

import java.awt.AWTEvent;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.AWTEventListener;
import java.util.ArrayList;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.newdawn.slick.util.ResourceLoader;

public class SwingUtils {
	
	public static boolean setSystemLookAndFeel(){
		boolean isSystemLookAndFeelSet = true;
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			isSystemLookAndFeelSet = false;
		}
		return isSystemLookAndFeelSet;	
	}
	
	public static boolean setNimbusLookAndFeel(){
		boolean isNumbusSet = true;
		try { // Nimbus Loyout
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (ClassNotFoundException e) {isNumbusSet = false;
		} catch (InstantiationException e) {isNumbusSet = false;
		} catch (IllegalAccessException e) {isNumbusSet = false;
		} catch (UnsupportedLookAndFeelException e) {isNumbusSet = false;}
		return isNumbusSet;
		
		/*try {//alternative way
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (UnsupportedLookAndFeelException e) {
		} catch (ClassNotFoundException e) {
		} catch (InstantiationException e) {
		} catch (IllegalAccessException e) {
		}*/
	}
		
	public static boolean setCoolLookAndFeel(){
		return (setNimbusLookAndFeel() || setSystemLookAndFeel());
	}

	public static void setLocationToCenter(Window window){
		window.setLocationRelativeTo(null);
	}
	
	public static void addGlobalKexListener(AWTEventListener listener){
		/*AWTEventListener ael = new AWTEventListener() { 
			  public void eventDispatched( AWTEvent e ) {
				  if(e.getID() == KeyEvent.KEY_PRESSED){ }
				   keyPressed((KeyEvent) e);
			  } 
			};*/ 
		Toolkit.getDefaultToolkit().addAWTEventListener( listener, AWTEvent.KEY_EVENT_MASK );
	}
	
	public static void setIcons(Frame frame, String iconFolder) {
		String icon = "icon";
		ArrayList<Image> imageList = new ArrayList<Image>();
		for (int i = 1; i < 4; i++) {
			imageList.add(Toolkit.getDefaultToolkit().getImage(
					ResourceLoader.getResource(iconFolder + icon + (16 * i) + ".png")));
		}
		frame.setIconImages(imageList);
	}
}