/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.latticeeditor;

import conexp.util.GenericStrategy;

public interface ILabelingStrategy extends GenericStrategy {
    void setContext(conexp.core.ExtendedContextEditingInterface cxt);

    void shutdown(ConceptSetDrawing drawing);

    void init(ConceptSetDrawing drawing);
}
