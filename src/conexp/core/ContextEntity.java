/**
 * Copyright (c) 2000-2003, Sergey Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/

package conexp.core;

import util.StringUtil;

import java.beans.PropertyChangeEvent;


public class ContextEntity {
    String name;
    ContextEntityListener listener;

    public void setContextEntityListener(ContextEntityListener listener) {
        this.listener = listener;
    }

    boolean obj = false;

    public String getName() {
        return name;
    }

    public boolean isObject() {
        return obj;
    }

    public void setName(String newName) {
        newName = StringUtil.safeTrim(newName);
        if ((this.name == null) ||
                (!this.name.equals(newName))) {
            String oldValue = this.name;
            this.name = newName;
            if (null != listener) {
                listener.nameChanged(new PropertyChangeEvent(this, obj ? "CONTEXT_OBJECT_NAME" : "CONTEXT_ATTRIBUTE_NAME", oldValue, this.name));
            }
        }
    }


    private ContextEntity(String name, boolean obj) {
        setName(name);
        this.obj = obj;
    }


    public void makeAttrib() {
        obj = false;
    }


    public void makeObject() {
        obj = true;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof ContextEntity)) {
            return false;
        }
        ContextEntity that = (ContextEntity) obj;
        if (!(this.getName().equals(that.getName()))) {
            return false;
        }

        if (this.isObject() != that.isObject()) {
            return false;
        }
        return true;
    }

    public String toString() {
        return (isObject() ? "Object " : "Attribute ") + getName();
    }

    public static ContextEntity createContextObject(String name) {
        return new ContextEntity(name, true);
    }

    public static ContextEntity createContextAttribute(String name) {
        return new ContextEntity(name, false);
    }
}
