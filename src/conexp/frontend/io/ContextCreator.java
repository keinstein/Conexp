package conexp.frontend.io;

import conexp.core.Context;

public interface ContextCreator {

	/**
	 *  creates a context using the entity to interpret it
	 *  @return Context
	 */
	public Context createContext(Object entity);

}
