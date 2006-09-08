/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.associations.tests;

import conexp.core.associations.AssociationRule;
import conexp.core.tests.SetBuilder;


public class ObjectMother {
    private ObjectMother() {
    }

    public static AssociationRule makeAssociationRule(int[] premise, int premiseSupport, int[] conclusion, int conclusionSupport) {
        return new AssociationRule(SetBuilder.makeSet(premise), premiseSupport,
                SetBuilder.makeSet(conclusion), conclusionSupport);
    }

    public static AssociationRule makeAssociationRule(int[] premise,
                                                      int[] conclusion,
                                                      int support,
                                                      double confidence) {
        return makeAssociationRule(premise,
                Math.round((float) (support / confidence)),
                conclusion,
                support);
    }
}
