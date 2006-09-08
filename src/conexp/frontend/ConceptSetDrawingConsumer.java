/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend;

import conexp.frontend.latticeeditor.ConceptSetDrawing;

public interface ConceptSetDrawingConsumer {
    void setConceptSetDrawing(ConceptSetDrawing drawing);

    void clearConceptSetDrawing();
}
