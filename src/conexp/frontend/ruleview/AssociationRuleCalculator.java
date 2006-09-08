/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.ruleview;

import conexp.core.Context;
import conexp.core.DependencySet;
import conexp.core.associations.SimpleAssociationMiner;
import conexp.frontend.DependencySetCalculator;
import conexp.frontend.DependencySetSupplier;
import conexp.util.gui.paramseditor.BoundedDoubleValueParamInfo;
import conexp.util.gui.paramseditor.IntValueParamInfo;
import conexp.util.gui.paramseditor.ParamInfo;
import conexp.util.valuemodels.BoundedDoubleValue;
import conexp.util.valuemodels.IntValueModel;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;


public class AssociationRuleCalculator extends AbstractDependencySetCalculator implements DependencySetSupplier, DependencySetCalculator {
    public AssociationRuleCalculator(Context cxt) {
        super(cxt);
    }

    protected DependencySet makeDependencySet() {
        return new DependencySet(getContext());
    }

    protected conexp.core.associations.AssociationMiner miner;

    protected void doFindDependencies() {
        getMiner().setContext(getContext());
        getMiner().findAssociations(getDependencySet(), getMinSupport(), getConfidence());
    }

    protected conexp.util.valuemodels.BoundedDoubleValue confidenceValueModel;

    protected BoundedDoubleValue getConfidenceValueModel() {
        if (null == confidenceValueModel) {
            confidenceValueModel = new BoundedDoubleValue("confidence", 0.8, 0., 1.0, 100);
            confidenceValueModel.addPropertyChangeListener(new PropertyChangeListener() {
                public void propertyChange(PropertyChangeEvent evt) {
                    findDependencies();
                }
            });
        }
        return confidenceValueModel;
    }

    protected double getConfidence() {
        return getConfidenceValueModel().getValue();
    }


    protected synchronized conexp.core.associations.AssociationMiner getMiner() {
        if (null == miner) {
            miner = new SimpleAssociationMiner();
        }
        return miner;
    }

    protected conexp.util.valuemodels.IntValueModel minSupportValueModel;

    protected IntValueModel getMinSupportValueModel() {
        if (null == minSupportValueModel) {
            minSupportValueModel = new IntValueModel("minSupport", 0);
            minSupportValueModel.addPropertyChangeListener(new PropertyChangeListener() {
                public void propertyChange(PropertyChangeEvent evt) {
                    findDependencies();
                }
            });
        }
        return minSupportValueModel;
    }

    protected int getMinSupport() {
        return getMinSupportValueModel().getValue();
    }

    protected conexp.util.gui.paramseditor.ParamInfo[] params;

    public ParamInfo[] getParams() {
        if (null == params) {
            params = new ParamInfo[]{
                    new IntValueParamInfo("Minimal support", getMinSupportValueModel()),
                    new BoundedDoubleValueParamInfo("Confidence", getConfidenceValueModel())
            };
        }
        return params;
    }

    public void setConfidence(double newValue) {
        getConfidenceValueModel().setValue(newValue);
    }
}
