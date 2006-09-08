/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package cefacade.implementation;

import cefacade.ISimpleContext;
import conexp.core.Context;


public class SimpleContextImplementation implements ISimpleContext {
    Context context;

    public SimpleContextImplementation(Context context) {
        this.context = context;
    }

    public int getAttributeCount() {
        return context.getAttributeCount();
    }

    public int getObjectCount() {
        return context.getObjectCount();
    }

    public String getAttributeName(int index) {
        return context.getAttribute(index).getName();
    }

    public void setAttributeName(int index, String name) {
        context.getAttribute(index).setName(name);
    }

    public String[] getAttributeName() {
        final int attributeCount = getAttributeCount();
        final String[] ret = new String[attributeCount];
        for (int i = 0; i < attributeCount; i++) {
            ret[i] = getAttributeName(i);
        }
        return ret;
    }

    public void setAttributeName(String[] newNames) {
        setDimension(getObjectCount(), newNames.length);
        for (int i = 0; i < newNames.length; i++) {
            setAttributeName(i, newNames[i]);
        }
    }

    public void setObjectName(int index, String name) {
        context.getObject(index).setName(name);
    }

    public String getObjectName(int index) {
        return context.getObject(index).getName();
    }

    public void setObjectName(String[] newNames) {
        setDimension(newNames.length, getAttributeCount());
        for (int i = 0; i < newNames.length; i++) {
            setObjectName(i, newNames[i]);
        }
    }

    public String[] getObjectName() {
        final int objectCount = getObjectCount();
        final String[] ret = new String[objectCount];
        for (int i = 0; i < objectCount; i++) {
            ret[i] = getObjectName(i);
        }
        return ret;
    }

    public void setDimension(int numObj, int numAttr) {
        context.setDimension(numObj, numAttr);
    }

    public boolean getRelationAt(int objectId, int attrId) {
        return context.getRelationAt(objectId, attrId);
    }

    public void setRelationAt(int objectId, int attrId, boolean value) {
        context.setRelationAt(objectId, attrId, value);
    }

    public ISimpleContext makeCopy() {
        return new SimpleContextImplementation(context.makeNativeCopy());
    }

    public Context getContext() {
        return context;
    }

}
