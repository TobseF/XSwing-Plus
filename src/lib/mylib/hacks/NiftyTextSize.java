/*
 * @version 0.0 06.09.2009
 * @author 	Tobse F
 */
package lib.mylib.hacks;

import java.util.Properties;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.effects.EffectImpl;
import de.lessvoid.nifty.effects.Falloff;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.render.NiftyRenderEngine;
import de.lessvoid.nifty.tools.SizeValue;

/**
 * TextSize effect.
 * @author void
 */
public class NiftyTextSize implements EffectImpl {

  private float startSize;
  private float endSize;
  private SizeValue textSize = new SizeValue("100%");

  public void activate(final Nifty nifty, final Element element, final Properties parameter) {
    startSize = Float.parseFloat(parameter.getProperty("startSize", "1.0"));
    endSize = Float.parseFloat(parameter.getProperty("endSize", "2.0"));

    // hover mode only
    String maxSizeString = parameter.getProperty("maxSize");
    if (maxSizeString != null) {
      textSize = new SizeValue(maxSizeString);
    }
  }

  public void execute(
      final Element element,
      final float normalizedTime,
      final Falloff falloff,
      final NiftyRenderEngine r) {
 /* Hack: kein Folloff hover mehr  
    } else {
	  float scale = 1.0f + falloff.getFalloffValue() * textSize.getValue(1.0f);
	  r.setRenderTextSize(scale);
     if (falloff == null) { */ 
    	  float t = normalizedTime;
    	  r.setRenderTextSize(startSize + t * (endSize - startSize));
    //}
  }

  public void deactivate() {
  }
}
