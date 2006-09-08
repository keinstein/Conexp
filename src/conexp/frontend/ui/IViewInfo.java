package conexp.frontend.ui;

import conexp.frontend.View;

/**
 * Created by IntelliJ IDEA.
 * User: sergey
 * Date: 19/5/2005
 * Time: 11:59:00
 * To change this template use Options | File Templates.
 */
public interface IViewInfo {
    String getViewCaption();

    String getViewPlace();

    View createView();
}
