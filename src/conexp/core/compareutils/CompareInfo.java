/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.compareutils;

import util.Assert;

import java.io.PrintWriter;


public class CompareInfo {
    public final static int IN_FIRST = 1;
    public final static int IN_SECOND = IN_FIRST + 1;
    public final static int IN_BOTH = IN_SECOND + 1;
    public final static int IN_BOTH_BUT_DIFFERENT = IN_BOTH + 1;
    private int type;

    protected Object key;
    protected Object one;
    protected Object two;

    protected boolean corrupt;

    protected int getType() {
        return type;
    }

    protected void makeInBothDifferent() {
        Assert.isTrue(IN_BOTH == type);
        type = IN_BOTH_BUT_DIFFERENT;
    }


    protected CompareInfo(Object key, Object element, int type) {
        this.key = key;
        this.type = type;
        switch (type) {
            case IN_FIRST:
                one = element;
                break;
            case IN_SECOND:
                two = element;
                break;
            default:
                Assert.isTrue(false);
                break;
        }
    }

    //todo: think about more clear compare model
    public boolean compare() {
        if (!isValid()) {
            return false;
        }
        final boolean result = doCompareElements();
        if (type == IN_BOTH) {
            if (!result) {
                makeInBothDifferent();
            }
        }
        return result;
    }

    protected boolean doCompareElements() {
        return one.equals(two);
    }


    /**
     * Insert the method's description here.
     * Creation date: (13.07.01 17:19:35)
     *
     * @param writer java.io.PrintWriter
     */
    protected void doDumpDifferencesForInBoth(PrintWriter writer) {
        writer.println("For key :" + key);
        writer.println("In first was: " + one);
        writer.println("In second was: " + two);
    }


    /**
     * Insert the method's description here.
     * Creation date: (13.07.01 16:51:13)
     *
     * @param writer java.io.PrintWriter
     */
    public void dumpDifferences(PrintWriter writer) {
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
            if (null == two) {
                Assert.isTrue(type == IN_FIRST);
                two = second;
                type = IN_BOTH;
                return true;
            } else {
                return false;
            }
        }
    }
}
