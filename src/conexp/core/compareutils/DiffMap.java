/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.compareutils;

import java.io.PrintWriter;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;


public class DiffMap {
    protected Map map;
    protected final CompareInfoFactory factory;
    protected Set corruptValues;
    protected Collection inFirst;
    protected Collection inSecond;
    protected Collection inBothButDifferent;

    public DiffMap(CompareInfoFactory factory) {
        super();
        this.factory = factory;
    }

    public void clear() {
        map = new HashMap();
        inFirst = null;
        inSecond = null;
        inBothButDifferent = null;
    }


    public boolean compareSets(ICompareSet one, ICompareSet two) {
        clear();
        boolean ret = true;
        if (one.getSize() != two.getSize()) {
            ret = false;
        }
        for (KeyValuePairIterator iterator = one.iterator(); iterator.hasNext();) {
            KeyValuePair pair = iterator.nextKeyValuePair();
            putFirst(pair.key, pair.value);
        }
        for (KeyValuePairIterator iterator = two.iterator(); iterator.hasNext();) {
            KeyValuePair pair = iterator.nextKeyValuePair();
            ret = putSecond(pair.key, pair.value) & ret;
        }
        ret = ret && !isCorrupt();
        if (!ret) {
            processDifferences();
        }
        return ret;
    }


    /**
     * Insert the method's description here.
     * Creation date: (13.07.01 16:53:29)
     *
     * @param writer java.io.PrintWriter
     */
    public void dumpDifferences(PrintWriter writer) {
        writer.println("COMPARATOR: Start logging ");
        if (isCorrupt()) {
            writer.println("Compared sets where corrupt and contained non unique values");
            printCollection(writer, corruptValues.iterator());
            writer.println("Finished logging non unique values");
        }
        if (null != inFirst && !inFirst.isEmpty()) {
            writer.println("===============================================");
            writer.println("Following object were only in first collection");

            Iterator iter = inFirst.iterator();
            while (iter.hasNext()) {
                writer.println(((CompareInfo) iter.next()).one);
            }
            writer.println("Finished logging First\\Second");
            writer.println("===============================================");
        }
        if (null != inSecond && !inSecond.isEmpty()) {
            writer.println("===============================================");
            writer.println("Following object were only in second collection");
            Iterator iter = inSecond.iterator();
            while (iter.hasNext()) {
                writer.println(((CompareInfo) iter.next()).two);
            }
            writer.println("Finished logging Second\\First");
            writer.println("===============================================");
        }
        if (null != inBothButDifferent && !inBothButDifferent.isEmpty()) {
            writer.println("===============================================");
            writer.println("Following object were in both collections, but they are different");
            Iterator iter = inBothButDifferent.iterator();
            while (iter.hasNext()) {
                CompareInfo comp = (CompareInfo) iter.next();
                comp.dumpDifferences(writer);
            }
            writer.println("Finished logging different objects");
        }
        writer.println("COMPARATOR: finished logging");
    }


    protected Set getCorruptValues() {
        if (null == corruptValues) {
            corruptValues = new HashSet();
        }
        return corruptValues;
    }


    public Collection getInBothButDifferent() {
        return unmodifiableCopy(getInBothButDifferentIntern());
    }

    private Collection getInBothButDifferentIntern() {
        if (null == inBothButDifferent) {
            inBothButDifferent = makeCollection();
        }
        return inBothButDifferent;
    }

    public Collection getInFirst() {
        return unmodifiableCopy(getInFirstIntern());
    }

    private Collection getInFirstIntern() {
        if (null == inFirst) {
            inFirst = makeCollection();
        }
        return inFirst;
    }


    public Collection getInSecond() {
        return unmodifiableCopy(getInSecondIntern());
    }


    private Collection getInSecondIntern() {
        if (null == inSecond) {
            inSecond = makeCollection();
        }
        return inSecond;
    }


    public boolean isCorrupt() {
        return !getCorruptValues().isEmpty();
    }


    protected void logCorruptKey(Object key) {
        getCorruptValues().add(key);
    }

    protected static Collection makeCollection() {
        return new LinkedList();
    }


    protected static Collection unmodifiableCopy(final Collection inSecondIntern) {
        return Collections.unmodifiableCollection(inSecondIntern);
    }


    protected static void printCollection(PrintWriter writer, Iterator iter) {
        while (iter.hasNext()) {
            writer.println(iter.next());
        }
    }

    protected void processDifferences() {
        Iterator iter = map.values().iterator();

        while (iter.hasNext()) {
            CompareInfo inf = (CompareInfo) iter.next();
            switch (inf.getType()) {
                case CompareInfo.IN_FIRST:
                    getInFirstIntern().add(inf);
                    break;
                case CompareInfo.IN_SECOND:
                    getInSecondIntern().add(inf);
                    break;
                case CompareInfo.IN_BOTH_BUT_DIFFERENT:
                    getInBothButDifferentIntern().add(inf);
                    break;
            }
        }
    }

    public void putFirst(Object key, Object firstValue) {
        if (null != map.put(key, factory.makeCompareInfo(key, firstValue, CompareInfo.IN_FIRST))) {
            logCorruptKey(key);
        }
    }

    public boolean putSecond(Object key, Object secondValue) {
        CompareInfo info = (CompareInfo) map.get(key);
        if (null == info) {
            map.put(key, factory.makeCompareInfo(key, secondValue, CompareInfo.IN_SECOND));
            return false;
        } else {
            if (!info.setCorresponding(secondValue)) {
                logCorruptKey(key);
                return false;
            }
            return info.compare();
        }

    }
}
