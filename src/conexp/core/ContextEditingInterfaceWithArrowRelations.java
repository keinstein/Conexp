/**
 * Copyright (c) 2000-2003, Sergey Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.core;

public interface ContextEditingInterfaceWithArrowRelations extends ContextEditingInterface {

    public boolean hasDownArrow(int row, int col);

    //---------------------------------------------------------------
    public boolean hasUpArrow(int row, int col);
}
