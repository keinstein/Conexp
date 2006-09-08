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



public class IntFrequencyHistogram {
    String name;
    Map values;

    public IntFrequencyHistogram(String name) {
        this.name = name;
        values = new TreeMap();
    }

    public void putValue(int value) {
        values.put(toObject(value), toObject(getFrequency(value) + 1));
    }

    private static Integer toObject(int value) {
        return new Integer(value);
    }

    public int getFrequency(int value) {
        Integer integer = (Integer) values.get(toObject(value));
        return null == integer ? 0 : integer.intValue();
    }

    public int[] getValues() {
        Set c = values.keySet();
        int[] ret = new int[values.size()];
        int i = 0;
        for (Iterator iterator = c.iterator(); iterator.hasNext();) {
            ret[i++] = ((Integer) iterator.next()).intValue();

        }
        return ret;
    }

    public String toString() {
        StringBuffer res = new StringBuffer();
        String separator = ";";
        res.append("FrequncyHistogram");
        res.append(separator);
        res.append(name);
        res.append(separator);
        String newline = "\n";
        res.append(newline);
        int[] values = getValues();

        res.append("Value");
        res.append(separator);
        for (int i = 0; i < values.length; i++) {
            int value = values[i];
            res.append(value);
            res.append(separator);
        }

        res.append(newline);
        res.append("Frequency");
        res.append(separator);
        for (int i = 0; i < values.length; i++) {
            int value = values[i];
            res.append(getFrequency(value));
            res.append(separator);
        }
        res.append(newline);


        return res.toString();
    }
}
