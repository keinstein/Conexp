/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend;

import javax.swing.JComponent;


public interface OptionPaneProvider extends View {
//----------------------------------------------

    JComponent getViewOptions();
}
