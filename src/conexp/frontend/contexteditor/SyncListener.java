package conexp.frontend.contexteditor;

import conexp.core.DefaultContextListener;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 * Date: 16/12/2003
 * Time: 1:34:37
 */
abstract class SyncListener extends DefaultContextListener implements TableModelListener {
    public SyncListener() {
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

    protected boolean reactOnInsertDeleteOrContextStructureChange(TableModelEvent evt) {
        return evt.getType() != TableModelEvent.UPDATE ||
                                    evt.getFirstRow() == TableModelEvent.HEADER_ROW;
    }
}
