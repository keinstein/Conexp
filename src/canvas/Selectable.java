package canvas;

/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 * Date: 14/7/2003
 * Time: 1:17:43
 */

public interface Selectable {
    boolean isSelected();
    public void setSelected(boolean newValue);
}
