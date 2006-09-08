/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core;

import java.util.EventObject;

public class ContextChangeEvent extends EventObject {
    public final static int ATTRIBUTE_REMOVED = 1;
    public final static int ATTRIBUTE_ADDED = ATTRIBUTE_REMOVED + 1;
    public final static int OBJECT_REMOVED = ATTRIBUTE_ADDED + 1;
    public final static int OBJECT_ADDED = OBJECT_REMOVED + 1;

    private ContextChangeEvent(Object source, int type, int column) {
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

    public static ContextChangeEvent makeObjectInsertedEvent(ExtendedContextEditingInterface cxt, int objIndex) {
        return new ContextChangeEvent(cxt, OBJECT_ADDED, objIndex);
    }

    public static ContextChangeEvent makeObjectRemovedEvent(ExtendedContextEditingInterface cxt, int objIndex) {
        return new ContextChangeEvent(cxt, OBJECT_REMOVED, objIndex);
    }


    private int type;
    private int column;

    public int getType() {
        return type;
    }

    public int getColumn() {
        return column;
    }

}
