/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core;

import java.util.EventObject;

public class ContextChangeEvent extends EventObject {
    public final static int ATTRIBUTE_REMOVED = 1;
    public final static int ATTRIBUTE_ADDED = ATTRIBUTE_REMOVED + 1;

    public ContextChangeEvent(Object source, int type, int column) {
        super(source);
        this.type = type;
        this.column = column;
    }

    public static ContextChangeEvent makeAttributeInsertedEvent(ExtendedContextEditingInterface cxt, int attrIndex) {
        return new ContextChangeEvent(cxt, ATTRIBUTE_ADDED, attrIndex);
    }

    public static ContextChangeEvent makeAttributeRemovedEvent(ExtendedContextEditingInterface cxt, int index) {
        return new ContextChangeEvent(cxt, ATTRIBUTE_REMOVED, index);
    }

    int type;
    int column;

    public int getType() {
        return type;
    }

    public int getColumn() {
        return column;
    }

}
