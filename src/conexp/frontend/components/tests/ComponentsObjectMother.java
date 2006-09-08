package conexp.frontend.components.tests;

import conexp.core.layoutengines.SimpleLayoutEngine;
import conexp.core.tests.SetBuilder;
import conexp.frontend.components.LatticeComponent;

/**
 * Created by IntelliJ IDEA.
 * User: sergey
 * Date: 20/5/2005
 * Time: 1:25:30
 * To change this template use Options | File Templates.
 */
public class ComponentsObjectMother {
    public static LatticeComponent makeLatticeComponent(int[][] relation) {
        return new LatticeComponent(SetBuilder.makeContext(relation));
    }

    public static LatticeComponent makeLatticeComponentWithSimpleLayoutEngine(int[][] relation) {
        LatticeComponent component = makeLatticeComponent(relation);
        component.setLayoutEngine(new SimpleLayoutEngine());
        return component;
    }

    public static LatticeComponent makeLatticeComponent() {
        return new LatticeComponent();
    }
}
