/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.latticeeditor;

import conexp.util.gui.paramseditor.ParamInfo;
import conexp.util.gui.paramseditor.StrategyValueItemParamInfo;
import conexp.util.gui.strategymodel.StrategyValueItem;

import java.beans.PropertyChangeSupport;

public class LabelingStrategiesContextImpl extends BasicStrategiesContext implements LabelingStrategiesContext {
    private StrategyValueItem attrLabelingStrategy;
    private StrategyValueItem objLabelingStrategy;

    private LabelingStrategyModelFactory factory;

    public LabelingStrategiesContextImpl(LabelingStrategyModelFactory factory, PropertyChangeSupport propertyChange) {
        super(propertyChange);
        this.factory = factory;
    }

    public ILabelingStrategy getAttrLabelingStrategy() {
        return (ILabelingStrategy) getAttrLabelingStrategyItem().getStrategy();
    }

    synchronized StrategyValueItem getAttrLabelingStrategyItem() {
        if (null == attrLabelingStrategy) {
            attrLabelingStrategy = makeStrategyValueItem("drawAttribs", factory.makeAttributeLabelingStrategiesModel());
        }
        return attrLabelingStrategy;
    }

    public String getAttributeLabelingStrategyKey() {
        return getAttrLabelingStrategyItem().getStrategyKey();
    }

    public boolean setAttributeLabelingStrategyByKey(String key) {
        return getAttrLabelingStrategyItem().setValueByKey(key);
    }

    public ILabelingStrategy getObjectsLabelingStrategy() {
        return (ILabelingStrategy) getObjectsLabelingStrategyItem().getStrategy();
    }

    synchronized StrategyValueItem getObjectsLabelingStrategyItem() {
        if (null == objLabelingStrategy) {
            objLabelingStrategy =
                    makeStrategyValueItem(
                            "drawObjects",
                            factory.makeObjectsLabelingStrategiesModel());
        }
        return objLabelingStrategy;
    }

    public String getObjectLabelingStrategyKey() {
        return getObjectsLabelingStrategyItem().getStrategyKey();
    }

    public boolean setObjectLabelingStrategyKey(String key) {
        return getObjectsLabelingStrategyItem().setValueByKey(key);
    }

    protected ParamInfo[] makeParamInfo() {
        return new ParamInfo[]{
            new StrategyValueItemParamInfo("Attribs", getAttrLabelingStrategyItem()),
            new StrategyValueItemParamInfo("Objects", getObjectsLabelingStrategyItem())
        };
    }

    public void initStrategies(conexp.core.ExtendedContextEditingInterface cxt, ConceptSetDrawing fd) {

        setupLabelingStrategies(cxt);

        doInitStrategies(fd);
    }

    public void setupLabelingStrategies(conexp.core.ExtendedContextEditingInterface cxt) {
        getAttrLabelingStrategy().setContext(cxt);
        getObjectsLabelingStrategy().setContext(cxt);
    }

    private void doInitStrategies(ConceptSetDrawing fd) {
        final DrawParameters drawParams = fd.getLatticeDrawingOptions().getDrawParams();
        getAttrLabelingStrategy().init(fd, drawParams);
        getObjectsLabelingStrategy().init(fd, drawParams);
        fd.makeBoundsRectDirty();
    }

    public void shutdownStrategies(ConceptSetDrawing fd) {
        getAttrLabelingStrategy().shutdown(fd);
        getObjectsLabelingStrategy().shutdown(fd);
    }
}
