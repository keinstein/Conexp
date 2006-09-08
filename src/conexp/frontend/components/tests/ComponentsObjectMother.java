/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.frontend.components.tests;

import conexp.core.layoutengines.SimpleLayoutEngine;
import conexp.core.tests.SetBuilder;
import conexp.frontend.components.LatticeComponent;


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
