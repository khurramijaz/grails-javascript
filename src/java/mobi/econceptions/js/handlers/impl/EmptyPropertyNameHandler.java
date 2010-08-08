package mobi.econceptions.js.handlers.impl;

import mobi.econceptions.js.StatementMutator;

public class EmptyPropertyNameHandler extends DefaultPropertyHandler{
	@Override
	public boolean propertyMissing(StatementMutator m, String name) {
		return super.propertyMissing(m, translateName( m , name) );
	}

	@Override
	public boolean propertyMissing(StatementMutator m, String name, Object value) {
		return super.propertyMissing(m, translateName( m , name) , value);
	}
	protected String translateName(StatementMutator m , String name){
		if( m.size() == 0 && "".equals(name)) return "$";
		return name;
	}
}
