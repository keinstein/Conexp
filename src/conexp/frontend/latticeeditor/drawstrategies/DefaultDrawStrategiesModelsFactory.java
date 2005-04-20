/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.frontend.latticeeditor.drawstrategies;

import conexp.frontend.ResourceLoader;
import conexp.frontend.latticeeditor.DrawParameters;
import conexp.frontend.latticeeditor.DrawStrategiesModelsFactory;
import conexp.util.gui.strategymodel.StrategyModel;

public class DefaultDrawStrategiesModelsFactory implements DrawStrategiesModelsFactory {

    DrawParameters drawParams;

    public DefaultDrawStrategiesModelsFactory(DrawParameters drawParams) {
        this.drawParams = drawParams;
    }

    public StrategyModel makeEdgeSizeStrategiesModel() {
        return new conexp.frontend.latticeeditor.edgesizecalcstrategies.EdgeSizeStrategyModel(drawParams);
    }

    public StrategyModel makeHighlightStrategiesModel() {
        return new conexp.frontend.latticeeditor.highlightstrategies.HighlightStrategyModel(drawParams);
    }


    public StrategyModel makeLayoutStrategiesModel() {
        String xmlFileUrl = ResourceLoader.getResourceUrl("resources/LayoutStrategyModel.xml").toString();
        return new conexp.frontend.latticeeditor.XMLFileStrategyModel(xmlFileUrl);
    }

    public StrategyModel makeNodeRadiusStrategiesModel() {
        return new conexp.frontend.latticeeditor.noderadiusstrategy.NodeRadiusStrategyModel(drawParams);
    }

    public DrawStrategiesModelsFactory makeCopy() {
        return new DefaultDrawStrategiesModelsFactory(drawParams.makeCopy());
    }
}
