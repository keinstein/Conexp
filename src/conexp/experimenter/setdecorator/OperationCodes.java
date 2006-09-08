/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.experimenter.setdecorator;



public interface OperationCodes {

    //queries

    int SIZE = 0;

    int ELEMENT_COUNT = SIZE + 1;
    int LENGTH = ELEMENT_COUNT + 1;
    int OUT_UPPER_BOUND = LENGTH + 1;

    int IS_EMPTY = OUT_UPPER_BOUND + 1;
    int IN = IS_EMPTY + 1;
    int OUT = IN + 1;

    int FIRST_IN = OUT + 1;
    int NEXT_IN = FIRST_IN + 1;

    int FIRST_OUT = NEXT_IN + 1;
    int NEXT_OUT = FIRST_OUT + 1;

    int INTERSECTS = NEXT_OUT + 1;
    int IS_EQUALS = INTERSECTS + 1;
    int IS_SUPERSET_OF = IS_EQUALS + 1;
    int IS_SUBSET_OF = IS_SUPERSET_OF + 1;

    int COMPARE_OPERATION = IS_SUBSET_OF + 1;
    int LEX_COMPARE_GANTER = COMPARE_OPERATION + 1;

    int IS_LESSER_THAN_PART_ORDERED = LEX_COMPARE_GANTER + 1;
    int IS_EQUAL_PART_ORDERED = IS_LESSER_THAN_PART_ORDERED + 1;


    int LAST_QUERY = IS_EQUAL_PART_ORDERED;

    //modifications
    int PUT_OPERATION = LAST_QUERY + 1;
    int REMOVE_OPERATION = PUT_OPERATION + 1;
    int AND_OPERATION = REMOVE_OPERATION + 1;
    int OR_OPERATION = AND_OPERATION + 1;
    int AND_NOT_OPERATION = OR_OPERATION + 1;
    int CLEAR_SET_OPERATION = AND_NOT_OPERATION + 1;
    int FILL_OPERATION = CLEAR_SET_OPERATION + 1;
    int COPY = FILL_OPERATION + 1;

    //size modifications
    int RESIZE_OPERATION = COPY + 1;
    int EXCLUDE_OPERATION = RESIZE_OPERATION + 1;
    int APPEND_OPERATION = EXCLUDE_OPERATION + 1;

    //java object interface
    int HASH_CODE_CALL = APPEND_OPERATION + 1;

    //copy creation
    int MAKE_MODIFIABLE_COPY = HASH_CODE_CALL + 1;
    int CLONE = MAKE_MODIFIABLE_COPY + 1;
    int MAKE_MODIFIABLE_FRAGMENT = CLONE + 1;

    int LAST_OPERATION = MAKE_MODIFIABLE_FRAGMENT;
    int OPERATION_COUNT = LAST_OPERATION + 1;
}
