package conexp.frontend;

import conexp.frontend.components.LatticeComponent;
import conexp.core.Context;

/**
 * Created by IntelliJ IDEA.
 * User: sergey
 * Date: 20/5/2005
 * Time: 1:46:18
 * To change this template use Options | File Templates.
 */
public interface ILatticeComponentFactory {
    LatticeComponent makeLatticeComponent(Context cxt);
}
