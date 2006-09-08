/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core;

public interface ItemSet {
    int LESS = 0;
    int EQUAL = LESS + 1;
    int GREATER = EQUAL + 1;
    int NOT_COMPARABLE = GREATER + 1;

    int compare(ItemSet that);

    int getObjCnt();

    Set getAttribs();

    int getIndex();
}
