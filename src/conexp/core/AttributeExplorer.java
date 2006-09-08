/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core;

public interface AttributeExplorer {
    interface AttributeExplorerUserCallback {
        int STOP = -1;
        int REJECT_IMPLICATION = 0;
        int ACCEPT_IMPLICATION = 1;
        /**
         * alias for readability
         */
        int HAS_CONTREXAMPLE = REJECT_IMPLICATION;

        void setAttributeInformationSupplier(AttributeInformationSupplier attrInfo);

        /**
         * returns STOP - to stop attribute exploration
         * returns REJECT_IMPLICATION - when implication is not true
         * returns ACCEPT_IMPLICATION - when implication is true
         */
        int isTrue(Set premise, Set conclusion);

        /**
         * returns STOP - when user wants to interrupt attribute exploration
         * returns HAS_CONTREXAMPLE - when user provides a contrexample
         * returns ACCEPT_IMPLICATION - when user doesn't can't provide a valid contrexample
         */

        int queryContrExample(ContextEntity obj, ModifiableSet contrExample);

        void error(AttributeExplorationError error);
    }


    interface ContextModificationCallback {
        void addObjectToContext(String name, Set intent);
    }

    void setUserCallback(AttributeExplorer.AttributeExplorerUserCallback userCallback);

    void setContextModificationCallback(AttributeExplorer.ContextModificationCallback contextModificationCallback);

    void setContext(Context context);

    void setImplicationSet(ImplicationSet implicationSet);

    void performAttributeExploration();
}
