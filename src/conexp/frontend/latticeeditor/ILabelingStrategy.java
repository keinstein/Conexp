/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.latticeeditor;

import conexp.core.ExtendedContextEditingInterface;
import conexp.core.layout.LayoutParameters;
import conexp.util.GenericStrategy;

public interface ILabelingStrategy extends GenericStrategy {
    void setContext(ExtendedContextEditingInterface cxt);

    void shutdown(ConceptSetDrawing drawing);

    void init(ConceptSetDrawing drawing, LayoutParameters drawParams);
}
