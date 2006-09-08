/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.frontend;

import conexp.core.Context;
import conexp.core.layoutengines.SimpleLayoutEngine;
import conexp.frontend.components.LatticeComponent;


public class LatticeComponentFactory {
    static ILatticeComponentFactory ourInstance;

    static {
        configureDefault();
    }

    private LatticeComponentFactory() {
    }

    public static void setOurInstance(ILatticeComponentFactory ourInstance) {
        LatticeComponentFactory.ourInstance = ourInstance;
    }

    public static void configureDefault() {
        setOurInstance(DefaultLatticeComponentFactory.getInstance());
    }

    public static void configureTest() {
        setOurInstance(LatticeComponentFactoryWithSimpleLayout.getInstance());
    }

    public static LatticeComponent makeLatticeComponent(Context cxt) {
        return getInstance().makeLatticeComponent(cxt);
    }

    public static ILatticeComponentFactory getInstance() {
        return ourInstance;
    }

    private static class LatticeComponentFactoryWithSimpleLayout implements ILatticeComponentFactory {
        private static final ILatticeComponentFactory ourInstance = new LatticeComponentFactoryWithSimpleLayout();

        static ILatticeComponentFactory getInstance() {
            return ourInstance;
        }

        private LatticeComponentFactoryWithSimpleLayout() {
        }

        public LatticeComponent makeLatticeComponent(Context cxt) {
            LatticeComponent latticeComponent = new LatticeComponent(cxt);
            latticeComponent.setLayoutEngine(new SimpleLayoutEngine());
            return latticeComponent;
        }
    }

    private static class DefaultLatticeComponentFactory implements ILatticeComponentFactory {
        private static final ILatticeComponentFactory ourInstance = new DefaultLatticeComponentFactory();

        static ILatticeComponentFactory getInstance() {
            return ourInstance;
        }

        private DefaultLatticeComponentFactory() {
        }

        public LatticeComponent makeLatticeComponent(Context cxt) {
            return new LatticeComponent(cxt);
        }
    }
}

