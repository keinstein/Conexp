/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.compareutils;

import java.io.PrintWriter;
import java.util.Collection;


public class BaseComparator {
    private DiffMap map;
    private final boolean equal;
    protected String noDifferencesMessage = "No differences";

    public BaseComparator(CompareInfoFactory compareInfoFactory,
                          ICompareSet compareSetOne,
                          ICompareSet compareSetTwo) {
        super();
        map = new DiffMap(compareInfoFactory);
        equal = map.compareSets(compareSetOne, compareSetTwo);
    }

    public void setNoDifferencesMessage(String noDifferencesMessage) {
        this.noDifferencesMessage = noDifferencesMessage;
    }

    public void dumpDifferencesToSout() {
        PrintWriter writer = new PrintWriter(System.out, true);
        writeReport(writer);
    }

    public void writeReport(PrintWriter writer) {
        if (isEqual()) {
            writer.println(noDifferencesMessage);
        } else {
            map.dumpDifferences(writer);
        }
    }


    public boolean isEqual() {
        return equal;
    }

    public Collection getOnlyInFirst() {
        return map.getInFirst();
    }

    public Collection getOnlyInSecond() {
        return map.getInSecond();
    }

    public Collection getInBothButDifferent() {
        return map.getInBothButDifferent();
    }

}
