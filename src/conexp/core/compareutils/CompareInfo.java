/**
 * Copyright (c) 2000-2003, Sergey Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/

package conexp.core.compareutils;


public class CompareInfo {
    public final static int IN_FIRST = 1;
    public final static int IN_SECOND = IN_FIRST + 1;
    public final static int IN_BOTH = IN_SECOND + 1;
    public final static int IN_BOTH_BUT_DIFFERENT = IN_BOTH + 1;
    private int type;

    protected Object one;
    protected Object two;

    protected boolean corrupt;

    protected int getType() {
        return type;
    }

    protected void makeInBothDifferent() {
        util.Assert.isTrue(IN_BOTH == type);
        type = IN_BOTH_BUT_DIFFERENT;
    }


    protected CompareInfo(Object el, int t) {
        type = t;
        switch (type) {
            case IN_FIRST:
                one = el;
                break;
            case IN_SECOND:
                two = el;
                break;
            default:
                util.Assert.isTrue(false);
                break;
        }
    }

    public boolean compare() {
        if (!isValid()) {
            return false;
        }
        return doCompareElements();
    }

    protected boolean doCompareElements() {
        return one.equals(two);
    }


    /**
     * Insert the method's description here.
     * Creation date: (13.07.01 17:19:35)
     * @param writer java.io.PrintWriter
     */
    protected void doDumpDifferencesForInBoth(java.io.PrintWriter writer) {
    }


    /**
     * Insert the method's description here.
     * Creation date: (13.07.01 16:51:13)
     * @param writer java.io.PrintWriter
     */
    public void dumpDifferences(java.io.PrintWriter writer) {
        switch (getType()) {
            case IN_FIRST:
                writer.println("Object " + one + "is only in first set");
                break;
            case IN_SECOND:
                writer.println("Object " + two + "is only in second set");
                break;
            case IN_BOTH_BUT_DIFFERENT:
                doDumpDifferencesForInBoth(writer);
                break;
        }
    }


    /**
     * Insert the method's description here.
     * Creation date: (13.07.01 17:14:55)
     */
    protected boolean isValid() {
        return !(one == null || two == null);
    }


    public boolean setCorresponding(Object second) {
        if (null == one) {
            return false;
        } else {
            two = second;
            type = IN_BOTH;
            return true;
        }
    }
}
