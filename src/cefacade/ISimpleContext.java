/**
 * Copyright (c) 2000-2003, Sergey Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/

package cefacade;


public interface ISimpleContext {
    int getAttributeCount();

    int getObjectCount();

    String getAttributeName(int index);

    String[] getAttributeName();

    void setAttributeName(int index, String name);

    void setAttributeName(String[] newNames);

    void setObjectName(int index, String name);

    String getObjectName(int index);

    void setObjectName(String[] newNames);

    String[] getObjectName();

    public void setDimension(int numObj, int numAttr);

    boolean getRelationAt(int objectId, int attrId);

    void setRelationAt(int objectId, int attrId, boolean value);

    ISimpleContext makeCopy();
}
