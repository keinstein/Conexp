/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.core;


public interface ExtendedContextEditingInterface extends ContextEditingInterfaceWithArrowRelations {
    void purifyAttributes();

    void purifyObjects();

    void reduceAttributes();

    void reduceObjects();

    void transpose();
}
