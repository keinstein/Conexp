package conexp.core.associations;

import conexp.core.GenericDependency;
import conexp.core.ItemSet;
import conexp.core.ModifiableSet;
import conexp.core.Set;

/**
 * Insert the type's description here.
 * Creation date: (01.05.01 13:47:30)
 * @author  Serhiy Yevtushenko
 */
public class AssociationRule extends GenericDependency {

    public AssociationRule(Set premise, int premiseSupport, Set conclusion, int conclusionSupport) {
        super(premise, conclusion, premiseSupport, conclusionSupport);
    }


    public static AssociationRule makeFromItemsets(ItemSet premise, ItemSet conclusion) {
        util.Assert.isTrue(premise.getObjCnt() >= conclusion.getObjCnt());
        ModifiableSet temp = conclusion.getAttribs().makeModifiableSetCopy();
        temp.andNot(premise.getAttribs());
        //copy is done in constructor of GenericDependency
        return new AssociationRule(premise.getAttribs(), premise.getObjCnt(), temp, conclusion.getObjCnt());
    }
}