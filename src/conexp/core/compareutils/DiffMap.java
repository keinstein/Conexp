package conexp.core.compareutils;

import java.util.*;

/**
 * Creation date: (13.07.01 16:30:45)
 * @author sergey yevtushenko
 */
public class DiffMap {
    protected Map map;
    protected final CompareInfoFactory factory;
    protected java.util.HashSet corruptValues;
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

        for (int i = one.getSize(); --i >= 0;) {
            KeyValuePair pair = one.get(i);
            putFirst(pair.key, pair.value);
        }
        for (int j = two.getSize(); --j >= 0;) {
            KeyValuePair pair = two.get(j);
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
     * @param writer java.io.PrintWriter
     */
    public void dumpDifferences(java.io.PrintWriter writer) {
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


    protected HashSet getCorruptValues() {
        if (null == corruptValues) {
            corruptValues = new HashSet();
        }
        return corruptValues;
    }


    public java.util.Collection getInBothButDifferent() {
        if (null == inBothButDifferent) {
            inBothButDifferent = makeCollection();
        }
        return inBothButDifferent;
    }

    public java.util.Collection getInFirst() {
        if (null == inFirst) {
            inFirst = makeCollection();
        }
        return inFirst;
    }


    public java.util.Collection getInSecond() {
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
        return new java.util.LinkedList();
    }


    protected static void printCollection(java.io.PrintWriter writer, Iterator iter) {
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
                    getInFirst().add(inf);
                    break;
                case CompareInfo.IN_SECOND:
                    getInSecond().add(inf);
                    break;
                case CompareInfo.IN_BOTH_BUT_DIFFERENT:
                    getInBothButDifferent().add(inf);
                    break;
            }
        }
    }

    public void putFirst(Object key, Object firstValue) {
        if (null != map.put(key, factory.makeCompareInfo(firstValue, CompareInfo.IN_FIRST))) {
            logCorruptKey(key);
        }
    }

    public boolean putSecond(Object key, Object secondValue) {
        CompareInfo info = (CompareInfo) map.get(key);
        if (null == info) {
            map.put(key, new CompareInfo(secondValue, CompareInfo.IN_SECOND));
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