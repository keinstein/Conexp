/**
 * Copyright (c) 2000-2003, Sergey Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/

package conexp.core;


public class ContextFactoryRegistry {
    private static ContextFactory factory;

    /**
     *  Description of the Method
     *
     *@param  rowCount  Number of rows in relation
     *@param  colCount  Number of columns in relation
     *@return        new  BinaryRelation
     */
    public static ModifiableBinaryRelation createRelation(int rowCount, int colCount) {
        return getFactory().createRelation(rowCount, colCount);
    }

    /**
     *  Description of the Method
     *
     *@param  size  maximum number of attributes in set
     *@return       new Set
     */
    public static ModifiableSet createSet(int size) {
        return getFactory().createSet(size);
    }

    /**
     * Insert the method's description here.
     * Creation date: (07.03.01 21:11:13)
     * @return conexp.core.ContextFactory
     */
    private synchronized static ContextFactory getFactory() {
        if (null == factory) {
            factory = new conexp.core.bitset.BitSetFactory();
        }
        return factory;
    }

}
