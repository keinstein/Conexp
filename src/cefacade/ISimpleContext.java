package cefacade;

/**
 * Author: Serhiy Yevtushenko
 * Date: 17.01.2003
 * Time: 17:21:57
 */
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
