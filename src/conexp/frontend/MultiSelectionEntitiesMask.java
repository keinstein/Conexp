package conexp.frontend;

/**
 * Copyright (c) 2000-2003, Sergey Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 */
public interface MultiSelectionEntitiesMask extends EntitiesMask{
    void selectAll();
    boolean hasUnselected();
}
