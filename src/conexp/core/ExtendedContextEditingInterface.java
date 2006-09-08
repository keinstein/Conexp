/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core;


public interface ExtendedContextEditingInterface extends ContextEditingInterfaceWithArrowRelations {
    void clarifyAttributes();

    void clarifyObjects();

    void reduceAttributes();

    void reduceObjects();

    void transpose();
}
