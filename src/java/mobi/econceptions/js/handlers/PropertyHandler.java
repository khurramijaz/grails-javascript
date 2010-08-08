package mobi.econceptions.js.handlers;

import mobi.econceptions.js.StatementMutator;

public interface PropertyHandler extends Handler{
	public boolean propertyMissing(StatementMutator m ,String name, Object value);
	public boolean propertyMissing(StatementMutator m ,String name);
}
