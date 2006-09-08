/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.frontend.contexteditor;

import conexp.core.DefaultContextListener;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;


abstract class SyncListener extends DefaultContextListener implements TableModelListener {
    protected SyncListener() {
    }

    abstract void doSync();

    public void contextStructureChanged() {
        doSync();
    }

    public void tableChanged(TableModelEvent evt) {
        if (reactOnEvent(evt)) {
            doSync();
        }
    }

    protected boolean reactOnEvent(TableModelEvent evt) {
        return reactOnInsertDeleteOrContextStructureChange(evt);
    }

    protected static boolean reactOnInsertDeleteOrContextStructureChange(TableModelEvent evt) {
        return evt.getType() != TableModelEvent.UPDATE ||
                evt.getFirstRow() == TableModelEvent.HEADER_ROW;
    }
}
