/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.attrexplorationimpl;

import conexp.core.AttributeExplorationCallback;
import conexp.core.AttributeExplorer;
import conexp.core.Context;
import conexp.core.ContextEntity;
import conexp.core.ContextFactoryRegistry;
import conexp.core.Implication;
import conexp.core.ImplicationSet;
import conexp.core.ModifiableSet;
import conexp.core.Set;
import conexp.core.calculationstrategies.NextClosedSetImplicationCalculator;
import util.Assert;

public class AttributeExplorerImplementation implements AttributeExplorer {
    Context context;
    ImplicationSet implicationSet;
    AttributeExplorerUserCallback userCallback;

    public void setUserCallback(AttributeExplorerUserCallback userCallback) {
        this.userCallback = userCallback;
    }

    ContextModificationCallback contextModificationCallback = new DefaultContextModificationCallback();

    public void setContextModificationCallback(AttributeExplorer.ContextModificationCallback contextModificationCallback) {
        this.contextModificationCallback = contextModificationCallback;
    }

    NextClosedSetImplicationCalculator calculator;

    public void setContext(Context context) {
        this.context = context;
    }

    public void setImplicationSet(ImplicationSet implicationSet) {
        this.implicationSet = implicationSet;
    }

    AttributeExplorationCallback attributeExplorationCallback = new AttributeExplorationCallback() {

        ImplicationSet violatedImplications;

        public ImplicationSet getViolatedImplications() {
            if (null == violatedImplications) {
                violatedImplications = new ImplicationSet(context);
            }
            return violatedImplications;
        }

        public int acceptImplication(Set premise, Set conclusion)/*throws cancel exception*/ {
            int result = userCallback.isTrue(premise, conclusion);
            switch (result) {
                case AttributeExplorerUserCallback.ACCEPT_IMPLICATION:
                    return AttributeExplorationCallback.ACCEPT;
                case AttributeExplorerUserCallback.STOP:
                    return AttributeExplorationCallback.STOP;
                case AttributeExplorerUserCallback.REJECT_IMPLICATION:
                    // continue processing
                    break;
                default:
                    Assert.isTrue(false, "Unexpected return code from user callback");

            }

            return getContrExample(premise, conclusion);

        }

        private int getContrExample(final Set premise, final Set conclusion) {

            int result;
            ContextEntity tempObject = ContextEntity.createContextObject(context.makeUniqueObjectName());
            counterExample.copy(premise);
            for (; ;) {
                result = userCallback.queryContrExample(tempObject, counterExample);
                switch (result) {
                    case AttributeExplorerUserCallback.ACCEPT_IMPLICATION:
                        return AttributeExplorationCallback.ACCEPT;
                    case AttributeExplorerUserCallback.STOP:
                        return AttributeExplorationCallback.STOP;
                    case AttributeExplorerUserCallback.HAS_CONTREXAMPLE:
                        break;
                    default:
                        Assert.isTrue(false, "Unexpected return code from user callback");
                }
                //check validness of contrexample
                if (!premise.isSubsetOf(counterExample)) {
                    userCallback.error(new CounterExampleDoesntContainPremiseError(context, tempObject.getName(), premise, counterExample));
                    continue;
                }

                Assert.isTrue(premise.isSubsetOf(counterExample));
                if (conclusion.isSubsetOf(counterExample)) {
                    userCallback.error(new BadCounterExampleError(context, tempObject.getName(), conclusion));
                    continue;

                }
                //check not violation of implication
                if (!implicationSet.isRespectedBySet(counterExample)) {
                    getViolatedImplications().clear();

                    implicationSet.forEach(new ImplicationSet.ImplicationProcessor() {
                        public void processImplication(Implication implication) {
                            if (!implication.isRespectedBySet(counterExample)) {
                                getViolatedImplications().addImplication(implication);
                            }
                        }
                    });
                    userCallback.error(new ErrorCounterExampleViolatesAcceptedImplications(context, tempObject.getName(), counterExample, getViolatedImplications()));
                    continue;
                }

                break;
            }
            int oldObjectCount = context.getObjectCount();
            addNewObjectToContext(tempObject.getName(), counterExample);

            Assert.isTrue(context.getObjectCount() == (oldObjectCount + 1));
            return AttributeExplorationCallback.REJECT;
        }
    };

    private void addNewObjectToContext(String newObjectName, Set lastCounterExample) {
        contextModificationCallback.addObjectToContext(newObjectName, lastCounterExample);
    }

    ModifiableSet counterExample;

    public void performAttributeExploration() {
        Assert.isTrue(implicationSet.getAttributesInformation() == context);
        counterExample = ContextFactoryRegistry.createSet(context.getAttributeCount());
        calculator = new NextClosedSetImplicationCalculator();
        calculator.setRelation(context.getRelation());
        implicationSet.clear(); // not neccesary true, but for first version is ok
        calculator.setImplications(implicationSet);
        calculator.setAttributeExplorationCallback(attributeExplorationCallback);
        calculator.calcImplications();
    }

    private class DefaultContextModificationCallback implements AttributeExplorer.ContextModificationCallback {
        public void addObjectToContext(String name, Set intent) {
            context.addObjectWithNameAndIntent(name, intent);
        }
    }
}
