/*
 * Created by IntelliJ IDEA.
 * User: Serhiy Yevtushenko
 * Date: May 17, 2002
 * Time: 8:15:53 PM
 * To change template for new interface use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package conexp.frontend;

import javax.swing.*;
import java.awt.Component;

public interface Document{
    void addViewChangeListener(ViewChangeListener optionPaneViewChangeListener);
    void removeViewChangeListener(ViewChangeListener optionPaneViewChangeListener);

    void setParentActionChain(ActionMap actionChain);
    void activateViews();

    Component getDocComponent();
    JToolBar getToolBar();
}
