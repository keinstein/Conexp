/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.util.valuemodels;

public interface IIntValueModel extends IVetoableValueModel {
    /**
     * Insert the method's description here.
     * Creation date: (02.02.01 0:18:53)
     * @return int
     */
    int getValue();

    /**
     * Insert the method's description here.
     * Creation date: (02.02.01 0:18:53)
     * @param newValue int
     */
    void setValue(int newValue) throws java.beans.PropertyVetoException;
}
