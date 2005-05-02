/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.core;

public interface ContextEditingInterfaceWithArrowRelations extends ContextEditingInterface {

    boolean hasDownArrow(int row, int col);

    //---------------------------------------------------------------
    boolean hasUpArrow(int row, int col);
}
