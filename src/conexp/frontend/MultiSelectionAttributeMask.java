package conexp.frontend;

/**
 * Copyright (c) 2000-2003, Sergey Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 */
public interface MultiSelectionAttributeMask extends AttributeMask{
    void selectAll();
    boolean hasUnselectedAttributes();
}
