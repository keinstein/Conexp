/*
 * Created by IntelliJ IDEA.
 * User: sergey
 * Date: Nov 17, 2001
 * Time: 3:53:53 PM
 * To change template for new interface use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package conexp.frontend;

import javax.swing.*;
import java.awt.Component;

public interface ViewChangeInterfaceWithConfig extends OptionPaneProvider, ConfigProvider, ActionChainBearer {
    Component getViewComponent();

    Action[] getActions();
}
