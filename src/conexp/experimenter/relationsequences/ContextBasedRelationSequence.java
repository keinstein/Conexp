/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.experimenter.relationsequences;

import conexp.core.BinaryRelation;
import conexp.core.Context;
import util.Assert;



public class ContextBasedRelationSequence extends BaseRelationGenerationStrategy {
    protected Context context;

    public ContextBasedRelationSequence(int count) {
        super(count);
    }

    protected void setContext(final Context context) {
        this.context = context;
    }

    public ContextBasedRelationSequence(Context context) {
        super(1);
        this.context = context;
        createRelations();
    }

    protected Context getContext() {
        return context;
    }

    public String describeStrategy() {
        return "From context";
    }

    public BinaryRelation makeRelation(int relNo) {
        Assert.isTrue(relNo <= count);
        return context.getRelation();
    }
}
