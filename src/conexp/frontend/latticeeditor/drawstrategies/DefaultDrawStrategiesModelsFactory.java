package conexp.frontend.latticeeditor.drawstrategies;

import conexp.frontend.ResourceLoader;
import conexp.frontend.latticeeditor.DrawParameters;
import conexp.frontend.latticeeditor.ModelsFactory;
import conexp.util.gui.strategymodel.StrategyModel;

public class DefaultDrawStrategiesModelsFactory implements ModelsFactory {

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

}