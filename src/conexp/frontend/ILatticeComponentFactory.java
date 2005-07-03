package conexp.frontend;

import conexp.core.Context;
import conexp.frontend.components.LatticeComponent;

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
