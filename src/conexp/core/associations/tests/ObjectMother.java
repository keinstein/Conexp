package conexp.core.associations.tests;

import conexp.core.associations.AssociationRule;
import conexp.core.tests.SetBuilder;

/**
 * Author: Serhiy Yevtushenko
 * Date: Dec 4, 2002
 * Time: 2:04:46 AM
 */
public class ObjectMother {
    public static AssociationRule makeAssociationRule(int[] premise, int premiseSupport, int[] conclusion, int conclusionSupport) {
        return new AssociationRule(SetBuilder.makeSet(premise), premiseSupport,
                SetBuilder.makeSet(conclusion), conclusionSupport);
    }

    public static AssociationRule makeAssociationRule(int[] premise,
                                                      int[] conclusion,
                                                      int support,
                                                      double confidence){
        return makeAssociationRule(
                premise,
                Math.round((float)(support/confidence)),
                conclusion,
                support
        );
    }
}
