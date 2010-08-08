package mobi.econceptions.js.handlers.impl;

import mobi.econceptions.js.StatementMutator;
import mobi.econceptions.js.handlers.HandlerType;
import mobi.econceptions.js.handlers.PropertyHandler;

public class ThisReferenceHandler extends AbstractHandler implements PropertyHandler{
	public ThisReferenceHandler(){
		super(HandlerType.Property );
	}

	public boolean propertyMissing(StatementMutator m, String name) {
		if( m.size() == 0 && "This".equals(name)){
			m.appendName( "this");
			return true;
		}
		return false;
	}

	public boolean propertyMissing(StatementMutator m, String name, Object value) {
		return false; 
	}
}
