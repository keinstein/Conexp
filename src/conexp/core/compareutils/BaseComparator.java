/**
 * Copyright (c) 2000-2003, Sergey Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/

package conexp.core.compareutils;


public class BaseComparator {
    public final DiffMap map;
    public final boolean equal;

    public BaseComparator(CompareInfoFactory compareInfoFactory,
                          ICompareSet compareSetOne,
                          ICompareSet compareSetTwo
                          ) {
        super();
        map = new DiffMap(compareInfoFactory);
        equal = map.compareSets(compareSetOne, compareSetTwo);
    }


    public void dumpDifferencesToSout() {
        map.dumpDifferences(new java.io.PrintWriter(System.out, true));
    }

}
