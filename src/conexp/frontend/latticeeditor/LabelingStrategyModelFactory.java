/*
 * Created by IntelliJ IDEA.
 * User: Serhiy Yevtushenko
 * Date: 06.03.2002
 * Time: 14:20:41
 * To change template for new interface use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package conexp.frontend.latticeeditor;

import conexp.util.gui.strategymodel.StrategyModel;

public interface LabelingStrategyModelFactory {
    StrategyModel makeAttributeLabelingStrategiesModel();

    StrategyModel makeObjectsLabelingStrategiesModel();
}
