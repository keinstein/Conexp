/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.experimenter.experiments;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;



public class IntFrequencyHistogram2D {
    String name;
    Map values;

    public IntFrequencyHistogram2D(String name) {
        this.name = name;
        values = new TreeMap();
    }

    public void putValue(int one, int second) {
        values.put(toObject(one, second), new Integer(getFrequency(one, second) + 1));
    }

    private static IntPair2D toObject(int first, int second) {
        return new IntPair2D(first, second);
    }

    public int getFrequency(int first, int second) {
        Integer integer = (Integer) values.get(toObject(first, second));
        return null == integer ? 0 : integer.intValue();
    }

    public int[] getXValues() {
        ObjectIntConvertor objectIntConvertor = new ObjectIntConvertor() {
            public int objToInt(Object o) {
                return ((IntPair2D) o).getX();
            }
        };
        return getValueArray(objectIntConvertor);
    }


    public int[] getYValues() {
        ObjectIntConvertor objectIntConvertor = new ObjectIntConvertor() {
            public int objToInt(Object o) {
                return ((IntPair2D) o).getY();
            }
        };
        return getValueArray(objectIntConvertor);
    }

    private int[] getValueArray(ObjectIntConvertor objectIntConvertor) {
        Set c = values.keySet();
        int[] ret = new int[values.size()];
        int i = 0;
        for (Iterator iterator = c.iterator(); iterator.hasNext();) {
            Object o = iterator.next();
            ret[i++] = objectIntConvertor.objToInt(o);
        }
        return ret;
    }

    interface ObjectIntConvertor {
        int objToInt(Object o);
    }


    public String toString() {
        StringBuffer res = new StringBuffer();
        String separator = ";";
        res.append("FrequncyHistogram2D");
        res.append(separator);
        res.append(name);
        res.append(separator);
        String newline = "\n";
        res.append(newline);

        appendValues(res, "X", getXValues(), separator);
        res.append(newline);
        appendValues(res, "Y", getYValues(), separator);
        res.append(newline);
        res.append("Frequency");
        res.append(separator);
        for (Iterator iterator = values.keySet().iterator(); iterator.hasNext();) {
            IntPair2D pair = (IntPair2D) iterator.next();
            res.append(getFrequency(pair.getX(), pair.getY()));
            res.append(separator);
        }
        res.append(newline);
        return res.toString();
    }

    private static void appendValues(StringBuffer res, String label, int[] valuesX, String separator) {
        res.append(label);
        res.append(separator);
        for (int i = 0; i < valuesX.length; i++) {
            int value = valuesX[i];
            res.append(value);
            res.append(separator);
        }
    }
}
