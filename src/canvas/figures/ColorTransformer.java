/**
 * Copyright (c) 2000-2003, Sergey Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package canvas.figures;

import java.awt.*;

public interface ColorTransformer {
    Color getColor(Color selectionColor, Color normalColor, boolean isSelected, boolean hasSelection);
}
