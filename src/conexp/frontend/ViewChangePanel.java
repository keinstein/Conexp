/**
 * Copyright (c) 2000-2003, Sergey Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/

package conexp.frontend;

import conexp.frontend.util.ActionChainUtil;
import conexp.frontend.util.IResourceManager;
import conexp.frontend.util.ResourceManager;

import javax.swing.*;
import java.awt.*;
import java.util.ResourceBundle;


public abstract class ViewChangePanel extends javax.swing.JPanel implements ViewChangeInterfaceWithConfig {
    private ActionMap actionChain = new ActionMap();

//---------------------------------------------
    public Component getViewComponent() {
        return this;
    }

    public ViewChangePanel(ActionMap parentActionChain) {
        getActionChain().setParent(parentActionChain);
        ActionChainUtil.putActions(getActionChain(), getActions());
    }
//---------------------------------------------

    public ActionMap getActionChain() {
        return actionChain;
    }
//---------------------------------------------
    public Action[] getActions() {
        return null;
    }

    public IResourceManager getResourceManager() {
        return new ResourceManager(getResources());
    }

    protected abstract ResourceBundle getResources();
}
