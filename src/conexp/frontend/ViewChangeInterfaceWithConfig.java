/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend;

import javax.swing.*;
import java.awt.Component;

public interface ViewChangeInterfaceWithConfig extends OptionPaneProvider, ConfigProvider, ActionChainBearer {
    Component getViewComponent();
    Action[] getActions();
}
