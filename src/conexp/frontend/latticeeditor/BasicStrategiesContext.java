/*
 * Created by IntelliJ IDEA.
 * User: Serhiy Yevtushenko
 * Date: 06.03.2002
 * Time: 14:27:57
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package conexp.frontend.latticeeditor;

import conexp.util.gui.paramseditor.ParamInfo;
import conexp.util.gui.paramseditor.ParamsProvider;
import conexp.util.gui.strategymodel.StrategyModel;
import conexp.util.gui.strategymodel.StrategyValueItem;
import util.BasePropertyChangeSupplier;

public class BasicStrategiesContext extends BasePropertyChangeSupplier implements ParamsProvider {

    public BasicStrategiesContext(java.beans.PropertyChangeSupport propertyChange) {
        this.propertyChange = propertyChange;
    }

    protected StrategyValueItem makeStrategyValueItem(String label, StrategyModel model) {
        return new StrategyValueItem(label, model, getPropertyChangeSupport());
    }

    private ParamInfo[] params;

    protected ParamInfo[] makeParamInfo() {
        return new ParamInfo[0];
    }

    synchronized public ParamInfo[] getParams() {
        if (null == params) {
            params = makeParamInfo();
        }
        return params;
    }

}
