/*
 * User: Serhiy Yevtushenko
 * Date: May 7, 2002
 * Time: 11:19:49 PM
 */
package conexp.frontend;

import conexp.core.Set;

public interface SetProvidingAttributeMask extends AttributeMask {

    Set toSet();
}
