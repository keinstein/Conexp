package conexp.frontend.ruleview;

import conexp.core.Context;
import conexp.core.DependencySet;
import conexp.frontend.DependencySetCalculator;
import conexp.frontend.DependencySetSupplier;
import conexp.util.gui.paramseditor.ParamInfo;
import util.BasePropertyChangeSupplier;


public abstract class AbstractDependencySetCalculator extends BasePropertyChangeSupplier implements DependencySetSupplier, DependencySetCalculator {
    public AbstractDependencySetCalculator(Context cxt) {
        super();
        setContext(cxt);
    }

    protected Context context;

    public void setContext(Context newContext) {
        if (context != newContext) {
            context = newContext;
            clearDependencySet();
        }
    }

    protected Context getContext() {
        return context;
    }

    public void clearDependencySet() {
        dependencySet = null;
        getPropertyChangeSupport().firePropertyChange(RULE_SET_CLEARED, null, null);
    }

    protected DependencySet dependencySet;

    public DependencySet getDependencySet() {
        if (null == dependencySet) {
            dependencySet = makeDependencySet();
        }
        return dependencySet;
    }

    protected abstract DependencySet makeDependencySet();


    public void findDependencies() {
        doFindDependencies();
        getPropertyChangeSupport().firePropertyChange(DependencySetSupplier.RULE_SET_PROPERTY, null, getDependencySet());
    }

    protected abstract void doFindDependencies();

    public ParamInfo[] getParams() {
        return new ParamInfo[0];
    }
}