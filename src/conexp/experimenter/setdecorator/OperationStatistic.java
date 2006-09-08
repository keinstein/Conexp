/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.experimenter.setdecorator;

import util.Assert;

import java.util.Arrays;



public class OperationStatistic extends OperationCountHolder implements OperationCodes {

    private OperationStatistic(long[] frequencies) {
        super(frequencies);
    }

    public OperationStatistic() {
        this(new long[OPERATION_COUNT]);
    }


    protected OperationCountHolder makeCopy(long[] frequencies) {
        return new OperationStatistic(frequencies);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof OperationStatistic)) {
            return false;
        }

        final OperationStatistic operationStatistic = (OperationStatistic) obj;
        if (!Arrays.equals(operationFrequencies, operationStatistic.operationFrequencies)) {
            return false;
        }

        return true;
    }


    public String opCodeToString(int opCode) {
        switch (opCode) {
            case SIZE:
                return "SIZE";
            case ELEMENT_COUNT:
                return "ELEMENT_COUNT";
            case LENGTH:
                return "LENGTH";
            case OUT_UPPER_BOUND:
                return "OUT_UPPER_BOUND";

            case IS_EMPTY:
                return "IS_EMPTY";
            case IN:
                return "IN";
            case OUT:
                return "OUT";

            case FIRST_IN:
                return "FIRST_IN";
            case NEXT_IN:
                return "NEXT_IN";

            case FIRST_OUT:
                return "FIRST_OUT";
            case NEXT_OUT:
                return "NEXT_OUT";

            case INTERSECTS:
                return "INTERSECTS";
            case IS_EQUALS:
                return "IS_EQUALS";
            case IS_SUPERSET_OF:
                return "IS_SUPERSET_OF";
            case IS_SUBSET_OF:
                return "IS_SUBSET_OF";

            case COMPARE_OPERATION:
                return "COMPARE_OPERATION";
            case LEX_COMPARE_GANTER:
                return "LEX_COMPARE_GANTER";

            case IS_LESSER_THAN_PART_ORDERED:
                return "IS_LESSER_THAN_PART_ORDERED";
            case IS_EQUAL_PART_ORDERED:
                return "IS_EQUAL_PART_ORDERED";

                //modifications
            case PUT_OPERATION:
                return "PUT_OPERATION";
            case REMOVE_OPERATION:
                return "REMOVE_OPERATION";
            case AND_OPERATION:
                return "AND_OPERATION";
            case OR_OPERATION:
                return "OR_OPERATION";
            case AND_NOT_OPERATION:
                return "AND_NOT_OPERATION";
            case CLEAR_SET_OPERATION:
                return "CLEAR_SET_OPERATION";
            case FILL_OPERATION:
                return "FILL_OPERATION";
            case COPY:
                return "COPY";

                //size modifications
            case RESIZE_OPERATION:
                return "RESIZE_OPERATION";
            case EXCLUDE_OPERATION:
                return "EXCLUDE_OPERATION";
            case APPEND_OPERATION:
                return "APPEND_OPERATION";

                //java object interface
            case HASH_CODE_CALL:
                return "HASH_CODE_CALL";

                //copy creation
            case MAKE_MODIFIABLE_COPY:
                return "MAKE_MODIFIABLE_COPY";
            case CLONE:
                return "CLONE";
            case MAKE_MODIFIABLE_FRAGMENT:
                return "MAKE_MODIFIABLE_FRAGMENT";
            default:
                Assert.isTrue(false, "Bad operation code");
        }

        return "Bad operation code";
    }


}
