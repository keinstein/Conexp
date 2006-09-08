/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend;

import javax.swing.Action;
import java.awt.Component;

public interface ViewChangeInterfaceWithConfig extends OptionPaneProvider, ConfigProvider, ActionChainBearer {
    Component getViewComponent();

    Action[] getActions();
}
