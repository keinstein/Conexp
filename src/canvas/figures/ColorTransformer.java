/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package canvas.figures;

import java.awt.Color;

public interface ColorTransformer {
    Color getColor(Color selectionColor, Color normalColor, boolean isSelected, boolean hasSelection);
}
