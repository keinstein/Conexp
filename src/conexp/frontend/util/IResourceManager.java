package conexp.frontend.util;

/**
 * Author: Serhiy Yevtushenko
 * Date: 14.01.2003
 * Time: 23:34:56
 */
public interface IResourceManager {

    String getCommandLabel(String key);

    String[] getResourceDescription(String resName);

    String getCommandImage(String command);

    String getCommandShortcut(String command);

    String getCommandTooltip(String command);
}
