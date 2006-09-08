/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.frontend.ui;



public abstract class ViewInfo implements IViewInfo {
    final String viewPlace;
    final String viewCaption;

    public ViewInfo(String viewPlace, String viewCaption) {
        this.viewPlace = viewPlace;
        this.viewCaption = viewCaption;
    }

    public String getViewPlace() {
        return viewPlace;
    }

    public String getViewCaption() {
        return viewCaption;
    }

}
