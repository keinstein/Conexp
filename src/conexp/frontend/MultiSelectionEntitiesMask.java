/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.frontend;


public interface MultiSelectionEntitiesMask extends EntitiesMask {
    void selectAll();

    boolean hasUnselected();
}
