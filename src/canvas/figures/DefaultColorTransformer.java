/*
 * User: Serhiy Yevtushenko
 * Date: Oct 21, 2002
 * Time: 12:16:12 PM
 */

package canvas.figures;

import java.awt.Color;

public class DefaultColorTransformer implements ColorTransformer {

    private static  final ColorTransformer g_Instance = new DefaultColorTransformer();

    private DefaultColorTransformer() {
    }

    public static ColorTransformer getInstance(){
        return g_Instance;
    }

    public Color getColor(Color selectionColor, Color normalColor, boolean isSelected, boolean hasSelection) {
        if(isSelected){
            return selectionColor;
        }
        return normalColor;
    }
}
