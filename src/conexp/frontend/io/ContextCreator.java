package conexp.frontend.io;
import conexp.core.Context;
public interface ContextCreator {
    /**
     * creates a context using the entity to interpret it.
     *
     * @return Context
     */
    Context createContext(Object entity);
}
