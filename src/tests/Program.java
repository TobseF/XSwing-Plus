/*
 * @version 0.0 27.08.2008
 * @author 	Tobse F
 */
package tests;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Program extends BasicGame {

   
   private Image image = null;
   private Shader shader;

   public Program(String title) {
      super(title);
   }

   @Override
public void init(GameContainer container) throws SlickException {
      try {
         image = new Image("res/balls1.png");
      } catch (SlickException e) {
         e.printStackTrace();
      }
      
      shader = new Shader("data/shader/test");
   }

   @Override
public void update(GameContainer container, int delta) throws SlickException {
      
   }

   public void render(GameContainer container, Graphics g) throws SlickException {
      
      g.drawString("Fixed pipeline:", 10f, 216f);
      g.drawString("Shader:", 148f, 216f);
      
      g.drawImage(image, 10f, 236.0f);
      //g.drawImage(image, 148f, 236.0f);
      shader.render(image, 148f, 236f);
   }
   
   
   public static void main(String[] args) {
      try {
         AppGameContainer container = new AppGameContainer(new Program("Shader Test"));
         container.setDisplayMode(800, 600, false);
         container.start();
      } catch (SlickException e) {
         e.printStackTrace();
      }
   }
} 