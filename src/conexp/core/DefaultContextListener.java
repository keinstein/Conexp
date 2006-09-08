/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core;

import java.beans.PropertyChangeEvent;

public class DefaultContextListener implements ContextListener {
    public void contextStructureChanged() {
    }

    public void contextTransposed() {
    }

    public void relationChanged() {
    }

    public void objectNameChanged(PropertyChangeEvent evt) {
    }

    public void attributeNameChanged(PropertyChangeEvent evt) {
    }

    public void attributeChanged(ContextChangeEvent changeEvent) {
    }

    public void objectChanged(ContextChangeEvent changeEvent) {
    }
}
