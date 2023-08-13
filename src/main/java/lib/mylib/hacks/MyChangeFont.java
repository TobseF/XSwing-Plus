/*
 * @version 0.0 08.09.2009
 * @author Tobse F
 */
package lib.mylib.hacks;

import de.lessvoid.nifty.effects.Falloff;
import de.lessvoid.nifty.effects.impl.ChangeFont;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.render.NiftyRenderEngine;

public class MyChangeFont extends ChangeFont {

    @Override
    public void execute(Element element, float normalizedTime, Falloff falloff,
                        NiftyRenderEngine r) {
        super.execute(element, normalizedTime, falloff, r);
        element.layoutElements();
    }

}