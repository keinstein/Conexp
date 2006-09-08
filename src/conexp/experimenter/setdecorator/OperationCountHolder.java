/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.experimenter.setdecorator;

import java.util.Arrays;



public abstract class OperationCountHolder {
    long[] operationFrequencies;

    protected OperationCountHolder(long[] operationCount) {
        this.operationFrequencies = operationCount;
    }


    public void register(int opCode) {
        operationFrequencies[opCode]++;
    }

    public long getOperationCount(int opCode) {
        return operationFrequencies[opCode];
    }

    public String toString() {
        StringBuffer result = new StringBuffer();
        fillContent(result);
        return result.toString();
    }

    protected void fillContent(StringBuffer result) {
        result.append("Operation count:\n");
        for (int i = 0; i < operationFrequencies.length; i++) {
            long frequency = operationFrequencies[i];
            if (frequency != 0) {
                result.append(opCodeToString(i));
                result.append(':');
                result.append(frequency);
                result.append('\n');
            }
        }
    }

    public int hashCode() {
        return 0;
        //todo: add when needed
        //return ArraysUtil.arrayHashCode(operationCount);
    }

    protected abstract OperationCountHolder makeCopy(long[] frequencies);

    public OperationCountHolder makeCopy() {
        long[] copy = new long[operationFrequencies.length];
        System.arraycopy(operationFrequencies, 0, copy, 0, operationFrequencies.length);
        return makeCopy(copy);
    }

    public void clear() {
        Arrays.fill(operationFrequencies, 0L);
    }

    public abstract String opCodeToString(int opCode);
}
