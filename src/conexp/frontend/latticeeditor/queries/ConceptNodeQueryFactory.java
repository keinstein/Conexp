package conexp.frontend.latticeeditor.queries;

import conexp.core.ConceptFactory;
import conexp.core.ContextFactoryRegistry;
import conexp.core.tests.SetBuilder;

/**
 * Author: Serhiy Yevtushenko
 * Date: Dec 4, 2002
 * Time: 1:48:58 AM
 */
public class ConceptNodeQueryFactory {
    public static ConceptNodeQuery makeEmpty(){
        return new ConceptNodeQuery(SetBuilder.makeContext(new int[0][0]), ConceptFactory.makeEmptyLatticeElement(),
                ContextFactoryRegistry.createSet(0));
    }

    public static ConceptNodeQuery makeWithOwnObjects(){
          return new ConceptNodeQuery(SetBuilder.makeContext(new int[2][0]),
                ConceptFactory.makeLatticeElementWithOwnObjects(), SetBuilder.makeSet(new int[0]));
    }

    public static ConceptNodeQuery makeWithOwnAttribs(){
          return new ConceptNodeQuery(SetBuilder.makeContext(new int[2][0]),
                ConceptFactory.makeLatticeElementWithOwnAttribs(), SetBuilder.makeSet(new int[]{1, 1}));
    }
}
