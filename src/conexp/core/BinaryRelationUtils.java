/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core;

import util.Assert;
import util.DoubleUtil;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


public class BinaryRelationUtils {
    private BinaryRelationUtils() {
    }

    /**
     * Insert the method's description here.
     * Creation date: (08.07.01 2:31:15)
     *
     * @return java.lang.String
     */
    public static String describeRelation(BinaryRelation rel) {
        StringBuffer ret = new StringBuffer();
        ret.append("Rows;");
        ret.append(rel.getRowCount());
        ret.append(";Cols;");
        ret.append(rel.getColCount());
        int cnt = calculateFilledCells(rel);
        ret.append(";Filled cells;");
        ret.append(cnt);
        ret.append(';');
        return ret.toString();
    }


    public static int calculateFilledCells(BinaryRelation rel) {
        int cnt = 0;
        for (int i = rel.getRowCount(); --i >= 0;) {
            for (int j = rel.getColCount(); --j >= 0;) {
                if (rel.getRelationAt(i, j)) {
                    cnt++;
                }
            }
        }
        return cnt;
    }

    public static double averageNumberOfAttributesPerObject(BinaryRelation rel) {
        int[] frequencies = attributePerObjectFrequencies(rel);
        return average(frequencies);
    }

    public static int[] attributePerObjectFrequencies(BinaryRelation rel) {
        int rowCount = rel.getRowCount();
        int[] frequencies = new int[rowCount];
        for (int i = 0; i < rowCount; i++) {
            frequencies[i] = rel.getSet(i).elementCount();
        }
        return frequencies;
    }

    public static boolean isSquare(BinaryRelation rel) {
        return rel.getRowCount() == rel.getColCount();
    }


    public static double varianceOfAttributesPerObjects(BinaryRelation rel) {
        return variance(attributePerObjectFrequencies(rel));
    }

    public static double average(int[] frequencies) {
        if (0 == frequencies.length) {
            return 0;
        }
        long sum = sum(frequencies);
        return DoubleUtil.getRate(sum, frequencies.length);
    }

    public static double averageNumberOfObjectsPerAttribute(BinaryRelation rel) {
        return average(attributeFrequencies(rel));
    }

