/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend;

import conexp.frontend.latticeeditor.LatticeDrawing;
import util.PropertyChangeSupplier;

public interface LatticeDrawingProvider extends PropertyChangeSupplier {
    LatticeDrawing getDrawing();
}
