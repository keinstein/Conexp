package conexp.frontend.latticeeditor;

import conexp.util.GenericStrategy;

/**
 * Insert the type's description here.
 * Creation date: (07.03.01 7:33:46)
 * @author Serhiy  Yevtushenko
 */
public abstract class DrawParamsIndependentStrategyModel extends conexp.frontend.latticeeditor.AbstractDrawingStrategyModel {

    public DrawParamsIndependentStrategyModel() {
        super(null);
    }


    protected DrawParamsIndependentStrategyModel(boolean unused) {
        super();
    }


    protected void createStrategies(
            DrawParameters opt) {
        String[][] createInfo = getCreateInfo();
        allocateStrategies(createInfo.length);
        for (int i = 0; i < createInfo.length; i++) {
            GenericStrategy genericStrategy= makeGenericStrategyByClassName(createInfo[i][1]);

            setStrategy(
                    i,
                    createInfo[i][0],
                    genericStrategy);


        }
    }



    public abstract String[][] getCreateInfo();
}