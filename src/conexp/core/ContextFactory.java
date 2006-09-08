/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core;

public interface ContextFactory {
    ModifiableBinaryRelation createRelation(int rowCount, int colCount);

    ModifiableSet createSet(int size);
}
