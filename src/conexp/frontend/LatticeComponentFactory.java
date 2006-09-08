package conexp.frontend;

import conexp.core.Context;
import conexp.core.layoutengines.SimpleLayoutEngine;
import conexp.frontend.components.LatticeComponent;

/**
 * Created by IntelliJ IDEA.
 * User: sergey
 * Date: 20/5/2005
 * Time: 1:43:37
 * To change this template use Options | File Templates.
 */
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

