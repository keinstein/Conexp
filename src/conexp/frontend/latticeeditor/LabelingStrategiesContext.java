/*
 * Created by IntelliJ IDEA.
 * User: Serhiy Yevtushenko
 * Date: 06.03.2002
 * Time: 1:41:51
 * To change template for new interface use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package conexp.frontend.latticeeditor;

import conexp.core.ExtendedContextEditingInterface;

public interface LabelingStrategiesContext {
    ILabelingStrategy getAttrLabelingStrategy();
    String getAttributeLabelingStrategyKey();
    boolean setAttributeLabelingStrategyByKey(String key);

    ILabelingStrategy getObjectsLabelingStrategy();
    String getObjectLabelingStrategyKey();
    boolean setObjectLabelingStrategyKey(String key);


    void initStrategies(ExtendedContextEditingInterface cxt, ConceptSetDrawing fd);
    void setupLabelingStrategies(ExtendedContextEditingInterface cxt);

    void shutdownStrategies(ConceptSetDrawing fd);
}
