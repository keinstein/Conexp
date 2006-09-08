/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core;

public interface ContextEditingInterface {
    int getAttributeCount();

    int getObjectCount();

    void setDimension(int numObj, int numAttr);

    boolean getRelationAt(int objectId, int attrId);

    void setRelationAt(int objectId, int attrId, boolean value);

    ContextEntity getObject(int index);

    ContextEntity getAttribute(int index);

    void removeAttribute(int index);

    void removeObject(int index);

    BinaryRelation getRelation();

    ContextEditingInterface makeCopy();

    void copyFrom(ContextEditingInterface source);

    void addObjectWithNameAndIntent(String objectName, Set intent);

    void addContextListener(ContextListener lst);

    void removeContextListener(ContextListener lst);
}