    public static int[] attributeFrequencies(BinaryRelation rel) {
        int colCount = rel.getColCount();
        int[] frequencies = new int[colCount];
        int rowCount = rel.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            Set set = rel.getSet(i);
            for (int j = set.firstIn(); j != Set.NOT_IN_SET; j = set.nextIn(j)) {
                frequencies[j]++;
            }
        }
        return frequencies;
    }

    /**
     * side effect - list became sorted
     */
    public static double quantile(int[] elements, double phi) {
        Arrays.sort(elements);
        return quantileFromSorted(elements, phi);
    }

    public static double quantileFromSorted(int[] sortedElements, double phi) {
        if (phi < 0 || phi > 1) {
            throw new IllegalArgumentException("Incorrect parameter value in quantile");
        }
        int n = sortedElements.length;

        double index = phi * (n - 1);
        int lhs = (int) index;
        double delta = index - lhs;
        double result;

        if (n == 0) {
            return 0.0;
        }

        if (lhs == n - 1) {
            result = sortedElements[lhs];
        } else {
            result = (1 - delta) * sortedElements[lhs] + delta * sortedElements[lhs + 1];
        }

        return result;
    }

    public static double varianceOfObjectPerAttribute(BinaryRelation rel) {
        int[] frequencies = attributeFrequencies(rel);
        return variance(frequencies);
    }

    public static int min(int[] array) {
        if (array.length == 0) {
            throw new IllegalArgumentException("Min has no value for empty array");
        }
        int last = array.length - 1;
        int min = array[last];
        for (int i = last; --i >= 0;) {
            int value = array[i];
            if (value < min) {
                min = value;
            }
        }
        return min;
    }

    public static int max(int[] array) {
        if (array.length == 0) {
            throw new IllegalArgumentException("Max has no value for empty array");
        }
        int last = array.length - 1;
        int max = array[last];
        for (int i = last; --i >= 0;) {
            int value = array[i];
            if (value > max) {
                max = value;
            }
        }
        return max;
    }


    public static double variance(int[] frequencies) {
        if (0 == frequencies.length) {
            return 0;
        }
        double average = average(frequencies);
        double sum = 0;
        for (int i = frequencies.length; --i >= 0;) {
            double deviation = frequencies[i] - average;
            sum += deviation * deviation;
        }
        return sum / frequencies.length;
    }


    private static long sum(int[] frequencies) {
        long sum = 0;
        for (int i = 0; i < frequencies.length; i++) {
            sum += frequencies[i];
        }
        return sum;
    }


    /**
     * Insert the method's description here.
     * Creation date: (04.08.01 6:54:31)
     *
     * @param rel conexp.core.BinaryRelation
     * @return conexp.core.BinaryRelation
     */
    public static BinaryRelation lexSort(BinaryRelation rel) {
        List zeros = new LinkedList();
        List ones = new LinkedList();

        LinkedList work = new LinkedList();
        for (int i = rel.getRowCount(); --i >= 0;) {
            work.add(rel.getSet(i));
        }
        for (int j = rel.getColCount(); --j >= 0;) {
            zeros.clear();
            ones.clear();
            for (int i = rel.getRowCount(); --i >= 0;) {
                Set s = (Set) work.removeFirst();
                if (s.in(j)) {
                    ones.add(s);
                } else {
                    zeros.add(s);
                }
            }
            work.addAll(zeros);
            work.addAll(ones);
        }
        ModifiableBinaryRelation ret = ContextFactoryRegistry.createRelation(rel.getRowCount(), rel.getColCount());

        for (int i = 0; i < ret.getRowCount(); i++) {
            Set s = (Set) work.removeFirst();
            ret.getModifiableSet(i).copy(s);
        }
        return ret;
    }


    /**
     * Insert the method's description here.
     * Creation date: (14.07.01 7:42:59)
     *
     * @param rel conexp.core.BinaryRelation
     * @param pw  java.io.PrintWriter
     */
    public static void logRelation(BinaryRelation rel, PrintWriter pw) {
        pw.println("===============================================");
        pw.println(" Rows " + rel.getRowCount());
        pw.println(" Cols " + rel.getColCount());
        for (int i = 0; i < rel.getRowCount(); i++) {
            pw.println(rel.getSet(i));
        }
        pw.println("===============================================");
    }


    /**
     * Insert the method's description here.
     * Creation date: (09.03.01 23:31:56)
     *
     * @param rel conexp.core.BinaryRelation
     */
    public static void makeSymmetric(ModifiableBinaryRelation rel) {
        if (rel.getRowCount() != rel.getColCount()) {
            throw new IllegalArgumentException("make Symmetric works only with square relations");
        }
        final int size = rel.getRowCount();
        for (int i = size; --i >= 0;) {
            for (int j = size; --j >= 0;) {
                if (rel.getRelationAt(i, j)) {
                    rel.setRelationAt(j, i, true);
                }
            }
        }
    }


    /**
     * Insert the method's description here.
     * Creation date: (28.07.01 23:41:57)
     */
    public static ModifiableBinaryRelation makeTransposedRelation(BinaryRelation rel) {
        ModifiableBinaryRelation newRel = ContextFactoryRegistry.createRelation(rel.getColCount(), rel.getRowCount());
        for (int i = rel.getColCount(); --i >= 0;) {
            for (int j = rel.getRowCount(); --j >= 0;) {
                newRel.setRelationAt(i, j, rel.getRelationAt(j, i));
            }
        }
        return newRel;
    }


    /**
     * Insert the method's description here.
     * Creation date: (09.03.01 23:32:28)
     *
     * @param rel conexp.core.BinaryRelation
     */
    public static void transitiveClosure(ModifiableBinaryRelation rel) {
        if (!BinaryRelationUtils.isSquare(rel)) {
            throw new IllegalArgumentException("transitiveClosure can be applyied only to square relations");
        }

        final int size = rel.getRowCount();
        for (int i = 0; i < size; i++) {
            final Set currSet = rel.getSet(i);
            for (int j = 0; j < size; j++) {
                if (i != j && rel.getRelationAt(j, i)) {
                    rel.getModifiableSet(j).or(currSet);
                }
            }
        }
    }

    public static BinaryRelation calcAttributesOrder(BinaryRelation relation) {
        final int size = relation.getColCount();

        ModifiableBinaryRelation ret = ContextFactoryRegistry.createRelation(size, size);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                ret.setRelationAt(i, j, isAttributeSubsetOf(relation, i, j));
            }
        }
        return ret;
    }

    protected static boolean isAttributeSubsetOf(BinaryRelation rel, int attr1, int attr2) {
        for (int k = rel.getRowCount(); --k >= 0;) {
            if (rel.getRelationAt(k, attr1) && !rel.getRelationAt(k, attr2)) {
                return false;
            }
        }
        return true;
    }


    public static ModifiableSet objectContingentOfAttributeSet(BinaryRelation relation, Set attribsSubset) {
        final int rowCount = relation.getRowCount();
        ModifiableSet objectDerivation = ContextFactoryRegistry.createSet(rowCount);
        for (int i = 0; i < rowCount; i++) {
            if (attribsSubset.isEquals(relation.getSet(i))) {
                objectDerivation.put(i);
            }
        }
        return objectDerivation;
    }

    public static ModifiableSet derivationOfAttributeSet(BinaryRelation relation, Set attribsSubset) {
        final int rowCount = relation.getRowCount();
        ModifiableSet objectDerivation = ContextFactoryRegistry.createSet(rowCount);
        for (int i = 0; i < rowCount; i++) {
            if (attribsSubset.isSubsetOf(relation.getSet(i))) {
                objectDerivation.put(i);
            }
        }
        return objectDerivation;
    }

    public static ModifiableSet derivationOfObjectsSet(BinaryRelation relation, Set objectSubset) {
        final int attrCount = relation.getColCount();
        ModifiableSet attribsClosure = ContextFactoryRegistry.createSet(attrCount);
        attribsClosure.fill();
        for (int i = objectSubset.firstIn(); i != Set.NOT_IN_SET; i = objectSubset.nextIn(i)) {
            attribsClosure.and(relation.getSet(i));
        }
        return attribsClosure;
    }

    public static int indexOfSet(BinaryRelation rel, Set temp) {
        for (int k = 0; k < rel.getRowCount(); k++) {
            if (temp.equals(rel.getSet(k))) {
                return k;
            }
        }
        return -1;
    }

    public static ModifiableSet closureOfAttributeSet(BinaryRelation relation, Set attributeSetToClose) {
        int colCount = relation.getColCount();
        ModifiableSet tempClosure = ContextFactoryRegistry.createSet(colCount);
        tempClosure.fill();
        int rowCount = relation.getRowCount();
        for (int k = 0; k < rowCount; k++) {
            Set set = relation.getSet(k);
            if (set.isSupersetOf(attributeSetToClose)) {
                tempClosure.and(set);
            }
        }
        return tempClosure;
    }

    public static int minAttrCountPerObject(BinaryRelation relation) {
        return min(attributePerObjectFrequencies(relation));
    }

    public static int maxAttributePerObject(BinaryRelation relation) {
        return max(attributePerObjectFrequencies(relation));
    }

    public static int minObjCountPerAttribute(BinaryRelation relation) {
        return min(attributeFrequencies(relation));
    }

    public static int maxObjCountPerAttribute(BinaryRelation relation) {
        return max(attributeFrequencies(relation));
    }

    public static BinaryRelation createSlice(BinaryRelation baseRelation, int startIndex, int endIndex) {
        Assert.isTrue(startIndex <= endIndex);
        ModifiableBinaryRelation relation = ContextFactoryRegistry.createRelation(endIndex - startIndex + 1, baseRelation.getColCount());
        for (int i = startIndex; i <= endIndex; i++) {
            relation.getModifiableSet(i).copy(baseRelation.getSet(i));
        }
        return relation;
    }

    public static boolean haveNoBidirectionalEdges(BinaryRelation lessThanRelation) {
        Assert.isTrue(isSquare(lessThanRelation));
        final int size = lessThanRelation.getRowCount();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (lessThanRelation.getRelationAt(i, j) && lessThanRelation.getRelationAt(j, i)) {
                    return false;
                }
            }
        }
        return true;
    }
}
