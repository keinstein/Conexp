/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.core.compareutils;


public class BaseComparator {
    public final DiffMap map;
    private final boolean equal;
    protected String noDifferencesMessage = "No differences";

    public BaseComparator(CompareInfoFactory compareInfoFactory,
                          ICompareSet compareSetOne,
                          ICompareSet compareSetTwo
                          ) {
        super();
        map = new DiffMap(compareInfoFactory);
        equal = map.compareSets(compareSetOne, compareSetTwo);
    }

    public void setNoDifferencesMessage(String noDifferencesMessage) {
        this.noDifferencesMessage = noDifferencesMessage;
    }

    public void dumpDifferencesToSout() {
        java.io.PrintWriter writer = new java.io.PrintWriter(System.out, true);
        writeReport(writer);
    }

    public void writeReport(java.io.PrintWriter writer) {
        if(isEqual()){
            writer.println(noDifferencesMessage);
        }else{
            map.dumpDifferences(writer);
        }
    }


    public boolean isEqual() {
        return equal;
    }

}
