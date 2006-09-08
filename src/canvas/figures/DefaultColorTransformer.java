/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package canvas.figures;

import java.awt.Color;

public class DefaultColorTransformer implements ColorTransformer {

    private static final ColorTransformer g_Instance = new DefaultColorTransformer();

    private DefaultColorTransformer() {
    }

    public static ColorTransformer getInstance() {
        return g_Instance;
    }

    public Color getColor(Color selectionColor, Color normalColor, boolean isSelected, boolean hasSelection) {
        if (isSelected) {
            return selectionColor;
        }
        return normalColor;
    }
}
