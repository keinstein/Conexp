/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.latticeeditor.drawstrategies;

import conexp.frontend.ResourceLoader;
import conexp.frontend.latticeeditor.DrawParameters;
import conexp.frontend.latticeeditor.DrawStrategiesModelsFactory;
import conexp.frontend.latticeeditor.XMLFileStrategyModel;
import conexp.frontend.latticeeditor.edgesizecalcstrategies.EdgeSizeStrategyModel;
import conexp.frontend.latticeeditor.highlightstrategies.HighlightStrategyModel;
import conexp.frontend.latticeeditor.noderadiusstrategy.NodeRadiusStrategyModel;
import conexp.util.gui.strategymodel.StrategyModel;

public class DefaultDrawStrategiesModelsFactory implements DrawStrategiesModelsFactory {

    DrawParameters drawParams;

    public DefaultDrawStrategiesModelsFactory(DrawParameters drawParams) {
        this.drawParams = drawParams;
    }

    public StrategyModel makeEdgeSizeStrategiesModel() {
        return new EdgeSizeStrategyModel(drawParams);
    }

    public StrategyModel makeHighlightStrategiesModel() {
        return new HighlightStrategyModel(drawParams);
    }


    public StrategyModel makeLayoutStrategiesModel() {
        String xmlFileUrl = ResourceLoader.getResourceUrl("resources/LayoutStrategyModel.xml").toString();
        return new XMLFileStrategyModel(xmlFileUrl);
    }

    public StrategyModel makeNodeRadiusStrategiesModel() {
        return new NodeRadiusStrategyModel(drawParams);
    }

    public DrawStrategiesModelsFactory makeCopy() {
        return new DefaultDrawStrategiesModelsFactory(drawParams.makeCopy());
    }
}
