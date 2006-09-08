/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core;

import util.Assert;

public class ObjectIterator extends ContextEntityIterator {
    public ObjectIterator(ExtendedContextEditingInterface cxt, Set entities) {
        super(cxt, entities);
    }

    protected void checkConsistency() {
        Assert.isTrue(cxt.getObjectCount() == entities.size());
    }

    protected ContextEntity getEntity(int entityIndex) {
        return cxt.getObject(entityIndex);
    }
}
