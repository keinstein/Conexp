/*
 * User: Serhiy Yevtushenko
 * Date: Oct 21, 2002
 * Time: 12:18:42 PM
 */

package canvas.figures;

import util.gui.ColorUtil;

import java.awt.Color;

public class ColorTransformerWithFadeOut implements ColorTransformer {

    private ColorTransformerWithFadeOut() {
    }

    private static final ColorTransformer g_Instance = new ColorTransformerWithFadeOut();

    public static ColorTransformer getInstance(){
        return g_Instance;
    }

    public Color getColor(Color selectionColor, Color normalColor, boolean isSelected, boolean hasSelection) {
        if(isSelected){
            return selectionColor;
        }
        if(hasSelection){
            return ColorUtil.fadeOut(normalColor);
        }else{
            return normalColor;
        }
    }
}
