/**
 * Copyright (c) 2000-2003, Sergey Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/

package conexp.frontend.ui;

import java.util.HashMap;


public class ViewMap {
    protected static class ViewInfo {
        ViewInfo(String capt_, String type_) {
            caption = capt_;
            type = type_;
        }

        final String type;
        final String caption;
    }

    protected HashMap viewInfoMap;

    protected java.util.Map getViewInfoMap() {
        if (null == viewInfoMap) {
            viewInfoMap = new HashMap();
        }
        return viewInfoMap;
    }

    protected String getViewCaption(String name) {
        ViewInfo inf = (ViewInfo) getViewInfoMap().get(name);
        if (null != inf) {
            return inf.caption;
        }
        return "";
    }


    protected String getViewType(String name) {
        ViewInfo inf = (ViewInfo) getViewInfoMap().get(name);
        if (null != inf) {
            return inf.type;
        }
        return "";
    }


    public void registerView(String id, String type, String caption) throws ViewManagerException {
        util.Assert.isTrue(null != id, "Name arg can't be null");
        util.Assert.isTrue(null != type, "Type arg can't be null");
        if (getViewInfoMap().containsKey(id)) {
            throw new ViewManagerException("ViewManager already contain's view with id =" + id);
        }
        getViewInfoMap().put(id, new ViewInfo(caption, type));
    }

}
