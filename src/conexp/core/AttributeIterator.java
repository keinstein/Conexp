package conexp.core;

import util.Assert;

public class AttributeIterator extends ContextEntityIterator {
    public AttributeIterator(ExtendedContextEditingInterface cxt, Set entities) {
        super(cxt, entities);
    }

    protected void checkConsistency() {
        Assert.isTrue(cxt.getAttributeCount() == entities.size());
    }

    protected ContextEntity getEntity(int entityIndex) {
        return cxt.getAttribute(entityIndex);
    }
}
