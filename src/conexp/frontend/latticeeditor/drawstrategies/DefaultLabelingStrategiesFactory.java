/*
 * User: Serhiy Yevtushenko
 * Date: 07.03.2002
 * Time: 18:14:52
  */
package conexp.frontend.latticeeditor.drawstrategies;

import conexp.frontend.latticeeditor.DrawParameters;
import conexp.frontend.latticeeditor.LabelingStrategyModelFactory;
import conexp.util.gui.strategymodel.StrategyModel;

public class DefaultLabelingStrategiesFactory implements LabelingStrategyModelFactory{
    DrawParameters drawParams;

    public DefaultLabelingStrategiesFactory(DrawParameters drawParams) {
        this.drawParams = drawParams;
    }

    public StrategyModel makeAttributeLabelingStrategiesModel() {
        return new conexp.frontend.latticeeditor.labelingstrategies.AttributesLabelingStrategyModel(drawParams);
    }

    public StrategyModel makeObjectsLabelingStrategiesModel() {
        return new conexp.frontend.latticeeditor.labelingstrategies.ObjectsLabelingStrategyModel(drawParams);
    }
}
