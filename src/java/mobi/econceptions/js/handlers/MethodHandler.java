package mobi.econceptions.js.handlers;

import mobi.econceptions.js.StatementMutator;

public interface MethodHandler extends Handler{
	public boolean methodMissing(StatementMutator m, String name , Object[] args);
}
