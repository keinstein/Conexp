/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package canvas.figures;

import util.gui.ColorUtil;

import java.awt.Color;

public class ColorTransformerWithFadeOut implements ColorTransformer {

    private ColorTransformerWithFadeOut() {
    }

    private static final ColorTransformer g_Instance = new ColorTransformerWithFadeOut();

    public static ColorTransformer getInstance() {
        return g_Instance;
    }

    public Color getColor(Color selectionColor, Color normalColor, boolean isSelected, boolean hasSelection) {
        if (isSelected) {
            return selectionColor;
        }
        if (hasSelection) {
            return ColorUtil.fadeOut(normalColor);
        } else {
            return normalColor;
        }
    }
}
