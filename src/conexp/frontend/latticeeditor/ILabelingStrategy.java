/*
 * User: Serhiy Yevtushenko
 * Date: 07.03.2002
 * Time: 13:12:35
 */
package conexp.frontend.latticeeditor;

import conexp.util.GenericStrategy;

public interface ILabelingStrategy extends GenericStrategy {
    void setContext(conexp.core.ExtendedContextEditingInterface cxt);

    void shutdown(ConceptSetDrawing drawing);

    void init(ConceptSetDrawing drawing);
}
