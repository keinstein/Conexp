/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.util;


public interface IResourceManager {

    String getCommandLabel(String key);

    String[] getResourceDescription(String resName);

    String getCommandImage(String command);

    String getCommandShortcut(String command);

    String getCommandTooltip(String command);

    String getSelectedImage(String command);
}
