/*
 * User: sergey
 * Date: Dec 13, 2001
 * Time: 7:22:12 PM
 */
package conexp.core;

public interface ContextEditingInterface {
    int getAttributeCount();

    int getObjectCount();

    public void setDimension(int numObj, int numAttr);

    boolean getRelationAt(int objectId, int attrId);

    void setRelationAt(int objectId, int attrId, boolean value);

    ContextEntity getObject(int index);

    ContextEntity getAttribute(int index);

    public void removeAttribute(int index);

    public void removeObject(int index);

    BinaryRelation getRelation();

    ContextEditingInterface makeCopy();

    void copyFrom(ContextEditingInterface source);

    void addObjectWithNameAndIntent(String objectName, Set intent);

    void addContextListener(ContextListener lst);

    void removeContextListener(ContextListener lst);
}
