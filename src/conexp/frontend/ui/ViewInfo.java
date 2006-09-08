package conexp.frontend.ui;


/**
 * Created by IntelliJ IDEA.
 * User: sergey
 * Date: 19/5/2005
 * Time: 11:59:51
 * To change this template use Options | File Templates.
 */
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
